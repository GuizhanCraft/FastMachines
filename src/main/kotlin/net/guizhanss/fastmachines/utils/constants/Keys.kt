package net.guizhanss.fastmachines.utils.constants

import net.guizhanss.fastmachines.FastMachines
import org.bukkit.NamespacedKey

object Keys {

    val MAIN_GROUP = fmKey("fast_machines")
    val MATERIALS = fmKey("materials")
    val MACHINES = fmKey("machines")
    val HIDDEN = fmKey("hidden")
    val DISPLAY_ITEM = fmKey("display_item")
}

internal fun fmKey(key: String) = NamespacedKey(FastMachines.instance, key)
