package net.justacoder.shadowclient.main;

import net.justacoder.shadowclient.main.config.ConfigManager;
import net.justacoder.shadowclient.main.events.KeyEvent;
import net.justacoder.shadowclient.main.keybinds.Key;
import net.justacoder.shadowclient.main.translation.TranslatableString;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.elements.Properties;
import net.justacoder.shadowclient.main.ui.elements.UiElement;
import net.justacoder.shadowclient.main.ui.elements.multiplechild.StackContainerElement;
import net.justacoder.shadowclient.main.ui.elements.nochild.TextBoxElement;
import net.justacoder.shadowclient.main.ui.elements.singlechild.BackgroundElement;
import net.justacoder.shadowclient.main.ui.elements.singlechild.CenterElement;
import net.justacoder.shadowclient.main.ui.elements.singlechild.CursorElement;
import net.justacoder.shadowclient.main.ui.elements.singlechild.FixedBoxElement;
import net.justacoder.shadowclient.main.ui.screens.GenericScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.cursor.StandardCursors;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

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
                if (mc.currentScreen instanceof GenericScreen) { // TODO later change SCScreen to the main ClickGUI screen
                    mc.setScreen(null);
                } else {
                    mc.setScreen(
                            new GenericScreen(
                                    "screen-testscreen",
                                    Text.of("test screen"),
                                    createExampleClickGUIFrame()
                            )
                    );
                    // TODO later actually make method that constructs the ClickGUI with all the modules etc
                }
            }
        });

    }

    private static UiElement createExampleClickGUIFrame() {

        Function<String, UiElement> frameChild = translationKey -> new BackgroundElement(
                new FixedBoxElement(
                        new CenterElement(
                                new TextBoxElement(
                                        TranslatableString.of(translationKey),
                                        -1
                                ),
                                Properties.CenterAxes.ONLY_Y_SAME_X
                        ),
                        new Properties.Size(Properties.SizeType.FIXED_SIZE, 200),
                        new Properties.Size(Properties.SizeType.FIXED_SIZE, 24)
                ),
                Colors.FRAME_CHILD_BACKGROUND_NORMAL.getColor()
        );

        return new CenterElement(
                new StackContainerElement(
                        List.of(
                                new BackgroundElement(
                                        new CursorElement(
                                                new FixedBoxElement(
                                                        new CenterElement(
                                                                new TextBoxElement(
                                                                        TranslatableString.of("testtitle"),
                                                                        -1
                                                                ),
                                                                Properties.CenterAxes.BOTH
                                                        ),
                                                        new Properties.Size(Properties.SizeType.FIXED_SIZE, 200),
                                                        new Properties.Size(Properties.SizeType.FIXED_SIZE, 24)
                                                ),
                                                StandardCursors.POINTING_HAND
                                        ),
                                        Colors.FRAME_COLOR.getColor()
                                ),
                                frameChild.apply("testlabel_01"),
                                frameChild.apply("testlabel_02"),
                                frameChild.apply("testlabel_03")
                        ),
                        Properties.Direction.VERTICAL,
                        new Properties.Size(Properties.SizeType.FIXED_SIZE, 200),
                        new Properties.Size(Properties.SizeType.FIT_CHILDREN)
                ),
                Properties.CenterAxes.BOTH
        );
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
