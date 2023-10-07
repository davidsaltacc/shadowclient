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
        public static String BLACK = "§0";
        public static String DARK_BLUE = "§1";
        public static String DARK_GREEN = "§2";
        public static String DARK_AQUA = "§3";
        public static String DARK_RED = "§4";
        public static String DARK_PURPLE = "§5";
        public static String GOLD = "§6";
        public static String GRAY = "§7";
        public static String DARK_GRAY = "§8";
        public static String BLUE = "§9";
        public static String GREEN = "§a";
        public static String AQUA = "§b";
        public static String RED = "§c";
        public static String LIGHT_PURPLE = "§d";
        public static String YELLOW = "§e";
        public static String WHITE = "§f";
        public static String MINECOIN_GOLD = "§g";
    }
    public static class Formattings {
        public static String UNDERLINE = "§u";
        public static String BOLD = "§l";
        public static String ITALIC = "§o";
        public static String STRIKETHROUGH = "§m";
        public static String CENSORED = "§k";
        public static String RESET = "§r";
    }
}