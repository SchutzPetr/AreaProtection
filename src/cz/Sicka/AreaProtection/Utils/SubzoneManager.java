package cz.Sicka.AreaProtection.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Subzone;

public class SubzoneManager {
	private Map<String, Subzone> subzones;
	private Area mainArea;
	
	public SubzoneManager(Area mainArea){
		this.subzones = new HashMap<String, Subzone>();
		this.mainArea = mainArea;
	}
	
	/**
	 * @param subzone the subzone to remove
	 */
	public void removeSubzone(Subzone subzone) {
		this.subzones.remove(subzone.getSubzoneName().toLowerCase());
	}
	
	/**
	 * @param subzone the subzone to remove
	 */
	public void removeSubzone(String subzoneName) {
		this.subzones.remove(subzoneName.toLowerCase());
	}
	
	/**
	 * @param subzone the subzone to add
	 */
	public void addSubzone(String subzoneName, Subzone subzone) {
		this.subzones.put(subzoneName.toLowerCase(), subzone);
	}

	/**
	 * @return the subzones
	 */
	public Map<String, Subzone> getSubzonesMap() {
		return subzones;
	}
	
	public Collection<Subzone> getSubzones() {
		return subzones.values();
	}

	/**
	 * @param subzones the subzones to set
	 */
	public void setSubzones(Map<String, Subzone> subzones) {
		this.subzones = subzones;
	}
	
	/**
	 * @return the subzone
	 */
	public Subzone getSubzone(String subzoneName){
		return subzones.get(subzoneName.toLowerCase());
	}

	/**
	 * @return the mainArea
	 */
	public Area getMainArea() {
		return mainArea;
	}

	/**
	 * @param mainArea the mainArea to set
	 */
	public void setMainArea(Area mainArea) {
		this.mainArea = mainArea;
	}
	
	public boolean isSubzoneExist(String subzoneName){
		return this.subzones.containsKey(subzoneName.toLowerCase());
	}
}
