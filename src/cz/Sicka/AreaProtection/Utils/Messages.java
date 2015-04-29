package cz.Sicka.AreaProtection.Utils;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Compatibility.CompatibilityManager;

public class Messages {
	
	public static void sendMessage(Player p, String message){
		p.sendMessage(Replacer.replace(message));
	}
	
	public static void sendActionBar(Player p, String message){
		CompatibilityManager.sendActionBar(p, Replacer.replace(message));
	}
}
