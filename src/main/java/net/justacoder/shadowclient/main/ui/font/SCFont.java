package net.justacoder.shadowclient.main.ui.font;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.util.ColorUtils;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.justacoder.shadowclient.mixin.RenderSystemAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class SCFont {

    public static String FONT_PATH = "/assets/shadowclient/font/roboto-regular.ttf";
    public static float FONT_SIZE = 9f;

    private static Map<Integer, CharacterData[]> characterDatas = new HashMap<>();

    public static void initializeFont() {
        SCMain.info("Initializing Font Renderer");
        for (int scaleValue = 1; scaleValue <= 4; scaleValue++) {
            characterDatas.put(scaleValue, initializeFont(scaleValue));
        }
        SCMain.info("Finished Initializing Font Renderer");
    }

    private static CharacterData getCharacterData(char character) {
        return characterDatas.getOrDefault(getGuiScale(), characterDatas.get(1))[character];
    }

    private static int getGuiScale() {
        return SCMain.mc.options.getGuiScale().getValue();
    }

    public static CharacterData[] initializeFont(int guiScale) {

        try {

            InputStream fontDataStream = SCFont.class.getResourceAsStream(FONT_PATH);

            Font font = Font.createFont(Font.TRUETYPE_FONT, fontDataStream).deriveFont(FONT_SIZE * guiScale);
            fontDataStream.close();

            CharacterData[] characterData = new CharacterData[256];

            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics();
            g.dispose();

            for (int index = 0; index < characterData.length; index++) {

                char character = (char) index;
                Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", g);
                float width = (float) characterBounds.getWidth();
                float height = (float) characterBounds.getHeight();

                FontRenderContext frc = new FontRenderContext(null, true, false);
                GlyphVector gv = font.createGlyphVector(frc, new char[]{ character });
                float xAdvance = gv.getGlyphMetrics(0).getAdvanceX();

                if (width == 0 && xAdvance == 0) {
                    xAdvance = font.createGlyphVector(frc, new char[]{ ' ' }).getGlyphMetrics(0).getAdvanceX();
                }

                BufferedImage characterImage = new BufferedImage(MathHelper.ceil(xAdvance), MathHelper.ceil(height), BufferedImage.TYPE_INT_ARGB);
                g = (Graphics2D) characterImage.getGraphics();

                g.setFont(font);
                g.setColor(new Color(255, 255, 255, 0));
                g.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
                g.setColor(Color.WHITE);

                g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                g.drawString("" + character, 0, fontMetrics.getAscent());

                int textureId;

                textureId = GlStateManager._genTexture();
                createTexture(textureId, characterImage);
                characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(), xAdvance, textureId);

                g.dispose();

            }

            return characterData;

        } catch (Exception e) {

            SCMain.error("Failed to load font " + FONT_PATH + " for gui scale " + SCMain.mc.options.getGuiScale() + ": " + JavaUtils.stackTraceFromThrowable(e));

        }

        return null;

    }

    private static void createTexture(int textureId, BufferedImage image) {

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int pixel = pixels[y * image.getWidth() + x];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        GlStateManager._bindTexture(textureId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        GlStateManager._bindTexture(0);

    }

    public static void renderString(DrawContext context, String text, float x, float y, int color) {
        int[] col = ColorUtils.int2RGBA(color);
        renderString(context, text, x, y, col[0], col[1], col[2], col[3]);
    }

    public static void renderString(DrawContext context, String text, float x, float y, int red, int green, int blue, int alpha) {
        renderString(context, text, x, y, red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

    public static void renderString(DrawContext context, String text, float x, float y, float red, float green, float blue, float alpha) {

        if (text.isEmpty()) { return; }

        MatrixStack matrices = context.getMatrices();

        matrices.push();

        matrices.scale(1f / getGuiScale(), 1f / getGuiScale(), 1f);

        x += 0.5f;
        y += 0.5f;

        x *= getGuiScale();
        y *= getGuiScale();

        for (int i = 0; i < text.length(); i++) {

            char character = text.charAt(i);
            if (character > 255) continue;

            CharacterData charData = getCharacterData(character);

            drawChar(context, charData, x, y, red, green, blue, alpha);

            x += charData.advance;
        }

        context.getMatrices().pop();

        GlStateManager._bindTexture(0);

    }

    private static void drawChar(DrawContext context, CharacterData charData, float x, float y, float red, float green, float blue, float alpha) {

        RenderSystemAccessor.getShaderTextures()[0] = charData.textureId;
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(red, green, blue, alpha);
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, x, y, 0).texture(0, 0).next();
        bufferBuilder.vertex(matrix4f, x, y + charData.height, 0).texture(0, 1).next();
        bufferBuilder.vertex(matrix4f, x + charData.width, y + charData.height, 0).texture(1, 1).next();
        bufferBuilder.vertex(matrix4f, x + charData.width, y, 0).texture(1, 0).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        GlStateManager._disableBlend();
    }

    public static float getWidth(String text) {

        float width = 0;
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char character = text.charAt(i);
            if (character > 255) { continue; }
            width += getCharacterData(character).advance / getGuiScale();
        }

        return width;

    }

    public static float getHeight() {
        return FONT_SIZE;
    }

    record CharacterData(char character, float width, float height, float advance, int textureId) {}

}