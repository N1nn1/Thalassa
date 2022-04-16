package com.ninni.thalassa.sound;

import net.minecraft.sound.BlockSoundGroup;

public class ThalassaBlockSoundGroups {

    public static final BlockSoundGroup THALASSA_STONES = new BlockSoundGroup(
        0.3F, 1.0F,

        ThalassaSoundEvents.BLOCK_STONE_BREAK,
        ThalassaSoundEvents.BLOCK_STONE_STEP,
        ThalassaSoundEvents.BLOCK_STONE_PLACE,
        ThalassaSoundEvents.BLOCK_STONE_HIT,
        ThalassaSoundEvents.BLOCK_STONE_FALL
    );

    public static final BlockSoundGroup THALASSA_TWILIGHT_STONES = new BlockSoundGroup(
        0.4F, 1.0F,

        ThalassaSoundEvents.BLOCK_TWILIGHT_STONE_BREAK,
        ThalassaSoundEvents.BLOCK_TWILIGHT_STONE_STEP,
        ThalassaSoundEvents.BLOCK_TWILIGHT_STONE_PLACE,
        ThalassaSoundEvents.BLOCK_TWILIGHT_STONE_HIT,
        ThalassaSoundEvents.BLOCK_TWILIGHT_STONE_FALL
    );
}
