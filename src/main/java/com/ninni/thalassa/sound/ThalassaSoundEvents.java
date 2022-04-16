package com.ninni.thalassa.sound;

import com.ninni.thalassa.Thalassa;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ThalassaSoundEvents {

    public static final SoundEvent ENTITY_FISH_FLOP = fish("flop");
    private static SoundEvent fish(String id) {
        return createEntitySound("fish", id);
    }

    public static final SoundEvent ENTITY_BLUMPLET_AMBIENT = blumplet("ambient");
    public static final SoundEvent ENTITY_BLUMPLET_HURT = blumplet("hurt");
    public static final SoundEvent ENTITY_BLUMPLET_DEATH = blumplet("death");
    private static SoundEvent blumplet(String id) {
        return createEntitySound("blumplet", id);
    }

    public static final SoundEvent ENTITY_SEA_RUG_SQUEAK = sea_rug("squeak");
    public static final SoundEvent ENTITY_SEA_RUG_HURT = sea_rug("hurt");
    public static final SoundEvent ENTITY_SEA_RUG_DEATH = sea_rug("death");
    private static SoundEvent sea_rug(String id) {
        return createEntitySound("sea_rug", id);
    }

    public static final SoundEvent BLOCK_STONE_BREAK = thalassa_stone("break");
    public static final SoundEvent BLOCK_STONE_STEP = thalassa_stone("step");
    public static final SoundEvent BLOCK_STONE_PLACE = thalassa_stone("place");
    public static final SoundEvent BLOCK_STONE_HIT = thalassa_stone("hit");
    public static final SoundEvent BLOCK_STONE_FALL = thalassa_stone("fall");
    private static SoundEvent thalassa_stone(String type) {
        return createBlockSound("thalassa_stone", type);
    }


    public static final SoundEvent BLOCK_TWILIGHT_STONE_BREAK = thalassa_twilight_stone("break");
    public static final SoundEvent BLOCK_TWILIGHT_STONE_STEP = thalassa_twilight_stone("step");
    public static final SoundEvent BLOCK_TWILIGHT_STONE_PLACE = thalassa_twilight_stone("place");
    public static final SoundEvent BLOCK_TWILIGHT_STONE_HIT = thalassa_twilight_stone("hit");
    public static final SoundEvent BLOCK_TWILIGHT_STONE_FALL = thalassa_twilight_stone("fall");
    private static SoundEvent thalassa_twilight_stone(String type) {return createBlockSound("thalassa_twilight_stone", type);}


    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Thalassa.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent createBlockSound(String block, String type) {
        return register("block." + block + "." + type);
    }

    private static SoundEvent createEntitySound(String entity, String id) {
        return register("entity." + entity + "." + id);
    }
}
