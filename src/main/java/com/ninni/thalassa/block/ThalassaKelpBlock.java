package com.ninni.thalassa.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpBlock;

public class ThalassaKelpBlock extends KelpBlock {
    public ThalassaKelpBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected Block getPlant() {
        return ThalassaBlocks.BOUY_KELP_PLANT;
    }
}
