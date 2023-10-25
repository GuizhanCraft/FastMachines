package net.guizhanss.fastmachines.items;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

import net.guizhanss.fastmachines.FastMachines;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class FastMachinesItems {
    // <editor-fold desc="Materials">
    public static final SlimefunItemStack ETERNAL_FIRE = FastMachines.getLocalization().getItem(
        "ETERNAL_FIRE",
        Material.IRON_INGOT
    );
    // </editor-fold>

    // <editor-fold desc="Machines">
    public static final SlimefunItemStack FAST_ENHANCED_CRAFTING_TABLE = FastMachines.getLocalization().getItem(
        "FAST_ENHANCED_CRAFTING_TABLE",
        Material.CRAFTING_TABLE
    );
    public static final SlimefunItemStack FAST_SMELTERY = FastMachines.getLocalization().getItem(
        "FAST_SMELTERY",
        Material.FURNACE
    );
    public static final SlimefunItemStack FAST_ORE_WASHER = FastMachines.getLocalization().getItem(
        "FAST_ORE_WASHER",
        Material.CAULDRON
    );
    // </editor-fold>
}
