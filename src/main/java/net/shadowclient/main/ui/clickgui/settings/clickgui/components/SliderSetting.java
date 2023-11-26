package net.shadowclient.main.ui.clickgui.settings.clickgui.components;

import net.minecraft.client.gui.DrawContext;
import net.shadowclient.main.setting.Setting;
import net.shadowclient.main.setting.settings.NumberSetting;
import net.shadowclient.main.ui.clickgui.Colors;
import net.shadowclient.main.ui.clickgui.ModuleButton;
import net.shadowclient.main.ui.clickgui.settings.clickgui.SettingComponent;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderSetting extends SettingComponent {

    private final NumberSetting numberSetting;

    private boolean sliding;

    public SliderSetting(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numberSetting = (NumberSetting) setting;
        this.sliding = false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (isHovered(mouseX, mouseY)) {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_HOVERED.color);
        } else {
            context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SETTING_COMPONENT_NORMAL.color);
        }

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int) (parent.parent.width * (numberSetting.numberValue().floatValue() - numberSetting.getMinValue().floatValue()) / (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()));
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height, Colors.SLIDER.color);

        if (sliding) {
            if (diff == 0) {
                numberSetting.setNumberValue(numberSetting.getMinValue());
            } else {
                numberSetting.setNumberValue(roundToPlace((diff / parent.parent.width) * (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()) + numberSetting.getMinValue().floatValue(), numberSetting.decimalPlaces));
            }
        }

        int textOffset = (parent.parent.height / 2 - mc.textRenderer.fontHeight / 2);

        context.drawTextWithShadow(mc.textRenderer, numberSetting.name + ": " + roundToPlace(numberSetting.numberValue().floatValue(), 3), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, Colors.TEXT_NORMAL.color);

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

        sliding = false;

        super.mouseReleased(mouseX, mouseY, button);
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }
        BigDecimal bdec = new BigDecimal(value);
        bdec = bdec.setScale(place, RoundingMode.HALF_UP);
        return bdec.doubleValue();
    }
}
