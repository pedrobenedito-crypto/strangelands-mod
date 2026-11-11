package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, StrangelandsMod.MOD_ID);

    public static final RegistryObject<Item> STRANGE_LIQUID_BUCKET = ITEMS.register("strange_liquid_bucket",
            () -> new BucketItem(ModFluids.SOURCE,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> GLOW_BLOOM = ITEMS.register("glow_bloom",
            () -> new BlockItem(ModBlocks.GLOW_BLOOM.get(), new Item.Properties()));

    public static final RegistryObject<Item> SPORELING_SPAWN_EGG = ITEMS.register("sporeling_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SPORELING, 0x7C4DFF, 0x5CFFD6, new Item.Properties()));

    public static final RegistryObject<Item> SKYRAY_SPAWN_EGG = ITEMS.register("skyray_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKYRAY, 0xC81E1E, 0x5CFFD6, new Item.Properties()));
}
