package net.shadowclient.main.module.modules.world;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.DeathEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.util.ChatUtils;
import org.jetbrains.annotations.Nullable;

@EventListener({DeathEvent.class})
@SearchTags({"death coords", "death notification", "death coordinates"})
public class DeathNotification extends Module {
    public DeathNotification() {
        super("deathnotification", "Death Notification", "Tells you the coordinates of your death.", ModuleCategory.WORLD);
    }

    @Override
    public void onEvent(Event event) {
        @Nullable Vec3d pos = ((DeathEvent) event).pos;
        if (pos == null) {
            ChatUtils.sendMessageClient("You died. Death Location could not be resolved.");
            return;
        }
        ChatUtils.sendMessageClient("You died at " + (int) pos.x + ", " + (int) pos.y + ", " + (int) pos.z + ".");
    }
}
