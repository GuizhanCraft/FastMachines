package net.guizhanss.fastmachines.listeners;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent;

import net.guizhanss.fastmachines.FastMachines;

public class SlimefunRegistryLoadedListener implements Listener {
    private final Runnable runnable;

    @ParametersAreNonnullByDefault
    public SlimefunRegistryLoadedListener(FastMachines plugin, Runnable runnable) {
        this.runnable = runnable;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegistryLoaded(@Nonnull SlimefunItemRegistryFinalizedEvent e) {
        runnable.run();
    }
}
