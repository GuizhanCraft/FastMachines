package net.guizhanss.fastmachines.setup;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.items.FastMachinesItems;
import net.guizhanss.fastmachines.items.machines.FastArmorForge;
import net.guizhanss.fastmachines.items.machines.FastComposter;
import net.guizhanss.fastmachines.items.machines.FastCompressor;
import net.guizhanss.fastmachines.items.machines.FastEnhancedCraftingTable;
import net.guizhanss.fastmachines.items.machines.FastGrindStone;
import net.guizhanss.fastmachines.items.machines.FastJuicer;
import net.guizhanss.fastmachines.items.machines.FastMagicWorkbench;
import net.guizhanss.fastmachines.items.machines.FastOreCrusher;
import net.guizhanss.fastmachines.items.machines.FastPressureChamber;
import net.guizhanss.fastmachines.items.machines.FastSmeltery;
import net.guizhanss.fastmachines.items.machines.FastTableSaw;
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
        ItemStack fastCore = FastMachines.getAddonConfig().getBoolean("fast-machines.use-energy", true) ?
            FastMachinesItems.FAST_CORE : null;

        new FastEnhancedCraftingTable(FastMachinesItems.FAST_ENHANCED_CRAFTING_TABLE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.CRAFTING_TABLE), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), fastCore
        }).register(plugin);
        new FastGrindStone(FastMachinesItems.FAST_GRIND_STONE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.OAK_FENCE), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), fastCore
        }).register(plugin);
        new FastArmorForge(FastMachinesItems.FAST_ARMOR_FORGE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            null, new ItemStack(Material.ANVIL), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), fastCore
        }).register(plugin);
        new FastOreCrusher(FastMachinesItems.FAST_ORE_CRUSHER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.NETHER_BRICK_FENCE), fastCore,
            new ItemStack(Material.IRON_BARS), new ItemStack(Material.DISPENSER), new ItemStack(Material.IRON_BARS)
        }).register(plugin);
        new FastCompressor(FastMachinesItems.FAST_COMPRESSOR, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.NETHER_BRICK_FENCE), fastCore,
            new ItemStack(Material.PISTON), new ItemStack(Material.DISPENSER), new ItemStack(Material.PISTON)
        }).register(plugin);
        new FastSmeltery(FastMachinesItems.FAST_SMELTERY, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            new ItemStack(Material.NETHER_BRICKS), new ItemStack(Material.DISPENSER), new ItemStack(Material.NETHER_BRICKS),
            SlimefunItems.OUTPUT_CHEST, FastMachinesItems.ETERNAL_FIRE, fastCore
        }).register(plugin);
        new FastPressureChamber(FastMachinesItems.FAST_PRESSURE_CHAMBER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), fastCore,
            new ItemStack(Material.PISTON), new ItemStack(Material.GLASS), new ItemStack(Material.PISTON),
            new ItemStack(Material.PISTON), new ItemStack(Material.CAULDRON), new ItemStack(Material.PISTON)
        }).register(plugin);
        new FastMagicWorkbench(FastMachinesItems.FAST_MAGIC_WORKBENCH, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            SlimefunItems.OUTPUT_CHEST, null, fastCore,
            new ItemStack(Material.BOOKSHELF), new ItemStack(Material.CRAFTING_TABLE), new ItemStack(Material.DISPENSER)
        }).register(plugin);
//        new FastOreWasher(FastMachinesItems.FAST_ORE_WASHER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
//            null, new ItemStack(Material.GLASS), null,
//            null, new ItemStack(Material.CRAFTING_TABLE), null,
//            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), null
//        }).register(plugin);
        new FastTableSaw(FastMachinesItems.FAST_TABLE_SAW, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, null, null,
            new ItemStack(Material.SMOOTH_STONE_SLAB), new ItemStack(Material.STONECUTTER), new ItemStack(Material.SMOOTH_STONE_SLAB),
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.IRON_BLOCK), fastCore
        }).register(plugin);
        new FastComposter(FastMachinesItems.FAST_COMPOSTER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            new ItemStack(Material.OAK_SLAB), SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), fastCore, new ItemStack(Material.OAK_SLAB),
            new ItemStack(Material.OAK_SLAB), new ItemStack(Material.CAULDRON), new ItemStack(Material.OAK_SLAB)
        }).register(plugin);
        new FastJuicer(FastMachinesItems.FAST_JUICER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, new ItemStack(Material.GLASS), null,
            null, new ItemStack(Material.NETHER_BRICK_FENCE), null,
            SlimefunItems.OUTPUT_CHEST, new ItemStack(Material.DISPENSER), fastCore
        }).register(plugin);
        // </editor-fold>
    }
}
