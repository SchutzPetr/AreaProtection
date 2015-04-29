package cz.Sicka.AreaProtection.Lang;

import cz.Sicka.AreaProtection.AreaProtection;

public class Lang {
	public static String pluginName = "[AreaProtection] ";
	
	public static String ErrorInLoadingSaveFile;
	public static String ErrorInCreatingFile;
	public static String Line;
	
	
	//PlayerMessage
	public static String SuccesCreate;
	public static String CreationUsageCommand;
	public static String FirstPointSelect;
	public static String SecondPointSelect;
	public static String ssssssssssssssssssssssssssssssssssssssssss;
	public static String sssssssssssssssssssssssssssssssssssssssss;
	public static String sssssssssssssssssssssssssssssssssssssss;
	
	public void PlayerLang(){
		SuccesCreate = getMessage("SuccesCreate", "&aArea byla uspesne vytvorena!");
		CreationUsageCommand = getMessage("CreationUsageCommand", "&4Spatne zadany prikaz! &dZadejte &e/area create &7<jmeno>");
		FirstPointSelect = getMessage("FirstPointSelect", "&aArea byla uspesne vytvorena!");
		SecondPointSelect = getMessage("SecondPointSelect", "&aArea byla uspesne vytvorena!");
		
	}
	
	private static String getMessage(String path, String defaultmessage) {
		if(!AreaProtection.getInstance().getLangConfiguration().getConfig().getConfig().isSet(path)){
			AreaProtection.getInstance().getLangConfiguration().getConfig().getConfig().set(path, defaultmessage);
		}
		return AreaProtection.getInstance().getLangConfiguration().getConfig().getConfig().getString(path, defaultmessage);
	}
	
	public Lang(){
		PlayerLang();
		ErrorInLoadingSaveFile = "Error in loading save file for world: ";
		ErrorInCreatingFile = "Error in creating file: ";
		Line = "--- --- --- --- --- --- --- ---";
	}
}