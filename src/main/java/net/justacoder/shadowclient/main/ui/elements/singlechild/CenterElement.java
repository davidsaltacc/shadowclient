package net.justacoder.shadowclient.main.ui.elements.singlechild;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public class CenterElement extends SingleChildUiElement {

    public CenterElement(String id, UiElement child) {
        super(id, child);
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(
                getWidth() / 2 - child.getWidth() / 2,
                getHeight() / 2 - child.getHeight() / 2
        );
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
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
