package net.justacoder.shadowclient.main.ui.settings;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.setting.settings.ColorSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.ui.text.TextField;
import net.justacoder.shadowclient.main.util.ColorUtils;
import net.justacoder.shadowclient.mixin.DrawContextAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import java.awt.Color;

public class ColorSelectionScreen extends Screen implements ShadowClientScreen {

    private ColorSetting setting;
    private Screen parent;

    public ColorSelectionScreen(ColorSetting setting, Screen parent) {
        super(Text.of("Color Selection"));

        this.setting = setting;
        this.parent = parent;

        int[] colors = ColorUtils.int2RGBA(setting.colorValue());
        float[] hsv = Color.RGBtoHSB(colors[0], colors[1], colors[2], null);
        colorH = hsv[0];
        colorS = hsv[1];
        colorV = hsv[2];
    }

    @Override
    protected void applyBlur() {
        if (ShadowClientSettings.BlurBackground.booleanValue()) {
            super.applyBlur();
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private int contentStartX;
    private int contentStartY;
    private int contentEndX;
    private int contentEndY;
    private int padding;
    private int contentWidth;
    private int contentHeight;

    private float colorH;
    private float colorS;
    private float colorV;

    private int hueY = -1;
    private int saturationY = -1;
    private int brightnessY = -1;
    private int okayY = -1;

    private boolean slidingHue = false;
    private boolean slidingSaturation = false;
    private boolean slidingBrightness = false;

    private static final TranslatableString hueName = TranslatableString.of("name.shadowclient.color_selection.hue");
    private static final TranslatableString saturationName = TranslatableString.of("name.shadowclient.color_selection.saturation");
    private static final TranslatableString brightnessName = TranslatableString.of("name.shadowclient.color_selection.brightness");
    private static final TranslatableString colorName = TranslatableString.of("name.shadowclient.color_selection.color");
    private static final TranslatableString colorHexName = TranslatableString.of("name.shadowclient.color_selection.color_hex");
    private static final TranslatableString okayText = TranslatableString.of("name.shadowclient.color_selection.okay");

    private TextField colorField;

    private static final Identifier HUE_GRADIENT = Identifier.of("shadowclient", "textures/gui/hue_gradient.png");

    private void rescale() {
        contentWidth = 300;
        contentHeight = 235;
        padding = 5;
        contentStartX = (int) (width * UIRenderUtils.guiScaleFactor() / 2f - contentWidth / 2f);
        contentStartY = (int) (height * UIRenderUtils.guiScaleFactor() / 2f - contentHeight / 2f);
        contentEndX = (int) (width * UIRenderUtils.guiScaleFactor() / 2f + contentWidth / 2f);
        contentEndY = (int) (height * UIRenderUtils.guiScaleFactor() / 2f + contentHeight / 2f);
    }

    @Override
    protected void init() {
        super.init();
        rescale();
        colorField = new TextField(this, String.format("#%06X", 0xFFFFFF & setting.colorValue()), colorHexName, new Vector2f(0, 0), new Vector2f(contentWidth, Font.getHeight() + 4), color -> {
            try {
                if (color.length() != 4 && color.length() != 7) {
                    throw new RuntimeException("");
                }
                if (color.length() == 4) {
                    color = "" + color.charAt(0) + color.charAt(1) + color.charAt(1) + color.charAt(2) + color.charAt(2) + color.charAt(3) + color.charAt(3);
                }
                Color decoded = Color.decode(color);
                float[] hsv = Color.RGBtoHSB(decoded.getRed(), decoded.getBlue(), decoded.getGreen(), null);
                colorH = hsv[0];
                colorS = hsv[1];
                colorV = hsv[2];
                setting.setColorValue(decoded.getRGB(), false);
            } catch (Exception ignored) {
                colorH = colorS = colorV = 0;
                setting.setColorValue(-16777216, false);
            }
        });
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        rescale();
    }

    private void fillGradientHorizontal(DrawContext context, float startX, float startY, float endX, float endY, int colorStart, int colorEnd) {
        Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
        VertexConsumer vertexConsumer = ((DrawContextAccessor) context).getVertexConsumers().getBuffer(RenderLayer.getGui());
        vertexConsumer.vertex(matrix4f, startX, startY, 0f).color(colorStart);
        vertexConsumer.vertex(matrix4f, startX, endY, 0f).color(colorStart);
        vertexConsumer.vertex(matrix4f, endX, endY, 0f).color(colorEnd);
        vertexConsumer.vertex(matrix4f, endX, startY, 0f).color(colorEnd);
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        this.applyBlur();

        UIRenderUtils.beforeUIRender(context);

        context.fill(contentStartX - padding, contentStartY - padding, contentEndX + padding, contentEndY + padding, Colors.MODULE_BUTTON_NORMAL.color);

        int offset = 0;

        offset += 2;
        Font.renderString(context, hueName, contentStartX, contentStartY + offset, Colors.TEXT_NORMAL.color);
        offset += 2 + Font.getHeight();

        hueY = contentStartY + offset;
        context.drawTexture(RenderLayer::getGuiTextured, HUE_GRADIENT, contentStartX, contentStartY + offset, 0, 0, contentEndX - contentStartX, 20, 800, 1, 800, 1);
        context.drawBorder((int) (contentStartX + colorH * (contentEndX - contentStartX)) - 2, contentStartY + offset, 4, 20, Colors.MODULE_BUTTON_NORMAL.color);
        offset += 20;

        offset += 2;
        Font.renderString(context, saturationName, contentStartX, contentStartY + offset, Colors.TEXT_NORMAL.color);
        offset += 2 + Font.getHeight();

        saturationY = contentStartY + offset;
        fillGradientHorizontal(context, contentStartX, contentStartY + offset, contentEndX, contentStartY + offset + 20f, Color.HSBtoRGB(colorH, 0, colorV), Color.HSBtoRGB(colorH, 1, colorV));
        context.drawBorder((int) (contentStartX + colorS * (contentEndX - contentStartX)) - 2, contentStartY + offset, 4, 20, Colors.MODULE_BUTTON_NORMAL.color);
        offset += 20;

        offset += 2;
        Font.renderString(context, brightnessName, contentStartX, contentStartY + offset, Colors.TEXT_NORMAL.color);
        offset += 2 + Font.getHeight();

        brightnessY = contentStartY + offset;
        fillGradientHorizontal(context, contentStartX, contentStartY + offset, contentEndX, contentStartY + offset + 20f, Color.HSBtoRGB(colorH, colorS, 0), Color.HSBtoRGB(colorH, colorS, 1));
        context.drawBorder((int) (contentStartX + colorV * (contentEndX - contentStartX)) - 2, contentStartY + offset, 4, 20, Colors.MODULE_BUTTON_NORMAL.color);
        offset += 20;

        offset += 2;
        Font.renderString(context, colorName, contentStartX, contentStartY + offset, Colors.TEXT_NORMAL.color);
        offset += 2 + Font.getHeight();

        context.fill(contentStartX, contentStartY + offset, contentEndX, contentStartY + offset + 40, setting.colorValue());
        offset += 40;

        offset += 2;
        colorField.setPosition(new Vector2f(contentStartX, contentStartY + offset));
        colorField.render(context);
        offset += 2;

        offset += 40;
        okayY = contentStartY + offset;
        context.fill(contentStartX, contentStartY + offset, contentEndX, contentStartY + offset + Font.getHeight() + 4, Colors.MODULE_BUTTON_NORMAL.color);
        String text = okayText.getTranslation();
        Font.renderString(context, text, contentStartX + (contentEndX - contentStartX) / 2f - Font.getWidth(text) / 2f, contentStartY + offset + 2, Colors.TEXT_NORMAL.color);
        offset += Font.getHeight() + 4;

        UIRenderUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        if (mouseX > contentStartX && mouseX < contentEndX && mouseY > contentStartY && mouseY < contentEndY) {
            if (mouseY > hueY && mouseY < hueY + 20) {
                slidingHue = true;
            } else if (mouseY > saturationY && mouseY < saturationY + 20) {
                slidingSaturation = true;
            } else if (mouseY > brightnessY && mouseY < brightnessY + 20) {
                slidingBrightness = true;
            } else if (mouseY > okayY && mouseY < okayY + Font.getHeight() + 4) {
                setting.setColorValue(Color.HSBtoRGB(colorH, colorS, colorV), true);
                ShadowClientMain.mc.setScreen(parent);
                return super.mouseClicked(scaledMouseX, scaledMouseY, button);
            }
            this.mouseMoved(scaledMouseX, scaledMouseY);
        } else {
            slidingHue = slidingSaturation = slidingBrightness = false;
        }

        colorField.mouseClicked(scaledMouseX, scaledMouseY, button);

        return super.mouseClicked(scaledMouseX, scaledMouseY, button);

    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        slidingHue = slidingSaturation = slidingBrightness = false;

        return super.mouseReleased(mouseX, mouseY, button);

    }

    @Override
    public void mouseMoved(double scaledMouseX, double scaledMouseY) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);

        float normalizedValue = Math.clamp((float) (mouseX - contentStartX) / (contentEndX - contentStartX), 0f, 1f);

        if (slidingHue) {
            colorH = normalizedValue;
        } else if (slidingSaturation) {
            colorS = normalizedValue;
        } else if (slidingBrightness) {
            colorV = normalizedValue;
        }

        if (slidingHue || slidingSaturation || slidingBrightness) {
            setting.setColorValue(Color.HSBtoRGB(colorH, colorS, colorV), false);
            colorField.setText(String.format("#%06X", 0xFFFFFF & setting.colorValue()));
        }

        super.mouseMoved(scaledMouseX, scaledMouseY);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            ShadowClientMain.mc.setScreen(parent);
        }

        colorField.keyPressed(keyCode, scanCode, modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);

    }

    @Override
    public boolean charTyped(char chr, int modifiers) {

        colorField.charTyped(chr, modifiers);

        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean capturesKeypress(int key) {
        return colorField.capturesKeypress(key) || key == GLFW.GLFW_KEY_ESCAPE;
    }
}
