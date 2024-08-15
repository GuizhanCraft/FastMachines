package net.guizhanss.fastmachines.items.machines.slimefun;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AncientAltar;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.generic.BasicFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastAncientAltar extends BasicFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem("CRAFT", Material.ENCHANTING_TABLE);

    public FastAncientAltar(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    public void registerRecipes() {
        var altar = (AncientAltar) SlimefunItems.ANCIENT_ALTAR.getItem();
        List<RawRecipe> rawRecipes = new ArrayList<>();
        for (var recipe : altar.getRecipes()) {
            if (recipe.getOutput().getType() == Material.SPAWNER) {
                continue;
            }
            var input = new ArrayList<>(recipe.getInput());
            input.add(recipe.getCatalyst());
            RawRecipe rawRecipe = new RawRecipe(input.toArray(new ItemStack[9]), new ItemStack[] {recipe.getOutput()});
            rawRecipes.add(rawRecipe);
        }
        RecipeUtils.registerRecipes(recipes, rawRecipes, false);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
