package pl.masnypen.penChecker.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LangManager {
    private FileConfiguration languageConfig;

    public LangManager(File pluginFolder) {

        File languageFile = new File(pluginFolder, "messages.yml");
        if (!languageFile.exists()) {
            saveDefaultResource(languageFile, "messages.yml");
        }

        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }

    /**
     * Pobiera wiadomość z pliku językowego i zamienia & na kody kolorów.
     *
     * @param key           Klucz wiadomości
     * @param defaultValue  Domyślna wartość, jeśli klucz nie istnieje
     * @return Sformatowana wiadomość
     */
    public String getMessage(String key, String defaultValue) {
        if (!languageConfig.contains(key)) {
            languageConfig.set(key, defaultValue);
            saveConfig(languageConfig);
        }
        return ChatColor.translateAlternateColorCodes('&', languageConfig.getString(key, defaultValue));
    }

    private void saveDefaultResource(File targetFile, String resourceName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName);
             FileOutputStream out = new FileOutputStream(targetFile)) {

            if (in == null) {
                System.err.println("Resource " + resourceName + " not found in the plugin JAR.");
                return;
            }

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Copied default language file: " + resourceName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig(FileConfiguration config) {
        try {
            File configFile = new File("messages.yml");
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
