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

class FastInfinityWorkbench2(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BaseFastMachine(itemGroup, itemStack, recipeType, recipe, 100_000_000, 10_000_000) {

    override val craftItemMaterial: Material
        get() = Material.RESPAWN_ANCHOR

    override val recipeLoader: RecipeLoader
        get() = InfinityExpansion2EnergyCrafterRecipeLoader(this, "IE_INFINITY_WORKBENCH")

    override fun registerPrecondition() = FastMachines.integrationService.infinityExpansion2Enabled
}
