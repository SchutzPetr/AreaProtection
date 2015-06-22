package cz.Sicka.AreaProtection.Configuration.Action;

import java.util.logging.Level;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Configuration.Configuration;
import cz.Sicka.AreaProtection.Lang.Lang;

public class EditArea {
	
	public static void removePlayerFlag(Area area, Player player, String flag){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getAreaName()) || !c.getConfig().isSet("Areas." + area.getAreaName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: EditArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removePlayerFlag");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}else{
			if(c.getConfig().isSet("Areas." + area.getAreaName() + ".Players." + player.getUniqueId().toString()) && c.getConfig().isSet("Areas." + area.getAreaName() + ".Players." + player.getUniqueId().toString() + "." + flag)){
				c.getConfig().set("Areas." + area.getAreaName() + ".Players." + player.getUniqueId().toString() + "." + flag, null);
				if(c.getConfig().getConfigurationSection("Areas." + area.getAreaName() + ".Players." + player.getUniqueId().toString()).getKeys(false).size() == 0){
					c.getConfig().set("Areas." + area.getAreaName() + ".Players." + player.getUniqueId().toString(), null);
				}
				c.saveConfig();
			}else{
				player.sendMessage("111");
				return;
			}
		}
	}
	
	public static void removeAreaFlag(Area area, String flag){
		Configuration c = AreaProtection.getInstance().getConfigurationManager().getSaveAreasFiles().get(area.getWorldName());
		if(!Manager.getAllAreas().containsKey(area.getAreaName()) || !c.getConfig().isSet("Areas." + area.getAreaName())){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: EditArea");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: removeAreaFlag");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}else{
			if(c.getConfig().isSet("Areas." + area.getAreaName() + ".Flags." + flag)){
				c.getConfig().set("Areas." + area.getAreaName() + ".Flags." + flag, null);
				c.saveConfig();
			}else{
				return;
			}
		}
	}
}
