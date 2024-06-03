package io.github.lapis256.enchantable_botanypots.extension

import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockEntityBotanyPot
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.entity.BlockEntity


fun BlockEntity.getEnchantments() = MixinImplBlockEntityBotanyPot.getEnchantments(this)
fun BlockEntity.setEnchantments(enchantments: Map<Enchantment, Int>) = MixinImplBlockEntityBotanyPot.setEnchantments(this, enchantments)
fun BlockEntity.getEnchantment(enchantment: Enchantment) = MixinImplBlockEntityBotanyPot.getEnchantment(this, enchantment)
