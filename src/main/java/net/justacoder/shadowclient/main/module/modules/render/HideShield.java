package net.justacoder.shadowclient.main.module.modules.render;

import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class HideShield extends Module {

    public HideShield() {
        super("hideshield", ModuleCategory.RENDER, new String[]{"lower shield", "lowershield", "hideshield", "low shield", "no shield", "hide shield", "lowshield", "hidden shield"});
    }

    public static void processItem(ItemStack item, AbstractClientPlayerEntity player, CallbackInfo ci) {

        if (item.getItem() instanceof ShieldItem && player.getItemUseTimeLeft() <= 0) {
            ci.cancel();
        }

    }
}
