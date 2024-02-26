package net.guizhanss.fastmachines.items.machines.slimefun;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AncientAltar;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.generic.AFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastAncientAltar extends AFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.ENCHANTING_TABLE);

    public FastAncientAltar(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    public void registerRecipes() {
        FastMachines.debug("Registering recipes for {0}", getClass().getSimpleName());
        try {
            var altar = (AncientAltar) SlimefunItems.ANCIENT_ALTAR.getItem();
            List<RawRecipe> rawRecipes = new ArrayList<>();
            for (var recipe : altar.getRecipes()) {
                RawRecipe rawRecipe = new RawRecipe(recipe.getInput().toArray(new ItemStack[0]), new ItemStack[] {recipe.getOutput()});
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
