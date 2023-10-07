package net.shadowclient.main;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.command.CommandManager;
import net.shadowclient.main.config.ConfigFiles;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.KeyPressEvent;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.ui.clickgui.ClickGUI;
import net.shadowclient.main.util.ChatUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO NoGravity hack, maybe Speed hack, other new hacks, ui settings, more settings
public class SCMain {

    public static final String ClientModId = "shadowclient";
    public static final String ClientName = "ShadowClient";
    public static final String ClientVersion = "0.1.2";
    public static final String ClientCommandPrefix = "sc/";

    public static ClickGUI clickGui;

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static final Logger ClientLogger = LoggerFactory.getLogger(ClientName);

    public static void init() {
        info("Starting " + ClientName + " " + ClientVersion);
        info("Registering Chat Commands");
        CommandManager.registerCommands();
        info("Registering Modules");
        ModuleManager.registerModules();
        info("Creating ClickGUI");
        clickGui = new ClickGUI();
        info("Loading ConfigFiles");
        ConfigFiles.loadConfig();
        info("Fisnishing");
        Runtime.getRuntime().addShutdownHook(new Thread(SCMain::closed));
        info("Finished " + ClientName + " initialization.");
    }

    public static void closed() {
        info("Saving config");
        ConfigFiles.saveConfig();
        info("Saved");
    }

    public static void OnEvent(Event evt) {

        try {
            if (evt instanceof KeyPressEvent) {
                if (((KeyPressEvent) evt).keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT && ((KeyPressEvent) evt).action == 1) {
                    if (mc.currentScreen == null) {
                        mc.setScreen(clickGui);
                    } else if (mc.currentScreen instanceof ClickGUI) {
                        mc.setScreen(null);
                    }
                }
            }

            if (ModuleManager.getModule("disablehackupdates").enabled) {
                return;
            }
            ModuleManager.getAllModules().forEach((name, module) -> {
                if (module.enabled && !module.getClass().isAnnotationPresent(ReceiveNoUpdates.class)) {
                    module.OnEvent(evt);
                }
            });
        } catch (Exception e) {
            error("Exception while handling event: " + evt.getClass().getName() + " - " + e);
            e.printStackTrace();
        }
    }

    public static void setModuleEnabled(String name, boolean enabled) {
        if (enabled) {
            ModuleManager.getModule(name).setEnabled();
        } else {
            ModuleManager.getModule(name).setDisabled();
        }
    }

    public static void setModuleEnabled(String name, boolean enabled, boolean callevents) {
        if (enabled) {
            ModuleManager.getModule(name).setEnabled(callevents);
        } else {
            ModuleManager.getModule(name).setDisabled(callevents);
        }
    }

    public static void toggleModuleEnabled(String name) {
        setModuleEnabled(name, !ModuleManager.getModule(name).enabled);
    }

    public static String createHelp() {
        final String[] help = {"§0§l§u" + ClientName + " §o" + ClientVersion + "§r help\nPress right shift for the ClickGUI.\nRight Click a Part of the UI (Frame Title, Button) to expand it.\nAvailable chat Commands:\n"};

        CommandManager.commands.forEach((name, cmd) -> help[0] += "  " + ClientCommandPrefix + name + "\n");

        return help[0];
    }

    public static String getWindowTitle() {
        return getFullClientName();
    }

    public static boolean interceptMessage(String message) {
        return message.startsWith(ClientCommandPrefix);
    }

    public static void onWorldJoined() {
        if (ModuleManager.getModule("disablewelcomemessage").enabled) {
            return;
        }
        ChatUtils.sendMessageClient("§0§l§u" + ClientName + " §o" + ClientVersion + "§r\nType " + ClientCommandPrefix + "help for useful help.");
    }

    public static @Nullable Screen allowKeyPress(@Nullable Screen screen) {
        if (screen instanceof ClickGUI && !clickGui.isAnyTextFieldCapturing()) {
            return null;
        }
        return screen;
    }

    public static String getFullClientName() {
        return ClientName + " " + ClientVersion;
    }

    public static void info(String text) {
        ClientLogger.info(text);
    }
    public static void warn(String text) {
        ClientLogger.warn(text);
    }
    public static void error(String text) {
        ClientLogger.error(text);
    }

}
