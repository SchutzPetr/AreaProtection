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
		this.flags.put(flag.getName().toLowerCase(), value);
	}
	
	public FlagsMap(String flag, boolean value){
		this.flags.put(flag.toLowerCase(), value);
	}
	
	public FlagsMap(){
		
	}
	
	public void addFlag(Flag flag, boolean value){
		addFlag(flag.getName().toLowerCase(), value);
	}
	
	public void addFlag(String flag, boolean value){
		if(this.flags.containsKey(flag.toLowerCase())){
			this.flags.remove(flag.toLowerCase());
		}
		this.flags.put(flag.toLowerCase(), value);
	}
	
	public void removeFlag(Flag flag){
		removeFlag(flag.getName().toLowerCase());
	}
	
	public void removeFlag(String flag){
		if(this.flags.containsKey(flag.toLowerCase())){
			this.flags.remove(flag.toLowerCase());
		}
	}
	
	public ArrayList<String> getFlags(){
		return new ArrayList<String>(this.flags.keySet());
	}
	
	public boolean getFlagValue(String name){
		return this.flags.get(name.toLowerCase());
	}
	
	public Map<String, Boolean> getFlagsAndValues(){
		return this.flags;
	}
}
