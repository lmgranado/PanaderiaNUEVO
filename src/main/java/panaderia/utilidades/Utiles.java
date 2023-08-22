package panaderia.utilidades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import panaderia.beans.AlbaranesVenta;

import utils.Utils;

public class Utiles {
    public static List convierteListaEnSelect(List eltos,
            String value, String name, boolean ponTodos) throws Exception {

        
        List lista = new ArrayList();

        // Ponemos la opción "Todos/as"
        if(ponTodos) {
            String todosAs = "Seleccione";        
            String[] opcionTodos = new String[2];
            opcionTodos[0] = "0";
            opcionTodos[1] = todosAs;
            lista.add(opcionTodos);
        }

        
        if(eltos != null) {
            // Iteramos en la lista de elementos
            Iterator iterador = eltos.iterator();
            while(iterador.hasNext()) {
                Object elemento = iterador.next();
                
                // Preparamos la opción del select
                String[] option = new String[2];
                option[0] = getProperty(elemento, value).toString();
                option[1] = getProperty(elemento, name).toString();
                
                // La añadimos a la lista
                lista.add(option);
                
            }
        }
        
        
        return lista;
    }
    
    public static Object getProperty(Object objeto, String propiedad)
    throws IllegalAccessException, NoSuchMethodException,
    InvocationTargetException {
	    Object resultado = null;
	    
	    if(propiedad!=null) {
	        // Si la propiedad es en sí "objeto.propiedad"
	        // aplicamos de forma recursiva
	        if(propiedad.indexOf('.')>0) {
	            String base = propiedad.substring(0, propiedad.indexOf('.'));
	            String resto = propiedad.substring(propiedad.indexOf('.') + 1);
	            resultado = getProperty(getProperty(objeto, base), resto);
	        }
	        else {
	            // Si es una propiedad simple la obtenemos
	            Class clase = objeto.getClass();
	            String getter = ClassUtil.getter(propiedad);
	            Method getMethod = clase.getDeclaredMethod(getter, new Class[0]);
	            resultado = getMethod.invoke(objeto, new Object[0]);
	        }
	    }
	    
	    return resultado;
	}
        
    public static String preparaProperties(String cadena){
    	cadena = cadena.toLowerCase();
    	cadena = cadena.replaceAll(" ", "_");
    	cadena = cadena.replaceAll("á", "a");
    	cadena = cadena.replaceAll("é", "e");
    	cadena = cadena.replaceAll("í", "i");
    	cadena = cadena.replaceAll("ó", "o");
    	cadena = cadena.replaceAll("ú", "u");
    	cadena = cadena.replaceAll("\\.", "");
    	return cadena;
    }
    
    public static String returnKeymapByValue(Map mapa, String value){
    	String resultado = "";
    	Set sKeys = mapa.keySet();
    	Vector keys = new Vector(sKeys);
    	Collections.sort(keys);
    	for(Iterator i = keys.iterator(); i.hasNext(); )   {
    	  Object key = i.next();
    	  String valueActual = (String)mapa.get(key);
    	  if(valueActual.equals(value)){
    		  resultado = (String)key;
    		  break;
    	  }
    	} 
    	return resultado;
    }

