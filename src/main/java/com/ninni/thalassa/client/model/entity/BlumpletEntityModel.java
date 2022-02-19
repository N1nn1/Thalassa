package com.ninni.thalassa.client.model.entity;

import com.ninni.thalassa.entity.BlumpletEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class BlumpletEntityModel extends SinglePartEntityModel<BlumpletEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart dorsalFin;
    private final ModelPart analFin;

    public BlumpletEntityModel(ModelPart root) {
        this.root = root;

        this.body       = root.getChild("body");

        this.tail       = body.getChild("tail");
        this.dorsalFin       = body.getChild("dorsalFin");
        this.analFin       = body.getChild("analFin");

    }

    public static TexturedModelData getTexturedModelData() {

    ModelData data = new ModelData();
    ModelPartData root = data.getRoot();

    ModelPartData body = root.addChild(
        "body",
        ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored(false)
                        .cuboid(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F))
                        .uv(0, 9)
                        .cuboid(-2.0F, -2.5F, -4.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)),
        ModelTransform.of(0.0F, 22.5F, 0.0F, 0.0F, 0.0F, 0.0F)
    );

    ModelPartData tail = body.addChild(
        "tail",
        ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored(false)
                        .cuboid(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F, new Dilation(0.0F)),
        ModelTransform.of(0.0F, 0.5F, 3.0F, 0.0F, 0.0F, 0.0F)
    );

    ModelPartData dorsalFin = body.addChild(
        "dorsalFin",
        ModelPartBuilder.create()
                        .uv(0, 7)
                        .mirrored(false)
                        .cuboid(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 2.0F, new Dilation(0.0F)),
        ModelTransform.of(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.0F)
    );

    ModelPartData analFin = body.addChild(
        "analFin",
        ModelPartBuilder.create()
                        .uv(12, 0)
                        .mirrored(false)
                        .cuboid(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 2.0F, new Dilation(0.0F)),
        ModelTransform.of(0.0F, -1.5F, 1.0F, 0.0F, 0.0F, 0.0F)
    );

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(BlumpletEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 2.5f;
        float degree = 1.5f;
        if (entity.isTouchingWater()) {
            this.body.pitch = headPitch * 0.015F;
            this.body.yaw = headYaw * 0.01F;
            this.body.yaw += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        }
        this.body.roll = MathHelper.cos(-1F + animationProgress * speed * 0.2F) * degree * 0.2F * 0.25F;
        this.analFin.roll = MathHelper.cos(3F + animationProgress * speed * 0.2F) * degree * 1.6F * 0.25F;
        this.dorsalFin.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 1.6F * 0.25F;
        this.tail.yaw = MathHelper.cos(limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
