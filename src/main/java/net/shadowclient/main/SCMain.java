package net.shadowclient.main;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.shadowclient.main.command.CommandManager;
import net.shadowclient.main.config.Config;
import net.shadowclient.main.config.SCSettings;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.ui.clickgui.ClickGUI;
import net.shadowclient.main.ui.clickgui.Frame;
import net.shadowclient.main.ui.clickgui.MainClickGUI;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import net.shadowclient.main.ui.clickgui.settings.scsettings.components.SCBoolSetting;
import net.shadowclient.main.ui.clickgui.text.TextField;
import net.shadowclient.main.util.ChatUtils;
import net.shadowclient.main.util.JavaUtils;
import net.shadowclient.mixin.KeyBindingAccessor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SCMain {

    public static final String ClientModId = "shadowclient";
    public static final String ClientName = "ShadowClient";
    public static final String ClientVersion = "0.2.0";
    public static final String ClientCommandPrefix = "sc/";

    public static MainClickGUI clickGui;
    public static ClickGUI settingsGui;

    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Logger logger = LoggerFactory.getLogger(ClientName);

    public static boolean configDeleted = false;

    public static List<KeyBinding> keyBindings = new ArrayList<>();

    public static KeyBinding ToggleGUIKeyBinding;

    public static void init() {
        try {
            info("Starting " + ClientName + " " + ClientVersion);
            ToggleGUIKeyBinding = registerKeyBinding(
                new KeyBinding(
                    "key." + ClientModId + ".togglegui",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_SHIFT,
                    "category." + ClientModId + ".clientcategory"
                ));
            CommandManager.registerCommands();
            ModuleManager.registerModules();
            clickGui = new MainClickGUI();
            settingsGui = new ClickGUI("Settings");
            initSettingsScreen(settingsGui);
            Config.loadConfig();
            Runtime.getRuntime().addShutdownHook(new Thread(SCMain::closed));
            checkConflictingMods();
            info("Finished " + ClientName + " initialization");
        } catch (Exception e) {
            error(JavaUtils.stackTraceFromThrowable(e));
        }
    }

    public static void closed() {
        if (!configDeleted) {
            Config.saveConfig();
        }
    }

    public static KeyBinding registerKeyBinding(KeyBinding bind) {
        keyBindings.add(bind);
        addKeybindCategory(bind.getCategory());
        return bind;
    }

    public static void addKeybindCategory(String category) {
        Map<String, Integer> map = KeyBindingAccessor.getCategoryMap();
        if (map.containsKey(category)) {
            return;
        }
        Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
        int largestInt = largest.orElse(0);
        map.put(category, largestInt + 1);
    }


    public static void initSettingsScreen(ClickGUI gui) {
        int offset = 5;

        Frame settingsframe = new Frame("Settings", offset, 5, 100, 14);
        gui.frames.add(settingsframe);
        offset += 105;

        Frame hideframe = new Frame("Options", offset, 5, 100, 14);
        gui.frames.add(hideframe);
        hideframe.children.add(new ModuleButton("hidesettings", hideframe, 14));
        hideframe.children.add(new ModuleButton("loaddata", hideframe, 28));
        hideframe.children.add(new ModuleButton("savedata", hideframe, 42));
        hideframe.children.add(new ModuleButton("resetdata", hideframe, 56));
        offset += 105;

        settingsframe.children.add(new SCBoolSetting(SCSettings.VanillaSpoof, settingsframe, 14));
        settingsframe.children.add(new SCBoolSetting(SCSettings.WelcomeMessage, settingsframe, 28));

        gui.searchFrame = new Frame("Search", offset, 5, 120, 14);
        gui.frames.add(gui.searchFrame);
        gui.searchFrame.children.add(new TextField(gui.searchFrame, 14, "Find Setting"));
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
        // we hate java
        AtomicReference<String> help = new AtomicReference<>("§9§l§u" + ClientName + " §o" + ClientVersion + "§r help\nPress right shift for the ClickGUI.\nRight Click a Part of the UI to expand it, expand Module Buttons for its settings. Hover over Module Buttons for a short description. Go to Minecraft's Key Binding menu to set custom keybindings. \nAvailable chat commands:\n");

        CommandManager.commands.forEach((name, cmd) -> help.set(help.get() + "  " + ClientCommandPrefix + name + "\n"));

        return help.get();
    }

    public static String getWindowTitle() {
        return getFullClientName();
    }

    public static void moduleToggleChatMessage(String moduleName) {
        ChatUtils.sendMessageClient(ChatUtils.Colors.GRAY + "[" + ChatUtils.Colors.BLUE + "Shadow" + ChatUtils.Colors.GRAY + "] " + ChatUtils.Colors.GRAY + "Toggled " + ChatUtils.Colors.WHITE + moduleName + ChatUtils.Colors.GRAY + ".");
    }

    public static boolean interceptMessage(String message) {
        return message.startsWith(ClientCommandPrefix);
    }

    public static void onWorldJoined() {
        if (!SCSettings.getSetting("WelcomeMessage").booleanValue()) {
            return;
        }
        ChatUtils.sendMessageClient("§9§l§u" + ClientName + " §o" + ClientVersion + "§r\nType " + ClientCommandPrefix + "help for useful help.");
    }

    public static @Nullable Screen allowKeyPress(@Nullable Screen screen) {
        if (screen instanceof ClickGUI && !clickGui.isAnyTextFieldCapturing() && !settingsGui.isAnyTextFieldCapturing()) {
            return null;
        }
        return screen;
    }

    public static String getFullClientName() {
        return ClientName + " " + ClientVersion;
    }

    public static void info(String text) {
        logger.info(text);
    }
    public static void warn(String text) {
        logger.warn(text);
    }
    public static void error(String text) {
        logger.error(text);
    }

    public static void checkConflictingMods() {
        if (isOptifinePresent()) {
            warn("Optifine is installed. Some modules may not work as intended.");
        }
        if (isSodiumPresent()) {
            warn("Sodium is installed. Some modules may not work as intended.");
        }
        if (isEntityCullPresent()) {
            warn("Entity Culling is installed. Some entity-related modules may not work as intended.");
        }
    }

    public static boolean isSodiumPresent() {
        return isModPresent(mod -> mod.contains("sodium"));
    }
    public static boolean isOptifinePresent() {
        return isModPresent(mod -> mod.contains("optifine") || mod.contains("optifabric"));
    }
    public static boolean isEntityCullPresent() {
        return isModPresent(mod -> mod.contains("entityculling"));
    }

    public static boolean isModPresent(Predicate<String> search) {
        Stream<String> mods = FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getMetadata).map(ModMetadata::getId);
        return mods.anyMatch(search);
    }
}
