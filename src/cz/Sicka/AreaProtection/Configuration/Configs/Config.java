package cz.Sicka.AreaProtection.Configuration.Configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import cz.Sicka.AreaProtection.AreaProtection;

public class Config {
	private FileConfiguration config = null;
	private File configfile = null;
	private AreaProtection plugin;
	
	public Config(AreaProtection instance, File folder, String yamlName){
		plugin = instance;
	}
	
	
	public void reloadConfig() {
		if(configfile == null){
			configfile = new File(plugin.getDataFolder(), ".yml");
		}
		config = YamlConfiguration.loadConfiguration(configfile);
		// Look for defaults in the jar
	    try {
			Reader defConfigStream = new InputStreamReader(plugin.getResource("config.yml"), "UTF8");
			if (defConfigStream != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        config.setDefaults(defConfig);
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	public FileConfiguration getConfig() {
	    if (config == null) {
	        reloadConfig();
	    }
	    return config;
	}
	
	public void saveConfig() {
	    if (config == null || configfile == null) {
	        return;
	    }
	    try {
	        getConfig().save(configfile);
	    } catch (IOException ex) {
	    	//TODO: AreaProtection.LogMessage(Level.SEVERE, Lang.CouldNotSaveConfig + configfile.getName());
	    }
	}
	
	public void saveDefaultConfig() {
	    if (configfile == null) {
	        configfile = new File(plugin.getDataFolder(), "config.yml");
	    }
	    if (!configfile.exists()) {            
	         plugin.saveResource("config.yml", false);
	       //TODO: AreaProtection.LogMessage(Level.INFO, Lang.CreateConfigFile + configfile.getName());
	    }
	}

}
