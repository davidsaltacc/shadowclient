package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.EventListener;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.event.Event;
import net.justacoder.shadowclient.main.event.events.PreTickEvent;
import net.justacoder.shadowclient.main.event.events.Render3DEvent;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.justacoder.shadowclient.main.util.RenderUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayDeque;
import java.util.Iterator;

@EventListener({Render3DEvent.class, PreTickEvent.class})
@SearchTags({"breadcrumbs", "trails", "player trails"})
public class Breadcrumbs extends Module {

    public final BooleanSetting DEPTH_TEST = new BooleanSetting("Depth Test", true);
    public final NumberSetting MIN_SEGMENT_LEN = new NumberSetting("Min. Segment Len.", 0.01f, 5.f, 0.5f, 2, MathUtils.Easing.EASE_IN_CUBIC);
    public final NumberSetting MAX_POSITIONS = new NumberSetting("Max Breadcrumbs", 2, 8000, 2000, 0);

    private final ArrayDeque<Vec3d> positions = new ArrayDeque<>(MAX_POSITIONS.intValue());

    public Breadcrumbs() {
        super("breadcrumbs", ModuleCategory.RENDER);

        addSettings(DEPTH_TEST, MIN_SEGMENT_LEN, MAX_POSITIONS);

        MAX_POSITIONS.addChangeCallback((newValue, oldValue) -> {
            if ((float) newValue < (float) oldValue) {
                while (positions.size() > (float) newValue) {
                    positions.pollFirst();
                }
            }
        });
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof PreTickEvent) {

            float minSegLenSq = MathHelper.square(MIN_SEGMENT_LEN.floatValue());
            if (positions.isEmpty() || positions.peekLast().squaredDistanceTo(mc.player.getPos()) > minSegLenSq) {
                if (positions.size() == MAX_POSITIONS.intValue()) {
                    positions.pollFirst();
                }
                positions.addLast(mc.player.getPos());
            }

        } else if (event instanceof Render3DEvent) {

            Iterator<Vec3d> iter = positions.iterator();

            if (iter.hasNext()) {
                Vec3d pos1 = iter.next();
                while (iter.hasNext()) {
                    Vec3d pos2 = iter.next();
                    RenderUtils.drawLine(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z, new float[]{1, 1, 1, 1}, 1, DEPTH_TEST.booleanValue());
                    pos1 = pos2;
                }
            }

        }
    }

    @Override
    public void onDisable() {
        positions.clear();
    }
}
