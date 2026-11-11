package com.strangelands.client;

import com.strangelands.StrangelandsMod;
import com.strangelands.entity.SkyrayEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkyrayRenderer extends MobRenderer<SkyrayEntity, SkyrayModel> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(StrangelandsMod.MOD_ID, "skyray"), "main");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(StrangelandsMod.MOD_ID, "textures/entity/skyray.png");

    public SkyrayRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyrayModel(context.bakeLayer(LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(SkyrayEntity entity) {
        return TEXTURE;
    }
}
