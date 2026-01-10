package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Font {

    public static final String FONT_PATH = "/assets/shadowclient/font/notosans-regular.ttf";
    public static final int FONT_SIZE = 14;
    public static final int FONT_OFFSET_X = 0;
    public static final int FONT_OFFSET_Y = 12; // probably not the best way, but who cares if it works

    public static FontRenderer fontRenderer;

    private static final List<Integer> registeredFontSizes = new ArrayList<>(List.of(FONT_SIZE));

    public static void initializeFont() {
        try {

            SCMain.info("Initializing font renderer");

            fontRenderer = new FontRenderer(FONT_PATH);

            registeredFontSizes.forEach(size -> fontRenderer.getAtlasForSize(size));

            SCMain.info("Finished initializing font renderer");

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

    public static List<Integer> getRegisteredFontSizes() {
        return registeredFontSizes;
    }


    public static void renderString(DrawContext context, String text, int x, int y, int color) {
        fontRenderer.drawText(context, text, x + FONT_OFFSET_X, y + FONT_OFFSET_Y, FONT_SIZE, color);
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


    // TODO uncomment once we bring back translatable strings

    //public static int getWidth(TranslatableString text) {
    //    return getWidth(text.getTranslation());
    //}

    //public static int getWidth(TranslatableString text, int fontSize) {
    //    return getWidth(text.getTranslation(), fontSize);
    //}

    public static int getHeight() {
        return FONT_SIZE;
    }
    public static int getHeight(int fontSize) {
        return fontSize;
    }

}