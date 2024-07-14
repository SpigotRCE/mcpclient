package dev.wrrulos.mcpclient.presence;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import dev.wrrulos.mcpclient.constants.DiscordPresenceConstants;
import java.io.File;
import java.time.Instant;

public class DiscordPresence implements Runnable {
    public static Boolean playingAServer = false;
    public static String serverAddress = "";

    @Override
    public void run() {
        Core.init(new File(DiscordPresenceConstants.DLL_PATH));

        // Set parameters for the Core
        try(CreateParams params = new CreateParams()) {
            long discordClientID = 1259173092954079345L;
            params.setClientID(discordClientID);
            params.setFlags(CreateParams.getDefaultFlags());

            // Create the Core
            try(Core core = new Core(params)) {
                // Create the firsts activity
                try(Activity activity = new Activity()) {
                    activity.setDetails(DiscordPresenceConstants.MENU_TEXT);
                    activity.timestamps().setStart(Instant.now());
                    activity.assets().setLargeImage(DiscordPresenceConstants.IMAGE);
                    core.activityManager().updateActivity(activity);

                    // Run callbacks forever
                    while(true) {
                        core.runCallbacks();
                        try {
                            if (playingAServer) {
                                activity.setDetails(DiscordPresenceConstants.PLAYING_TEXT);
                                activity.setState(serverAddress);
                            } else {
                                activity.setDetails(DiscordPresenceConstants.MENU_TEXT);
                                activity.setState("");
                            }
                            core.activityManager().updateActivity(activity);
                            Thread.sleep(16);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }
}
