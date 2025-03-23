package net.guizhanss.fastmachines.implementation.items.machines.base

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.Recipe
import net.guizhanss.fastmachines.utils.countItems

class FastMachineCache(
    private val machine: BaseFastMachine,
    private val menu: BlockMenu,
) {

    private val pos = BlockPosition(menu.location)
    private var page = -1
    private var invChecksum = 0
    private var cachedOutputs: Map<Recipe, Int> = emptyMap()

    init {
        menu.addMenuClickHandler(BaseFastMachine.SCROLL_UP_SLOT) { _, _, _, _ ->
            page--
            false
        }
        menu.addMenuClickHandler(BaseFastMachine.SCROLL_DOWN_SLOT) { _, _, _, _ ->
            page++
            false
        }
    }

    fun tick() {
        if (!menu.hasViewer()) return

        if (FastMachines.slimefunTickCount % 2 == 0) {
            cachedOutputs = generateOutputs()
            updateMenu()
        }

        val inputs = menu.countItems(*BaseFastMachine.INPUT_SLOTS)

        // if checksum does not need update
        if (inputs.hashCode() == invChecksum) return
        invChecksum = inputs.hashCode()

        FastMachines.debug("Ticking ${machine.javaClass.simpleName} at $pos")
        FastMachines.debug("Inputs: $inputs")


    }

    private fun generateOutputs(): Map<Recipe, Int> {


        return emptyMap()
    }

    private fun updateMenu() {
        //
    }
}
