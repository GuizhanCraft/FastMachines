@file:Suppress("unused")

package net.guizhanss.fastmachines.implementation.items

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.implementation.groups.FMItemGroups
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastInfinityWorkbench
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion.FastMobDataInfuser
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion2.FastInfinityWorkbench2
import net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion2.FastMobDataInfuser2
import net.guizhanss.fastmachines.implementation.items.machines.slimeframe.FastSlimeFrameFoundry
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastAncientAltar
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastArmorForge
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastComposter
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastCompressor
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastEnhancedCraftingTable
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastGrindStone
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastJuicer
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastMagicWorkbench
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreCrusher
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastOreWasher
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPanningMachine
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastPressureChamber
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastSmeltery
import net.guizhanss.fastmachines.implementation.items.machines.slimefun.FastTableSaw
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastCraftingTable
import net.guizhanss.fastmachines.implementation.items.machines.vanilla.FastFurnace
import net.guizhanss.fastmachines.implementation.items.materials.StackedAncientPedestal
import net.guizhanss.fastmachines.utils.items.builder.buildSlimefunItem
import net.guizhanss.fastmachines.utils.items.getSfItem
import net.guizhanss.guizhanlib.kt.slimefun.items.builder.ItemRegistry
import net.guizhanss.guizhanlib.kt.slimefun.items.builder.asMaterialType
import net.guizhanss.guizhanlib.kt.slimefun.items.builder.buildRecipe
import org.bukkit.Material

object FMItems : ItemRegistry(FastMachines.instance, FastMachines.localization.idPrefix) {

