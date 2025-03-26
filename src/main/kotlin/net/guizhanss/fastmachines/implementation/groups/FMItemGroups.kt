package net.guizhanss.fastmachines.implementation.groups

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.groups.HiddenItemGroup
import net.guizhanss.fastmachines.utils.constants.HeadTexture
import net.guizhanss.fastmachines.utils.constants.Keys
import net.guizhanss.guizhanlib.kt.minecraft.extensions.toItem
import net.guizhanss.guizhanlib.kt.minecraft.items.edit
import net.guizhanss.guizhanlib.kt.slimefun.items.toItem
import org.bukkit.Material

object FMItemGroups {

    val MAIN = NestedItemGroup(
        Keys.MAIN_GROUP,
        FastMachines.localization.getItem(
            "FAST_MACHINES",
            HeadTexture.MAIN.texture
        ).toItem()
    )

    val MATERIALS = SubItemGroup(
        Keys.MATERIALS,
        MAIN,
        FastMachines.localization.getItem(
            "MATERIALS",
            Material.DIAMOND
        ).toItem()
    )

    val MACHINES = SubItemGroup(
        Keys.MACHINES,
        MAIN,
        FastMachines.localization.getItem(
            "MACHINES",
            HeadTexture.MAIN.texture
        ).toItem()
    )

    val HIDDEN = HiddenItemGroup(
        Keys.HIDDEN,
        Material.BARRIER.toItem().edit { name("FM Invalid items") }
    )
}
