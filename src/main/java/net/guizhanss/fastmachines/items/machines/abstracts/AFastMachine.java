package net.guizhanss.fastmachines.items.machines.abstracts;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

/**
 * General abstract class for Fast Machines.
 * The default energy per use is 8 and the default capacity is 1024.
 *
 * @author ybw0014
 */
public abstract class AFastMachine extends AbstractFastMachine {

    private final IntRangeSetting energyPerUse = new IntRangeSetting(this, "energy-per-use", 0, 8,
        Integer.MAX_VALUE);
    private final IntRangeSetting energyCapacity = new IntRangeSetting(this, "energy-capacity", 0, 1024,
        Integer.MAX_VALUE);

    @ParametersAreNonnullByDefault
    protected AFastMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);

        addItemSetting(energyPerUse, energyCapacity);
    }

    @Override
    public int getEnergyPerUse() {
        return energyPerUse.getValue();
    }

    @Override
    public int getCapacity() {
        return energyCapacity.getValue();
    }
}
