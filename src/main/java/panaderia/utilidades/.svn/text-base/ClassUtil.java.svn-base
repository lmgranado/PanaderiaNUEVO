package panaderia.utilidades;

import java.lang.reflect.Method;

	public class ClassUtil {

	    public static String getter(String property) {
	        String resultado = null;
	        if(property!=null && property.length()>2) {
	            resultado = "get"+property.substring(0, 1).toUpperCase()
	                +property.substring(1);
	        }
	        
	        return resultado;
	    }

	    public static String setter(String property) {
	        String resultado = null;
	        if(property!=null && property.length()>2) {
	            resultado = "set"+property.substring(0, 1).toUpperCase()
	                + property.substring(1);
	        }
	        
	        return resultado;        
	    }

	    public static Method buscaMetodo(Class clase, String nombre) {
	        Method metodo = null;
	        
	        Method[] metodos = clase.getMethods();
	        try{
	        	System.out.println(clase.getField("CUSUARIO").get("CUSUARIO"));
	        }catch (Exception e) {
				// TODO: handle exception
			}
	        for(int i = 0; i<metodos.length; i++) {
	            if(metodos[i].getName().equals(nombre)) {
	                metodo = metodos[i];
	                break;
	            }
	        }         
	        
	        return metodo;
	    }
	    
	    public static String buscaConstanteNueva( Class clase, String nombre, String direccion ) {
	    	String constante = "";	    	
	    	String tipoOrden = (direccion.equals("ascending") ?"DESC":"ASC"); 
	        String [] campos = nombre.split(",");
	        
	        for( int i=0; i<campos.length;i++){
	        	if( i == campos.length-1){
	        		constante += buscaConstante( clase, campos[i] ) + " " + tipoOrden ;
	        	}else{
	        		constante += buscaConstante( clase, campos[i] ) + " "  + tipoOrden + ",";
	        	}
	        }
	        
	        constante = "ORDER BY " + constante;
	        return constante;
	    }
	    
	    
	    public static String buscaConstante(Class clase, String nombre) {
	    	String constante = "";
	        try{
	        	constante = (String)clase.getField(nombre.toUpperCase()).get(nombre.toUpperCase()).toString();
	        }catch (Exception e) {
				System.out.println("buscaConstante " + e);
	    	}
	        
	        return constante;
	    }

	    public static Method buscaGetter(Class clase, String propiedad) {
	        return buscaMetodo(clase, getter(propiedad));
	    }

	    public static Method buscaSetter(Class clase, String propiedad) {
	        return buscaMetodo(clase, setter(propiedad));
	    }    
	    
	    public static final java.lang.Object skipNullObj(java.lang.Object obj)
	    {
	        java.lang.Object resultado = obj;
	        if(obj == null)
	            resultado = "";
	        return resultado;
	    }
	    
	    public static boolean comparaObj(java.lang.Object obj1, java.lang.Object obj2)
	    {
	        boolean resultado = true;
	        try
	        {
	            if(obj1 != null && obj2 != null)
	            {
	                java.lang.reflect.Method metodos[] = obj1.getClass().getDeclaredMethods();
	                for(int j = 0; j < metodos.length; j++){
	                    if(!metodos[j].getReturnType().toString().equals("void") && 
	                    	skipNullObj(metodos[j].invoke(obj1, null)).equals(skipNullObj(metodos[j].invoke(obj2, null))))
	                        resultado = false;
	                }

	            } else
	            if(obj1 == null && obj2 != null || obj1 != null && obj2 == null)
	                resultado = false;
	        }
	        catch(java.lang.Exception e)
	        {
	            System.out.println("Error en ComparaObj " + e);
	        }
	        return resultado;
	    }
}
