package net.guizhanss.fastmachines.items.machines.infinityexpansion;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastMobDataInfuser extends AbstractFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.LODESTONE);

    private final IntRangeSetting energyPerUse = new IntRangeSetting(this, "energy-per-use", 0, 20_000,
        Integer.MAX_VALUE);
    private final IntRangeSetting energyCapacity = new IntRangeSetting(this, "energy-capacity", 0, 200_000,
        Integer.MAX_VALUE);

    public FastMobDataInfuser(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);

        addItemSetting(energyPerUse, energyCapacity);
    }

    @Override
    public void registerRecipes() {
        List<RawRecipe> rawRecipes = RecipeUtils.getInfinityMachineRecipes(MobDataInfuser.class);
        RecipeUtils.registerRecipes(recipes, rawRecipes, false);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }

    @Override
    public int getEnergyPerUse() {
        return FastMachines.getConfigService().isFastMachinesUseEnergy() ?
            energyPerUse.getValue() : 0;
    }

    @Override
    public int getCapacity() {
        return FastMachines.getConfigService().isFastMachinesUseEnergy() ?
            energyCapacity.getValue() : 0;
    }
}
