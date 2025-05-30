package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.event.events.PacketSentEvent;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PacketReceivedEvent;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.mixininterface.IClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@EventListener({PacketReceivedEvent.class, PacketSentEvent.class, PreTickEvent.class})
@DoNotSaveState
public class Blink extends Module {

    public Blink() {
        super("blink", ModuleCategory.OTHER, new String[]{"blink", "lagging"});
        addSetting(DELAY);
    }

    private long lastBlink;

    private final NumberSetting DELAY = new NumberSetting("Delay in seconds", 0.1, 2, 2, 1);

    private final Queue<Packet<?>> outgoingPacketQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onEvent(Event event) {
        if (event instanceof PacketSentEvent evt) {
            outgoingPacketQueue.add(evt.packet);
            evt.cancel();
        }
        if (event instanceof PreTickEvent && System.currentTimeMillis() - lastBlink > DELAY.longValue() * 1000) {
            processQueuedPackets();
            lastBlink = System.currentTimeMillis();
        }
    }

    @Override
    public void onDisable() {
        if (mc.world != null) {
            processQueuedPackets();
        }
        super.onDisable();
    }

    @Override
    public void onEnable() {
        lastBlink = System.currentTimeMillis();
        super.onEnable();
    }

    @SuppressWarnings("unchecked")
    private <T extends PacketListener> void processQueuedPackets() {

        ((IClientConnection) mc.player.networkHandler.getConnection()).createEventsForOutgoing(false);
        while (!outgoingPacketQueue.isEmpty()) {
            Packet<T> packet = (Packet<T>) outgoingPacketQueue.poll();
            try {
                mc.player.networkHandler.getConnection().send(packet);
            } catch (Exception ignored) {}
        }
        ((IClientConnection) mc.player.networkHandler.getConnection()).createEventsForOutgoing(true);

    }

}
