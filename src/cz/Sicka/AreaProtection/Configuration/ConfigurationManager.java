package cz.Sicka.AreaProtection.Configuration;

import java.util.Map;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Configuration.Action.LoadArea;
import cz.Sicka.AreaProtection.Configuration.Action.LoadWorldArea;
import cz.Sicka.AreaProtection.Configuration.Configs.Config;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class ConfigurationManager {
	private AreaProtection plugin;
	private LoadArea loadArea;
	private LoadWorldArea loadWorldArea;
	private Config config;
	
	public ConfigurationManager(AreaProtection instance){
		this.plugin = instance;
		this.loadArea = new LoadArea(this.plugin);
		this.loadWorldArea = new LoadWorldArea(this.plugin);
		this.config = new Config(this.plugin);
	}
	
	public Map<String, Configuration> getSaveAreasFiles(){
		return loadArea.getSaveAreasFiles();
	}
	
	public Map<String, Configuration> getSaveWorldAreasFiles(){
		return loadWorldArea.getSaveWorldAreasFiles();
	}
	
	public Configuration getSaveAreasFile(String worldName){
		return this.getSaveAreasFiles().get(worldName);
	}
	
	public Configuration getSaveWorldAreasFile(String worldName){
		return this.getSaveWorldAreasFiles().get(worldName);
	}
	
	/*public Map<String, Configuration> getWorldConfigs(){
		return worldsworldConfigs;
	}*/
	
	public void saveFiles(){
		for(Configuration c : this.getSaveAreasFiles().values()){
			c.saveConfig();
		}
		
		for(Configuration c : this.getSaveWorldAreasFiles().values()){
			c.saveConfig();
		}
	}

	public LoadArea getLoadArea() {
		return loadArea;
	}
	
	public LoadWorldArea getLoadWorldArea() {
		return loadWorldArea;
	}

	/**
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}
}
