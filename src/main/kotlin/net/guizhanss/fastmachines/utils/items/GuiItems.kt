package net.guizhanss.fastmachines.utils.items

import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI
import net.guizhanss.fastmachines.utils.constants.Keys
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

/**
 * Add the PDC flag to the [ItemStack] to indicate it is a display item.
 *
 * To get a copy, use [asDisplayItem] instead.
 */
internal fun ItemStack.asDisplayItem(): ItemStack {
    val meta = itemMeta!!
    PersistentDataAPI.setByte(
        meta,
        Keys.DISPLAY_ITEM,
        Random.nextInt(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt() + 1).toByte()
    )
    itemMeta = meta
    return this
}

/**
 * Get a copy of the original [ItemStack] with display item flag.
 *
 * To perform the operation on the original [ItemStack], use [asDisplayItem] instead.
 */
internal fun ItemStack.toDisplayItem() = clone().asDisplayItem()

/**
 * Remove the PDC flag from the [ItemStack] to indicate it is not a display item.
 *
 * To get a copy, use [removeDisplayItem] instead.
 */
internal fun ItemStack.asNotDisplayItem(): ItemStack {
    val meta = itemMeta!!
    PersistentDataAPI.remove(meta, Keys.DISPLAY_ITEM)
    itemMeta = meta
    return this
}

/**
 * Get a copy of the original [ItemStack] without display item flag.
 *
 * To perform the operation on the original [ItemStack], use [asNotDisplayItem] instead.
 */
internal fun ItemStack.removeDisplayItem() = clone().asNotDisplayItem()

internal fun ItemStack.isDisplayItem() =
    hasItemMeta() && PersistentDataAPI.hasByte(itemMeta!!, Keys.DISPLAY_ITEM)
