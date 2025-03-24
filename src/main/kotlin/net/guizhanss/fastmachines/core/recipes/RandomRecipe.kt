package net.guizhanss.fastmachines.core.recipes

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.utils.items.isDisabledIn
import org.bukkit.World
import org.bukkit.inventory.ItemStack

/**
 * A random recipe accepts a single input and returns a random output from the list of outputs.
 */
data class RandomRecipe(
    private val input: RecipeChoice,
    override val outputs: List<ItemStack>,
) : Recipe {

    override val inputs = listOf(input)

    override fun getOutput(world: World) = outputs.filter { !it.isDisabledIn(world) }.random()

    override fun isDisabledIn(world: World) = outputs.all { it.isDisabledIn(world) }
}
