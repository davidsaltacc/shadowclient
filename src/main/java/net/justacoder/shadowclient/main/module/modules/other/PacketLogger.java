package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.notifications.push.PushNotification;
import net.justacoder.shadowclient.main.ui.notifications.push.PushNotificationManager;
import net.minecraft.network.packet.Packet;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PacketReceivedEvent;
import net.justacoder.shadowclient.main.event.events.PacketSentEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.setting.settings.StringSetting;
import net.justacoder.shadowclient.main.util.ChatUtils;

@EventListener({PacketSentEvent.class, PacketReceivedEvent.class})
public class PacketLogger extends Module {

    public final EnumSetting<Mode> MODE = new EnumSetting<>(new TranslatableString("setting.module.shadowclient.packetlogger.mode"), Mode.ALL);
    public final StringSetting FILTER = new StringSetting(new TranslatableString("setting.module.shadowclient.packetlogger.filter"));
    public final EnumSetting<FMode> FMODE = new EnumSetting<>(new TranslatableString("setting.module.shadowclient.packetlogger.filter_mode"), FMode.WHITELIST);

    public PacketLogger() {
        super("packetlogger", ModuleCategory.OTHER, new String[]{"packet logger", "packetlogger"});
        addSettings(MODE, FILTER, FMODE);
    }

    private static final TranslatableString SENT_TEXT = new TranslatableString("name.shadowclient.module.packetlogger.sent");
    private static final TranslatableString RECEIVED_TEXT = new TranslatableString("name.shadowclient.module.packetlogger.received");

    public String packetName(Packet<?> cl) {
        return cl.getPacketType().id().getPath();
    }

    public boolean filter(String text) {
        if (text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.BLACKLIST) {
            return true;
        }
        return !text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.WHITELIST;
    }

    public void send(String text) {
        if (!FILTER.stringValue().isEmpty() && filter(text)) {
            return;
        }

        PushNotificationManager.addNotification(new PushNotification(text));
    }

    @Override
    public void onEvent(Event event) {
        if (MODE.getEnumValue() == Mode.ALL) {
            if (event instanceof PacketReceivedEvent evt) {
                send(RECEIVED_TEXT.getTranslation() + " " + packetName(evt.packet));
                return;
            }
            send(SENT_TEXT.getTranslation() + " " + packetName(((PacketSentEvent) event).packet));
            return;
        }
        if (MODE.getEnumValue() == Mode.RECEIVED && event instanceof PacketReceivedEvent evt) {
            send(RECEIVED_TEXT.getTranslation() + " " + packetName(evt.packet));
            return;
        }
        if (MODE.getEnumValue() == Mode.SENT && event instanceof PacketSentEvent evt) {
            send(SENT_TEXT.getTranslation() + " " + packetName(evt.packet));
        }
    }

    public enum Mode {
        ALL("All"),
        RECEIVED("Received"),
        SENT("Sent");


        final String name;
        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum FMode {
        BLACKLIST("Blacklist"),
        WHITELIST("Whitelist");


        final String name;
        FMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
