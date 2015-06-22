package cz.Sicka.AreaProtection.Configuration;

import java.util.Map;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Configuration.Action.LoadArea;
import cz.Sicka.AreaProtection.Configuration.Action.LoadWorldArea;

public class ConfigurationManager {
	private AreaProtection plugin;
	private LoadArea loadArea;
	private LoadWorldArea loadWorldArea;
	
	public ConfigurationManager(AreaProtection instance){
		this.plugin = instance;
		this.loadArea = new LoadArea(this.plugin);
		this.loadWorldArea = new LoadWorldArea(this.plugin);
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
}
