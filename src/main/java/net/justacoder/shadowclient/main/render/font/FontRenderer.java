package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.RenderingTypes;
import net.justacoder.shadowclient.mixin.DrawContextAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
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


    public void drawText(DrawContext context, String text, float x, float y, int fontSize, int color) {
        drawText(((DrawContextAccessor) context).getVertexConsumers(), context.getMatrices(), text, x, y, fontSize, color);
    }

    public void drawText(VertexConsumerProvider provider, MatrixStack matrices, String text, float x, float y, int fontSize, int color) {
        FontTextureAtlas atlas = getAtlasForSize(fontSize);
        drawText(provider.getBuffer(RenderLayer.getText(atlas.getTextureId())), matrices.peek().getPositionMatrix(), text, x, y, fontSize, color);
    }

    public void drawText(BufferBuilderProvider provider, MatrixStack matrices, String text, float x, float y, int fontSize, int color, boolean depthTest) {
        FontTextureAtlas atlas = getAtlasForSize(fontSize);
        drawText(provider.getBufferBuilder(RenderingTypes.getText(atlas.getTextureId(), depthTest)), matrices.peek().getPositionMatrix(), text, x, y, fontSize, color);
    }

    public void drawText(BufferBuilderProvider provider, MatrixStack matrices, String text, float x, float y, int fontSize, int color) {
        drawText(provider, matrices, text, x, y, fontSize, color, false);
    }

    private void drawText(VertexConsumer consumer, Matrix4f matrix, String text, float x, float y, int fontSize, int color) {

        FontTextureAtlas atlas = getAtlasForSize(fontSize);
        FontTextureAtlas.Glyph glyph;

        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        float a = (color >> 24 & 255) / 255f;

        float cursorX = x;
        for (char c : text.toCharArray()) {
            glyph = atlas.getGlyph(c); // so we basically just fill the font atlas over time and hope it never runs out of space. great. love me the lack of optimization

            consumer.vertex(matrix, cursorX, y, 0).color(r, g, b, a).texture(glyph.u0, glyph.v0).light(15728880); // apparently the maximum value
            consumer.vertex(matrix, cursorX, y + glyph.height, 0).color(r, g, b, a).texture(glyph.u0, glyph.v1).light(15728880);
            consumer.vertex(matrix, cursorX + glyph.width, y + glyph.height, 0).color(r, g, b, a).texture(glyph.u1, glyph.v1).light(15728880);
            consumer.vertex(matrix, cursorX + glyph.width, y, 0).color(r, g, b, a).texture(glyph.u1, glyph.v0).light(15728880);

            cursorX += glyph.width;
        }

    }

    public FontTextureAtlas getAtlasForSize(int size) {
        return atlases.computeIfAbsent(size, s ->
                new FontTextureAtlas(baseFont.deriveFont((float) s))
        );
    }

}
