package net.justacoder.shadowclient.main.ui.elements.singlechild;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.elements.Properties;
import net.justacoder.shadowclient.main.ui.elements.SingleChildUiElement;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

// element of certain size containing a single child. no padding, no other shenanigans. can be used to constrain something (like a center)
public class FixedBoxElement extends SingleChildUiElement {

    private final Properties.Size sizeX;
    private final Properties.Size sizeY;

    public FixedBoxElement(UiElement child, Properties.Size sizeX, Properties.Size sizeY) {
        super(null, child);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public Vector2i getNewChildPosition() {
        return new Vector2i(x, y);
    }

    @Override
    public int getWidth() {
        if (sizeX.type == Properties.SizeType.FIXED_SIZE) {
            return sizeX.length;
        } else if (sizeX.type == Properties.SizeType.FIT_CHILDREN) {
            return child.getWidth();
        } else if (sizeX.type == Properties.SizeType.FILL_PARENT) {
            return parent.getWidth();
        }
        return 0;
    }

    @Override
    public int getHeight() {
        if (sizeY.type == Properties.SizeType.FIXED_SIZE) {
            return sizeY.length;
        } else if (sizeY.type == Properties.SizeType.FIT_CHILDREN) {
            return child.getHeight();
        } else if (sizeY.type == Properties.SizeType.FILL_PARENT) {
            return parent.getHeight();
        }
        return 0;
    }

    @Override
    public boolean widthReliesOnChildWidths() {
        return sizeX.type == Properties.SizeType.FIT_CHILDREN;
    }

    @Override
    public boolean heightReliesOnChildHeights() {
        return sizeY.type == Properties.SizeType.FIT_CHILDREN;
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
