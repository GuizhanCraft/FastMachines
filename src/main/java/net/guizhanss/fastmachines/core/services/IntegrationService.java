package net.guizhanss.fastmachines.core.services;

import java.io.File;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.listeners.TranslationsLoadListener;
import net.guizhanss.fastmachines.setup.Items;
import net.guizhanss.fastmachines.setup.Researches;
import net.guizhanss.slimefuntranslation.api.config.TranslationConfiguration;
import net.guizhanss.slimefuntranslation.api.config.TranslationConfigurationDefaults;
import net.guizhanss.slimefuntranslation.api.config.TranslationConfigurationFields;
import net.guizhanss.slimefuntranslation.core.factories.MessageFactory;
import net.guizhanss.slimefuntranslation.utils.FileUtils;

import lombok.Getter;

public final class IntegrationService {
    private final FastMachines plugin;

    @Getter
    private final boolean slimefunTranslationEnabled;

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

        slimefunTranslationEnabled = isEnabled("SlimefunTranslation");
        if (slimefunTranslationEnabled) {
            new TranslationsLoadListener(plugin);
        }
    }

    private boolean isEnabled(String pluginName) {
        return plugin.getServer().getPluginManager().isPluginEnabled(pluginName);
    }

    public void loadTranslations() {
        var fields = TranslationConfigurationFields.builder().items("items").build();
        var defaults = TranslationConfigurationDefaults.builder().name("FastMachines").prefix("FM_").build();
        List<String> languages = FileUtils.listYamlFiles(new File(plugin.getDataFolder(), "lang"));
        for (String langFile : languages) {
            var file = new File(plugin.getDataFolder(), "lang" + File.separator + langFile);
            String lang = langFile.replace(".yml", "");
            var fileConfig = YamlConfiguration.loadConfiguration(file);
            var cfg = TranslationConfiguration.fromFileConfiguration(lang, fileConfig, fields, defaults);
            cfg.ifPresent(translationConfiguration -> translationConfiguration.register(plugin));
        }
    }

    @ParametersAreNonnullByDefault
    public void sendMessage(CommandSender sender, String messageKey, Object... args) {
        MessageFactory.get(plugin).sendMessage(sender, messageKey, args);
    }
}
