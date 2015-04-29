package cz.Sicka.AreaProtection.Configuration;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;

public class Settings {
	public static Map<Flag, Boolean> defaultAreaFlags;
	
	public Settings(){
		ConfigurationSection flags = AreaProtection.getInstance().getConfig().getConfigurationSection("");
		for (String key : flags.getKeys(false)) {
            Flag flag = FlagManager.getFlag(key);
            if (flag != null) {
                defaultAreaFlags.put(flag, flags.getBoolean(key));
            }
        }
	}
}
