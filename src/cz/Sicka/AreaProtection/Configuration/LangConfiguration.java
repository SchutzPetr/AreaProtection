package cz.Sicka.AreaProtection.Configuration;

import java.io.File;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class LangConfiguration {
	File folder = new File(AreaProtection.getInstance().getDataFolder(), "language.yml");
	private Configuration config;
	
	public LangConfiguration(){
		this.config = new Configuration(this.folder);
	}

	public Configuration getConfig() {
		return this.config;
	}
	
	public void save(){
		this.config.saveConfig();
	}
}
