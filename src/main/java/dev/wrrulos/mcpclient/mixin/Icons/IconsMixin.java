package dev.wrrulos.mcpclient.mixin.Icons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Icons;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Icons.class)
public abstract class IconsMixin {
    /**
     * Get the icons
     * @param resourcePack Resource pack
     * @param cir Callback info returnable
     * @throws IOException Input/output exception
     */
    @Inject(method = "getIcons", at = @At("HEAD"), cancellable = true)
    private void onGetIcons(ResourcePack resourcePack, CallbackInfoReturnable<List<InputSupplier<InputStream>>> cir) throws IOException {
        List<InputSupplier<InputStream>> customIcons = List.of(
            getCustomIcon("icon16x16.png"),
            getCustomIcon("icon32x32.png"),
            getCustomIcon("icon48x48.png"),
            getCustomIcon("icon128x128.png"),
            getCustomIcon("icon256x256.png")
        );
        cir.setReturnValue(customIcons);
        cir.cancel();
    }

    /**
     * Get the custom icon
     * @param fileName File name
     * @return Input supplier
     * @throws IOException Input/output exception
     */
    @Unique
    private InputSupplier<InputStream> getCustomIcon(String fileName) throws IOException {
        String gameDir = MinecraftClient.getInstance().runDirectory.getAbsolutePath();
        String filePath = gameDir + "/mcpfiles/icons/" + fileName;

        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        return () -> new FileInputStream(file);
    }
}
