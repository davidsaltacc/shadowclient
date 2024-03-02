package net.justacoder.shadowclient.main.event;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.event.events.PostTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.clickgui.ClickGUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {

    public static Map<Class<? extends Event>, List<Module>> listeners = new ConcurrentHashMap<>();

    public static void addModule(Module module, Class<? extends Event> event) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>());
        listeners.get(event).add(module);
    }

    public static void fireEvent(Event evt) {
        try {
            if (evt instanceof PostTickEvent) {
                if (SCMain.ToggleGUIKeyBinding.wasPressed()) {
                    if (SCMain.mc.currentScreen == null) {
                        SCMain.mc.setScreen(SCMain.clickGui);
                    } else if (SCMain.mc.currentScreen instanceof ClickGUI) {
                        SCMain.mc.setScreen(null);
                    }
                }
            }
            if (ModuleManager.UpdatesDisableModule.enabled) {
                return;
            }

            if (evt instanceof PostTickEvent) {
                ModuleManager.modules.forEach((name, module) -> {
                    if (module.keybinding.wasPressed()) {
                        module.toggle();
                    }
                });
            }

            List<Module> modules;
            if ((modules = listeners.get(evt.getClass())) != null) {
                modules.forEach((module) -> {
                    if (module.enabled) {
                        module.onEvent(evt);
                    }
                });
            }
        } catch (Exception e) {
            SCMain.error("Exception while handling event: " + evt.getClass().getName() + " - " + e);
            e.printStackTrace();
        }
    }
}
