package com.ninni.thalassa.entity;

import com.ninni.thalassa.entity.ai.goals.ThalassaFollowGroupLeaderGoal;
import com.ninni.thalassa.entity.common.ThalassaSchoolingFishEntity;
import com.ninni.thalassa.item.ThalassaItems;
import com.ninni.thalassa.sound.ThalassaSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlumpletEntity extends ThalassaSchoolingFishEntity {
    private static final TrackedData<Byte> BLUMPLET_FLAGS = DataTracker.registerData(BlumpletEntity.class, TrackedDataHandlerRegistry.BYTE);

    public BlumpletEntity(EntityType<? extends ThalassaSchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new TemptGoal(this, 1.1D, Ingredient.ofItems(Items.SEAGRASS), false));
        this.goalSelector.add(1, new ThalassaFollowGroupLeaderGoal(this));
        this.goalSelector.add(2, new BlumpletEntity.SwimToRandomPlaceGoal(this));
    }

    public static DefaultAttributeContainer.Builder createBlumpletAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.75D);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        //TODO make this good
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
            this.sheared(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.SHEAR, player);
            if (!this.world.isClient) {
                itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
            }
            return ActionResult.success(world.isClient);
        } else if (itemStack.isOf(Items.SEAGRASS) && !this.hasBlump()) {
            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.6F, 1F);

            if (random.nextFloat() > 0.9F) {
                this.setHasBlump(true);
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1F, 1F);
            }

            Vec3d vec3d = this.getBoundingBox().getCenter();
            Random random = this.world.getRandom();
            for(int i = 0; i < 10; ++i) {
                double velX = random.nextGaussian() * 0.075D;
                double velY = random.nextGaussian() * 0.075D;
                double velZ = random.nextGaussian() * 0.075D;
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, player.getStackInHand(hand)), vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
            }

            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }

            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BLUMPLET_FLAGS, (byte)16);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Blump", this.hasBlump());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Blump")) {
            this.setHasBlump(nbt.getBoolean("Blump"));
        }

    }

    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        if (!this.world.isClient()) {
            this.setHasBlump(false);
            this.dropStack(new ItemStack(ThalassaItems.BLUMP), 0.5F);
        }

    }

    public boolean isShearable() {
        return this.isAlive() && this.hasBlump();
    }

    public boolean hasBlump() {
        return (this.dataTracker.get(BLUMPLET_FLAGS) & 16) != 0;
    }

    public void setHasBlump(boolean hasBlump) {
        byte b = this.dataTracker.get(BLUMPLET_FLAGS);
        if (hasBlump) {
            this.dataTracker.set(BLUMPLET_FLAGS, (byte)(b | 16));
        } else {
            this.dataTracker.set(BLUMPLET_FLAGS, (byte)(b & -17));
        }

    }

    @Override
    public int getMaxGroupSize() {
        return 20;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ThalassaSoundEvents.ENTITY_BLUMPLET_HURT;
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ThalassaSoundEvents.ENTITY_BLUMPLET_DEATH;
    }
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ThalassaSoundEvents.ENTITY_BLUMPLET_AMBIENT;
    }

    @Override
    protected float getSoundVolume() {
        return 1.25F;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (0.55F * this.getStandingEyeHeight()), (this.getWidth() * 0.4F));
    }

    private static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final BlumpletEntity fish;

        public SwimToRandomPlaceGoal(BlumpletEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }
}
