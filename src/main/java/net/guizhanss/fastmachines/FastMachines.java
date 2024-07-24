package net.guizhanss.fastmachines;

import java.util.logging.Level;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.BlobBuildUpdater;

import net.guizhanss.fastmachines.core.Registry;
import net.guizhanss.fastmachines.core.services.ConfigurationService;
import net.guizhanss.fastmachines.core.services.IntegrationService;
import net.guizhanss.fastmachines.core.services.ListenerService;
import net.guizhanss.fastmachines.core.services.LocalizationService;
import net.guizhanss.fastmachines.setup.Items;
import net.guizhanss.fastmachines.setup.Researches;
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon;
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater;

import org.bstats.bukkit.Metrics;

public final class FastMachines extends AbstractAddon {

    public static final String DEFAULT_LANG = "en-US";

    private Registry registry;
    private ConfigurationService configService;
    private LocalizationService localization;
    private IntegrationService integrationService;
    private boolean debugEnabled = false;

    public FastMachines() {
        super("ybw0014", "FastMachines", "master", "auto-update");
    }

    @Nonnull
    public static Registry getRegistry() {
        return inst().registry;
    }

    @Nonnull
    public static ConfigurationService getConfigService() {
        return inst().configService;
    }

    @Nonnull
    public static LocalizationService getLocalization() {
        return inst().localization;
    }

    @Nonnull
    public static IntegrationService getIntegrationService() {
        return inst().integrationService;
    }

    public static void debug(@Nonnull String message, @Nonnull Object... args) {
        Preconditions.checkNotNull(message, "message cannot be null");

        if (inst().debugEnabled) {
            inst().getLogger().log(Level.INFO, "[DEBUG] " + message, args);
        }
    }

    @Nonnull
    private static FastMachines inst() {
        return getInstance();
    }

    @Override
    public void enable() {
        log(Level.INFO, "====================");
        log(Level.INFO, "     FastMachines   ");
        log(Level.INFO, "     by ybw0014     ");
        log(Level.INFO, "====================");

        // check sf version
        if (!checkSlimefunVersion()) return;

        // registry
        registry = new Registry();

        // config
        configService = new ConfigurationService(this);

        // debug
        debugEnabled = configService.isDebugEnabled();

        // localization
        log(Level.INFO, "Loading language...");
        String lang = configService.getLang();
        localization = new LocalizationService(this, getFile());
        try {
            localization.addLanguage(lang);
        } catch (Exception e) {
            log(Level.SEVERE, "An error has occurred while loading language " + lang);
        }
        if (!lang.equals(DEFAULT_LANG)) {
            localization.addLanguage(DEFAULT_LANG);
        }
        localization.setIdPrefix("FM_");
        log(Level.INFO, localization.getString("console.loaded-language"), lang);

        // items
        log(Level.INFO, localization.getString("console.loading-items"));
        Items.setup(this);

        // integrations
        integrationService = new IntegrationService(this);

        // researches
        if (configService.isEnableResearches()) {
            log(Level.INFO, localization.getString("console.loading-researches"));
            Researches.setup();
            Researches.register();
        }

        new ListenerService(this);

        setupMetrics();
    }

    @Override
    public void disable() {
        // nothing to do here for now
    }

    private void setupMetrics() {
        new Metrics(this, 20046);
    }

    @Override
    protected void autoUpdate() {
        if (getPluginVersion().startsWith("Dev")) {
            new BlobBuildUpdater(this, getFile(), getGithubRepo()).start();
        } else if (getPluginVersion().startsWith("Build")) {
            new GuizhanBuildsUpdater(this, getFile(), getGithubUser(), getGithubRepo(), getGithubBranch()).start();
        }
    }

    private boolean checkSlimefunVersion() {
        String sfVersion = getServer().getPluginManager().getPlugin("Slimefun").getDescription().getVersion();
        if (sfVersion.startsWith("DEV - 1104")) {
            for (int i = 0; i < 100; i++) {
                log(Level.SEVERE, "You are using a damn ancient version of Slimefun, update Slimefun first!");
                log(Level.SEVERE, "Download Slimefun from here: https://blob.build/project/Slimefun4/Dev");
                log(Level.SEVERE, "Also join the Slimefun discord so you can get the announcements for new versions and will not be a primitive any more.");
                log(Level.SEVERE, "https://discord.gg/slimefun");
            }
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }
}
