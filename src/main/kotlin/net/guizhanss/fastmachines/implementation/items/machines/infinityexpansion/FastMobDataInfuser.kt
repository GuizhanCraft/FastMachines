package net.guizhanss.fastmachines.implementation.items.machines.infinityexpansion

import io.github.mooy1.infinityexpansion.items.mobdata.MobDataInfuser
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.recipes.loaders.InfinityExpansionRecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BaseFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastMobDataInfuser(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BaseFastMachine(itemGroup, itemStack, recipeType, recipe, 100_000_000, 10_000_000) {

    override val craftItemMaterial: Material
        get() = Material.LODESTONE

    override val recipeLoader: RecipeLoader
        get() = InfinityExpansionRecipeLoader(this, MobDataInfuser::class.java)

    override fun registerPrecondition() = FastMachines.integrationService.infinityExpansionEnabled
}
