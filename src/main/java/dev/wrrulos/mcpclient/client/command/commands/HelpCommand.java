package dev.wrrulos.mcpclient.client.command.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.wrrulos.mcpclient.util.ColorUtil;
import dev.wrrulos.mcpclient.constants.CommandConstants;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class HelpCommand {
    // Register the help command
    public void register() {
        ClientCommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess) -> dispatcher.register(
                literal(CommandConstants.HELP_COMMAND)
                    .then(literal(CommandConstants.PLAYERS_COMMAND)
                        .executes(HelpCommand::executePlayers))
                    .then(literal(CommandConstants.GAMEMODE_COMMAND)
                        .executes(HelpCommand::executeGamemode))
                    .executes(HelpCommand::executeRoot)
            )
        );
    }

    /**
     * Execute the help command
     * @param context Command context
     * @return Integer value (1)
     */
    private static int executeRoot(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(ColorUtil.parseColoredText(CommandConstants.GENERAL_MESSAGE));
        return 1;
    }

    /**
     * Execute the help command with the players subcommand
     * @param context Command context
     * @return Integer value (1)
     */
    private static int executePlayers(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(ColorUtil.parseColoredText(CommandConstants.PLAYERS_MESSAGE));
        return 1;
    }

    /**
     * Execute the help command with the gamemode subcommand
     * @param context Command context
     * @return Integer value (1)
     */
    private static int executeGamemode(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(ColorUtil.parseColoredText(CommandConstants.GAMEMODE_MESSAGE));
        return 1;
    }
}
