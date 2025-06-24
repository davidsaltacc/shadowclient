package net.justacoder.shadowclient.main.module;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.annotations.NotKeybindable;
import net.justacoder.shadowclient.main.module.modules.world.Timer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.EventManager;
import net.justacoder.shadowclient.main.module.modules.combat.*;
import net.justacoder.shadowclient.main.module.modules.fun.*;
import net.justacoder.shadowclient.main.module.modules.menus.*;
import net.justacoder.shadowclient.main.module.modules.movement.*;
import net.justacoder.shadowclient.main.module.modules.other.*;
import net.justacoder.shadowclient.main.module.modules.player.*;
import net.justacoder.shadowclient.main.module.modules.player.cheststeal.ChestSteal;
import net.justacoder.shadowclient.main.module.modules.render.*;
import net.justacoder.shadowclient.main.module.modules.settings.*;
import net.justacoder.shadowclient.main.module.modules.world.*;
import org.jetbrains.annotations.Nullable;
import java.util.*;

public abstract class ModuleManager {

    private static final Map<String, Module> modules = new LinkedHashMap<>();

    public static AutoSprint AutoSprintModule;
    public static Spider SpiderModule;
    public static StepUp StepUpModule;
    public static AutoSwim AutoSwimModule;
    public static Fly FlyModule;
    public static AutoFish AutoFishModule;
    public static AutoCriticalHit AutoCriticalHitModule;
    public static AntiKnockback AntiKnockbackModule;
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
    public static EndermanMagnet EndermanMagnetModule;
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
    public static FastPlace FastPlaceModule;
    public static NoPumkin NoPumkinModule;
    public static PortalGUI PortalGUIModule;
    public static SeeInvisibles SeeInvisiblesModule;
    public static HighJump HighJumpModule;
    public static NoSlowdown NoSlowdownModule;
    public static Reach ReachModule;
    public static AdvancedHelp AdvancedHelpModule;
    public static SafeWalk SafeWalkModule;
    public static DinnerbonifyAll DinnerbonifyAllModule;
    public static RenderBarriers RenderBarriersModule;
    public static BetterPingDisplay BetterPingDisplayModule;
    public static DeathNotification DeathNotificationModule;
    public static Blink BlinkModule;
    public static Trajectories TrajectoriesModule;
    public static Freecam FreecamModule;
    public static Xray XrayModule;
    public static ShadowHud ShadowHudModule;
    public static NoOverlay NoOverlayModule;
    public static ChestSteal ChestStealModule;
    public static AllModules AllModulesModule;
    public static WeatherControl WeatherControlModule;
    public static FlatItems FlatItemsModule;
    public static ClickTP ClickTPModule;
    public static LightOverlay LightOverlayModule;
    public static HideShield HideShieldModule;
    public static Zoom ZoomModule;
    public static NoBob NoBobModule;
    public static AutoHit AutoHitModule;
    public static AutoMove AutoMoveModule;
    public static Breadcrumbs BreadcrumbsModule;
    public static AutoSneak AutoSneakModule;
    public static SpoofRenderDistance SpoofRenderDistanceModule;
    public static WallInteract WallInteractModule;
    public static NoFog NoFogModule;
    public static StepDown StepDownModule;

