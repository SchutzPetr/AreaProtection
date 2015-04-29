package cz.Sicka.AreaProtection.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.Sicka.AreaProtection.Flags.Flag;

public class FlagsMap {
	private Map<String, Boolean> flags = new HashMap<String, Boolean>();
	
	public FlagsMap(Map<String, Boolean> flags){
		this.flags = flags;
	}
	
	public FlagsMap(Flag flag, boolean value){
		this.flags.put(flag.getName(), value);
	}
	
	public FlagsMap(String flag, boolean value){
		this.flags.put(flag, value);
	}
	
	public FlagsMap(){
		
	}
	
	public void addFlag(Flag flag, boolean value){
		addFlag(flag.getName(), value);
	}
	
	public void addFlag(String flag, boolean value){
		if(this.flags.containsKey(flag)){
			this.flags.remove(flag);
		}
		this.flags.put(flag, value);
	}
	
	public ArrayList<String> getFlags(){
		return new ArrayList<String>(this.flags.keySet());
	}
	
	public boolean getFlagValue(String name){
		return this.flags.get(name);
	}
	
	public Map<String, Boolean> getFlagsAndValues(){
		return this.flags;
	}
}
