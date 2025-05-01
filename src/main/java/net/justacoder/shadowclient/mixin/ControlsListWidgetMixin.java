package net.justacoder.shadowclient.mixin;

import net.justacoder.shadowclient.main.ShadowClientMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlsListWidget.class)
public abstract class ControlsListWidgetMixin extends ElementListWidget<ControlsListWidget.Entry> {

    public ControlsListWidgetMixin(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/ControlsListWidget;addEntry(Lnet/minecraft/client/gui/widget/EntryListWidget$Entry;)I"))
    private int redirectAddEntry(ControlsListWidget instance, EntryListWidget.Entry<ControlsListWidget.Entry> entry) {
        return addEntryCustom((ControlsListWidget.Entry) entry);
    }

    @Unique
    @SuppressWarnings("RedundantCast")
    private int addEntryCustom(ControlsListWidget.Entry entry) {
        if (entry instanceof ControlsListWidget.KeyBindingEntry) {
            if (!ShadowClientMain.moduleKeyBindings.contains(((KeyBindingEntryAccessor) (ControlsListWidget.KeyBindingEntry) entry).getBinding())) {
                children().add(entry);
            }
            return this.children().size() - 1;
        }
        this.children().add(entry);
        return this.children().size() - 1;
    }

}
