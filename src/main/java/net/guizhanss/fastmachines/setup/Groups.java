package net.guizhanss.fastmachines.setup;

import org.bukkit.Material;

import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.utils.Heads;
import net.guizhanss.fastmachines.utils.Keys;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Groups {

    public static final NestedItemGroup MAIN = new NestedItemGroup(
        Keys.get("fast_machines"),
        FastMachines.getLocalization().getItem(
            "FAST_MACHINES",
            Heads.MAIN.getTexture()
        )
    );

    public static final SubItemGroup MATERIALS = new SubItemGroup(
        Keys.get("materials"),
        MAIN,
        FastMachines.getLocalization().getItem(
            "MATERIALS",
            Material.DIAMOND
        )
    );

    public static final SubItemGroup MACHINES = new SubItemGroup(
        Keys.get("machines"),
        MAIN,
        FastMachines.getLocalization().getItem(
            "MACHINES",
            Heads.MAIN.getTexture()
        )
    );
}
