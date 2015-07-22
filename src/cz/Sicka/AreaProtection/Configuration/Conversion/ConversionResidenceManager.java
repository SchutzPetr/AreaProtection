package cz.Sicka.AreaProtection.Configuration.Conversion;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.bukkit.World;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Area.AreaPlayerFlags;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.FlagsMap;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class ConversionResidenceManager {
	private AreaProtection plugin;
	
	public ConversionResidenceManager(AreaProtection instance){
		this.plugin = instance;
	}
	
	public void ConvertResidence(){
		File dataF = this.plugin.getDataFolder();
		File worldFolder = new File(new File(dataF, "Residence/Save"), "Worlds");
		if (!worldFolder.isDirectory()) {
            worldFolder.mkdirs();
        }
		for(World world : AreaProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(worldFolder, "res_" + world.getName() + ".yml");
			Configuration residenceConfig = new Configuration(worldFile);
			Configuration areaConfig = this.plugin.getConfigurationManager().getSaveAreasFile(world.getName());
			for(String areaName : residenceConfig.getConfig().getConfigurationSection("Residences").getKeys(false)){
				String selLoc = "Residences." + areaName + ".Data.Area.";
				int x1 = residenceConfig.getConfig().getInt(selLoc + "X1");
				int y1 = residenceConfig.getConfig().getInt(selLoc + "Y1");
				int z1 = residenceConfig.getConfig().getInt(selLoc + "Z1");
				
				int x2 = residenceConfig.getConfig().getInt(selLoc + "X2");
				int y2 = residenceConfig.getConfig().getInt(selLoc + "Y2");
				int z2 = residenceConfig.getConfig().getInt(selLoc + "Z2");
				
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.LowX", x2);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.LowY", y2);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.LowZ", z2);
				
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.HighX", x1);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.HighY", y1);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.HighZ", z1);
				
				String worldLoc = residenceConfig.getConfig().getString(selLoc + "World");
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Location.World", worldLoc);
				
				String selTPLoc = "Residences." + areaName + ".Data.TPLocation.";
				
				int telx = residenceConfig.getConfig().getInt(selTPLoc + "X");
				int tely = residenceConfig.getConfig().getInt(selTPLoc + "Y");
				int telz = residenceConfig.getConfig().getInt(selTPLoc + "Z");
				
				areaConfig.getConfig().set("Areas." + areaName + ".Data.TPLocation.X", telx);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.TPLocation.Y", tely);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.TPLocation.Z", telz);
				
				//TODO: areaConfig.getConfig().set("Areas." + areaName + ".Data.AreaType", "PLAYER_AREA");
				
				String seldata = "Residences." + areaName + ".Data.";
				
				String owner = residenceConfig.getConfig().getString(seldata + ".Owner");
				areaConfig.getConfig().set("Areas." + areaName + ".Data.Owner", owner);
				
				Long creationDate = residenceConfig.getConfig().getLong(seldata + ".CreationDate");
				areaConfig.getConfig().set("Areas." + areaName + ".Data.CreationDate", creationDate);
				
				String enterMessage = residenceConfig.getConfig().getString(seldata + ".EnterMessage");
				String leaveMessage = residenceConfig.getConfig().getString(seldata + ".LeaveMessage");
				areaConfig.getConfig().set("Areas." + areaName + ".Data.EnterMessage", enterMessage);
				areaConfig.getConfig().set("Areas." + areaName + ".Data.LeaveMessage", leaveMessage);
				
				FlagsMap areaFlags = new FlagsMap();
				AreaPlayerFlags areaPlayerFlags = new AreaPlayerFlags();
				
				for(String aflags : residenceConfig.getConfig().getConfigurationSection("Residences." + areaName + ".Flags").getKeys(false)){
					if(CommandsUtils.isValidFlag(aflags)){
						Flag f = FlagManager.getFlag(aflags);
						areaFlags.addFlag(f, residenceConfig.getConfig().getBoolean("Residences." + areaName + ".Flags." + aflags ));
					}else{
						AreaProtection.LogMessage("Fail: Flag is not valid - " + aflags);
					}
				}
				for(String players : residenceConfig.getConfig().getConfigurationSection("Residences." + areaName + ".Players").getKeys(false)){
					for(String plflags : residenceConfig.getConfig().getConfigurationSection("Residences." + areaName + ".Players." + players).getKeys(false)){
						if(CommandsUtils.isValidFlag(plflags)){
							Flag f = FlagManager.getFlag(plflags);
							areaPlayerFlags.addPlayerFlag(UUID.fromString(players), f, residenceConfig.getConfig().getBoolean("Residences." + areaName + ".Players." + players + "." + plflags));
						}else{
							AreaProtection.LogMessage("Fail: Flag is not valid - " + plflags);
						}
					}
				}
				
				if(!residenceConfig.getConfig().isSet("Areas." + areaName + ".Players")){
					residenceConfig.getConfig().createSection("Areas." + areaName + ".Players");
				}
				for(UUID uuid : areaPlayerFlags.getPlayerFlags().keySet()){
					if(residenceConfig.getConfig().isSet("Areas." + areaName + ".Players." + uuid.toString())){
						for(String flag : areaPlayerFlags.getPlayerFlags(uuid).getFlagsAndValues().keySet()){
							areaConfig.getConfig().set("Areas." + areaName + ".Players." + uuid.toString() + "." + flag,  areaPlayerFlags.getPlayerFlags(uuid).getFlagValue(flag));
						}
					}else{
						residenceConfig.getConfig().createSection("Areas." + areaName + ".Players." + uuid.toString());
						for(String flag : areaPlayerFlags.getPlayerFlags(uuid).getFlagsAndValues().keySet()){
							areaConfig.getConfig().set("Areas." + areaName + ".Players." + uuid.toString() + "." + flag,  areaPlayerFlags.getPlayerFlags(uuid).getFlagValue(flag));
						}
					}
				}
				
				if(!residenceConfig.getConfig().isSet("Areas." + areaName + ".Flags")){
					residenceConfig.getConfig().createSection("Areas." + areaName + ".Flags");
				}
				Map<String, Boolean> map = areaFlags.getFlagsAndValues();
				for(String flags : map.keySet()){
					areaConfig.getConfig().set("Areas." + areaName + ".Flags." + flags , map.get(flags));
				}
				areaConfig.saveConfig();
				
				//ConvertSubzone(residenceConfig, areaConfig, areaName);
			}
		}
	}
	
	public void ConvertSubzone(Configuration residenceConfig, Configuration areaConfig, String residence){
		String subzoneSelection = "Residences." + residence + ".Subzones";
		if(!residenceConfig.getConfig().isSet(subzoneSelection)){
			return;
		}
		for(String subzoneName : residenceConfig.getConfig().getConfigurationSection(subzoneSelection).getKeys(false)){
			String subzoneSelectionPlusName = subzoneSelection + "." + subzoneName;
			
			String selLoc = subzoneSelectionPlusName + ".Data.Area.";
			int x1 = residenceConfig.getConfig().getInt(selLoc + "X1");
			int y1 = residenceConfig.getConfig().getInt(selLoc + "Y1");
			int z1 = residenceConfig.getConfig().getInt(selLoc + "Z1");
			
			int x2 = residenceConfig.getConfig().getInt(selLoc + "X2");
			int y2 = residenceConfig.getConfig().getInt(selLoc + "Y2");
			int z2 = residenceConfig.getConfig().getInt(selLoc + "Z2");
			
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.LowX", x2);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.LowY", y2);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.LowZ", z2);
			
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.HighX", x1);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.HighY", y1);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.HighZ", z1);
			
			String worldLoc = residenceConfig.getConfig().getString(selLoc + "World");
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Location.World", worldLoc);
			
			String selTPLoc = subzoneSelectionPlusName + ".Data.TPLocation.";
			
			int telx = residenceConfig.getConfig().getInt(selTPLoc + "X");
			int tely = residenceConfig.getConfig().getInt(selTPLoc + "Y");
			int telz = residenceConfig.getConfig().getInt(selTPLoc + "Z");
			
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.TPLocation.X", telx);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.TPLocation.Y", tely);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.TPLocation.Z", telz);
			
			//TODO: areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.AreaType", "PLAYER_AREA");
			
			String seldata = subzoneSelectionPlusName + ".Data.";
			
			String owner = residenceConfig.getConfig().getString(seldata + ".Owner");
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.Owner", owner);
			
			Long creationDate = residenceConfig.getConfig().getLong(seldata + ".CreationDate");
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.CreationDate", creationDate);
			
			String enterMessage = residenceConfig.getConfig().getString(seldata + ".EnterMessage");
			String leaveMessage = residenceConfig.getConfig().getString(seldata + ".LeaveMessage");
			
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.EnterMessage", enterMessage);
			areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Data.LeaveMessage", leaveMessage);
			
			FlagsMap areaFlags = new FlagsMap();
			AreaPlayerFlags areaPlayerFlags = new AreaPlayerFlags();
			
			for(String aflags : residenceConfig.getConfig().getConfigurationSection(subzoneSelectionPlusName + ".Flags").getKeys(false)){
				if(CommandsUtils.isValidFlag(aflags)){
					Flag f = FlagManager.getFlag(aflags);
					areaFlags.addFlag(f, residenceConfig.getConfig().getBoolean(subzoneSelectionPlusName + ".Flags." + aflags ));
				}else{
					AreaProtection.LogMessage("Fail: Flag is not valid - " + aflags);
				}
			}
			for(String players : residenceConfig.getConfig().getConfigurationSection(subzoneSelectionPlusName + ".Players").getKeys(false)){
				for(String plflags : residenceConfig.getConfig().getConfigurationSection(subzoneSelectionPlusName + ".Players." + players).getKeys(false)){
					if(CommandsUtils.isValidFlag(plflags)){
						Flag f = FlagManager.getFlag(plflags);
						areaPlayerFlags.addPlayerFlag(UUID.fromString(players), f, residenceConfig.getConfig().getBoolean(subzoneSelectionPlusName + ".Players." + players + "." + plflags));
					}else{
						AreaProtection.LogMessage("Fail: Flag is not valid - " + plflags);
					}
				}
			}
			
			if(!residenceConfig.getConfig().isSet("Areas." + residence + ".Subzones." + subzoneName + ".Players")){
				residenceConfig.getConfig().createSection("Areas." + residence + ".Subzones." + subzoneName + ".Players");
			}
			for(UUID uuid : areaPlayerFlags.getPlayerFlags().keySet()){
				if(residenceConfig.getConfig().isSet("Areas." + residence + ".Subzones." + subzoneName + ".Players." + uuid.toString())){
					for(String flag : areaPlayerFlags.getPlayerFlags(uuid).getFlagsAndValues().keySet()){
						areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Players." + uuid.toString() + "." + flag,  areaPlayerFlags.getPlayerFlags(uuid).getFlagValue(flag));
					}
				}else{
					residenceConfig.getConfig().createSection("Areas." + residence + ".Subzones." + subzoneName + ".Players." + uuid.toString());
					for(String flag : areaPlayerFlags.getPlayerFlags(uuid).getFlagsAndValues().keySet()){
						areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Players." + uuid.toString() + "." + flag,  areaPlayerFlags.getPlayerFlags(uuid).getFlagValue(flag));
					}
				}
			}
			
			if(!residenceConfig.getConfig().isSet("Areas." + residence + ".Subzones." + subzoneName + ".Flags")){
				residenceConfig.getConfig().createSection("Areas." + residence + ".Subzones." + subzoneName + ".Flags");
			}
			Map<String, Boolean> map = areaFlags.getFlagsAndValues();
			for(String flags : map.keySet()){
				areaConfig.getConfig().set("Areas." + residence + ".Subzones." + subzoneName + ".Flags." + flags , map.get(flags));
			}
			areaConfig.saveConfig();
		}
	}
	
	
}
