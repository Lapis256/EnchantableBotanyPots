package io.github.lapis256.enchantable_botanypots.interfaces;

import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;


public interface IEnchantableBlockEntityBotanyPot {
    Map<Enchantment, Integer> enchantableBotanyPots$getEnchantments();

    void enchantableBotanyPots$setEnchantments(Map<Enchantment, Integer> enchantments);
}
