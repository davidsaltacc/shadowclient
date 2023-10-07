package net.shadowclient.mixininterface;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface IGameRenderer {
    void loadShader(@Nullable Identifier id);
}
