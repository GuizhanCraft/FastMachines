package net.guizhanss.fastmachines.items.machines.generic;

import javax.annotation.ParametersAreNonnullByDefault;

import net.guizhanss.fastmachines.FastMachines;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

/**
 * General abstract class for Infinity Fast Machines.
 * The default energy per use is 10m and the default capacity is 100m.
 *
 * @author ybw0014
 */
public abstract class AInfinityMachine extends AbstractFastMachine {

    private final IntRangeSetting energyPerUse = new IntRangeSetting(this, "energy-per-use", 0, 10_000_000,
        Integer.MAX_VALUE);
    private final IntRangeSetting energyCapacity = new IntRangeSetting(this, "energy-capacity", 0, 100_000_000,
        Integer.MAX_VALUE);

    @ParametersAreNonnullByDefault
    protected AInfinityMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);

        addItemSetting(energyPerUse, energyCapacity);
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
