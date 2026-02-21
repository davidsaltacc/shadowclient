package net.justacoder.shadowclient.main;

import net.justacoder.shadowclient.main.config.ConfigManager;
import net.justacoder.shadowclient.main.events.KeyEvent;
import net.justacoder.shadowclient.main.keybinds.Key;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.elements.nochild.TextBoxElement;
import net.justacoder.shadowclient.main.ui.elements.singlechild.CenterElement;
import net.justacoder.shadowclient.main.ui.screens.SCScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SCMain {

    public static final String VERSION = /*$ mod_version*/ "0.3";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.11";
    public static final String NAME = /*$ mod_name*/ "ShadowClient";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static @Nullable MinecraftClient mc = null;

    public static final Key OPEN_GUI_KEYBIND = new Key(GLFW.GLFW_KEY_RIGHT_SHIFT);

    public static void init() {

        info("Loading {} version {} for Minecraft {}", NAME, VERSION, MINECRAFT);
        info("This project was proudly made without the use of generative AI.");

        info("{} config directory at {}", NAME, ConfigManager.SC_CONFIG_DIR.toString());

        if (mc == null) {
            throw new RuntimeException("This should not happen (SCMain.mc was null on init() call)");
        }

        Runtime.getRuntime().addShutdownHook(new Thread(LifecyclePoints::shutdown, NAME + " Shutdown"));

        KeyEvent.subscribe(data -> {
            if (OPEN_GUI_KEYBIND.matches(data.key) && data.action == GLFW.GLFW_PRESS && mc.canCurrentScreenInterruptOtherScreen()) {
                if (mc.currentScreen instanceof SCScreen) { // TODO later change SCScreen to the main ClickGUI screen
                    mc.setScreen(null);
                } else {
                    mc.setScreen(
                            new SCScreen(
                                    "screen-testscreen",
                                    Text.of("test screen"),
                                    new CenterElement(
                                            "center-testcenter",
                                            new TextBoxElement(
                                                    "textbox-testlabel",
                                                    TranslatableString.of("testlabel_01"),
                                                    -1
                                            )
                                    )
                            )
                    );
                    // TODO later actually make method that constructs the ClickGUI with all the modules etc
                }
            }
        });

    }

    public static void info(String string, Object... objects) {
        LOGGER.info(string, objects);
    }

    public static void warn(String string, Object... objects) {
        LOGGER.warn(string, objects);
    }

    public static void error(String string, Object... objects) {
        LOGGER.error(string, objects);
    }

}
