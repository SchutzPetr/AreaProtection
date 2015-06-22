package cz.Sicka.AreaProtection.Area;

import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class WorldArea{
	private String worldName;
	private String areaName;
	private String owner;
	private FlagsMap worldFlags;
	private AreaType type = AreaType.WORLD_AREA;
	
	public WorldArea(String worldName, String areaName, String owner){
		this.owner = owner;
		this.worldName = worldName;
		this.areaName = areaName;
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

	public String getOwner() {
		return owner;
	}
}
