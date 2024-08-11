package net.guizhanss.fastmachines.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

import lombok.experimental.UtilityClass;

/**
 * Utility class for {@link BlockStorage}.
 * No parameter checks.
 *
 * @author ybw0014
 */
@UtilityClass
public final class BlockStorageUtils {

    @ParametersAreNonnullByDefault
    public static int getInt(Location location, String key) {
        return getInt(location, key, 0);
    }

    @ParametersAreNonnullByDefault
    public static int getInt(BlockPosition pos, String key) {
        return getInt(pos.toLocation(), key, 0);
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
    public static int getInt(BlockPosition pos, String key, int defaultVal) {
        return getInt(pos.toLocation(), key, defaultVal);
    }

    @ParametersAreNonnullByDefault
    public static void setInt(Location location, String key, int value) {
        BlockStorage.addBlockInfo(location, key, String.valueOf(value));
    }

    @ParametersAreNonnullByDefault
    public static void setInt(BlockPosition pos, String key, int value) {
        setInt(pos.toLocation(), key, value);
    }
}
