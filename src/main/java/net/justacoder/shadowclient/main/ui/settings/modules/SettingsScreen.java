package net.justacoder.shadowclient.main.ui.settings.modules;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.ButtonSetting;
import net.justacoder.shadowclient.main.setting.settings.KeySetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsScreen extends Screen implements ShadowClientScreen {

    public final Module module;
    public TranslatableString title;
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

    public SettingComponent.KeybindingSettingComponent toggleModuleKeybindComponent = null;
    public BooleanSetting enabledSetting;

    public SettingsScreen(Module module) {
        super(Text.of(module != null ? module.name.getTranslation() : "Setting Screen"));
        this.module = module;
        this.title = module != null ? module.name : null;

        if (module != null) {

            enabledSetting = new BooleanSetting(TranslatableString.of("name.shadowclient.enabled"), module.enabled);
            components.add(SettingComponent.ofSetting(enabledSetting, new Vector2i()));
            enabledSetting.addChangeCallback((newValue, ignored) -> {
                if ((boolean) newValue) {
                    module.setEnabled();
                } else {
                    module.setDisabled();
                }
            });

            if (!module.getClass().isAnnotationPresent(NotKeybindable.class)) {
                toggleModuleKeybindComponent = (SettingComponent.KeybindingSettingComponent) SettingComponent.ofSetting(new KeySetting(TranslatableString.of("name.shadowclient.keybind"), module.keyBinding), new Vector2i());
                components.add(toggleModuleKeybindComponent);
            }

            components.add(SettingComponent.ofSetting(new ButtonSetting(TranslatableString.of("name.shadowclient.reset_all_settings"), () -> module.getSettings().forEach(Setting::reset)), new Vector2i()));

            components.add(SettingComponent.ofSetting(new PaddingSetting(), new Vector2i()));

            for (Setting setting : module.getSettings()) {
                components.add(SettingComponent.ofSetting(setting, new Vector2i()));
            }

        }
    }

    public static class ModuleLess extends SettingsScreen {

        public ModuleLess(TranslatableString title, List<SettingComponent> components) {
            super(null);
            this.title = title;
            this.components.clear();
            this.components.addAll(components);
        }

    }

    public static Screen createSCSettings() {
        List<SettingComponent> components1 = new ArrayList<>();
        components1.add(SettingComponent.ofSetting(new ButtonSetting(TranslatableString.of("name.shadowclient.reset_all_settings"), () -> ShadowClientSettings.getAllSCSettings().values().forEach(Setting::reset)), new Vector2i(0, 0)));
        components1.addAll(ShadowClientSettings.getAllSCSettings().values().stream().map(setting -> SettingComponent.ofSetting(setting, new Vector2i(0, 0))).toList());
        return new SettingsScreen.ModuleLess(
                TranslatableString.of("name.shadowclient.sc_settings"),
                components1
        );
    }

    @Override
    protected void init() {
        rescale();
        updateComponents();
        components.forEach(SettingComponent::init);
    }

    @Override
    public boolean capturesKeypress(int key) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            return true;
        }
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
        overlayWidth = (int) Math.min(1000, width * UIRenderUtils.guiScaleFactor() - 100);
        overlayHeight = (int) Math.min(700, height * UIRenderUtils.guiScaleFactor() - 100);
        overlayStartX = (int) (width * UIRenderUtils.guiScaleFactor() / 2 - (float) overlayWidth / 2);
        overlayStartY = (int) (height * UIRenderUtils.guiScaleFactor() / 2 - (float) overlayHeight / 2);
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

        float disableScaleFactor = UIRenderUtils.guiScaleFactor(); // ClickGUI#L47
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UIRenderUtils.beforeUIRender(context);

        context.fill(overlayStartX, overlayStartY, overlayStartX + overlayWidth, overlayStartY + overlayHeight, Colors.MODULE_BUTTON_NORMAL.color);

        Font.renderString(context, title, contentStartX, contentStartY, Colors.TEXT_NORMAL.color, titleFontSize);
        context.drawHorizontalLine(contentStartX, contentEndX, contentStartY + Font.getHeight(titleFontSize) + 4, Colors.HORIZONTAL_LINE.color);

        context.enableScissor(contentStartX, contentStartY + titleOffset, contentEndX, contentEndY);
        context.getMatrices().push();
        context.getMatrices().translate(0f, scrollY, 0f);
        components.forEach(component -> component.render(context, mouseX, mouseY, mouseX > contentStartX && mouseX < contentEndX && mouseY > contentStartY && mouseY < contentEndY, delta));
        context.getMatrices().pop();
        context.disableScissor();

        UIRenderUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseScrolled(double scaledMouseX, double scaledMouseY, double horizontalAmount, double verticalAmount) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor) - (int) scrollY;
        int mouseYScreen = mouseY + (int) scrollY;

        boolean canScroll = true;
        for (SettingComponent component : components) {
            if (!component.mayScrollContainer(mouseX, mouseY, mouseX > contentStartX && mouseX < contentEndX && mouseYScreen > contentStartY + titleOffset && mouseYScreen < contentEndY)) {
                canScroll = false;
            }
            component.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount, mouseX > contentStartX && mouseX < contentEndX && mouseYScreen > contentStartY + titleOffset && mouseYScreen < contentEndY);
        }

        if (canScroll && contentActualHeight > contentEndY - contentStartY && mouseX > contentStartX && mouseX < contentEndX && mouseYScreen > contentStartY + titleOffset && mouseYScreen < contentEndY) {
            scrollY = (float) Math.clamp(scrollY + Math.signum(verticalAmount) * MathHelper.square(verticalAmount) * scrollSpeed, Math.min(0f, -(contentActualHeight - (contentEndY - contentStartY - titleOffset))), 0f);
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
            boolean configuring = false;
            for (SettingComponent component : components) {
                if (component instanceof SettingComponent.KeybindingSettingComponent settingComponent && settingComponent.isConfiguring()) {
                    configuring = true;
                }
            }
            if (!configuring) {
                client.setScreen(ShadowClientMain.clickGui);
            }
        }

        components.forEach(component -> component.keyPressed(keyCode, scanCode, modifiers));

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor) - (int) scrollY;
        int mouseYScreen = mouseY + (int) scrollY;

        components.forEach(component -> component.mouseClicked(mouseX, mouseY, button, mouseX > contentStartX && mouseX < contentEndX && mouseYScreen > contentStartY + titleOffset && mouseYScreen < contentEndY));

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor) - (int) scrollY;
        int mouseYScreen = mouseY + (int) scrollY;

        components.forEach(component -> component.mouseReleased(mouseX, mouseY, button, mouseX > contentStartX && mouseX < contentEndX && mouseYScreen > contentStartY + titleOffset && mouseYScreen < contentEndY));

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
