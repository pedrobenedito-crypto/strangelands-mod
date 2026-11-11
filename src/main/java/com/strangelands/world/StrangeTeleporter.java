package com.strangelands.world;

import com.strangelands.block.StrangeLiquidBlock;
import com.strangelands.registry.ModBlocks;
import com.strangelands.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

/**
 * Sends players between the overworld (or wherever they stepped in the liquid) and the Strange Realm.
 * The realm has a small spawn island with a pool of the liquid that leads back.
 */
public final class StrangeTeleporter {
    private static final String COOLDOWN_KEY = "strangelands_cooldown";
    private static final String ORIGIN_KEY = "strangelands_origin";

    /** Centre of the arrival island (top layer of grass is at this Y). */
    public static final BlockPos PLATFORM = new BlockPos(0, 96, 0);

    private StrangeTeleporter() {
    }

    public static void tryTeleport(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        long now = player.level().getGameTime();
        CompoundTag data = player.getPersistentData();
        if (data.getLong(COOLDOWN_KEY) > now) {
            return;
        }

        ServerLevel current = player.serverLevel();
        if (current.dimension().equals(ModDimensions.STRANGE_REALM)) {
            goHome(player, server, data, now);
        } else {
            goToRealm(player, server, current, data, now);
        }
    }

    private static void goToRealm(ServerPlayer player, MinecraftServer server, ServerLevel current,
                                  CompoundTag data, long now) {
        ServerLevel realm = server.getLevel(ModDimensions.STRANGE_REALM);
        if (realm == null) {
            player.displayClientMessage(Component.translatable("message.strangelands.no_dimension"), true);
            data.putLong(COOLDOWN_KEY, now + 100L);
            return;
        }

        CompoundTag origin = new CompoundTag();
        origin.putString("dim", current.dimension().location().toString());
        origin.putDouble("x", player.getX());
        origin.putDouble("y", player.getY());
        origin.putDouble("z", player.getZ());
        data.put(ORIGIN_KEY, origin);

        ensurePlatform(realm);

        current.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);
        Vec3 arrival = arrivalPoint();
        player.teleportTo(realm, arrival.x, arrival.y, arrival.z, player.getYRot(), 0.0F);
        player.fallDistance = 0.0F;
        realm.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.2F);
        data.putLong(COOLDOWN_KEY, now + 100L);
    }

    private static void goHome(ServerPlayer player, MinecraftServer server, CompoundTag data, long now) {
        CompoundTag origin = data.getCompound(ORIGIN_KEY);
        ServerLevel target = null;
        if (origin.contains("dim")) {
            ResourceLocation id = ResourceLocation.tryParse(origin.getString("dim"));
            if (id != null) {
                target = server.getLevel(ResourceKey.create(Registries.DIMENSION, id));
            }
        }

        double x;
        double y;
        double z;
        if (target == null || !origin.contains("x")) {
            // No remembered origin (e.g. you died in the realm): go to the world spawn.
            target = server.overworld();
            BlockPos spawn = target.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, target.getSharedSpawnPos());
            x = spawn.getX() + 0.5D;
            y = spawn.getY();
            z = spawn.getZ() + 0.5D;
        } else {
            x = origin.getDouble("x");
            y = origin.getDouble("y");
            z = origin.getDouble("z");
            // Don't land back inside the liquid, or we would bounce straight back.
            BlockPos.MutableBlockPos pos = BlockPos.containing(x, y, z).mutable();
            int guard = 0;
            while (guard++ < 24 && target.getBlockState(pos).getBlock() instanceof StrangeLiquidBlock) {
                pos.move(Direction.UP);
            }
            y = pos.getY();
        }

        ServerLevel from = player.serverLevel();
        from.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);
        player.teleportTo(target, x, y, z, player.getYRot(), player.getXRot());
        player.fallDistance = 0.0F;
        target.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.2F);
        data.putLong(COOLDOWN_KEY, now + 100L);
    }

    /** Called when someone falls into the void of the realm. */
    public static void rescue(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        ServerLevel realm = server.getLevel(ModDimensions.STRANGE_REALM);
        if (realm == null) {
            return;
        }
        ensurePlatform(realm);
        Vec3 arrival = arrivalPoint();
        player.teleportTo(realm, arrival.x, arrival.y, arrival.z, player.getYRot(), 0.0F);
        player.fallDistance = 0.0F;
        player.setDeltaMovement(Vec3.ZERO);
        player.hurtMarked = true;
    }

    private static Vec3 arrivalPoint() {
        return new Vec3(PLATFORM.getX() - 1.5D, PLATFORM.getY() + 1.0D, PLATFORM.getZ() + 0.5D);
    }

    /** Builds the small spawn island (once) with a pool of the liquid for going home. */
    private static void ensurePlatform(ServerLevel realm) {
        BlockState liquid = ModBlocks.STRANGE_LIQUID_BLOCK.get().defaultBlockState();
        BlockPos probe = PLATFORM.offset(3, 0, 0);
        if (realm.getBlockState(probe).is(ModBlocks.STRANGE_LIQUID_BLOCK.get())) {
            return;
        }

        BlockState grass = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState dirt = Blocks.DIRT.defaultBlockState();
        BlockState stone = Blocks.STONE.defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        for (int dx = -5; dx <= 5; dx++) {
            for (int dz = -5; dz <= 5; dz++) {
                int r2 = dx * dx + dz * dz;
                if (r2 > 30) {
                    continue;
                }
                for (int dy = 1; dy <= 6; dy++) {
                    realm.setBlock(PLATFORM.offset(dx, dy, dz), air, 2);
                }
                boolean pool = dx >= 2 && dx <= 4 && dz >= -1 && dz <= 1;
                realm.setBlock(PLATFORM.offset(dx, 0, dz), pool ? liquid : grass, 2);
                if (r2 <= 24) {
                    realm.setBlock(PLATFORM.offset(dx, -1, dz), dirt, 2);
                }
                if (r2 <= 16) {
                    realm.setBlock(PLATFORM.offset(dx, -2, dz), dirt, 2);
                }
                if (r2 <= 9) {
                    realm.setBlock(PLATFORM.offset(dx, -3, dz), stone, 2);
                }
                if (r2 <= 3) {
                    realm.setBlock(PLATFORM.offset(dx, -4, dz), stone, 2);
                }
            }
        }
    }
}
