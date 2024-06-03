package io.github.lapis256.enchantable_botanypots.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockBotanyPot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Mixin(LootTable.class)
public class MixinLootTable {
    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Ljava/util/List;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    private void enchantableBotanyPots$getRandomItems(LootContext ctx, CallbackInfoReturnable<?> cir, @Local List<ItemStack> list) {
        if (!ctx.hasParam(LootContextParams.BLOCK_ENTITY)) {
            return;
        }
        MixinImplBlockBotanyPot.applyEnchantmentsToItems(list, ctx.getParam(LootContextParams.BLOCK_ENTITY));
    }
}
