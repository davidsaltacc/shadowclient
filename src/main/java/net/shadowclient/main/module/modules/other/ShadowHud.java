package net.shadowclient.main.module.modules.other;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.EnumSetting;
import net.shadowclient.main.ui.hud.HudElement;
import net.shadowclient.main.ui.hud.HudRenderer;
import net.shadowclient.main.util.MathUtils;
import net.shadowclient.mixin.WorldRendererAccessor;

@SearchTags({"shadowhud", "shadow hud", "minihud", "hud", "coordinates", "coords"})
@EventListener({PreTickEvent.class})
public class ShadowHud extends Module { // todo add to this

    public EnumSetting<HudRenderer.Corner> CORNER = new EnumSetting<>("Corner", HudRenderer.Corner.Top_Left);

    public BooleanSetting COORDINATES = new BooleanSetting("Coords", true);
    public BooleanSetting PING = new BooleanSetting("Ping", true);
    public BooleanSetting SATURATION = new BooleanSetting("Saturation", true);
    public BooleanSetting ROTATION = new BooleanSetting("Rotation", false);
    public BooleanSetting FRAMES = new BooleanSetting("FPS", true);
    public BooleanSetting ENTITIES = new BooleanSetting("Entities", false);

    public HudElement COORDINATES_ELEMENT = new HudElement(true, "");
    public HudElement PING_ELEMENT = new HudElement(true, "");
    public HudElement SATURATION_ELEMENT = new HudElement(true, "");
    public HudElement ROTATION_ELEMENT = new HudElement(false, "");
    public HudElement FRAMES_ELEMENT = new HudElement(true, "");
    public HudElement ENTITIES_ELEMENT = new HudElement(false, "");

    public ShadowHud() {
        super("shadowhud", "ShadowHud", "Renders a minimalistic hud showing useful info.", ModuleCategory.OTHER);

        addSettings(CORNER, COORDINATES, PING, SATURATION, ROTATION, FRAMES, ENTITIES);

        CORNER.addChangeCallback(() -> HudRenderer.setCorner(CORNER.getEnumValue()));

        COORDINATES.addChangeCallback(() -> COORDINATES_ELEMENT.shouldBeRendered(COORDINATES.booleanValue()));
        PING.addChangeCallback(() -> PING_ELEMENT.shouldBeRendered(PING.booleanValue()));
        SATURATION.addChangeCallback(() -> SATURATION_ELEMENT.shouldBeRendered(SATURATION.booleanValue()));
        ROTATION.addChangeCallback(() -> ROTATION_ELEMENT.shouldBeRendered(ROTATION.booleanValue()));
        FRAMES.addChangeCallback(() -> FRAMES_ELEMENT.shouldBeRendered(FRAMES.booleanValue()));
        ENTITIES.addChangeCallback(() -> ENTITIES_ELEMENT.shouldBeRendered(ENTITIES.booleanValue()));

        HudRenderer.addElement(COORDINATES_ELEMENT);
        HudRenderer.addElement(ROTATION_ELEMENT);
        HudRenderer.addElement(PING_ELEMENT);
        HudRenderer.addElement(SATURATION_ELEMENT);
        HudRenderer.addElement(FRAMES_ELEMENT);
        HudRenderer.addElement(ENTITIES_ELEMENT);
    }

    @Override
    public void onEnable() {
        HudRenderer.setCorner(CORNER.getEnumValue());

        COORDINATES_ELEMENT.shouldBeRendered(COORDINATES.booleanValue());
        PING_ELEMENT.shouldBeRendered(PING.booleanValue());
        SATURATION_ELEMENT.shouldBeRendered(SATURATION.booleanValue());
        ROTATION_ELEMENT.shouldBeRendered(ROTATION.booleanValue());
        FRAMES_ELEMENT.shouldBeRendered(FRAMES.booleanValue());
        ENTITIES_ELEMENT.shouldBeRendered(ENTITIES.booleanValue());

        super.onEnable();
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
            SATURATION_ELEMENT.setTextContent("Saturation: " + mc.player.getHungerManager().getSaturationLevel() + " / 20.0");
        }
        if (ROTATION.booleanValue()) {
            ROTATION_ELEMENT.setTextContent("Rotation: " + MathUtils.roundToPlace(MathHelper.wrapDegrees(mc.player.getYaw()), 2) + ", " + MathUtils.roundToPlace(MathHelper.wrapDegrees(mc.player.getPitch()), 2));
        }
        if (FRAMES.booleanValue()) {
            FRAMES_ELEMENT.setTextContent(mc.getCurrentFps() + " fps");
        }
        if (ENTITIES.booleanValue()) {
            ENTITIES_ELEMENT.setTextContent(((WorldRendererAccessor) mc.worldRenderer).getRegularEntityCount() + " entities rendered, " + mc.world.getRegularEntityCount() + " loaded");
        }
    }

}
