package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.util.MiscUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

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

            registeredFontSizes.forEach(size -> {
                fontRenderer.getAtlasForSize(size);
                stringWidthCaches.put(size, new LinkedHashMap<>(1001, .75f, true) {
                    @Override
                    public boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                        return size() > 1000;
                    }
                });
            });

            SCMain.info("Finished initializing font renderer");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize font " + FONT_PATH + " " + MiscUtils.stackTraceFromThrowable(e));
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

    private static final Map<Integer, Map<String, Integer>> stringWidthCaches = new HashMap<>();

    public static int getWidth(String text, int fontSize) {

        int cached = stringWidthCaches.get(fontSize).getOrDefault(text, -1);

        if (cached != -1) {

            return cached;

        } else {

            FontTextureAtlas atlas = fontRenderer.getAtlasForSize(fontSize);
            int width = 0;

            for (char c : text.toCharArray()) {
                FontTextureAtlas.Glyph glyph = atlas.getGlyph(c);
                width += glyph.advance();
            }

            stringWidthCaches.get(fontSize).put(text, width);

            return width;

        }
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


    public static int getWidth(TranslatableString text) {
        return getWidth(text.getTranslation());
    }

    public static int getWidth(TranslatableString text, int fontSize) {
        return getWidth(text.getTranslation(), fontSize);
    }

    public static int getHeight() {
        return FONT_SIZE;
    }
    public static int getHeight(int fontSize) {
        return fontSize;
    }

}