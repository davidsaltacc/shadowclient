package net.justacoder.shadowclient.main.ui.elements.singlechild;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector4i;

public class PaddingElement extends SingleChildUiElement {

    private final Vector4i padding;

    public PaddingElement(UiElement child, Vector4i paddingLTRB) {
        super(null, child);
        this.padding = paddingLTRB;
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(
                x + padding.x,
                y + padding.y
        );
    }

    @Override
    public int getWidth() {
        return child.getWidth() + padding.x + padding.z;
    }

    @Override
    public int getHeight() {
        return child.getHeight() + padding.y + padding.w;
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return true;
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return true;
    }

    @Override
    public @NotNull JsonObject serialize() {
        return child.serialize();
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {
        child.deserialize(in);
    }

}
