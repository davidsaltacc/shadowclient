package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.SCMain;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontRenderer {

    private final Map<Integer, FontTextureAtlas> atlases = new HashMap<>();
    private final String fontPath;

    public FontRenderer(String fontPath) {
        this.fontPath = fontPath;
    }

    public void drawText(DrawContext context, String text, int x, int y, int fontSize, int color) {

        FontTextureAtlas atlas = getAtlasForSize(fontSize);
        FontTextureAtlas.Glyph glyph;

        int advX = x;

        for (char c : text.toCharArray()) {
            glyph = atlas.getGlyph(c);

            context.drawTexture(RenderPipelines.GUI_TEXTURED, atlas.getTextureId(),
                    advX + glyph.bearingX(),
                    y - glyph.bearingY(),
                    glyph.regionStartX(),
                    glyph.regionStartY(),
                    glyph.width(),
                    glyph.height(),
                    glyph.regionWidth(),
                    glyph.regionHeight(),
                    atlas.getSize().x,
                    atlas.getSize().y,
                    color
            );

            advX += glyph.advance();
        }

    }

    public FontTextureAtlas getAtlasForSize(int size) {
        if (!Font.isFontSizeRegistered(size)) {
            throw new RuntimeException("Tried to get a font atlas for a size that was not registered and generated on launch (" + size + ")! (Registered: " + Font.getRegisteredFontSizes() + ")");
        }
        return atlases.computeIfAbsent(size, s ->
                {
                    try {
                        return new FontTextureAtlas(fontPath, s);
                    } catch (IOException e) {
                        SCMain.error("Failed to load font atlas");
                        throw new RuntimeException(e);
                    }
                }
        );
    }

}