package net.guizhanss.fastmachines.core.recipes.loaders

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem.getById
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import net.guizhanss.fastmachines.utils.items.countItems
import net.guizhanss.infinityexpansion2.implementation.items.machines.abstracts.AbstractEnergyCraftingMachine

/**
 * A [RecipeLoader] that loads recipes from an InfinityExpansion2 item.
 */
class InfinityExpansion2EnergyCrafterRecipeLoader(
    machine: BaseFastMachine,
    private val id: String,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val sfItem = getById(id)
        if (sfItem !is AbstractEnergyCraftingMachine) {
            FastMachines.debug("The item $id is not an AbstractEnergyCraftingMachine.")
            return
        }

        for (recipe in sfItem.recipes) {
            rawRecipes.add(
                RawRecipe(
                    recipe.recipe.toList().countItems().map { (item, amount) -> ExactChoice(item, amount) },
                    listOf(recipe.output)
                )
            )
        }
    }
}
