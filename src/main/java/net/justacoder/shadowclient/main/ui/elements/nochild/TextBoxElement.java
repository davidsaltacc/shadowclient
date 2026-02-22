package net.justacoder.shadowclient.main.ui.elements.nochild;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.render.font.Font;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextBoxElement extends UiElement {

    private final TranslatableString content;
    private final int color;

    public TextBoxElement(TranslatableString content, int color) {
        super(null);
        this.content = content;
        this.color = color;
    }

    @Override
    public int getWidth() {
        return Font.getWidth(content.getTranslation());
    }

    @Override
    public int getHeight() {
        return Font.getHeight();
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return false;
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        Font.renderString(context, content.getTranslation(), x, y, color);
    }

    @Override public void mouseClicked(Click click, boolean doubled) {}
    @Override public void mouseReleased(Click click) {}
    @Override public void mouseMoved(double mouseX, double mouseY) {}
    @Override public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {}
    @Override public @NotNull JsonObject serialize() { return new JsonObject(); }
    @Override public void deserialize(@Nullable JsonObject in) {}

}
