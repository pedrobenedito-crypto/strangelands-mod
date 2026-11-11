package com.strangelands.block;

import com.strangelands.world.StrangeTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

/** The strange liquid. Stepping into it sends the player to the other side. */
public class StrangeLiquidBlock extends LiquidBlock {
    public StrangeLiquidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            StrangeTeleporter.tryTeleport(player);
        }
        super.entityInside(state, level, pos, entity);
    }
}
