package io.github.lapis256.enchantable_botanypots.integration

import com.ultramega.botanypotstiers.block.TieredBlockBotanyPot
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockEntityBotanyPot
import mcp.mobius.waila.api.*
import mcp.mobius.waila.api.config.IPluginConfig
import net.darkhax.botanypots.block.BlockBotanyPot


@WailaPlugin
class Jade : IWailaPlugin, IComponentProvider {

    override fun registerClient(registration: IWailaClientRegistration) {
        registration.registerComponentProvider(this, TooltipPosition.BODY, TieredBlockBotanyPot::class.java)
        registration.registerComponentProvider(this, TooltipPosition.BODY, BlockBotanyPot::class.java)
    }

    override fun appendTooltip(tooltip: ITooltip, accssor: BlockAccessor, config: IPluginConfig) {
        val enchantments = MixinImplBlockEntityBotanyPot.getEnchantments(accssor.blockEntity) ?: return
        enchantments.forEach { (e, l) -> tooltip.add(e.getFullname(l)) }
    }
}
