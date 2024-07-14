package dev.wrrulos.mcpclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import dev.wrrulos.mcpclient.client.render.HudRenderer;

public class Client implements ClientModInitializer {
    /**
     * Initialize the client
     */
    @Override
    public void onInitializeClient() {
        System.out.println("MCPClientClient has been initialized!");
        HudRenderCallback.EVENT.register(new HudRenderer());
    }
}
