package net.justacoder.shadowclient.mixin;

import net.minecraft.client.font.FontStorage;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FontStorage.class)
public interface FontStorageAccessor {

    @Accessor("id")
    Identifier getId();

}
