package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.utils.items.isDisabledIn
import org.bukkit.World
import org.bukkit.inventory.ItemStack

/**
 * The standard recipe that has fixed inputs and outputs.
 */
data class StandardRecipe(
    override val inputs: List<RecipeChoice>,
    private val output: ItemStack,
) : Recipe {

    override val outputs = listOf(output)

    override fun getOutput(world: World) = output

    override fun isDisabledIn(world: World) = output.isDisabledIn(world)
}
