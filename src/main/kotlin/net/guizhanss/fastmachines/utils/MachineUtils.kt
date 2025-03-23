package net.guizhanss.fastmachines.utils

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.utils.items.countItems
import org.bukkit.inventory.ItemStack

fun BlockMenu.getItems(vararg slots: Int): List<ItemStack> = getItems(slots.toList())

fun BlockMenu.getItems(slots: List<Int>): List<ItemStack> = slots.mapNotNull { getItemInSlot(it) }.toList()

fun BlockMenu.countItems(vararg slots: Int): Map<ItemWrapper, Int> = countItems(slots.toList())

fun BlockMenu.countItems(slots: List<Int>): Map<ItemWrapper, Int> = getItems(slots).countItems()
