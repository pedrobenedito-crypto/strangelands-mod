package com.strangelands.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class StrangeFluidType extends FluidType {
    private final ResourceLocation still;
    private final ResourceLocation flowing;
    private final ResourceLocation overlay;
    private final int tint;

    public StrangeFluidType(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay,
                            int tint, Properties properties) {
        super(properties);
        this.still = still;
        this.flowing = flowing;
        this.overlay = overlay;
        this.tint = tint;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowing;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return overlay;
            }

            @Override
            public int getTintColor() {
                return tint;
            }
        });
    }
}
