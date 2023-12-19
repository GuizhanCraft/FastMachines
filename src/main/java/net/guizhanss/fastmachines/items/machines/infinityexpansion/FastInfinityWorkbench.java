package net.guizhanss.fastmachines.items.machines.infinityexpansion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.blocks.InfinityWorkbench;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.abstracts.AInfinityMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastInfinityWorkbench extends AInfinityMachine {
    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.RESPAWN_ANCHOR);

    public FastInfinityWorkbench(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    protected void registerRecipes() {
        FastMachines.debug("Registering recipes for {0}", getClass().getSimpleName());
        try {
            var recipeType = InfinityWorkbench.TYPE;
            List<RawRecipe> rawRecipes = new ArrayList<>();
            for (var recipe : recipeType.recipes().entrySet()) {
                RawRecipe rawRecipe = new RawRecipe(recipe.getKey(), new ItemStack[] { recipe.getValue() });
                rawRecipes.add(rawRecipe);
            }
            RecipeUtils.registerRecipes(recipes, rawRecipes);
        } catch (Exception ex) {
            FastMachines.log(Level.SEVERE, ex, "An error has occurred while registering recipes for {0}", getClass().getSimpleName());
        }
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
