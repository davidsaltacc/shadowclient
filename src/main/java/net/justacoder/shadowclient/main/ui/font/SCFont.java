package net.justacoder.shadowclient.main.ui.font;

import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.awt.*;
import java.io.InputStream;

public abstract class SCFont {

    public static String FONT_PATH = "/assets/shadowclient/font/notosans-regular.ttf";
    public static float FONT_SIZE = 7f;
    public static float FONT_OFFSET_X = 0f;
    public static float FONT_OFFSET_Y = -2f;

    public static FontRenderer fontRenderer;

    public static void initializeFont() {
        try {

            SCMain.info("Initializing font renderer");

            InputStream fontDataStream = SCFont.class.getResourceAsStream(FONT_PATH);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontDataStream).deriveFont(FONT_SIZE);
            fontDataStream.close();
            fontRenderer = new FontRenderer(font);

            for (int s = 1; s <= 4; s++) {
                try {
                    fontRenderer.getAtlasForSize((int) FONT_SIZE * s);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create font atlas for size " + (int) FONT_SIZE * s + " (gui scale " + s + "): " + e);
                }
            }

            SCMain.info("Finished initializing font renderer");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize font " + FONT_PATH + " " + e);
        }
    }

    private static int getGuiScale() {
        int scale = SCMain.mc.options.getGuiScale().getValue();
        return scale == 0 ? 2 : scale;
    }

    public static void renderString(DrawContext context, String text, float x, float y, int color) {
        fontRenderer.drawText(context, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, (int) FONT_SIZE, color, getGuiScale());
    }

    public static int getWidth(String text) {
        FontTextureAtlas atlas = fontRenderer.getAtlasForSize((int) FONT_SIZE);
        int width = 0;

        for (char c : text.toCharArray()) {
            FontTextureAtlas.Glyph glyph = atlas.getGlyph(c);
            width += glyph.width;
        }

        return width;
    }

    public static int getWidth(Text text) {
        return getWidth(text.getString());
    }

    public static float getHeight() {
        return FONT_SIZE;
    }

}