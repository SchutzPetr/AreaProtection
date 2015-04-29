package cz.Sicka.AreaProtection.Area;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import cz.Sicka.AreaProtection.AreaProtection;
import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Flags.FlagManager;
import cz.Sicka.AreaProtection.Lang.Lang;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class Area {
	private AreaType areaType;
	private PlayerArea playerArea;
	private ServerArea serverArea;
	private WorldArea worldArea;
	
	public Area(PlayerArea pa){
		this.areaType = AreaType.PLAYER_AREA;
		this.playerArea = pa;
		
		this.serverArea = null;
		this.worldArea = null;
	}
	
	public Area(ServerArea sa){
		this.areaType = AreaType.SERVER_AREA;
		this.serverArea = sa;
		
		this.playerArea = null;
		this.worldArea = null;
	}
	
	public Area(WorldArea wa){
		this.areaType = AreaType.WORLD_AREA;
		this.worldArea = wa;
		
		this.serverArea = null;
		this.playerArea = null;
	}
	
	public String getWorldName() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getWorldName();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getWorldName();
		}else{
			return worldArea.getWorldName();
		}
	}

	public String getName() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getAreaName();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getAreaName();
		}else{
			return worldArea.getAreaName();
		}
	}
	
	public AreaPlayerFlags getAreaPlayerFlags() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getAreaPlayerFlags();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getAreaPlayerFlags();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getAreaPlayerFlags");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public void setAreaPlayerFlags(AreaPlayerFlags areaPlayerFlags) {
		if(this.areaType == AreaType.PLAYER_AREA){
			playerArea.setAreaPlayerFlags(areaPlayerFlags);
		}else if(this.areaType == AreaType.SERVER_AREA){
			serverArea.setAreaPlayerFlags(areaPlayerFlags);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: setAreaPlayerFlags");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
	}
	
	public void setAreaFlags(FlagsMap areaFlags) {
		if(this.areaType == AreaType.PLAYER_AREA){
			playerArea.setAreaFlags(areaFlags);
		}else if(this.areaType == AreaType.SERVER_AREA){
			serverArea.setAreaFlags(areaFlags);
		}else{
			worldArea.setAreaFlags(areaFlags);
			return;
		}
	}
	
	public long getCreationDate() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getCreationDate();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getCreationDate();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getCreationDate");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public void setCreationDate(long creationDate) {
		if(this.areaType == AreaType.PLAYER_AREA){
			playerArea.setCreationDate(creationDate);
		}else if(this.areaType == AreaType.SERVER_AREA){
			serverArea.setCreationDate(creationDate);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: setCreationDate");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return;
		}
	}
	
	public FlagsMap getAreaFlags() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getAreaFlags();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getAreaFlags();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getAreaFlags");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public OfflinePlayer getOfflineOwner() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getOfflineOwner();
		}else if(this.areaType == AreaType.SERVER_AREA){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getOfflineOwner");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getOfflineOwner");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public UUID getOwnerUUID() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getOwnerUUID();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return null;
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getOwnerUUID");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public String getOwnerName() {
		if(this.areaType == AreaType.PLAYER_AREA){
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getOwnerName");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getOwnerName();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getOwnerName");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return null;
		}
	}
	
	public int getLowX() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getLowX();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getLowX();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getLowX");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public int getLowY() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getLowY();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getLowY();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getLowY");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public int getLowZ() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getLowZ();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getLowZ();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getLowZ");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public int getHighX() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getHighX();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getHighX();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getHighX");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public boolean containsLocation(int x, int y, int z) {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.containsLocation(x, y, z);
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.containsLocation(x, y, z);
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: containsLocation");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return true;
		}
	}
	
	public boolean containsLocation(Location loc) {
		return containsLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public int getHighY() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getHighY();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getHighY();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getHighY");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public int getHighZ() {
		if(this.areaType == AreaType.PLAYER_AREA){
			return playerArea.getHighZ();
		}else if(this.areaType == AreaType.SERVER_AREA){
			return serverArea.getHighZ();
		}else{
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Class: Area");
			AreaProtection.LogMessage(Level.SEVERE, "&4Error&f: Metod: getHighZ");
			AreaProtection.LogMessage(Level.SEVERE, Lang.Line);
			return 0;
		}
	}
	
	public AreaType getAreaType(){
		return areaType;
	}
	
	public boolean allowAction(Flag flag){
		return allowActionAreaFlag(flag);
	}
	/**
	 * Note: Use only for PLAYER_AREA and SERVER_AREA
	 */
	private boolean allowActionPlayerFlag(UUID uuid, Flag flag){
		if(this.areaType == AreaType.PLAYER_AREA){
			if(playerArea.getAreaPlayerFlags().containsPlayer(uuid)){
				FlagsMap fm = playerArea.getAreaPlayerFlags().getPlayerFlags(uuid);
				if(fm.getFlags().contains(flag.getName())){
					return fm.getFlagValue(flag.getName());
				}else{
					return allowActionInPLAYER_AREA(flag);
				}
			}else{
				return allowActionInPLAYER_AREA(flag);
			}
		}else{
			if(serverArea.getAreaPlayerFlags().containsPlayer(uuid)){
				FlagsMap fm = serverArea.getAreaPlayerFlags().getPlayerFlags(uuid);
				if(fm.getFlags().contains(flag.getName())){
					return fm.getFlagValue(flag.getName());
				}else{
					return allowActionInSERVER_AREA(flag);
				}
			}else{
				return allowActionInSERVER_AREA(flag);
			}
		}
	}
	
	private boolean allowActionAreaFlag(Flag flag){
		if(this.areaType == AreaType.PLAYER_AREA){
			return allowActionInPLAYER_AREA(flag);
		}else if(this.areaType == AreaType.SERVER_AREA){
			return allowActionInSERVER_AREA(flag);
		}else{
			return allowActionInWORLD_AREA(flag);
		}
	}
	
	private boolean allowActionInPLAYER_AREA(Flag flag){
		if(playerArea.getAreaFlags().getFlags().contains(flag.getName())){
			return playerArea.getAreaFlags().getFlagValue(flag.getName());
		}else{
			return FlagManager.getAreaFlag(flag.getName()).getDefaultAreaFlagValue();
		}
	}
	
	private boolean allowActionInSERVER_AREA(Flag flag){
		if(serverArea.getAreaFlags().getFlags().contains(flag.getName())){
			return serverArea.getAreaFlags().getFlagValue(flag.getName());
		}else{
			return FlagManager.getAreaFlag(flag.getName()).getDefaultAreaFlagValue();
		}
	}
	
	private boolean allowActionInWORLD_AREA(Flag flag){
		if(worldArea.getWorldFlags().getFlags().contains(flag.getName())){
			return worldArea.getWorldFlags().getFlagValue(flag.getName());
		}else{
			return true;
		}
	}
	
	public boolean allowAction(UUID player, Flag flag){
		if(areaType == AreaType.PLAYER_AREA || areaType == AreaType.SERVER_AREA){
			if(isPlayerOwner(player)){
				return true;
			}else{
				return allowActionPlayerFlag(player, flag);
			}
		}else{
			return allowActionInWORLD_AREA(flag);
		}
	}
	
	private boolean isPlayerOwner(UUID player){
		if(playerArea.getOwnerUUID().equals(player)){
			return true;
		}else{
			return false;
		}
	}
	
	public enum AreaType {
        WORLD_AREA, PLAYER_AREA, SERVER_AREA;
    }
}
