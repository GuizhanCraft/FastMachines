package net.guizhanss.fastmachines.utils.constants

import net.guizhanss.fastmachines.FastMachines
import org.bukkit.NamespacedKey

object Keys {

    val MAIN_GROUP = "fast_machines".createKey()
    val MATERIALS = "materials".createKey()
    val MACHINES = "machines".createKey()
    val HIDDEN = "hidden".createKey()
    val DISPLAY_ITEM = "display_item".createKey()
}

internal fun String.createKey() = NamespacedKey(FastMachines.instance, this)
