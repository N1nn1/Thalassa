package com.ninni.thalassa.block;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.KelpPlantBlock;

public class ThalassaKelpPlantBlock extends KelpPlantBlock {
    public ThalassaKelpPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ThalassaBlocks.BOUY_KELP;
    }
}
