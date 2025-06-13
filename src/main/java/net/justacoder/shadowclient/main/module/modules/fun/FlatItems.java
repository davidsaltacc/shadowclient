package net.justacoder.shadowclient.main.module.modules.fun;

import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

public class FlatItems extends Module {

    public BooleanSetting FACE_PLAYER = new BooleanSetting(new TranslatableString("setting.module.shadowclient.flatitems.face"), false);

    public FlatItems() {
        super("flatitems", ModuleCategory.FUN, new String[]{"flat items", "item physics", "flatitems"});

        addSetting(FACE_PLAYER);
    }

    public float getItemAngle(ItemEntityRenderState itemEntity, PlayerEntity playerEntity) {
        Vec3d pPos = playerEntity.getPos();
        double diffX = itemEntity.x - pPos.x;
        double diffZ = itemEntity.z - pPos.z;
        return (float) Math.atan2(diffZ, diffX) - (float) Math.PI / 2;
    }

    public boolean face() {
        return FACE_PLAYER.booleanValue();
    }
}
