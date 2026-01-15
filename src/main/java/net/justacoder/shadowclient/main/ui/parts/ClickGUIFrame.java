package net.justacoder.shadowclient.main.ui.parts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.render.font.Font;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.MouseInteractableUiElement;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.DrawableUiElement;
import net.justacoder.shadowclient.main.ui.JsonSerializableUiElement;
import net.justacoder.shadowclient.main.ui.animation.AnimationStateContainer;
import net.justacoder.shadowclient.main.ui.parts.framechildren.AbstractClickGUIFrameChild;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.StandardCursors;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ClickGUIFrame implements JsonSerializableUiElement, DrawableUiElement, MouseInteractableUiElement {

    private final String id;
    private final TranslatableString name;
    private int x;
    private int y;
    private boolean extended;
    private int width;
    private int height;
    private boolean dragging;
    private int draggingAtX;
    private int draggingAtY;
    private final List<AbstractClickGUIFrameChild> children;

    private final AnimationStateContainer extendedAnimationContainer = new AnimationStateContainer(0.2, true, MathUtils.Easing.EASE_IN_OUT_CUBIC);

    public ClickGUIFrame(String id, TranslatableString name, int defaultX, int defaultY, boolean defaultExtended, int width, int height, List<AbstractClickGUIFrameChild> children) {
        this.id = id;
        this.name = name;
        this.x = defaultX;
        this.y = defaultY;
        this.extended = defaultExtended;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.draggingAtX = 0;
        this.draggingAtY = 0;
        this.children = children;
        this.children.forEach(child -> child.setParent(this));
        updateChildren();
        extendedAnimationContainer.restartAnimation();
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

        if (isHovered(mouseX, mouseY)) {

            context.setCursor(StandardCursors.POINTING_HAND);

        }

        if (extendedAnimationContainer.getAnimProgressEased() > 0) {

            int scissorHeight = 0;
            int posY = y + height;

            for (AbstractClickGUIFrameChild child : children) {
                scissorHeight += child.getHeight();
            }

            context.enableScissor(x, y + height, x + width, y + height + (int) (scissorHeight * extendedAnimationContainer.getAnimProgressEased()));

            for (AbstractClickGUIFrameChild child : children) {
                child.render(context, x, posY, mouseX, mouseY, deltaTicks);
                posY += child.getHeight();
            }

            context.disableScissor();

        }

        extendedAnimationContainer.progressAnimation(deltaTicks);

    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {

        if (isHovered((int) click.x(), (int) click.y())) {

            if (click.button() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                extended = !extended;
                extendedAnimationContainer.setAnimProgressesUp(extended);
            } else if (click.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                dragging = true;
                draggingAtX = (int) click.x() - x;
                draggingAtY = (int) click.y() - y;
            }

        } else {

            if (extended) {
                for (AbstractClickGUIFrameChild child : children) {
                    child.mouseClicked(click, doubled);
                }
            }

        }

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {

        if (extended) {
            for (AbstractClickGUIFrameChild child : children) {
                child.mouseMoved(mouseX, mouseY);
            }
        }

        if (dragging) {
            x = MathHelper.clamp(
                    (int) (mouseX - draggingAtX),
                    0,
                    SCMain.mc.getWindow().getWidth() - width
            );
            y = MathHelper.clamp(
                    (int) (mouseY - draggingAtY),
                    0,
                    SCMain.mc.getWindow().getHeight() - height
            );
        }

    }

    @Override
    public void mouseReleased(Click click) {

        if (click.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            dragging = false;
            draggingAtX = 0;
            draggingAtY = 0;
        }

    }

    public boolean isHovered(int mouseX, int mouseY) {
        return
                mouseX > x &&
                mouseX < x + width &&
                mouseY > y &&
                mouseY < y + height;
    }

    @Override
    public @NotNull JsonObject serialize() {

        JsonObject data = new JsonObject();
        JsonObject childrenData = new JsonObject();

        children.forEach(child -> childrenData.add(child.getId(), child.serialize()));

        data.add("children", childrenData);

        data.add("pos_x", new JsonPrimitive(x));
        data.add("pos_y", new JsonPrimitive(y));
        data.add("extended", new JsonPrimitive(extended));

        return data;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {

        if (in == null) {
            return;
        }

        JsonElement childrenElement = in.get("children");

        if (childrenElement == null) {
            return;
        }

        JsonObject childrenData = childrenElement.getAsJsonObject();

        childrenData.getAsJsonObject().keySet().forEach(key -> children.stream().filter(child -> child.getId() == key).findFirst().ifPresent(child -> child.deserialize(childrenData.get(key).getAsJsonObject())));

        x = in.get("pos_x").getAsInt();
        y = in.get("pos_y").getAsInt();
        extended = in.get("extended").getAsBoolean();
        extendedAnimationContainer.setAnimProgressesUp(extended);
        if (extended) {
            extendedAnimationContainer.restartAnimation();
        }

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
