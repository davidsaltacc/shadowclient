package net.justacoder.shadowclient.main.ui.clickgui;

import com.google.gson.JsonObject;
import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.config.ConfigSaveable;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.minecraft.client.MinecraftClient;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.ui.clickgui.text.FrameTextField;
import java.util.Arrays;

public class MainClickGUI extends ClickGUI implements ConfigSaveable {

    public final MinecraftClient mc = MinecraftClient.getInstance();

    public MainClickGUI() {
        super("ClickGUI");

        for (ModuleCategory category : ModuleCategory.values()) {
            if (category.hiddenFromMain) {
                continue;
            }
            frames.add(Frame.create(this, category, 0, 0, 200, 26));
        }

        searchFrame = Frame.createWithoutAddingModules(this, ModuleCategory.SEARCH, 0, 0, 200, 26);
        frames.add(searchFrame);
        searchFrame.children.add(new FrameTextField(searchFrame, 26, TranslatableString.of("textfield.placeholder.find_module"), this::searchChanged));
    }

    public void repositionFramesProperly() {

        int width = mc.getWindow().getMonitor().findClosestVideoMode(mc.getWindow().getFullscreenVideoMode()).getWidth(); // why is this so hard???? anyway it works now

        int columns = (int) Math.floor((float) (width / 2.) / 210);

        int[] columnsY = new int[columns];
        Arrays.fill(columnsY, 10);

        for (int index = 0; index < frames.size(); index++) {
            int xCol = index % columns;
            Frame frame = frames.get(index);
            if (columnsY[xCol] == 10) {
                frame.y = columnsY[xCol];
                columnsY[xCol] += frame.getHeight() + 10;
                frame.x = 10 + xCol * 210;
            } else {
                int minY = (int) 1e7;
                int minYIndex = -1;
                for (int ind = 0; ind < columns; ind++) {
                    int newY = columnsY[ind] + frame.getHeight();
                    if (newY < minY) {
                        minY = newY;
                        minYIndex = ind;
                    }
                }
                frame.y = columnsY[minYIndex];
                columnsY[minYIndex] += frame.getHeight() + 10;
                frame.x = 10 + minYIndex * 210;
            }
        }
    }

    @Override
    public JsonObject writeConfig() {
        JsonObject object = new JsonObject();
        JsonObject frames = new JsonObject();
        this.frames.forEach(frame -> frames.add(frame.category.id, frame.writeConfig()));
        object.add("frames", frames);
        return object;
    }

    @Override
    public void readConfig(JsonObject in) {
        this.frames.forEach(frame -> {
            try {
                frame.readConfig(in.get("frames").getAsJsonObject().get(frame.category.id).getAsJsonObject());
            } catch (Exception e) {
                ShadowClientMain.error("Failed to read config for ClickGUI frame " + frame.category.id + ": " + e);
            }
        });
    }
}
