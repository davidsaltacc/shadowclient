package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PacketRecievedEvent;
import net.shadowclient.main.event.events.PacketSentEvent;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.setting.settings.StringSetting;
import net.shadowclient.main.util.ChatUtils;

@EventListener({PacketSentEvent.class, PacketRecievedEvent.class})
@SearchTags({"packet logger"})
public class PacketLogger extends Module {

    public final EnumSetting<Mode> MODE = new EnumSetting<>("Mode", Mode.ALL);
    public final StringSetting FILTER = new StringSetting("Filter");
    public final EnumSetting<FMode> FMODE = new EnumSetting<>("Filter", FMode.WHITELIST);

    public PacketLogger() {
        super("packetlogger", "Packet Log", "See what packets are being sent between you and the server. ", ModuleCategory.OTHER);
        addSettings(MODE, FILTER, FMODE);
    }

    public String cleanClassName(Class<?> cl) {
        String[] namessplit = cl.getName().split("\\.");
        return namessplit[namessplit.length - 1].replace("SC2", "").replace("C2S", "");
    }

    public boolean filter(String text) {
        if (text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.BLACKLIST) {
            return true;
        }
        return !text.toLowerCase().contains(FILTER.stringValue().toLowerCase()) && FMODE.getEnumValue() == FMode.WHITELIST;
    }

    public void send(String text) {
        if (FILTER.stringValue().length() != 0) {
            if (filter(text)) {
                return;
            }
        }
        ChatUtils.sendMessageClient(text);
    }

    @Override
    public void onEvent(Event event) {

        if (MODE.getEnumValue() == Mode.ALL) {
            if (event instanceof PacketRecievedEvent) {
                send(ChatUtils.Formattings.ITALIC + "RECEIVED " + ChatUtils.Formattings.RESET + cleanClassName(((PacketRecievedEvent) event).packet.getClass()));
                return;
            }
            send(ChatUtils.Formattings.ITALIC + "SENT " + ChatUtils.Formattings.RESET + cleanClassName(((PacketSentEvent) event).packet.getClass()));
            return;
        }
        if (MODE.getEnumValue() == Mode.RECEIVED && event instanceof PacketRecievedEvent) {
            send(ChatUtils.Formattings.ITALIC + "RECEIVED " + ChatUtils.Formattings.RESET + cleanClassName(((PacketRecievedEvent) event).packet.getClass()));
            return;
        }
        if (MODE.getEnumValue() == Mode.SENT && event instanceof PacketSentEvent) {
            send(ChatUtils.Formattings.ITALIC + "SENT " + ChatUtils.Formattings.RESET + cleanClassName(((PacketSentEvent) event).packet.getClass()));
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
