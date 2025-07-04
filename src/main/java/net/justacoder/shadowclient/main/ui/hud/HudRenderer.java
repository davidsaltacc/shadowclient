package net.justacoder.shadowclient.main.ui.hud;

import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.setting.SettingEnum;
import net.justacoder.shadowclient.main.translations.TranslatableString;
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
        UIRenderUtils.beforeUIRender(context);
        if (!shouldRender) {
            return;
        }
        AtomicInteger offset;
        switch (corner) {
            case Bottom_Left, Bottom_Right -> offset = new AtomicInteger(context.getScaledWindowHeight() * (int) UIRenderUtils.guiScaleFactor() - elements.get(0).getHeight() - 4);
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
        UIRenderUtils.afterUIRender(context);
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

    public enum Corner implements SettingEnum {
        Top_Left("name.settingenum.shadowclient.corner.top_left"),
        Top_Right("name.settingenum.shadowclient.corner.top_right"),
        Bottom_Left("name.settingenum.shadowclient.corner.bottom_left"),
        Bottom_Right("name.settingenum.shadowclient.corner.bottom_right");

        Corner(String key) {
            this.name = TranslatableString.of(key);
        }

        private TranslatableString name;

        @Override
        public TranslatableString fullName() {
            return name;
        }
    }
}
