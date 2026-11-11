package com.strangelands.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.strangelands.entity.SkyrayEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** Flat ray with two big wings and a tail. Texture is 64x32. */
public class SkyrayModel extends EntityModel<SkyrayEntity> {
    private final ModelPart root;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;

    public SkyrayModel(ModelPart root) {
        this.root = root;
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.5F, -5.0F, 8.0F, 3.0F, 10.0F),
                PartPose.offset(0.0F, 20.0F, 0.0F));

        root.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -0.5F, -4.0F, 10.0F, 1.0F, 8.0F),
                PartPose.offset(4.0F, 20.0F, 0.0F));
        root.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(0, 16).addBox(-10.0F, -0.5F, -4.0F, 10.0F, 1.0F, 8.0F),
                PartPose.offset(-4.0F, 20.0F, 0.0F));

        root.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(40, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offset(0.0F, 20.0F, 5.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(SkyrayEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float flap = Mth.sin(ageInTicks * 0.2F) * 0.45F;
        this.leftWing.zRot = -flap - 0.1F;
        this.rightWing.zRot = flap + 0.1F;
        this.tail.yRot = Mth.sin(ageInTicks * 0.1F) * 0.35F;
        this.tail.xRot = 0.1F + Mth.sin(ageInTicks * 0.2F) * 0.05F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
