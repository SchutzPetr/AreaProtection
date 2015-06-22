package cz.Sicka.AreaProtection.Compatibility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CompatibilityManager {
	private static Compatibility compability;
	private static String bukkitVersion;
	static {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        bukkitVersion = packageName.substring(packageName.lastIndexOf('.') + 1);

        initcompability();
    }
	
	 private static void initcompability() {
		 try{
			 compability = (Compatibility)Class.forName("cz.Sicka.AreaProtection.Compatibility.Bukkit" + bukkitVersion).newInstance();
			 
			 return;
		}catch(ClassNotFoundException e){
			 e.printStackTrace();
		} catch (InstantiationException e) {
			 e.printStackTrace();
		} catch (IllegalAccessException e) {
			 e.printStackTrace();
		}
	 }
	 
	 public static void sendActionBar(Player p, String msg) {
		 compability.sendActionBar(p, msg);
	 }
	 
	 public static void send(Player player, String message){
		 compability.send(player, message);;
	 }
	
}
