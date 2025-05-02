package net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.gui.DrawContext;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.ModuleButton;
import net.justacoder.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import net.justacoder.shadowclient.main.util.MathUtils;

public class SliderSetting extends SettingComponent {

    private final NumberSetting numberSetting;

    private boolean sliding;
    private Number oldValue;

    public SliderSetting(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numberSetting = (NumberSetting) setting;
        this.sliding = false;
        this.oldValue = numberSetting.numberValue();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_HOVERED.color);
        } else {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_NORMAL.color);
        }

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int) (parent.parent.width * (numberSetting.numberValueUneased().floatValue() - numberSetting.getMinValue().floatValue()) / (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()));
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SLIDER.color);

        if (sliding) {
            if (diff != 0) {
                numberSetting.setNumberValue(MathUtils.roundToPlace((diff / parent.parent.width) * (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()) + numberSetting.getMinValue().floatValue(), numberSetting.decimalPlaces), false);
            }
        }

        int textOffset = (int) ((float) parent.parent.height / 2 - (float) Font.getHeight() / 2);

        Font.renderString(context, numberSetting.name + ": " + MathUtils.roundToPlace(numberSetting.numberValue().floatValue(), 3), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, Colors.TEXT_NORMAL.color);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {

        if (isHovered(mouseX, mouseY)) {
            sliding = true;
        }

        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {

        if (sliding) {
            sliding = false;
            numberSetting.callFinishCallbacks(oldValue, numberSetting.numberValue());
            oldValue = numberSetting.numberValue();
        }

        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public int getHeight() {
        return parent.parent.height;
    }

}
