package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"cameranoclip", "camera noclip", "camera clip", "camera see through"})
public class CameraNoclip extends Module {
    public CameraNoclip() {
        super("cameranoclip", "Camera NoClip", "In third person, allows you to move your camera into walls.", ModuleCategory.RENDER);
    }
}
