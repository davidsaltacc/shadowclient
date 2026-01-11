package net.justacoder.shadowclient.main.ui.parts;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.justacoder.shadowclient.main.render.font.Font;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.ClickableUiElement;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// TODO only a ClickGUIFrame can be placed inside a clickgui, and any AbstractClickGUIFrameChild can be put under a ClickGUIFrame
public class ClickGUIFrame implements JsonSerializableUiElement, DrawableUiElement, ClickableUiElement {

    private final String id;
    private final TranslatableString name;
    private int x;
    private int y;
    private boolean extended;
    private int width;
    private int height;
    private final List<AbstractClickGUIFrameChild> children;

    public ClickGUIFrame(String id, TranslatableString name, int defaultX, int defaultY, boolean defaultExtended, int width, int height, List<AbstractClickGUIFrameChild> children) {
        this.id = id;
        this.name = name;
        this.x = defaultX;
        this.y = defaultY;
        this.extended = defaultExtended;
        this.width = width;
        this.height = height;
        this.children = children;
        this.children.forEach(child -> child.setParent(this));
        updateChildren();
    }

    public void updateChildren() { // update stuff like remembered offset etc., call this when modifying children
        int offset = 0;
        for (AbstractClickGUIFrameChild child : children) {
            child.setOffsetY(offset);
            offset += child.getHeight();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

        context.fill(
                x,
                y,
                x + width,
                y + height,
                Colors.FRAME_COLOR.getColor()
        );

        Font.renderString(
                context,
                name.getTranslation(),
                x + height / 2 - Font.getHeight() / 2, // offset equally from the left as from the top
                y + height / 2 - Font.getHeight() / 2, // vertically centered
                Colors.TEXT_NORMAL.getColor()
        );

        if (extended) {

            int posY = y + height;

            for (AbstractClickGUIFrameChild child : children) {
                child.render(context, x, posY, mouseX, mouseY, deltaTicks);
                posY += child.getHeight();
            }

        }

    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {

        if (extended) {
            for (AbstractClickGUIFrameChild child : children) {
                child.mouseClicked(click, doubled);
            }
        }

    }

    @Override
    public @NotNull JsonObject serialize() {

        JsonObject data = new JsonObject();
        JsonObject childrenData = new JsonObject();

        children.forEach(child -> childrenData.add(child.getId(), child.serialize()));

        data.add("children", childrenData);

        data.add("pos_x", new JsonPrimitive(x));
        data.add("pos_y", new JsonPrimitive(y));

        return data;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public void deserialize(@NotNull JsonObject in) {

        JsonObject childrenData = in.get("children").getAsJsonObject();

        childrenData.keySet().forEach(key -> children.stream().findFirst().ifPresent(child -> child.deserialize(childrenData.get(key).getAsJsonObject())));

        x = in.get("pos_x").getAsInt();
        y = in.get("pos_y").getAsInt();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
