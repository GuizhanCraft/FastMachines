package net.guizhanss.fastmachines.setup;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;

import net.guizhanss.fastmachines.items.FastMachinesItems;
import net.guizhanss.fastmachines.utils.Keys;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Researches {
    public static final Research MATERIALS = new Research(
        Keys.get("materials"),
        Keys.get("materials").hashCode(),
        "Fast Machine Materials",
        4
    );

    public static final Research MACHINES = new Research(
        Keys.get("machines"),
        Keys.get("machines").hashCode(),
        "Fast Machines",
        40
    );

    public static void setup() {
        // <editor-fold desc="Materials">
        MATERIALS.addItems(
            FastMachinesItems.ETERNAL_FIRE,
            FastMachinesItems.FAST_CORE
        );
        // </editor-fold>

        // <editor-fold desc="Machines">
        MACHINES.addItems(
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
            FastMachinesItems.FAST_JUICER,
            FastMachinesItems.FAST_ANCIENT_ALTAR
        );
        // </editor-fold>
    }

    public static void setupIE() {
        MACHINES.addItems(
            FastMachinesItems.FAST_INFINITY_WORKBENCH
        );
    }

    public static void setupSFrame() {
        MACHINES.addItems(
            FastMachinesItems.FAST_SLIMEFRAME_FOUNDRY
        );
    }

    public static void register() {
        MACHINES.register();
        MATERIALS.register();
    }
}
