package net.guizhanss.fastmachines.items.materials;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.services.sounds.SoundEffect;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;

import net.guizhanss.guizhanlib.minecraft.utils.InventoryUtil;

import org.jetbrains.annotations.NotNull;

public final class StackedAncientPedestal extends FastMaterial {
    @ParametersAreNonnullByDefault
    public StackedAncientPedestal(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();
            var p = e.getPlayer();
            var pedestal = SlimefunItems.ANCIENT_PEDESTAL;
            if (pedestal.getItem().isDisabledIn(p.getWorld())) {
                return;
            }
            ItemUtils.consumeItem(e.getItem(), true);
            InventoryUtil.push(p, new CustomItemStack(pedestal, 4));
            SoundEffect.ANCIENT_ALTAR_START_SOUND.playFor(p);
        };
    }
}
