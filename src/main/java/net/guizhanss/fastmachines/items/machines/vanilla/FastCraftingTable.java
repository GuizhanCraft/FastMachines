package net.guizhanss.fastmachines.items.machines.vanilla;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.machines.generic.AFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastCraftingTable extends AFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.CRAFTING_TABLE);

    public FastCraftingTable(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    protected void registerRecipes() {
        FastMachines.debug("Registering recipes for {0}", getClass().getSimpleName());
        RecipeUtils.registerVanillaRecipes(recipes, ShapedRecipe.class);
        RecipeUtils.registerVanillaRecipes(recipes, ShapelessRecipe.class);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
