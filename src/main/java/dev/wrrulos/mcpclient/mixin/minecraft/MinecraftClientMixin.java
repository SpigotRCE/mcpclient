package dev.wrrulos.mcpclient.mixin.minecraft;

import dev.wrrulos.mcpclient.constants.ClientConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    /**
     * Get the window title
     * @param cir Callback info returnable
     */
    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    private void setWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(ClientConstants.WINDOW_TITLE);
    }
}