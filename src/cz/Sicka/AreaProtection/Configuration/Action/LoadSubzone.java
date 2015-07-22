package cz.Sicka.AreaProtection.Configuration.Action;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaPlayerFlags;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.FlagsMap;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class LoadSubzone {
	
	public static void loadAreaSubzones(Area area, Configuration conf){
		String areaName = area.getAreaName();
		if(!conf.getConfig().isSet("Areas." + areaName + ".Subzones")){
			return;
		}
		for(String subzoneName : conf.getConfig().getConfigurationSection("Areas." + areaName + ".Subzones").getKeys(false)){
			String SubzoneSelection = "Areas." + areaName + ".Subzones." + subzoneName;
			
			try{
				int x1 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.LowX");
				int y1 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.LowY");
				int z1 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.LowZ");
				
				int x2 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.HighX");
				int y2 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.HighY");
				int z2 = conf.getConfig().getInt(SubzoneSelection + ".Data.Location.HighZ");
				
				int telx = conf.getConfig().getInt(SubzoneSelection + ".Data.TPLocation.X");
				int tely = conf.getConfig().getInt(SubzoneSelection + ".Data.TPLocation.Y");
				int telz = conf.getConfig().getInt(SubzoneSelection + ".Data.TPLocation.Z");
				
				String world = conf.getConfig().getString(SubzoneSelection + ".Data.Location.World");
				
				Location teleportLocation = new Location(Bukkit.getWorld(world), telx, tely, telz);
				
				String owner = conf.getConfig().getString(SubzoneSelection + ".Data.Owner");
				
				String enterMessage = conf.getConfig().getString(SubzoneSelection + ".Data.EnterMessage");
				String leaveMessage = conf.getConfig().getString(SubzoneSelection + ".Data.LeaveMessage");
				
				Subzone subzone = new Subzone(subzoneName, area, owner, x1, y1, z1, x2, y2, z2, world);
				
				subzone.setEnterMessage(enterMessage);
				subzone.setLeaveMessage(leaveMessage);
				
				subzone.setTeleportLocation(teleportLocation);
				
				subzone.setCreationDate(conf.getConfig().getLong(SubzoneSelection + ".Data.CreationDate"));
				
				FlagsMap areaFlags = new FlagsMap();
				AreaPlayerFlags areaPlayerFlags = new AreaPlayerFlags();
				for(String aflags : conf.getConfig().getConfigurationSection(SubzoneSelection + ".Flags").getKeys(false)){
					Flag f = FlagManager.getFlag(aflags);
					areaFlags.addFlag(f, conf.getConfig().getBoolean(SubzoneSelection + ".Flags." + aflags ));
				}
				for(String players : conf.getConfig().getConfigurationSection(SubzoneSelection + ".Players").getKeys(false)){
					for(String plflags : conf.getConfig().getConfigurationSection(SubzoneSelection + ".Players." + players).getKeys(false)){
						Flag f = FlagManager.getFlag(plflags);
						areaPlayerFlags.addPlayerFlag(UUID.fromString(players), f, conf.getConfig().getBoolean(SubzoneSelection + ".Players." + players + "." + plflags));
					}
				}
				subzone.setAreaFlags(areaFlags);
				subzone.setAreaPlayerFlags(areaPlayerFlags);
				
				//AreaProtection.LogMessage("Load: " + subzone.getFullName());
				area.getSubzoneManager().addSubzone(subzoneName, subzone);
				//TODO: only load
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
