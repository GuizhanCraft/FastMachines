package net.guizhanss.fastmachines.core.recipes.loaders

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.generic.AbstractFastMachine

/**
 * A [RecipeLoader] that loads recipes from display recipes.
 */
class SlimefunDisplayRecipeLoader(
    machine: AbstractFastMachine,
    private val id: String,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val sfItem = SlimefunItem.getById(id)
        require(sfItem is RecipeDisplayItem) { "The item $id is not RecipeDisplayItem." }

        val recipes = sfItem.displayRecipes
        require(recipes.size % 2 == 0) { "The item $id has invalid display recipe list." }

        for (i in recipes.indices step 2) {
            val input = listOf(ExactChoice(ItemWrapper.of(recipes[i])))
            val output = listOf(recipes[i + 1])

            rawRecipes.add(RawRecipe(input, output))
        }
    }

}
