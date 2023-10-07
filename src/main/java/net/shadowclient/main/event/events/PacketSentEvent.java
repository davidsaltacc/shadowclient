package net.shadowclient.main.event.events;

import net.minecraft.network.packet.Packet;
import net.shadowclient.main.event.Event;

public class PacketSentEvent extends Event {
    public final Packet<?> packet;
    public PacketSentEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
