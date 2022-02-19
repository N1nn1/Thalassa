package com.ninni.thalassa.client.model.entity;

import com.ninni.thalassa.entity.SeaRugEntity;
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
public class SeaRugEntityModel extends SinglePartEntityModel<SeaRugEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;

    public SeaRugEntityModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.root = root;

        this.body       = root.getChild("body");

        this.leftAntenna       = body.getChild("leftAntenna");
        this.rightAntenna       = body.getChild("rightAntenna");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-2.5F, -1.0F, -5.5F, 5.0F, 1.0F, 11.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftAntenna = body.addChild(
            "leftAntenna",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(1.0F, -1.0F, -5.5F, -0.3927F, 0.0F, 0.0F)
        );

        ModelPartData rightAntenna = body.addChild(
            "rightAntenna",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(-1.0F, -1.0F, -5.5F, -0.3927F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 32, 16);
    }

    @Override
    public void setAngles(SeaRugEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1f;
        float degree = 1f;
        this.rightAntenna.pitch = MathHelper.cos(animationProgress * speed * 0.2F) * degree * -0.8F * 0.25F - 0.3927F ;
        this.leftAntenna.pitch = MathHelper.cos(-1F + animationProgress * speed * 0.2F) * degree * 0.8F * 0.25F - 0.3927F;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }


}