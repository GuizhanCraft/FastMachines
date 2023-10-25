package net.guizhanss.fastmachines.utils;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

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
}
