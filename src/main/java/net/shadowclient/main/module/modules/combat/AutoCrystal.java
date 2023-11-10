package net.shadowclient.main.module.modules.combat;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;
import net.shadowclient.main.annotations.EventListener;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.event.Event;
import net.shadowclient.main.event.events.PreTickEvent;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.util.RotationUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@EventListener({PreTickEvent.class})
@SearchTags({"auto crystal", "auto detonate", "crystal aura", "autocrystal"})
public class AutoCrystal extends Module {

    public final BooleanSetting FACE_CRYSTALS = new BooleanSetting("Face Crystals", false);

    public AutoCrystal() {
        super("autocrystal", "Auto Crystal", "Automatically detonate (not place) crystals in your area.", ModuleCategory.COMBAT);

        addSetting(FACE_CRYSTALS);
    }

    @Override
    public void OnEvent(Event event) {

        ArrayList<Entity> crystals = getNearbyCrystals();

        if (!crystals.isEmpty()) {
            detonate(crystals);
        }
    }

    private void detonate(ArrayList<Entity> crystals) {
        for (Entity e : crystals) {
            if (FACE_CRYSTALS.booleanValue()) {
                RotationUtils.rotateToVec3d(e.getBoundingBox().getCenter());
            }
            mc.interactionManager.attackEntity(mc.player, e);
        }

        if (!crystals.isEmpty()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }

    public ArrayList<Entity> getNearbyCrystals() {
        ClientPlayerEntity player = mc.player;
        double rangeSq = 36;

        Comparator<Entity> furthestFromPlayer = Comparator.<Entity>comparingDouble(e -> mc.player.squaredDistanceTo(e)).reversed();

        return StreamSupport.stream(mc.world.getEntities().spliterator(), true).filter(e -> e instanceof EndCrystalEntity).filter(e -> !e.isRemoved()).filter(e -> player.squaredDistanceTo(e) <= rangeSq).sorted(furthestFromPlayer).collect(Collectors.toCollection(ArrayList::new));
    }
}
