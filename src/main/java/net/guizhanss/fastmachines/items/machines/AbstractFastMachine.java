package net.guizhanss.fastmachines.items.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.IRecipe;
import net.guizhanss.fastmachines.core.recipes.RandomRecipe;
import net.guizhanss.fastmachines.setup.Groups;
import net.guizhanss.fastmachines.utils.BlockStorageUtils;
import net.guizhanss.fastmachines.utils.Heads;
import net.guizhanss.fastmachines.utils.Keys;
import net.guizhanss.fastmachines.utils.MachineUtils;
import net.guizhanss.guizhanlib.minecraft.utils.ItemUtil;
import net.guizhanss.guizhanlib.slimefun.machines.TickingMenuBlock;

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

    // slots
    protected static final int[] INPUT_SLOTS = new int[] {
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        27, 28, 29, 30, 31, 32, 33, 34, 35
    };
    protected static final int[] OUTPUT_SLOTS = new int[] {
        27, 28, 29, 30, 31, 32, 33, 34, 35,
        18, 19, 20, 21, 22, 23, 24, 25, 26,
        9, 10, 11, 12, 13, 14, 15, 16, 17,
        0, 1, 2, 3, 4, 5, 6, 7, 8,
    };
    protected static final int[] PREVIEW_SLOTS = new int[] {
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    protected static final int SCROLL_UP_SLOT = 42;
    protected static final int SCROLL_DOWN_SLOT = 51;
    protected static final int CHOICE_SLOT = 52;
    protected static final int CRAFT_SLOT = 53;
    protected static final int CHOICE_INDICATOR_SLOT = 43;
    protected static final int INFO_SLOT = 44;

    // constants
    protected static final int ITEMS_PER_PAGE = PREVIEW_SLOTS.length;
    protected static final String KEY_CHOICE = "choice";

    // menu items
    protected static final ItemStack SCROLL_UP_ITEM = FastMachines.getLocalization().getItem(
        "SCROLL_UP", Heads.ARROW_UP.getTexture());
    protected static final ItemStack SCROLL_DOWN_ITEM = FastMachines.getLocalization().getItem(
        "SCROLL_DOWN", Heads.ARROW_DOWN.getTexture());
    protected static final ItemStack CHOICE_INDICATOR_ITEM = FastMachines.getLocalization().getItem(
        "CHOICE_INDICATOR", Material.YELLOW_STAINED_GLASS_PANE);
    protected static final ItemStack INFO_ITEM = FastMachines.getLocalization().getItem(
        "INFO", Heads.INFO.getTexture());
    protected static final ItemStack NO_ITEM = FastMachines.getLocalization().getItem(
        "NO_ITEM", Material.BARRIER);

    // outputs map
    protected static final Map<BlockPosition, Map<IRecipe, Integer>> OUTPUTS_MAP = new HashMap<>();

    protected final List<IRecipe> recipes = new ArrayList<>();
    protected final IntRangeSetting energyPerUse = new IntRangeSetting(this, "energy-per-use", 0, 8, Integer.MAX_VALUE);
    protected final IntRangeSetting energyCapacity = new IntRangeSetting(this, "energy-capacity", 0, 1024, Integer.MAX_VALUE);

    protected AbstractFastMachine(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(Groups.MACHINES, item, recipeType, recipe);

        addItemSetting(energyPerUse);
        addItemSetting(energyCapacity);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return energyCapacity.getValue();
    }

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
            LoreBuilder.power(energyPerUse.getValue(), FastMachines.getLocalization().getString("lores.per-craft"))
        );
        preset.addItem(CRAFT_SLOT, craftItem, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onNewInstance(BlockMenu blockMenu, Block block) {
        BlockPosition pos = new BlockPosition(block);
        blockMenu.addMenuClickHandler(SCROLL_UP_SLOT, (player, i, itemStack, clickAction) -> {
            int currentPage = BlockStorageUtils.getInt(pos, "page", 1);
            BlockStorageUtils.setInt(pos, "page", currentPage - 1);
            updateMenu(blockMenu);
            return false;
        });
        blockMenu.addMenuClickHandler(SCROLL_DOWN_SLOT, (player, i, itemStack, clickAction) -> {
            int currentPage = BlockStorageUtils.getInt(pos, "page", 1);
            BlockStorageUtils.setInt(pos, "page", currentPage + 1);
            updateMenu(blockMenu);
            return false;
        });
        blockMenu.addMenuClickHandler(CRAFT_SLOT, (player, i, itemStack, clickAction) -> {
            if (clickAction.isShiftClicked()) {
                if (clickAction.isRightClicked()) {
                    craft(blockMenu, player, Integer.MAX_VALUE);
                } else {
                    craft(blockMenu, player, 64);
                }
            } else {
                if (clickAction.isRightClicked()) {
                    craft(blockMenu, player, 16);
                } else {
                    craft(blockMenu, player, 1);
                }
            }
            return false;
        });
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void tick(Block block, BlockMenu blockMenu) {
        if (blockMenu.hasViewer() && FastMachines.getSlimefunTickCount() % 2 == 0) {
            // Calculate machine inputs
            findAvailableOutputs(blockMenu);
            // Display available outputs
            updateMenu(blockMenu);
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
    }

    @Nonnull
    protected Map<IRecipe, Integer> getMachineOutputs(@Nonnull BlockMenu menu) {
        BlockPosition pos = new BlockPosition(menu.getLocation());
        var outputs = OUTPUTS_MAP.getOrDefault(pos, new LinkedHashMap<>());
        OUTPUTS_MAP.put(pos, outputs);
        return outputs;
    }

    /**
     * Find all the available outputs based on the given inputs.
     *
     * @param blockMenu
     *     The {@link BlockMenu} of this machine.
     */
    @ParametersAreNonnullByDefault
    protected void findAvailableOutputs(BlockMenu blockMenu) {
        BlockPosition pos = new BlockPosition(blockMenu.getLocation());
        Map<ItemStack, Integer> machineInputs = MachineUtils.getMachineInputAmount(blockMenu, INPUT_SLOTS);
        var outputs = getMachineOutputs(blockMenu);
        outputs.clear();

        if (machineInputs.isEmpty()) {
            return;
        }

        FastMachines.debug("current machine location: {0}", pos);
        FastMachines.debug("machine inputs: {0}", machineInputs);

        // Fetch available recipes based on inputs
        for (var recipe : recipes) {
            if (recipe.isDisabledInWorld(pos.getWorld())) {
                continue;
            }

            FastMachines.debug("checking recipe: {0}", recipe);

            // stores the possible maximum output amount
            int outputAmount = Integer.MAX_VALUE;

            for (var recipeInputEntry : recipe.getInput().entrySet()) {

                // if the current recipe input isn't in the machine input, just ignore this
                if (!machineInputs.containsKey(recipeInputEntry.getKey())) {
                    outputAmount = 0;
                    break;
                }

                FastMachines.debug("input: {0}, machine amount: {1}", recipeInputEntry.getKey(),
                    machineInputs.get(recipeInputEntry.getKey()));
                FastMachines.debug("recipe amount: {0}", recipeInputEntry.getValue());

                // check the maximum possible output amount based on inputs
                int inputAmount = machineInputs.get(recipeInputEntry.getKey());
                int recipeAmount = recipeInputEntry.getValue();
                outputAmount = Math.min(outputAmount, inputAmount / recipeAmount);

                FastMachines.debug("result amount: {0}", inputAmount / recipeAmount);
            }

            // this recipe is available
            if (outputAmount > 0) {
                FastMachines.debug("recipe is available, output amount: {0}", outputAmount);
                outputs.put(recipe, outputAmount);
            }
        }

        FastMachines.debug("outputs: " + outputs);
    }

    @ParametersAreNonnullByDefault
    protected void updateMenu(BlockMenu blockMenu) {
        BlockPosition pos = new BlockPosition(blockMenu.getLocation());
        var outputs = getMachineOutputs(blockMenu);
        ItemStack[] outputItems =
            outputs.keySet().stream().map(recipe -> recipe.getOutput(pos.getWorld())).toArray(ItemStack[]::new);
        int currentPage = BlockStorageUtils.getInt(pos, "page", 1);
        int totalPages = (int) Math.ceil(outputs.size() * 1.0 / ITEMS_PER_PAGE);
        // limit the page in range
        if (currentPage < 1) {
            currentPage = 1;
            BlockStorageUtils.setInt(pos, "page", 1);
        }
        if (currentPage > totalPages) {
            currentPage = totalPages;
            BlockStorageUtils.setInt(pos, "page", totalPages);
        }

        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            int index = (currentPage - 1) * ITEMS_PER_PAGE + i;
            if (totalPages == 0 || index >= outputs.size()) {
                blockMenu.replaceExistingItem(PREVIEW_SLOTS[i], ChestMenuUtils.getBackground());
                blockMenu.addMenuClickHandler(PREVIEW_SLOTS[i], ChestMenuUtils.getEmptyClickHandler());
                continue;
            }
            ItemStack output = getDisplayItem(outputItems[index]);
            blockMenu.replaceExistingItem(PREVIEW_SLOTS[i], output);
            blockMenu.addMenuClickHandler(PREVIEW_SLOTS[i], ((player, slot, itemStack, clickAction) -> {
                BlockStorageUtils.setInt(pos, KEY_CHOICE, index);
                updateChoice(blockMenu);
                return false;
            }));
        }

        updateChoice(blockMenu);
    }

    @ParametersAreNonnullByDefault
    protected void updateChoice(BlockMenu blockMenu) {
        BlockPosition pos = new BlockPosition(blockMenu.getLocation());
        var outputs = getMachineOutputs(blockMenu);
        ItemStack[] outputItems =
            outputs.keySet().stream().map(recipe -> recipe.getOutput(pos.getWorld())).toArray(ItemStack[]::new);

        int choice = BlockStorageUtils.getInt(pos, KEY_CHOICE);
        if (choice >= outputs.size()) {
            blockMenu.replaceExistingItem(CHOICE_SLOT, NO_ITEM);
        } else {
            ItemStack output = getDisplayItem(outputItems[choice]);
            blockMenu.replaceExistingItem(CHOICE_SLOT, output);
        }
    }

    @ParametersAreNonnullByDefault
    protected void craft(BlockMenu blockMenu, Player p, int amount) {
        Preconditions.checkArgument(amount > 0, "amount must greater than 0");

        BlockPosition pos = new BlockPosition(blockMenu.getLocation());
        var outputs = getMachineOutputs(blockMenu);
        var outputRecipes = outputs.entrySet().stream().toList();

        int choice = BlockStorageUtils.getInt(pos, KEY_CHOICE);
        // invalid choice, due to previous selection not available anymore
        if (choice >= outputs.size()) {
            return;
        }
        var recipe = outputRecipes.get(choice);
        int maxAmount = recipe.getValue();
        amount = Math.min(maxAmount, amount);

        // check if the machine has enough energy
        if (FastMachines.getAddonConfig().getBoolean("fast-machines.use-energy")) {
            int energyNeeded = energyPerUse.getValue() * amount;
            int currentEnergy = getCharge(blockMenu.getLocation());
            if (currentEnergy < energyNeeded) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-energy");
                return;
            }
            setCharge(blockMenu.getLocation(), currentEnergy - energyNeeded);
        }

        // remove recipe inputs
        for (var inputEntry : recipe.getKey().getInput().entrySet()) {
            int requiredAmount = inputEntry.getValue() * amount;
            var itemAmount = MachineUtils.getItemAmount(blockMenu, INPUT_SLOTS, inputEntry.getKey());
            // total amount is less than required amount, usually shouldn't happen
            if (itemAmount.getSecondValue() < requiredAmount) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-materials");
                return;
            }
            // remove items from machine
            MachineUtils.removeItems(blockMenu, itemAmount.getFirstValue().stream().mapToInt(Integer::intValue).toArray(),
                inputEntry.getKey(), requiredAmount);
        }

        // push the product
        if (recipe.getKey() instanceof RandomRecipe randomRecipe) {
            boolean machineFull = false;
            for (int i = 0; i < amount; i++) {
                ItemStack product = randomRecipe.getOutput(pos.getWorld()).clone();
                if (MachineUtils.addItem(p, blockMenu, OUTPUT_SLOTS, product, 1)) {
                    machineFull = true;
                }
            }
            if (machineFull) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-space");
            }
        } else {
            ItemStack product = recipe.getKey().getOutput(pos.getWorld()).clone();
            if (MachineUtils.addItem(p, blockMenu, OUTPUT_SLOTS, product, amount)) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-space");
            }
        }
    }

    /**
     * Get the {@link ItemStack} that is used to display in the preview slots.
     *
     * @param item
     *     The original {@link ItemStack}.
     *
     * @return The new {@link ItemStack} that is used to display.
     */
    @Nonnull
    protected ItemStack getDisplayItem(@Nonnull ItemStack item) {
        ItemStack newItem = item.clone();
        ItemMeta meta = newItem.getItemMeta();
        PersistentDataAPI.setBoolean(meta, Keys.get("display"), true);
        newItem.setItemMeta(meta);
        return newItem;
    }

    /**
     * Register available recipes for this machine.
     * <p>
     * Note: this method is called synchronously after server completes loading.
     */
    protected abstract void registerRecipes();

    /**
     * Get the item that shows the craft button of this machine.
     *
     * @return the item that shows the craft button of this machine.
     */
    protected abstract ItemStack getCraftItem();
}
