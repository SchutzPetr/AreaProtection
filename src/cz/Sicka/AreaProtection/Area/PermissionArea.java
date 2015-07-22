package cz.Sicka.AreaProtection.Area;

import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class PermissionArea {
	private String worldName;
	private String areaName;
	private FlagsMap worldFlags;
	private AreaType type;
	private String ownerName;
	private String fullName;
	
	public PermissionArea(String areaName, AreaType type, FlagsMap worldFlags, String worldName, String ownerName){
		this.worldName = worldName;
		this.worldFlags = worldFlags;
		this.type = type;
		this.areaName = areaName;
		this.ownerName = ownerName;
		this.fullName = areaName;
	}
	
	public PermissionArea(String areaName, String mainAreaName, AreaType type, FlagsMap worldFlags, String worldName, String ownerName){
		this.worldName = worldName;
		this.worldFlags = worldFlags;
		this.type = type;
		this.areaName = areaName;
		this.ownerName = ownerName;
		this.fullName = mainAreaName + "." + areaName;
	}

	public FlagsMap getWorldFlags() {
		return worldFlags;
	}

	public void setAreaFlags(FlagsMap worldFlags) {
		this.worldFlags = worldFlags;
	}

	public String getWorldName() {
		return worldName;
	}

	public String getAreaName() {
		return areaName;
	}

	public AreaType getType() {
		return type;
	}

	public String getOwnerName() {
		if(this.ownerName == null){
			return "nezname jmeno!";
		}
		return this.ownerName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
}
