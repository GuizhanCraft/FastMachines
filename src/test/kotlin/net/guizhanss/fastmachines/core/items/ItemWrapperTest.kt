package net.guizhanss.fastmachines.core.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockbukkit.mockbukkit.MockBukkit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ItemWrapperTest {

    @BeforeEach
    fun beforeEach() {
        MockBukkit.mock()
    }

    @AfterEach
    fun afterEach() {
        MockBukkit.unmock()
    }

    @Test
    fun testConstructor() {
        val wrapper1 = ItemWrapper.of(ItemStack(Material.DIAMOND, 32))

        assertEquals(1, wrapper1.baseItem.amount)
    }

    @Test
    fun testEquals() {
        val wrapper1 = ItemWrapper.of(ItemStack(Material.DIAMOND, 32))
        val wrapper2 = ItemWrapper.of(ItemStack(Material.DIAMOND, 32))

        assertTrue(wrapper1 == wrapper2)

        val wrapper3 = ItemWrapper.of(ItemStack(Material.GOLD_INGOT, 32))
        val wrapper4 = ItemWrapper.of(ItemStack(Material.GOLD_INGOT, 16))

        assertTrue(wrapper3 == wrapper4)
    }
}
