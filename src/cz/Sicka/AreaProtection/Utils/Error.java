package cz.Sicka.AreaProtection.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cz.Sicka.AreaProtection.AreaProtection;

public class Error {
	List<String> errors = new ArrayList<String>();
	
	public Error(List<String> errors){
		this.errors = errors;
	}
	
	public Error(){
		
	}
	
	public void addErrorMessage(String message){
		this.errors.add(message);
	}
	
	public void print(){
		for(String message : this.errors){
			AreaProtection.LogMessage(Level.SEVERE, message);
		}
		AreaProtection.getInstance().getDebug().log(this.errors);
	}
}
