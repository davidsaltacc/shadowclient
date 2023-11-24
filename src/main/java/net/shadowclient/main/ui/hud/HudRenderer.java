package net.shadowclient.main.ui.hud;

import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HudRenderer {

    public static List<HudElement> elements = new ArrayList<>();

    public static void onHudRender(DrawContext context, float tickDelta) {
        AtomicInteger offset = new AtomicInteger(); // todo different locations (bottom left, bottom right, top right)
        elements.forEach(hudElement -> {
            if (hudElement.shouldBeRendered) {
                hudElement.render(context, tickDelta, offset.get());
                offset.addAndGet(hudElement.getHeight());
            }
        });
    }

    public static void addElement(HudElement el) {
        elements.add(el);
    }
    public static void removeElement(HudElement el) {
        elements.remove(el);
    }
}
