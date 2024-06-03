package io.github.lapis256.enchantable_botanypots

import net.minecraft.nbt.ListTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper


object Utils {
    private val SUPPOERTED = setOf("botanypots", "botanypotstiers")

    @JvmStatic
    fun supportedNamespace(namespace: String) = namespace in SUPPOERTED

    @JvmStatic
    fun supportedNamespace(path: ResourceLocation) = supportedNamespace(path.namespace)

    @JvmStatic
    fun buildEnchantmentsTag(enchantments: Map<Enchantment, Int>) = ListTag().apply {
        enchantments.forEach { (enchantment, level) ->
            add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId(enchantment), level))
        }
    }
}
