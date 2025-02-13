package com.Twilight.ModEntities.client;

import com.Twilight.ModEntities.custom.LaserEntity;
import com.Twilight.SBMod.Main;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class LaserRenderer extends EntityRenderer<LaserEntity> {
    // 直接使用信标的渲染类型和材质
    private static final RenderType BEAM_RENDER_TYPE =
            RenderType.entityTranslucent(new ResourceLocation(Main.MOD_ID, "textures/entities/laser_beam.png"));

    public LaserRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LaserEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        long gameTime = entity.level().getGameTime();
        float progress = (float)entity.age / entity.maxAge;

        // 获取起点和终点
        Vec3 start = entity.getStart();
        Vec3 end = entity.getEnd();
        Vec3 direction = end.subtract(start).normalize();
        float length = (float)start.distanceTo(end);

        // 设置颜色（使用实体存储的颜色）
        int color = entity.getColor();
        float[] rgb = new float[]{
                ((color >> 16) & 0xFF) / 255f,
                ((color >> 8) & 0xFF) / 255f,
                (color & 0xFF) / 255f
        };

        // 开始渲染
        poseStack.pushPose();
        VertexConsumer vertexBuilder = buffer.getBuffer(BEAM_RENDER_TYPE);

        // 计算旋转角度
        Vec3 normalizedDir = direction.normalize();
        float yaw = (float)Math.toDegrees(Math.atan2(normalizedDir.z, normalizedDir.x)) - 90.0F;
        float pitch = (float)Math.toDegrees(Math.asin(normalizedDir.y));

        // 调整姿态矩阵
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));

        // 核心渲染逻辑（参考信标）
        renderBeam(poseStack, vertexBuilder, gameTime, partialTicks,
                progress, length, rgb, packedLight);

        poseStack.popPose();
    }

    private void renderBeam(PoseStack poseStack, VertexConsumer builder,
                            long gameTime, float partialTicks, float progress,
                            float length, float[] rgb, int packedLight) {
        float textureScale = 0.2F; // 纹理缩放系数
        float alpha = 1.0F - progress; // 根据生命周期计算透明度

        // 计算动画参数
        float offset = -gameTime * 0.2F - Mth.floor(partialTicks * 0.1F);
        float scaleY = length * textureScale;
        float minU = offset * 0.5F;
        float maxU = minU + scaleY;

        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();

        // 构建光束立方体
        addQuad(matrix, normalMatrix, builder,
                rgb[0], rgb[1], rgb[2], alpha * 0.8F,
                0.0F, length, -0.1F, -0.1F, minU, maxU,
                packedLight);
        addQuad(matrix, normalMatrix, builder,
                rgb[0], rgb[1], rgb[2], alpha,
                0.0F, length, -0.2F, -0.2F, minU, maxU,
                packedLight);
    }

    private void addQuad(Matrix4f matrix, Matrix3f normalMatrix, VertexConsumer builder,
                         float r, float g, float b, float a,
                         float yOffset, float height, float width, float zOffset,
                         float minU, float maxU, int packedLight) {
        // 前侧
        builder.vertex(matrix, -width, yOffset + height, zOffset)
                .color(r, g, b, a)
                .uv(minU, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .endVertex();
        builder.vertex(matrix, width, yOffset + height, zOffset)
                .color(r, g, b, a)
                .uv(maxU, 0.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .endVertex();
        builder.vertex(matrix, width, yOffset, zOffset)
                .color(r, g, b, a)
                .uv(maxU, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .endVertex();
        builder.vertex(matrix, -width, yOffset, zOffset)
                .color(r, g, b, a)
                .uv(minU, 1.0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(LaserEntity entity) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/laser_beam.png");
    }
}
