package net.shadowclient.main.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;

@SearchTags({"see invisibles", "anti invisible", "seeinvisibles"})
public class SeeInvisibles extends Module {

    public final BooleanSetting SeePlayerEntities = new BooleanSetting("Players", true);
    public final BooleanSetting SeeHostileEntities = new BooleanSetting("Hostiles", false);
    public final BooleanSetting SeePassiveEntities = new BooleanSetting("Passives", false);
    public final BooleanSetting SeeAmbientEntities = new BooleanSetting("Ambients", false);
    public final BooleanSetting SeeOtherEntities = new BooleanSetting("Others", false);

    public SeeInvisibles() {
        super("seeinvisibles", "See Invisibles", "Renders invisible entities as ghosts.", ModuleCategory.RENDER);

        addSettings(SeePlayerEntities, SeeHostileEntities, SeePassiveEntities, SeeAmbientEntities, SeeOtherEntities);
    }

    public boolean visible(Entity entity) {
        if (!enabled) {
            return false;
        }
        if (entity instanceof PlayerEntity && SeePlayerEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof HostileEntity && SeeHostileEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof PassiveEntity && SeePassiveEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof AmbientEntity && SeeAmbientEntities.booleanValue()) {
            return true;
        }
        return SeeOtherEntities.booleanValue();
    }
}
