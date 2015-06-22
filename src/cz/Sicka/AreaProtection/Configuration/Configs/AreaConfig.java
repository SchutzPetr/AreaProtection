package cz.Sicka.AreaProtection.Configuration.Configs;

import org.bukkit.Location;

import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class AreaConfig {
	private Configuration config;
	private String areaName;
	
	public AreaConfig(Configuration instanceConfiguration, String areaName){
		this.config = instanceConfiguration;
		this.areaName = areaName;
	}

	public void setFirstLocation(Location loc){
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowX", loc.getBlockX());
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowY", loc.getBlockY());
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowZ", loc.getBlockZ());
	}
	
	public void setSecondLocation(Location loc){
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighX", loc.getBlockX());
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighY", loc.getBlockY());
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighZ", loc.getBlockZ());
	}
	
	public void setFirstLocation(int x, int y, int z){
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowX", x);
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowY", y);
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.LowZ", z);
	}
	
	public void setSecondLocation(int x, int y, int z){
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighX", x);
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighY", y);
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.HighZ", z);
	}
	
	public void setWorld(String worldName){
		this.config.getConfig().set("Areas." + areaName + ".Data.Location.World", worldName);
	}
	
	public void setOwner(String ownerUUID){
		this.config.getConfig().set("Areas." + areaName + ".Data.Owner", ownerUUID);
	}
	
	public void setAreaType(AreaType type){
		this.config.getConfig().set("Areas." + areaName + ".Data.AreaType", type);
	}
	
	public void setCreationDate(long date){
		this.config.getConfig().set("Areas." + areaName + ".Data.CreationDate", date);
	}
	
	public void setPlayerFlags(String uuid, FlagsMap map){
		for(String flags : map.getFlagsAndValues().keySet()){
			this.config.getConfig().set("Areas." + areaName + ".Players." + uuid + "." + flags,  map.getFlagValue(flags));
		}
	}
	
	public void setAreaFlags(FlagsMap map){
		for(String flags : map.getFlagsAndValues().keySet()){
			this.config.getConfig().set("Areas." + areaName + ".Flags." + flags,  map.getFlagValue(flags));
		}
	}
}
