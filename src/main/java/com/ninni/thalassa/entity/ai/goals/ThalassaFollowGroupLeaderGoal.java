package com.ninni.thalassa.entity.ai.goals;

import com.mojang.datafixers.DataFixUtils;
import com.ninni.thalassa.entity.common.ThalassaSchoolingFishEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class ThalassaFollowGroupLeaderGoal extends Goal {
    private final ThalassaSchoolingFishEntity fish;
    private int moveDelay;
    private int checkSurroundingDelay;

    public ThalassaFollowGroupLeaderGoal(ThalassaSchoolingFishEntity fish) {
        this.fish = fish;
        this.checkSurroundingDelay = this.getSurroundingSearchDelay(fish);
    }

    protected int getSurroundingSearchDelay(ThalassaSchoolingFishEntity fish) {
        return toGoalTicks(200 + fish.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canStart() {
        if (this.fish.hasOtherFishInGroup()) {
            return false;
        } else if (this.fish.hasLeader()) {
            return true;
        } else if (this.checkSurroundingDelay > 0) {
            --this.checkSurroundingDelay;
            return false;
        } else {
            this.checkSurroundingDelay = this.getSurroundingSearchDelay(this.fish);
            Predicate<ThalassaSchoolingFishEntity> predicate = (schoolingFishEntityx) -> schoolingFishEntityx.canHaveMoreFishInGroup() || !schoolingFishEntityx.hasLeader();
            List<? extends ThalassaSchoolingFishEntity> list = this.fish.world.getEntitiesByClass(this.fish.getClass(), this.fish.getBoundingBox().expand(8.0D, 8.0D, 8.0D), predicate);
            ThalassaSchoolingFishEntity schoolingFishEntity = DataFixUtils.orElse(list.stream().filter(ThalassaSchoolingFishEntity::canHaveMoreFishInGroup).findAny(), this.fish);
            schoolingFishEntity.pullInOtherFish(list.stream().filter((schoolingFishEntityx) -> !schoolingFishEntityx.hasLeader()));
            return this.fish.hasLeader();
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.fish.hasLeader() && this.fish.isCloseEnoughToLeader();
    }

    @Override
    public void start() {
        this.moveDelay = 0;
    }

    @Override
    public void stop() {
        this.fish.leaveGroup();
    }

    @Override
    public void tick() {
        if (--this.moveDelay <= 0) {
            this.moveDelay = this.getTickCount(10);
            this.fish.moveTowardLeader();
        }
    }
}

