package net.justacoder.shadowclient.mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @SuppressWarnings("SameReturnValue")
    @Accessor("CATEGORY_ORDER_MAP")
    static Map<String, Integer> getCategoryMap() {
        return null;
    }

}
