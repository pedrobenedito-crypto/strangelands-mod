package com.strangelands;

import com.strangelands.registry.ModBlocks;
import com.strangelands.registry.ModEntities;
import com.strangelands.registry.ModFluidTypes;
import com.strangelands.registry.ModFluids;
import com.strangelands.registry.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StrangelandsMod.MOD_ID)
public class StrangelandsMod {
    public static final String MOD_ID = "strangelands";

    public StrangelandsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModFluidTypes.FLUID_TYPES.register(modBus);
        ModFluids.FLUIDS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
    }
}
