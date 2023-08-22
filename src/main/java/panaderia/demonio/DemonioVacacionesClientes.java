package panaderia.demonio;

import java.util.ArrayList;
import panaderia.beans.VacacionesClientes;
import utils.Log4j;
import utils.Utils;

public class DemonioVacacionesClientes extends Demonio 
{
	
  public DemonioVacacionesClientes()
  {
    super();
  }
  
  private static DemonioVacacionesClientes instance = null;
  public static DemonioVacacionesClientes getInstance()
  {
    if( instance==null )
    {
      instance = new DemonioVacacionesClientes();
    }
    return instance;
  }
  
  public long getSleepTime()
  {
    //return 2*60*60*1000; // 1 horas
	  //long tiempoParada = 1*60*60*1000;
	  long tiempoParada = 1*60*60*1000;
	  Log4j.info("***** Tiempo de parada del demonio: "+(tiempoParada / (60*60*1000))+" horas *****");
	  return tiempoParada;
  }
  
  public void stopping()
  {
  }
  
  public void starting()
  {
  }
  
  public void task()
  {
	  //Vacaciones disfrutadas y tenemos que poner activos los clientes
	  String fechaActual = Utils.date2String( Utils.getFechaActualSinHora(), Utils.DATE_MYSQL_SHORT ) ;
	  ArrayList disfrutadas = VacacionesClientes.getAllVacacionesClientesDisfrutadas( fechaActual );
	  VacacionesClientes.actualizaVacacionesADisfrutadas( disfrutadas );
	  
	  //Vacaciones disfrutandolas actualmente y tenemos que poner inactivos los clientes 
	  ArrayList disfrutandolas = VacacionesClientes.getAllVacacionesClientesDisfrutandolas( fechaActual );
	  VacacionesClientes.inactivaClientes( disfrutandolas );
	  
	 
  }
  
  public static void main ( String args[] ){
	  //Vacaciones disfrutadas y tenemos que poner activos los clientes
	  String fechaActual = Utils.date2String( Utils.getFechaActualSinHora(), Utils.DATE_MYSQL_SHORT ) ;
	  ArrayList disfrutadas = VacacionesClientes.getAllVacacionesClientesDisfrutadas( fechaActual );
	  VacacionesClientes.actualizaVacacionesADisfrutadas( disfrutadas );
	  
	  //Vacaciones disfrutandolas actualmente y tenemos que poner inactivos los clientes 
	  ArrayList disfrutandolas = VacacionesClientes.getAllVacacionesClientesDisfrutandolas( fechaActual );
	  VacacionesClientes.inactivaClientes( disfrutandolas );
	  
  }
  

  public void tratarVacacionesClientes()
  {
	  try{
		 
	  }catch (Exception ex){
		  Log4j.error("Error en el demonio", ex);
	  }
  }
  
}