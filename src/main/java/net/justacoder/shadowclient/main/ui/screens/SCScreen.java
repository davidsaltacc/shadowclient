package net.justacoder.shadowclient.main.ui.screens;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.config.ConfigManager;
import net.justacoder.shadowclient.main.config.HopefullyLaterConfigurableSettings;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiPart;
import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.justacoder.shadowclient.main.util.UiUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public class SCScreen extends Screen implements JsonSerializableUiPart {

    protected final String id;
    protected UiElement child;

    public SCScreen(String id, Text title, UiElement child) {
        super(title);
        this.id = id;
        setChild(child);
    }

    public void setChild(UiElement child) {
        SCScreen screen = this;
        this.child = new SingleChildUiElement(id + "-childwrapper", child) { // so even the root element has a parent whose width it can access

            @Override
            public Vector2i getNewChildPosition() {
                return new Vector2i(0, 0);
            }

            @Override
            public int getWidth() {
                return (int) Math.floor(screen.width * UiUtils.guiScaleFactor());
            }

            @Override
            public int getHeight() {
                return (int) Math.floor(screen.height * UiUtils.guiScaleFactor());
            }

            @Override
            public @NotNull JsonObject serialize() {
                return child.serialize();
            }

            @Override
            public void deserialize(@Nullable JsonObject in) {
                child.deserialize(in);
            }

        };
    }

    @Override
    protected void init() {
        this.deserialize(ConfigManager.getData(ConfigManager.ConfigType.UI_DATA, this.id));
        child.updatePositioning(0, 0);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        child.updatePositioning(0, 0);
    }

    @Override
    public void removed() {
        ConfigManager.setData(ConfigManager.ConfigType.UI_DATA, id, this.serialize());
    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.add("root_child", child.serialize());
        return object;
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {
        if (in != null && !in.has("root_child")) {
            child.deserialize(in.get("root_child").getAsJsonObject());
        }
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void renderInGameBackground(DrawContext context) {
        // no darkening
    }

    @Override
    protected void renderDarkening(DrawContext context, int x, int y, int width, int height) {
        // no darkening
    }

    @Override
    protected void applyBlur(DrawContext context) {
        if (HopefullyLaterConfigurableSettings.BLUR_BACKGROUND) {
            super.applyBlur(context);
        }
    }

    @Override
    public void render(DrawContext context, int scaledMouseX, int scaledMouseY, float deltaTicks) {

        super.render(context, scaledMouseX, scaledMouseY, deltaTicks);
        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        UiUtils.beforeUIRender(context);
        child.render(context, mouseX, mouseY, deltaTicks);
        UiUtils.afterUIRender(context);

    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {

        float disableScaleFactor = UiUtils.guiScaleFactor();

        child.mouseClicked(new Click(
                click.x() * disableScaleFactor,
                click.y() * disableScaleFactor,
                click.buttonInfo()
        ), doubled);

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {

        float disableScaleFactor = UiUtils.guiScaleFactor();

        Click scaledClick = new Click(
                click.x() * disableScaleFactor,
                click.y() * disableScaleFactor,
                click.buttonInfo()
        );

        child.mouseReleased(scaledClick);

        return super.mouseReleased(click);
    }

    @Override
    public void mouseMoved(double scaledMouseX, double scaledMouseY) {

        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        child.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double scaledMouseX, double scaledMouseY, double horizontalAmount, double verticalAmount) {

        float disableScaleFactor = UiUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

}
