package dev.wrrulos.mcpclient.mixin.session;

import dev.wrrulos.mcpclient.constants.ClientConstants;
import dev.wrrulos.mcpclient.presence.DiscordPresence;
import dev.wrrulos.mcpclient.util.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Objects;


@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    /**
     * On game join
     * @param packet Game join packet
     * @param info Callback info
     */
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
        if (MinecraftClient.getInstance().player != null && !Objects.equals(ClientConstants.NEW_VERSION, "") && !DiscordPresence.playingAServer) {
            MinecraftClient.getInstance().player.sendMessage(ColorUtil.parseColoredText("\n\n&aNew version of MCPClient is available!\nhttps://github.com/pedroagustinvega/mcpclient\n\n", "https://github.com/pedroagustinvega/mcpclient"));
        }

        DiscordPresence.playingAServer = true;
        ClientConnection connection = ((ClientPlayNetworkHandler) (Object) this).getConnection();
        DiscordPresence.serverAddress = connection.getAddress().toString().split("/")[0];
    }
}
