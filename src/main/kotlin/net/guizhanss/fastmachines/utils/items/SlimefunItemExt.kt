package net.guizhanss.fastmachines.utils.items

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import net.guizhanss.guizhanlib.kt.slimefun.extensions.getSlimefunItem
import net.guizhanss.guizhanlib.kt.slimefun.extensions.isSlimefunItem
import org.bukkit.World
import org.bukkit.inventory.ItemStack

fun String.getSfItem(): SlimefunItem? =
    SlimefunItem.getById(this.uppercase())

fun ItemStack.isDisabled(): Boolean {
    if (!this.isSlimefunItem()) return false

    val item = this.getSlimefunItem()
    return item.isDisabled
}

fun ItemStack.isDisabledIn(world: World): Boolean {
    if (!this.isSlimefunItem()) return false

    val item = this.getSlimefunItem()
    return item.isDisabledIn(world)
}
