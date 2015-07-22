package cz.Sicka.AreaProtection.Configuration.Action;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.Core.Utils.Configuration.Configuration;

public class SaveArea {
	
	public static void save_All(){
		for(String a : Manager.getAllAreas().keySet()){
			save(Manager.getArea(a));
		}
	}
	
	public static void save(Area area){
		savePlayerFlags(area);
		saveFlags(area);
	}
	
	public static void savePlayerFlags(Area area){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getAreaName()) || !c.getConfig().isSet("Areas." + area.getAreaName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: SaveArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: savePlayerFlags");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area not exist!");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: AreasList:&a " + !Manager.isAreaExist(area.getAreaName()));
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Config:&a " + !c.getConfig().isSet("Areas." + area.getAreaName()));
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}else{
			if(!c.getConfig().isSet("Areas." + area.getAreaName() + ".Players")){
				c.getConfig().createSection("Areas." + area.getAreaName() + ".Players");
			}
			for(UUID uuid : area.getAreaPlayerFlags().getPlayerFlags().keySet()){
				if(c.getConfig().isSet("Areas." + area.getAreaName() + ".Players." + uuid.toString())){
					for(String flag : area.getAreaPlayerFlags().getPlayerFlags(uuid).getFlagsAndValues().keySet()){
						c.getConfig().set("Areas." + area.getAreaName() + ".Players." + uuid.toString() + "." + flag,  area.getAreaPlayerFlags().getPlayerFlags(uuid).getFlagValue(flag));
					}
				}else{
					c.getConfig().createSection("Areas." + area.getAreaName() + ".Players." + uuid.toString());
					for(String flag : area.getAreaPlayerFlags().getPlayerFlags(uuid).getFlagsAndValues().keySet()){
						c.getConfig().set("Areas." + area.getAreaName() + ".Players." + uuid.toString() + "." + flag,  area.getAreaPlayerFlags().getPlayerFlags(uuid).getFlagValue(flag));
					}
				}
			}
			c.saveConfig();
		}
	}
	
	public static void saveFlags(Area area){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getAreaName()) || !c.getConfig().isSet("Areas." + area.getAreaName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: SaveArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: saveFlags");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Description: Area not exist!");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: AreasList:&a " + !Manager.isAreaExist(area.getAreaName()));
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Config:&a " + !c.getConfig().isSet("Areas." + area.getAreaName()));
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}else{
			if(!c.getConfig().isSet("Areas." + area.getAreaName() + ".Flags")){
				c.getConfig().createSection("Areas." + area.getAreaName() + ".Flags");
			}
			Map<String, Boolean> map = area.getAreaFlags().getFlagsAndValues();
			for(String flags : map.keySet()){
				c.getConfig().set("Areas." + area.getAreaName() + ".Flags." + flags , map.get(flags));
			}
			c.saveConfig();
		}
	}

}
