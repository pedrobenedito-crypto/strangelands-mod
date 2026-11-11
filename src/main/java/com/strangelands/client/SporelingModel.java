package com.strangelands.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.strangelands.entity.SporelingEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** Round little body, two glowing antennae and two stubby feet. Texture is 64x32. */
public class SporelingModel extends EntityModel<SporelingEntity> {
    private final ModelPart root;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    private final ModelPart leftFoot;
    private final ModelPart rightFoot;

    public SporelingModel(ModelPart root) {
        this.root = root;
        this.leftAntenna = root.getChild("left_antenna");
        this.rightAntenna = root.getChild("right_antenna");
        this.leftFoot = root.getChild("left_foot");
        this.rightFoot = root.getChild("right_foot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition left = root.addOrReplaceChild("left_antenna",
                CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(2.0F, 14.0F, 0.0F));
        left.addOrReplaceChild("left_bulb",
                CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition right = root.addOrReplaceChild("right_antenna",
                CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(-2.0F, 14.0F, 0.0F));
        right.addOrReplaceChild("right_bulb",
                CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, -4.0F, 0.0F));

        root.addOrReplaceChild("left_foot",
                CubeListBuilder.create().texOffs(16, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(2.0F, 22.0F, 0.0F));
        root.addOrReplaceChild("right_foot",
                CubeListBuilder.create().texOffs(16, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(-2.0F, 22.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(SporelingEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftFoot.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
        this.rightFoot.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;

        float sway = Mth.sin(ageInTicks * 0.1F) * 0.15F;
        this.leftAntenna.zRot = 0.2F + sway;
        this.rightAntenna.zRot = -0.2F - sway;
        this.leftAntenna.xRot = Mth.sin(ageInTicks * 0.07F) * 0.1F;
        this.rightAntenna.xRot = Mth.sin(ageInTicks * 0.07F + 1.0F) * 0.1F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
