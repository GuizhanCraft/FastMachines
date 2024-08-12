package net.guizhanss.fastmachines.items.machines.infinityexpansion;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;

import net.guizhanss.fastmachines.FastMachines;
import net.guizhanss.fastmachines.core.recipes.RawRecipe;
import net.guizhanss.fastmachines.items.machines.generic.AbstractFastMachine;
import net.guizhanss.fastmachines.utils.RecipeUtils;

public final class FastMobDataInfuser extends AbstractFastMachine {

    private static final ItemStack CRAFT_ITEM = FastMachines.getLocalization().getItem("CRAFT", Material.LODESTONE);

    public FastMobDataInfuser(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(item, recipeType, recipe, 20_000, 200_000);
    }

    @Override
    public void registerRecipes() {
        List<RawRecipe> rawRecipes = RecipeUtils.getInfinityMachineRecipes(MobDataInfuser.class);
        RecipeUtils.registerRecipes(recipes, rawRecipes, false);
    }

    @Override
    protected ItemStack getCraftItem() {
        return CRAFT_ITEM;
    }
}
