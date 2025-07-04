package net.justacoder.shadowclient.main.ui.notifications;

import net.justacoder.shadowclient.main.config.ShadowClientSettings;
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

    public static void onReloadTranslations() {
        notifications.forEach(Notification::onReloadTranslations);
    }

    public static void renderNotifications(DrawContext context, int scaledMouseX, int scaledMouseY, float delta) {

        if (notifications.isEmpty()) {
            return;
        }

        UIRenderUtils.beforeUIRender(context);

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        ShadowClientSettings.NotificationCorner corner = ShadowClientSettings.notificationCorner.getEnumValue();

        AtomicInteger offset = new AtomicInteger(
                (corner == ShadowClientSettings.NotificationCorner.Top_Left || corner == ShadowClientSettings.NotificationCorner.Top_Right) ?
                        10 :
                        context.getScaledWindowHeight() * (int) UIRenderUtils.guiScaleFactor() - notifications.getFirst().getHeight() - 10
        );
        notifications.forEach(n -> {
            n.render(context, mouseX, mouseY, delta,
                    ((corner == ShadowClientSettings.NotificationCorner.Top_Left || corner == ShadowClientSettings.NotificationCorner.Bottom_Left) ?
                            10 :
                            context.getScaledWindowWidth() * (int) UIRenderUtils.guiScaleFactor() - n.getWidth() - 10),
            offset.get());
            offset.addAndGet(
                    ((corner == ShadowClientSettings.NotificationCorner.Top_Left || corner == ShadowClientSettings.NotificationCorner.Top_Right) ?
                            1 :
                            -1) *
                    (n.getHeight() + 10)
            );
        });

        UIRenderUtils.afterUIRender(context);

    }

    public static void mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        notifications.forEach(n -> n.mouseClicked(mouseX, mouseY, button));
        toDelete.forEach(NotificationsManager::removeNotification);
        toDelete.clear();

    }

}
