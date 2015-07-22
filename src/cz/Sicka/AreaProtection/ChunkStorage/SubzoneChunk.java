package cz.Sicka.AreaProtection.ChunkStorage;

import java.util.ArrayList;
import java.util.List;
import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Subzone;

public class SubzoneChunk {
	private List<String> subzones;
	private Area mainArea;
	
	public SubzoneChunk(Area mainArea){
		this.subzones = new ArrayList<String>();
		this.mainArea = mainArea;
	}
	
	/**
	 * @param subzone the subzone to remove
	 */
	public void removeSubzone(Subzone subzone) {
		this.subzones.remove(subzone.getSubzoneName());
	}
	
	/**
	 * @param subzone the subzone to remove
	 */
	public void removeSubzone(String subzoneName) {
		this.subzones.remove(subzoneName);
	}
	
	/**
	 * @param subzone the subzone to add
	 */
	public void addSubzone(Subzone subzone) {
		this.subzones.add(subzone.getSubzoneName());
	}
	
	/**
	 * @param subzone the subzone to add
	 */
	public void addSubzone(String subzoneName, Subzone subzone) {
		this.subzones.add(subzoneName);
	}
	
	/**
	 * @return the subzone
	 */
	public Subzone getSubzone(String subzoneName){
		return this.mainArea.getSubzoneManager().getSubzone(subzoneName);
	}

	/**
	 * @return the subzones
	 */
	public List<String> getSubzones() {
		return subzones;
	}

	/**
	 * @param subzones the subzones to set
	 */
	public void setSubzones(List<String> subzones) {
		this.subzones = subzones;
	}

	/**
	 * @return the mainArea
	 */
	public Area getMainArea() {
		return mainArea;
	}
}
