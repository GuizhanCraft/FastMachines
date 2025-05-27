package net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion2

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.loaders.InfinityExpansion2EnergyCrafterRecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastMobDataInfuser2(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BaseFastMachine(itemGroup, itemStack, recipeType, recipe, 200_000, 20_000) {

    override val craftItemMaterial: Material
        get() = Material.LODESTONE

    override val recipeLoader: RecipeLoader
        get() = InfinityExpansion2EnergyCrafterRecipeLoader(this, "IE_MOB_DATA_INFUSER")

    override fun registerPrecondition() = FastMachines.integrationService.infinityExpansion2Enabled
}
