package net.guizhanss.fastmachines.core.recipes;

import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine;

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

    /**
     * Get the output for the given world. Can use `isDisabledIn` check.
     *
     * @param world The {@link World}.
     * @return The output in the given world.
     */
    @Nonnull
    ItemStack getOutput(@Nonnull World world);

    /**
     * Check if every output is disabled in the given world.
     *
     * @param world The {@link World}.
     * @return True if every output is disabled in the given world.
     */
    boolean isDisabledInWorld(@Nonnull World world);

    /**
     * Get the ingredients.
     *
     * @return A map of ingredients. The key {@link ItemStack} should always have the amount of 1, and the value {@link Integer} should be the amount of the ingredient.
     */
    @Nonnull
    Map<ItemStack, Integer> getInput();
}
