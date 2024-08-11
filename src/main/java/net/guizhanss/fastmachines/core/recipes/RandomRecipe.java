package net.guizhanss.fastmachines.core.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import net.guizhanss.fastmachines.utils.RecipeUtils;

/**
 * A {@link RandomRecipe} is a recipe that contains only one fixed output {@link ItemStack}.
 *
 * @author ybw0014
 */
public class RandomRecipe implements IRecipe {

    private final List<ItemStack> outputs;
    private final ItemStack input;

    public RandomRecipe(ItemStack input, ItemStack... outputs) {
        this(input, Arrays.asList(outputs));
    }

    public RandomRecipe(ItemStack input, List<ItemStack> outputs) {
        this.input = input;
        this.outputs = new ArrayList<>(outputs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomRecipe other = (RandomRecipe) o;
        return this.input.equals(other.input) && this.outputs.equals(other.outputs);
    }

    @Override
    public int hashCode() {
        int result = outputs.hashCode();
        result = 31 * result + input.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RandomRecipe{" +
            "input=" + input +
            ", outputs=" + outputs +
            '}';
    }

    /**
     * Check whether all the output items are disabled in the given {@link World}.
     *
     * @param world The world to check.
     * @return True if all the output items are disabled in the given {@link World}.
     */
    @Override
    public boolean isDisabledInWorld(@Nonnull World world) {
        for (ItemStack output : outputs) {
            SlimefunItem sfItem = SlimefunItem.getByItem(output);
            if (sfItem == null || !sfItem.isDisabledIn(world)) {
                return false;
            }
        }
        return true;
    }

    @Nonnull
    public ItemStack getRawInput() {
        return input;
    }

    public void addOutput(@Nonnull ItemStack... output) {
        addOutput(Arrays.asList(output));
    }

    public void addOutput(@Nonnull Collection<ItemStack> output) {
        outputs.addAll(output);
    }

    @Nonnull
    @Override
    public Map<ItemStack, Integer> getInput() {
        return RecipeUtils.calculateItems(input);
    }

    @Override
    @Nonnull
    public ItemStack[] getAllOutputs() {
        return outputs.toArray(new ItemStack[0]);
    }

    @Override
    @Nonnull
    public ItemStack getOutput(@Nonnull World world) {
        List<ItemStack> filteredOutputs = outputs.stream().filter(output -> {
            SlimefunItem sfItem = SlimefunItem.getByItem(output);
            return sfItem == null || !sfItem.isDisabledIn(world);
        }).toList();
        int index = ThreadLocalRandom.current().nextInt(filteredOutputs.size());
        return filteredOutputs.get(index);
    }
}
