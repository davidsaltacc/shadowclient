package net.justacoder.shadowclient.main.ui.font;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FontTextureAtlas {

    private static final int ATLAS_SIZE = 2048;
    private final Map<Character, Glyph> glyphMap = new HashMap<>();
    private final NativeImageBackedTexture texture;
    private final Identifier textureId;
    private final Font font;
    private int currentX = 0;
    private int currentY = 0;
    private int lineHeight = 0;

    public FontTextureAtlas(Font font) {
        this.font = font;
        this.texture = new NativeImageBackedTexture(ATLAS_SIZE, ATLAS_SIZE, true);
        this.textureId = Identifier.of("scfont_" + font.getSize());
        MinecraftClient.getInstance().getTextureManager().registerTexture(textureId, texture);
        initializeTexture();
        for (int c = 0; c < 256; c++) {
            getGlyph((char) c);
        }
    }

    private void initializeTexture() {
        NativeImage image = texture.getImage();
        image.fillRect(0, 0, ATLAS_SIZE, ATLAS_SIZE, 0x00000000);
        texture.upload();
    }

    public Glyph getGlyph(char c) {
        return glyphMap.computeIfAbsent(c, this::createGlyph);
    }

    private Glyph createGlyph(char c) {
        BufferedImage charImage = renderCharacter(c);

        int width;
        int height;

        if (charImage != null) {
            width = charImage.getWidth();
            height = charImage.getHeight();
        } else {
            width = 0;
            height = 0;
        }

        if (currentX + width >= ATLAS_SIZE) {
            currentX = 0;
            currentY += lineHeight;
            lineHeight = 0;
        }

        if (currentY + height >= ATLAS_SIZE) {
            throw new RuntimeException("Font atlas is full");
        }

        NativeImage atlasImage = texture.getImage();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = charImage.getRGB(x, y);
                atlasImage.setColorArgb(currentX + x, currentY + y, argb);
            }
        }
        texture.upload();

        if (charImage == null) {
            return new Glyph(0, 0, 0, 0, width, height);
        }

        Glyph glyph = new Glyph(
                (float) currentX / ATLAS_SIZE,
                (float) currentY / ATLAS_SIZE,
                (float) (currentX + width) / ATLAS_SIZE,
                (float) (currentY + height) / ATLAS_SIZE,
                width,
                height
        );

        currentX += width;
        lineHeight = Math.max(lineHeight, height);
        return glyph;
    }

    private BufferedImage renderCharacter(char c) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        int width = metrics.charWidth(c);
        int height = metrics.getHeight();
        g.dispose();

        if (width <= 0 || height <= 0) {
            return null;
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public Identifier getTextureId() {
        return textureId;
    }

    public static class Glyph {
        public final float u0, v0, u1, v1;
        public final int width, height;

        public Glyph(float u0, float v0, float u1, float v1, int width, int height) {
            this.u0 = u0;
            this.v0 = v0;
            this.u1 = u1;
            this.v1 = v1;
            this.width = width;
            this.height = height;
        }
    }

}
