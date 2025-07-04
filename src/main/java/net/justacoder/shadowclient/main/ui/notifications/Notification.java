package net.justacoder.shadowclient.main.ui.notifications;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.ui.Colors;
import net.minecraft.client.render.RenderLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    public TranslatableString title;
    public List<TranslatableString> desc;

    public MinecraftClient mc;

    private TranslatableString dismissText = TranslatableString.of("name.shadowclient.click_to_dismiss");

    public int offX;
    public int offY;

    public Notification(String title, List<String> desc) {
        this.title = TranslatableString.of(title);
        this.desc = new ArrayList<>();
        desc.forEach(s -> this.desc.add(TranslatableString.of(s)));
        this.mc = ShadowClientMain.mc;
    }
    public Notification(String title, String desc) {
        this.title = TranslatableString.of(title);
        this.desc = new ArrayList<>();
        this.desc.add(TranslatableString.of(desc));
        this.mc = ShadowClientMain.mc;
    }

    public void onReloadTranslations() {
        getWidth();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta, int offsetX, int offsetY) {
        offX = offsetX;
        offY = offsetY;
        boolean hovered = isHovered(mouseX, mouseY, offsetX, offsetY);
        int w = getWidth();
        context.fill(RenderLayer.getGuiOverlay(), offsetX, offsetY, offsetX + w, offsetY + getHeight(), hovered ? Colors.NOTIFICATION_HOVERED.color : Colors.NOTIFICATION_NORMAL.color);
        Font.renderString(context, title, offsetX + 10, offsetY + 10, Colors.TEXT_NORMAL.color);
        context.drawHorizontalLine(offsetX + 10, offsetX + w - 10, offsetY + 20 + Font.getHeight(), Colors.HORIZONTAL_LINE.color);
        AtomicInteger offset = new AtomicInteger(30 + Font.getHeight());
        desc.forEach(line -> {
            Font.renderString(context, line, offsetX + 10, offsetY + offset.get(), Colors.TEXT_NORMAL.color);
            offset.addAndGet(10 + Font.getHeight());
        });
        context.drawHorizontalLine(offsetX + 10, offsetX + w - 10, offsetY + offset.get(), Colors.HORIZONTAL_LINE.color);
        Font.renderString(context, dismissText, offsetX + 10, offsetY + offset.get() + 10, Colors.NOTIFICATION_CLICK_TO_DISMISS.color);

    }

    public int getHeight() {
        return 50 + Font.getHeight() * 2 + (Font.getHeight() + 10) * desc.size();
    }
    public int getWidth() {
        int[] longest = {Math.max(Font.getWidth(dismissText), Font.getWidth(title)) };
        desc.forEach(line -> longest[0] = Math.max(Font.getWidth(line), longest[0]));
        return longest[0] + 20;
    }

    public boolean isHovered(int mouseX, int mouseY, int offsetX, int offsetY) {
        return mouseX > offsetX && mouseX < offsetX + getWidth() && mouseY > offsetY && mouseY < offsetY + getHeight();
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY, offX, offY) && button == 0) {
            NotificationsManager.dismissNotification(this);
        }
    }
}
