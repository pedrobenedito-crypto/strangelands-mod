package com.strangelands.event;

import com.strangelands.StrangelandsMod;
import com.strangelands.registry.ModDimensions;
import com.strangelands.world.StrangeTeleporter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StrangelandsMod.MOD_ID)
public class ForgeBusEvents {

    /** The realm is floating islands over a void: pull people back to the spawn island instead of killing them. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        if (player.tickCount % 10 != 0) {
            return;
        }
        if (player.level().dimension().equals(ModDimensions.STRANGE_REALM) && player.getY() < 30.0D) {
            StrangeTeleporter.rescue(player);
        }
    }
}
