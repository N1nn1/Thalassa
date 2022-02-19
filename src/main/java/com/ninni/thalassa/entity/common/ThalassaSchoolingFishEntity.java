package com.ninni.thalassa.entity.common;

import com.ninni.thalassa.entity.ai.goals.ThalassaFollowGroupLeaderGoal;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class ThalassaSchoolingFishEntity extends ThalassaFishEntity {
    @Nullable
    private ThalassaSchoolingFishEntity leader;
    private int groupSize = 1;

    protected ThalassaSchoolingFishEntity(EntityType<? extends ThalassaSchoolingFishEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
        this.stepHeight = 0;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new ThalassaFollowGroupLeaderGoal(this));
    }

    @Override
    public int getLimitPerChunk() {
        return this.getMaxGroupSize();
    }

    public int getMaxGroupSize() {
        return super.getLimitPerChunk();
    }

    protected boolean hasSelfControl() {
        return !this.hasLeader();
    }

    public boolean hasLeader() {
        return this.leader != null && this.leader.isAlive();
    }

    public ThalassaSchoolingFishEntity joinGroupOf(ThalassaSchoolingFishEntity groupLeader) {
        this.leader = groupLeader;
        groupLeader.increaseGroupSize();
        return groupLeader;
    }

    public void leaveGroup() {
        assert this.leader != null;
        this.leader.decreaseGroupSize();
        this.leader = null;
    }

    private void increaseGroupSize() {
        ++this.groupSize;
    }

    private void decreaseGroupSize() {
        --this.groupSize;
    }

    public boolean canHaveMoreFishInGroup() {
        return this.hasOtherFishInGroup() && this.groupSize < this.getMaxGroupSize();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasOtherFishInGroup() && this.world.random.nextInt(200) == 1) {
            List<? extends ThalassaFishEntity> list = this.world.getNonSpectatingEntities(this.getClass(), this.getBoundingBox().expand(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.groupSize = 1;
            }
        }

    }

    public boolean hasOtherFishInGroup() {
        return this.groupSize > 1;
    }

    public boolean isCloseEnoughToLeader() {
        return this.squaredDistanceTo(this.leader) <= 121.0D;
    }

    public void moveTowardLeader() {
        if (this.hasLeader()) {
            this.getNavigation().startMovingTo(this.leader, 1.0D);
        }

    }

    public void pullInOtherFish(Stream<? extends ThalassaSchoolingFishEntity> fish) {
        fish.limit((this.getMaxGroupSize() - this.groupSize)).filter((fishx) -> fishx != this).forEach((fishx) -> fishx.joinGroupOf(this));
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        if (entityData == null) {
            entityData = new ThalassaSchoolingFishEntity.FishData(this);
        } else {
            this.joinGroupOf(((ThalassaSchoolingFishEntity.FishData)entityData).leader);
        }

        return entityData;
    }

    public record FishData(ThalassaSchoolingFishEntity leader) implements EntityData {}
}
