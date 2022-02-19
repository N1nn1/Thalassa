package com.ninni.thalassa;

import com.google.common.reflect.Reflection;
import com.ninni.thalassa.block.ThalassaBlocks;
import com.ninni.thalassa.entity.ThalassaEntities;
import com.ninni.thalassa.item.ThalassaItems;
import com.ninni.thalassa.sound.ThalassaSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Thalassa implements ModInitializer {
	public static final String MOD_ID = "thalassa";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Thalassa.MOD_ID, "item_group"), () -> new ItemStack(ThalassaItems.BLUMP));

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Reflection.initialize(
			ThalassaItems.class,
			ThalassaBlocks.class,
			ThalassaSoundEvents.class,
			ThalassaEntities.class
		);
	}
}
