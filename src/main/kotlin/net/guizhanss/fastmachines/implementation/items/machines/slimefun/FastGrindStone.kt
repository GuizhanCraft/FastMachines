package net.guizhanss.fastmachines.implementation.items.machines.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.generic.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastGrindStone(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe) {

    override val craftItemMaterial: Material
        get() = Material.DISPENSER

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SlimefunItems.GRIND_STONE.itemId)
}
