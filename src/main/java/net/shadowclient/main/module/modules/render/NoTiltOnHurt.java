package net.shadowclient.main.module.modules.render;

import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@ReceiveNoUpdates
@SearchTags({"no hurtcam", "no tilt on hurt", "no hurt", "on hurt no tilt", "no tilt when damage", "no damaged tilt"})
public class NoTiltOnHurt extends Module {
    public NoTiltOnHurt() {
        super("notiltonhurt", "No Hurt Tilt", ModuleCategory.RENDER);
    }
}
