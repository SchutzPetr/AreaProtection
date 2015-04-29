package cz.Sicka.AreaProtection.Area;

import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class WorldArea{
	private String worldName;
	private String areaName;
	private FlagsMap worldFlags;
	
	public WorldArea(String worldName, String areaName){
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
}
