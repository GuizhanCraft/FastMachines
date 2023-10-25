package net.guizhanss.fastmachines.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class BlockStorageUtils {
    @ParametersAreNonnullByDefault
    public static int getInt(Location location, String key) {
        return getInt(location, key, 0);
    }

    @ParametersAreNonnullByDefault
    public static int getInt(Location location, String key, int defaultVal) {
        String result = BlockStorage.getLocationInfo(location, key);
        if (result != null) {
            return Integer.parseInt(result);
        } else {
            return defaultVal;
        }
    }

    @ParametersAreNonnullByDefault
    public static void setInt(Location location, String key, int value) {
        BlockStorage.addBlockInfo(location, key, String.valueOf(value));
    }
}
