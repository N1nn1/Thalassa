package com.ninni.thalassa.client.model.entity;

import com.ninni.thalassa.entity.SeaBlanketEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class SeaBlanketEntityModel extends SinglePartEntityModel<SeaBlanketEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart rightTail;
    private final ModelPart rightTailTip;
    private final ModelPart leftTail;
    private final ModelPart leftTailTip;
    private final ModelPart rightAntenna;
    private final ModelPart leftAntenna;

    public SeaBlanketEntityModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.root = root;

        this.body       = root.getChild("body");

        this.leftAntenna       = body.getChild("leftAntenna");
        this.rightAntenna       = body.getChild("rightAntenna");
        this.leftWing       = body.getChild("leftWing");
        this.rightWing       = body.getChild("rightWing");
        this.leftTail       = body.getChild("leftTail");
        this.rightTail       = body.getChild("rightTail");

        this.leftTailTip       = leftTail.getChild("leftTailTip");

        this.rightTailTip       = rightTail.getChild("rightTailTip");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-4.0F, -1.0F, -6.0F, 8.0F, 2.0F, 12.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftWing = body.addChild(
            "leftWing",
            ModelPartBuilder.create()
                            .uv(-10, 14)
                            .mirrored(true)
                            .cuboid(-9.0F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightWing = body.addChild(
            "rightWing",
            ModelPartBuilder.create()
                            .uv(-10, 14)
                            .mirrored(false)
                            .cuboid(0.0F, 0.0F, -5.0F, 9.0F, 0.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightTail = body.addChild(
            "rightTail",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(2.5F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightTailTip = rightTail.addChild(
            "rightTailTip",
            ModelPartBuilder.create()
                            .uv(0, 6)
                            .mirrored(false)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftTail = body.addChild(
            "leftTail",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(true)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.5F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftTailTip = leftTail.addChild(
            "leftTailTip",
            ModelPartBuilder.create()
                            .uv(0, 6)
                            .mirrored(true)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightAntenna = body.addChild(
            "rightAntenna",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(2.0F, -1.0F, -5.0F, -1.0472F, 0.0F, 0.0F)
        );

        ModelPartData leftAntenna = body.addChild(
            "leftAntenna",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(true)
                            .cuboid(-1.0F, -4.0F, 0.0F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.0F, -1.0F, -5.0F, -1.0472F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 32);
    }

    @Override
    public void setAngles(SeaBlanketEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.0f;
        if (entity.isTouchingWater()) {
            this.body.pitch = headPitch * 0.015F;
            this.body.yaw = headYaw * 0.01F;
            this.body.yaw += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
            this.body.pitch += MathHelper.cos(animationProgress * speed * 0.1F) * degree * 0.4F * 0.25F;
        }
        this.leftWing.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 2.5F * 0.25F;
        this.rightWing.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * -2.5F * 0.25F;
        this.body.roll = MathHelper.cos(-1.0F + animationProgress * speed * 0.05F) * degree * 0.6F * 0.25F;
        this.leftTail.pitch = MathHelper.cos(-1.0F + animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
        this.leftTailTip.pitch = MathHelper.cos(-2.0F + animationProgress * speed * 0.1F) * degree * 1.0F * 0.25F;
        this.rightTail.pitch = MathHelper.cos(-1.5F + animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F;
        this.rightTailTip.pitch = MathHelper.cos(-2.5F + animationProgress * speed * 0.1F) * degree * 1.0F * 0.25F;
        this.rightAntenna.pitch = MathHelper.cos(-1.0F + animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F - 1.0F;
        this.leftAntenna.pitch = MathHelper.cos(-1.5F + animationProgress * speed * 0.1F) * degree * 0.8F * 0.25F - 1.0F;
        this.body.pivotY = MathHelper.cos(animationProgress * speed * 0.1F) * degree * 1F * 0.25F + 23.0F;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }


}