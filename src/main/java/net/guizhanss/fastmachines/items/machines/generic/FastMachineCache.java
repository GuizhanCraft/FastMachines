package net.guizhanss.fastmachines.items.machines.generic;

import java.util.LinkedHashMap;
import java.util.List;
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
    private int inputChecksum;
    private Map<IRecipe, Integer> outputs;
    private int page = -1;
    private ItemStack choice;
    private boolean crafting = false;

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
            final int amount;
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
            if (crafting) {
                return false;
            }
            crafting = true;
            craft(player, amount);
            crafting = false;
            return false;
        });
    }

    public void tick() {
        if (FastMachines.getSlimefunTickCount() % 2 == 0) {
            findAvailableOutputs();
        }
        if (outputs != null) {
            updateMenu();
        }
    }

    /**
     * Find all the available outputs based on the given inputs.
     */
    private void findAvailableOutputs() {
        Map<ItemStack, Integer> machineInputs = MachineUtils.getMachineInputAmount(menu, INPUT_SLOTS);
        if (machineInputs.isEmpty()) {
            return;
        }
        int currentInputChecksum = MachineUtils.checksum(machineInputs);
        if (currentInputChecksum == inputChecksum) {
            return;
        }
        inputChecksum = currentInputChecksum;
        Map<IRecipe, Integer> newOutputs = new LinkedHashMap<>();

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
                newOutputs.put(recipe, outputAmount);
            }
        }

        FastMachines.debug("outputs: " + newOutputs);
        outputs = newOutputs;
    }

    private void updateMenu() {
        ItemStack[] outputItems = new LinkedHashMap<>(outputs)
            .keySet().stream().map(recipe -> recipe.getOutput(blockPosition.getWorld())).toArray(ItemStack[]::new);
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
        var outputItemSet = new LinkedHashMap<>(outputs).keySet();
        for (IRecipe recipe : outputItemSet) {
            if (recipe instanceof RandomRecipe randomRecipe) {
                if (ItemUtils.isSimilar(choice, List.of(randomRecipe.getAllOutputs()))) {
                    menu.replaceExistingItem(CHOICE_SLOT, getDisplayItem(randomRecipe.getOutput(blockPosition.getWorld())));
                    return;
                }
            } else {
                var output = recipe.getOutput(blockPosition.getWorld());
                if (ItemUtils.isSimilar(choice, output)) {
                    menu.replaceExistingItem(CHOICE_SLOT, getDisplayItem(output));
                    return;
                }
            }
        }
        menu.replaceExistingItem(CHOICE_SLOT, NO_ITEM);
    }


    @ParametersAreNonnullByDefault
    private void craft(final Player p, final int amount) {
        craft(p, amount, 0);
    }

    @ParametersAreNonnullByDefault
    private void craft(final Player p, final int amount, int tries) {
        Preconditions.checkArgument(amount > 0, "amount must greater than 0");

        List<Map.Entry<IRecipe, Integer>> outputRecipes;
        try {
            outputRecipes = new LinkedHashMap<>(outputs).entrySet().stream().toList();
        } catch (Exception e) {
            // sometimes player crafts when the machine is calculating outputs,
            // so we just delay this one tick and try again
            if (tries < 5) {
                FastMachines.getScheduler().run(() -> craft(p, amount, tries + 1));
            }
            return;
        }

        // invalid choice, due to previous selection not available anymore
        if (choice == null) {
            return;
        }
        var recipeEntry = outputRecipes.stream().filter(entry ->
            ItemUtils.isSimilar(choice, List.of(entry.getKey().getAllOutputs()))
        ).findFirst();
        if (recipeEntry.isEmpty()) {
            return;
        }
        var recipe = recipeEntry.get();
        final int maxAmount = recipe.getValue();
        int actualAmount = Math.min(maxAmount, amount);

        // check if the machine has enough energy
        if (FastMachines.getConfigService().isFastMachinesUseEnergy()) {
            int energyNeeded = machine.getEnergyPerUse() * actualAmount;
            int currentEnergy = machine.getCharge(blockPosition.toLocation());
            if (currentEnergy < energyNeeded) {
                FastMachines.getLocalization().sendMessage(p, "not-enough-energy");
                return;
            }
            machine.setCharge(blockPosition.toLocation(), currentEnergy - energyNeeded);
        }

        // remove recipe inputs
        for (var inputEntry : recipe.getKey().getInput().entrySet()) {
            int requiredAmount = inputEntry.getValue() * actualAmount;
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
            for (int i = 0; i < actualAmount; i++) {
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
            if (MachineUtils.addItem(p, menu, OUTPUT_SLOTS, product, actualAmount)) {
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
