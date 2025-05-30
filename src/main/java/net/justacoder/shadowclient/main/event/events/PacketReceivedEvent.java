package net.justacoder.shadowclient.main.event.events;

import net.minecraft.network.packet.Packet;
import net.justacoder.shadowclient.main.event.Event;

public class PacketReceivedEvent extends Event {
    public final Packet<?> packet;
    public PacketReceivedEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
