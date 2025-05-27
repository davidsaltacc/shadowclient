package net.justacoder.shadowclient.main.ui.font;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.font.FontRenderer;
import net.justacoder.shadowclient.main.render.font.FontTextureAtlas;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;

public abstract class Font {

    public static final String FONT_PATH = "/assets/shadowclient/font/notosans-regular.ttf";
    public static final int FONT_SIZE = 14;
    public static final float FONT_OFFSET_X = 0f;
    public static final float FONT_OFFSET_Y = 12f; // probably not the best way, but who cares if it works

    public static FontRenderer fontRenderer;

    private static final List<Integer> registeredFontSizes = new ArrayList<>(List.of(FONT_SIZE));

    public static void initializeFont() {
        try {

            ShadowClientMain.info("Initializing font renderer");

            fontRenderer = new FontRenderer(FONT_PATH);

            registeredFontSizes.forEach(size -> fontRenderer.getAtlasForSize(size));

            ShadowClientMain.info("Finished initializing font renderer");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize font " + FONT_PATH + " " + e);
        }
    }

    public static void registerFontSize(int size) {
        if (isFontSizeRegistered(size)) {
            return;
        }
        registeredFontSizes.add(size);
    }

    public static boolean isFontSizeRegistered(int size) {
        return registeredFontSizes.contains(size);
    }

    public static void renderString(DrawContext context, String text, float x, float y, int color) {
        fontRenderer.drawText(context, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, FONT_SIZE, color);
    }

    public static void renderString(VertexConsumerProvider.Immediate vertexConsumers, MatrixStack matrices, String text, float x, float y, int color) {
        fontRenderer.drawText(vertexConsumers, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, FONT_SIZE, color);
    }

    public static void renderString(BufferBuilderProvider bufferBuilderProvider, MatrixStack matrices, String text, float x, float y, int color) {
        fontRenderer.drawText(bufferBuilderProvider, matrices, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, FONT_SIZE, color);
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
            width += glyph.advance();
        }

        return width;
    }

    public static int getWidth(String text) {
        return getWidth(text, FONT_SIZE);
    }

    public static int getWidth(Text text) {
        return getWidth(text.getString());
    }

    public static int getWidth(Text text, int fontSize) {
        return getWidth(text.getString(), fontSize);
    }

    public static int getHeight() {
        return FONT_SIZE;
    }
    public static int getHeight(int fontSize) {
        return fontSize;
    }

}