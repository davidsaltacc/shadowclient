package net.justacoder.shadowclient.main.ui.font;

import net.justacoder.shadowclient.mixin.DrawContextAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FontRenderer {

    private final Map<Integer, FontTextureAtlas> atlases = new HashMap<>();
    private final Font baseFont;

    public FontRenderer(Font baseFont) {
        this.baseFont = baseFont;
    }

    public void drawText(DrawContext context, String text, float x, float y, int fontSize, int color, int guiScale) {
        FontTextureAtlas atlas = getAtlasForSize(fontSize * guiScale);
        FontTextureAtlas.Glyph glyph;
        context.getMatrices().push();
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        VertexConsumer vertexConsumer = ((DrawContextAccessor) context).getVertexConsumers().getBuffer(
                RenderLayer.getText(atlas.getTextureId())
        );
        matrix.scale(1f / guiScale, 1f / guiScale, 1f);

        x *= guiScale;
        y *= guiScale;

        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        float a = (color >> 24 & 255) / 255f;

        float cursorX = x;
        for (char c : text.toCharArray()) {
            glyph = atlas.getGlyph(c); // so we basically just fill the font atlas over time and hope it never runs out of space. great.

            vertexConsumer.vertex(matrix, cursorX, y, 0).color(r, g, b, a).texture(glyph.u0, glyph.v0).light(15728880); // minecraft uses this value. I don't know its purpose. Apparently it's the maximum value.
            vertexConsumer.vertex(matrix, cursorX, y + glyph.height, 0).color(r, g, b, a).texture(glyph.u0, glyph.v1).light(15728880);
            vertexConsumer.vertex(matrix, cursorX + glyph.width, y + glyph.height, 0).color(r, g, b, a).texture(glyph.u1, glyph.v1).light(15728880);
            vertexConsumer.vertex(matrix, cursorX + glyph.width, y, 0).color(r, g, b, a).texture(glyph.u1, glyph.v0).light(15728880);

            cursorX += glyph.width;
        }

        context.getMatrices().pop();
    }

    public FontTextureAtlas getAtlasForSize(int size) {
        return atlases.computeIfAbsent(size, s ->
                new FontTextureAtlas(baseFont.deriveFont((float) s))
        );
    }

}
