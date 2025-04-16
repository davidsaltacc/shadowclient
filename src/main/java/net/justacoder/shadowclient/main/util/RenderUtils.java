package net.justacoder.shadowclient.main.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.mixininterface.IGameRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class RenderUtils {

    public static Vec3d getInterpolationOffset(Entity e) {
        if (MinecraftClient.getInstance().isPaused()) {
            return Vec3d.ZERO;
        }

        double tickDelta = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
        return new Vec3d(e.getX() - MathHelper.lerp(tickDelta, e.lastRenderX, e.getX()), e.getY() - MathHelper.lerp(tickDelta, e.lastRenderY, e.getY()), e.getZ() - MathHelper.lerp(tickDelta, e.lastRenderZ, e.getZ()));
    }

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float[] color, float width, boolean depthTest) {

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        MatrixStack matrices = matrixFrom(x1, y1, z1);

        Tessellator tessellator = Tessellator.getInstance();

        if (depthTest) {
            RenderSystem.enableDepthTest();
        } else {
            RenderSystem.disableDepthTest();
        }
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.lineWidth(width);

        BufferBuilder builder = tessellator.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        vertexLine(matrices, builder, 0f, 0f, 0f, (float) (x2 - x1), (float) (y2 - y1), (float) (z2 - z1), color);
        BufferRenderer.drawWithGlobalProgram(builder.end());

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float[] color, float width) {
        drawLine(x1, y1, z1, x2, y2, z2, color, width, false);
    }

    public static void vertexLine(MatrixStack matrices, VertexConsumer vertexConsumer, float x1, float y1, float z1, float x2, float y2, float z2, float[] lineColor) {
        MatrixStack.Entry entry = matrices.peek();

        Vector3f normalVec = getNormal(x1, y1, z1, x2, y2, z2);

        vertexConsumer.vertex(entry, x1, y1, z1).color(lineColor[0], lineColor[1], lineColor[2], lineColor[3]).normal(entry, normalVec.x(), normalVec.y(), normalVec.z());
        vertexConsumer.vertex(entry, x2, y2, z2).color(lineColor[0], lineColor[1], lineColor[2], lineColor[3]).normal(entry, normalVec.x(), normalVec.y(), normalVec.z());
    }

    public static Vector3f getNormal(float x1, float y1, float z1, float x2, float y2, float z2) {
        float xNormal = x2 - x1;
        float yNormal = y2 - y1;
        float zNormal = z2 - z1;
        float normalSqrt = MathHelper.sqrt(xNormal * xNormal + yNormal * yNormal + zNormal * zNormal);

        return new Vector3f(xNormal / normalSqrt, yNormal / normalSqrt, zNormal / normalSqrt);
    }

    public static MatrixStack matrixFrom(double x, double y, double z) {
        MatrixStack matrices = new MatrixStack();

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));

        matrices.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);

        return matrices;
    }

    public static void loadShader(@Nullable Identifier id) {
        ((IGameRenderer) MinecraftClient.getInstance().gameRenderer).loadShader(id);
    }

    public static void drawOutlinedBox(Box box, MatrixStack matrices) {

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ); // TODO someone please fucking optimize this for me
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ);
        bufferBuilder.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ);

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }
}