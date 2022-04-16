package com.ninni.thalassa.block;

import com.ninni.thalassa.Thalassa;
import com.ninni.thalassa.sound.ThalassaBlockSoundGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ThalassaBlocks {

    public static final Block SKARN = register("skarn", new Block(FabricBlockSettings.copyOf(Blocks.TUFF).sounds(ThalassaBlockSoundGroups.THALASSA_STONES)));
    public static final Block SKARN_LAYER = register("skarn_layer", new LayerBlock(FabricBlockSettings.copyOf(SKARN)));
    public static final Block CLAMSTONE = register("clamstone", new Block(FabricBlockSettings.copyOf(Blocks.TUFF).sounds(ThalassaBlockSoundGroups.THALASSA_STONES)));
    public static final Block BOPRALT = register("bopralt", new PillarBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(ThalassaBlockSoundGroups.THALASSA_TWILIGHT_STONES)));
    public static final Block LEENSTONE = register("leenstone", new Block(AbstractBlock.Settings.of(Material.STONE).sounds(ThalassaBlockSoundGroups.THALASSA_TWILIGHT_STONES).strength(-1.0F, 3600000.0F).dropsNothing()));
    public static final Block CRAFTING_MAT = register("crafting_mat", new CraftingMatBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));
    public static final Block BUBBLE_QUARTZ = register("bubble_quartz", new BubbleQuartzBlock(7, 3, AbstractBlock.Settings.of(Material.GLASS).nonOpaque().ticksRandomly().sounds(ThalassaBlockSoundGroups.THALASSA_STONES).strength(1.5F).luminance((state) -> 5)));
    public static final Block BOUY_KELP = register("bouy_kelp", new ThalassaKelpBlock(FabricBlockSettings.copyOf(Blocks.KELP)));
    public static final Block BOUY_KELP_PLANT = register("bouy_kelp_plant", new ThalassaKelpPlantBlock(FabricBlockSettings.copyOf(Blocks.KELP_PLANT)));


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
