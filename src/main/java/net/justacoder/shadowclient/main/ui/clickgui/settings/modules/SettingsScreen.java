package net.justacoder.shadowclient.main.ui.clickgui.settings.modules;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends Screen {

    public final Module module;
    public final List<SettingComponent> components = new ArrayList<>();

    public static final int titleFontSize = 16;

    private int overlayWidth;
    private int overlayHeight;
    private int overlayStartX;
    private int overlayStartY;
    private int contentStartX;
    private int contentStartY;
    private int contentEndX;
    private int contentEndY;
    private int contentActualHeight;

    private int padding = 10;
    private int titleOffset = 9 + Font.getHeight(titleFontSize);

    private float scrollY = 0;
    private float scrollSpeed = 10;

    public SettingsScreen(Module module) {
        super(Text.of(module.friendlyName));
        this.module = module;
        BooleanSetting enabledSetting = new BooleanSetting("Enabled", module.enabled);
        components.add(SettingComponent.ofSetting(enabledSetting, new Vector2i()));
        enabledSetting.addChangeCallback((newValue, ignored) -> {
            if ((boolean) newValue) { module.setEnabled(); }
            else { module.setDisabled(); }
        });
        components.add(SettingComponent.ofSetting(new PaddingSetting(""), new Vector2i()));
        for (Setting setting : module.getSettings()) {
            components.add(SettingComponent.ofSetting(setting, new Vector2i()));
        }
    }

    @Override
    protected void init() {
        rescale();
        updateComponents();
        components.forEach(FrameChild::init);
    }

    public boolean interceptKeypresses() {
        boolean intercept = false;
        for (SettingComponent component : components) {
            if (component.interceptKeypresses()) {
                intercept = true;
            }
        }
        return intercept;
    }

    private void updateComponents() {
        int offset = 0;
        for (SettingComponent component : components) {
            component.updatePosition(new Vector2i(contentStartX, contentStartY + titleOffset + offset));
            offset += component.getHeight();
        }
        contentActualHeight = offset;
    }

    public void rescale() {
        overlayWidth = (int) Math.min(1000, width * UIRenderUtils.enableGuiScaleFactor() - 100);
        overlayHeight = (int) Math.min(700, height * UIRenderUtils.enableGuiScaleFactor() - 100);
        overlayStartX = (int) (width * UIRenderUtils.enableGuiScaleFactor() / 2 - (float) overlayWidth / 2);
        overlayStartY = (int) (height * UIRenderUtils.enableGuiScaleFactor() / 2 - (float) overlayHeight / 2);
        contentStartX = overlayStartX + padding;
        contentStartY = overlayStartY + padding;
        contentEndX = overlayStartX + overlayWidth - padding;
        contentEndY = overlayStartY + overlayHeight - padding;
        scrollY = 0f;
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {

        super.resize(client, width, height);

        rescale();
        updateComponents();

    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        this.applyBlur();

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor(); // ClickGUI#L47
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UIRenderUtils.beforeUIRender(context);

        context.fill(overlayStartX, overlayStartY, overlayStartX + overlayWidth, overlayStartY + overlayHeight, Colors.MODULE_BUTTON_NORMAL.color);

        Font.renderString(context, module.friendlyName, contentStartX, contentStartY, Colors.TEXT_NORMAL.color, titleFontSize);
        context.drawHorizontalLine(contentStartX, contentEndX, contentStartY + Font.getHeight(titleFontSize) + 4, Colors.HORIZONTAL_LINE.color);

        context.enableScissor(contentStartX, contentStartY + titleOffset, contentEndX, contentEndY);
        context.getMatrices().push();
        context.getMatrices().translate(0f, scrollY, 0f);
        components.forEach(component -> component.render(context, mouseX, mouseY, delta));
        context.getMatrices().pop();
        context.disableScissor();

        UIRenderUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseScrolled(double scaledMouseX, double scaledMouseY, double horizontalAmount, double verticalAmount) {

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        boolean canScroll = true;
        for (SettingComponent component : components) {
            if (!component.mayScrollContainer(mouseX, mouseY)) {
                canScroll = false;
            }
            component.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }

        if (canScroll && contentActualHeight > contentEndY - contentStartY && mouseX > overlayStartX && mouseX < overlayStartX + overlayWidth && mouseY > overlayStartY && mouseY < overlayStartY + overlayHeight) {
            scrollY = (float) Math.clamp(scrollY + Math.signum(verticalAmount) * MathHelper.square(verticalAmount) * scrollSpeed, -(contentActualHeight - (contentEndY - contentStartY - padding)), 0f);
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            client.setScreen(ShadowClientMain.clickGui);
        }

        components.forEach(component -> component.keyPressed(keyCode, scanCode, modifiers));

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        components.forEach(component -> component.mouseClicked(mouseX, mouseY, button));

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        components.forEach(component -> component.mouseReleased(mouseX, mouseY, button));

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void applyBlur() {
        if (ShadowClientSettings.BlurBackground.booleanValue()) {
            super.applyBlur();
        }
    }

}
