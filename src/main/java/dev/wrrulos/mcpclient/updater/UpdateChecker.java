package dev.wrrulos.mcpclient.updater;

import dev.wrrulos.mcpclient.constants.ClientConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateChecker {
    private static final String VERSION_URL = "https://raw.githubusercontent.com/pedroagustinvega/mcpclient/main/version";
    private static final String LOCAL_VERSION = "1.0.1"; // Local version
    private static final Logger LOGGER = Logger.getLogger(UpdateChecker.class.getName());

    public static void main(String[] args) {
        checkForUpdates();
    }

    /**
     * Checks if an update is available by comparing the local version with the remote version.
     */
    public static void checkForUpdates() {
        try {
            String remoteVersion = getRemoteVersion();

            if (remoteVersion == null) {
                remoteVersion = LOCAL_VERSION;
            }

            if (!remoteVersion.equals(LOCAL_VERSION)) {
                ClientConstants.NEW_VERSION = remoteVersion;
            }

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error checking for updates", e);
        }
    }

    /**
     * Gets the remote version from the configured URL.
     *
     * @return The remote version as a string.
     * @throws IOException If an error occurs while making the HTTP request.
     */
    private static String getRemoteVersion() throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URI uri = new URI(VERSION_URL);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();

            if (status != HttpURLConnection.HTTP_OK) {
                LOGGER.warning("Received non-OK response code: " + status);
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return reader.readLine().trim();

        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve remote version", e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Failed to close BufferedReader", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
