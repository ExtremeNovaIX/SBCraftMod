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

public class LaserRenderer extends EntityRenderer<LaserEntity> {
    // 配置参数
    private static final int SEGMENTS = 16;
    private static final float PI = (float) Math.PI;
    private static final RenderType INNER_RENDER_TYPE = RenderType.entityTranslucentEmissive(
            new ResourceLocation(Main.MOD_ID, "textures/entities/laser_inner.png"));
    private static final RenderType OUTER_RENDER_TYPE = RenderType.entityTranslucentEmissive(
            new ResourceLocation(Main.MOD_ID, "textures/entities/laser_outer.png"));

    public LaserRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LaserEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Vec3 start = entity.getStart();
        Vec3 end = entity.getEnd();
        Vec3 direction = end.subtract(start);
        float length = (float)direction.length();
        direction = direction.normalize();
        float width = entity.getWidth();

        int color = entity.getColor();
        float[] innerColor = getColorComponents(color, 0.9F);
        float[] outerColor = getColorComponents(color, 0.4F);

        poseStack.pushPose();
        alignToDirection(poseStack, start, direction);

        // 渲染外层辉光
        renderCylinder(poseStack, buffer.getBuffer(OUTER_RENDER_TYPE),
                length, width * 1.5f, outerColor, packedLight);
        // 渲染内层光柱
        renderCylinder(poseStack, buffer.getBuffer(INNER_RENDER_TYPE),
                length, width * 0.8f, innerColor, packedLight);

        poseStack.popPose();
    }

    private float[] getColorComponents(int argb, float alphaMod) {
        return new float[] {
                ((argb >> 16) & 0xFF) / 255f,
                ((argb >> 8) & 0xFF) / 255f,
                (argb & 0xFF) / 255f,
                ((argb >> 24) & 0xFF) / 255f * alphaMod
        };
    }

    private void alignToDirection(PoseStack poseStack, Vec3 start, Vec3 direction) {
        // 重置矩阵并创建局部变换空间
        poseStack.pushPose();

        // 先移动到世界坐标系起点
        poseStack.translate(start.x, start.y, start.z);

        // 计算旋转角度（使用Minecraft方向规范）
        double dx = direction.x;
        double dy = direction.y;
        double dz = direction.z;

        // 偏航角计算（绕Y轴）
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
        // 俯仰角计算（绕X轴）
        float pitch = (float) Math.toDegrees(-Math.asin(dy));

        // 应用旋转（顺序：Y轴 → X轴）
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));

        // 调整原点偏移（关键修复）
        poseStack.translate(0, 0, -0.5); // 将光柱起点对齐到实际位置
        poseStack.popPose();
    }

    private void renderCylinder(PoseStack poseStack, VertexConsumer consumer,
                                float length, float radius,
                                float[] color, int light) {
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();
        long time = System.currentTimeMillis();
        float uvOffset = (time % 2000) / 2000f;

        for (int i = 0; i < SEGMENTS; i++) {
            float angle1 = i * 2 * PI / SEGMENTS;
            float angle2 = (i + 1) * 2 * PI / SEGMENTS;

            float x1 = Mth.cos(angle1) * radius;
            float z1 = Mth.sin(angle1) * radius;
            float x2 = Mth.cos(angle2) * radius;
            float z2 = Mth.sin(angle2) * radius;

            addBeamQuad(matrix, normalMatrix, consumer,
                    x1, z1, x2, z2,
                    length, color, uvOffset, light);
        }
    }

    // 修复后的四边形生成（正确的顶点顺序）
    private void addBeamQuad(Matrix4f matrix, Matrix3f normalMatrix,
                             VertexConsumer consumer,
                             float x1, float z1, float x2, float z2,
                             float length, float[] color,
                             float uvOffset, int light) {
        float r = color[0];
        float g = color[1];
        float b = color[2];
        float a = color[3];

        float minV = uvOffset;
        float maxV = uvOffset + length * 0.1f;

        // 顶点顺序：左下 → 右下 → 右上 → 左上
        vertex(matrix, normalMatrix, consumer, x1, 0, z1, 0, maxV, r, g, b, a, light);
        vertex(matrix, normalMatrix, consumer, x2, 0, z2, 1, maxV, r, g, b, a, light);
        vertex(matrix, normalMatrix, consumer, x2, length, z2, 1, minV, r, g, b, a, light);
        vertex(matrix, normalMatrix, consumer, x1, length, z1, 0, minV, r, g, b, a, light);
    }

    private void vertex(Matrix4f matrix, Matrix3f normalMatrix,
                        VertexConsumer consumer,
                        float x, float y, float z,
                        float u, float v,
                        float r, float g, float b, float a,
                        int light) {
        consumer.vertex(matrix, x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(LaserEntity entity) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/laser_inner.png");
    }
}
