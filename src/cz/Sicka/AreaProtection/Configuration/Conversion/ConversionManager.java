package cz.Sicka.AreaProtection.Configuration.Conversion;

import cz.Sicka.AreaProtection.AreaProtection;

public class ConversionManager {
	private static ConversionResidenceManager res;
	
	public ConversionManager(AreaProtection instance){
		res = new ConversionResidenceManager(instance);
	}
	
	public static ConversionResidenceManager getConversionResidence(){
		return res;
	}

}
