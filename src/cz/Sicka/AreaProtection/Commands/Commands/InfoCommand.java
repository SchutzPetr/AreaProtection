package cz.Sicka.AreaProtection.Commands.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Utils.Replacer;

public class InfoCommand {
	
	public static boolean onInfoCommand(Area area, Player sender){
		sender.sendMessage(Replacer.replace("&7++++&2--------[ &6" + "Informace o oblasti: " + area.getAreaName() + "&2 ]--------&7++++"));
		sender.sendMessage(Replacer.replace("&6Jmeno oblasti&7: &b" + area.getAreaName()));
		sender.sendMessage(Replacer.replace("&6Vlastnik oblasti&7: &b" + area.getOwner().getPlayer().getDisplayName()));
		sender.sendMessage(Replacer.replace("&6Vlajky oblasti&7: &b" + getAreaFlagsInfo(area)));
		sender.sendMessage(Replacer.replace("&6Tve vlajky&7: &b" + getAreaPlayerFlagsInfo(area, sender)));
		sender.sendMessage(Replacer.replace("&6Vytvoreni oblasti&7: &b" + getDateByCurrentTimeMillis(area.getCreationDate())));
		return false;
	}
	
	private static String getDateByCurrentTimeMillis (long date){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		Date resultdate = new Date(date);    
		return sdf.format(resultdate);
	}
	
	private static String getAreaFlagsInfo(Area area){
		StringBuilder flagsinfo = new StringBuilder();
		for(String flag : area.getAreaFlags().getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(area.getAreaFlags().getFlagValue(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag);
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag);
			}
		}
		return flagsinfo.toString();
	}
	
	private static String getAreaPlayerFlagsInfo(Area area, Player player){
		if(area.isOwner(player.getUniqueId())){
			return ChatColor.GREEN + "Jsi vlastnik oblasti! Proto mas vsechna opravneni.";
		}
		if(!area.getAreaPlayerFlags().containsPlayer(player)){
			return ChatColor.RED + "Zadne";
		}
		StringBuilder flagsinfo = new StringBuilder();
		for(String flag : area.getAreaPlayerFlags().getPlayerFlags(player.getUniqueId()).getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(area.getAreaPlayerFlags().getPlayerFlags(player.getUniqueId()).getFlagValue(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag);
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag);
			}
		}
		return flagsinfo.toString();
	}
}
