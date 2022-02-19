package com.ninni.thalassa.client;

import com.google.common.collect.ImmutableMap;
import com.ninni.thalassa.client.init.ThalassaEntityModelLayers;
import com.ninni.thalassa.client.model.entity.BlumpletEntityModel;
import com.ninni.thalassa.client.model.entity.SeaBlanketEntityModel;
import com.ninni.thalassa.client.model.entity.SeaRugEntityModel;
import com.ninni.thalassa.client.render.BlumpletEntityRenderer;
import com.ninni.thalassa.client.render.SeaBlanketEntityRenderer;
import com.ninni.thalassa.client.render.SeaRugEntityRenderer;
import com.ninni.thalassa.entity.ThalassaEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class ThalassaClient implements ClientModInitializer {

	@Override
	@SuppressWarnings({ "deprecation" })
	public void onInitializeClient() {
		EntityRendererRegistry erri = EntityRendererRegistry.INSTANCE;
		erri.register(ThalassaEntities.BLUMPLET, BlumpletEntityRenderer::new);
		erri.register(ThalassaEntities.SEA_RUG, SeaRugEntityRenderer::new);
		erri.register(ThalassaEntities.SEA_BLANKET, SeaBlanketEntityRenderer::new);

		new ImmutableMap.Builder<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider>()
			.put(ThalassaEntityModelLayers.BLUMPLET, BlumpletEntityModel::getTexturedModelData)
			.put(ThalassaEntityModelLayers.SEA_RUG, SeaRugEntityModel::getTexturedModelData)
			.put(ThalassaEntityModelLayers.SEA_BLANKET, SeaBlanketEntityModel::getTexturedModelData)
			.build().forEach(EntityModelLayerRegistry::registerModelLayer);

	}
}
