package io.github.lapis256.enchantable_botanypots.integration

import io.github.lapis256.enchantable_botanypots.EnchantableBotanyPots
import io.github.lapis256.enchantable_botanypots.mixinImpl.MixinImplBlockEntityBotanyPot
import mcjty.theoneprobe.api.*
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import java.util.function.Function


private const val TOP_MOD_ID = "theoneprobe"

@Suppress("unused_parameter")
fun registerTheOneProbeIntegration(event: InterModEnqueueEvent) {
    if (ModList.get().isLoaded(TOP_MOD_ID).not()) {
        return
    }

    val theOneProbe = object : IProbeInfoProvider {
        override fun getID() = EnchantableBotanyPots.rl("top")

        override fun addProbeInfo(mode: ProbeMode, info: IProbeInfo, player: Player, level: Level, state: BlockState, hitData: IProbeHitData) {
            val blockEntity = level.getBlockEntity(hitData.pos) ?: return
            val enchantments = MixinImplBlockEntityBotanyPot.getEnchantments(blockEntity) ?: return
            enchantments.forEach { (e, l) -> info.text(e.getFullname(l)) }
        }
    }

    InterModComms.sendTo(TOP_MOD_ID, "getTheOneProbe") {
        Function<ITheOneProbe, Void> {
            it.registerProvider(theOneProbe)
            null
        }
    }
}
