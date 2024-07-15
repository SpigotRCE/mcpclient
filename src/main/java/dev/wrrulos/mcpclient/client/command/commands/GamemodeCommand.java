package dev.wrrulos.mcpclient.client.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import dev.wrrulos.mcpclient.util.ColorUtil;
import dev.wrrulos.mcpclient.util.StringUtil;
import dev.wrrulos.mcpclient.constants.CommandConstants;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.world.GameMode;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class GamemodeCommand {
    // Game mode static variable to store the current game mode
    private static GameMode gameMode;

    // Register the fake game mode command
    public void register() {
        ClientCommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess) -> dispatcher.register(
                literal(CommandConstants.GAMEMODE_COMMAND)
                    .then(literal("survival")
                        .executes(context -> executeGameMode(context, "survival")))
                    .then(literal("creative")
                        .executes(context -> executeGameMode(context, "creative")))
                    .then(literal("adventure")
                        .executes(context -> executeGameMode(context, "adventure")))
                    .then(literal("spectator")
                        .executes(context -> executeGameMode(context, "spectator")))
                    .executes(GamemodeCommand::executeRoot)
            )
        );
    }

    /**
     * Execute the fake game mode command
     * @param context Command context
     * @return Integer value (1)
     */
    private static int executeRoot(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(ColorUtil.parseColoredText(CommandConstants.GAMEMODE_MISSING));
        return Command.SINGLE_SUCCESS;
    }

    /**
     * Execute the fake game mode command with a specific game mode
     * @param context Command context
     * @param mode Game mode
     * @return Integer value (1)
     */
    private static int executeGameMode(CommandContext<FabricClientCommandSource> context, String mode) {
        switch (mode.toLowerCase()) {
            case "survival":
                gameMode = GameMode.SURVIVAL;
                break;
            case "creative":
                gameMode = GameMode.CREATIVE;
                break;
            case "adventure":
                gameMode = GameMode.ADVENTURE;
                break;
            case "spectator":
                gameMode = GameMode.SPECTATOR;
                break;
        }

        context.getSource().getClient().interactionManager.setGameMode(gameMode);
        context.getSource().getPlayer().sendMessage(ColorUtil.parseColoredText(CommandConstants.GAMEMODE_CHANGED + StringUtil.capitalizeFirstLetter(mode)), false);
        return Command.SINGLE_SUCCESS;
    }
}
