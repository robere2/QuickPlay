package co.bugg.quickplay.config;

import co.bugg.quickplay.QuickPlay;
import co.bugg.quickplay.Reference;
import co.bugg.quickplay.util.FileUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.*;

public class ConfigManager {

    // Paths to folders
    public String configPath = "config";
    public String modFolderPath = Reference.MOD_ID;
    public String fullPath = configPath + "/" + modFolderPath;

    // Path & File to the config file
    public String configFilePath = fullPath + "/config.ser";
    public File configFile = new File(configFilePath);

    // new ConfigSettings instance by default, but will later be assigned
    // to the instance in our file if possible.
    private ConfigSettings config = new ConfigSettings();

    public ConfigManager() {
        // Create directories if they don't exist already
        FileUtil.createDir(new File(configPath));
        boolean dirExists = FileUtil.createDir(new File(fullPath));

        if(dirExists) {
            try {
                // Try reading the file for the configuration
                FileInputStream configFileInputStream = new FileInputStream(configFile);
                ObjectInputStream configObjectInputStream = new ObjectInputStream(configFileInputStream);

                config = (ConfigSettings) configObjectInputStream.readObject();

                configFileInputStream.close();
                configObjectInputStream.close();
            } catch (FileNotFoundException e) {

                // Config file wasn't found, so let's try creating it.
                boolean createSuccessful = FileUtil.createFile(configFile);

                if (createSuccessful) {
                    saveConfig();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Configuration directory doesn't exist & wasn't able to be created! Configuration changes might not be saved.");
        }

        // Register this instance to the event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Getter for the config
     * @return ConfigSettings
     */
    public ConfigSettings getConfig() {
        return this.config;
    }

    private int configUpdateTickCounter = 0;
    private int updateConfigEveryTicks = 1200;
    /**
     * Config file is saved every updateConfigEveryTicks ticks
     * @param event Forge event
     */
    @SubscribeEvent
    public void gameTick(TickEvent.ClientTickEvent event) {
        if(configUpdateTickCounter >= updateConfigEveryTicks) {
            configUpdateTickCounter = 0;

            // Update keybinds
            {
                config.openGuiKey = QuickPlay.openGui.getKeyCode();
                config.openFavoriteKey = QuickPlay.openFavorite.getKeyCode();
            }

            saveConfig();
        }

        configUpdateTickCounter++;
    }

    /**
     * Save the config to its file
     */
    public void saveConfig() {
        try {
            FileUtil.serializeObject(configFile, config);
        } catch (IOException e1) {
            // Error serializing default config settings to the file
            System.out.println("Error serializing ConfigSettings object. Configuration changes might not be saved.");
            e1.printStackTrace();
        }
    }

    /**
     * Reset the config to a new ConfigSettings instance
     */
    public void resetConfig() {
        config = new ConfigSettings();
        saveConfig();
    }

    public void togglePartyCommand(String command, boolean enabled) {
        // Only change the value if it's not already set correctly
        if(enabled && !getConfig().enabledPartyCommands.contains(command)) {
            getConfig().enabledPartyCommands.add(command);
        } else if(!enabled && getConfig().enabledPartyCommands.contains(command)) {
            getConfig().enabledPartyCommands.remove(command);
        }

        saveConfig();
    }
}
