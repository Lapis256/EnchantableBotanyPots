package io.github.lapis256.enchantable_botanypots.mixinImpl

import io.github.lapis256.enchantable_botanypots.extension.getEnchantments
import io.github.lapis256.enchantable_botanypots.extension.setEnchantments
import io.github.lapis256.enchantable_botanypots.item.EnchantableBotanyPotsBlockItem
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity


object MixinImplBlockBotanyPot {
    @JvmStatic
    fun setPlacedBy(level: Level, pos: BlockPos, stack: ItemStack) {
        val blockEntity = level.getBlockEntity(pos) ?: return
        blockEntity.setEnchantments(EnchantmentHelper.getEnchantments(stack))
    }

    @JvmStatic
    fun applyEnchantmentsToItems(items: List<ItemStack>, blockEntity: BlockEntity): List<ItemStack> {
        val enchantments = blockEntity.getEnchantments() ?: return items
        return items.map {
            if (it.item is EnchantableBotanyPotsBlockItem) EnchantmentHelper.setEnchantments(enchantments, it)
            it
        }
    }
}
