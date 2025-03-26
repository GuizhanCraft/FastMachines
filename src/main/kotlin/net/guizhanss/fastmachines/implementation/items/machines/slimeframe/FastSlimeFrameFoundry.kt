package net.guizhanss.fastmachines.implementation.items.machines.slimeframe

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import me.voper.slimeframe.implementation.SFrameStacks
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.attributes.NotAnAnvil
import net.guizhanss.fastmachines.core.recipes.loaders.RecipeLoader
import net.guizhanss.fastmachines.core.recipes.loaders.SlimefunMultiblockRecipeLoader
import net.guizhanss.fastmachines.implementation.items.machines.base.BasicFastMachine
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class FastSlimeFrameFoundry(
    itemGroup: ItemGroup,
    itemStack: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack?>,
) : BasicFastMachine(itemGroup, itemStack, recipeType, recipe), NotAnAnvil {

    override val craftItemMaterial: Material
        get() = Material.ANVIL

    override val recipeLoader: RecipeLoader
        get() = SlimefunMultiblockRecipeLoader(this, SFrameStacks.FOUNDRY.itemId)

    override fun registerPrecondition() = FastMachines.integrationService.slimeFrameEnabled
}
