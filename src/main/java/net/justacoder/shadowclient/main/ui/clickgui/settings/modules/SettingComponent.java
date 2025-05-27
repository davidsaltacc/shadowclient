package net.justacoder.shadowclient.main.ui.clickgui.settings.modules;

import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.*;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.clickgui.FrameChild;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public abstract class SettingComponent extends FrameChild {

    public final Setting setting;
    protected Vector2i position;

    protected SettingComponent(Setting setting, Vector2i position) {
        this.setting = setting;
        this.position = position;
    }

    public void updatePosition(Vector2i position) {
        this.position = position;
    }

    public abstract int getWidth();

    public boolean mayScrollContainer(double mouseX, double mouseY) {
        return true;
    }

    public boolean interceptKeypresses() {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > position.x && mouseX < position.x + getWidth() && mouseY > position.y  && mouseY < position.y + getHeight();
    }

    public static SettingComponent ofSetting(Setting setting, Vector2i position) {
        return switch (setting) {
            case BooleanSetting ignored -> new BooleanSettingComponent(setting, position);
            case EnumSetting<?> ignored -> new EnumSettingComponent(setting, position);
            case NumberSetting ignored -> new NumberSettingComponent(setting, position);
            case StringSetting ignored -> new TextSettingComponent(setting, position);
            case PaddingSetting ignored -> new PaddingSettingComponent(setting, position);
            default -> throw new RuntimeException("Tried to create a setting component for an unsupported setting type: " + setting.getClass().getName());
        };
    }

    public static class BooleanSettingComponent extends SettingComponent {

        protected BooleanSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        int checkboxSize = Font.getHeight() + 4;
        int textPaddingTop = 2;
        int checkboxPaddingRight = 4;
        int checkboxFilledPadding = 2;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {

            Font.renderString(context, setting.name, position.x + checkboxSize + checkboxPaddingRight + 2, position.y + 2 + textPaddingTop, Colors.TEXT_NORMAL.color);

            context.fill(position.x, position.y + 2, position.x + checkboxSize, position.y + 2 + checkboxSize, Colors.CHECKBOX_BACKGROUND.color);

            if (((BooleanSetting) setting).booleanValue()) {
                context.fill(position.x + checkboxFilledPadding, position.y + 2 + checkboxFilledPadding, position.x + checkboxSize - checkboxFilledPadding, position.y + 2 + checkboxSize - checkboxFilledPadding, Colors.CHECKBOX_FILLED.color);
            }

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button) {
            if (mouseX > position.x + 2 && mouseY > position.y + 2 && mouseX < position.x + 2 + checkboxSize && mouseY < position.y + 2 + checkboxSize) {
                ((BooleanSetting) setting).setBooleanValue(!((BooleanSetting) setting).booleanValue());
            }
        }

        @Override
        public int getWidth() {
            return checkboxSize + checkboxPaddingRight + Font.getWidth(setting.name);
        }

        @Override
        public int getHeight() {
            return Math.max(Font.getHeight(), checkboxSize) + 4; // 2 + box/line + 2
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static class EnumSettingComponent extends SettingComponent {

        protected EnumSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        private int enumSettingIndex = 0;

        @Override
        public void init() {
            enumSettingIndex = 0;
            EnumSetting enumSetting = (EnumSetting) setting;
            for (Enum value : enumSetting.getEnumValue().getClass().getEnumConstants()) {
                if (value != enumSetting.getEnumValue()) {
                    enumSettingIndex += 1;
                } else {
                    break;
                }
            }
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {

            int nameWidth = Font.getWidth(setting.name + ": ");
            Font.renderString(context, setting.name + ": ", position.x, position.y + 4, Colors.TEXT_NORMAL.color);

            String valueName = ((EnumSetting<?>) setting).getEnumValue().name();

            context.fill(position.x + nameWidth, position.y + 2, position.x + nameWidth + Font.getWidth(valueName) + 4, position.y + 4 + Font.getHeight() , Colors.ENUM_SETTING_BACKGROUND.color);
            Font.renderString(context, valueName, position.x + nameWidth + 2, position.y + 4, Colors.TEXT_NORMAL.color);

        }

        public void loopSettingIndex() {
            EnumSetting enumSetting = (EnumSetting) setting;
            if (enumSettingIndex >= enumSetting.getEnumValue().getClass().getEnumConstants().length) {
                enumSettingIndex = 0;
            }
            if (enumSettingIndex <= -1) {
                enumSettingIndex = enumSetting.getEnumValue().getClass().getEnumConstants().length - 1;
            }
        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button) {
            if (isHovered(mouseX, mouseY)) {
                EnumSetting enumSetting = (EnumSetting) setting;

                if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                    enumSettingIndex += 1;
                }
                if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
                    enumSettingIndex -= 1;
                }

                loopSettingIndex();

                enumSetting.setEnumValue(enumSetting.getEnumValue().getClass().getEnumConstants()[enumSettingIndex]);
            }
        }

        @Override
        public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
            if (isHovered(mouseX, mouseY)) {
                enumSettingIndex += (int) Math.signum(verticalAmount);
                loopSettingIndex();
                EnumSetting enumSetting = (EnumSetting) setting;
                enumSetting.setEnumValue(enumSetting.getEnumValue().getClass().getEnumConstants()[enumSettingIndex]);
            }
        }

        @Override
        public boolean mayScrollContainer(double mouseX, double mouseY) {
            return !isHovered(mouseX, mouseY);
        }

        @Override
        public int getWidth() {
            return Font.getWidth(setting.name + ": " + ((EnumSetting<?>) setting).getEnumValue().name());
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 4; // 4 + line + 4
        }

    }

    public static class NumberSettingComponent extends SettingComponent {

        protected NumberSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
            this.sliding = false;
            this.oldValue = ((NumberSetting) setting).numberValue();
        }

        private boolean sliding;
        private Number oldValue;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {

            NumberSetting numberSetting = (NumberSetting) setting;

            String text = getText(numberSetting);

            int sliderTotalWidth = Math.max(200, Font.getWidth(text));
            int sliderSize = (int) ((sliderTotalWidth - 2 - 2) * (numberSetting.numberValueUneased().floatValue() - numberSetting.getMinValue().floatValue()) / (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()));

            if (sliding) {
                numberSetting.setNumberValue(
                        MathUtils.roundToPlace(
                                (float) (mouseX - (position.x + 2)) / ((position.x + sliderTotalWidth - 2) - (position.x + 2))
                                    * (numberSetting.getMaxValue().doubleValue() - numberSetting.getMinValue().doubleValue()) + numberSetting.getMinValue().doubleValue(),
                                numberSetting.decimalPlaces
                        ),
                        false
                );
            }

            context.fill(position.x, position.y + 2, position.x + sliderTotalWidth, position.y + 6 + Font.getHeight(), Colors.SLIDER_BACKGROUND.color);
            context.fill(position.x + 2, position.y + 4, position.x + 2 + sliderSize, position.y + 4 + Font.getHeight(), Colors.SLIDER.color);

            Font.renderString(context, text, position.x + sliderTotalWidth / 2f - Font.getWidth(text) / 2f, position.y + 4, Colors.TEXT_NORMAL.color);

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button) {
            if (isHovered(mouseX, mouseY)) {
                sliding = true;
            }
        }

        @Override
        public void mouseReleased(double mouseX, double mouseY, int button) {
            if (sliding) {
                NumberSetting numberSetting = (NumberSetting) setting;
                sliding = false;
                numberSetting.callFinishCallbacks(oldValue, numberSetting.numberValue());
                oldValue = numberSetting.numberValue();
            }
        }

        @Override
        public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
            if (isHovered(mouseX, mouseY)) {
                NumberSetting numberSetting = (NumberSetting) setting;
                numberSetting.setNumberValue(
                        MathUtils.roundToPlace(
                                numberSetting.doubleValue() + (numberSetting.getMaxValue().doubleValue() - numberSetting.getMinValue().doubleValue()) / 100. * verticalAmount,
                                numberSetting.decimalPlaces
                        )
                );
            }
        }

        private String getText(NumberSetting numberSetting) {
            return numberSetting.name + ": " + MathUtils.roundToPlace(numberSetting.doubleValue(), numberSetting.decimalPlaces);
        }

        @Override
        public boolean mayScrollContainer(double mouseX, double mouseY) {
            return !isHovered(mouseX, mouseY);
        }

        @Override
        public int getWidth() {
            return Math.max(200, Font.getWidth(getText((NumberSetting) setting)));
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 2 + (text height + slider padding (2 * 2)) + 2
        }

    }

    public static class TextSettingComponent extends SettingComponent {

        private boolean typing;

        protected TextSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
            this.typing = false;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {

            int nameWidth = Font.getWidth(setting.name + ": ") + 2;
            String value = ((StringSetting) setting).stringValue();
            Font.renderString(context, setting.name + ": ", position.x, position.y + 4, Colors.TEXT_NORMAL.color);

            context.fill(position.x + nameWidth, position.y + 2, position.x + nameWidth + Font.getWidth(value.isEmpty() ? setting.name : value) + 4, position.y + getHeight() - 2, Colors.TEXT_SETTING_BACKEND.color);

            Font.renderString(context, value.isEmpty() ? setting.name : value, position.x + nameWidth + 2, position.y + 4, value.isEmpty() ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button) {
            typing = isHovered(mouseX, mouseY);
        }

        @Override
        public void keyPressed(int keyCode, int scanCode, int modifiers) {
            StringSetting stringSetting = (StringSetting) setting;
            if (typing) {
                if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                    if (!stringSetting.stringValue().isEmpty()) {
                        stringSetting.setStringValue(stringSetting.stringValue().substring(0, stringSetting.stringValue().length() - 1));
                    }
                    return;
                }
                if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                    typing = false;
                    return;
                }
                stringSetting.setStringValue(stringSetting.stringValue() + ("" + (char) keyCode).toLowerCase());
            }
        }

        @Override
        public boolean interceptKeypresses() {
            return typing;
        }

        @Override
        public int getWidth() {
            String value = ((StringSetting) setting).stringValue();
            String text = value.isEmpty() ? setting.name : value;
            return Font.getWidth(text) + 2 + Font.getWidth(text) + 4;
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 2 + (line + 2*2 padding) + 2
        }

    }

    public static class PaddingSettingComponent extends SettingComponent {

        protected PaddingSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 4;
        }

    }
}
