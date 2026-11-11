package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import com.strangelands.entity.SporelingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, StrangelandsMod.MOD_ID);

    public static final RegistryObject<EntityType<SporelingEntity>> SPORELING = ENTITIES.register("sporeling",
            () -> EntityType.Builder.of(SporelingEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(StrangelandsMod.MOD_ID, "sporeling").toString()));
}
