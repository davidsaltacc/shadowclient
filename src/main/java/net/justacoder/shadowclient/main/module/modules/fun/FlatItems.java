package net.justacoder.shadowclient.main.module.modules.fun;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.justacoder.shadowclient.main.annotations.SearchTags;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;

@SearchTags({"flat items", "item physics", "flatitems"})
public class FlatItems extends Module {

    public BooleanSetting FACE_PLAYER = new BooleanSetting("Items Face you", false);

    public FlatItems() {
        super("flatitems", "Flat Items", "Items lay down flat on the floor.", ModuleCategory.FUN);

        addSetting(FACE_PLAYER);
    }

    public float getItemAngle(ItemEntity itemEntity, PlayerEntity playerEntity) {
        Vec3d iPos = itemEntity.getPos();
        Vec3d pPos = playerEntity.getPos();
        double diffX = iPos.x - pPos.x;
        double diffZ = iPos.z - pPos.z;
        return (float) Math.atan2(diffZ, diffX) - (float) Math.PI / 2;
    }

    public boolean face() {
        return FACE_PLAYER.booleanValue();
    }
}
