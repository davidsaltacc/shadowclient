package net.justacoder.shadowclient.main;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.render.Renderer;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.ui.clickgui.settings.modules.SettingsScreen;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import net.justacoder.shadowclient.main.command.CommandManager;
import net.justacoder.shadowclient.main.config.Config;
import net.justacoder.shadowclient.main.config.ShadowClientSettings;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.clickgui.ClickGUI;
import net.justacoder.shadowclient.main.ui.clickgui.Frame;
import net.justacoder.shadowclient.main.ui.clickgui.MainClickGUI;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;
import net.justacoder.shadowclient.main.ui.clickgui.settings.scsettings.components.SCBoolSetting;
import net.justacoder.shadowclient.main.ui.clickgui.text.TextField;
import net.justacoder.shadowclient.main.ui.notifications.Notification;
import net.justacoder.shadowclient.main.ui.notifications.NotificationsManager;
import net.justacoder.shadowclient.main.util.ChatUtils;
import net.justacoder.shadowclient.main.util.JavaUtils;
import net.justacoder.shadowclient.mixin.KeyBindingAccessor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class ShadowClientMain {

    public static final String CLIENT_MOD_ID = "shadowclient";
    public static final String CLIENT_NAME = "ShadowClient";
    public static final String CLIENT_VERSION = "0.3.0";
    public static final String CLIENT_COMMAND_PREFIX = "sc/";

    public static MainClickGUI clickGui;
    public static ClickGUI settingsGui;

    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Logger logger = LoggerFactory.getLogger(CLIENT_NAME);

    public static boolean configDeleted = false;

    public static final List<KeyBinding> keyBindings = new ArrayList<>();
    public static final List<KeyBinding> moduleKeyBindings = new ArrayList<>();

    public static KeyBinding toggleGUIKeyBinding;
    public static SimpleOption<Integer> guiScaleOption;

    public static boolean mayWriteConfig = false;

    public static void init() {
        try {
            info("Starting " + CLIENT_NAME + " " + CLIENT_VERSION);
            toggleGUIKeyBinding = registerKeyBinding(
                new KeyBinding(
                    "key." + CLIENT_MOD_ID + ".togglegui",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_RIGHT_SHIFT,
                    "category." + CLIENT_MOD_ID + ".clientcategory"
                )
            );
            CommandManager.registerCommands();
            ModuleManager.registerModules();
            clickGui = new MainClickGUI();
            settingsGui = new ClickGUI("Settings");
            initSettingsScreen(settingsGui);
            mayWriteConfig = true;
            Runtime.getRuntime().addShutdownHook(new Thread(ShadowClientMain::closed));
            Config.loadConfig();
            checkConflictingMods();
            registerAllFontSizes();
            info("Finished " + CLIENT_NAME + " initialization");

        } catch (Exception e) {
            error("Error starting client: \n" + JavaUtils.stackTraceFromThrowable(e));
        }
    }

    public static void closed() {
        Thread.currentThread().setName("ShadowClient Shutdown");
        if (!configDeleted) {
            Config.saveConfig();
        }
    }

    public static void registerAllFontSizes() {
        Font.registerFontSize(Renderer.FONT_SIZE);
        Font.registerFontSize(SettingsScreen.titleFontSize);
    }

    public static KeyBinding registerKeyBinding(KeyBinding bind) {
        keyBindings.add(bind);
        addKeybindCategory(bind.getCategory());
        return bind;
    }

    public static KeyBinding registerKeyBinding(KeyBinding bind, boolean module) {
        if (module) {
            moduleKeyBindings.add(bind);
        }
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

    public static void reloadTranslations() {
        ModuleManager.getAllModules().forEach((name, module) -> module.reloadTranslations());
        for (ModuleCategory category : ModuleCategory.values()) { category.reloadTranslations(); }
        Frame.allFrames.forEach(Frame::reloadTranslation);
        NotificationsManager.reloadTranslations();
        TextField.allTextFields.forEach(TextField::reloadTranslations);
    }

    public static void initSettingsScreen(ClickGUI gui) {
        int offset = 10;

        Frame settingsframe = Frame.createWithoutAddingModules(ModuleCategory.SETTINGS, offset, 10, 200, 26);
        gui.frames.add(settingsframe);
        offset += 210;

        Frame hideframe = Frame.createWithoutAddingModules(ModuleCategory.OPTIONS, offset, 10, 200, 26);
        gui.frames.add(hideframe);
        hideframe.children.add(new ModuleButton("hidesettings", hideframe, 26));
        hideframe.children.add(new ModuleButton("loaddata", hideframe, 52));
        hideframe.children.add(new ModuleButton("savedata", hideframe, 78));
        hideframe.children.add(new ModuleButton("resetdata", hideframe, 104));
        offset += 210;

        settingsframe.children.add(new SCBoolSetting(ShadowClientSettings.VanillaSpoof, settingsframe, 26));
        settingsframe.children.add(new SCBoolSetting(ShadowClientSettings.ChatMessages, settingsframe, 52));
        settingsframe.children.add(new SCBoolSetting(ShadowClientSettings.BlurBackground, settingsframe, 78));

        gui.searchFrame = Frame.createWithoutAddingModules(ModuleCategory.SEARCH, offset, 10, 240, 26);
        gui.frames.add(gui.searchFrame);
        gui.searchFrame.children.add(new TextField(gui.searchFrame, 26, "textfield.placeholder.find_setting"));
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
    public static void setModuleEnabled(String name, boolean enabled, boolean callevents, boolean message) {
        if (enabled) {
            ModuleManager.getModule(name).setEnabled(callevents, message);
        } else {
            ModuleManager.getModule(name).setDisabled(callevents, message);
        }
    }

    public static void toggleModuleEnabled(String name) {
        setModuleEnabled(name, !ModuleManager.getModule(name).enabled);
    }

    public static void mainClickGUIClosed() {
        ModuleManager.endKeybindConfiguration();
    }

    public static String createHelp() {
        String help = "§9§l§u" + CLIENT_NAME + " §o" + CLIENT_VERSION + "§r help\nPress right shift for the ClickGUI.\nRight click a part of the UI to expand it, expand module buttons for its settings. Hover over module buttons for a short description. Use the \"Configure Keybinds\" button to change the keybinds. \nAvailable chat commands:\n";
        help += String.join("\n", CommandManager.commands.keySet().stream().map(name -> CLIENT_COMMAND_PREFIX + name).toList());
        return help;
    }

    public static String getWindowTitle() {
        return getFullClientName();
    }

    public static void moduleToggleChatMessage(String moduleName, boolean enabled) {
        if (!ShadowClientSettings.ChatMessages.booleanValue()) {
            return;
        }
        ChatUtils.sendMessageClient(ChatUtils.Colors.GRAY + "[" + ChatUtils.Colors.BLUE + "Shadow" + ChatUtils.Colors.GRAY + "] " + ChatUtils.Colors.GRAY + (enabled ? "Enabled " : "Disabled ") + ChatUtils.Colors.WHITE + moduleName + ChatUtils.Colors.GRAY + ".");
    }

    public static boolean interceptMessage(String message) {
        return message.startsWith(CLIENT_COMMAND_PREFIX);
    }

    public static void onWorldJoined() {
        if (!((BooleanSetting) ShadowClientSettings.getSetting("ChatMessages")).booleanValue()) {
            return;
        }
        ChatUtils.sendMessageClient("§9§l§u" + CLIENT_NAME + " §o" + CLIENT_VERSION + "§r\nType " + CLIENT_COMMAND_PREFIX + "help for useful help.");
    }

    public static @Nullable Screen allowKeyPress(@Nullable Screen screen, int key) {
        if (ModuleManager.isConfiguringKeyBinds()) {
            return screen;
        }
        if (screen instanceof ClickGUI && !clickGui.isAnyTextFieldCapturing() && !settingsGui.isAnyTextFieldCapturing() || (screen instanceof SettingsScreen && !((SettingsScreen) screen).interceptKeypresses())) {
            if (key == GLFW.GLFW_KEY_ESCAPE) {
                return screen;
            }
            return null;
        }
        return screen;
    }

    public static String getFullClientName() {
        return CLIENT_NAME + " " + CLIENT_VERSION;
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

    public static void notification(String title, List<String> desc) {
        NotificationsManager.addNotification(new Notification(title, desc));
    }
    public static void notification(String title, String desc) {
        NotificationsManager.addNotification(new Notification(title, desc));
    }

    public static void checkConflictingMods() {
        String warningTitle = "name.shadowclient.sc_warning";
        if (isOptifinePresent()) {
            warn("Optifine is installed");
            notification(warningTitle, "warning.shadowclient.optifine");
        }
        if (isSodiumPresent()) {
            warn("Sodium is installed");
            notification(warningTitle, "warning.shadowclient.sodium");
        }
        if (isEntityCullPresent()) {
            warn("EntityCulling is installed");
            notification(warningTitle, "warning.shadowclient.entityculling");
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

    public static void println(Object object) {
        Bootstrap.println(String.valueOf(object));
    }
}
