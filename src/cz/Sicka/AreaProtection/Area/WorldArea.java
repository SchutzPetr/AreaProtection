package cz.Sicka.AreaProtection.Area;

import cz.Sicka.AreaProtection.Flags.Flag;
import cz.Sicka.AreaProtection.Utils.FlagsMap;

public class WorldArea{
	private String worldName;
	private String areaName;
	private String owner;
	private FlagsMap worldFlags;
	
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

	public String getOwner() {
		return owner;
	}
	
	public boolean allowAction(Flag flag){
		if(getWorldFlags().getFlags().contains(flag.getName())){
			return getWorldFlags().getFlagValue(flag.getName());
		}else{
			return true;
		}
	}
}
