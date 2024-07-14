package dev.wrrulos.mcpclient.mixin.gui;

import dev.wrrulos.mcpclient.client.gui.SpoofSectionScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.wrrulos.mcpclient.presence.DiscordPresence;

@Mixin(MultiplayerScreen.class)
public abstract class ServerSelectionScreenMixin extends Screen {
    private ButtonWidget spoofButton;

    // Server selection screen mixin constructor
    protected ServerSelectionScreenMixin(Text title) {
        super(title);
    }

    /**
     * Initialize the widgets
     * @param info Callback info
     */
    @Inject(method = "init", at = @At(value = "TAIL", target = "Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;updateButtonActivationStates()V"))
    private void init(CallbackInfo info) {
        // Update discord presence
        DiscordPresence.playingAServer = false;

        this.spoofButton = ButtonWidget.builder(
            Text.literal("Spoof"),
            button -> MinecraftClient.getInstance().setScreen(new SpoofSectionScreen(this))
        ).width(50).position(5, 5).build();
        this.addDrawableChild(this.spoofButton);
    }
}
