package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

public class SeeInvisibles extends Module {

    public final BooleanSetting seePlayerEntities = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.seeinvisibles.players"), true);
    public final BooleanSetting seeHostileEntities = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.seeinvisibles.hostiles"), false);
    public final BooleanSetting seePassiveEntities = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.seeinvisibles.passives"), false);
    public final BooleanSetting seeAmbientEntities = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.seeinvisibles.ambients"), false);
    public final BooleanSetting seeOtherEntities = new BooleanSetting(TranslatableString.of("setting.module.shadowclient.seeinvisibles.others"), false);

    public SeeInvisibles() {
        super("seeinvisibles", ModuleCategory.RENDER, new String[]{"see invisibles", "anti invisible", "seeinvisibles"});

        addSettings(seePlayerEntities, seeHostileEntities, seePassiveEntities, seeAmbientEntities, seeOtherEntities);
    }

    public boolean visible(Entity entity) {
        if (!enabled) {
            return false;
        }
        if (entity instanceof PlayerEntity && seePlayerEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof HostileEntity && seeHostileEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof PassiveEntity && seePassiveEntities.booleanValue()) {
            return true;
        }
        if (entity instanceof AmbientEntity && seeAmbientEntities.booleanValue()) {
            return true;
        }
        return seeOtherEntities.booleanValue();
    }
}
