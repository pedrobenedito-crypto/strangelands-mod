package com.strangelands.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

/** A big glowing ray that glides around the red sky of the realm. */
public class SkyrayEntity extends PathfinderMob {

    public SkyrayEntity(EntityType<? extends SkyrayEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SkyWanderGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        // Flying creature: never takes fall damage.
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMETHYST_BLOCK_CHIME;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ALLAY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ALLAY_DEATH;
    }

    /** Picks a random point in the air, preferring higher altitudes. */
    static class SkyWanderGoal extends Goal {
        private final SkyrayEntity mob;

        SkyWanderGoal(SkyrayEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl control = this.mob.getMoveControl();
            if (!control.hasWanted()) {
                return true;
            }
            double dx = control.getWantedX() - this.mob.getX();
            double dy = control.getWantedY() - this.mob.getY();
            double dz = control.getWantedZ() - this.mob.getZ();
            double dist = dx * dx + dy * dy + dz * dz;
            return dist < 1.0D || dist > 3600.0D;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource random = this.mob.getRandom();
            double x = this.mob.getX() + (random.nextFloat() * 2.0F - 1.0F) * 14.0F;
            double y = this.mob.getY() + (random.nextFloat() * 2.0F - 1.0F) * 5.0F;
            if (this.mob.getY() < 90.0D) {
                y += 6.0D;
            }
            y = Mth.clamp(y, 70.0D, 120.0D);
            double z = this.mob.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 14.0F;
            this.mob.getMoveControl().setWantedPosition(x, y, z, 1.0D);
        }
    }
}
