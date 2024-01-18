package net.guizhanss.fastmachines.items.machines.generic;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.IRecipe;
import net.guizhanss.fastmachines.core.recipes.RandomRecipe;
import net.guizhanss.fastmachines.utils.ItemUtils;
import net.guizhanss.fastmachines.utils.Keys;
import net.guizhanss.fastmachines.utils.MachineUtils;

import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.CHOICE_SLOT;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.CRAFT_SLOT;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.INPUT_SLOTS;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.ITEMS_PER_PAGE;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.NO_ITEM;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.OUTPUT_SLOTS;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.PREVIEW_SLOTS;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.SCROLL_DOWN_SLOT;
import static net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine.SCROLL_UP_SLOT;

/**
 * A cache layer to store some of the data of a Fast Machine.
 */
public final class FastMachineCache {
    private final AbstractFastMachine machine;
    private final BlockMenu menu;
    private final BlockPosition blockPosition;
    private final Map<IRecipe, Integer> outputs = new LinkedHashMap<>();
    private int page = -1;
    private ItemStack choice;

    @ParametersAreNonnullByDefault
    public FastMachineCache(AbstractFastMachine machine, BlockMenu menu) {
        this.machine = machine;
        this.menu = menu;
        this.blockPosition = new BlockPosition(menu.getLocation());
        init();
    }

    private void init() {
        menu.addMenuClickHandler(SCROLL_UP_SLOT, (player, i, itemStack, clickAction) -> {
            page--;
            return false;
        });
        menu.addMenuClickHandler(SCROLL_DOWN_SLOT, (player, i, itemStack, clickAction) -> {
            page++;
            return false;
        });
        menu.addMenuClickHandler(CRAFT_SLOT, (player, i, itemStack, clickAction) -> {
            int amount;
            if (clickAction.isShiftClicked()) {
                if (clickAction.isRightClicked()) {
                    amount = Integer.MAX_VALUE;
                } else {
                    amount = 64;
                }
            } else {
                if (clickAction.isRightClicked()) {
                    amount = 16;
                } else {
                    amount = 1;
                }
            }
            craft(player, amount);
            return false;
        });
    }

    public void tick() {
        if (FastMachines.getSlimefunTickCount() % 2 == 0) {
            findAvailableOutputs();
        }
        updateMenu();
    }

