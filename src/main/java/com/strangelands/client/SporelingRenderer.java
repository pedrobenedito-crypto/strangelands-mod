package com.strangelands.client;

import com.strangelands.StrangelandsMod;
import com.strangelands.entity.SporelingEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SporelingRenderer extends MobRenderer<SporelingEntity, SporelingModel> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(StrangelandsMod.MOD_ID, "sporeling"), "main");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(StrangelandsMod.MOD_ID, "textures/entity/sporeling.png");

    public SporelingRenderer(EntityRendererProvider.Context context) {
        super(context, new SporelingModel(context.bakeLayer(LAYER)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(SporelingEntity entity) {
        return TEXTURE;
    }
}
