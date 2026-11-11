package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, StrangelandsMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE = FLUIDS.register("strange_liquid",
            () -> new ForgeFlowingFluid.Source(ModFluids.PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING = FLUIDS.register("flowing_strange_liquid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.PROPERTIES));

    public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.STRANGE_LIQUID_TYPE, SOURCE, FLOWING)
            .slopeFindDistance(3)
            .levelDecreasePerBlock(2)
            .block(ModBlocks.STRANGE_LIQUID_BLOCK)
            .bucket(ModItems.STRANGE_LIQUID_BUCKET);
}
