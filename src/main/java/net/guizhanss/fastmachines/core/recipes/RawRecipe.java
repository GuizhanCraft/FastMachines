package net.guizhanss.fastmachines.core.recipes;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;

/**
 * A temporary class used to store a pair of input and output. This class is used before registering recipes.
 *
 * @param input  The input {@link ItemStack}s.
 * @param output The output {@link ItemStack}s.
 */
public record RawRecipe(ItemStack[] input, ItemStack[] output) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RawRecipe other = (RawRecipe) obj;
        return Arrays.equals(input, other.input) && Arrays.equals(output, other.output);
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(input) + 17 * Arrays.hashCode(output);
    }

    @Override
    public String toString() {
        return "RawRecipe{" +
            "input=" + Arrays.toString(input) +
            ", output=" + Arrays.toString(output) +
            '}';
    }
}
