package cz.Sicka.AreaProtection.Commands.Commands;

import java.util.List;

import org.bukkit.Location;
import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaManager;
import cz.Sicka.AreaProtection.Commands.CommandsUtils;
import cz.Sicka.AreaProtection.Configuration.Configs.Config;
import cz.Sicka.AreaProtection.Utils.Selections.Selection;
import cz.Sicka.AreaProtection.Utils.Selections.SelectionManager;
import cz.Sicka.Core.Users.User;


public class CreationCommand {
	static Config conf = AreaProtection.getInstance().getConfigurationManager().getConfig();
	
	public static boolean createArea(User user, String areaName){
		if(CommandsUtils.isAreaExist(areaName)){
			user.sendMessage("AreaExist");
			return false;
		}
		if(!SelectionManager.alreadyInSelection(user.getOfflinePlayer().getPlayer())){
			return false;
		}
		Selection sel = SelectionManager.getPlayerSelection(user.getOfflinePlayer().getPlayer());
		if(!sel.isSelectionComplete()){
			return false;
		}
		List<Area> areas = AreaManager.getColisionAreas(sel.getFirstPosition(), sel.getSecondPosition());
		if(areas == null)System.out.print(areas==null);
		if(!areas.isEmpty() || areas.size() != 0){
			user.sendMessage("Colision");
			for(Area a : areas){
				user.sendMessage(a.getAreaName());
			}
			return false;
		}
		Location f = sel.getFirstPosition();
		Location s = sel.getSecondPosition();
		
		Area area = new Area(areaName, user.getUniqueId().toString(), f.getBlockX(), f.getBlockY(), f.getBlockZ(), s.getBlockX(), s.getBlockY(), s.getBlockZ(), f.getWorld().getName());
		
		if(area.getXSize() > conf.getMaxWidth()){
			user.sendMessage("TooWide");
			return false;
		}
		if(area.getXSize() < conf.getMinWidth()){
			user.sendMessage("TooNarrow");
			return false;
		}
		if(area.getZSize() > conf.getMaxLength()){
			user.sendMessage("TooLong");
			return false;
		}
		if(area.getZSize() < conf.getMinLength()){
			user.sendMessage("TooSkinny");
			return false;
		}
		if(area.getYSize() > conf.getMaxHeight()){
			user.sendMessage("TooTall");
			return false;
		}
		if(area.getYSize() < conf.getMinHeight()){
			user.sendMessage("TooShort");
			return false;
		}
		if(user.getAreas().size() >= user.getMaxAreas()){
			user.sendMessage("MaxAreasLimit");
			return false;
		}

		
		AreaManager.createMainArea(area);
		user.sendMessage("succes");
		return true;
	}
}
