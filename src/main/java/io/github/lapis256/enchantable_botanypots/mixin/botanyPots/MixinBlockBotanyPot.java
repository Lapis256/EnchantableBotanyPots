package io.github.lapis256.enchantable_botanypots.mixin.botanyPots;

import io.github.lapis256.enchantable_botanypots.mixin.bases.MixinBlock;
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockBotanyPot;
import net.darkhax.botanypots.block.BlockBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BlockBotanyPot.class)
public class MixinBlockBotanyPot extends MixinBlock {
    @Override
    protected void enchantableBotanyPots$setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        MixinImplBlockBotanyPot.setPlacedBy(level, pos, stack);
    }
}
