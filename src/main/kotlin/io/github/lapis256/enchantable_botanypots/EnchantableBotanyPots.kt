package io.github.lapis256.enchantable_botanypots

import io.github.lapis256.enchantable_botanypots.integration.registerTheOneProbeIntegration
import io.github.lapis256.enchantable_botanypots.item.EnchantableBotanyPotsBlockItem
import io.github.lapis256.enchantable_botanypots.mixin.AccessorItem
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.fml.common.Mod
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(EnchantableBotanyPots.MOD_ID)
object EnchantableBotanyPots {
    const val MOD_ID = "enchantable_botanypots"
    const val MOD_NAME = "Enchantable BotanyPots"

    @JvmField
    val LOG: Logger = LoggerFactory.getLogger(MOD_NAME)

    init {
        MOD_BUS.addGenericListener(Item::class.java, EventPriority.LOWEST, ::registerItems)
        MOD_BUS.addListener(::registerTheOneProbeIntegration)
    }

    fun rl(path: String) = ResourceLocation(MOD_ID, path)

    private fun registerItems(event: Register<Item>) {
        val potItems = event.registry.entries
            .mapNotNull { (key, item) ->
                if (Utils.supportedNamespace(key.location())) EnchantableBotanyPotsBlockItem(
                    (item as BlockItem).block, Properties().tab((item as AccessorItem).category)
                ).setRegistryName(key.location())
                else null
            }

        LOG.info("Registering ${potItems.size} BotanyPots items")
        event.registry.registerAll(*potItems.toTypedArray())
    }
}
