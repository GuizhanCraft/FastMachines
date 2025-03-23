package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import net.guizhanss.fastmachines.core.items.attributes.NotAHopper
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastPanningMachine(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe), NotAHopper {

    override val craftItemMaterial: Material
        get() = Material.BOWL

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SlimefunItems.AUTOMATED_PANNING_MACHINE.itemId, true)
}
