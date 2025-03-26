package net.guizhanss.fastmachines.core.recipes.loaders

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import net.guizhanss.fastmachines.utils.items.countItems

/**
 * A [RecipeLoader] that loads recipes from a [MultiBlockMachine].
 */
class SlimefunMultiblockRecipeLoader(
    machine: BaseFastMachine,
    private val id: String,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val mbm = SlimefunItem.getById(id)
        require(mbm is MultiBlockMachine) { "The item $id is not MultiBlockMachine." }

        val recipes = mbm.recipes
        require(recipes.size % 2 == 0) { "The multiblock machine $id has invalid recipe list." }

        for (i in recipes.indices step 2) {
            val input = recipes[i].filterNotNull().countItems().map { (item, amount) -> ExactChoice(item, amount) }
            val output = recipes[i + 1].toList()

            rawRecipes.add(RawRecipe(input, output))
        }
    }

}
