package net.justacoder.shadowclient.main.ui.elements.multiplechild;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ui.elements.MultipleChildrenUiElement;
import net.justacoder.shadowclient.main.ui.elements.Properties;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class StackContainerElement extends MultipleChildrenUiElement {

    private final Properties.Direction direction;
    private final Properties.Size sizeX;
    private final Properties.Size sizeY;

    public StackContainerElement(List<UiElement> children, Properties.Direction direction, Properties.Size sizeX, Properties.Size sizeY) {
        super(null, children);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.direction = direction;
    }

    @Override
    public List<Vector2i> getNewChildPositions() {
        List<Vector2i> list = new ArrayList<>();
        int offset = 0;
        for (UiElement child : children) {
            list.add(new Vector2i(
                    direction == Properties.Direction.VERTICAL ? x : x + offset,
                    direction == Properties.Direction.HORIZONTAL ? y : y + offset
            ));
            offset += direction == Properties.Direction.VERTICAL ? child.getHeight() : child.getWidth();
        }
        return list;
    }

    private int maxChildWidth() {
        int max = 0;
        for (UiElement child : children) {
            max = Math.max(child.getWidth(), max);
        }
        return max;
    }

    private int maxChildHeight() {
        int max = 0;
        for (UiElement child : children) {
            max = Math.max(child.getHeight(), max);
        }
        return max;
    }

    private int totalChildWidth() {
        int total = 0;
        for (UiElement child : children) {
            total += child.getWidth();
        }
        return total;
    }

    private int totalChildHeight() {
        int total = 0;
        for (UiElement child : children) {
            total += child.getHeight();
        }
        return total;
    }

    private int width = 0;
    private int height = 0;
    private boolean cachedWidthInvalid = true;
    private boolean cachedHeightInvalid = true;

    @Override
    public void updatePositioning(int newX, int newY) {
        cachedWidthInvalid = true;
        cachedHeightInvalid = true;
        super.updatePositioning(newX, newY);
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
    public int getWidth() {
        if (sizeX.type == Properties.SizeType.FIXED_SIZE) {
            return sizeX.length;
        }
        if (cachedWidthInvalid) {
            width = sizeX.type == Properties.SizeType.FILL_PARENT ? parent.getWidth() : (direction == Properties.Direction.VERTICAL ? maxChildWidth() : totalChildWidth());
            cachedWidthInvalid = false;
        }
        return width;
    }

    @Override
    public int getHeight() {
        if (sizeY.type == Properties.SizeType.FIXED_SIZE) {
            return sizeY.length;
        }
        if (cachedHeightInvalid) {
            height = sizeY.type == Properties.SizeType.FILL_PARENT ? parent.getHeight() : (direction == Properties.Direction.HORIZONTAL ? maxChildHeight() : totalChildHeight());
            cachedHeightInvalid = false;
        }
        return height;
    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject object = new JsonObject();
        //children.forEach(child -> object.add(child.getId(), child.serialize()));
        return object;
    }

    @Override
    public void deserialize(@Nullable JsonObject in) {
        if (in != null) {
            in.keySet().forEach(key ->
                    children.stream().filter(
                            child -> child.getId().equals(key)
                    ).findFirst().ifPresent(
                            child -> child.deserialize(in.get(key).getAsJsonObject())
                    )
            );
        }
    }

}
