package net.shadowclient.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.module.modules.player.cheststeal.button.CustomButtonWidget;
import net.shadowclient.main.util.JavaUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin extends HandledScreen<ShulkerBoxScreenHandler> {
    public ShulkerBoxScreenMixin(ShulkerBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        addDrawableChild(CustomButtonWidget.newButton(x + backgroundWidth - 55, y + 4, 50, 12, "Steal", b -> steal()));
    }

    @Unique
    public void steal() {
        run(() -> clickSlots(0, 27));
    }

    @Unique
    public void run(Runnable r) {
        Thread thread = new Thread(() -> {
            try { r.run(); } catch(Exception e) {
                SCMain.error(JavaUtils.stackTraceFromThrowable(e));
            }
        });
        thread.setName("ChestSteal Thread");
        thread.start();
    }

    @Unique
    private void clickSlots(int from, int to) {
        for(int i = from; i < to; i++) {
            Slot slot = handler.slots.get(i);
            if (slot.getStack().isEmpty()) {
                continue;
            }

            try { Thread.sleep(ModuleManager.ChestStealModule.getDelay()); }
            catch (Exception e) { SCMain.error(JavaUtils.stackTraceFromThrowable(e)); }

            if (client.currentScreen == null) {
                break;
            }

            onMouseClick(slot, slot.id, 0, SlotActionType.QUICK_MOVE);
        }
    }
}
