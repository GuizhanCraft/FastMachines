package net.guizhanss.fastmachines.utils.items

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.choices.MultipleChoice
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.guizhanlib.kt.minecraft.extensions.toItem
import org.bukkit.inventory.RecipeChoice as BukkitRecipeChoice

fun Collection<RecipeChoice>.countItems(): Map<RecipeChoice, Int> {
    val map = mutableMapOf<RecipeChoice, Int>()

    for (choice in this) {
        map[choice] = map.getOrDefault(choice, 0) + 1
    }

    return map
}

fun BukkitRecipeChoice.asFMRecipeChoice() =
    when (this) {
        is BukkitRecipeChoice.MaterialChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first().toItem()))
            } else {
                MultipleChoice(choices.associate { ItemWrapper.of(it.toItem()) to 1 })
            }
        }

        is BukkitRecipeChoice.ExactChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first()))
            } else {
                MultipleChoice(choices.associate { ItemWrapper.of(it) to 1 })
            }
        }

        else -> {
            throw IllegalArgumentException("Unknown RecipeChoice type: $this")
        }
    }
