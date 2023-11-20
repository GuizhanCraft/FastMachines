package net.guizhanss.fastmachines.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import org.bukkit.Material;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.IRecipe;
import net.guizhanss.fastmachines.core.recipes.RandomRecipe;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.core.recipes.StandardRecipe;
import net.guizhanss.fastmachines.items.machines.AbstractFastMachine;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("ConstantConditions")
public final class RecipeUtils {
    private static final String MSG_ID_NULL = "id cannot be null";
    private static final String MSG_RECIPE_NULL = "recipes cannot be null";
    // This comparator is just used to check if 2 ItemStacks are similar.
    private static final Comparator<ItemStack> ITEM_COMPARATOR = (aItem, bItem) -> {
        if (aItem == null && bItem == null) return 0;
        if (aItem == null) return -1;
        if (bItem == null) return 1;
        if (isItemSimilar(aItem, bItem)) return 0;
        if (Objects.hashCode(aItem) != Objects.hashCode(bItem))
            return Objects.hashCode(aItem) - Objects.hashCode(bItem);
        if (aItem.getAmount() != bItem.getAmount()) return aItem.getAmount() - bItem.getAmount();
        return 0;
    };

    /**
     * This private method is only used by ITEM_COMPARATOR.
     * It covers edge cases that need to change the parameters of the
     * {@link SlimefunUtils#isItemSimilar(ItemStack, ItemStack, boolean, boolean, boolean)} call.
     *
     * @param aItem
     *     The first {@link ItemStack}
     * @param bItem
     *     The second {@link ItemStack}
     *
     * @return Whether the two items are similar.
     */
    @ParametersAreNonnullByDefault
    private static boolean isItemSimilar(ItemStack aItem, ItemStack bItem) {
        if (aItem.getType() == Material.SPAWNER && bItem.getType() == Material.SPAWNER) {
            return SlimefunUtils.isItemSimilar(aItem, bItem, true, true, true);
        } else {
            return SlimefunUtils.isItemSimilar(aItem, bItem, false, true, true);
        }
    }

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
     * Find a {@link RandomRecipe} in all the recipes, with the given {@link ItemStack} input. If such recipe is
     * found, append output to it, then return true. Otherwise, nothing happens and return false.
     *
     * @param recipes
     *     The {@link List} of {@link IRecipe}.
     * @param input
     *     The input {@link ItemStack} which will be used to find.
     * @param output
     *     The {@link List} of output {@link ItemStack}s.
     *
     * @return Whether there is an existing {@link RandomRecipe}
     */
    private static boolean appendRandomRecipe(List<IRecipe> recipes, ItemStack input, List<ItemStack> output) {
        for (IRecipe recipe : recipes) {
            if (recipe instanceof RandomRecipe randomRecipe &&
                SlimefunUtils.isItemSimilar(input, randomRecipe.getRawInput(), false, true, true)
            ) {
                FastMachines.debug("existing random recipe found: {0}, adding more items: {1}", randomRecipe,
                    output);
                randomRecipe.addOutput(output);
                return true;
            }
        }
        return false;
    }

    /**
     * Register recipes.
     *
     * @param recipes
     *     The {@link List} that stores {@link IRecipe}s.
     * @param pendingRecipes
     *     The {@link List} of recipes that need to be registered. The first element in pair is input, and the second
     *     element in pair is output.
     */
    public static void registerRecipes(List<IRecipe> recipes, List<RawRecipe> pendingRecipes) {
        ItemStack[] lastInput = new ItemStack[0];
        List<ItemStack> storedOutput = new ArrayList<>();

        // put recipes that with same input together
        int tries = 0;
        while (tries < 3) {
            try {
                pendingRecipes.sort((a, b) -> {
                    int result = Arrays.compare(a.input(), b.input(), ITEM_COMPARATOR);
                    if (result != 0) return result;
                    return Arrays.compare(a.output(), b.output(), ITEM_COMPARATOR);
                });
                break;
            } catch (IllegalArgumentException e) {
                tries++;
            }
        }

        FastMachines.debug("raw recipes: {0}", pendingRecipes);

        // shut up, sonar
        for (var recipe : pendingRecipes) {
            ItemStack[] input = recipe.input();
            ItemStack[] output = recipe.output();

            FastMachines.debug("--------------------");

            if (input == null || output == null) {
                FastMachines.log(Level.WARNING, "Unexpected empty input/output");
                continue;
            }

            if (output.length != 1) {
                FastMachines.log(Level.WARNING, "Unexpected multiple output items from recipe, ignoring. input: {0}, " +
                    "output: {1}", input, output);
                continue;
            }

            if (SlimefunItemUtils.isDisabled(output[0])) {
                continue;
            }

            FastMachines.debug("processing raw recipe: input={0}, output={1}",
                Arrays.toString(input), Arrays.toString(output));

            if (Arrays.equals(input, lastInput, ITEM_COMPARATOR)) {
                // this recipe still belongs to a random recipe, add current output to stored items
                FastMachines.debug("input matches last recipe, adding to stored output");
                storedOutput.add(output[0]);
            } else if (storedOutput.isEmpty()) {
                // initial state, store it
                FastMachines.debug("empty output, storing current recipe");
                lastInput = input;
                storedOutput.add(output[0]);
            } else {
                // current input is different with stored input
                registerRecipeHelper(recipes, lastInput, storedOutput);

                // clear store, put current one in
                lastInput = input;
                storedOutput.clear();
                storedOutput.add(output[0]);
            }
        }

        // the last recipe is not processed yet.
        registerRecipeHelper(recipes, lastInput, storedOutput);
    }

