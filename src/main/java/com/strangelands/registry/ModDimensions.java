package com.strangelands.registry;

import com.strangelands.StrangelandsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
    public static final ResourceKey<Level> STRANGE_REALM =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(StrangelandsMod.MOD_ID, "strange_realm"));
}
