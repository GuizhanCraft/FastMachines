package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

/**
 * The [RecipeChoice] that matches multiple items.
 */
data class MultipleChoice(
    val items: Map<ItemWrapper, Int>
) : RecipeChoice {

    override fun getChoices() = items

    override fun isValid(item: ItemStack) = items.entries.any { (choiceItem, amount) -> choiceItem.isSimilarTo(item) }
}
