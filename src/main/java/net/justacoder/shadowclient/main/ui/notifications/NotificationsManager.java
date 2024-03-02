package net.justacoder.shadowclient.main.ui.notifications;

import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationsManager {

    public static List<Notification> notifications = new ArrayList<>();

    public static List<Notification> toDelete = new ArrayList<>();

    public static void addNotification(Notification n) {
        notifications.add(n);
    }
    public static void removeNotification(Notification n) {
        notifications.remove(n);
    }
    public static void dismissNotification(Notification n) {
        toDelete.add(n);
    }

    public static void renderNotifications(DrawContext context, int mouseX, int mouseY, float delta) {
        AtomicInteger offset = new AtomicInteger(5); // I hate java, once again
        notifications.forEach((n) -> {
            n.render(context, mouseX, mouseY, delta, 5, offset.get());
            offset.addAndGet(n.getHeight() + 5);
        });
    }

    public static void mouseClicked(double mouseX, double mouseY, int button) {
        notifications.forEach((n) -> n.mouseClicked((int) mouseX, (int) mouseY, button));
        toDelete.forEach(NotificationsManager::removeNotification);
    }

}