      public static void main(String argv[]) {
    	  try{
    		  System.out.println("pasaDemonio " + Utils.string2Date("15/03/2010 12:20:22", Utils.DATE_HOUR_SHORT));
    		  System.out.println("pasaDemonio " + pasaDemonioSemanal(Utils.string2Date("15/03/2010 12:20:22", Utils.DATE_HOUR_SHORT)));
    		  String pQuincena[] = fechasSemana(Utils.string2Date("15/03/2010 12:20:22", Utils.DATE_HOUR_SHORT));
    		  System.out.println("[" +pQuincena[0] + " - " + pQuincena[1]+"]");
    		  /*System.out.println("pasaDemonio " + "01/03/2010" + ": " +pasaDemonioMensual(Utils.string2Date("01/03/2010", Utils.DATE_SHORT)));
    		  String mensual[] = fechasMensual(Utils.string2Date("01/01/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +mensual[0] + " - " + mensual[1]+"]");
    		  mensual = fechasMensual(Utils.string2Date("01/02/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +mensual[0] + " - " + mensual[1]+"]");
    		  mensual = fechasMensual(Utils.string2Date("01/03/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +mensual[0] + " - " + mensual[1]+"]");
    		  mensual = fechasMensual(Utils.string2Date("01/04/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +mensual[0] + " - " + mensual[1]+"]");
    		  mensual = fechasMensual(Utils.string2Date("01/05/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +mensual[0] + " - " + mensual[1]+"]");
    		  String sQuincena[] = fechasSegundaQuincena(Utils.string2Date("01/03/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +sQuincena[0] + " - " + sQuincena[1]+"]");
    		  System.out.println("pasaDemonio " + "08/02/2010" + ": " +pasaDemonioSemanal(Utils.string2Date("08/02/2010", Utils.DATE_SHORT)));
    		  String semana[] = fechasSemana(Utils.string2Date("08/02/2010", Utils.DATE_SHORT));
    		  System.out.println("[" +semana[0] + " - " + semana[1]+"]");
    		  System.out.println("Recordar que puede coincidir semanal, quincenal y mensual, por ejemplo el 01/03/2010 que es lunes.");*/
    	  }catch (Exception e) {
			// TODO: handle exception
		}
      }
      
