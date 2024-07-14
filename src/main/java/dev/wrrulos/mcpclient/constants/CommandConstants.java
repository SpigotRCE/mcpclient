package dev.wrrulos.mcpclient.constants;

public class CommandConstants {
    // Command names
    public static final String HELP_COMMAND = "mcphelp";
    public static final String PLAYERS_COMMAND = "playerl";
    public static final String GAMEMODE_COMMAND = "fakegm";

    // Help messages
    public static final String GENERAL_MESSAGE = "&d⏏ Available commands: \n\n&b/" + HELP_COMMAND + " [command]\n/" + PLAYERS_COMMAND + "\n/" + GAMEMODE_COMMAND + "\n";
    public static final String PLAYERS_MESSAGE = "&8[&b?&8] &bThe players command returns the list of current players on the server, along with their uuid, current game mode, and ping.";
    public static final String GAMEMODE_MESSAGE = "&8[&b?&8] &bThe fake game mode command allows you to change your game mode to survival, creative, adventure, or spectator. (Only visible to you)";

    // Players command
    public static final String NO_PLAYERS = "&cNo players were found.";
    public static final String PLAYERS_TITLE = "&aList of players connected to the server:\n";

    // FakeGame mode command
    public static final String GAMEMODE_MISSING = "&cYou have to enter a gamemode.";
    public static final String GAMEMODE_CHANGED = "&aFake game mode changed to ";
}