package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.render.BufferBuilderProvider;
import net.justacoder.shadowclient.main.render.RenderingTypes;
import net.justacoder.shadowclient.mixin.DrawContextAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontRenderer {

    private final Map<Integer, FontTextureAtlas> atlases = new HashMap<>();
    private final String fontPath;

    public FontRenderer(String fontPath) {
        this.fontPath = fontPath;
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

        float advX = x;

        for (char c : text.toCharArray()) {
            glyph = atlas.getGlyph(c);

            consumer.vertex(matrix, advX + glyph.bearingX(), y - glyph.bearingY(), 0).color(r, g, b, a).texture(glyph.u0(), glyph.v0()).light(15728880); // x0 y0
            consumer.vertex(matrix, advX + glyph.bearingX(), y - glyph.bearingY() + glyph.height(), 0).color(r, g, b, a).texture(glyph.u0(), glyph.v1()).light(15728880); // x0 y1
            consumer.vertex(matrix, advX + glyph.bearingX() + glyph.width(), y - glyph.bearingY() + glyph.height(), 0).color(r, g, b, a).texture(glyph.u1(), glyph.v1()).light(15728880); // x1 y1
            consumer.vertex(matrix, advX + glyph.bearingX() + glyph.width(), y - glyph.bearingY(), 0).color(r, g, b, a).texture(glyph.u1(), glyph.v0()).light(15728880); // x1 y0

            advX += glyph.advance();
        }

    }

    public FontTextureAtlas getAtlasForSize(int size) {
        if (!net.justacoder.shadowclient.main.ui.font.Font.isFontSizeRegistered(size)) {
            throw new RuntimeException("Tried to get a font atlas for a size that was not registered and generated on launch!");
        }
        return atlases.computeIfAbsent(size, s ->
                {
                    try {
                        return new FontTextureAtlas(fontPath, s);
                    } catch (IOException e) {
                        ShadowClientMain.error("Failed to load font atlas");
                        throw new RuntimeException(e);
                    }
                }
        );
    }

}
