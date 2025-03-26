package net.guizhanss.fastmachines.utils

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.fastmachines.core.items.ItemWrapper
import net.guizhanss.fastmachines.core.recipes.choices.RecipeChoice
import net.guizhanss.fastmachines.utils.items.countItems
import net.guizhanss.fastmachines.utils.items.isSimilarTo
import org.bukkit.inventory.ItemStack

/**
 * Get all the [ItemStack]s in the given slots.
 */
fun BlockMenu.getItems(vararg slots: Int): List<ItemStack> = getItems(slots.toList())

/**
 * Get all the [ItemStack]s in the given slots.
 */
fun BlockMenu.getItems(slots: List<Int>): List<ItemStack> = slots.mapNotNull { getItemInSlot(it) }.toList()

/**
 * Count the number of each item in the given slots.
 */
fun BlockMenu.countItems(vararg slots: Int): Map<ItemWrapper, Int> = countItems(slots.toList())

/**
 * Count the number of each item in the given slots.
 */
fun BlockMenu.countItems(slots: List<Int>): Map<ItemWrapper, Int> = getItems(slots).countItems()

/**
 * Consume the given amount of items based on the [RecipeChoice].
 */
fun BlockMenu.consumeChoice(choice: RecipeChoice, amount: Int, vararg slots: Int) {
    consumeChoice(choice, amount, slots.toList())
}

/**
 * Consume the given amount of items based on the [RecipeChoice].
 */
fun BlockMenu.consumeChoice(choice: RecipeChoice, amount: Int, slots: List<Int>) {
    FastMachines.debug("Consuming choice $choice with amount of $amount")
    var remainingAmount = amount

    // Iterate over each possible choice item until the requirement is fully met
    choice.choices.forEach choiceLoop@{ (choiceItem, choiceAMount) ->
        var totalRequiredAmount = choiceAMount * remainingAmount

        slots.forEach slotLoop@{ slot ->
            if (totalRequiredAmount <= 0) return@choiceLoop

            val itemInSlot = getItemInSlot(slot) ?: return@slotLoop
            if (!choiceItem.isSimilarTo(itemInSlot)) return@slotLoop

            val availableAmount = itemInSlot.amount
            val consumeNow = availableAmount.coerceAtMost(totalRequiredAmount)

            consumeItem(slot, consumeNow)
            totalRequiredAmount -= consumeNow
        }

        // Update remaining amount of recipes to fulfill
        remainingAmount = totalRequiredAmount / choiceAMount
    }
}
