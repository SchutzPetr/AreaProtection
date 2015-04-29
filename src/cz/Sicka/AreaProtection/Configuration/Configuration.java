package cz.Sicka.AreaProtection.Configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration {
    private final File configFile;
    private FileConfiguration config;

    public Configuration (File configFile) {
        this.configFile = configFile;
        reloadConfig();
    }

    public FileConfiguration getConfig() {
    	if (config == null) {
	        reloadConfig();
	    }
	    return config;
    }

    public File getFile() {
        return configFile;
    }

    public boolean reloadConfig() {
        try {
            config = YamlConfiguration.loadConfiguration(configFile);
            return true;
        } catch (Exception erorr) {
            return false;
        }
    }

    public void saveConfig() {
    	if (config == null || configFile == null) {
	        return;
	    }
    	try {
	        getConfig().save(configFile);
	    } catch (IOException ex) {
	    	//TODO: AreaProtection.LogMessage(Level.SEVERE, Lang.CouldNotSaveConfig + configfile.getName());
	    }
    }
}
