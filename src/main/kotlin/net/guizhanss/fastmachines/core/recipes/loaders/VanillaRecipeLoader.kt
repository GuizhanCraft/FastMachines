@file:Suppress("UNCHECKED_CAST")

package net.guizhanss.fastmachines.core.recipes.loaders

import net.guizhanss.fastmachines.core.recipes.raw.RawRecipe
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import net.guizhanss.fastmachines.utils.consolidate
import net.guizhanss.fastmachines.utils.items.asFMRecipeChoice
import net.guizhanss.fastmachines.utils.reflections.resultItem
import org.bukkit.Bukkit
import org.bukkit.inventory.CookingRecipe
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.RecipeChoice as BukkitRecipeChoice

/**
 * A [RecipeLoader] that loads recipes from vanilla registry.
 */
class VanillaRecipeLoader<T : Recipe>(
    machine: BaseFastMachine,
    private val recipeClass: Class<T>,
    enableRandomRecipes: Boolean = false,
) : RecipeLoader(machine, enableRandomRecipes) {

    override fun beforeLoad() {
        val iterator = Bukkit.getServer().recipeIterator()
        while (iterator.hasNext()) {
            val recipe = iterator.next()
            if (recipeClass.isInstance(recipe)) {
                registerRecipe(recipe as T)
            }
        }
    }

    private fun registerRecipe(recipe: T) {
        when (recipe) {
            is ShapedRecipe -> {
                // each character is the shape is somehow different,
                // so we need to count the amount of each bukkit recipe choice
                val ingredientMap = mutableMapOf<BukkitRecipeChoice, Int>()
                val shape = recipe.shape.joinToString("").filter { !it.isWhitespace() }.toCharArray()

                shape.forEach { ingredientChar ->
                    val choice = recipe.choiceMap[ingredientChar] ?: return@forEach
                    ingredientMap[choice] = ingredientMap.getOrDefault(choice, 0) + 1
                }

                // now transform bukkit recipe choices to our recipe choices
                val ingredients = ingredientMap.map { (choice, amount) -> choice.asFMRecipeChoice(amount) }

                rawRecipes.add(RawRecipe(ingredients, listOf(recipe.resultItem)))
            }

            is ShapelessRecipe -> {
                val ingredients = recipe.choiceList.map { it.asFMRecipeChoice() }.consolidate()
                rawRecipes.add(RawRecipe(ingredients, listOf(recipe.resultItem)))
            }

            is CookingRecipe<*> -> {
                rawRecipes.add(RawRecipe(listOf(recipe.inputChoice.asFMRecipeChoice()), listOf(recipe.result)))
            }
        }
    }
}
