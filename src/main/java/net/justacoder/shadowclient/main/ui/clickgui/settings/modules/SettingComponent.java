package net.justacoder.shadowclient.main.ui.clickgui.settings.modules;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.justacoder.shadowclient.main.setting.Setting;
import net.justacoder.shadowclient.main.setting.settings.*;
import net.justacoder.shadowclient.main.ui.clickgui.settings.ColorSelectionScreen;
import net.justacoder.shadowclient.main.ui.clickgui.Colors;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.justacoder.shadowclient.main.util.MathUtils;
import net.justacoder.shadowclient.mixin.KeyBindingAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public abstract class SettingComponent {

    public final Setting setting;
    protected Vector2i position;

    protected SettingComponent(Setting setting, Vector2i position) {
        this.setting = setting;
        this.position = position;
    }

    public void updatePosition(Vector2i position) {
        this.position = position;
    }

    public void init() {}

    public abstract void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta);

    public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {}

    public void mouseReleased(double mouseX, double mouseY, int button, boolean inBounds) {}

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, boolean inBounds) {}

    public boolean mayScrollContainer(double mouseX, double mouseY, boolean inBounds) {
        return true;
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {}

    public boolean interceptKeypresses() {
        return false;
    }

    public boolean isHovered(double mouseX, double mouseY, boolean inBounds) {
        return mouseX > position.x && mouseX < position.x + getWidth() && mouseY > position.y  && mouseY < position.y + getHeight() && inBounds;
    }

    public static SettingComponent ofSetting(Setting setting, Vector2i position) {
        return switch (setting) {
            case BooleanSetting ignored -> new BooleanSettingComponent(setting, position);
            case EnumSetting<?> ignored -> new EnumSettingComponent(setting, position);
            case NumberSetting ignored -> new NumberSettingComponent(setting, position);
            case StringSetting ignored -> new TextSettingComponent(setting, position);
            case PaddingSetting ignored -> new PaddingSettingComponent(setting, position);
            case KeySetting ignored -> new KeybindingSettingComponent(setting, position);
            case ColorSetting ignored -> new ColorSettingComponent(setting, position);
            case ButtonSetting ignored -> new ButtonSettingComponent(setting, position);
            default -> throw new RuntimeException("Tried to create a setting component for an unsupported setting type: " + setting.getClass().getName());
        };
    }

    public abstract int getHeight();
    public abstract int getWidth();

    public static class BooleanSettingComponent extends SettingComponent {

        protected BooleanSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        int checkboxSize = Font.getHeight() + 4;
        int textPaddingTop = 2;
        int checkboxPaddingRight = 4;
        int checkboxFilledPadding = 2;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            Font.renderString(context, setting.name, position.x + checkboxSize + checkboxPaddingRight + 2, position.y + 2 + textPaddingTop, Colors.TEXT_NORMAL.color);

            context.fill(position.x, position.y + 2, position.x + checkboxSize, position.y + 2 + checkboxSize, Colors.CHECKBOX_BACKGROUND.color);

            if (((BooleanSetting) setting).booleanValue()) {
                context.fill(position.x + checkboxFilledPadding, position.y + 2 + checkboxFilledPadding, position.x + checkboxSize - checkboxFilledPadding, position.y + 2 + checkboxSize - checkboxFilledPadding, Colors.CHECKBOX_FILLED.color);
            }

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            if (mouseX > position.x + 2 && mouseY > position.y + 2 && mouseX < position.x + 2 + checkboxSize && mouseY < position.y + 2 + checkboxSize && inBounds) {
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
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            int nameWidth = Font.getWidth(setting.name.getTranslation() + ": ");
            Font.renderString(context, setting.name.getTranslation() + ": ", position.x, position.y + 4, Colors.TEXT_NORMAL.color);

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
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            if (isHovered(mouseX, mouseY, inBounds)) {
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
        public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, boolean inBounds) {
            if (isHovered(mouseX, mouseY, inBounds)) {
                enumSettingIndex += (int) Math.signum(verticalAmount);
                loopSettingIndex();
                EnumSetting enumSetting = (EnumSetting) setting;
                enumSetting.setEnumValue(enumSetting.getEnumValue().getClass().getEnumConstants()[enumSettingIndex]);
            }
        }

        @Override
        public boolean mayScrollContainer(double mouseX, double mouseY, boolean inBounds) {
            return !isHovered(mouseX, mouseY, inBounds);
        }

        @Override
        public int getWidth() {
            return Font.getWidth(setting.name + ": " + ((EnumSetting<?>) setting).getEnumValue().name());
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 4 + line + 4
        }

    }

    public static class NumberSettingComponent extends SettingComponent {

        protected NumberSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
            this.sliding = false;
            this.oldValue = ((NumberSetting) setting).numberValueEased();
        }

        private boolean sliding;
        private Number oldValue;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            NumberSetting numberSetting = (NumberSetting) setting;

            String text = getText(numberSetting);

            int sliderTotalWidth = Math.max(200, Font.getWidth(text));
            int sliderSize = (int) ((sliderTotalWidth - 2 - 2) * (numberSetting.numberValue().floatValue() - numberSetting.getMinValue().floatValue()) / (numberSetting.getMaxValue().floatValue() - numberSetting.getMinValue().floatValue()));

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
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            if (isHovered(mouseX, mouseY, inBounds)) {
                sliding = true;
            }
        }

        @Override
        public void mouseReleased(double mouseX, double mouseY, int button, boolean inBounds) {
            if (sliding) {
                NumberSetting numberSetting = (NumberSetting) setting;
                sliding = false;
                numberSetting.callFinishCallbacks(oldValue, numberSetting.numberValueEased());
                oldValue = numberSetting.numberValueEased();
            }
        }

        @Override
        public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, boolean inBounds) {
            if (isHovered(mouseX, mouseY, inBounds)) {
                NumberSetting numberSetting = (NumberSetting) setting;
                numberSetting.setNumberValue(
                        MathUtils.roundToPlace(
                                numberSetting.numberValue().doubleValue() + (numberSetting.getMaxValue().doubleValue() - numberSetting.getMinValue().doubleValue()) / 100. * verticalAmount,
                                numberSetting.decimalPlaces
                        )
                );
            }
        }

        private String getText(NumberSetting numberSetting) {
            return numberSetting.name.getTranslation() + ": " + MathUtils.roundToPlace(numberSetting.doubleValueEased(), numberSetting.decimalPlaces);
        }

        @Override
        public boolean mayScrollContainer(double mouseX, double mouseY, boolean inBounds) {
            return !isHovered(mouseX, mouseY, inBounds);
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
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            int nameWidth = Font.getWidth(setting.name.getTranslation() + ": ") + 2;
            String value = ((StringSetting) setting).stringValue();
            Font.renderString(context, setting.name.getTranslation() + ": ", position.x, position.y + 4, Colors.TEXT_NORMAL.color);

            context.fill(position.x + nameWidth, position.y + 2, position.x + nameWidth + Font.getWidth(value.isEmpty() ? setting.name.getTranslation() : value) + 4, position.y + getHeight() - 2, Colors.TEXT_FIELD_BACKGROUND.color);

            Font.renderString(context, value.isEmpty() ? setting.name.getTranslation() : value, position.x + nameWidth + 2, position.y + 4, value.isEmpty() ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            typing = isHovered(mouseX, mouseY, inBounds);
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
                if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == ((KeyBindingAccessor) ShadowClientMain.toggleGUIKeyBinding).getBoundKey().getCode() || keyCode == GLFW.GLFW_KEY_ESCAPE) {
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
            String text = value.isEmpty() ? setting.name.getTranslation() : value;
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
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {}

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 4;
        }

    }

    public static class KeybindingSettingComponent extends SettingComponent {

        protected KeybindingSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        private boolean configuring = false;
        private boolean wasConfiguring = false;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            String text = setting.name.getTranslation() + ": ";
            int textWidth = Font.getWidth(text);
            String binding = configuring ? "[Press a key]" : getBindingName(((KeySetting) setting).keyValue());
            int bindingWidth = Font.getWidth(binding);
            context.fill(position.x + textWidth,position.y + 2, position.x + textWidth + bindingWidth + 4, position.y + 2 + Font.getHeight() + 4, Colors.KEYBIND_SETTING_BACKGROUND.color);
            Font.renderString(context, text, position.x + 2, position.y + 4, Colors.TEXT_NORMAL.color);
            Font.renderString(context, binding, position.x + textWidth + 2, position.y + 4, Colors.TEXT_NORMAL.color);

        }

        @Override
        public boolean interceptKeypresses() {
            return configuring;
        }

        public boolean isConfiguring() {
            return configuring;
        }

        public boolean recentlyWasConfiguring() {
            if (wasConfiguring) {
                wasConfiguring = false;
                return true;
            }
            return false;
        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            configuring = isHovered(mouseX, mouseY, inBounds);
        }

        @Override
        public void keyPressed(int keyCode, int scanCode, int modifiers) {
            if (configuring) {
                configuring = false;
                if (keyCode == ((KeyBindingAccessor) ShadowClientMain.toggleGUIKeyBinding).getBoundKey().getCode()) {
                    return;
                }
                if (keyCode != GLFW.GLFW_KEY_ESCAPE) {
                    ((KeySetting) setting).setKeyValue(keyCode);
                    wasConfiguring = true;
                } else {
                    ((KeySetting) setting).setKeyValue(-1);
                }
            }
        }

        private String getBindingName(int key) {
            String binding = key == -1 ? null : (GLFW.glfwGetKeyName(key, -1) == null ? null : GLFW.glfwGetKeyName(key, -1).toUpperCase());
            return binding == null ? I18n.translate("name.shadowclient.none") : binding;
        }

        @Override
        public int getWidth() {
            return Font.getWidth(setting.name + ": " + getBindingName(((KeySetting) setting).keyValue()));
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 2 + line (+ 2*2 padding) + 2
        }

    }

    public static class ColorSettingComponent extends SettingComponent {

        protected ColorSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            context.fill(position.x, position.y + 2, position.x + getHeight() * 2, position.y + getHeight() - 2, Colors.COLOR_SETTING_BACKGROUND.color);
            context.fill(position.x + 2, position.y + 4, position.x + getHeight() * 2 - 2, position.y + getHeight() - 4, ((ColorSetting) setting).colorValue());

            Font.renderString(context, " " + setting.name.getTranslation(), position.x + getHeight() * 2, position.y + 4, Colors.TEXT_NORMAL.color);

        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 2 + (box = line, box border = 2*2) + 2
        }

        @Override
        public int getWidth() {
            return getHeight() * 2 + 2 + Font.getWidth(" " + setting.name.getTranslation());
        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {

            if (inBounds && mouseX > position.x && mouseX < position.x + getHeight() * 2 && mouseY > position.y + 2 && mouseY < position.y + getHeight() - 2) {
                ShadowClientMain.mc.setScreen(new ColorSelectionScreen((ColorSetting) setting, ShadowClientMain.mc.currentScreen));
            }

        }
    }

    public static class ButtonSettingComponent extends SettingComponent {

        protected ButtonSettingComponent(Setting setting, Vector2i position) {
            super(setting, position);
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean inBounds, float delta) {

            context.fill(position.x, position.y + 2, position.x + Font.getWidth(setting.name.getTranslation()) + 4, position.y + 4 + Font.getHeight() , Colors.BUTTON_SETTING_BACKGROUND.color);
            Font.renderString(context, setting.name.getTranslation(), position.x + 2, position.y + 4, Colors.TEXT_NORMAL.color);

        }

        @Override
        public void mouseClicked(double mouseX, double mouseY, int button, boolean inBounds) {
            if (isHovered(mouseX, mouseY, inBounds)) {
                ((ButtonSetting) setting).press();
            }
        }

        @Override
        public int getWidth() {
            return Font.getWidth(setting.name) + 4;
        }

        @Override
        public int getHeight() {
            return Font.getHeight() + 8; // 4 + line + 4
        }

    }
}
