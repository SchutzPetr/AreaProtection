package cz.Sicka.AreaProtection.HookedPlugins.Plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHookedPlugin {
	private static Permission permission = null;
	private static Economy econ = null;
	private static Chat chat = null;
	
	private static boolean economyFound;
	private static boolean permissionFound;
	private static boolean chatFound;
	
	private static boolean vaultboolean;
	
	public VaultHookedPlugin(){
		CheckVault();
	}
	
	private static void CheckVault(){
		final PluginManager pm = Bukkit.getServer().getPluginManager();
		Plugin vault = pm.getPlugin("Vault");
		
		if(vault != null && vault.isEnabled()){
			economyFound = setupEconomy();
			permissionFound = setupPermissions();
			chatFound = setupChat();
			vaultboolean = true;
			//MessageManager.LogMessage(Level.INFO, "&a" + Lang.VaultFound);
			if(economyFound){
				//MessageManager.LogMessage(Level.INFO, "&a" + Lang.SuccessfulhookedEconomy + "&f: " + econ.getName());
			}else{
				//MessageManager.LogMessage(Level.INFO, "&c" + Lang.EconomyPluginNotFound);
			}
	        if(permissionFound){
	        	if(permission.getName().equals("SuperPerms")){
	        		//MessageManager.LogMessage(Level.INFO, "&c" + Lang.UnsupportedPermissionPlugin);
	        		permissionFound = false;
	        	}else{
	        		//MessageManager.LogMessage(Level.INFO, "&a" + Lang.SuccessfulhookedPermission + "&f: " + permission.getName());
	        	}
			}else{
				//MessageManager.LogMessage(Level.INFO, "&c" + Lang.PermissionPluginNotFound);
			}
	        if(chatFound){
	        	//MessageManager.LogMessage(Level.INFO, "&a" + Lang.SuccessfulhookedChat + "&f: " + chat.getName());
			}else{
				//MessageManager.LogMessage(Level.INFO, "&c" + Lang.ChatPluginNotFound);
			}
		}else{
			//MessageManager.LogMessage(Level.INFO, "&c" + Lang.UnableVault);
			//plugin.setDisableMyServer(true);
		}
	}
	
	
	private static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	private static boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

	private static boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }
	
	
	public boolean isChatFound() {
		return chatFound;
	}

	public boolean isEconomyFound() {
		return economyFound;
	}
	
	public boolean isPermissionFound() {
		return permissionFound;
	}
	
	public Economy getEconomy() {
        return econ;
    }
    
    public Permission getPermission() {
        return permission;
    }
    
    public Chat getChat() {
        return chat;
    }

	public boolean isVault() {
		return vaultboolean;
	}
}
