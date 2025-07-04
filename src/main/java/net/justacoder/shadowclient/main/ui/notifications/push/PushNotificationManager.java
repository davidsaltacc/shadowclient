package net.justacoder.shadowclient.main.ui.notifications.push;

import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.util.CircularList;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public abstract class PushNotificationManager {

    private static int disappearTimeMillis = 10_500;
    private static int notificationCapacity = 20;

    private static final CircularList<PushNotification> notifications = new CircularList<>(notificationCapacity);
    private static final CircularList<Long> appearTimes = new CircularList<>(notificationCapacity);
    private static final List<Integer> toDelete = new ArrayList<>();

    public static void addNotification(PushNotification n) {
        notifications.add(n);
        appearTimes.add(System.currentTimeMillis());
    }
    public static void removeNotification(int index) {
        try {
            notifications.remove(index);
            appearTimes.remove(index);
        } catch (Exception e) { // idk
            notifications.clear();
            appearTimes.clear();
        }
    }
    public static void dismissNotification(int index) {
        toDelete.add(index);
    }

    public static void onReloadTranslations() {
        notifications.forEach(PushNotification::onReloadTranslations);
    }

    public static void renderNotifications(DrawContext context, float delta) {

        if (notifications.isEmpty()) {
            return;
        }

        UIRenderUtils.beforeUIRender(context);

        List<PushNotification> notifis = notifications.getAll().reversed(); // getAll to receive a copy that can't be modified while rendering

        ShadowClientSettings.PushNotificationCorner corner = ShadowClientSettings.pushNotificationCorner.getEnumValue();

        int offset = (corner == ShadowClientSettings.PushNotificationCorner.Top_Left || corner == ShadowClientSettings.PushNotificationCorner.Top_Right) ?
                0 :
                context.getScaledWindowHeight() * (int) UIRenderUtils.guiScaleFactor() - notifications.getFirst().getHeight();
        int index = 0;
        for (PushNotification n : notifis) {
            if (System.currentTimeMillis() - appearTimes.get(index) > disappearTimeMillis) {
                dismissNotification(index);
            }
            n.render(context, delta,
                    (corner == ShadowClientSettings.PushNotificationCorner.Top_Left || corner == ShadowClientSettings.PushNotificationCorner.Bottom_Left) ?
                    0 :
                    context.getScaledWindowWidth() * (int) UIRenderUtils.guiScaleFactor() - n.getWidth(),
            offset);
            offset += (
                    (corner == ShadowClientSettings.PushNotificationCorner.Top_Left || corner == ShadowClientSettings.PushNotificationCorner.Top_Right) ?
                            1 :
                            -1
                    ) * n.getHeight();
            index++;
        }

        toDelete.forEach(PushNotificationManager::removeNotification);
        toDelete.clear();

        UIRenderUtils.afterUIRender(context);

    }

}
