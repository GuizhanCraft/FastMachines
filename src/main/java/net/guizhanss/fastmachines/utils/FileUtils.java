package net.guizhanss.fastmachines.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import net.guizhanss.guizhanlib.utils.FileUtil;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class FileUtils {

    @Nonnull
    @ParametersAreNonnullByDefault
    public static List<String> listYmlFilesInJar(File jarFile, String folderName) {
        Preconditions.checkArgument(jarFile != null, "Jar file cannot be null");
        Preconditions.checkArgument(folderName != null, "Folder name cannot be null");
        try {
            return FileUtil.listJarEntries(
                jarFile,
                (entryName, entry) -> entryName.startsWith(folderName + "/") && !entry.isDirectory() && entryName.endsWith(".yml"),
                (entryName, entry) -> entryName.replace(folderName + "/", "")
            );
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
