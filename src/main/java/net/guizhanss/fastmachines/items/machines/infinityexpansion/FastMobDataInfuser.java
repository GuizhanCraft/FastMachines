package net.guizhanss.fastmachines.items.machines.infinityexpansion;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.generic.AInfinityMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastMobDataInfuser extends AInfinityMachine {
    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem(
        "CRAFT", Material.LODESTONE);

    public FastMobDataInfuser(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe);
    }

    @Override
    public void registerRecipes() {
        FastMachines.debug("Registering recipes for {0}", getClass().getSimpleName());
        try {
            List<RawRecipe> rawRecipes = RecipeUtils.getInfinityMachineRecipes(MobDataInfuser.class);
            RecipeUtils.registerRecipes(recipes, rawRecipes, false);
        } catch (Exception ex) {
            FastMachines.log(Level.SEVERE, ex, "An error has occurred while registering recipes for {0}", getClass().getSimpleName());
        }
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
