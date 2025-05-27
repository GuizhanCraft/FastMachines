package net.guizhanss.fastmachines.implementation.setup

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.researches.Research
import net.guizhanss.fastmachines.implementation.items.FMItems
import net.guizhanss.fastmachines.utils.constants.Keys
import net.guizhanss.guizhanlib.kt.slimefun.items.toItem

object ResearchSetup {

    val MATERIALS = Research(
        Keys.MATERIALS,
        1145141,
        "Fast Machine Materials",
        4
    )

    val MACHINES = Research(
        Keys.MACHINES,
        1145142,
        "Fast Machines",
        40
    )

    init {
        MATERIALS.addItems(
            FMItems.ETERNAL_FIRE,
            FMItems.FAST_CORE,
            FMItems.STACKED_ANCIENT_PEDESTAL,
        ).register()

        MACHINES.addItems(
            // vanilla
            FMItems.FAST_CRAFTING_TABLE,
            FMItems.FAST_FURNACE,
            // slimefun
            FMItems.FAST_ENHANCED_CRAFTING_TABLE,
            FMItems.FAST_GRIND_STONE,
            FMItems.FAST_ARMOR_FORGE,
            FMItems.FAST_ORE_CRUSHER,
            FMItems.FAST_COMPRESSOR,
            FMItems.FAST_SMELTERY,
            FMItems.FAST_PRESSURE_CHAMBER,
            FMItems.FAST_MAGIC_WORKBENCH,
            FMItems.FAST_ORE_WASHER,
            FMItems.FAST_TABLE_SAW,
            FMItems.FAST_COMPOSTER,
            FMItems.FAST_PANNING_MACHINE,
            FMItems.FAST_JUICER,
            FMItems.FAST_ANCIENT_ALTAR,
            // infinity expansion
            FMItems.FAST_INFINITY_WORKBENCH,
            FMItems.FAST_MOB_DATA_INFUSER,
            // slimeframe
            FMItems.FAST_SLIMEFRAME_FOUNDRY,
            // infinity expansion 2
            FMItems.FAST_INFINITY_WORKBENCH_2,
            FMItems.FAST_MOB_DATA_INFUSER_2,
        ).register()
    }

    private fun Research.addItems(vararg items: SlimefunItemStack): Research {
        items.forEach { item ->
            addItems(item.toItem())
        }
        return this
    }
}
