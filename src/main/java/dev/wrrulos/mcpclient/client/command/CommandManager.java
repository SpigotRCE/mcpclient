package dev.wrrulos.mcpclient.client.command;

import dev.wrrulos.mcpclient.client.command.commands.GamemodeCommand;
import dev.wrrulos.mcpclient.client.command.commands.HelpCommand;
import dev.wrrulos.mcpclient.client.command.commands.PlayersCommand;

public class CommandManager {
    public static void registerCommands() {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.register();

        PlayersCommand playersCommand = new PlayersCommand();
        playersCommand.register();

        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        gamemodeCommand.register();
    }
}
