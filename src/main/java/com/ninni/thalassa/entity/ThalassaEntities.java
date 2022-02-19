package com.ninni.thalassa.entity;

import com.ninni.thalassa.Thalassa;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class ThalassaEntities {
    public static final EntityType<BlumpletEntity> BLUMPLET = register(
        "blumplet",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(BlumpletEntity::new)
                               .defaultAttributes(BlumpletEntity::createBlumpletAttributes)
                               .spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(0.4F, 0.4F))
                               .trackRangeBlocks(8),
        new int[]{ 0x2b8292, 0xbf3652 }
    );
    public static final EntityType<SeaRugEntity> SEA_RUG = register(
        "sea_rug",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(SeaRugEntity::new)
                               .defaultAttributes(SeaRugEntity::createSeaRugAttributes)
                               .spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(0.6F, 0.05F))
                               .trackRangeBlocks(8),
        new int[]{ 0x384679, 0xffd743 }
    );
    public static final EntityType<SeaBlanketEntity> SEA_BLANKET = register(
        "sea_blanket",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(SeaBlanketEntity::new)
                               .defaultAttributes(SeaBlanketEntity::createSeaBlanketAttributes)
                               .spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(1F, 0.2F))
                               .trackRangeBlocks(8),
        new int[]{ 0x282c2f, 0xe4e9e9 }
    );

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType, int[] spawnEggColors) {
        if (spawnEggColors != null)
            Registry.register(Registry.ITEM, new Identifier(Thalassa.MOD_ID, id + "_spawn_egg"), new SpawnEggItem((EntityType<? extends MobEntity>) entityType, spawnEggColors[0], spawnEggColors[1], new Item.Settings().maxCount(64).group(Thalassa.ITEM_GROUP)));

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Thalassa.MOD_ID, id), entityType);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, int[] spawnEggColors) {
        return register(id, entityType.build(), spawnEggColors);
    }

}
