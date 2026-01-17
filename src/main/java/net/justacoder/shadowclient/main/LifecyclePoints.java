package net.justacoder.shadowclient.main;

import net.justacoder.shadowclient.main.config.ConfigManager;
import net.minecraft.client.MinecraftClient;

import javax.swing.*;

public class LifecyclePoints {

    public static void veryEarly() {
        // do not put anything in here that references MinecraftClient indirectly (also not SCMain), as most mixins will not even be loaded at this point and things will break
    }

    public static void fabricLaunch() {
        SCMain.init();
    }

    public static void preFabricLaunch() {}

    public static void minecraftClientCreated() {
        SCMain.mc = MinecraftClient.getInstance();
        ConfigManager.loadConfig();
    }

    public static void main(String[] args) {

        JFrame parent = new JFrame();
        JLabel text = new JLabel();

        text.setText("<html>Launching the client like this is of no use. You need to install a fabric instance and put it in your mods folder.</html>");
        text.setFont(text.getFont().deriveFont(14f));

        parent.setTitle("ShadowClient Popup");
        parent.add(text);
        parent.setSize(400, 150);
        parent.setVisible(true);

    }

    public static void shutdown() {
        ConfigManager.saveConfig();
    }

}
