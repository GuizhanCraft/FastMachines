package net.guizhanss.fastmachines.listeners;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.slimefuntranslation.api.events.TranslationsLoadEvent;

public class TranslationsLoadListener implements Listener {

    @ParametersAreNonnullByDefault
    public TranslationsLoadListener(@Nonnull FastMachines plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTranslationsLoad(@Nonnull TranslationsLoadEvent e) {
        FastMachines.getScheduler().runAsync(() -> FastMachines.getIntegrationService().loadTranslations());
    }
}
