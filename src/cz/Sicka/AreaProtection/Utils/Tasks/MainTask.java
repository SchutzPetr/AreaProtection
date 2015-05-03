package cz.Sicka.AreaProtection.Utils.Tasks;

import java.io.IOException;

import org.bukkit.Bukkit;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Utils.ZipLibrary;

public class MainTask {
	private AreaProtection plugin;
	private int taskid;
	
	public MainTask(AreaProtection instance){
		this.plugin = instance;
		AreaProtectionMainTask();
	}

	public void AreaProtectionMainTask(){
		taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){

			@Override
			public void run(){
				try {
					save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0L, 600*20L);
		
	}
	
	
	private void save() throws IOException{
		ZipLibrary.backup();
		plugin.getConfigurationManager().saveFiles();
		plugin.getDebug().save();
		plugin.getLangConfiguration().save();
		ZipLibrary.backup();
	}

	public int getTaskID() {
		return taskid;
	}
}
