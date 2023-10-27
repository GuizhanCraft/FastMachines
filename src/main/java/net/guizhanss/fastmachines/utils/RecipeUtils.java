package net.guizhanss.fastmachines.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.IRecipe;
import net.guizhanss.fastmachines.core.recipes.StandardRecipe;
import net.guizhanss.fastmachines.items.machines.AbstractFastMachine;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("ConstantConditions")
public final class RecipeUtils {
    private static final String MSG_ID_NULL = "id cannot be null";
    private static final String MSG_RECIPE_NULL = "recipes cannot be null";

    /**
     * Calculate the amount of each item in the given array.
     * Similar items will be merged by
     * {@link SlimefunUtils#isItemSimilar(ItemStack, ItemStack, boolean, boolean, boolean)}.
     *
     * @param items
     *     The array of {@link ItemStack}.
     *
     * @return A {@link Map} that contains the amount of each item.
     */
    public static Map<ItemStack, Integer> calculateItems(@Nonnull ItemStack... items) {
        Preconditions.checkArgument(items != null, "items cannot be null");
        Map<ItemStack, Integer> result = new HashMap<>();
        for (var item : items) {
            if (item == null || item.getType().isAir()) {
                continue;
            }
            boolean existingItem = false;
            for (var entry : result.entrySet()) {
                if (SlimefunUtils.isItemSimilar(item, entry.getKey(), false, false, true)) {
                    existingItem = true;
                    result.put(entry.getKey(), entry.getValue() + item.getAmount());
                    break;
                }
            }
            if (!existingItem) {
                ItemStack itemCopy = item.clone();
                itemCopy.setAmount(1);
                result.put(itemCopy, item.getAmount());
            }
        }
        return result;
    }

    /**
     * Register recipes from a {@link MultiBlockMachine}.
     *
     * @param recipes
     *     The {@link List} instance of {@link IRecipe} from a {@link AbstractFastMachine}.
     * @param id
     *     The id of the {@link MultiBlockMachine}.
     */
    @ParametersAreNonnullByDefault
    public static void registerMultiblockMachineRecipes(List<IRecipe> recipes, String id) {
        Preconditions.checkArgument(recipes != null, MSG_RECIPE_NULL);
        Preconditions.checkArgument(id != null, MSG_ID_NULL);

        SlimefunItem machineItem = SlimefunItem.getById(id);
        if (!(machineItem instanceof MultiBlockMachine machine)) {
            throw new IllegalArgumentException("must be a multiblock machine");
        }

        // initial recipes
        List<ItemStack[]> recipeList = machine.getRecipes();

        if (recipeList.size() % 2 != 0) {
            throw new IllegalArgumentException("The given MultiBlockMachine has illegal recipe list: " + id);
        }

        FastMachines.debug("Registering recipes from multiblock: {0}", id);
        FastMachines.debug("Total recipes: {0}", recipeList.size() / 2);
        for (int i = 0; i < recipeList.size(); i += 2) {
            ItemStack[] input = recipeList.get(i);
            ItemStack[] output = recipeList.get(i + 1);
            if (SlimefunItemUtils.isDisabled(output[0])) {
                continue;
            }
            var recipe = new StandardRecipe(output[0], input);
            FastMachines.debug("registering standard recipe: {0}", recipe);
            recipes.add(recipe);
        }
    }

    /**
     * Register recipes from a electric machine.
     *
     * @param recipes
     *     The {@link List} instance of {@link IRecipe} from a {@link AbstractFastMachine}.
     * @param id
     *     The id of the {@link MultiBlockMachine}.
     */
    @ParametersAreNonnullByDefault
    public static void registerMachineRecipes(List<IRecipe> recipes, String id) {
        Preconditions.checkArgument(recipes != null, MSG_RECIPE_NULL);
        Preconditions.checkArgument(id != null, MSG_ID_NULL);

        SlimefunItem machineItem = SlimefunItem.getById(id);
        if (machineItem == null) {
            throw new IllegalArgumentException("The given id is not a valid SlimefunItem: " + id);
        }

        FastMachines.debug("Registering recipes from machine: {0}", id);
        try {
            Method method = machineItem.getClass().getMethod("getMachineRecipes");
            List<MachineRecipe> machineRecipes = (List<MachineRecipe>) method.invoke(machineItem);
            if (machineRecipes == null) {
                FastMachines.debug("Retrieved recipes are null, ignoring.");
                return;
            }
            for (MachineRecipe machineRecipe : machineRecipes) {
                if (machineRecipe.getOutput().length > 0) {
                    ItemStack output = machineRecipe.getOutput()[0];
                    if (SlimefunItemUtils.isDisabled(output)) {
                        continue;
                    }

                    var recipe = new StandardRecipe(machineRecipe.getOutput()[0], machineRecipe.getInput());
                    FastMachines.debug("registering standard recipe: {0}", recipe);
                    recipes.add(recipe);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            FastMachines.log(Level.WARNING, "Failed to retrieve machine recipes from {0}, attempting to use backup method.", id);
            if (machineItem instanceof RecipeDisplayItem recipeDisplayItem) {
                registerDisplayRecipes(recipes, recipeDisplayItem.getDisplayRecipes());
                FastMachines.log(Level.INFO, "Backup method succeeded. You can ignore the above warning.");
            } else {
                FastMachines.log(Level.SEVERE, "Backup method failed, please report this.");
            }
        }
    }

    public static void registerDisplayRecipes(List<IRecipe> recipes, String id) {
        Preconditions.checkArgument(recipes != null, MSG_RECIPE_NULL);
        Preconditions.checkArgument(id != null, MSG_ID_NULL);

        SlimefunItem sfItem = SlimefunItem.getById(id);
        if (sfItem == null) {
            throw new IllegalArgumentException("The given id is not a valid SlimefunItem: " + id);
        }

        if (!(sfItem instanceof RecipeDisplayItem recipeDisplayItem)) {
            throw new IllegalArgumentException("The given item is not a valid RecipeDisplayItem: " + id);
        }

        registerDisplayRecipes(recipes, recipeDisplayItem.getDisplayRecipes());
    }

    /**
     * Register recipes from display recipes.
     *
     * @param recipes
     *     The {@link List} instance of {@link IRecipe} from a {@link AbstractFastMachine}.
     * @param displayRecipes
     *     The display recipes from a {@link RecipeDisplayItem}.
     */
    @ParametersAreNonnullByDefault
    public static void registerDisplayRecipes(List<IRecipe> recipes, List<ItemStack> displayRecipes) {
        Preconditions.checkArgument(recipes != null, MSG_RECIPE_NULL);
        Preconditions.checkArgument(displayRecipes != null, "displayRecipes cannot be null");

        if (displayRecipes.size() % 2 != 0) {
            throw new IllegalArgumentException("The display recipes list is illegal!");
        }

        for (int i = 0; i < displayRecipes.size(); i += 2) {
            ItemStack input = displayRecipes.get(i);
            ItemStack output = displayRecipes.get(i + 1);
            if (SlimefunItemUtils.isDisabled(output)) {
                continue;
            }
            var recipe = new StandardRecipe(output, input);
            FastMachines.debug("registering standard recipe: {0}", recipe);
            recipes.add(recipe);
        }
    }
}
