package net.justacoder.shadowclient.main.ui.text;

import net.justacoder.shadowclient.main.render.UIRenderUtils;
import net.justacoder.shadowclient.main.translations.TranslatableString;
import net.justacoder.shadowclient.main.ui.ShadowClientScreen;
import net.justacoder.shadowclient.main.ui.Colors;
import net.justacoder.shadowclient.main.ui.font.Font;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.StringHelper;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import java.util.function.Consumer;

public class TextField {

    public TextField(ShadowClientScreen screen, String defaultText, TranslatableString placeholder, Vector2f position, Vector2f size, Consumer<String> changedCallback) {
        screen.addTextField(this);
        this.position = position;
        this.size = size;
        this.changedCallback = changedCallback;
        this.text = defaultText;
        this.placeholder = placeholder;
        this.cursorPos = text.length();
    }

    public boolean typing = false;
    private int cursorPos = 0;
    private String text;
    private TranslatableString placeholder;
    private Vector2f position;
    private Vector2f size;
    private Consumer<String> changedCallback;

    public void setPosition(Vector2f position) {
        this.position = position;
    }
    public Vector2f getPosition() {
        return this.position;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public void setText(String text) {
        this.text = text;
        this.cursorPos = text.length();
    }

    public String getText() {
        return text;
    }

    public void render(DrawContext context) {

        context.fill((int) position.x, (int) position.y, (int) (position.x + size.x), (int) (position.y + size.y), Colors.TEXT_FIELD_BACKGROUND.color);
        int textOffset = (int) ((size.y - Font.getHeight()) / 2);
        Font.renderString(context, text.isEmpty() ? placeholder.getTranslation() : text, position.x + textOffset, position.y + size.y / 2 - (float) Font.getHeight() / 2, text.isEmpty() ? Colors.TEXT_DISABLED.color : Colors.TEXT_NORMAL.color);
        if (typing) {
            float cursorOffset = textOffset + Font.getWidth(text.substring(0, cursorPos));
            context.fill((int) (position.x + cursorOffset), (int) (position.y + size.y / 2 - (float) Font.getHeight() / 2), (int) (position.x + cursorOffset + 1), (int) (position.y + size.y / 2 + (float) Font.getHeight() / 2), Colors.TEXT_NORMAL.color);
        }

    }

    public void mouseClicked(double scaledMouseX, double scaledMouseY, int button) {

        float disableScaleFactor = UIRenderUtils.guiScaleFactor();
        int mouseX = (int) (scaledMouseX * disableScaleFactor);
        int mouseY = (int) (scaledMouseY * disableScaleFactor);

        typing = mouseX > position.x && mouseX < position.x + size.x && mouseY > position.y && mouseY < position.y + size.y;

    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (typing) {

            if (modifiers == GLFW.GLFW_MOD_CONTROL) {
                if (keyCode == GLFW.GLFW_KEY_X) {
                    MinecraftClient.getInstance().keyboard.setClipboard(text);
                    text = "";
                    changedCallback.accept(text);
                } else if (keyCode == GLFW.GLFW_KEY_V) {
                    String beforeCursor = text.substring(0, cursorPos);
                    String afterCursor = text.substring(cursorPos);
                    text = beforeCursor + MinecraftClient.getInstance().keyboard.getClipboard() + afterCursor;
                    changedCallback.accept(text);
                }
            } else if (modifiers == 0) {
                if (keyCode == GLFW.GLFW_KEY_LEFT) {
                    cursorPos = Math.clamp(cursorPos - 1, 0, text.length());
                } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
                    cursorPos = Math.clamp(cursorPos + 1, 0, text.length());
                } else if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_ENTER) {
                    typing = false;
                } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                    String beforeCursor = text.substring(0, Math.clamp(cursorPos - 1, 0, text.length()));
                    String afterCursor = text.substring(cursorPos);
                    text = beforeCursor + afterCursor;
                    cursorPos = Math.clamp(cursorPos - 1, 0, text.length());
                    changedCallback.accept(text);
                } else if (keyCode == GLFW.GLFW_KEY_DELETE) {
                    String beforeCursor = text.substring(0, cursorPos);
                    String afterCursor = text.substring(Math.clamp(cursorPos + 1, 0, text.length()));
                    text = beforeCursor + afterCursor;
                    changedCallback.accept(text);
                }
            }

        }
    }

    public void charTyped(char chr, int modifiers) {
        if (typing) {

            if (StringHelper.isValidChar(chr)) {
                String c = Character.toString(chr);
                String beforeCursor = text.substring(0, cursorPos);
                String afterCursor = text.substring(cursorPos);
                cursorPos += 1;
                text = beforeCursor + c + afterCursor;
                changedCallback.accept(text);
            }

        }
    }

    public boolean capturesKeypress(int key) {
        return typing;
    }

    public boolean capturesKeypress() {
        return typing;
    }

}
