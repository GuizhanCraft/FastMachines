package net.guizhanss.fastmachines.core.items.groups

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HiddenItemGroup(
    key: NamespacedKey,
    item: ItemStack,
    tier: Int = 3,
) : ItemGroup(key, item, tier) {

    override fun isAccessible(p: Player) = false

    override fun isVisible(p: Player) = false
}
