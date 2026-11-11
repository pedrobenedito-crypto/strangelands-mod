package com.strangelands.event;

import com.strangelands.StrangelandsMod;
import com.strangelands.entity.SkyrayEntity;
import com.strangelands.entity.SporelingEntity;
import com.strangelands.registry.ModEntities;
import com.strangelands.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StrangelandsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPORELING.get(), SporelingEntity.createAttributes().build());
        event.put(ModEntities.SKYRAY.get(), SkyrayEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.STRANGE_LIQUID_BUCKET);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModItems.GLOW_BLOOM);
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SPORELING_SPAWN_EGG);
            event.accept(ModItems.SKYRAY_SPAWN_EGG);
        }
    }
}
