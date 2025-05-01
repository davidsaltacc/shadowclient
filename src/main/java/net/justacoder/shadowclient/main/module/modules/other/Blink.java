package net.justacoder.shadowclient.main.module.modules.other;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.Hidden;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PacketRecievedEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import java.util.ArrayList;
import java.util.List;

@Hidden
@EventListener({PacketRecievedEvent.class})
public class Blink extends Module { // TODO fix
    public Blink() {
        super("blink", ModuleCategory.OTHER, new String[]{"blink", "lagging"});
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
            packets.forEach(packet -> mc.player.networkHandler.sendPacket(packet));
            packets.clear();
            return;
        }

        packets.add((PlayerMoveC2SPacket) ((PacketRecievedEvent) event).packet);
        event.cancel();
    }
}
