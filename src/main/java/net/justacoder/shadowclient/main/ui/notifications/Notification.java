package net.justacoder.shadowclient.main.ui.notifications;

import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    public String title;
    public List<String> desc;
    public String friendlyTitle;
    public List<String> friendlyDesc;

    public MinecraftClient mc;

    private String dismissText = "";

    public int width = -999;
    public int height = -999;
    public int offX;
    public int offY;

    public Notification(String title, List<String> desc) {
        this.title = title;
        this.desc = desc;
        this.friendlyTitle = "";
        this.friendlyDesc = new ArrayList<>(Collections.nCopies(desc.size(), ""));
        this.mc = ShadowClientMain.mc;
    }
    public Notification(String title, String desc) {
        this.title = title;
        this.desc = List.of(desc);
        this.friendlyTitle = "";
        this.friendlyDesc = new ArrayList<>();
        friendlyDesc.add("");
        this.mc = ShadowClientMain.mc;
    }

    public void reloadTranslations() {
        this.friendlyTitle = I18n.translate(title);
        this.dismissText = I18n.translate("name.shadowclient.click_to_dismiss");
        for (int i = 0; i < desc.size(); i++) {
            friendlyDesc.set(i, I18n.translate(desc.get(i)));
        }

        width = -999;
        getWidth();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta, int offsetX, int offsetY) {
        offX = offsetX;
        offY = offsetY;
        boolean hovered = isHovered(mouseX, mouseY, offsetX, offsetY);
        int w = getWidth();
        context.fill(RenderLayer.getGuiOverlay(), offsetX, offsetY, offsetX + w, offsetY + getHeight(), hovered ? Colors.NOTIFICATION_HOVERED.color : Colors.NOTIFICATION_NORMAL.color);
        Font.renderString(context, friendlyTitle, offsetX + 10, offsetY + 10, Colors.TEXT_NORMAL.color);
        context.drawHorizontalLine(offsetX + 10, offsetX + w - 10, offsetY + 20 + Font.getHeight(), Colors.HORIZONTAL_LINE.color);
        AtomicInteger offset = new AtomicInteger(30 + Font.getHeight()); // java this is annoying
        friendlyDesc.forEach(line -> {
            Font.renderString(context, line, offsetX + 10, offsetY + offset.get(), Colors.TEXT_NORMAL.color);
            offset.addAndGet(10 + Font.getHeight());
        });
        context.drawHorizontalLine(offsetX + 10, offsetX + w - 10, offsetY + offset.get(), Colors.HORIZONTAL_LINE.color);
        Font.renderString(context, dismissText, offsetX + 10, offsetY + offset.get() + 10, Colors.TEXT_DISABLED.color);

    }

    public int getHeight() {
        if (height == -999) {
            height = (Font.getHeight() + 20) * (desc.size() // desc
                + 2); // title and dismiss text
        }
        return height;
    }
    public int getWidth() {
        if (width == -999) {
            int[] longest = {Math.max(Font.getWidth(dismissText), Font.getWidth(friendlyTitle)) };
            friendlyDesc.forEach(line -> longest[0] = Math.max(Font.getWidth(line), longest[0]));
            width = longest[0] + 20;
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
