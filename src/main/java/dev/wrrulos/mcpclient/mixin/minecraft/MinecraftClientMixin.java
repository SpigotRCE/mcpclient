package dev.wrrulos.mcpclient.mixin.minecraft;

import dev.wrrulos.mcpclient.constants.ClientConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.report.AbuseReportContext;
import net.minecraft.resource.InputSupplier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.*;
import java.util.ArrayList;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow protected abstract boolean canExecute(Runnable task);

    @Shadow public abstract AbuseReportContext getAbuseReportContext();

    @Shadow protected abstract void cleanUpAfterCrash();

    /**
     * Get the window title
     * @param cir Callback info returnable
     */
    @Inject(method = "getWindowTitle", at = @At("RETURN"), cancellable = true)
    private void setWindowTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(ClientConstants.WINDOW_TITLE);
    }

    @Inject(method = "<init>", at = @At ("TAIL"))
    private void onInit(CallbackInfo info) {
        transferFiles();
    }

    @Unique
    private void transferFiles() {
        ArrayList<Identifier> identifiers = new ArrayList<>();

        String gameDir = MinecraftClient.getInstance().runDirectory.getAbsolutePath();
        String filePath = gameDir + "\\mcpfiles\\";
        File dir = new File(filePath);

        // Add identifiers for the icons
        identifiers.add(Identifier.of("mcpclient", "icons/icon16x16.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon32x32.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon48x48.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon128x128.png"));
        identifiers.add(Identifier.of("mcpclient", "icons/icon256x256.png"));
        identifiers.add(Identifier.of("mcpclient", "discord_game_sdk.dll"));

        try {
            // Create the directory if it doesn't exist
            dir.mkdirs();

            for (Identifier identifier : identifiers) {
                // Create the full path for the file
                File file = new File(filePath + identifier.getPath());

                // Create the parent directories if they do not exist
                file.getParentFile().mkdirs();

                // Create the file if it doesn't exist
                if (!file.exists()) {
                    file.createNewFile();
                }

                // Write the InputStream to the file
                try (InputStream inputStream = MinecraftClient.getInstance().getResourceManager().getResource(identifier).get().getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(file)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error writing file: " + file.getPath());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating directory: " + filePath);
            e.printStackTrace();
        }
    }

}