package net.guizhanss.fastmachines.items.machines.slimefun;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.machines.generic.BasicFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastArmorForge extends BasicFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem("CRAFT", Material.ANVIL);

    public FastArmorForge(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    public void registerRecipes() {
        RecipeUtils.registerMultiblockMachineRecipes(recipes, SlimefunItems.ARMOR_FORGE.getItemId(), false);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
