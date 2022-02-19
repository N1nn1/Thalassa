package com.ninni.thalassa.entity.enums;

import com.ninni.thalassa.Thalassa;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum SeaRugVariant {
    YELLOW,
    BLUE,
    BLACK,
    PINK,
    WHITE;

    private final Identifier texture;

    SeaRugVariant() {
        this.texture = new Identifier(Thalassa.MOD_ID, "textures/entity/sea_rug/sea_rug_" + this.name().toLowerCase(Locale.ROOT) + ".png");
    }

    public Identifier getTexture() {
        return texture;
    }

    public static SeaRugVariant getDefault() {
        return YELLOW;
    }
}
