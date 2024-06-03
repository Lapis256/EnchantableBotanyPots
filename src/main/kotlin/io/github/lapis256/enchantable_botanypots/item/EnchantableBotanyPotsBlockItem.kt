package io.github.lapis256.enchantable_botanypots.item

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block


class EnchantableBotanyPotsBlockItem(block: Block, properties: Properties) : BlockItem(block, properties) {
    companion object {
        private val supportedEnchantments = setOf(Enchantments.BLOCK_EFFICIENCY, Enchantments.BLOCK_FORTUNE)
    }

    override fun isEnchantable(stack: ItemStack) = stack.count == 1
    override fun canApplyAtEnchantingTable(stack: ItemStack, enchantment: Enchantment) = stack.count == 1 && enchantment in supportedEnchantments
    override fun getEnchantmentValue() = 1
}
