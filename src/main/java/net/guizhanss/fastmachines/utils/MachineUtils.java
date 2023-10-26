package net.guizhanss.fastmachines.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MachineUtils {
    /**
     * Get the amount map of machine inputs.
     *
     * @param menu
     *     The {@link BlockMenu} of machine.
     * @param slots
     *     The slots of machine inputs.
     *
     * @return The amount map of machine inputs.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static Map<ItemStack, Integer> getMachineInputAmount(BlockMenu menu, int[] slots) {
        return RecipeUtils.calculateItems(getItems(menu, slots));
    }

    /**
     * Retrive all the {@link ItemStack} inside given machine slots.
     *
     * @param menu
     *     The {@link BlockMenu} of machine.
     * @param slots
     *     The slots of machine.
     *
     * @return The array of {@link ItemStack} inside given machine slots.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static ItemStack[] getItems(BlockMenu menu, int[] slots) {
        ItemStack[] items = new ItemStack[slots.length];
        for (int i = 0; i < slots.length; i++) {
            items[i] = menu.getItemInSlot(slots[i]);
        }
        return items;
    }

    /**
     * Get the amount of given {@link ItemStack} in machine slots.
     *
     * @param menu
     *     The {@link BlockMenu} of machine.
     * @param slots
     *     The slots of machine to scan.
     * @param item
     *     The {@link ItemStack} to scan.
     *
     * @return A pair, where the first element is the slots that contains of given {@link ItemStack},
     * and the second element is the total amount of given {@link ItemStack}.
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    public static Pair<List<Integer>, Integer> getItemAmount(BlockMenu menu, int[] slots, ItemStack item) {
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
     * @param menu
     *     The {@link BlockMenu} of machine.
     * @param slots
     *     The slots of machine to remove {@link ItemStack} from.
     * @param item
     *     The {@link ItemStack} to remove.
     * @param amount
     *     The amount of {@link ItemStack} to remove.
     *
     * @return Whether the item is removed.
     */
    public static boolean removeItems(BlockMenu menu, int[] slots, ItemStack item, int amount) {
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
     * If the {@link ItemStack} is already in machine slots, the amount will be added.
     *
     * @param menu
     *     The {@link BlockMenu} of machine.
     * @param slots
     *     The slots of machine to add {@link ItemStack} to.
     * @param item
     *     The {@link ItemStack} to add.
     * @param amount
     *     The amount of {@link ItemStack}s to add.
     *
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
}
