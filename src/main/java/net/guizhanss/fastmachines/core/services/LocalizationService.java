package net.guizhanss.fastmachines.core.services;

import java.io.File;
import java.text.MessageFormat;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import org.bukkit.command.CommandSender;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.utils.FileUtils;
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil;
import net.guizhanss.guizhanlib.slimefun.addon.SlimefunLocalization;

@SuppressWarnings("ConstantConditions")
public final class LocalizationService extends SlimefunLocalization {
    private static final String FOLDER_NAME = "lang";
    private final FastMachines plugin;
    private final File jarFile;

    @ParametersAreNonnullByDefault
    public LocalizationService(FastMachines plugin, File jarFile) {
        super(plugin);

        this.plugin = plugin;
        this.jarFile = jarFile;
        extractTranslations();
    }

    private void extractTranslations() {
        final File translationsFolder = new File(plugin.getDataFolder(), FOLDER_NAME);
        if (!translationsFolder.exists()) {
            translationsFolder.mkdirs();
        }
        var translationFiles = FileUtils.listYmlFilesInJar(jarFile, FOLDER_NAME);
        for (String translationFile : translationFiles) {
            String filePath = FOLDER_NAME + File.separator + translationFile;
            File file = new File(plugin.getDataFolder(), filePath);
            if (file.exists()) {
                continue;
            }
            plugin.saveResource(filePath, true);
        }
    }

    @ParametersAreNonnullByDefault
    public void sendMessage(CommandSender sender, String messageKey, Object... args) {
        Preconditions.checkArgument(sender != null, "CommandSender cannot be null");
        Preconditions.checkArgument(messageKey != null, "Message key cannot be null");

        if (FastMachines.getIntegrationService().isSlimefunTranslationEnabled()) {
            FastMachines.getIntegrationService().sendMessage(sender, messageKey, args);
        } else {
            ChatUtil.send(sender, MessageFormat.format(getString("messages." + messageKey), args));
        }
    }
}
