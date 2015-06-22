package cz.Sicka.AreaProtection.Commands.Commands;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Configuration.Conversion.ConversionManager;

public class ConversionCommand {
	
	public static boolean Convert(ConversionType type){
		if(type == ConversionType.RESIDENCE){
			AreaProtection.LogMessage("Starting conversion");
			ConversionManager.getConversionResidence().ConvertResidence();
			AreaProtection.LogMessage("Conversion is complete!");
			AreaProtection.LogMessage("Restart server to load new areas!");
			return false;
		}else{
			return true;
		}
	}
	
	
	
	
	
	public enum ConversionType{
		RESIDENCE;
	}
}