      public static boolean pasaDemonioSemanal(Date fechaActual){
    	  boolean resultado = false;
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Date currentDate1 = calendar1.getTime();
	  	    
	  	    //Comprobamos si es dia lunes y si son mas de las diez de la mñn
	  	    if(Utils.date2String(currentDate1, "EEEEE").equals("lunes") && 
	  	       Integer.parseInt(Utils.date2String(currentDate1, "H"))>=10 ){
	  	    	resultado = true; //semanal
	  	    }
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en pasaDemonio");
    	  }
    	  return resultado;
      }
      
      public static boolean pasaDemonioMensual(Date fechaActual){
    	  boolean resultado = false;
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Date currentDate1 = calendar1.getTime();
	  	    
	  	    //Comprobamos si es dia 1
	  	    if(Utils.date2String(currentDate1, "dd").equals("01") && 
	 	  	   Integer.parseInt(Utils.date2String(currentDate1, "H"))>=10){
	  	    	resultado = true; //mensual
	  	    }
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en pasaDemonio");
    	  }
    	  //0 no pasa
    	  return resultado;
      }
      
      public static boolean pasaDemonioQuincenal(Date fechaActual){
    	  boolean resultado = false;
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Date currentDate1 = calendar1.getTime();
	  	    
	  	    //Comprobamos si es dia 1
	  	    if(Utils.date2String(currentDate1, "dd").equals("01") && 
	 	  	   Integer.parseInt(Utils.date2String(currentDate1, "H"))>=10){
	  	    	resultado = true; //segunda quincena
	  	    }else if(Utils.date2String(currentDate1, "dd").equals("16") && 
	  	  	    Integer.parseInt(Utils.date2String(currentDate1, "H"))>=10){
	  	    	resultado = true; //primera quincena
	  	    }
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en pasaDemonio");
    	  }
    	  return resultado;
      }
      
      public static String[] fechasMensual(Date fechaActual){
    	  String[] fechas = new String[2];
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Calendar calendar2 = new GregorianCalendar(Locale.getDefault());
	  	    calendar2.setTime(fechaActual);
	  	    
	  	    calendar1.add(Calendar.DAY_OF_YEAR, -1);
	  	    int diasMes = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
	  	    calendar1.add(Calendar.DAY_OF_YEAR, -diasMes+1);
	  	    fechas[0] = Utils.date2String(calendar1.getTime(), Utils.DATE_SHORT);
	  	    calendar2.add(Calendar.DAY_OF_YEAR, -1);
	  	    fechas[1] = Utils.date2String(calendar2.getTime(), Utils.DATE_SHORT);
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en fechasMensual");
    	  }
    	  return fechas;
      }
      
      public static String[] fechasPrimeraQuincena(Date fechaActual){
    	  String[] fechas = new String[2];
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Calendar calendar2 = new GregorianCalendar(Locale.getDefault());
	  	    calendar2.setTime(fechaActual);
	  	    
	  	    calendar1.add(Calendar.DAY_OF_YEAR, -15);
	  	    fechas[0] = Utils.date2String(calendar1.getTime(), Utils.DATE_SHORT);
	  	    calendar2.add(Calendar.DAY_OF_YEAR, -1);
	  	    fechas[1] = Utils.date2String(calendar2.getTime(), Utils.DATE_SHORT);
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en fechasPrimeraQuincena");
    	  }
    	  return fechas;
      }
      
      public static String[] fechasSegundaQuincena(Date fechaActual){
    	  String[] fechas = new String[2];
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    Date currentDate1 = calendar1.getTime();
	  	    Calendar calendar2 = new GregorianCalendar(Locale.getDefault());
	  	    calendar2.setTime(fechaActual);
	  	    String primerDiaMes = "";
	  	    
		  	  for(int i=0; i<15; i++){
		    	    if(Utils.date2String(currentDate1, "dd").equals("15")){
		    	    	primerDiaMes = Utils.date2String(currentDate1, Utils.DATE_SHORT);
		    	    	break;
		    	    }else{
		    	    	calendar1.add(Calendar.DAY_OF_YEAR, -1);
		    	    	currentDate1 = calendar1.getTime();
		    	    }
		  	  }
	  	    
	  	    fechas[0] = primerDiaMes;
	  	    calendar2.add(Calendar.DAY_OF_YEAR, -1);
	  	    fechas[1] = Utils.date2String(calendar2.getTime(), Utils.DATE_SHORT);
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en fechasSegundaQuincena");
    	  }
    	  return fechas;
      }
      
      public static String[] fechasSemana(Date fechaActual){
    	  String[] fechas = new String[2];
    	  try{
    		  
	    	Calendar calendar1 = new GregorianCalendar(Locale.getDefault());
	  	    calendar1.setTime(fechaActual);
	  	    calendar1.add(Calendar.DAY_OF_YEAR, -1);
	  	    Date currentDate1 = calendar1.getTime();
	  	    Calendar calendar2 = new GregorianCalendar(Locale.getDefault());
	  	    calendar2.setTime(fechaActual);
	  	    calendar2.add(Calendar.DAY_OF_YEAR, -1);
	  	    Date currentDate2 = calendar2.getTime();
	  	    String primerDiaSemana = "";
	  	    String ultimoDiaSemana = "";
	  	    
	  	  for(int i=0; i<7; i++){
	    	    if(Utils.date2String(currentDate1, "EEEEE").equals("lunes")){
	    	    	primerDiaSemana = Utils.date2String(currentDate1, Utils.DATE_SHORT);
	    	    	break;
	    	    }else{
	    	    	calendar1.add(Calendar.DAY_OF_YEAR, -1);
	    	    	currentDate1 = calendar1.getTime();
	    	    }
  	    }
  	    
  	    for(int i=0; i<7; i++){
	    	    if(Utils.date2String(currentDate2, "EEEEE").equals("domingo")){
	    	    	ultimoDiaSemana = Utils.date2String(currentDate2, Utils.DATE_SHORT);
	    	    	break;
	    	    }else{
	    	    	calendar2.add(Calendar.DAY_OF_YEAR, 1);
	    	    	currentDate2 = calendar2.getTime();
	    	    }
  	    }
	  	    
	  	    fechas[0] = primerDiaSemana;
	  	    fechas[1] = ultimoDiaSemana;
	  	    
    	  }catch (Exception e) {
			System.out.println("Error en fechasSemana");
    	  }
    	  return fechas;
      }
   
   
}
