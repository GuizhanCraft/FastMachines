package net.guizhanss.fastmachines.core.services;

import javax.annotation.Nonnull;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig;

import lombok.Getter;

public final class ConfigurationService {

    private final AddonConfig config;

    @Getter
    private boolean autoUpdate;
    @Getter
    private String lang;
    @Getter
    private boolean enableResearches;
    @Getter
    private boolean fastMachinesUseEnergy;
    @Getter
    private boolean debugEnabled;

    public ConfigurationService(@Nonnull FastMachines plugin) {
        config = new AddonConfig(plugin, "config.yml");
        reload();
    }

    public void reload() {
        autoUpdate = config.getBoolean("auto-update", true);
        lang = config.getString("lang", FastMachines.DEFAULT_LANG);
        enableResearches = config.getBoolean("enable-researches", true);
        fastMachinesUseEnergy = config.getBoolean("fast-machines.use-energy", true);
        debugEnabled = config.getBoolean("debug", false);
    }
}
