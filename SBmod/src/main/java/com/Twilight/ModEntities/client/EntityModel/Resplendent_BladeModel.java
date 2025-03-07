package com.Twilight.ModEntities.client.EntityModel;

import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Resplendent_BladeModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation
			(Main.MOD_ID, "resplendent_blade"), "main");
	private final ModelPart bone;

	public Resplendent_BladeModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(1, 2).addBox(-9.0F, -32.0F, 7.0F, 1.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(5, 3).addBox(-9.0F, -15.0F, 7.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(10, 5).addBox(-9.0F, -4.0F, 7.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 10).addBox(-8.0F, -3.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 14).mirror().addBox(-9.0F, -3.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(16, 2).addBox(-9.0F, -3.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 3).addBox(-8.0F, -28.0F, 7.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(11, 5).addBox(-9.0F, -24.0F, 6.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 17).addBox(-9.0F, -24.0F, 8.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(13, 8).addBox(-10.0F, -25.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(13, 17).addBox(-10.0F, -22.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 17).mirror().addBox(-8.0F, -22.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(14, 17).addBox(-8.0F, -25.0F, 6.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 17).addBox(-8.0F, -25.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 17).addBox(-10.0F, -25.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 17).addBox(-10.0F, -22.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(3, 17).mirror().addBox(-8.0F, -22.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 1).addBox(-7.0F, -22.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(3, 5).addBox(-8.0F, -19.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 18).addBox(-10.0F, -19.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 18).addBox(-7.0F, -24.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 18).addBox(-11.0F, -24.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 18).addBox(-14.0F, -16.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(1, 2).addBox(-12.0F, -21.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 5).addBox(-6.0F, -21.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 8).addBox(-5.0F, -28.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(10, 11).addBox(-11.0F, -22.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 12).addBox(-10.0F, -28.0F, 7.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-10.0F, -3.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// Your existing code
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}