package net.shadowclient.main.event.events;

import net.minecraft.network.packet.Packet;
import net.shadowclient.main.event.Event;

public class PacketRecievedEvent extends Event {
    public final Packet<?> packet;
    public PacketRecievedEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