    private static void registerRecipeHelper(List<IRecipe> recipes, ItemStack[] lastInput, List<ItemStack> storedOutput) {
        if (!appendRandomRecipe(recipes, lastInput[0], storedOutput)) {
            IRecipe iRecipe;
            if (storedOutput.size() > 1) {
                iRecipe = new RandomRecipe(lastInput[0], storedOutput);
                FastMachines.debug("registering random recipe: {0}", iRecipe);
            } else {
                iRecipe = new StandardRecipe(storedOutput.get(0), lastInput);
                FastMachines.debug("registering standard recipe: {0}", iRecipe);
            }
            recipes.add(iRecipe);
        }
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
        FastMachines.debug("Expected total recipes: {0}", recipeList.size() / 2);

        List<RawRecipe> pendingRecipes = new ArrayList<>();

        for (int i = 0; i < recipeList.size(); i += 2) {
            ItemStack[] input = recipeList.get(i);
            ItemStack[] output = recipeList.get(i + 1);
            pendingRecipes.add(new RawRecipe(input, output));
        }
        registerRecipes(recipes, pendingRecipes);
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

            List<RawRecipe> pendingRecipes = new ArrayList<>();

            for (MachineRecipe machineRecipe : machineRecipes) {
                ItemStack[] input = machineRecipe.getInput();
                ItemStack[] output = machineRecipe.getOutput();
                if (output.length > 0) {
                    pendingRecipes.add(new RawRecipe(input, output));
                }
            }
            registerRecipes(recipes, pendingRecipes);
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

        FastMachines.debug("Registering recipes from display recipes: {0}", id);

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

        FastMachines.debug("Expected total recipes: {0}", displayRecipes.size() / 2);

        List<RawRecipe> pendingRecipes = new ArrayList<>();

        for (int i = 0; i < displayRecipes.size(); i += 2) {
            ItemStack[] input = new ItemStack[] { displayRecipes.get(i) };
            ItemStack[] output = new ItemStack[] { displayRecipes.get(i + 1) };

            pendingRecipes.add(new RawRecipe(input, output));
        }
        registerRecipes(recipes, pendingRecipes);
    }

    public static void registerVanillaRecipes(List<IRecipe> recipes, Class<? extends Recipe> recipeClass) {
        Iterator<Recipe> iterator = FastMachines.getInstance().getServer().recipeIterator();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipeClass.isInstance(recipe)) {
                registerVanillaRecipe(recipes, recipe);
            }
        }
    }

    private static <T extends Recipe> void registerVanillaRecipe(List<IRecipe> recipes, T recipe) {
        IRecipe iRecipe = null;
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            List<ItemStack> ingredientList = new ArrayList<>();
            var ingredients = shapedRecipe.getIngredientMap();

            StringBuilder shapeR = new StringBuilder();
            for (var shapeLine : shapedRecipe.getShape()) {
                shapeR.append(shapeLine.replaceAll(" ", ""));
            }

            var shape = shapeR.toString().toCharArray();
            for (var i : shape) {
                ingredientList.add(ingredients.get(i));
            }

            iRecipe = new StandardRecipe(shapedRecipe.getResult(), ingredientList);
            FastMachines.debug("registering standard recipe: {0}", iRecipe);
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
            iRecipe = new StandardRecipe(shapelessRecipe.getResult(), shapelessRecipe.getIngredientList());
            FastMachines.debug("registering standard recipe: {0}", iRecipe);
        } else if (recipe instanceof CookingRecipe cookingRecipe) {
            iRecipe = new StandardRecipe(cookingRecipe.getResult(), cookingRecipe.getInput());
            FastMachines.debug("registering standard recipe: {0}", iRecipe);
        }

        if (iRecipe != null) {
            recipes.add(iRecipe);
        }
    }
}
