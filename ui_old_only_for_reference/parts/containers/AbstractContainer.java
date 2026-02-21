package net.justacoder.shadowclient.main.ui_old.parts.containers;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainer extends ContainerChild  {

    private final String id;
    protected final List<ContainerChild> children = new ArrayList<>();

    protected AbstractContainer(String id, int x, int y, List<ContainerChild> initialChildren) {
        super(x, y);
        this.id = id;
        initialChildren.forEach(this::addChild);
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    public abstract void addChild(ContainerChild child);
    public abstract void updateChildrenPositioning();

    @Override
    public void mouseClicked(Click click, boolean doubled) {
        children.forEach(child -> child.mouseClicked(click, doubled));
    }

    @Override
    public void mouseReleased(Click click) {
        children.forEach(child -> child.mouseReleased(click));
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        children.forEach(child -> child.mouseMoved(mouseX, mouseY));
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        children.forEach(child -> child.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        children.forEach(child -> child.render(context, mouseX, mouseY, deltaTicks));
    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject data = new JsonObject();
        children.forEach(child -> data.add(child.getId(), child.serialize()));
        return data;
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

    @Override
    public void screenSizeChanged(int width, int height) {
        updateChildrenPositioning();
        children.forEach(child -> child.screenSizeChanged(width, height));
    }

}
