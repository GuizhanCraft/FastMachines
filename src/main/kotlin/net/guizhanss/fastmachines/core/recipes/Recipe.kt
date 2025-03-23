package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import org.bukkit.World
import org.bukkit.inventory.ItemStack

/**
 * A shapeless recipe.
 */
interface Recipe {

    /**
     * The recipe inputs.
     */
    val inputs: Map<RecipeChoice, Int>

    /**
     * The recipe outputs.
     */
    val outputs: List<ItemStack>

    /**
     * Get the output [ItemStack] in the given [World].
     */
    fun getOutput(world: World): ItemStack

    /**
     * Check if the all the recipes are fully disabled in the given [World].
     */
    fun isDisabledIn(world: World): Boolean
}