    //<editor-fold desc="Materials" collapsed="true">
    val ETERNAL_FIRE by buildSlimefunItem<UnplaceableBlock> {
        material = Material.IRON_INGOT.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +"FFF"
            +"CNC"
            'F' means Material.FLINT_AND_STEEL
            'C' means SlimefunItems.IGNITION_CHAMBER
            'N' means Material.NETHERRACK
        }
    }

    val FAST_CORE by buildSlimefunItem<UnplaceableBlock> {
        material = Material.CONDUIT.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" C "
            +" R "
            'C' means SlimefunItems.SMALL_CAPACITOR
            'R' means SlimefunItems.REDSTONE_ALLOY
        }
    }

    val STACKED_ANCIENT_PEDESTAL by buildSlimefunItem<StackedAncientPedestal> {
        material = Material.DISPENSER.asMaterialType()
        itemGroup = FMItemGroups.MATERIALS
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"A A"
            +"   "
            +"A A"
            'A' means SlimefunItems.ANCIENT_PEDESTAL
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Vanilla" collapsed="true">
    val FAST_CRAFTING_TABLE by buildSlimefunItem<FastCraftingTable> {
        material = Material.CRAFTING_TABLE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" c "
            +" C "
            +" o "
            'c' means Material.CHEST
            'C' means Material.CRAFTING_TABLE
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_FURNACE by buildSlimefunItem<FastFurnace> {
        material = Material.FURNACE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" c "
            +" F "
            +" o "
            'c' means Material.CHEST
            'F' means Material.FURNACE
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - Slimefun" collapsed="true">
    val FAST_ENHANCED_CRAFTING_TABLE by buildSlimefunItem<FastEnhancedCraftingTable> {
        material = Material.CARTOGRAPHY_TABLE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" C "
            +"ODo"
            'C' means Material.CRAFTING_TABLE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_GRIND_STONE by buildSlimefunItem<FastGrindStone> {
        material = Material.GRINDSTONE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" F "
            +"ODo"
            'F' means Material.OAK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ARMOR_FORGE by buildSlimefunItem<FastArmorForge> {
        material = Material.IRON_BLOCK.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" A "
            +"ODo"
            'A' means Material.ANVIL
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ORE_CRUSHER by buildSlimefunItem<FastOreCrusher> {
        material = Material.DROPPER.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"OFo"
            +"BDB"
            'F' means Material.NETHER_BRICK_FENCE
            'B' means Material.IRON_BARS
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_COMPRESSOR by buildSlimefunItem<FastCompressor> {
        material = Material.PISTON.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"OFo"
            +"PDP"
            'F' means Material.NETHER_BRICK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'P' means Material.PISTON
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_SMELTERY by buildSlimefunItem<FastSmeltery> {
        material = Material.BLAST_FURNACE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" F "
            +"BDB"
            +"Ofo"
            'F' means Material.NETHER_BRICK_FENCE
            'B' means Material.NETHER_BRICKS
            'D' means Material.DISPENSER
            'f' means ETERNAL_FIRE
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_PRESSURE_CHAMBER by buildSlimefunItem<FastPressureChamber> {
        material = Material.SMOKER.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"ODo"
            +"PGP"
            +"PCP"
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'P' means Material.PISTON
            'G' means Material.GLASS
            'C' means Material.CAULDRON
        }
    }

    val FAST_MAGIC_WORKBENCH by buildSlimefunItem<FastMagicWorkbench> {
        material = Material.BOOKSHELF.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +"O o"
            +"BCD"
            'B' means Material.BOOKSHELF
            'C' means Material.CRAFTING_TABLE
            'D' means Material.DISPENSER
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ORE_WASHER by buildSlimefunItem<FastOreWasher> {
        material = Material.CAULDRON.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" G "
            +" F "
            +"ODo"
            'G' means Material.GLASS
            'F' means Material.OAK_FENCE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_TABLE_SAW by buildSlimefunItem<FastTableSaw> {
        material = Material.STONECUTTER.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"SCS"
            +"OIo"
            'S' means Material.SMOOTH_STONE_SLAB
            'C' means Material.STONECUTTER
            'I' means Material.IRON_BLOCK
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_COMPOSTER by buildSlimefunItem<FastComposter> {
        material = Material.COMPOSTER.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"SOS"
            +"SoS"
            +"SCS"
            'S' means Material.OAK_SLAB
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'C' means Material.CAULDRON
        }
    }

    val FAST_PANNING_MACHINE by buildSlimefunItem<FastPanningMachine> {
        material = Material.HOPPER.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +" T "
            +"OCo"
            'T' means Material.OAK_TRAPDOOR
            'C' means Material.CAULDRON
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_JUICER by buildSlimefunItem<FastJuicer> {
        material = Material.BREWING_STAND.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +" G "
            +" F "
            +"ODo"
            'G' means Material.GLASS
            'F' means Material.NETHER_BRICK_FENCE
            'D' means Material.DISPENSER
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }

    val FAST_ANCIENT_ALTAR by buildSlimefunItem<FastAncientAltar> {
        material = Material.ENCHANTING_TABLE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.MAGIC_WORKBENCH
        recipe = buildRecipe {
            +"   "
            +" o "
            +"PAP"
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'P' means STACKED_ANCIENT_PEDESTAL
            'A' means SlimefunItems.ANCIENT_ALTAR
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - InfinityExpansion" collapsed="true">
    val FAST_INFINITY_WORKBENCH by buildSlimefunItem<FastInfinityWorkbench> {
        material = Material.RESPAWN_ANCHOR.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "INFINITY_FORGE".getSfItem()?.item
        }
    }

    val FAST_MOB_DATA_INFUSER by buildSlimefunItem<FastMobDataInfuser> {
        material = Material.LODESTONE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "DATA_INFUSER".getSfItem()?.item
        }
    }
    //</editor-fold>

    //<editor-fold desc="Machines - SlimeFrame" collapsed="true">
    val FAST_SLIMEFRAME_FOUNDRY by buildSlimefunItem<FastSlimeFrameFoundry> {
        material = Material.ANVIL.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"SCS"
            +"ODo"
            'S' means Material.STONECUTTER
            'C' means Material.CRAFTING_TABLE
            'O' means SlimefunItems.OUTPUT_CHEST
            'D' means Material.DISPENSER
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
        }
    }
    //</editor-fold>

    // <editor-fold desc="Machines - InfinityExpansion2" collapsed="true">
    val FAST_INFINITY_WORKBENCH_2 by buildSlimefunItem<FastInfinityWorkbench2> {
        material = Material.RESPAWN_ANCHOR.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "IE_INFINITY_WORKBENCH".getSfItem()?.item
        }
    }

    val FAST_MOB_DATA_INFUSER_2 by buildSlimefunItem<FastMobDataInfuser2> {
        material = Material.LODESTONE.asMaterialType()
        itemGroup = FMItemGroups.MACHINES
        recipeType = RecipeType.ENHANCED_CRAFTING_TABLE
        recipe = buildRecipe {
            +"   "
            +"   "
            +"Omo"
            'O' means SlimefunItems.OUTPUT_CHEST
            'o' means if (FastMachines.configService.fmUseEnergy.value) FAST_CORE else null
            'm' means "IE_MOB_DATA_INFUSER".getSfItem()?.item
        }
    }
    //</editor-fold>
}
