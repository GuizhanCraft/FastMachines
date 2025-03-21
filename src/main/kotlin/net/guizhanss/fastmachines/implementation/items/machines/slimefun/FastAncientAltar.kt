package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AncientAltar
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.generic.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastAncientAltar(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = Material.ENCHANTING_TABLE

    override val recipeLoader: RecipeLoader
        get() = object : RecipeLoader(this) {
            override fun beforeLoad() {
                val altar = SlimefunItems.ANCIENT_ALTAR.item as? AncientAltar ?: return

                for (recipe in altar.recipes) {
                    // explicitly ignore spawner recipes
                    if (recipe.output.type === Material.SPAWNER) continue

                    val input = recipe.input.toMutableList()
                    input.add(recipe.catalyst)
                    val rawRecipe = RawRecipe(input.map { ExactChoice(ItemWrapper.of(it)) }, listOf(recipe.output))
                    rawRecipes.add(rawRecipe)
                }
            }
        }
}
