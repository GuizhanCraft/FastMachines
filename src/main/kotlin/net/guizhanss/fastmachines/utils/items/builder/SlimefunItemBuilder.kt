package net.guizhanss.fastmachines.utils.items.builder

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import net.guizhanss.fastmachines.FastMachines
import net.guizhanss.guizhanlib.kt.common.utils.RequiredProperty
import net.guizhanss.guizhanlib.kt.common.utils.getConstructor
import net.guizhanss.guizhanlib.kt.slimefun.items.builder.ItemRegistry
import net.guizhanss.guizhanlib.kt.slimefun.items.builder.MaterialType
import net.guizhanss.guizhanlib.minecraft.utils.ChatUtil
import org.bukkit.inventory.ItemStack
import java.util.logging.Level
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

/**
 * The main DSL class for constructing a [SlimefunItem], adapted for FastMachines.
 */
class SlimefunItemBuilder(private val registry: ItemRegistry) {

    var condition: Boolean by RequiredProperty(true)

    var id: String by RequiredProperty(setter = { it.uppercase() })
    var material: MaterialType by RequiredProperty()
    var amount: Int by RequiredProperty(1)

    var itemGroup: ItemGroup by RequiredProperty()
    var recipeType: RecipeType by RequiredProperty()
    var recipe: Array<out ItemStack?> by RequiredProperty(arrayOfNulls<ItemStack>(9))

    var editItem: (SlimefunItemStack) -> SlimefunItemStack = { it }

    private val extraLore = mutableListOf<String>()

    operator fun String.unaryPlus() {
        extraLore += ChatUtil.color(this)
    }

    fun <T : SlimefunItem> build(clazz: KClass<T>, vararg otherArgs: Any?): SlimefunItemStack {
        // SlimefunItemStack
        val name = FastMachines.localization.getItemName(id)
        val lore = FastMachines.localization.getItemLore(id).toMutableList() + extraLore
        val sfis = SlimefunItemStack(
            "${registry.prefix}$id",
            material.convert(),
            name,
            *lore.toTypedArray()
        )
        sfis.amount = amount

        // SlimefunItem
        val args = arrayOf(itemGroup, editItem(sfis), recipeType, recipe, *otherArgs)
        val constructor = clazz.getConstructor(*args)
            ?: error("No constructor found for ${clazz.simpleName} with arguments: ${args.joinToString()}")

        val item = try {
            constructor.call(*args)
        } catch (e: Exception) {
            FastMachines.log(Level.SEVERE, e, "Failed to create SlimefunItem")
            throw e
        }
        item.register(FastMachines.instance)
        return sfis
    }
}

inline fun <reified I : SlimefunItem> ItemRegistry.buildSlimefunItem(
    vararg otherArgs: Any?,
    crossinline builder: SlimefunItemBuilder.() -> Unit
) = PropertyDelegateProvider<Any?, ReadOnlyProperty<Any?, SlimefunItemStack>> { _, property ->
    val itemBuilder = SlimefunItemBuilder(this)
    itemBuilder.id = property.name.uppercase()
    itemBuilder.apply(builder)
    val item = itemBuilder.build(I::class, *otherArgs)
    ReadOnlyProperty { _, _ -> item }
}
