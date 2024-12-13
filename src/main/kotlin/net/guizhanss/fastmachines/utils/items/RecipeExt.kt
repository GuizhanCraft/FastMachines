package net.guizhanss.fastmachines.utils.items

import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice

fun Collection<RecipeChoice>.summarize(): Map<RecipeChoice, Int> {
    val map = mutableMapOf<RecipeChoice, Int>()

    for (choice in this) {
        map[choice] = map.getOrDefault(choice, 0) + 1
    }

    return map
}
