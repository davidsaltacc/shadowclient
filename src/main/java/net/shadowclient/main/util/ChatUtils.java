package net.shadowclient.main.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtils {
    public static void sendMessageClient(String text) {
        MinecraftClient.getInstance().player.sendMessage(Text.of(text));
    }
    public static void sendMessageServer(String text) {
        MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(text);
    }
    public static class Colors {
        public static final String BLACK = "§0";
        public static final String DARK_BLUE = "§1";
        public static final String DARK_GREEN = "§2";
        public static final String DARK_AQUA = "§3";
        public static final String DARK_RED = "§4";
        public static final String DARK_PURPLE = "§5";
        public static final String GOLD = "§6";
        public static final String GRAY = "§7";
        public static final String DARK_GRAY = "§8";
        public static final String BLUE = "§9";
        public static final String GREEN = "§a";
        public static final String AQUA = "§b";
        public static final String RED = "§c";
        public static final String LIGHT_PURPLE = "§d";
        public static final String YELLOW = "§e";
        public static final String WHITE = "§f";
        public static final String MINECOIN_GOLD = "§g";
    }
    public static class Formattings {
        public static final String UNDERLINE = "§u";
        public static final String BOLD = "§l";
        public static final String ITALIC = "§o";
        public static final String STRIKETHROUGH = "§m";
        public static final String CENSORED = "§k";
        public static final String RESET = "§r";
    }
}