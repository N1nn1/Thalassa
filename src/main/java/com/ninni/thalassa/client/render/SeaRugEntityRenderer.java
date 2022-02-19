package com.ninni.thalassa.client.render;

import com.ninni.thalassa.client.init.ThalassaEntityModelLayers;
import com.ninni.thalassa.client.model.entity.SeaRugEntityModel;
import com.ninni.thalassa.entity.SeaRugEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SeaRugEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<SeaRugEntity, SeaRugEntityModel> {

    public SeaRugEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SeaRugEntityModel(ctx.getPart(ThalassaEntityModelLayers.SEA_RUG)), 0.3F);
    }

    @Override
    public Identifier getTexture(SeaRugEntity entity) {
        return entity.getVariant().getTexture();
    }
}
