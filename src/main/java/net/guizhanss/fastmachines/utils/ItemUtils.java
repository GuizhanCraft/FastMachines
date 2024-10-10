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

import net.guizhanss.guizhanlib.minecraft.utils.MinecraftVersionUtil;

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
     * Cleanse the {@link ItemStack}. Usually these items are vanilla recipe ingredients.
     *
     * @param item The item to cleanse
     * @return A cleansed item copy.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static ItemStack cleanse(ItemStack item) {
        ItemStack clone = item.clone();
        if (!clone.hasItemMeta()) {
            return clone;
        }

        ItemMeta meta = clone.getItemMeta();
        // not a plain vanilla item
        if (meta.hasDisplayName() || meta.hasLore() || meta.hasEnchants() || meta.hasAttributeModifiers() || meta.hasCustomModelData()) {
            return clone;
        }

        // remove damage
        if (meta instanceof Damageable damageable) {
            // after 1.20.5, it is possible to have different max damage
            int maxDamage = 32767;
            if (MinecraftVersionUtil.isAtLeast(20, 5) && damageable.hasMaxDamage()) {
                maxDamage = damageable.getMaxDamage();
            }

            if (damageable.getDamage() == 0 || damageable.getDamage() == maxDamage) {
                // just return a clean ItemStack in 1.20.5+
                if (MinecraftVersionUtil.isAtLeast(20, 5)) {
                    return new ItemStack(clone.getType(), clone.getAmount());
                }
                damageable.setDamage(0);
            }
            clone.setItemMeta(meta);
        }
        return clone;
    }
}
