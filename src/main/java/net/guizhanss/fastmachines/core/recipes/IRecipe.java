package net.guizhanss.fastmachines.core.recipes;

import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.items.machines.AbstractFastMachine;

/**
 * This interface represents a recipe used by a {@link AbstractFastMachine}.
 */
public interface IRecipe {
    /**
     * Get all the possible outputs.
     *
     * @return All the possible outputs.
     */
    @Nonnull
    ItemStack[] getAllOutputs();

    @Nonnull
    ItemStack getOutput(@Nonnull World world);

    boolean isDisabledInWorld(@Nonnull World world);

    @Nonnull
    Map<ItemStack, Integer> getInput();
}
