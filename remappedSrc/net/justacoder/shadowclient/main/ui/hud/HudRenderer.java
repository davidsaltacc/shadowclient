package net.justacoder.shadowclient.main.ui.hud;

import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HudRenderer {

    public static List<HudElement> elements = new ArrayList<>();
    public static Corner corner;
    private static boolean shouldRender = true;

    public static void onHudRender(DrawContext context, float tickDelta) {
        if (!shouldRender) {
            return;
        }
        AtomicInteger offset;
        switch (corner) {
            case Bottom_Left, Bottom_Right -> offset = new AtomicInteger(context.getScaledWindowHeight() - elements.get(0).getHeight() - 4);
            default -> offset = new AtomicInteger();
        }
        List<HudElement> el = new ArrayList<>(elements);
        switch (corner) {
            case Bottom_Left, Bottom_Right -> Collections.reverse(el);
        }
        el.forEach(hudElement -> {
            if (hudElement.shouldBeRendered) {
                switch (corner) {
                    case Top_Left, Bottom_Left -> hudElement.render(context, tickDelta, offset.get());
                    default -> hudElement.render(context, tickDelta, offset.get(), true);
                }
                switch (corner) {
                    case Top_Left, Top_Right -> offset.addAndGet(hudElement.getHeight());
                    case Bottom_Left, Bottom_Right -> offset.addAndGet(-hudElement.getHeight());
                }

            }
        });
    }

    public static void addElement(HudElement el) {
        elements.add(el);
    }
    public static void removeElement(HudElement el) {
        elements.remove(el);
    }
    public static void shouldRender(boolean should) {
        shouldRender = should;
    }
    public static void setCorner(Corner c) {
        corner = c;
    }

    public enum Corner {
        Top_Left, Top_Right, Bottom_Left, Bottom_Right
    }
}
