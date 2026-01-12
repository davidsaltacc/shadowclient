package net.justacoder.shadowclient.main.ui.parts;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.render.font.Font;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.animation.AnimationStateContainer;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.StandardCursors;
import org.jetbrains.annotations.NotNull;

public class SimpleFrameButton extends AbstractClickGUIFrameChild {

    private final TranslatableString text;
    private final Runnable handler;

    private final AnimationStateContainer hoveredAnimationContainer = new AnimationStateContainer(0.3, false, MathUtils.Easing.EASE_IN_OUT_CUBIC);

    public SimpleFrameButton(String id, TranslatableString text, Runnable handler) {
        super(id);
        this.text = text;
        this.handler = handler;
    }

    @Override
    public void render(DrawContext context, int posX, int posY, int mouseX, int mouseY, float deltaTicks) {

        context.fill(
                posX,
                posY,
                posX + parent.getWidth(),
                posY + this.getHeight(),
                Colors.FRAME_CHILD_BACKGROUND_NORMAL.lerpWithOther(hoveredAnimationContainer.getAnimProgressEased(), Colors.FRAME_CHILD_BACKGROUND_HOVERED.getColor())
        );

        Font.renderString(
                context,
                text.getTranslation(),
                posX + getHeight() / 2 - Font.getHeight() / 2,
                posY + getHeight() / 2 - Font.getHeight() / 2,
                Colors.TEXT_NORMAL.getColor()
        );

        if (isHovered(mouseX, mouseY)) {
            context.setCursor(StandardCursors.POINTING_HAND);
        }

        hoveredAnimationContainer.progressAnimation(deltaTicks);

    }

    @Override
    public void mouseClicked(Click click, boolean doubled) {

        if (isHovered((int) click.x(), (int) click.y())) {
            handler.run();
        }

    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        hoveredAnimationContainer.setAnimProgressesUp(isHovered((int) mouseX, (int) mouseY));
    }

    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public @NotNull JsonObject serialize() { return new JsonObject(); }

    @Override
    public void deserialize(@NotNull JsonObject in) {}

}
