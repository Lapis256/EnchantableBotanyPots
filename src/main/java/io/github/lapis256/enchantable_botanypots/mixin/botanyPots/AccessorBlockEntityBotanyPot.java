package io.github.lapis256.enchantable_botanypots.mixin.botanyPots;

import com.ultramega.botanypotstiers.block.TieredBlockEntityBotanyPot;
import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;


@Pseudo
@Mixin(value = {BlockEntityBotanyPot.class, TieredBlockEntityBotanyPot.class}, remap = false)
public interface AccessorBlockEntityBotanyPot {
    @Accessor
    void setGrowthTime(int growthTime);

    @Accessor
    void setHarvestDelay(int harvestDelay);

    @Accessor
    void setExportDelay(int exportDelay);
}
