package com.ninni.thalassa.block;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.sound.ThalassaBlockSoundGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ThalassaBlocks {

    public static final Block SKARN = register("skarn", new Block(FabricBlockSettings.copyOf(Blocks.TUFF).sounds(ThalassaBlockSoundGroups.THALASSA_STONES)));
    public static final Block SKARN_LAYER = register("skarn_layer", new LayerBlock(FabricBlockSettings.copyOf(SKARN)));
    public static final Block CLAMSTONE = register("clamstone", new Block(FabricBlockSettings.copyOf(Blocks.TUFF).sounds(ThalassaBlockSoundGroups.THALASSA_STONES)));


    private static Block register(String id, Block block, boolean registerItem) {
        Block registered = Registry.register(Registry.BLOCK, new Identifier(Thalassa.MOD_ID, id), block);
        if (registerItem) {
            Registry.register(Registry.ITEM, new Identifier(Thalassa.MOD_ID, id), new BlockItem(registered, new FabricItemSettings().group(Thalassa.ITEM_GROUP)));
        }
        return registered;
    }
    private static Block register(String id, Block block) {
        return register(id, block, false);
    }
}
