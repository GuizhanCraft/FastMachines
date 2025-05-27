package net.guizhanss.fastmachines.core.items

import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import net.guizhanss.guizhanlib.kt.slimefun.extensions.getSlimefunItem
import net.guizhanss.guizhanlib.kt.slimefun.extensions.isSlimefunItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * A wrapper for [ItemStack] that only focus on the item type and meta.
 */
@ConsistentCopyVisibility
data class ItemWrapper private constructor(
    val baseItem: ItemStack
) : Comparable<ItemWrapper> {

    companion object {

        /**
         * Creates a new [ItemWrapper] with the given [ItemStack].
         */
        fun of(item: ItemStack): ItemWrapper {
            val baseItem = if (item is ItemStackWrapper) {
                // Slimefun's ItemStackWrapper, construct a new ItemStack
                ItemStack(item.type).apply {
                    if (item.hasItemMeta()) {
                        itemMeta = item.itemMeta
                    }
                }
            } else {
                item.clone().apply { amount = 1 }
            }

            return ItemWrapper(baseItem)
        }

        /**
         * Creates a new [ItemWrapper] with the given [Material].
         */
        fun of(material: Material): ItemWrapper {
            val baseItem = ItemStack(material)

            return ItemWrapper(baseItem)
        }

        private fun ItemStack.calcHash(): Int {
            var result = type.hashCode()
            result = 31 * result + (if (hasItemMeta()) itemMeta.hashCode() else 0)
            return result
        }
    }

    // cache ItemMeta and hashCode
    val baseItemMeta = if (baseItem.hasItemMeta()) baseItem.itemMeta else null
    private val itemHash = baseItem.calcHash()

    override fun equals(other: Any?) = other is ItemWrapper && isSimilarTo(other.baseItem)

    override fun hashCode() = itemHash

    override fun compareTo(other: ItemWrapper): Int {
        // quick compare hash code, items with different hash code are likely different
        if (itemHash != other.itemHash) return itemHash - other.itemHash

        // same hash and amount, compare item
        if (!isSimilarTo(other.baseItem)) return baseItem.hashCode() - other.baseItem.hashCode()

        // should be same
        return 0
    }

    override fun toString() = if (baseItem.isSlimefunItem()) {
        "ItemWrapper(slimefunId=${baseItem.getSlimefunItem().id})"
    } else {
        "ItemWrapper(type=${baseItem.type}${if (baseItemMeta != null) ", meta=${baseItemMeta}" else ""})"
    }

}
