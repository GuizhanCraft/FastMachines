package net.guizhanss.fastmachines.core.recipes;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import net.guizhanss.fastmachines.utils.RecipeUtils;

import lombok.Getter;

/**
 * A {@link StandardRecipe} is a recipe that contains only one fixed output {@link ItemStack}.
 *
 * @author ybw0014
 */
public class StandardRecipe implements IRecipe {

    private final ItemStack output;
    @Getter
    private final Map<ItemStack, Integer> input;

    public StandardRecipe(ItemStack output, ItemStack... input) {
        this.output = output;
        this.input = RecipeUtils.countItems(input);
    }

    public StandardRecipe(ItemStack output, List<ItemStack> input) {
        this(output, input.toArray(new ItemStack[0]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardRecipe other = (StandardRecipe) o;
        return this.input.equals(other.input) && this.output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = output.hashCode();
        result = 31 * result + input.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StandardRecipe{" + "output=" + output + ", input=" + input + '}';
    }

    @Override
    public boolean isDisabledInWorld(@Nonnull World world) {
        SlimefunItem sfItem = SlimefunItem.getByItem(output);
        return sfItem != null && sfItem.isDisabledIn(world);
    }

    @Override
    @Nonnull
    public ItemStack[] getAllOutputs() {
        return new ItemStack[] {output};
    }

    @Override
    @Nonnull
    public ItemStack getOutput(@Nonnull World world) {
        return output;
    }
}
