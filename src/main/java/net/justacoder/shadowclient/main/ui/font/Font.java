package net.justacoder.shadowclient.main.ui.font;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.font.FontRenderer;
import net.justacoder.shadowclient.main.render.font.FontTextureAtlas;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.io.InputStream;

public abstract class Font {

    public static final String FONT_PATH = "/assets/shadowclient/font/notosans-regular.ttf";
    public static final float FONT_SIZE = 14f;
    public static final float FONT_OFFSET_X = 0f;
    public static final float FONT_OFFSET_Y = -4f;

    public static FontRenderer fontRenderer;

    public static void initializeFont() {
        try {

            ShadowClientMain.info("Initializing font renderer");

            InputStream fontDataStream = Font.class.getResourceAsStream(FONT_PATH);
            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, fontDataStream).deriveFont(FONT_SIZE);
            fontDataStream.close();
            fontRenderer = new FontRenderer(font);

            ShadowClientMain.info("Finished initializing font renderer");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize font " + FONT_PATH + " " + e);
        }
    }

    public static void renderString(DrawContext context, String text, float x, float y, int color) {
        fontRenderer.drawText(context, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, (int) FONT_SIZE, color);
    }

    public static void renderString(VertexConsumerProvider.Immediate vertexConsumers, MatrixStack matrices, String text, float x, float y, int color) {
        fontRenderer.drawText(vertexConsumers, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, (int) FONT_SIZE, color);
    }

    public static void renderString(BufferBuilderProvider bufferBuilderProvider, MatrixStack matrices, String text, float x, float y, int color) {
        fontRenderer.drawText(bufferBuilderProvider, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, (int) FONT_SIZE, color);
    }

    public static void renderString(DrawContext context, String text, float x, float y, int color, int fontSize) {
        fontRenderer.drawText(context, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, fontSize, color);
    }

    public static void renderString(VertexConsumerProvider.Immediate vertexConsumers, MatrixStack matrices, String text, float x, float y, int color, int fontSize) {
        fontRenderer.drawText(vertexConsumers, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, fontSize, color);
    }

    public static void renderString(BufferBuilderProvider bufferBuilderProvider, MatrixStack matrices, String text, float x, float y, int color, int fontSize) {
        fontRenderer.drawText(bufferBuilderProvider, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, fontSize, color);
    }

    public static int getWidth(String text, int fontSize) {
        FontTextureAtlas atlas = fontRenderer.getAtlasForSize(fontSize);
        int width = 0;

        for (char c : text.toCharArray()) {
            FontTextureAtlas.Glyph glyph = atlas.getGlyph(c);
            width += glyph.width;
        }

        return width;
    }

    public static int getWidth(String text) {
        return getWidth(text, (int) FONT_SIZE);
    }

    public static int getWidth(Text text) {
        return getWidth(text.getString());
    }

    public static int getWidth(Text text, int fontSize) {
        return getWidth(text.getString(), fontSize);
    }

    public static int getHeight() {
        return (int) FONT_SIZE;
    }
    public static int getHeight(int fontSize) {
        return fontSize;
    }

}