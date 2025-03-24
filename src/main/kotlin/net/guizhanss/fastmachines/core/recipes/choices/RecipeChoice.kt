package net.guizhanss.fastmachines.core.recipes.choices

import net.guizhanss.fastmachines.core.items.ItemWrapper
import org.bukkit.inventory.ItemStack

/**
 * A recipe ingredient.
 */
interface RecipeChoice {

    /**
     * Get the choices, with the item [ItemWrapper] and its amount.
     */
    val choices: Map<ItemWrapper, Int>

    /**
     * Only check if the item is valid, without checking the amount.
     */
    fun isValidItem(item: ItemStack): Boolean

    /**
     * Calculate the maximum craftable amount based on the given available items.
     */
    fun maxCraftableAmount(availableItems: Map<ItemWrapper, Int>): Int
}
