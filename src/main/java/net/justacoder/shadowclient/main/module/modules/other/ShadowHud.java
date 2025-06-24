package net.justacoder.shadowclient.main.module.modules.other;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.EnumSetting;
import net.justacoder.shadowclient.main.ui.hud.HudElement;
import net.justacoder.shadowclient.main.ui.hud.HudRenderer;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.justacoder.shadowclient.mixin.WorldRendererAccessor;

@EventListener({PreTickEvent.class})
public class ShadowHud extends Module {

    public EnumSetting<HudRenderer.Corner> CORNER = new EnumSetting<>(TranslatableString.of("setting.module.shadowclient.shadowhud.corner"), HudRenderer.Corner.Top_Left);

    public BooleanSetting COORDINATES = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.coords"), true);
    public BooleanSetting PING = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.ping"), true);
    public BooleanSetting SATURATION = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.saturation"), true);
    public BooleanSetting ROTATION = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.rotation"), false);
    public BooleanSetting FRAMES = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.fps"), true);
    public BooleanSetting ENTITIES = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.shadowhud.entities"), false);

    public HudElement COORDINATES_ELEMENT = new HudElement(true, "");
    public HudElement PING_ELEMENT = new HudElement(true, "");
    public HudElement SATURATION_ELEMENT = new HudElement(true, "");
    public HudElement ROTATION_ELEMENT = new HudElement(false, "");
    public HudElement FRAMES_ELEMENT = new HudElement(true, "");
    public HudElement ENTITIES_ELEMENT = new HudElement(false, "");

    public ShadowHud() {
        super("shadowhud", ModuleCategory.OTHER, new String[]{"shadowhud", "shadow hud", "minihud", "hud", "coordinates", "coords"});

        addSettings(CORNER, COORDINATES, PING, SATURATION, ROTATION, FRAMES, ENTITIES);

        CORNER.addChangeCallback((newV, ignored) -> HudRenderer.setCorner((HudRenderer.Corner) newV));

        COORDINATES.addChangeCallback((newV, ignored) -> COORDINATES_ELEMENT.shouldBeRendered((boolean) newV));
        PING.addChangeCallback((newV, ignored) -> PING_ELEMENT.shouldBeRendered(PING.booleanValue()));
        SATURATION.addChangeCallback((newV, ignored) -> SATURATION_ELEMENT.shouldBeRendered((boolean) newV));
        ROTATION.addChangeCallback((newV, ignored) -> ROTATION_ELEMENT.shouldBeRendered((boolean) newV));
        FRAMES.addChangeCallback((newV, ignored) -> FRAMES_ELEMENT.shouldBeRendered((boolean) newV));
        ENTITIES.addChangeCallback((newV, ignored) -> ENTITIES_ELEMENT.shouldBeRendered((boolean) newV));

        HudRenderer.addElement(COORDINATES_ELEMENT);
        HudRenderer.addElement(ROTATION_ELEMENT);
        HudRenderer.addElement(PING_ELEMENT);
        HudRenderer.addElement(SATURATION_ELEMENT);
        HudRenderer.addElement(FRAMES_ELEMENT);
        HudRenderer.addElement(ENTITIES_ELEMENT);
    }

    @Override
    public boolean onEnable() {
        HudRenderer.setCorner(CORNER.getEnumValue());

        COORDINATES_ELEMENT.shouldBeRendered(COORDINATES.booleanValue());
        PING_ELEMENT.shouldBeRendered(PING.booleanValue());
        SATURATION_ELEMENT.shouldBeRendered(SATURATION.booleanValue());
        ROTATION_ELEMENT.shouldBeRendered(ROTATION.booleanValue());
        FRAMES_ELEMENT.shouldBeRendered(FRAMES.booleanValue());
        ENTITIES_ELEMENT.shouldBeRendered(ENTITIES.booleanValue());

        return super.onEnable();
    }

    @Override
    public void onEvent(Event event) {
        if (COORDINATES.booleanValue()) {
            Vec3d pos = mc.player.getPos();
            COORDINATES_ELEMENT.setTextContent("Position: " + (int) pos.x + ", " + (int) pos.y + ", " + (int) pos.z);
        }
        if (PING.booleanValue()) {
            PING_ELEMENT.setTextContent("Ping: " +  mc.player.networkHandler.getPlayerListEntry(mc.player.getUuid()).getLatency());
        }
        if (SATURATION.booleanValue()) {
            SATURATION_ELEMENT.setTextContent("Saturation: " + MathUtils.roundToPlace(mc.player.getHungerManager().getSaturationLevel(), 2) + " / 20.0");
        }
        if (ROTATION.booleanValue()) {
            ROTATION_ELEMENT.setTextContent("Rotation: " + MathUtils.roundToPlace(MathHelper.wrapDegrees(mc.player.getYaw()), 2) + ", " + MathUtils.roundToPlace(MathHelper.wrapDegrees(mc.player.getPitch()), 2));
        }
        if (FRAMES.booleanValue()) {
            FRAMES_ELEMENT.setTextContent(mc.getCurrentFps() + " fps");
        }
        if (ENTITIES.booleanValue()) {
            ENTITIES_ELEMENT.setTextContent(((WorldRendererAccessor) mc.worldRenderer).getRenderedEntitiesCount() + " entities rendered, " + mc.world.getRegularEntityCount() + " loaded");
        }
    }

}
