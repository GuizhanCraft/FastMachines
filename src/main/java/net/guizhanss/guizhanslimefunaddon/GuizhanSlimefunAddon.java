package net.guizhanss.guizhanslimefunaddon;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;

import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon;
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater;

import org.bstats.bukkit.Metrics;

public final class GuizhanSlimefunAddon extends AbstractAddon {

    public GuizhanSlimefunAddon() {
        super("ybw0014", "GuizhanSlimefunAddon", "master", "auto-update");
    }

    @Override
    public void enable() {
        log(Level.INFO, "====================");
        log(Level.INFO, "GuizhanSlimefunAddon");
        log(Level.INFO, "     by ybw0014     ");
        log(Level.INFO, "====================");
    }

    @Override
    public void disable() {
    }

    private void setupMetrics() {
        new Metrics(this, 114514);
    }

    @Override
    protected void autoUpdate() {
        if (getPluginVersion().startsWith("DEV")) {
            String path = getGithubUser() + "/" + getGithubRepo() + "/" + getGithubBranch();
            new GitHubBuildsUpdater(this, getFile(), path).start();
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
