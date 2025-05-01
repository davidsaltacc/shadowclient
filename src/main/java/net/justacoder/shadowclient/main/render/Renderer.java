package net.justacoder.shadowclient.main.render;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class Renderer { // IntelliJ says this can be converted to a record. it's right, but I don't feel comfortable doing that. like, not at all.

    private final BufferBuilderProvider bufferBuilderProvider;
    private final MatrixStack matrices;
    private final float tickDelta;

    public Renderer(BufferBuilderProvider bufferBuilderProvider, MatrixStack matrices, float tickDelta) {
        this.bufferBuilderProvider = bufferBuilderProvider;
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public BufferBuilderProvider getBufferBuilderProvider() {
        return bufferBuilderProvider;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public Vec3d getInterpolationOffset(Entity e) {
        return MinecraftClient.getInstance().isPaused() ? Vec3d.ZERO : new Vec3d(e.getX() - MathHelper.lerp(tickDelta, e.lastRenderX, e.getX()), e.getY() - MathHelper.lerp(tickDelta, e.lastRenderY, e.getY()), e.getZ() - MathHelper.lerp(tickDelta, e.lastRenderZ, e.getZ()));
    }

    public void matricesToWorldSpace() {
        Camera camera = ShadowClientMain.mc.gameRenderer.getCamera();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180f));
        matrices.translate(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);
    }

    public void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float[] color) {
        drawLine(x1, y1, z1, x2, y2, z2, color, true);
    }

    public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, float[] color) {
        drawLine(x1, y1, z1, x2, y2, z2, color, true);
    }

    public void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float[] color, boolean depthTest) {
        drawLine((float) x1, (float) y1, (float) z1, (float) x2, (float) y2, (float) z2, color, depthTest);
    }

    public void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, float[] color, boolean depthTest) {
        VertexConsumer consumer = bufferBuilderProvider.getBufferBuilder(RenderingTypes.getLines(depthTest));
        matrices.push();
        matricesToWorldSpace();
        Matrix4f posMat = matrices.peek().getPositionMatrix();
        consumer.vertex(posMat, x1, y1, z1).color(color[0], color[1], color[2], color[3]);
        consumer.vertex(posMat, x2, y2, z2).color(color[0], color[1], color[2], color[3]);
        matrices.pop();
    }

    public void drawLineList(List<Vec3d> points, float[] color) {
        drawLineList(points, color, true);
    }

    public void drawLineList(List<Vec3d> points, float[] color, boolean depthTest) {
        if (points.size() < 2) {
            return;
        }
        VertexConsumer consumer = bufferBuilderProvider.getBufferBuilder(RenderingTypes.getLinesStrip(depthTest));
        matrices.push();
        matricesToWorldSpace();
        Matrix4f posMat = matrices.peek().getPositionMatrix();
        Vec3d firstPoint = points.getFirst();
        Vec3d lastPoint = points.getLast();
        consumer.vertex(posMat, (float) firstPoint.x, (float) firstPoint.y, (float) firstPoint.z).color(0f, 0f, 0f, 0f); // hacky, but like, cry about it or something
        points.forEach(point -> consumer.vertex(posMat, (float) point.x, (float) point.y, (float) point.z).color(color[0], color[1], color[2], color[3]));
        consumer.vertex(posMat, (float) lastPoint.x, (float) lastPoint.y, (float) lastPoint.z).color(0f, 0f, 0f, 0f);
        matrices.pop();
    }

    public void drawText(String text, float x, float y, float z, Quaternionf rotation, int color, float offsetX, float offsetY) {
        matrices.push();
        matricesToWorldSpace();
        matrices.translate(x, y, z);
        matrices.multiply(rotation);
        matrices.scale(-0.01f, -0.01f, 0.01f);
        Font.renderString(bufferBuilderProvider, matrices, text, offsetX, offsetY, color, 4);
        matrices.pop();
    }

    public void drawText(String text, float x, float y, float z, Quaternionf rotation, int color) {
        drawText(text, x, y, z, rotation, color, 0, 0);
    }

    public void drawText(Text text, float x, float y, float z, Quaternionf rotation, int color) {
        drawText(text.getString(), x, y, z, rotation, color, 0f, 0f);
    }

    public void drawText(String text, double x, double y, double z, Quaternionf rotation, int color) {
        drawText(text, (float) x, (float) y, (float) z, rotation, color, 0f, 0f);
    }

    public void drawText(Text text, double x, double y, double z, Quaternionf rotation, int color) {
        drawText(text.getString(), x, y, z, rotation, color);
    }

    public void drawText(Text text, float x, float y, float z, Quaternionf rotation, int color, float offsetX, float offsetY) {
        drawText(text.getString(), x, y, z, rotation, color, offsetX, offsetY);
    }

    public void drawText(String text, double x, double y, double z, Quaternionf rotation, int color, float offsetX, float offsetY) {
        drawText(text, (float) x, (float) y, (float) z, rotation, color, offsetX, offsetY);
    }

    public void drawText(Text text, double x, double y, double z, Quaternionf rotation, int color, float offsetX, float offsetY) {
        drawText(text.getString(), x, y, z, rotation, color, offsetX, offsetY);
    }

}