package io.github.lapis256.enchantable_botanypots.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.lapis256.enchantable_botanypots.Utils;
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplPotRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = ShapedRecipe.class)
public abstract class MixinShapedRecipe {
    @Unique
    boolean enchantableBotanyPots$isSupported = false;

    @Shadow
    @Final
    private ResourceLocation id;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void enchantableBotanyPots$init(CallbackInfo ci) {
        enchantableBotanyPots$isSupported = Utils.supportedNamespace(id);
    }

    @ModifyReturnValue(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"))
    private ItemStack enchantableBotanyPots$applyEnchantments(ItemStack original, CraftingContainer inputs) {
        return enchantableBotanyPots$isSupported ? MixinImplPotRecipe.applyEnchantments(inputs, original) : original;
    }
}
