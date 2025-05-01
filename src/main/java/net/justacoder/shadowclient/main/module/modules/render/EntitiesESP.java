package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
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

    public final BooleanSetting drawPlayerEntityOutlines = new BooleanSetting("Players", true);
    public final BooleanSetting drawHostileEntityOutlines = new BooleanSetting("Hostiles", true);
    public final BooleanSetting drawPassiveEntityOutlines = new BooleanSetting("Passives", false);
    public final BooleanSetting drawOtherEntityOutlines = new BooleanSetting("Others", false);
    public final BooleanSetting drawAmbientEntityOutlines = new BooleanSetting("Ambient", true);

    public EntitiesESP() {
        super("entitiesesp", ModuleCategory.RENDER, new String[]{"entitiesesp", "esp", "entity esp", "entities esp", "wallhack", "wall hack"});

        addSettings(drawPlayerEntityOutlines, drawHostileEntityOutlines, drawPassiveEntityOutlines, drawOtherEntityOutlines, drawAmbientEntityOutlines);
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
    public void onEnable() {

        EntityCullingFix.disableCull();

        super.onEnable();
    }

    @Override
    public void onDisable() {

        EntityCullingFix.enableCull();

        super.onDisable();
    }
}
