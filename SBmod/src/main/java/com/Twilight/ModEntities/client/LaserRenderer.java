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
import org.joml.Quaternionf;
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

        // 新增加的坐标平移
        Vec3 cameraPos = this.entityRenderDispatcher.camera.getPosition();
        poseStack.translate(
                start.x - cameraPos.x,
                start.y - cameraPos.y,
                start.z - cameraPos.z
        );

        // 替换原来的旋转计算
        Vector3f lookVec = new Vector3f((float)direction.x, (float)direction.y, (float)direction.z);
        Quaternionf rotation = new Quaternionf().rotationTo(new Vector3f(0, 1, 0), lookVec.normalize());
        poseStack.mulPose(rotation);

        // 增加缩放补偿（可选）
        float scaleFactor = 0.8f; // 根据实际效果调整
        poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

        // 核心渲染逻辑
        renderBeam(poseStack, vertexBuilder, gameTime, partialTicks,
                progress, length, rgb, packedLight);

        poseStack.popPose();
    }

    private void renderBeam(PoseStack poseStack, VertexConsumer builder,
                            long gameTime, float partialTicks, float progress,
                            float length, float[] rgb, int packedLight) {
        float alpha = 1.0F - progress;

        // 核心层参数
        renderCylinder(poseStack.last(), builder,
                length, 0.1f, alpha * 0.8f,
                16, packedLight);

        // 辉光层参数
        renderCylinder(poseStack.last(), builder,
                length, 0.2f, alpha * 0.3f,
                32, packedLight);
    }

    private void renderCylinder(PoseStack.Pose pose, VertexConsumer builder,
                                float length, float radius, float alpha,
                                int segments, int packedLight) {
        Matrix4f matrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (Math.PI * 2 * i / segments);
            float angle2 = (float) (Math.PI * 2 * (i+1) / segments);

            Vector3f v1 = new Vector3f(
                    radius * Mth.cos(angle1),
                    0,
                    radius * Mth.sin(angle1)
            );

            Vector3f v2 = new Vector3f(
                    radius * Mth.cos(angle2),
                    0,
                    radius * Mth.sin(angle2)
            );

            // 生成四边形侧面
            addCylinderQuad(matrix, normalMatrix, builder,
                    v1, v2, length, alpha, packedLight);
        }
    }

    private void addCylinderQuad(Matrix4f matrix, Matrix3f normalMatrix, VertexConsumer builder,
                                 Vector3f v1, Vector3f v2, float length,
                                 float alpha, int packedLight) {
        // 顶部顶点
        builder.vertex(matrix, v1.x, length, v1.z)
                .color(1, 1, 1, alpha)
                .uv(0, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, v1.x, 0, v1.z)
                .endVertex();

        builder.vertex(matrix, v2.x, length, v2.z)
                .color(1, 1, 1, alpha)
                .uv(1, 0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, v2.x, 0, v2.z)
                .endVertex();

        // 底部顶点
        builder.vertex(matrix, v2.x, 0, v2.z)
                .color(1, 1, 1, alpha)
                .uv(1, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, v2.x, 0, v2.z)
                .endVertex();

        builder.vertex(matrix, v1.x, 0, v1.z)
                .color(1, 1, 1, alpha)
                .uv(0, 1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, v1.x, 0, v1.z)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(LaserEntity entity) {
        return new ResourceLocation(Main.MOD_ID, "textures/entities/laser_beam.png");
    }
}
