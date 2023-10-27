package net.guizhanss.fastmachines.items.machines;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastGrindStone extends AbstractFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.DISPENSER);

    public FastGrindStone(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    protected void registerRecipes() {
        FastMachines.debug("Registering recipes for {0}", getClass().getSimpleName());
        RecipeUtils.registerMultiblockMachineRecipes(recipes, SlimefunItems.GRIND_STONE.getItemId());
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
