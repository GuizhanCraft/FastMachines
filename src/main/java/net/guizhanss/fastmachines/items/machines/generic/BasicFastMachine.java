package net.guizhanss.fastmachines.items.machines.generic;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

/**
 * The basic fast machine.
 * The default energy per use is 8 and the default capacity is 1024.
 *
 * @author ybw0014
 */
public abstract class BasicFastMachine extends AbstractFastMachine {

    @ParametersAreNonnullByDefault
    protected BasicFastMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe, 8, 1024);
    }
}
