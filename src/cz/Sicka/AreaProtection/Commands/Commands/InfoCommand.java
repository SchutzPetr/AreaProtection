package cz.Sicka.AreaProtection.Commands.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.AreaType;
import cz.Sicka.AreaProtection.Area.PermissionArea;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Area.WorldArea;
import cz.Sicka.AreaProtection.Utils.FlagsMap;
import cz.Sicka.AreaProtection.Utils.Messages;

public class InfoCommand {
	
	public static void onInfoCommand(PermissionArea permArea, Player sender){
		onInfoCommand(permArea.getFullName(), sender);
	}
	
	public static void onInfoCommand(String areaName, Player sender){
		String lowerCaseName = areaName.toLowerCase();
		System.out.print(lowerCaseName);
		if(Manager.isWorldExist(lowerCaseName)){
			WorldArea wa = cz.Sicka.AreaProtection.Manager.getWorldArea(lowerCaseName);
			printInfo(sender, wa);
		}
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainArea = split[0];
			String sub = split[1];
			if(Manager.isAreaExist(mainArea)){
				Area area = Manager.getArea(mainArea);
				if(area.getSubzoneManager().isSubzoneExist(sub)){
					Subzone subzone = area.getSubzoneManager().getSubzone(sub);
					printInfo(sender, subzone);
				}else{
					System.out.print("Subzone not exist!");
					return;
				}
			}else{
				System.out.print("Area not exist!");
				return;
			}
		}else{
			if(Manager.isAreaExist(lowerCaseName)){
				Area area = Manager.getArea(lowerCaseName);
				printInfo(sender, area);
			}else{
				System.out.print("Area not exist!");
				return;
			}
		}
	}
	
	private static void printInfo(Player sender, WorldArea wa) {
		Messages.sendMessage(sender, "&7++++&2--------[ &6" + "Informace o oblasti: &a" + wa.getAreaName() + "&2 ]--------&7++++");
		Messages.sendMessage(sender, "&6Jmeno oblasti&7: &b" + wa.getAreaName());
		Messages.sendMessage(sender, "&6Typ oblasti&7: &b" + AreaType.WORLD_AREA.toString());
		Messages.sendMessage(sender, "&6Vlastnik oblasti&7: &b" + wa.getOwner());
		Messages.sendMessage(sender, "&6Vlajky oblasti&7: &b" + getAreaFlagsInfo(wa.getWorldFlags()));
	}

	private static void printInfo(Player sender, Area area) {
		Messages.sendMessage(sender, "&7++++&2--------[ &6" + "Informace o oblasti: &a" + area.getAreaName() + "&2 ]--------&7++++");
		Messages.sendMessage(sender, "&6Jmeno oblasti&7: &b" + area.getAreaName());
		Messages.sendMessage(sender, "&6Typ oblasti&7: &b" + AreaType.MAIN.toString());
		Messages.sendMessage(sender, "&6Vlastnik oblasti&7: &b" + area.getOwner().getName());
		Messages.sendMessage(sender, "&6Vlajky oblasti&7: &b" + getAreaFlagsInfo(area.getAreaFlags()));
		Messages.sendMessage(sender, "&6Tve vlajky&7: &b" + getAreaPlayerFlagsInfo(area, sender));
		Messages.sendMessage(sender, "&6Vytvoreni oblasti&7: &b" + getDateByCurrentTimeMillis(area.getCreationDate()));
	}
	
	private static void printInfo(Player sender, Subzone subzone) {
		Messages.sendMessage(sender, "&7++++&2--------[ &6" + "Informace o oblasti: &a" + subzone.getSubzoneName() + "&2 ]--------&7++++");
		Messages.sendMessage(sender, "&6Jmeno oblasti&7: &b" + subzone.getSubzoneName());
		Messages.sendMessage(sender, "&6Typ oblasti&7: &b" + AreaType.SUBZONE.toString());
		Messages.sendMessage(sender, "&6Vlastnik oblasti&7: &b" + subzone.getOwner().getName());
		Messages.sendMessage(sender, "&6Vlajky oblasti&7: &b" + getAreaFlagsInfo(subzone.getAreaFlags()));
		Messages.sendMessage(sender, "&6Tve vlajky&7: &b" + getAreaPlayerFlagsInfo(subzone, sender));
		Messages.sendMessage(sender, "&6Vytvoreni oblasti&7: &b" + getDateByCurrentTimeMillis(subzone.getCreationDate()));
	}

	private static String getAreaPlayerFlagsInfo(Subzone subzone, Player sender) {
		if(subzone.isOwner(sender.getUniqueId())){
			return ChatColor.GREEN + "Jsi vlastnik oblasti! Proto mas vsechna opravneni.";
		}
		if(!subzone.getAreaPlayerFlags().containsPlayer(sender)){
			return ChatColor.RED + "Zadne";
		}
		StringBuilder flagsinfo = new StringBuilder();
		for(String flag : subzone.getAreaPlayerFlags().getPlayerFlags(sender.getUniqueId()).getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(subzone.getAreaPlayerFlags().getPlayerFlags(sender.getUniqueId()).getFlagValue(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag);
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag);
			}
		}
		return flagsinfo.toString();
	}
	
	private static String getDateByCurrentTimeMillis (long date){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(date);    
		return sdf.format(resultdate);
	}
	
	private static String getAreaFlagsInfo(FlagsMap areaFlags){
		StringBuilder flagsinfo = new StringBuilder();
		for(String flag : areaFlags.getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(areaFlags.getFlagValue(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag);
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag);
			}
		}
		return flagsinfo.toString();
	}
	
	private static String getAreaPlayerFlagsInfo(Area area, Player sender){
		if(area.isOwner(sender.getUniqueId())){
			return ChatColor.GREEN + "Jsi vlastnik oblasti! Proto mas vsechna opravneni.";
		}
		if(!area.getAreaPlayerFlags().containsPlayer(sender)){
			return ChatColor.RED + "Zadne";
		}
		StringBuilder flagsinfo = new StringBuilder();
		for(String flag : area.getAreaPlayerFlags().getPlayerFlags(sender.getUniqueId()).getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(area.getAreaPlayerFlags().getPlayerFlags(sender.getUniqueId()).getFlagValue(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag);
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag);
			}
		}
		return flagsinfo.toString();
	}
}
