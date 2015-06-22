package cz.Sicka.AreaProtection.Commands;

import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;
import cz.Sicka.AreaProtection.Compatibility.CompatibilityManager;
import cz.Sicka.AreaProtection.Utils.fanciful.FancyMessage;

public class newTest {

	public static void onTest(Player p) {
		p.sendRawMessage(welcome(p.getName()));
		;
		p.sendRawMessage(advertisement());
		;
		p.sendRawMessage(gui(p.getName(), 10));
		;

		CompatibilityManager.send(p, welcome(p.getName()));
		CompatibilityManager.send(p, advertisement());
		CompatibilityManager.send(p, gui(p.getName(), 10));
	}

	static String welcome(String playername) {
		return new FancyMessage("Hello, ").color(YELLOW).then(playername)
				.color(LIGHT_PURPLE).style(ITALIC, UNDERLINE).then("!")
				.color(YELLOW).style(ITALIC).toJSONString();
	}

	static String advertisement() {
		return new FancyMessage("Visit ")
				.color(GREEN)
				.then("our website")
				.color(YELLOW)
				.style(UNDERLINE)
				.link("http://awesome-server.net")
				.tooltip("AwesomeServer Forums")
				.then(" to win ")
				.color(GREEN)
				.then("big prizes!")
				.color(AQUA)
				.style(BOLD)
				.tooltip(
						"Terms and conditions may apply. Offer not valid in Sweden.")
				.toJSONString();
	}

	static String gui(String playername, int blocksEdited) {
		return new FancyMessage("Player ")
				.color(DARK_RED)
				.then(playername)
				.color(RED)
				.style(ITALIC)
				.then(" changed ")
				.color(DARK_RED)
				.then(Integer.toString(blocksEdited))
				.color(AQUA)
				.then(" blocks. ")
				.color(DARK_RED)
				.then("Roll back?")
				.color(GOLD)
				.style(UNDERLINE)
				.suggest("/rollenbacken " + playername)
				.tooltip("Be careful, this might undo legitimate edits!")
				.then(" ")
				.then("Ban?")
				.color(RED)
				.style(UNDERLINE)
				.suggest("/banhammer " + playername)
				.tooltip(
						"Remember: only ban if you have photographic evidence of grief.")
				.toJSONString();
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
				.suggest("/area create ").toJSONString();
	}

	public static String areaRemoveCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area remove")
				.tooltip(
						AQUA + "" + BOLD + "/area remove [Jmeno Oblasti]\n\n"
								+ GRAY + "Odstrani oblast s danym jmenem. \n\n"
								+ DARK_RED + "Pozor!" + GRAY
								+ "Tato akce je nevratná!\n\n" + GOLD
								+ " Priklad: /area remove testArea")
				.suggest("/area remove ").toJSONString();
	}

	public static String areaInfoCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area info")
				.tooltip(
						AQUA + "" + BOLD + "/area info [Jmeno Oblasti]\n\n"
								+ GRAY + "Zobrazi informace o dane oblasti\n\n"
								+ GOLD + "Priklad: /area info testArea")
				.suggest("/area info").toJSONString();
	}

	public static String getToolsCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "area gettools")
				.tooltip(
						AQUA + "" + BOLD + "/area gettools \n\n" + GRAY
								+ "Obdrzite nastroje pro zpravu oblasti\n\n"
								+ GOLD + "Priklad: /area gettools")
				.suggest("/area gettools").toJSONString();
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
				.suggest("/area pset ").toJSONString();
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
		.suggest("/area set ").toJSONString();
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
						+ "Vybere oblast. x/z = vzdalenost mezi x/z hranici oblasti a støedem."
						+ GRAY
						+ "\ny = výška/hloubka oblasti\n\n"
						+ GOLD + "Priklad: /area select 50")
		.suggest("/area select ").toJSONString();
	}

}
