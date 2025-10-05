package net.guizhanss.fastmachines.utils

import net.guizhanss.fastmachines.core.recipes.choices.ExactChoice
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach

/**
 * Consolidate a list of [RecipeChoice] by merging [ExactChoice] with the same item.
 *
 * Other types of [RecipeChoice] will remain unchanged.
 */
fun List<RecipeChoice>.consolidate(): List<RecipeChoice> {
    val exactChoices = mutableListOf<ExactChoice>()
    val otherChoices = mutableListOf<RecipeChoice>()

    this.forEach { choice ->
        when (choice) {
            is ExactChoice -> exactChoices.add(choice)
            else -> otherChoices.add(choice)
        }
    }

    val mergedExactChoices = exactChoices.groupBy { it.item }.flatMap { (item, itemChoices) ->
        val totalAmount = itemChoices.sumOf { it.amount }
        val maxStackSize = item.baseItem.maxStackSize

        if (totalAmount <= maxStackSize) {
            listOf(ExactChoice(item, totalAmount))
        } else {
            // Split into multiple choices if exceeding max stack size
            val fullStacks = totalAmount / maxStackSize
            val remainder = totalAmount % maxStackSize

            buildList {
                repeat(fullStacks) {
                    add(ExactChoice(item, maxStackSize))
                }
                if (remainder > 0) {
                    add(ExactChoice(item, remainder))
                }
            }
        }
    }

    return mergedExactChoices + otherChoices
}
