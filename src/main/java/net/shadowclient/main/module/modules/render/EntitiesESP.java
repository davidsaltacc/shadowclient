package net.shadowclient.main.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;

@ReceiveNoUpdates
@SearchTags({"entitiesesp", "esp", "entity esp", "entities esp", "wallhack", "wall hack"})
public class EntitiesESP extends Module {

    public final BooleanSetting DrawPlayerEntityOutlines = new BooleanSetting("Players", true);
    public final BooleanSetting DrawHostileEntityOutlines = new BooleanSetting("Hostiles", true);
    public final BooleanSetting DrawPassiveEntityOutlines = new BooleanSetting("Passives", false);
    public final BooleanSetting DrawOtherEntityOutlines = new BooleanSetting("Ambients", false);
    public final BooleanSetting DrawAmbientEntityOutlines = new BooleanSetting("Others", true);

    public EntitiesESP() {
        super("entitiesesp", "ESP", ModuleCategory.RENDER);

        addSettings(DrawPlayerEntityOutlines, DrawHostileEntityOutlines, DrawPassiveEntityOutlines, DrawOtherEntityOutlines, DrawAmbientEntityOutlines);
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
}
