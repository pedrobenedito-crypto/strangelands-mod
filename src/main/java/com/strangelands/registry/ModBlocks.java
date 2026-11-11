package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import com.strangelands.block.GlowBloomBlock;
import com.strangelands.block.StrangeLiquidBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, StrangelandsMod.MOD_ID);

    public static final RegistryObject<LiquidBlock> STRANGE_LIQUID_BLOCK = BLOCKS.register("strange_liquid_block",
            () -> new StrangeLiquidBlock(ModFluids.SOURCE,
                    BlockBehaviour.Properties.copy(Blocks.WATER).lightLevel(state -> 6)));

    public static final RegistryObject<Block> GLOW_BLOOM = BLOCKS.register("glow_bloom",
            () -> new GlowBloomBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .lightLevel(state -> 10)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));
}
