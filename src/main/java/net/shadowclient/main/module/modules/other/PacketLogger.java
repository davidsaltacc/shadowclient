package net.shadowclient.main.module.modules.other;

import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PacketRecievedEvent;
import net.shadowclient.main.event.events.PacketSentEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.setting.settings.StringSetting;
import net.shadowclient.main.util.ChatUtils;

@SearchTags({"packet logger"})
public class PacketLogger extends Module { // TODO log to ui instead of chat lol

    public EnumSetting<Mode> MODE = new EnumSetting<>("Mode", Mode.ALL);
    public StringSetting FILTER = new StringSetting("Filter");

    public PacketLogger() {
        super("packetlogger", "Packet Log", ModuleCategory.OTHER);
        addSettings(MODE, FILTER);
    }

    String cleanClassName(Class<?> cl) {
        String[] namessplit = cl.getName().split("\\.");
        return namessplit[namessplit.length - 1].replace("SC2", "").replace("C2S", "");
    }

    public void send(String text) {
        if (FILTER.stringValue().length() != 0) {
            if (!text.toLowerCase().contains(FILTER.stringValue().toLowerCase())) {
                return;
            }
        }
        ChatUtils.sendMessageClient(text);
    }

    @Override
    public void OnEvent(Event event) {
        if (!(event instanceof PacketSentEvent || event instanceof PacketRecievedEvent)) {
            return;
        }

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
}
