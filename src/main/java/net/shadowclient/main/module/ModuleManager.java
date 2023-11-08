package net.shadowclient.main.module;

import net.shadowclient.main.module.modules.combat.*;
import net.shadowclient.main.module.modules.fun.*;
import net.shadowclient.main.module.modules.menus.HideSettings;
import net.shadowclient.main.module.modules.menus.ShowSettings;
import net.shadowclient.main.module.modules.movement.*;
import net.shadowclient.main.module.modules.other.*;
import net.shadowclient.main.module.modules.player.*;
import net.shadowclient.main.module.modules.render.*;
import net.shadowclient.main.module.modules.settings.LoadData;
import net.shadowclient.main.module.modules.settings.ResetData;
import net.shadowclient.main.module.modules.settings.SaveData;
import net.shadowclient.main.module.modules.world.NoEntityPush;
import net.shadowclient.main.module.modules.world.NoWaterPush;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {

    public static final Map<String, Module> modules = new HashMap<>();

    public static AutoSprint AutoSprintModule;
    public static Spider SpiderModule;
    public static StepUp StepUpModule;
    public static AutoSwim AutoSwimModule;
    public static Fly FlyModule;
    public static AutoFish AutoFishModule;
    public static AutoCriticalHit AutoCriticalHitModule;
    public static AntiKnockback AntiKnockbackModule;
    public static AutoRespawn AutoRespawnModule;
    public static CameraNoclip CameraNoclipModule;
    public static FastClimb FastClimbModule;
    public static AirJump AirJumpModule;
    public static NoFallDamage NoFallDamageModule;
    public static Parkour ParkourModule;
    public static PowderSnowWalk PowderSnowWalkModule;
    public static Tracers TracersModule;
    public static FastBlockBreak FastBlockBreakModule;
    public static Timer TimerModule;
    public static EntitiesESP EntitiesESPModule;
    public static EndermanLook EndermanLookModule;
    public static SecretShaders SecretShadersModule;
    public static SneakSpam SneakSpamModule;
    public static Derpy DerpyModule;
    public static NoTiltOnHurt NoTiltOnHurtModule;
    public static Panic PanicModule;
    public static RainbowGUI RainbowGUIModule;
    public static NoFireOverlay NoFireOverlayModule;
    public static AutoCrystal AutoCrystalModule;
    public static BunnyHop BunnyHopModule;
    public static UpdatesDisabled UpdatesDisableModule;
    public static NoWaterPush NoWaterPushModule;
    public static SaveData SaveDataModule;
    public static LoadData LoadDataModule;
    public static ShowSettings ShowSettingsModule;
    public static HideSettings HideSettingsModule;
    public static ResetData ResetDataModule;
    public static KillAura KillAuraModule;
    public static PacketLogger PacketLoggerModule;
    public static NightVision NightVisionModule;
    public static NoBlind NoBlindModule;
    public static NoLevitation NoLevitationModule;
    public static NoWobble NoWobbleModule;
    public static BoatFly BoatFlyModule;
    public static NoEntityPush NoEntityPushModule;
    public static AutoHotbarCycle AutoHotbarCycleModule;
    public static ExtendedCameraDistance ExtendedCameraDistanceModule;

    public static void registerModules() {
        AutoSprintModule = (AutoSprint) register(new AutoSprint());
        SpiderModule = (Spider) register(new Spider());
        StepUpModule = (StepUp) register(new StepUp());
        AutoSwimModule = (AutoSwim) register(new AutoSwim());
        FlyModule = (Fly) register(new Fly());
        AutoFishModule = (AutoFish) register(new AutoFish());
        AutoCriticalHitModule = (AutoCriticalHit) register(new AutoCriticalHit());
        AntiKnockbackModule = (AntiKnockback) register(new AntiKnockback());
        AutoRespawnModule = (AutoRespawn) register(new AutoRespawn());
        CameraNoclipModule = (CameraNoclip) register(new CameraNoclip());
        FastClimbModule = (FastClimb) register(new FastClimb());
        AirJumpModule = (AirJump) register(new AirJump());
        NoFallDamageModule = (NoFallDamage) register(new NoFallDamage());
        ParkourModule = (Parkour) register(new Parkour());
        PowderSnowWalkModule = (PowderSnowWalk) register(new PowderSnowWalk());
        TracersModule = (Tracers) register(new Tracers());
        FastBlockBreakModule = (FastBlockBreak) register(new FastBlockBreak());
        TimerModule = (Timer) register(new Timer());
        EntitiesESPModule = (EntitiesESP) register(new EntitiesESP());
        EndermanLookModule = (EndermanLook) register(new EndermanLook());
        SecretShadersModule = (SecretShaders) register(new SecretShaders());
        SneakSpamModule = (SneakSpam) register(new SneakSpam());
        DerpyModule = (Derpy) register(new Derpy());
        NoTiltOnHurtModule = (NoTiltOnHurt) register(new NoTiltOnHurt());
        PanicModule = (Panic) register(new Panic());
        RainbowGUIModule = (RainbowGUI) register(new RainbowGUI());
        NoFireOverlayModule = (NoFireOverlay) register(new NoFireOverlay());
        AutoCrystalModule = (AutoCrystal) register(new AutoCrystal());
        BunnyHopModule = (BunnyHop) register(new BunnyHop());
        UpdatesDisableModule = (UpdatesDisabled) register(new UpdatesDisabled());
        NoWaterPushModule = (NoWaterPush) register(new NoWaterPush());
        SaveDataModule = (SaveData) register(new SaveData());
        LoadDataModule = (LoadData) register(new LoadData());
        ShowSettingsModule = (ShowSettings) register(new ShowSettings());
        HideSettingsModule = (HideSettings) register(new HideSettings());
        ResetDataModule = (ResetData) register(new ResetData());
        KillAuraModule = (KillAura) register(new KillAura());
        PacketLoggerModule = (PacketLogger) register(new PacketLogger());
        NightVisionModule = (NightVision) register(new NightVision());
        NoBlindModule = (NoBlind) register(new NoBlind());
        NoLevitationModule = (NoLevitation) register(new NoLevitation());
        NoWobbleModule = (NoWobble) register(new NoWobble());
        BoatFlyModule = (BoatFly) register(new BoatFly());
        NoEntityPushModule = (NoEntityPush) register(new NoEntityPush());
        AutoHotbarCycleModule = (AutoHotbarCycle) register(new AutoHotbarCycle());
        ExtendedCameraDistanceModule = (ExtendedCameraDistance) register(new ExtendedCameraDistance());
    }

    public static Module register(Module module) {
        modules.put(module.ModuleName, module);
        return module;
    }

    public static @Nullable Module getModule(String name) {
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
