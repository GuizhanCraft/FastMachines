package net.guizhanss.fastmachines.core.recipes.loaders

import io.github.mooy1.infinityexpansion.infinitylib.machines.MachineRecipeType
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import net.guizhanss.fastmachines.utils.items.countItems
import java.util.logging.Level

/**
 * A [RecipeLoader] that loads recipes from an InfinityExpansion item.
 * The item must have a static `MachineRecipeType` field `TYPE`.
 */
class InfinityExpansionRecipeLoader(
    machine: BaseFastMachine,
    private val clazz: Class<out SlimefunItem>,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val type: MachineRecipeType = try {
            val field = clazz.getDeclaredField("TYPE")
            field.setAccessible(true)

            field.get(null) as MachineRecipeType
        } catch (e: Exception) {
            FastMachines.log(Level.SEVERE, e, "An error occurred while loading InfinityExpansion recipes.")
            return
        }

        for ((input, output) in type.recipes()) {
            rawRecipes.add(
                RawRecipe(
                    input.toList().countItems().map { (item, amount) -> ExactChoice(item, amount) },
                    listOf(output)
                )
            )
        }
    }

}
