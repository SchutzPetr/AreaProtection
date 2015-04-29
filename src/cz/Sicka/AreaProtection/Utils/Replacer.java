package cz.Sicka.AreaProtection.Utils;

import org.bukkit.ChatColor;

public class Replacer {
	
	public static String replace(String toReplace){
		if(toReplace == null){
			return null;
		}else{
			toReplace = ChatColor.translateAlternateColorCodes("&".charAt(0), toReplace);
			toReplace = toReplace.replace("<New_Line>", ChatColor.RESET + "\n");
			return toReplace;
		}
	}

}
