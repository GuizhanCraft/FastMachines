package net.guizhanss.fastmachines.utils.items

import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.choices.MultipleChoice
import org.bukkit.inventory.RecipeChoice as BukkitRecipeChoice

fun BukkitRecipeChoice.asFMRecipeChoice(amount: Int = 1) =
    when (this) {
        is BukkitRecipeChoice.MaterialChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first()), amount)
            } else {
                MultipleChoice(choices.associate { ItemWrapper.of(it) to amount })
            }
        }

        is BukkitRecipeChoice.ExactChoice -> {
            if (choices.size == 1) {
                ExactChoice(ItemWrapper.of(choices.first()), amount)
            } else {
                MultipleChoice(choices.associate { ItemWrapper.of(it) to amount })
            }
        }

        else -> {
            throw IllegalArgumentException("Unknown RecipeChoice type: $this")
        }
    }
