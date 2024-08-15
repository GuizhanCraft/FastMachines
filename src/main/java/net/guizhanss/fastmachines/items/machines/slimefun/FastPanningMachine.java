package net.guizhanss.fastmachines.items.machines.slimefun;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.machines.generic.BasicFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastPanningMachine extends BasicFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem("CRAFT", Material.CAULDRON);

    public FastPanningMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    public void registerRecipes() {
        RecipeUtils.registerDisplayRecipes(recipes, SlimefunItems.AUTOMATED_PANNING_MACHINE.getItemId(), true);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
