package com.ninni.thalassa.entity;

import com.ninni.thalassa.entity.ai.goals.ThalassaEscapeDangerGoal;
import com.ninni.thalassa.entity.ai.goals.ThalassaFollowOwnerGoal;
import com.ninni.thalassa.entity.common.ThalassaFishEntity;
import com.ninni.thalassa.entity.common.ThalassaTameableFishEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class SeaBlanketEntity extends ThalassaTameableFishEntity {

    protected SeaBlanketEntity(EntityType<? extends ThalassaFishEntity> entityType, World world) {
        super(entityType, world);
        this.goalSelector.add(0, new ThalassaEscapeDangerGoal(this, 1.25F));
        this.goalSelector.add(6, new ThalassaFollowOwnerGoal(this, 1.2D, 10.0F, 2.0F));
    }

    public static DefaultAttributeContainer.Builder createSeaBlanketAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1D);
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.world.isClient) {
            boolean bl = this.isOwner(player) || this.isTamed() || item == Items.SEAGRASS && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            if (this.isTamed()) {
                if (this.isHealingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!this.isSilent()) {
                        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_GENERIC_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    }
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }

                    this.heal((float)item.getFoodComponent().getHunger());
                    return ActionResult.SUCCESS;
                }
                    ActionResult actionResult = super.interactMob(player, hand);
                    if ((!actionResult.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                        this.setStaying(!this.isStaying());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return ActionResult.SUCCESS;
                    }

                    return actionResult;


            } else if (item == Items.SEAGRASS) {
                if (!this.isSilent()) {
                    this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_GENERIC_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setStaying(true);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }

            return super.interactMob(player, hand);
        }
    }

    public boolean isHealingItem (ItemStack stack){
        Item item = stack.getItem();
        return item.isFood() && !Objects.requireNonNull(item.getFoodComponent()).isMeat();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
            if (this.isStaying()) {
                this.setVelocity(0.0D, -0.005D, 0.0D);
            }
        } else {
            super.travel(movementInput);
        }
    }
}
