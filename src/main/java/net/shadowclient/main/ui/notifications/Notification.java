package net.shadowclient.main.ui.notifications;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.ui.clickgui.Colors;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    public String title;
    public List<String> desc;

    public MinecraftClient mc;

    public int width = -999;
    public int height = -999;

    public int offX;
    public int offY;

    public Notification(String title, List<String> desc) {
        this.title = title;
        this.desc = desc;
        this.mc = SCMain.mc;
    }
    public Notification(String title, String desc) {
        this.title = title;
        this.desc = List.of(desc);
        this.mc = SCMain.mc;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta, int offsetX, int offsetY) {
        offX = offsetX;
        offY = offsetY;
        boolean hovered = isHovered(mouseX, mouseY, offsetX, offsetY);
        context.fill(offsetX, offsetY, offsetX + getWidth(), offsetY + getHeight(), hovered ? Colors.NOTIFICATION_HOVERED.color : Colors.NOTIFICATION_NORMAL.color);
        context.drawTextWithShadow(mc.textRenderer, title, offsetX + 5, offsetY + 5, Colors.TEXT_NORMAL.color);
        context.drawHorizontalLine(offsetX + 5, offsetX + getWidth() - 5, offsetY + 10 + mc.textRenderer.fontHeight, Colors.HORIZONTAL_LINE.color);
        AtomicInteger offset = new AtomicInteger(15 + mc.textRenderer.fontHeight); // java this is annoying
        desc.forEach((line) -> {
            context.drawTextWithShadow(mc.textRenderer, line, offsetX + 5, offsetY + offset.get(), Colors.TEXT_NORMAL.color);
            offset.addAndGet(5 + mc.textRenderer.fontHeight);
        });
        context.drawHorizontalLine(offsetX + 5, offsetX + getWidth() - 5, offsetY + offset.get(), Colors.HORIZONTAL_LINE.color);
        context.drawTextWithShadow(mc.textRenderer, "Click to Dismiss.", offsetX + 5, offsetY + offset.get() + 5, Colors.TEXT_DISABLED.color);
    }

    public int getHeight() {
        if (height == -999) {
            height = mc.textRenderer.fontHeight + 10 +          // title
                (mc.textRenderer.fontHeight + 5) * (desc.size() // desc
                + 1) + 10;                                       // dismiss text
        }
        return height;
    }
    public int getWidth() {
        if (width == -999) {
            int[] longest = {0}; // java, why
            desc.forEach((line) -> {
                int w = mc.textRenderer.getWidth(line);
                if (w > longest[0]) {
                    longest[0] = w;
                }
            });
            width = longest[0] + 10;
        }
        return width;
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
