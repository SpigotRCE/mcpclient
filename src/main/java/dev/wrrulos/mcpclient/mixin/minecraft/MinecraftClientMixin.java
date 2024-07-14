package dev.wrrulos.mcpclient.mixin.minecraft;

import dev.wrrulos.mcpclient.constants.ClientConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final private static Logger LOGGER;

    /**
     * Get the window title
     * @param cir Callback info returnable
     */
    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    private void setWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(ClientConstants.WINDOW_TITLE);
    }

    /**
     * Inject the transfer files method after the constructor
     * :TODO: Currently the files are copied late and the client needs to be restarted after the first run.
     * :TODO: This is a temporary solution, we should find a better way to transfer the files
     * @param info Callback info
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        transferFiles();
    }

    /**
     * Transfer the files to the game directory
     */
    @Unique
    private void transferFiles() {
        ArrayList<Identifier> identifiers = new ArrayList<>();

        // Get the game directory
        String gameDir = MinecraftClient.getInstance().runDirectory.getAbsolutePath();
        String filePath = gameDir + "\\mcpfiles\\";
        File dir = new File(filePath);

        // Add identifiers for the icons
        identifiers.add(Identifier.of("mcpclient", "icons/icon_16x16.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon_32x32.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon_48x48.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon_128x128.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon_256x256.png"));
        identifiers.add(Identifier.of("mcpclient", "dll/discord_game_sdk.dll"));

        try {
            // Create the directory if it doesn't exist
            if (!dir.exists()) {
                var success = dir.mkdirs();

                if (!success) {
                    LOGGER.error("Error creating directory: {}", filePath);
                    return;
                }
            }

            for (Identifier identifier : identifiers) {
                // Create the full path for the file
                File file = new File(filePath + identifier.getPath());

                // Create the parent directories if they do not exist
                if (file.getParentFile() != null) {
                    var success = file.getParentFile().mkdirs();

                    if (!success) {
                        LOGGER.error("Error creating directory for file: {}", file.getParentFile().getPath());
                    }
                }

                // Create the file if it doesn't exist
                if (!file.exists()) {
                    var success = file.createNewFile();

                    if (!success) {
                        LOGGER.error("Error creating file: {}", file.getPath());
                    }
                }

                // Write the InputStream to the file
                Optional<Resource> resourceOptional = MinecraftClient.getInstance().getResourceManager().getResource(identifier);
                if (resourceOptional.isPresent()) {
                    try (InputStream inputStream = resourceOptional.get().getInputStream();
                         FileOutputStream outputStream = new FileOutputStream(file)) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        LOGGER.error("Error writing file: {}", file.getPath(), e);
                    }
                } else {
                    LOGGER.error("Resource not found: {}", identifier);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error transferring files", e);
        }
    }

}