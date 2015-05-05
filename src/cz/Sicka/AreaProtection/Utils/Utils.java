package cz.Sicka.AreaProtection.Utils;

public class Utils {
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isBoolean(String s) {
	    try { 
	        Boolean.parseBoolean(s);
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
