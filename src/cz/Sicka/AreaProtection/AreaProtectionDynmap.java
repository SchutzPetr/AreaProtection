package cz.Sicka.AreaProtection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import cz.Sicka.AreaProtection.Area.Area;
import cz.Sicka.AreaProtection.Area.Subzone;
import cz.Sicka.AreaProtection.Flags.Flag;

public class AreaProtectionDynmap {
	@SuppressWarnings("unused")
	private AreaProtection plugin;
	private static final String DEF_INFOWINDOW_AREA = "<div class=\"infowindow\"><span style=\"font-weight:bold;font-size:200%\">Informace o oblasti</span><br /><span style=\"font-weight:bold;\">Jmeno oblasti&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%regionname%</span><br /> <span style=\"font-weight:bold;\">Majitel&#720 </span><span style=\"font-weight:bold;color:#FF0066\">%playerowners%</span><br /><br /><span style=\"font-weight:bold;\">Flags&#720<br />------------------</span><br /><span style=\"font-weight:bold;\">%flags%</span></div>";
	private static final String DEF_INFOWINDOW_SUBZONE = "<div class=\"infowindow\"><span style=\"font-weight:bold;font-size:200%\">Informace o subzone</span><br /><span style=\"font-weight:bold;\">Jmeno subzony&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%regionname%</span><br /> <span style=\"font-weight:bold;\">Jmeno hlavni oblasti&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%mainregionname%</span><br /> <span style=\"font-weight:bold;\">Majitel&#720 </span><span style=\"font-weight:bold;color:#FF0066\">%playerowners%</span><br /><br /><span style=\"font-weight:bold;\">Flags&#720<br />------------------</span><br /><span style=\"font-weight:bold;\">%flags%</span></div>";
	private DynmapAPI api;
	private MarkerAPI markerapi;
	private MarkerSet set;
	private String infowindow_area;
	private List<String> flags = new ArrayList<String>();
	private Plugin dynmap;
	private boolean use3d;
	private String infowindow_subzone;
	    
	public AreaProtectionDynmap(AreaProtection instance){
		this.plugin = instance;
		this.infowindow_area = DEF_INFOWINDOW_AREA;
		this.infowindow_subzone = DEF_INFOWINDOW_SUBZONE;
		
		PluginManager pm = instance.getServer().getPluginManager();
        dynmap = pm.getPlugin("dynmap");
        api = (DynmapAPI)dynmap;
		
		markerapi = api.getMarkerAPI();
		if(markerapi == null) {
			AreaProtection.LogMessage(Level.SEVERE, "Error loading dynmap marker API!");
            return;
        }
		set = markerapi.getMarkerSet("residence.markerset");
		if(set == null){
			set = markerapi.createMarkerSet("residence.markerset", "AreaProtection", null, false);
		}else{
			 set.setMarkerSetLabel("AreaProtection");
		}
        if(set == null) {
        	AreaProtection.LogMessage(Level.SEVERE, "Error creating marker set");
            return;
        }
        set.setLayerPriority(10);
        set.setHideByDefault(false);
        showAreas();
	}
	
	public void showAreas(){
		for(Area area : Manager.getAllAreas().values()){
			showArea(area);
		}
	}
	
	public void showArea(Area area){
		String id = area.getAreaName(); 
		String label = area.getAreaName(); 
		String world = area.getWorldName();
		double[] x = new double[4];
        double[] z = new double[4];
        
        String desc = formatInfoWindow(area);
        
        x[0] = area.getLowX(); z[0] = area.getLowZ();
        x[1] = area.getLowX(); z[1] = area.getHighZ()+1.0;
        x[2] = area.getHighX() + 1.0; z[2] = area.getHighZ()+1.0;
        x[3] = area.getHighX() + 1.0; z[3] = area.getLowZ();
        
		AreaMarker m = set.createAreaMarker(id, label, false, world, z, x, false);
		m.setDescription(desc);
		if(use3d) { /* If 3D? */
            m.setRangeY(area.getHighY() + 1.0, area.getLowY());
        }
		AreaProtection.LogMessage("Dynmap load Area: " + area.getAreaName());
		showSubzones(area);
		
	}
	
	public void showSubzones(Area area){
		for(Subzone sub : area.getSubzoneManager().getSubzones()){
			showSubzone(sub);
		}
	}
	
	public void showSubzone(Subzone sub){
		String id = sub.getMainAreaName() + "%" + sub.getSubzoneName(); 
		String label = sub.getSubzoneName(); 
		String world = sub.getWorldName();
		double[] x = new double[4];
        double[] z = new double[4];
        
        String desc = formatInfoWindow(sub);
        
        x[0] = sub.getLowX(); z[0] = sub.getLowZ();
        x[1] = sub.getLowX(); z[1] = sub.getHighZ()+1.0;
        x[2] = sub.getHighX() + 1.0; z[2] = sub.getHighZ()+1.0;
        x[3] = sub.getHighX() + 1.0; z[3] = sub.getLowZ();
		
		AreaMarker m = set.createAreaMarker(id, label, false, world, z, x, false);
		m.setDescription(desc);
		if(use3d) { /* If 3D? */
            m.setRangeY(sub.getHighY() + 1.0, sub.getLowY());
        }
		
		AreaProtection.LogMessage("Dynmap load Subzone: " + sub.getFullName());
	}
	
	private String formatInfoWindow(Subzone sub) {
    	String v = "<div class=\"regioninfo\">"+ infowindow_subzone +"</div>";
        v = v.replace("%mainregionname%", sub.getSubzoneName());
    	v = v.replace("%regionname%", sub.getSubzoneName());
        v = v.replace("%playerowners%", sub.getOwner().getName());
        String m = sub.getEnterMessage();
        v = v.replace("%entermsg%", (m!=null)?m:"" );
        m = sub.getLeaveMessage();
        v = v.replace("%leavemsg%", (m!=null)?m:"");
        Map<Flag, Boolean> p = sub.getAreaFlags().getAreaFlags();
        
        for(Flag flg : p.keySet()){
        	if(p.get(flg)){
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:green\">True</span><br>");
        	}else{
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:red\">False</span><br>");
        	}
        }
        
        v = v.replace("%flags%", Flags(flags));
        flags.clear();
        return v;
    }
	
	private String formatInfoWindow(Area area) {
    	String v = "<div class=\"regioninfo\">"+ infowindow_area +"</div>";
        v = v.replace("%regionname%", area.getAreaName());
        v = v.replace("%playerowners%", area.getOwner().getName());
        String m = area.getEnterMessage();
        v = v.replace("%entermsg%", (m!=null)?m:"" );
        m = area.getLeaveMessage();
        v = v.replace("%leavemsg%", (m!=null)?m:"");
        Map<Flag, Boolean> p = area.getAreaFlags().getAreaFlags();
        
        for(Flag flg : p.keySet()){
        	if(p.get(flg)){
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:green\">True</span><br>");
        	}else{
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:red\">False</span><br>");
        	}
        }
        
        v = v.replace("%flags%", Flags(flags));
        flags.clear();
        return v;
    }
	
	private String Flags(List<String> args) {
    	String s = "";
		if(args == null){
			args = null;
		}else{
			for (int i = 0; i < args.size(); i++) {
				s = s + args.get(i) + " ";
			}
		}
		return s;
	}

}