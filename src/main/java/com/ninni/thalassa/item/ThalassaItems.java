package com.ninni.thalassa.item;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.block.ThalassaBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ThalassaItems {
    public static final Item BLUMP = register("blump", new Item(new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item SKARN = register("skarn", new BlockItem(ThalassaBlocks.SKARN, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item SKARN_LAYER = register("skarn_layer", new BlockItem(ThalassaBlocks.SKARN_LAYER, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item CLAMSTONE = register("clamstone", new BlockItem(ThalassaBlocks.CLAMSTONE, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item BOPRALT = register("bopralt", new BlockItem(ThalassaBlocks.BOPRALT, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item LEENSTONE = register("leenstone", new BlockItem(ThalassaBlocks.LEENSTONE, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item CRAFTING_MAT = register("crafting_mat", new BlockItem(ThalassaBlocks.CRAFTING_MAT, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item BUBBLE_QUARTZ = register("bubble_quartz", new BlockItem(ThalassaBlocks.BUBBLE_QUARTZ, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
    public static final Item BOUY_KELP = register("bouy_kelp", new BlockItem(ThalassaBlocks.BOUY_KELP, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Thalassa.MOD_ID, id), item);
    }
}


