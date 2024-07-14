package dev.wrrulos.mcpclient;

import dev.wrrulos.mcpclient.client.command.CommandManager;
import dev.wrrulos.mcpclient.presence.DiscordPresence;
import dev.wrrulos.mcpclient.updater.UpdateChecker;
import net.fabricmc.api.ModInitializer;
import dev.wrrulos.mcpclient.client.session.SessionController;

/**
 * MCPClient
 */
public class MCPClient implements ModInitializer {
    private static final SessionController sessionController = new SessionController();

    @Override
    public void onInitialize() {
        System.out.println("MCPClient has been initialized!");

        // Check updates
        UpdateChecker.checkForUpdates();

        // Save UUIDs
        String originalUUID = sessionController.getOriginalUUIDFromSession();
        sessionController.setUUID(originalUUID);
        sessionController.setOriginalUUID(originalUUID);

        // Register commands.
        CommandManager.registerCommands();

        // Start the discord presence in a thread.
        DiscordPresence discordPresence = new DiscordPresence();
        Thread thread = new Thread(discordPresence);
        thread.start();
    }

    /**
     * Get the session controller
     * @return Session controller
     */
    public static SessionController getSessionController() {
        return sessionController;
    }
}
