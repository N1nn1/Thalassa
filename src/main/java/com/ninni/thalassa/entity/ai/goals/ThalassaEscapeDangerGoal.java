package com.ninni.thalassa.entity.ai.goals;

import com.ninni.thalassa.entity.common.ThalassaTameableFishEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ThalassaEscapeDangerGoal extends Goal {
    protected final ThalassaTameableFishEntity mob;
    protected final double speed;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected boolean active;

    public ThalassaEscapeDangerGoal(ThalassaTameableFishEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mob.isStaying()) {
            return false;
        }
        if (this.mob.getAttacker() == null && !this.mob.isOnFire()) {
            return false;
        } else {
            if (this.mob.isOnFire()) {
                BlockPos blockPos = this.locateClosestWater(this.mob.world, this.mob, 5);
                if (blockPos != null) {
                    this.targetX = blockPos.getX();
                    this.targetY = blockPos.getY();
                    this.targetZ = blockPos.getZ();
                    return true;
                }
            }

            return this.findTarget();
        }
    }

    protected boolean findTarget() {
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 5, 4);
        if (vec3d == null) {
            return false;
        } else {
            this.targetX = vec3d.x;
            this.targetY = vec3d.y;
            this.targetZ = vec3d.z;
            return true;
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        this.active = true;
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public boolean shouldContinue() {
        if (this.mob.isStaying()) {
            return false;
        } else {
            return !this.mob.getNavigation().isIdle();
        }
    }

    @Nullable
    protected BlockPos locateClosestWater(BlockView blockView, Entity entity, int rangeX) {
        BlockPos blockPos = entity.getBlockPos();
        return !blockView.getBlockState(blockPos).getCollisionShape(blockView, blockPos).isEmpty() ? null : BlockPos.findClosest(entity.getBlockPos(), rangeX, 1, (blockPosx) -> blockView.getFluidState(blockPosx).isIn(FluidTags.WATER)).orElse(null);
    }
}
