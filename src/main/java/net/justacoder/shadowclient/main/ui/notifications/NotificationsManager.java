package net.justacoder.shadowclient.main.ui.notifications;

import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class NotificationsManager {

    public static final List<Notification> notifications = new ArrayList<>();
    public static final List<Notification> toDelete = new ArrayList<>();

    public static void addNotification(Notification n) {
        notifications.add(n);
    }
    public static void removeNotification(Notification n) {
        notifications.remove(n);
    }
    public static void dismissNotification(Notification n) {
        toDelete.add(n);
    }

    public static void reloadTranslations() {
        notifications.forEach(Notification::reloadTranslations);
    }

    public static void renderNotifications(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        UIRenderUtils.beforeUIRender(context);

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor(); // see ClickGUI.render() for the reason using enableGuiScaleFactor()
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        AtomicInteger offset = new AtomicInteger(10);
        notifications.forEach(n -> {
            n.render(context, mouseX, mouseY, delta, 10, offset.get());
            offset.addAndGet(n.getHeight() + 10);
        });

        UIRenderUtils.afterUIRender(context);

    }

    public static void mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.enableGuiScaleFactor(); // see ClickGUI.render() for the reason using enableGuiScaleFactor()
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        notifications.forEach(n -> n.mouseClicked(mouseX, mouseY, button));
        toDelete.forEach(NotificationsManager::removeNotification);
        toDelete.clear();

    }

}
