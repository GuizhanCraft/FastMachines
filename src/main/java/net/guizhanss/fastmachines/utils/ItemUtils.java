package net.guizhanss.fastmachines.utils;

import javax.annotation.ParametersAreNullableByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ItemUtils {
    @ParametersAreNullableByDefault
    public static boolean isSimilar(ItemStack aItem, ItemStack bItem) {
        if (aItem == null || bItem == null) {
            return false;
        }
        if (aItem.getType().isAir() || bItem.getType().isAir()) {
            return false;
        }
        if (aItem.getType() != bItem.getType()) {
            return false;
        }
        if (!aItem.hasItemMeta() || !bItem.hasItemMeta()) {
            return aItem.hasItemMeta() == bItem.hasItemMeta();
        }
        boolean checkLore = aItem.getType() == Material.SPAWNER && bItem.getType() == Material.SPAWNER;
        return SlimefunUtils.isItemSimilar(aItem, bItem, checkLore, true, true);
    }
}
