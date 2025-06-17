package net.justacoder.shadowclient.main.ui.notifications.push;

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

        UIRenderUtils.beforeUIRender(context);

        List<PushNotification> notifis = notifications.getAll().reversed(); // getAll to receive a copy that can't be changed while rendering

        int offset = 0;
        int index = 0;
        for (PushNotification n : notifis) {
            if (System.currentTimeMillis() - appearTimes.get(index) > disappearTimeMillis) {
                dismissNotification(index);
            }
            n.render(context, delta, 0, offset);
            offset += n.getHeight();
            index++;
        }

        toDelete.forEach(PushNotificationManager::removeNotification);
        toDelete.clear();

        UIRenderUtils.afterUIRender(context);

    }

}
