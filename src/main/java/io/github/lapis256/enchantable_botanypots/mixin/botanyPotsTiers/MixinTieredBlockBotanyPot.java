package io.github.lapis256.enchantable_botanypots.mixin.botanyPotsTiers;

import com.ultramega.botanypotstiers.block.TieredBlockBotanyPot;
import io.github.lapis256.enchantable_botanypots.mixin.bases.MixinBlock;
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockBotanyPot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Pseudo
@Mixin(TieredBlockBotanyPot.class)
public class MixinTieredBlockBotanyPot extends MixinBlock {
    @Override
    protected void enchantableBotanyPots$setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        MixinImplBlockBotanyPot.setPlacedBy(level, pos, stack);
    }
}
