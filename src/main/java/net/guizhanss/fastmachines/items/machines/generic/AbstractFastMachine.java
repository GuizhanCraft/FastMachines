package net.guizhanss.fastmachines.items.machines.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.IRecipe;
import net.guizhanss.fastmachines.setup.Groups;
import net.guizhanss.fastmachines.utils.Heads;
import net.guizhanss.guizhanlib.minecraft.utils.ItemUtil;
import net.guizhanss.guizhanlib.slimefun.machines.TickingMenuBlock;

import lombok.Getter;

/**
 * A fast machine is a basic machine but uses energy to fast craft from the given materials.
 * <p>
 * Idea from <a href="https://github.com/ecro-fun/FinalTECH">FinalTECH</a>.
 *
 * @author Final_ROOT
 * @author ybw0014
 */
@SuppressWarnings("ConstantConditions")
public abstract class AbstractFastMachine extends TickingMenuBlock implements EnergyNetComponent {
    // outputs map
    protected static final Map<BlockPosition, Map<IRecipe, Integer>> OUTPUTS_MAP = new HashMap<>();
    // slots
    static final int[] INPUT_SLOTS = new int[] {
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        27, 28, 29, 30, 31, 32, 33, 34, 35
    };
    static final int[] OUTPUT_SLOTS = new int[] {
        27, 28, 29, 30, 31, 32, 33, 34, 35,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        0, 1, 2, 3, 4, 5, 6, 7, 8,
    };
    static final int[] PREVIEW_SLOTS = new int[] {
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    static final int SCROLL_UP_SLOT = 42;
    static final int SCROLL_DOWN_SLOT = 51;
    static final int CHOICE_SLOT = 52;
    static final int CRAFT_SLOT = 53;
    static final int CHOICE_INDICATOR_SLOT = 43;
    static final int INFO_SLOT = 44;
    // constants
    static final int ITEMS_PER_PAGE = PREVIEW_SLOTS.length;
    // menu items
    static final ItemStack NO_ITEM = FastMachines.getLocalization().getItem(
        "NO_ITEM", Material.BARRIER);
    static final ItemStack SCROLL_UP_ITEM = FastMachines.getLocalization().getItem(
        "SCROLL_UP", Heads.ARROW_UP.getTexture());
    static final ItemStack SCROLL_DOWN_ITEM = FastMachines.getLocalization().getItem(
        "SCROLL_DOWN", Heads.ARROW_DOWN.getTexture());
    static final ItemStack CHOICE_INDICATOR_ITEM = FastMachines.getLocalization().getItem(
        "CHOICE_INDICATOR", Material.YELLOW_STAINED_GLASS_PANE);
    static final ItemStack INFO_ITEM = FastMachines.getLocalization().getItem(
        "INFO", Heads.INFO.getTexture());

    @Getter
    protected final List<IRecipe> recipes = new ArrayList<>();

    private final Map<BlockPosition, FastMachineCache> CACHES = new HashMap<>();

    @ParametersAreNonnullByDefault
    protected AbstractFastMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Groups.MACHINES, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    // Also getCapacity() which is already defined.
    public abstract int getEnergyPerUse();

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        for (int slot : PREVIEW_SLOTS) {
            preset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addItem(CHOICE_INDICATOR_SLOT, CHOICE_INDICATOR_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INFO_SLOT, INFO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(CHOICE_SLOT, NO_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(SCROLL_UP_SLOT, SCROLL_UP_ITEM, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(SCROLL_DOWN_SLOT, SCROLL_DOWN_ITEM, ChestMenuUtils.getEmptyClickHandler());

        ItemStack craftItem = ItemUtil.appendLore(
            getCraftItem(),
            "",
            LoreBuilder.power(getEnergyPerUse(), FastMachines.getLocalization().getString("lores.per-craft"))
        );
        preset.addItem(CRAFT_SLOT, craftItem, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onNewInstance(BlockMenu menu, Block block) {
        BlockPosition pos = new BlockPosition(block);
        CACHES.put(pos, new FastMachineCache(this, menu));
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void tick(Block b, BlockMenu menu) {
        BlockPosition pos = new BlockPosition(b);
        if (menu.hasViewer() && CACHES.containsKey(pos)) {
            CACHES.get(pos).tick();
        }
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        // Basic machines should not support cargo access
        return new int[0];
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);
        FastMachines.getScheduler().run(2, this::registerRecipes);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onBreak(BlockBreakEvent e, BlockMenu menu) {
        super.onBreak(e, menu);
        Location l = menu.getLocation();
        menu.dropItems(l, INPUT_SLOTS);
        CACHES.remove(new BlockPosition(l));
    }

    /**
     * Register available recipes for this machine.
     * <p>
     * Note: this method is called synchronously 2 ticks after server completes loading.
     */
    protected abstract void registerRecipes();

    /**
     * Get the item that shows the craft button of this machine.
     *
     * @return the item that shows the craft button of this machine.
     */
    protected abstract ItemStack getCraftItem();
}
