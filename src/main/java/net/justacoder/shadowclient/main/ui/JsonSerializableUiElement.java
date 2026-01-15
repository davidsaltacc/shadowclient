package net.justacoder.shadowclient.main.ui;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JsonSerializableUiElement { // for things such as positions and states of draggable ui elements - NOT to store actual config

    @NotNull JsonObject serialize();

    // per-instance - if we have multiple serializable widgets of the same type, they still need different ids
    // possible useful naming convention - "type-id", like "button-something" or "clickgui-main" just to make it more readable
    @NotNull String getId();

    void deserialize(@Nullable JsonObject in);

}
