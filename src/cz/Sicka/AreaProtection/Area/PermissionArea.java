package cz.Sicka.AreaProtection.Area;

import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class PermissionArea {
	private String worldName;
	private String areaName;
	private FlagsMap worldFlags;
	private AreaType type;
	private String ownerName;
	
	public PermissionArea(String areaName, AreaType type, FlagsMap worldFlags, String worldName, String ownerName){
		this.worldName = worldName;
		this.worldFlags = worldFlags;
		this.type = type;
		this.areaName = areaName;
		this.ownerName = ownerName;
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
		return this.ownerName;
	}
}
