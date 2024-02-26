package net.guizhanss.fastmachines.listeners;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine;

public class SlimefunRegistryLoadedListener implements Listener {

    @ParametersAreNonnullByDefault
    public SlimefunRegistryLoadedListener(@Nonnull FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegistryLoaded(@Nonnull SlimefunItemRegistryFinalizedEvent e) {
        FastMachines.getRegistry().getAllEnabledFastMachines().forEach(AbstractFastMachine::registerRecipes);
    }
}