    /**
     * Find all the available outputs based on the given inputs.
     */
    private void findAvailableOutputs() {
        Map<ItemStack, Integer> machineInputs = MachineUtils.getMachineInputAmount(menu, INPUT_SLOTS);
        outputs.clear();

        if (machineInputs.isEmpty()) {
            return;
        }

        FastMachines.debug("current machine: {0}, location: {1}", machine.getClass().getSimpleName(), blockPosition);
        FastMachines.debug("machine inputs: {0}", machineInputs);

        // Fetch available recipes based on inputs
        for (var recipe : machine.getRecipes()) {
            if (recipe.isDisabledInWorld(blockPosition.getWorld())) {
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

    private void updateMenu() {
        ItemStack[] outputItems =
            outputs.keySet().stream().map(recipe -> recipe.getOutput(blockPosition.getWorld())).toArray(ItemStack[]::new);
        int totalPages = (int) Math.ceil(outputs.size() * 1.0 / ITEMS_PER_PAGE);
        // limit the page in range
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            int index = (page - 1) * ITEMS_PER_PAGE + i;
            if (totalPages == 0 || index >= outputs.size()) {
                menu.replaceExistingItem(PREVIEW_SLOTS[i], ChestMenuUtils.getBackground());
                menu.addMenuClickHandler(PREVIEW_SLOTS[i], ChestMenuUtils.getEmptyClickHandler());
                continue;
            }
            ItemStack output = getDisplayItem(outputItems[index]);
            menu.replaceExistingItem(PREVIEW_SLOTS[i], output);
            menu.addMenuClickHandler(PREVIEW_SLOTS[i], (player, slot, itemStack, clickAction) -> {
                choice = outputItems[index];
                updateChoice();
                return false;
            });
        }

        updateChoice();
    }

    private void updateChoice() {
        ItemStack[] outputItems =
            outputs.keySet().stream().map(recipe -> recipe.getOutput(blockPosition.getWorld())).toArray(ItemStack[]::new);

        for (ItemStack output : outputItems) {
            if (ItemUtils.isSimilar(output, choice)) {
                menu.replaceExistingItem(CHOICE_SLOT, getDisplayItem(choice));
                return;
            }
        }
        menu.replaceExistingItem(CHOICE_SLOT, NO_ITEM);
    }

    @ParametersAreNonnullByDefault
    private void craft(Player p, int amount) {
        Preconditions.checkArgument(amount > 0, "amount must greater than 0");

        var outputRecipes = outputs.entrySet().stream().toList();

        // invalid choice, due to previous selection not available anymore
        if (choice == null) {
            return;
        }
        var recipeEntry = outputRecipes.stream().filter(entry ->
            ItemUtils.isSimilar(entry.getKey().getOutput(blockPosition.getWorld()), choice)
        ).findFirst();
        if (recipeEntry.isEmpty()) {
            return;
        }
        var recipe = recipeEntry.get();
        int maxAmount = recipe.getValue();
        amount = Math.min(maxAmount, amount);

        // check if the machine has enough energy
        if (FastMachines.getAddonConfig().getBoolean("fast-machines.use-energy")) {
            int energyNeeded = machine.getEnergyPerUse() * amount;
            int currentEnergy = machine.getCharge(blockPosition.toLocation());
            if (currentEnergy < energyNeeded) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-energy");
                return;
            }
            machine.setCharge(blockPosition.toLocation(), currentEnergy - energyNeeded);
        }

        // remove recipe inputs
        for (var inputEntry : recipe.getKey().getInput().entrySet()) {
            int requiredAmount = inputEntry.getValue() * amount;
            var itemAmount = MachineUtils.getItemAmount(menu, INPUT_SLOTS, inputEntry.getKey());
            // total amount is less than required amount, usually shouldn't happen
            if (itemAmount.getSecondValue() < requiredAmount) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-materials");
                return;
            }
            // remove items from machine
            MachineUtils.removeItems(menu, itemAmount.getFirstValue().stream().mapToInt(Integer::intValue).toArray(),
                inputEntry.getKey(), requiredAmount);
        }

        // push the product
        if (recipe.getKey() instanceof RandomRecipe randomRecipe) {
            boolean machineFull = false;
            for (int i = 0; i < amount; i++) {
                ItemStack product = randomRecipe.getOutput(blockPosition.getWorld()).clone();
                if (MachineUtils.addItem(p, menu, OUTPUT_SLOTS, product, 1)) {
                    machineFull = true;
                }
            }
            if (machineFull) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-space");
            }
        } else {
            ItemStack product = recipe.getKey().getOutput(blockPosition.getWorld()).clone();
            if (MachineUtils.addItem(p, menu, OUTPUT_SLOTS, product, amount)) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-space");
            }
        }
    }

    /**
     * Get the {@link ItemStack} that is used to display in the preview slots.
     *
     * @param item The original {@link ItemStack}.
     * @return The new {@link ItemStack} that is used to display.
     */
    @Nonnull
    private ItemStack getDisplayItem(@Nonnull ItemStack item) {
        ItemStack newItem = item.clone();
        ItemMeta meta = newItem.getItemMeta();
        PersistentDataAPI.setBoolean(meta, Keys.get("display"), true);
        newItem.setItemMeta(meta);
        return newItem;
    }

    /**
     * Get the original {@link ItemStack} from a display {@link ItemStack}.
     *
     * @param displayItem The display {@link ItemStack}.
     * @return The original {@link ItemStack}.
     */
    @Nonnull
    private ItemStack getOriginalItem(@Nonnull ItemStack displayItem) {
        ItemStack newItem = displayItem.clone();
        ItemMeta meta = newItem.getItemMeta();
        PersistentDataAPI.remove(meta, Keys.get("display"));
        newItem.setItemMeta(meta);
        return newItem;
    }
}
