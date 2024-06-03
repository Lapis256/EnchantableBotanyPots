package io.github.lapis256.enchantable_botanypots.extension

import net.minecraft.world.Container
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import kotlin.reflect.KClass


inline fun Container.forEach(action: (ItemStack, Int) -> Unit) = (0 until containerSize).forEach { i -> action(getItem(i), i) }

inline fun Container.forEach(action: (ItemStack) -> Unit) = forEach { item, _ -> action(item) }

fun <ITEM : Item> Container.getItem(itemClass: KClass<ITEM>): ItemStack? {
    forEach { item ->
        if (itemClass.isInstance(item.item)) {
            return item
        }
    }
    return null
}
