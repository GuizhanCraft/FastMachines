package net.guizhanss.fastmachines;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.BlobBuildUpdater;

import net.guizhanss.fastmachines.core.Registry;
import net.guizhanss.fastmachines.core.services.IntegrationService;
import net.guizhanss.fastmachines.core.services.ListenerService;
import net.guizhanss.fastmachines.core.services.LocalizationService;
import net.guizhanss.fastmachines.setup.Items;
import net.guizhanss.fastmachines.setup.Researches;
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon;
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig;
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater;

import org.bstats.bukkit.Metrics;

public final class FastMachines extends AbstractAddon {

    private static final String DEFAULT_LANG = "en-US";

    private Registry registry;
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

        // config
        AddonConfig config = getAddonConfig();

        // registry
        registry = new Registry();

        // debug
        debugEnabled = config.getBoolean("debug", false);

        // localization
        log(Level.INFO, "Loading language...");
        String lang = config.getString("lang", DEFAULT_LANG);
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
        if (config.getBoolean("enable-researches", true)) {
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
            try {
                // use updater in lib plugin
                Class<?> clazz = Class.forName("net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater");
                Method updaterStart = clazz.getDeclaredMethod("start", Plugin.class, File.class, String.class, String.class, String.class);
                updaterStart.invoke(null, this, getFile(), getGithubUser(), getGithubRepo(), getGithubBranch());
            } catch (Exception ignored) {
                // use updater in lib
                new GuizhanBuildsUpdater(this, getFile(), getGithubUser(), getGithubRepo(), getGithubBranch()).start();
            }
        }
    }
}
