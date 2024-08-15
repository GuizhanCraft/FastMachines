package net.guizhanss.fastmachines.listeners;

import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunItemRegistryFinalizedEvent;

import net.guizhanss.fastmachines.FastMachines;

public class SlimefunRegistryLoadedListener implements Listener {

    @ParametersAreNonnullByDefault
    public SlimefunRegistryLoadedListener(@Nonnull FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegistryLoaded(@Nonnull SlimefunItemRegistryFinalizedEvent e) {
        FastMachines.getRegistry().getEnabledFastMachines().forEach(machine -> {
            FastMachines.debug("Registering recipes for {0}", machine.getClass().getSimpleName());
            try {
                machine.registerRecipes();
            } catch (Exception ex) {
                FastMachines.log(Level.SEVERE, ex, "An error has occurred while registering recipes for {0}", machine.getClass().getSimpleName());
            }
        });
    }
}
