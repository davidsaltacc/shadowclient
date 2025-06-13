package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.util.EntityCullingFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

@DoNotSaveState
public class EntitiesESP extends Module {

    public final BooleanSetting drawPlayerEntityOutlines = new BooleanSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.players"), true);
    public final BooleanSetting drawHostileEntityOutlines = new BooleanSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.hostiles"), true);
    public final BooleanSetting drawPassiveEntityOutlines = new BooleanSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.passives"), false);
    public final BooleanSetting drawAmbientEntityOutlines = new BooleanSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.ambients"), true);
    public final BooleanSetting drawOtherEntityOutlines = new BooleanSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.others"), false);

    public EntitiesESP() {
        super("entitiesesp", ModuleCategory.RENDER, new String[]{"entitiesesp", "esp", "entity esp", "entities esp", "wallhack", "wall hack"});

        addSettings(drawPlayerEntityOutlines, drawHostileEntityOutlines, drawPassiveEntityOutlines, drawAmbientEntityOutlines, drawOtherEntityOutlines);
    }

    public int[] getColor(Entity entity) {
        int[] color;
        if (entity instanceof PlayerEntity) {
            color = new int[]{255, 0, 0};
        } else if (entity instanceof Monster) {
            color = new int[]{255, 127, 0};
        } else if (entity instanceof PassiveEntity) {
            color = new int[]{0, 255, 0};
        } else if (entity instanceof AmbientEntity) {
            color = new int[]{0, 0, 255};
        } else {
            color = new int[]{0, 255, 255};
        }
        return color;
    }

    @Override
    public boolean onEnable() {

        EntityCullingFix.disableCull();

        return super.onEnable();
    }

    @Override
    public boolean onDisable() {

        EntityCullingFix.enableCull();

        return super.onDisable();
    }
}
