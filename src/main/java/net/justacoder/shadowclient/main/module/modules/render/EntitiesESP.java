package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.annotations.DoNotSaveState;
import net.justacoder.shadowclient.main.setting.settings.ColorSetting;
import net.justacoder.shadowclient.main.setting.settings.PaddingSetting;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.util.ColorUtils;
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

    public final ColorSetting playerOutlineColor = new ColorSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.players_color"), -65536);
    public final ColorSetting hostileOutlineColor = new ColorSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.hostiles_color"), -33024);
    public final ColorSetting passiveOutlineColor = new ColorSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.passives_color"), -16711936);
    public final ColorSetting ambientOutlineColor = new ColorSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.ambients_color"), -16776999);
    public final ColorSetting otherOutlineColor = new ColorSetting(new TranslatableString("setting.module.shadowclient.entitiesesp.others_color"), -16711681);

    public EntitiesESP() {
        super("entitiesesp", ModuleCategory.RENDER, new String[]{"entitiesesp", "esp", "entity esp", "entities esp", "wallhack", "wall hack"});

        addSettings(
                drawPlayerEntityOutlines, drawHostileEntityOutlines, drawPassiveEntityOutlines, drawAmbientEntityOutlines, drawOtherEntityOutlines,
                new PaddingSetting(),
                playerOutlineColor, hostileOutlineColor, passiveOutlineColor, ambientOutlineColor, otherOutlineColor
        );
    }

    public int[] getColor(Entity entity) {
        return switch (entity) {
            case PlayerEntity ignored -> ColorUtils.int2RGB(playerOutlineColor.colorValue());
            case Monster ignored -> ColorUtils.int2RGB(hostileOutlineColor.colorValue());
            case PassiveEntity ignored -> ColorUtils.int2RGB(passiveOutlineColor.colorValue());
            case AmbientEntity ignored -> ColorUtils.int2RGB(ambientOutlineColor.colorValue());
            default -> ColorUtils.int2RGB(otherOutlineColor.colorValue());
        };
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
