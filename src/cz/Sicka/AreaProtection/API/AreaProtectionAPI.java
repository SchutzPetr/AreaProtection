package cz.Sicka.AreaProtection.API;

import cz.Sicka.AreaProtection.AreaProtection;

public class AreaProtectionAPI {
	
	public static AreaProtectionManager getAreaProtectionManager(){
		return AreaProtection.getInstance().getAreaProtectionManager();
	}
}
