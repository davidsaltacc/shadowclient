package net.shadowclient.main.module.modules.render;

import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shadowclient.main.annotations.DontSaveState;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.DamageEvent;
import net.shadowclient.main.event.events.KeyPressEvent;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.ui.clickgui.ClickGUI;
import net.shadowclient.main.util.ChatUtils;
import net.shadowclient.main.util.FakePlayerEntity;
import org.joml.Vector2d;
import org.joml.Vector3d;

@DontSaveState
@SearchTags({"freecam", "camera fly", "free cam"})
@EventListener({PreTickEvent.class, KeyPressEvent.class, DamageEvent.class})
public class Freecam extends Module {

    public NumberSetting SPEED = new NumberSetting("Speed", 0.1, 2, 0.5, 1);

    public Freecam() {
        super("freecam", "Freecam", "Fly the camera.", ModuleCategory.RENDER);

        addSetting(SPEED);
    }

    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;

    public Vector3d pos = new Vector3d();
    public Vector2d rot = new Vector2d();

    public double fovEffectScale;
    public boolean bobView;

    public FakePlayerEntity fakePlayer;

    @Override
    public void onEnable() {

        Camera cam = mc.gameRenderer.getCamera();
        Vec3d cpos = cam.getPos();
        pos.x = cpos.x;
        pos.y = cpos.y;
        pos.z = cpos.z;

        rot.x = mc.player.getYaw();
        rot.y = mc.player.getPitch();

        mc.options.forwardKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.rightKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
        mc.options.sneakKey.setPressed(false);

        fovEffectScale = mc.options.getFovEffectScale().getValue();
        bobView = mc.options.getBobView().getValue();
        mc.options.getFovEffectScale().setValue(0d);
        mc.options.getBobView().setValue(false);

        fakePlayer = new FakePlayerEntity();
        fakePlayer.spawn();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.options.getFovEffectScale().setValue(fovEffectScale);
        mc.options.getBobView().setValue(bobView);

        if (fakePlayer != null) {
            fakePlayer.resetPlayerPosition();
            fakePlayer.despawn();
        }

        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof KeyPressEvent) {
            if (mc.currentScreen == null || mc.currentScreen instanceof ClickGUI) {

                int keyCode = ((KeyPressEvent) event).keyCode;

                boolean cancel = false;

                if (mc.options.forwardKey.matchesKey(keyCode, 0)) {
                    forward = ((KeyPressEvent) event).action != 0;
                    mc.options.forwardKey.setPressed(false);
                    cancel = true;
                }
                if (mc.options.backKey.matchesKey(keyCode, 0)) {
                    backward = ((KeyPressEvent) event).action != 0;
                    mc.options.backKey.setPressed(false);
                    cancel = true;
                }
                if (mc.options.rightKey.matchesKey(keyCode, 0)) {
                    right = ((KeyPressEvent) event).action != 0;
                    mc.options.rightKey.setPressed(false);
                    cancel = true;
                }
                if (mc.options.leftKey.matchesKey(keyCode, 0)) {
                    left = ((KeyPressEvent) event).action != 0;
                    mc.options.leftKey.setPressed(false);
                    cancel = true;
                }
                if (mc.options.jumpKey.matchesKey(keyCode, 0)) {
                    up = ((KeyPressEvent) event).action != 0;
                    mc.options.jumpKey.setPressed(false);
                    cancel = true;
                }
                if (mc.options.sneakKey.matchesKey(keyCode, 0)) {
                    down = ((KeyPressEvent) event).action != 0;
                    mc.options.sneakKey.setPressed(false);
                    cancel = true;
                }

                if (cancel) {
                    event.cancel();
                }
            }
            return;
        }

        if (event instanceof DamageEvent) {
            setDisabled(true, false);
            ChatUtils.sendMessageClient("Toggled freecam because you took damage.");
            return;
        }

        if (mc.cameraEntity.isInsideWall()) {
            mc.getCameraEntity().noClip = true;
        }
        if (!mc.options.getPerspective().isFirstPerson()) {
            mc.options.setPerspective(Perspective.FIRST_PERSON);
        }

        Vec3d forward = Vec3d.fromPolar(0f, (float) rot.x);
        Vec3d right = Vec3d.fromPolar(0f, (float) rot.x + 90f);
        double velX = 0;
        double velY = 0;
        double velZ = 0;

        double s = 0.5;
        if (mc.options.sprintKey.isPressed()) {
            s = 1;
        }

        s *= SPEED.doubleValue();

        boolean a = false;
        if (this.forward) {
            velX += forward.x * s;
            velZ += forward.z * s;
            a = true;
        }
        if (this.backward) {
            velX -= forward.x * s;
            velZ -= forward.z * s;
            a = true;
        }

        boolean b = false;
        if (this.right) {
            velX += right.x * s;
            velZ += right.z * s;
            b = true;
        }
        if (this.left) {
            velX -= right.x * s;
            velZ -= right.z * s;
            b = true;
        }

        if (a && b) {
            double d = 1 / Math.sqrt(2);
            velX *= d;
            velZ *= d;
        }

        if (this.up) {
            velY += s;
        }
        if (this.down) {
            velY -= s;
        }

        pos.x += velX;
        pos.y += velY;
        pos.z += velZ;
    }

    public void lookDirection(double dx, double dy) {
        rot.x += dx;
        rot.y = MathHelper.clamp(rot.y + dy, -90, 90);
    }

    public double getX() {
        return pos.x;
    }
    public double getY() {
        return pos.y;
    }
    public double getZ() {
        return pos.z;
    }

    public double getYaw() {
        return rot.x;
    }
    public double getPitch() {
        return rot.y;
    }
}
