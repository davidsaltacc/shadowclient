package net.justacoder.shadowclient.main.ui;

import net.minecraft.client.gui.Click;

public interface MouseInteractableUiElement {

    void mouseClicked(Click click, boolean doubled); // does always get fired, does not check if element is hovered
    void mouseMoved(double mouseX, double mouseY);

}
