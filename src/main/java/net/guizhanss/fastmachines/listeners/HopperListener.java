package net.guizhanss.fastmachines.listeners;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.items.attributes.NotAHopper;

public class HopperListener implements Listener {

    @ParametersAreNonnullByDefault
    public HopperListener(@Nonnull FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPickupItem(@Nonnull InventoryPickupItemEvent e) {
        if (e.getInventory().getType() != InventoryType.HOPPER) {
            return;
        }

        Location loc = e.getInventory().getLocation();
        if (loc == null) {
            return;
        }

        SlimefunItem sfItem = BlockStorage.check(loc);
        if (sfItem instanceof NotAHopper) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItemIntoHopper(@Nonnull InventoryMoveItemEvent e) {
        Location loc = e.getDestination().getLocation();

        if (loc != null
            && e.getDestination().getType() == InventoryType.HOPPER
            && BlockStorage.check(loc) instanceof NotAHopper
        ) {
            e.setCancelled(true);
        }
    }
}
