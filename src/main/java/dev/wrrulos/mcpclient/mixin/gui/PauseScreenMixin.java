package dev.wrrulos.mcpclient.mixin.gui;

import dev.wrrulos.mcpclient.client.gui.SpoofSectionScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class PauseScreenMixin extends Screen {
    /**
     * Pause screen mixin constructor
     * @param title Screen title
     */
    protected PauseScreenMixin(Text title) {
        super(title);
    }

    /**
     * Initialize the widgets
     * @param info Callback info
     */
    @Inject(at = @At("TAIL"), method = "initWidgets")
    private void init(CallbackInfo info) {
        ButtonWidget disconnectButton = null;

        for (ButtonWidget button : this.children().stream().filter(element -> element instanceof ButtonWidget).map(element -> (ButtonWidget) element).toArray(ButtonWidget[]::new)) {
            if (button.getMessage().getString().equals("Disconnect")) {
                disconnectButton = button;
                break;
            }
        }

        // Add the spoof settings button to the pause screen
        // if the disconnect button is not null
        if (disconnectButton != null) {
            int buttonWidth = disconnectButton.getWidth();
            int buttonX = disconnectButton.getX();
            int buttonY = disconnectButton.getY() + 24;

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Spoof Settings"),
                button -> this.client.setScreen(new SpoofSectionScreen(this))
            ).width(buttonWidth).position(buttonX, buttonY).build());
        }
    }
}
