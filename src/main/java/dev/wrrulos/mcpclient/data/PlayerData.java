package dev.wrrulos.mcpclient.data;

import dev.wrrulos.mcpclient.util.ColorUtil;
import dev.wrrulos.mcpclient.util.StringUtil;
import net.minecraft.text.Text;

public class PlayerData {
    String name;
    String uuid;
    String gameMode;
    int ping;

    /**
     * Player data constructor
     * @param name Player name
     * @param uuid Player UUID
     * @param gameMode Player game mode
     * @param ping Player ping
     */
    public PlayerData(String name, String uuid, String gameMode, int ping) {
        this.name = name;
        this.uuid = uuid;
        this.gameMode = gameMode;
        this.ping = ping;
    }

    /**
     * Get the player message with the player name, UUID, game mode, and ping
     * @return Player message
     */
    public Text getMessage() {
        Text nameText = ColorUtil.parseColoredText("&7" + name);
        Text uuidText = ColorUtil.parseColoredText(" &8[&bCopy UUID&8]", uuid);
        Text gamemodeText = ColorUtil.parseColoredText(" &8(" + getGameModeColor() + StringUtil.capitalizeFirstLetter(gameMode) + "&8)");
        Text pingText = ColorUtil.parseColoredText(" &8(&a" + ping + "ms&8)");
        return Text.empty()
            .append(nameText)
            .append(uuidText)
            .append(gamemodeText)
            .append(pingText);
    }

    /**
     * Get the player game mode color
     * @return Game mode color
     */
    public String getGameModeColor() {
        return switch (gameMode.toLowerCase()) {
            case "survival" -> "&c";
            case "creative" -> "&a";
            case "adventure" -> "&b";
            default -> "&7";
        };
    }
}
