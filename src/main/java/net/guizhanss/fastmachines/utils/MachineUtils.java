package net.guizhanss.fastmachines.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import net.guizhanss.guizhanlib.minecraft.utils.InventoryUtil;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MachineUtils {

    /**
     * Get a map that contains the amount of each {@link ItemStack} in machine slots.
     * The key is the {@link ItemStack} with amount of 1, and the value is the amount of the {@link ItemStack}.
     *
     * @param menu  The {@link BlockMenu} of machine.
     * @param slots The slots of machine input.
     * @return The amount map of machine inputs.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static Map<ItemStack, Integer> countItems(BlockMenu menu, int[] slots) {
        return RecipeUtils.countItems(getItems(menu, slots));
    }

    /**
     * Calculate the checksum of given item map.
     *
     * @param map The item map to calculate checksum.
     * @return The checksum of given item map.
     */
    @ParametersAreNonnullByDefault
    public static int checksum(Map<ItemStack, Integer> map) {
        int checksum = 0;
        for (var entry : map.entrySet()) {
            checksum ^= entry.getKey().hashCode() * entry.getValue();
        }
        return checksum;
    }

    /**
     * Retrieve all the {@link ItemStack} inside given machine slots.
     *
     * @param menu  The {@link BlockMenu} of machine.
     * @param slots The slots of machine.
     * @return The array of {@link ItemStack} inside given machine slots.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static ItemStack[] getItems(BlockMenu menu, int[] slots) {
        ItemStack[] items = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            items[i] = menu.getItemInSlot(slots[i]);

            // convert to SlimefunItemStack if possible
            SlimefunItem sfItem = SlimefunItem.getByItem(items[i]);
            if (sfItem != null && SlimefunUtils.isItemSimilar(items[i], sfItem.getItem(), false, false, true)) {
                items[i] = new SlimefunItemStack((SlimefunItemStack) sfItem.getItem(), items[i].getAmount());
            }
        }
        return items;
    }

    /**
     * Get the amount of given {@link ItemStack} in machine slots.
     *
     * @param menu  The {@link BlockMenu} of machine.
     * @param slots The slots of machine to scan.
     * @param item  The {@link ItemStack} to scan.
     * @return A pair, where the first element is the slots that contains of given {@link ItemStack},
     * and the second element is the total amount of given {@link ItemStack}.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static Pair<List<Integer>, Integer> countItem(BlockMenu menu, int[] slots, ItemStack item) {
        int amount = 0;
        List<Integer> slotList = new ArrayList<>();
        for (int slot : slots) {
            ItemStack slotItem = menu.getItemInSlot(slot);
            if (slotItem == null || slotItem.getType().isAir()) {
                continue;
            }
            if (SlimefunUtils.isItemSimilar(slotItem, item, false, false, true)) {
                amount += slotItem.getAmount();
                slotList.add(slot);
            }
        }
        return new Pair<>(slotList, amount);
    }

    /**
     * Remove the given amount of {@link ItemStack} from machine slots.
     *
     * @param menu   The {@link BlockMenu} of machine.
     * @param slots  The slots of machine to remove {@link ItemStack} from.
     * @param item   The {@link ItemStack} to remove.
     * @param amount The amount of {@link ItemStack} to remove.
     * @return Whether the item is removed.
     */
    public static boolean removeItem(BlockMenu menu, int[] slots, ItemStack item, int amount) {
        for (int slot : slots) {
            ItemStack slotItem = menu.getItemInSlot(slot);
            if (slotItem == null || slotItem.getType().isAir()) {
                continue;
            }
            if (SlimefunUtils.isItemSimilar(slotItem, item, false, false, true)) {
                if (slotItem.getAmount() > amount) {
                    slotItem.setAmount(slotItem.getAmount() - amount);
                    return true;
                } else if (slotItem.getAmount() == amount) {
                    menu.replaceExistingItem(slot, null);
                    return true;
                } else {
                    amount -= slotItem.getAmount();
                    menu.replaceExistingItem(slot, null);
                }
            }
        }
        return false;
    }

    /**
     * Add the given {@link ItemStack} to machine slots.
     *
     * @param menu   The {@link BlockMenu} of machine.
     * @param slots  The slots of machine to add {@link ItemStack} to.
     * @param item   The {@link ItemStack} to add.
     * @param amount The amount of {@link ItemStack}s to add.
     * @return The remaining amount of {@link ItemStack}s.
     */
    @ParametersAreNonnullByDefault
    public static int addItem(BlockMenu menu, int[] slots, ItemStack item, int amount) {
        int requiredAmount = item.getAmount() * amount;
        for (int slot : slots) {
            if (requiredAmount <= 0) {
                break;
            }
            ItemStack slotItem = menu.getItemInSlot(slot);
            ItemStack itemCopy = item.clone();

            if (slotItem == null || slotItem.getType().isAir()) {
                // empty slot
                int itemAmount = Math.min(requiredAmount, item.getMaxStackSize());
                itemCopy.setAmount(itemAmount);
                menu.replaceExistingItem(slot, itemCopy);
                requiredAmount -= itemAmount;
            } else if (SlimefunUtils.isItemSimilar(slotItem, item, false, false, true)) {
                // match item
                int itemAmount = Math.min(requiredAmount, item.getMaxStackSize() - slotItem.getAmount());
                slotItem.setAmount(slotItem.getAmount() + itemAmount);
                requiredAmount -= itemAmount;
            }
        }
        return requiredAmount;
    }

    /**
     * Add the given {@link ItemStack} to machine slots.
     * If machine slots are all full, send to {@link Player}'s inventory.
     * If player's inventory is full, drop on the ground.
     *
     * @param p      The {@link Player} to send items to.
     * @param menu   The {@link BlockMenu} of machine.
     * @param slots  The slots of machine to add {@link ItemStack} to.
     * @param item   The {@link ItemStack} to add.
     * @param amount The amount of {@link ItemStack}s to add.
     * @return Whether there are remaining items after pushing to machine slots.
     */
    @ParametersAreNonnullByDefault
    public static boolean addItem(Player p, BlockMenu menu, int[] slots, ItemStack item, int amount) {
        int remaining = addItem(menu, slots, item, amount);
        if (remaining > 0) {
            // push the remaining to player inventory
            int stacks = remaining / item.getMaxStackSize();
            int reminder = remaining % item.getMaxStackSize();
            ItemStack[] items = new ItemStack[stacks + (reminder > 0 ? 1 : 0)];
            for (int i = 0; i < stacks; i++) {
                items[i] = item.clone();
                items[i].setAmount(item.getMaxStackSize());
            }
            if (reminder > 0) {
                items[stacks] = item.clone();
                items[stacks].setAmount(reminder);
            }
            InventoryUtil.push(p, items);
            return true;
        } else {
            return false;
        }
    }
}
