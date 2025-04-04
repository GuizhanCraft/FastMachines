package net.guizhanss.fastmachines.core

import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import java.util.LinkedList

internal object FMRegistry {

    val enabledFastMachines = LinkedList<BaseFastMachine>()
}
