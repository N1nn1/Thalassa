package com.ninni.thalassa.advancement.criterion;

import com.google.common.collect.Maps;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ThalassaCriteria {
    private static final Map<Identifier, Criterion<?>> VALUES = Maps.newHashMap();

    //TODO add an advancement for taming a creature from the mod
    public static final TameThalassaEntityCriterion TAME_THALASSA_ENTITY = register(new TameThalassaEntityCriterion());


    public ThalassaCriteria() {
    }

    private static <T extends Criterion<?>> T register(T object) {
        if (VALUES.containsKey(object.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + object.getId());
        } else {
            VALUES.put(object.getId(), object);
            return object;
        }
    }
}
