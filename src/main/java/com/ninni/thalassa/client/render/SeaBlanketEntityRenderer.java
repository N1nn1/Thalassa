package com.ninni.thalassa.client.render;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.client.init.ThalassaEntityModelLayers;
import com.ninni.thalassa.client.model.entity.SeaBlanketEntityModel;
import com.ninni.thalassa.entity.SeaBlanketEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SeaBlanketEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<SeaBlanketEntity, SeaBlanketEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Thalassa.MOD_ID, "textures/entity/sea_blanket.png");

    public SeaBlanketEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SeaBlanketEntityModel(ctx.getPart(ThalassaEntityModelLayers.SEA_BLANKET)), 0.6F);
    }

    @Override
    public Identifier getTexture(SeaBlanketEntity entity) {
        return TEXTURE;
    }
}
