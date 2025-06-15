package net.justacoder.shadowclient.main.event;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.event.events.PostTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.util.JavaUtils;
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
            if (evt instanceof PostTickEvent && ShadowClientMain.toggleGUIKeyBinding.wasPressed()) {
                if (ShadowClientMain.mc.currentScreen == null) {
                    ShadowClientMain.mc.setScreen(ShadowClientMain.clickGui);
                } else if (ShadowClientMain.mc.currentScreen instanceof ShadowClientScreen) {
                    ShadowClientMain.mc.setScreen(null);
                }
            }

            if (ModuleManager.UpdatesDisableModule.enabled) {
                return;
            }

            List<Module> modules;
            if ((modules = listeners.get(evt.getClass())) != null) {
                modules.forEach(module -> {
                    if (module.enabled) {
                        module.onEvent(evt);
                    }
                });
            }
        } catch (Exception e) {
            ShadowClientMain.error("Exception while handling event: " + evt.getClass().getName());
            ShadowClientMain.error(JavaUtils.stackTraceFromThrowable(e));
        }
    }
}
