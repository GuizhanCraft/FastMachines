package net.guizhanss.fastmachines.core.services;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.setup.Items;
import net.guizhanss.fastmachines.setup.Researches;

public final class IntegrationService {
    private final FastMachines plugin;

    public IntegrationService(FastMachines plugin) {
        this.plugin = plugin;

        if (isEnabled("InfinityExpansion")) {
            Items.setupIE(plugin);
            Researches.setupIE();
        }

        if (isEnabled("SlimeFrame")) {
            Items.setupSFrame(plugin);
            Researches.setupSFrame();
        }
    }

    private boolean isEnabled(String pluginName) {
        return plugin.getServer().getPluginManager().isPluginEnabled(pluginName);
    }
}
