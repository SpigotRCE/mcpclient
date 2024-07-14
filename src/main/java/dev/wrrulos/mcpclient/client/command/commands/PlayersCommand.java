package dev.wrrulos.mcpclient.client.command.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.wrrulos.mcpclient.data.PlayerData;
import dev.wrrulos.mcpclient.util.ColorUtil;
import dev.wrrulos.mcpclient.constants.CommandConstants;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import java.util.HashMap;
import java.util.Map;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class PlayersCommand {
    // Register the players command
    public void register() {
        ClientCommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess) -> dispatcher.register(
                literal(CommandConstants.PLAYERS_COMMAND).executes(PlayersCommand::executeRoot)
            )
        );
    }

    /**
     * Execute the players command
     * @param context Command context
     * @return Integer value (1)
     */
    private static int executeRoot(CommandContext<FabricClientCommandSource> context) {
        Map<String, PlayerData> playerDataMap = new HashMap<>();

        context.getSource().getPlayer().networkHandler.getPlayerList().forEach(playerInfo -> {
            String playerName = playerInfo.getProfile().getName();
            String playerUUID = playerInfo.getProfile().getId().toString();
            String playerGamemode = playerInfo.getGameMode().getName();
            int playerPing = playerInfo.getLatency();
            PlayerData playerData = new PlayerData(playerName, playerUUID, playerGamemode, playerPing);
            playerDataMap.put(playerName, playerData);
        });

        if (playerDataMap.isEmpty()) {
            context.getSource().getPlayer().sendMessage(ColorUtil.parseColoredText(CommandConstants.NO_PLAYERS), true);
        }

        context.getSource().getPlayer().sendMessage(ColorUtil.parseColoredText(CommandConstants.PLAYERS_TITLE));

        playerDataMap.forEach((name, playerData) -> {
            context.getSource().getPlayer().sendMessage(playerData.getMessage(), false);
        });
        return 1;
    }
}
