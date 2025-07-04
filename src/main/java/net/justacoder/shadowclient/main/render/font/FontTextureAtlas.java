package net.justacoder.shadowclient.main.render.font;

import net.justacoder.shadowclient.main.util.JavaUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.*;
import static org.lwjgl.util.freetype.FreeType.*;

public class FontTextureAtlas {

    private static final int ATLAS_SIZE = 2048;
    private final Map<Character, Glyph> glyphMap = new HashMap<>();
    private final NativeImageBackedTexture texture;
    private final Identifier textureId;
    private final int fontSize;
    private int currentX = 0;
    private int currentY = 0;
    private int lineHeight = 0;

    private boolean initialized = false;
    private long ftLibrary;
    private ByteBuffer fontBuffer;
    private FT_Face face;

    public FontTextureAtlas(String font, int size) throws IOException {
        this.fontSize = size;
        this.texture = new NativeImageBackedTexture(ATLAS_SIZE, ATLAS_SIZE, true);
        this.textureId = Identifier.of("scfont_" + size);
        MinecraftClient.getInstance().getTextureManager().registerTexture(textureId, texture);
        initializeTexture();

        initFreeType();
        fontBuffer = JavaUtils.resourceToByteBuffer(font);
        face = loadFace(fontBuffer, fontSize);

        for (int c = 0; c < 256; c++) {
            getGlyph((char) c);
        }

        FT_Done_Face(face);
        FT_Done_FreeType(ftLibrary);
        MemoryUtil.memFree(fontBuffer);

        initialized = true;

    }

    private void initFreeType() {
        PointerBuffer pLib = MemoryUtil.memAllocPointer(1);
        int err = FT_Init_FreeType(pLib);
        if (err != 0) {
            throw new RuntimeException("Could not init FreeType: " + err);
        }
        ftLibrary = pLib.get(0);
        MemoryUtil.memFree(pLib);
    }

    private FT_Face loadFace(ByteBuffer fontData, int pixelSize) {
        PointerBuffer pFace = MemoryUtil.memAllocPointer(1);
        int err = FT_New_Memory_Face(ftLibrary, fontData, 0, pFace);
        if (err != 0) {
            throw new RuntimeException("Error creating face from memory (err=" + err + ")");
        }
        FT_Face f = FT_Face.create(pFace.get(0));
        FT_Set_Pixel_Sizes(f, 0, pixelSize);
        MemoryUtil.memFree(pFace);
        return f;
    }

    private void initializeTexture() {
        NativeImage image = texture.getImage();
        image.fillRect(0, 0, ATLAS_SIZE, ATLAS_SIZE, 0x00000000);
        texture.upload();
    }

    public Glyph getGlyph(char c) {
        if (!initialized) {
            return glyphMap.computeIfAbsent(c, this::createGlyph);
        } else {
            return glyphMap.getOrDefault(c, glyphMap.get((char) 0));
        }
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

        FT_Glyph_Metrics metrics = face.glyph().metrics();
        int bearingX = (int) metrics.horiBearingX() >> 6;
        int bearingY = (int) metrics.horiBearingY() >> 6;
        int advance = (int) metrics.horiAdvance() >> 6;

        if (charImage == null) {
            return new Glyph(0, 0, 0, 0, width, height, bearingX, bearingY, advance);
        }

        Glyph glyph = new Glyph(
                (float) currentX / ATLAS_SIZE,
                (float) currentY / ATLAS_SIZE,
                (float) (currentX + width) / ATLAS_SIZE,
                (float) (currentY + height) / ATLAS_SIZE,
                width,
                height,
                bearingX,
                bearingY,
                advance
        );

        currentX += width;
        lineHeight = Math.max(lineHeight, height);
        return glyph;
    }

    private BufferedImage renderCharacter(char c) { // TODO doing the whole 2d iteration process twice. unnecessary

        int err = FT_Load_Char(face, c, FT_LOAD_RENDER);
        if (err != 0) {
            throw new RuntimeException("Failed to load glyph for '" + c + "' (err=" + err + ")");
        }
        FT_Bitmap bitmap = face.glyph().bitmap();

        int w = bitmap.width();
        int h = bitmap.rows();

        if (w <= 0 || h <= 0) {
            return null;
        }

        ByteBuffer buffer = bitmap.buffer(w * h);

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int alpha = buffer.get(y * w + x) & 0xFF;
                int pixel = (alpha << 24) | 0x00FFFFFF;
                image.setRGB(x, y, pixel);
            }
        }

        return image;

    }

    //private BufferedImage renderCharacter(char c) {
//
    //    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    //    Graphics2D g = image.createGraphics();
    //    g.setFont(font);
    //    FontMetrics metrics = g.getFontMetrics();
    //    int width = metrics.charWidth(c);
    //    int height = metrics.getHeight();
    //    g.dispose();
//
    //    if (width <= 0 || height <= 0) {
    //        return null;
    //    }
//
    //    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    //    g = image.createGraphics();
    //    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    //    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    //    g.setFont(font);
    //    g.setColor(new Color(255, 255, 255, 0));
    //    g.fillRect(0, 0, width, height);
    //    g.setColor(Color.WHITE);
    //    g.drawString(String.valueOf(c), 0, metrics.getAscent());
    //    g.dispose();
//
    //    return image;
    //}

    public Identifier getTextureId() {
        return textureId;
    }

    public record Glyph(float u0, float v0, float u1, float v1, int width, int height, int bearingX, int bearingY, int advance) {}

}
