package net.guizhanss.fastmachines.core.recipes;
import net.guizhanss.fastmachines.items.machines.AbstractFastMachine;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * This interface represents a recipe used by a {@link AbstractFastMachine}.
 */
public interface IRecipe {
    /**
     * Get all the possible outputs.
     * @return All the possible outputs.
     */
    ItemStack[] getAllOutputs();

    ItemStack getOutput(World world);

    Map<ItemStack, Integer> getInput();
}
