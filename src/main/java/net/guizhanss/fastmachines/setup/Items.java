package net.guizhanss.fastmachines.setup;

import net.guizhanss.fastmachines.items.machines.FastArmorForge;
import net.guizhanss.fastmachines.items.machines.FastCompressor;
import net.guizhanss.fastmachines.items.machines.FastEnhancedCraftingTable;
import net.guizhanss.fastmachines.items.machines.FastGrindStone;
import net.guizhanss.fastmachines.items.machines.FastOreCrusher;
import net.guizhanss.fastmachines.items.machines.FastOreWasher;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.FastMachinesItems;
import net.guizhanss.fastmachines.items.machines.FastSmeltery;
import net.guizhanss.fastmachines.items.materials.FastMaterial;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Items {
    public static void setup(FastMachines plugin) {
        // <editor-fold desc="Materials">
        new FastMaterial(FastMachinesItems.ETERNAL_FIRE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL),
            SlimefunItems.IGNITION_CHAMBER, new ItemStack(Material.NETHERRACK), SlimefunItems.IGNITION_CHAMBER
        }).register(plugin);
        new FastMaterial(FastMachinesItems.FAST_CORE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, SlimefunItems.SMALL_CAPACITOR, null,
            null, SlimefunItems.REDSTONE_ALLOY, null,
            null, null, null
        }).register(plugin);
        // </editor-fold>

        // <editor-fold desc="Machines">
        new FastEnhancedCraftingTable(FastMachinesItems.FAST_ENHANCED_CRAFTING_TABLE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.CRAFTING_TABLE), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), FastMachinesItems.FAST_CORE
        }).register(plugin);
        new FastGrindStone(FastMachinesItems.FAST_GRIND_STONE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.OAK_FENCE), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), FastMachinesItems.FAST_CORE
        }).register(plugin);
        new FastArmorForge(FastMachinesItems.FAST_ARMOR_FORGE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.ANVIL), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), FastMachinesItems.FAST_CORE
        }).register(plugin);
        new FastOreCrusher(FastMachinesItems.FAST_ORE_CRUSHER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.IRON_BARS), new ItemStack(Material.DISPENSER), new ItemStack(Material.IRON_BARS),
            SlimefunItems.OUTPUT_CHEST, null, FastMachinesItems.FAST_CORE
        }).register(plugin);
        new FastCompressor(FastMachinesItems.FAST_COMPRESSOR, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.PISTON), new ItemStack(Material.DISPENSER), new ItemStack(Material.PISTON),
            SlimefunItems.OUTPUT_CHEST, null, FastMachinesItems.FAST_CORE
        }).register(plugin);
        new FastSmeltery(FastMachinesItems.FAST_SMELTERY, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.NETHER_BRICKS), new ItemStack(Material.DISPENSER), new ItemStack(Material.NETHER_BRICKS),
            SlimefunItems.OUTPUT_CHEST, FastMachinesItems.ETERNAL_FIRE, FastMachinesItems.FAST_CORE
        }).register(plugin);
//        new FastOreWasher(FastMachinesItems.FAST_ORE_WASHER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
//            null, new ItemStack(Material.GLASS), null,
//            null, new ItemStack(Material.CRAFTING_TABLE), null,
//            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), null
//        }).register(plugin);
        // </editor-fold>
    }
}
