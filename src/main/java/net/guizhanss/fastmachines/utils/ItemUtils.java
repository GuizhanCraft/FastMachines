package net.guizhanss.fastmachines.utils;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ItemUtils {
    /**
     * Compare two {@link ItemStack}s and check if they are similar.
     *
     * @param aItem The first item to compare
     * @param bItem The second item to compare
     * @return Whether the two items are similar
     */
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

    public static boolean isSimilar(ItemStack targetItem, Collection<ItemStack> sourceItems) {
        for (ItemStack sourceItem : sourceItems) {
            if (isSimilar(targetItem, sourceItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the damage meta from an {@link ItemStack}.
     *
     * @param item The item to remove the damage from
     * @return A cloned item without the damage meta
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static ItemStack removeDamage(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        if (meta instanceof Damageable damageable) {
            damageable.setDamage(0);
            clone.setItemMeta(meta);
        }
        return clone;
    }
}
