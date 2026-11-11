package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import com.strangelands.fluid.StrangeFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, StrangelandsMod.MOD_ID);

    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final RegistryObject<FluidType> STRANGE_LIQUID_TYPE = FLUID_TYPES.register("strange_liquid",
            () -> new StrangeFluidType(WATER_STILL, WATER_FLOW, WATER_OVERLAY, 0xDDB04DFF,
                    FluidType.Properties.create()
                            .descriptionId("block.strangelands.strange_liquid_block")
                            .canSwim(true)
                            .canDrown(false)
                            .fallDistanceModifier(0.0F)
                            .lightLevel(6)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
}
