package io.github.lapis256.enchantable_botanypots.mixinImpl

import io.github.lapis256.enchantable_botanypots.extension.getItem
import io.github.lapis256.enchantable_botanypots.item.EnchantableBotanyPotsBlockItem
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper


object MixinImplPotRecipe {
    @JvmStatic
    fun applyEnchantments(inputs: CraftingContainer, stack: ItemStack): ItemStack {
        val pot = inputs.getItem(EnchantableBotanyPotsBlockItem::class) ?: return stack
        if (pot.isEnchanted) {
            EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(pot), stack)
        }
        return stack
    }
}
