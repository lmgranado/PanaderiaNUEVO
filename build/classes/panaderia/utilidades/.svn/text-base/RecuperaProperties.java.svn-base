package panaderia.utilidades;

import java.util.*;

public class RecuperaProperties {
    
    private ResourceBundle bundle;
    
    public RecuperaProperties(String ficheroProperties){
        this.bundle = ResourceBundle.getBundle(ficheroProperties);
    }

	  public String getValue(String key) {
	    return bundle.getString(key);
	  }
	
	  public int getIntValue(String key) {
	    return Integer.parseInt(bundle.getString(key));
	  }
    	  
}