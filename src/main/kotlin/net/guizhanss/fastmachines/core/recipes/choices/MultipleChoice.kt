package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

/**
 * The [RecipeChoice] that matches multiple items.
 */
data class MultipleChoice(
    override val choices: Map<ItemWrapper, Int>
) : RecipeChoice {

    override fun isValidItem(item: ItemStack) =
        choices.keys.any { it.isSimilarTo(item) }

    override fun maxCraftableAmount(availableItems: Map<ItemWrapper, Int>): Int {
        val items = availableItems.toMutableMap()
        var totalCrafts = 0

        while (true) {
            var matched = false

            for ((item, amount) in choices) {
                val availableAmount = items.getOrDefault(item, 0)
                if (availableAmount >= amount) {
                    items[item] = availableAmount - amount
                    matched = true
                    break
                }
            }

            if (!matched) {
                break
            }

            totalCrafts++
        }

        return totalCrafts
    }
}
