package com.ninni.thalassa.client.init;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.mixin.client.EntityModelLayersInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ThalassaEntityModelLayers {

    public static final EntityModelLayer BLUMPLET = registerMain("blumplet");
    public static final EntityModelLayer SEA_RUG = registerMain("sea_rug");
    public static final EntityModelLayer SEA_BLANKET = registerMain("sea_blanket");

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.register(new Identifier(Thalassa.MOD_ID, id).toString(), "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        return EntityModelLayersInvoker.register(new Identifier(Thalassa.MOD_ID, id).toString(), layer);
    }
}
