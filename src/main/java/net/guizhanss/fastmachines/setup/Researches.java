package net.guizhanss.fastmachines.setup;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.FastMachinesItems;
import net.guizhanss.fastmachines.utils.Keys;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Researches {
    public static void setup(FastMachines plugin) {
        // <editor-fold desc="Materials">
        Research materialsResearch = new Research(
            Keys.get("materials"),
            Keys.get("materials").hashCode(),
            "Fast Machine Materials",
            4
        );
        materialsResearch.addItems(
            FastMachinesItems.ETERNAL_FIRE,
            FastMachinesItems.FAST_CORE
        );
        materialsResearch.register();
        // </editor-fold>

        // <editor-fold desc="Machines">
        Research machinesResearch = new Research(
            Keys.get("machines"),
            Keys.get("machines").hashCode(),
            "Fast Machines",
            40
        );
        machinesResearch.addItems(
            FastMachinesItems.FAST_CRAFTING_TABLE,
            FastMachinesItems.FAST_FURNACE,
            FastMachinesItems.FAST_ENHANCED_CRAFTING_TABLE,
            FastMachinesItems.FAST_GRIND_STONE,
            FastMachinesItems.FAST_ARMOR_FORGE,
            FastMachinesItems.FAST_ORE_CRUSHER,
            FastMachinesItems.FAST_COMPRESSOR,
            FastMachinesItems.FAST_SMELTERY,
            FastMachinesItems.FAST_PRESSURE_CHAMBER,
            FastMachinesItems.FAST_MAGIC_WORKBENCH,
            FastMachinesItems.FAST_ORE_WASHER,
            FastMachinesItems.FAST_TABLE_SAW,
            FastMachinesItems.FAST_COMPOSTER,
            FastMachinesItems.FAST_PANNING_MACHINE,
            FastMachinesItems.FAST_JUICER
        );
        machinesResearch.register();
        // </editor-fold>
    }
}
