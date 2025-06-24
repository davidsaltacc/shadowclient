package net.justacoder.shadowclient.main.ui.notifications.push;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.gui.DrawContext;

public class PushNotification {

    public TranslatableString text;

    public int width = -999;
    public int height = -999;
    public int offX;
    public int offY;

    public PushNotification(String text) {
        this.text = TranslatableString.of(text);
    }

    public void onReloadTranslations() {
        width = -999;
        getWidth();
    }

    public void render(DrawContext context, float delta, int offsetX, int offsetY) {
        offX = offsetX;
        offY = offsetY;
        context.fill(offsetX, offsetY, offsetX + getWidth(), offsetY + getHeight(), Colors.NOTIFICATION_NORMAL.color);
        Font.renderString(context, text, offsetX + 4, offsetY + 4, Colors.TEXT_NORMAL.color);
    }

    public int getHeight() {
        if (height == -999) {
            height = Font.getHeight() + 4 + 4;
        }
        return height;
    }

    public int getWidth() {
        if (width == -999) {
            width = Font.getWidth(text.getTranslation()) + 4 + 4;
        }
        return width;
    }

}
