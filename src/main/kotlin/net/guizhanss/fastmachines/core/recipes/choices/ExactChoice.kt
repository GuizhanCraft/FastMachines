package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

/**
 * The [RecipeChoice] that matches exactly one item.
 */
data class ExactChoice(
    val item: ItemWrapper,
    val amount: Int = 1,
) : RecipeChoice {

    override val choices = mapOf(item to amount)

    override fun isValidItem(item: ItemStack) = this.item.isSimilarTo(item)

    override fun maxCraftableAmount(availableItems: Map<ItemWrapper, Int>): Int {
        val available = availableItems.getOrDefault(item, 0)
        return available / amount
    }
}
