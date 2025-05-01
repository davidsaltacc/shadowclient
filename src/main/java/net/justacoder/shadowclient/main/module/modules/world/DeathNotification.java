package net.justacoder.shadowclient.main.module.modules.world;

import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.DeathEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.util.ChatUtils;
import org.jetbrains.annotations.Nullable;

@EventListener({DeathEvent.class})
public class DeathNotification extends Module {
    public DeathNotification() {
        super("deathnotification", ModuleCategory.WORLD, new String[]{"death coords", "death notification", "death coordinates"});
    }

    @Override
    public void onEvent(Event event) {
        @Nullable Vec3d pos = ((DeathEvent) event).pos;
        if (pos == null) {
            ChatUtils.sendMessageClient("You died. Death location could not be found.");
            return;
        }
        ChatUtils.sendMessageClient("You died at " + (int) pos.x + ", " + (int) pos.y + ", " + (int) pos.z + ".");
    }
}