    public static void registerModules() {
        AutoSprintModule = register(new AutoSprint());
        SpiderModule = register(new Spider());
        StepUpModule = register(new StepUp());
        AutoSwimModule = register(new AutoSwim());
        FlyModule = register(new Fly());
        AutoFishModule = register(new AutoFish());
        AutoCriticalHitModule = register(new AutoCriticalHit());
        AntiKnockbackModule = register(new AntiKnockback());
        CameraNoclipModule = register(new CameraNoclip());
        FastClimbModule = register(new FastClimb());
        AirJumpModule = register(new AirJump());
        NoFallDamageModule = register(new NoFallDamage());
        ParkourModule = register(new Parkour());
        PowderSnowWalkModule = register(new PowderSnowWalk());
        TracersModule = register(new Tracers());
        FastBlockBreakModule = register(new FastBlockBreak());
        TimerModule = register(new Timer());
        EntitiesESPModule = register(new EntitiesESP());
        EndermanMagnetModule = register(new EndermanMagnet());
        SneakSpamModule = register(new SneakSpam());
        DerpyModule = register(new Derpy());
        NoTiltOnHurtModule = register(new NoTiltOnHurt());
        PanicModule = register(new Panic());
        RainbowGUIModule = register(new RainbowGUI());
        NoFireOverlayModule = register(new NoFireOverlay());
        AutoCrystalModule = register(new AutoCrystal());
        BunnyHopModule = register(new BunnyHop());
        UpdatesDisableModule = register(new UpdatesDisabled());
        NoWaterPushModule = register(new NoWaterPush());
        SaveDataModule = register(new SaveData());
        LoadDataModule = register(new LoadData());
        ShowSettingsModule = register(new ShowSettings());
        HideSettingsModule = register(new HideSettings());
        ResetDataModule = register(new ResetData());
        KillAuraModule = register(new KillAura());
        PacketLoggerModule = register(new PacketLogger());
        NightVisionModule = register(new NightVision());
        NoBlindModule = register(new NoBlind());
        NoLevitationModule = register(new NoLevitation());
        NoWobbleModule = register(new NoWobble());
        BoatFlyModule = register(new BoatFly());
        NoEntityPushModule = register(new NoEntityPush());
        AutoHotbarCycleModule = register(new AutoHotbarCycle());
        ExtendedCameraDistanceModule = register(new ExtendedCameraDistance());
        FastPlaceModule = register(new FastPlace());
        NoPumkinModule = register(new NoPumkin());
        PortalGUIModule = register(new PortalGUI());
        SeeInvisiblesModule = register(new SeeInvisibles());
        HighJumpModule = register(new HighJump());
        NoSlowdownModule = register(new NoSlowdown());
        ReachModule = register(new Reach());
        AdvancedHelpModule = register(new AdvancedHelp());
        SafeWalkModule = register(new SafeWalk());
        DinnerbonifyAllModule = register(new DinnerbonifyAll());
        RenderBarriersModule = register(new RenderBarriers());
        BetterPingDisplayModule = register(new BetterPingDisplay());
        DeathNotificationModule = register(new DeathNotification());
        BlinkModule = register(new Blink());
        TrajectoriesModule = register(new Trajectories());
        FreecamModule = register(new Freecam());
        XrayModule = register(new Xray());
        ShadowHudModule = register(new ShadowHud());
        NoOverlayModule = register(new NoOverlay());
        ChestStealModule = register(new ChestSteal());
        AllModulesModule = register(new AllModules());
        WeatherControlModule = register(new WeatherControl());
        FlatItemsModule = register(new FlatItems());
        ClickTPModule = register(new ClickTP());
        LightOverlayModule = register(new LightOverlay());
        HideShieldModule = register(new HideShield());
        ZoomModule = register(new Zoom());
        NoBobModule = register(new NoBob());
        AutoHitModule = register(new AutoHit());
        AutoMoveModule = register(new AutoMove());
        BreadcrumbsModule = register(new Breadcrumbs());
        AutoSneakModule = register(new AutoSneak());
        SpoofRenderDistanceModule = register(new SpoofRenderDistance());
        WallInteractModule = register(new WallInteract());
        NoFogModule = register(new NoFog());
        StepDownModule = register(new StepDown());
    }

    public static<M extends Module> M register(M module) {
        modules.put(module.moduleId, module);
        if (!module.getClass().isAnnotationPresent(NotKeybindable.class)) {
            module.keyBinding = new KeyBinding("module.shadowclient." + module.moduleId, InputUtil.UNKNOWN_KEY.getCode(), "category.shadowclient.clientcategory");
            ShadowClientMain.registerKeyBinding(module.keyBinding, true);
        }
        if (module.getClass().isAnnotationPresent(EventListener.class)) {
            for (Class<? extends Event> evtcl : module.getClass().getAnnotation(EventListener.class).value()) {
                EventManager.addModule(module, evtcl);
            }
        }
        return module;
    }

    public static @Nullable Module getModule(String name) {
        return modules.get(name);
    }

    public static List<String> getAllModuleNamesInCategory(ModuleCategory category) {

        List<String> categoryModules = new ArrayList<>();

        getAllModules().forEach((name, module) -> {
            if (module.category == category) {
                categoryModules.add(name);
            }
        });

        return categoryModules;
    }

    public static Map<String, Module> getAllModules() {
        return modules;
    }

}
