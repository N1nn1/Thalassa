package com.ninni.thalassa.client.render;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.client.init.ThalassaEntityModelLayers;
import com.ninni.thalassa.client.model.entity.BlumpletEntityModel;
import com.ninni.thalassa.entity.BlumpletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class BlumpletEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<BlumpletEntity, BlumpletEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Thalassa.MOD_ID, "textures/entity/blumplet/blumplet.png");
    public static final Identifier TEXTURE_BLUMP = new Identifier(Thalassa.MOD_ID, "textures/entity/blumplet/blumplet_blump.png");

    public BlumpletEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlumpletEntityModel(ctx.getPart(ThalassaEntityModelLayers.BLUMPLET)), 0.2F);
    }

    @Override
    protected void setupTransforms(BlumpletEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        if (!entity.isTouchingWater()) {
            matrixStack.translate(0.1D, 0.15D, -0.05D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(BlumpletEntity entity) {
        return entity.hasBlump() ? TEXTURE_BLUMP : TEXTURE;

    }
}
