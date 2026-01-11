package net.justacoder.shadowclient.main;

import net.justacoder.shadowclient.main.events.KeyEvent;
import net.justacoder.shadowclient.main.keybinds.Key;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.parts.ClickGUIFrame;
import net.justacoder.shadowclient.main.ui.parts.SimpleFrameButton;
import net.justacoder.shadowclient.main.ui.screens.ClickGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class SCMain {

    public static final String VERSION = /*$ mod_version*/ "0.3";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.11";
    public static final String NAME = /*$ mod_name*/ "ShadowClient";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static final Key OPEN_GUI_KEYBIND = new Key(GLFW.GLFW_KEY_RIGHT_SHIFT);

    public static void init() {

        info("Loading {} version {} for Minecraft {}", NAME, VERSION, MINECRAFT);

        KeyEvent.subscribe(data -> {
            if (OPEN_GUI_KEYBIND.matches(data.key) && data.action == GLFW.GLFW_PRESS && mc.canCurrentScreenInterruptOtherScreen()) {
                if (mc.currentScreen instanceof ClickGUI) {
                    mc.setScreen(null);
                } else {
                    mc.setScreen(
                            new ClickGUI(Text.of("ShadowClient ClickGUI"), "clickgui-main", List.of(new ClickGUIFrame(
                                    "clickguiframe-test01",
                                    TranslatableString.of("ui.frame.test01"),
                                    5,
                                    5,
                                    true,
                                    200,
                                    26,
                                    List.of(new SimpleFrameButton(
                                            "framebutton-test_button",
                                            TranslatableString.of("ui.frame.button.test"),
                                            () -> info("test button pressed!")
                                    ))
                            )))
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
