package cz.Sicka.AreaProtection.Commands;

import static org.bukkit.ChatColor.*;

import org.bukkit.entity.Player;

import cz.Sicka.AreaProtection.Manager;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Utils.fanciful.FancyMessage;

public class CommandsUtils {
	public static String AreaNotExist = "Oblast neexistuje!";
	public static String AreaExist = "Oblast jiz existuje!";
	public static String AreaNotPermission = "Nemas opravneni ke zmene nastaveni v teto oblasti!";
	public static String NotSelect = "Oblast nebyla vybrana!";
	public static String SuccesCreate = "Oblast uspesne vytvorena!";
	public static String SuccesRemove = "Oblast uspesne odstranena!";
	public static String SuccesFlagSet = "Vlajka byla uspesne nastavena";
	public static String NotValidFlag = "Neznama vlajka!";
	public static String NotValidBoolean = "Neznama logicka hodnota! Pouzij true/false/remove t/f/r";
	public static String SuccesSelected = "Oblast uspesne vybrana!";
	public static String Colision = "Oblast je v kolizi!";
	public static String MustBeInt = "Parametr musi byt cislo!";
	public static String CommandNotFound = "Prikaz nebyl nalezen!";
	public static String DidYouMean = "Nemyslel jsi ";
	
	
	public static boolean isAreaExist(String areaName){
		return Manager.isAreaExist(areaName);
	}
	
	public static boolean isValidFlag(String flagName){
		return FlagManager.getAllFlags().containsKey(flagName.toLowerCase());
	}
	
	public static void sendCommandMessage(Player p, String message, CommandMessageType type){
		p.sendMessage(type.toString() + " " + message);
	}
	
	public enum CommandMessageType {
		Error, Succes, NotPerm;
	}
	
	public static String areaCreateCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area create")
				.tooltip(
						BOLD
								+ ""
								+ AQUA
								+ "/area create <Jmeno Oblasti> \n\n"
								+ GRAY
								+ "Vytvori novou oblast s danym jmenem. \n"
								+ GRAY
								+ "Jmeno musi byt jedinecne a musi obsahovat alfanumericke znaky!\n\n"
								+ GOLD + "Priklad: /area create testArea")
				.suggest("/area create ").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String areaRemoveCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area remove")
				.tooltip(
						AQUA + "" + BOLD + "/area remove [Jmeno Oblasti]\n\n"
								+ GRAY + "Odstrani oblast s danym jmenem. \n\n"
								+ DARK_RED + "Pozor!" + GRAY
								+ "Tato akce je nevratná!\n\n" + GOLD
								+ " Priklad: /area remove testArea")
				.suggest("/area remove ").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String areaInfoCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area info")
				.tooltip(
						AQUA + "" + BOLD + "/area info [Jmeno Oblasti]\n\n"
								+ GRAY + "Zobrazi informace o dane oblasti\n\n"
								+ GOLD + "Priklad: /area info testArea")
				.suggest("/area info").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String getToolsCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area gettools")
				.tooltip(
						AQUA + "" + BOLD + "/area gettools \n\n" + GRAY
								+ "Obdrzite nastroje pro zpravu oblasti\n\n"
								+ GOLD + "Priklad: /area gettools")
				.suggest("/area gettools").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String areaPlayerSetCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area pset")
				.tooltip(
						AQUA
								+ ""
								+ BOLD
								+ "/area pset [Jmeno Oblasti] <Jmeno Hrace> <Flag> true/false/remove \n\n"
								+ GRAY
								+ "Nastavi v oblasti opravneni pro daneho hrace\n"
								+ GREEN
								+ "TRUE "
								+ GRAY
								+ "- Povoli vlajku(Opraveni)\n"
								+ RED
								+ "False "
								+ GRAY
								+ "- Zakaze vlajku(Opraveni)\n"
								+ DARK_RED
								+ "Remove "
								+ GRAY
								+ "- Odstrani dane nastaveni a pouzije vychozi hodnoty \n\n"
								+ GREEN
								+ "TIP:"
								+ GRAY
								+ "V pripade, ze chcete zmenit nastaveni v oblasti ve ktere prave stojite,\n"
								+ GRAY
								+ "nemusite vypisovat nazev oblasti do prikazu.\n\n"
								+ GOLD + "Priklad: /area pset testArea")
				.suggest("/area pset ").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String areaSetCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area set")
		.tooltip(
				AQUA
						+ ""
						+ BOLD
						+ "/area pset [Jmeno Oblasti] <Flag> true/false/remove \n\n"
						+ GRAY
						+ "Nastavi v oblasti opravneni pro vsechny hrace\n"
						+ GREEN
						+ "TRUE "
						+ GRAY
						+ "- Povoli vlajku(Opraveni)\n"
						+ RED
						+ "False "
						+ GRAY
						+ "- Zakaze vlajku(Opraveni)\n"
						+ DARK_RED
						+ "Remove "
						+ GRAY
						+ "- Odstrani dane nastaveni a pouzije vychozi hodnoty \n\n"
						+ GREEN
						+ "TIP:"
						+ GRAY
						+ "V pripade, ze chcete zmenit nastaveni v oblasti ve ktere prave stojite,\n"
						+ GRAY
						+ "nemusite vypisovat nazev oblasti do prikazu.\n\n"
						+ GOLD + "Priklad: /area set testArea")
		.suggest("/area set ").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String areaSelectCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area select")
		.tooltip(
				AQUA
						+ ""
						+ BOLD
						+ "/area select <radius>\n\n"
						+ GRAY
						+ "Vybere vertikální oblast. Radius = vzdalenost mezi hranici oblasti a støedem\n\n"
						+ AQUA
						+ ""
						+ BOLD
						+ "/area select <x> <z>\n\n"
						+ GRAY
						+ "Vybere vertikální oblast. x/z = vzdalenost mezi x/z hranici oblasti a støedem\n\n"
						+ AQUA
						+ ""
						+ BOLD
						+ "/area select <x> <y> <z>\n\n"
						+ GRAY
						+ "Vybere oblast. x/z = vzdalenost mezi x/z hranici oblasti a støedem.\n"
						+ GRAY
						+ "y = výška/hloubka oblasti\n\n"
						+ GOLD + "Priklad: /area select 50")
		.suggest("/area select ").then(GRAY + "  <--- Pro vice info najed mysi!").toJSONString();
	}

}
