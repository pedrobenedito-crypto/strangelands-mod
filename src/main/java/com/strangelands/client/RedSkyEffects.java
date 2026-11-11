package com.strangelands.client;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

/** No sun, moon, stars or clouds: the sky is just the biome's (red) sky colour. */
public class RedSkyEffects extends DimensionSpecialEffects {
    public RedSkyEffects() {
        super(Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return fogColor;
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }
}
