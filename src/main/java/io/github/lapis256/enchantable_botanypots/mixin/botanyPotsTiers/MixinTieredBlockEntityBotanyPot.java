package io.github.lapis256.enchantable_botanypots.mixin.botanyPotsTiers;

import com.llamalad7.mixinextras.sugar.Local;
import com.ultramega.botanypotstiers.block.TieredBlockEntityBotanyPot;
import io.github.lapis256.enchantable_botanypots.interfaces.IEnchantableBlockEntityBotanyPot;
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockEntityBotanyPot;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;


@Pseudo
@Mixin(TieredBlockEntityBotanyPot.class)
public abstract class MixinTieredBlockEntityBotanyPot implements IEnchantableBlockEntityBotanyPot {
    @Unique
    private final Object2IntLinkedOpenHashMap<Enchantment> enchantableBotanyPots$enchantments = new Object2IntLinkedOpenHashMap<>();

    @Unique
    public Map<Enchantment, Integer> enchantableBotanyPots$getEnchantments() {
        return enchantableBotanyPots$enchantments;
    }

    @Unique
    public void enchantableBotanyPots$setEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantableBotanyPots$enchantments.clear();
        enchantableBotanyPots$enchantments.putAll(enchantments);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void enchantableBotanyPots$saveAdditional(CompoundTag tag, CallbackInfo ci) {
        MixinImplBlockEntityBotanyPot.saveAdditional(((BlockEntity) (Object) this), tag);
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void enchantableBotanyPots$load(CompoundTag tag, CallbackInfo ci) {
        MixinImplBlockEntityBotanyPot.load(((BlockEntity) (Object) this), tag);
    }

    @Inject(method = "attemptAutoHarvest", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCount()I", shift = At.Shift.BEFORE, ordinal = 0))
    private void enchantableBotanyPots$attemptAutoHarvest(CallbackInfoReturnable<Boolean> cir, @Local ItemStack drop) {
        MixinImplBlockEntityBotanyPot.attemptAutoHarvest(((BlockEntity) (Object) this), drop);
    }

    @Redirect(method = "tickPot", at = @At(value = "FIELD", target = "Lcom/ultramega/botanypotstiers/block/TieredBlockEntityBotanyPot;harvestDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 1), remap = false)
    private static void enchantableBotanyPots$setHarvestDelay(TieredBlockEntityBotanyPot pot, int delay) {
        MixinImplBlockEntityBotanyPot.setHarvestDelay(pot, delay);
    }

    @Redirect(method = "tickPot", at = @At(value = "FIELD", target = "Lcom/ultramega/botanypotstiers/block/TieredBlockEntityBotanyPot;exportDelay:I", opcode = Opcodes.PUTFIELD, ordinal = 1), remap = false)
    private static void enchantableBotanyPots$setExportDelay(TieredBlockEntityBotanyPot pot, int delay) {
        MixinImplBlockEntityBotanyPot.setExportDelay(pot, delay);
    }

    @Redirect(method = "tickPot", at = @At(value = "FIELD", target = "Lcom/ultramega/botanypotstiers/block/TieredBlockEntityBotanyPot;growthTime:I", opcode = Opcodes.PUTFIELD, ordinal = 0), remap = false)
    private static void enchantableBotanyPots$setGrowthTime(TieredBlockEntityBotanyPot pot, int value) {
        MixinImplBlockEntityBotanyPot.setGrowthTime(pot, value);
    }
}
