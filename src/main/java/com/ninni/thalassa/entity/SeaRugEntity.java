package com.ninni.thalassa.entity;

import com.ninni.thalassa.entity.enums.SeaRugVariant;
import com.ninni.thalassa.sound.ThalassaSoundEvents;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SeaRugEntity extends WaterCreatureEntity {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(SeaRugEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> SQUEAK_TICKS = DataTracker.registerData(SeaRugEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected SeaRugEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        stepHeight = 1;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 0.5));
        this.goalSelector.add(6, new SwimAroundGoal(this, 1D, 10));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData data, @Nullable NbtCompound nbt) {
        this.setVariant(SeaRugVariant.values()[this.random.nextInt(SeaRugVariant.values().length)]);
        return super.initialize(world, difficulty, spawnReason, data, nbt);
    }

    public static DefaultAttributeContainer.Builder createSeaRugAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D)
                        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.05D);
    }

    public int getSqueakTicks() {
        return this.dataTracker.get(SQUEAK_TICKS);
    }
    public void setSqueakTicks(int squeak_ticks) {
        this.dataTracker.set(SQUEAK_TICKS, squeak_ticks);
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        Vec3d vec3d = this.getBoundingBox().getCenter();
        Random random = this.world.getRandom();
        if (this.getSqueakTicks() == 0) {
            this.playSound(ThalassaSoundEvents.ENTITY_SEA_RUG_SQUEAK, 1F, 1F);
            this.setSqueakTicks(20);
            for(int i = 0; i < 10; ++i) {
                double velX = random.nextGaussian() * 0.075D;
                double velY = random.nextGaussian() * 0.075D;
                double velZ = random.nextGaussian() * 0.075D;
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.SLIME_BALL)), vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isCollidable() {
        return this.isAlive();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(getSqueakTicks() > 0) {
            this.setSqueakTicks(this.getSqueakTicks() - 1);
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        if (!this.isAiDisabled()) {
            this.tickAir(i);
        }

    }

    @Override
    public int getMaxAir() {
        return 900;
    }

    protected void tickAir(int air) {
        if (this.isAlive() && !this.isWet()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(DamageSource.DRYOUT, 1.0F);
            }
        } else {
            this.setAir(this.getMaxAir());
        }

    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.8D));
            this.setVelocity(this.getVelocity().add(0.0D, -0.25D, 0.0D));
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SQUEAK_TICKS, 0);
        this.dataTracker.startTracking(VARIANT, SeaRugVariant.getDefault().toString());
    }

    public SeaRugVariant getVariant() {
        return SeaRugVariant.valueOf(this.dataTracker.get(VARIANT));
    }
    public void setVariant(SeaRugVariant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Variant", this.getVariant().toString());
        nbt.putInt("SqueakTicks", this.getSqueakTicks());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(SeaRugVariant.valueOf(nbt.getString("Variant")));
        this.setSqueakTicks(nbt.getInt("SqueakTicks"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ThalassaSoundEvents.ENTITY_SEA_RUG_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ThalassaSoundEvents.ENTITY_SEA_RUG_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 3.0F;
    }
}
