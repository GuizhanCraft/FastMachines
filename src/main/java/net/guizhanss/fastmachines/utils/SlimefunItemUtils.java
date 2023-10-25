package net.guizhanss.fastmachines.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SlimefunItemUtils {
    public static boolean isDisabled(@Nullable ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        return sfItem != null && sfItem.isDisabled();
    }

    public static boolean isDisabled(@Nonnull String id) {
        SlimefunItem sfItem = SlimefunItem.getById(id);
        return sfItem != null && sfItem.isDisabled();
    }
}
