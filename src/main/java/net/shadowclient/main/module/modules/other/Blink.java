package net.shadowclient.main.module.modules.other;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.Hidden;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PacketRecievedEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import java.util.ArrayList;
import java.util.List;

@Hidden
@SearchTags({"blink"})
@EventListener({PacketRecievedEvent.class})
public class Blink extends Module { // TODO fix
    public Blink() {
        super("blink", "Blink", "Interrupts all movements packets for a while, so it looks like you are lagging.", ModuleCategory.OTHER);
    }

    public List<PlayerMoveC2SPacket> packets = new ArrayList<>();

    @Override
    public void onEvent(Event event) {
        if (!(event instanceof PacketRecievedEvent)) {
            return;
        }
        if (!(((PacketRecievedEvent) event).packet instanceof PlayerMoveC2SPacket)) {
            return;
        }

        if (packets.size() > 50) {
            packets.forEach(packet -> {
                mc.player.networkHandler.sendPacket(packet);
            });
            packets.clear();
            return;
        }

        packets.add((PlayerMoveC2SPacket) ((PacketRecievedEvent) event).packet);
        event.cancel();
    }
}
