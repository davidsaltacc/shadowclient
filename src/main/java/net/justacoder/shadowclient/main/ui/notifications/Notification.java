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
        context.fill(RenderLayer.getGuiOverlay(), offsetX, offsetY, offsetX + getWidth(), offsetY + getHeight(), hovered ? Colors.NOTIFICATION_HOVERED.color : Colors.NOTIFICATION_NORMAL.color);
        Font.renderString(context, friendlyTitle, offsetX + 5, offsetY + 5, Colors.TEXT_NORMAL.color);
        context.drawHorizontalLine(offsetX + 5, offsetX + getWidth() - 5, offsetY + 10 + (int) Font.getHeight(), Colors.HORIZONTAL_LINE.color);
        AtomicInteger offset = new AtomicInteger(15 + (int) Font.getHeight()); // java this is annoying
        friendlyDesc.forEach(line -> {
            Font.renderString(context, line, offsetX + 5, offsetY + offset.get(), Colors.TEXT_NORMAL.color);
            offset.addAndGet(5 + (int) Font.getHeight());
        });
        context.drawHorizontalLine(offsetX + 5, offsetX + getWidth() - 5, offsetY + offset.get(), Colors.HORIZONTAL_LINE.color);
        Font.renderString(context, dismissText, offsetX + 5, offsetY + offset.get() + 5, Colors.TEXT_DISABLED.color);

    }

    public int getHeight() {
        if (height == -999) {
            height = (int) Font.getHeight() + 10 +          // title
                ((int) Font.getHeight() + 5) * (desc.size() // desc
                + 1) + 10;                                    // dismiss text
        }
        return height;
    }
    public int getWidth() {
        if (width == -999) {
            int[] longest = { (int) Math.max(Font.getWidth(dismissText), Font.getWidth(friendlyTitle)) };
            friendlyDesc.forEach(line -> longest[0] = Math.max((int) Font.getWidth(line), longest[0]));
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
