package net.shadowclient.main.module;

import net.shadowclient.main.module.modules.combat.*;
import net.shadowclient.main.module.modules.fun.*;
import net.shadowclient.main.module.modules.movement.*;
import net.shadowclient.main.module.modules.other.*;
import net.shadowclient.main.module.modules.player.*;
import net.shadowclient.main.module.modules.render.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {

    public static final Map<String, Module> modules = new HashMap<>();

    public static void registerModules() {
        register(new AutoSprint());
        register(new Spider());
        register(new StepUp());
        register(new AutoSwim());
        register(new Fly());
        register(new AutoFish());
        register(new AutoCriticalHit());
        register(new AntiKnockback());
        register(new AutoRespawn());
        register(new CameraNoclip());
        register(new FastClimb());
        register(new AirJump());
        register(new NoFallDamage());
        register(new Parkour());
        register(new PowderSnowWalk());
        register(new Tracers());
        register(new FastBlockBreak());
        register(new Timer());
        register(new EntitiesESP());
        register(new EndermanLook());
        register(new SecretShaders());
        register(new SneakSpam());
        register(new Derpy());
        register(new NoTiltOnHurt());
        register(new Panic());
        register(new RainbowGUI());
        register(new NoFireOverlay());
        register(new AutoCrystal());
        register(new BunnyHop());
        register(new UpdatesDisabled());
        register(new NoWaterPush());
        register(new SaveData());
        register(new LoadData());
        register(new ShowSettings());
        register(new HideSettings());
    }

    public static void register(Module module) {
        modules.put(module.ModuleName, module);
    }

    public static Module getModule(String name) {
        return modules.get(name);
    }

    public static List<String> getAllModuleNamesInCategory(ModuleCategory category) {

        List<String> categoryModules = new ArrayList<>();

        getAllModules().forEach((name, module) -> {
            if (module.Category == category) {
                categoryModules.add(name);
            }
        });

        return categoryModules;
    }

    public static Map<String, Module> getAllModules() {
        return modules;
    }

}
