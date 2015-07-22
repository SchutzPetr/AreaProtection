package cz.Sicka.AreaProtection.Configuration;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class Debug {
	File folder = new File(AreaProtection.getInstance().getDataFolder(), "debug.yml");
	private Configuration conf;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public Debug(){
		this.conf = new Configuration(this.folder);
	}
	
	@Deprecated
	public void log(String error){
		Date today = Calendar.getInstance().getTime();        
		String reportDate = df.format(today);
		this.conf.getConfig().set("Report Date: " + reportDate, error);
	}
	
	public void log(List<String> error){
		
	}
	
	public void save(){
		this.conf.saveConfig();
	}
}
