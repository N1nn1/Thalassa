package com.ninni.thalassa.advancement.criterion;

import com.google.gson.JsonObject;
import com.ninni.thalassa.entity.common.ThalassaFishEntity;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TameThalassaEntityCriterion extends AbstractCriterion<TameThalassaEntityCriterion.Conditions> {
    static final Identifier ID = new Identifier("tame_thalassa_entity");

    public TameThalassaEntityCriterion() {
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public TameThalassaEntityCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended extended2 = EntityPredicate.Extended.getInJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
        return new TameThalassaEntityCriterion.Conditions(extended, extended2);
    }

    public void trigger(ServerPlayerEntity player, ThalassaFishEntity entity) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
        this.trigger(player, (conditions) -> conditions.matches(lootContext));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final EntityPredicate.Extended entity;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended entity) {
            super(TameThalassaEntityCriterion.ID, player);
            this.entity = entity;
        }

        public boolean matches(LootContext tamedEntityContext) {
            return this.entity.test(tamedEntityContext);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("entity", this.entity.toJson(predicateSerializer));
            return jsonObject;
        }
    }
}
