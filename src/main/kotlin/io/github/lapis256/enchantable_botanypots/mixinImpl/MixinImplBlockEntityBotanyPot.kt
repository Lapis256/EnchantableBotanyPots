package io.github.lapis256.enchantable_botanypots.mixinImpl

import com.ultramega.botanypotstiers.block.TieredBlockEntityBotanyPot
import io.github.lapis256.enchantable_botanypots.Utils.buildEnchantmentsTag
import io.github.lapis256.enchantable_botanypots.extension.getEnchantment
import io.github.lapis256.enchantable_botanypots.interfaces.IEnchantableBlockEntityBotanyPot
import io.github.lapis256.enchantable_botanypots.mixin.botanyPots.AccessorBlockEntityBotanyPot
import net.darkhax.botanypots.block.BlockEntityBotanyPot
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.entity.BlockEntity


object MixinImplBlockEntityBotanyPot {
    private fun cast(blockEntity: BlockEntity) = blockEntity as? IEnchantableBlockEntityBotanyPot

    @JvmStatic
    fun getEnchantments(blockEntity: BlockEntity): MutableMap<Enchantment, Int>? =
        cast(blockEntity)?.`enchantableBotanyPots$getEnchantments`()

    @JvmStatic
    fun setEnchantments(blockEntity: BlockEntity, enchantments: Map<Enchantment, Int>) =
        cast(blockEntity)?.`enchantableBotanyPots$setEnchantments`(enchantments)

    @JvmStatic
    fun getEnchantment(blockEntity: BlockEntity, enchantment: Enchantment): Int =
        getEnchantments(blockEntity)?.get(enchantment) ?: 0

    @JvmStatic
    fun saveAdditional(pot: BlockEntity, tag: CompoundTag) {
        val enchantments = buildEnchantmentsTag(getEnchantments(pot) ?: return)
        tag.put("enchantments", enchantments)
    }

    @JvmStatic
    fun load(pot: BlockEntity, tag: CompoundTag) {
        val enchants = tag.getList("enchantments", Tag.TAG_COMPOUND.toInt())
        val enchantments = EnchantmentHelper.deserializeEnchantments(enchants)
        setEnchantments(pot, enchantments)
    }


    @JvmStatic
    fun attemptAutoHarvest(pot: BlockEntity, drop: ItemStack) {
        drop.count *= pot.getEnchantment(Enchantments.BLOCK_FORTUNE) + 1
    }

    @JvmStatic
    fun setHarvestDelay(pot: BlockEntityBotanyPot, delay: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setHarvestDelay(delay / efficiency)
    }

    @JvmStatic
    fun setHarvestDelay(pot: TieredBlockEntityBotanyPot, delay: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setHarvestDelay(delay / (pot.tier.speed * efficiency))
    }

    @JvmStatic
    fun setExportDelay(pot: BlockEntityBotanyPot, delay: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setExportDelay(delay / efficiency)
    }

    @JvmStatic
    fun setExportDelay(pot: TieredBlockEntityBotanyPot, delay: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setExportDelay(delay / (pot.tier.speed * efficiency))
    }

    @JvmStatic
    fun setGrowthTime(pot: BlockEntityBotanyPot, value: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setGrowthTime(value - 1 + efficiency)
    }

    @JvmStatic
    fun setGrowthTime(pot: TieredBlockEntityBotanyPot, value: Int) {
        val efficiency = pot.getEnchantment(Enchantments.BLOCK_EFFICIENCY) + 1
        (pot as AccessorBlockEntityBotanyPot).setGrowthTime((value - 1) + pot.tier.speed * efficiency)
    }
}
