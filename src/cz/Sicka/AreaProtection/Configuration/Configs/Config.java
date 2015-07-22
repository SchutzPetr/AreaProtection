package cz.Sicka.AreaProtection.Configuration.Configs;

import java.io.File;
import java.util.Map;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Utils.FlagsMap;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class Config {
	Configuration config;
	private AreaProtection plugin;
	
	String defaultEnterMessage;
	String defaultLeaveMessage;
	String version;

	FlagsMap areaFlags;
	
	int maxHeight;
	int minHeight;
	int maxLength;
	int minLength;
	int maxWidth;
	int minWidth;
	
	int priceOfTheBlockExpanse;

	boolean economy;
	
	public Config(AreaProtection plugin){
		this.config = new Configuration(new File(this.plugin.getDataFolder(), "Configuration.yml"));
		
		this.defaultEnterMessage = this.config.getConfig().getString("DefaultAreaSettings.DefaultEnterMessage");
		this.defaultLeaveMessage = this.config.getConfig().getString("DefaultAreaSettings.DefaultLeaveMessage");
		this.version = this.config.getConfig().getString("Version");
		
		this.areaFlags = new FlagsMap();
		
		Map<String, Object> flags = this.config.getConfig().getConfigurationSection("DefaultAreaSettings.AreaFlags").getValues(false);
		for(String flag : flags.keySet()){
			this.areaFlags.addFlag(flag, ((Boolean)flags.get(flag)).booleanValue());
		}
		
		this.maxHeight = this.config.getConfig().getInt("DefaultAreaSettings.MaxHeight");
		this.minHeight = this.config.getConfig().getInt("DefaultAreaSettings.MinHeight");
		this.maxLength = this.config.getConfig().getInt("DefaultAreaSettings.MaxLength");
		this.minLength = this.config.getConfig().getInt("DefaultAreaSettings.MinLength");
		this.maxWidth = this.config.getConfig().getInt("DefaultAreaSettings.MaxWidth");
		this.minWidth = this.config.getConfig().getInt("DefaultAreaSettings.MinWidth");
		
		this.priceOfTheBlockExpanse = this.config.getConfig().getInt("Settings.PriceOfTheBlockExpanse");
		this.economy = this.config.getConfig().getBoolean("Settings.Economy");
	}

	/**
	 * @return the config
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * @return the defaultEnterMessage
	 */
	public String getDefaultEnterMessage() {
		return defaultEnterMessage;
	}

	/**
	 * @return the defaultLeaveMessage
	 */
	public String getDefaultLeaveMessage() {
		return defaultLeaveMessage;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the areaFlags
	 */
	public FlagsMap getAreaFlags() {
		return areaFlags;
	}

	/**
	 * @return the maxHeight
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * @return the minHeight
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @return the minLength
	 */
	public int getMinLength() {
		return minLength;
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * @return the minWidth
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * @return the priceOfTheBlockExpanse
	 */
	public int getPriceOfTheBlockExpanse() {
		return priceOfTheBlockExpanse;
	}

	/**
	 * @return the economy
	 */
	public boolean isEconomy() {
		return economy;
	}

}
