package net.shadowclient.main.event.events;

import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.event.Event;
import org.jetbrains.annotations.Nullable;

public class DeathEvent extends Event {
    public @Nullable Vec3d pos;

    public DeathEvent(@Nullable Vec3d pos) {
        this.pos = pos;
    }
}
