package io.github.lapis256.enchantable_botanypots.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(Item.class)
public interface AccessorItem {
    @Accessor
    CreativeModeTab getCategory();
}
