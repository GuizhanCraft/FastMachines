package net.guizhanss.fastmachines.items.materials;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;

import net.guizhanss.fastmachines.setup.Groups;

public class FastMaterial extends UnplaceableBlock {
    @ParametersAreNonnullByDefault
    public FastMaterial(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Groups.MATERIALS, item, recipeType, recipe);
    }
}
