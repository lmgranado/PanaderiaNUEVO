package panaderia.demonio;

import java.util.ArrayList;

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.utilidades.Utiles;
import utils.Log4j;
import utils.Utils;

public class DemonioFacturacion_buena extends Demonio 
{
	
  public DemonioFacturacion_buena()
  {
    super();
  }
  
  private static DemonioFacturacion instance = null;
  public static DemonioFacturacion getInstance()
  {
    if( instance==null )
    {
      instance = new DemonioFacturacion();
    }
    return instance;
  }
  
  public long getSleepTime()
  {
    //return 2*60*60*1000; // 1 horas
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
	  //Estamos a Lunes
	  if(Utiles.pasaDemonioSemanal(Utils.getFechaActualConHora())){
		  Log4j.info("***** PASANDO DEMONIO:  pasaDemonioSemanal "+Utils.getFechaActualConHoraString()+" horas *****");
		  String[] fechas = Utiles.fechasSemana(Utils.getFechaActualSinHora());
		  tratarFacturas(fechas[0], fechas[1], "SEMANAL");
	  }
	  //Estamos a dia 16
	  if(Utiles.pasaDemonioQuincenal(Utils.getFechaActualConHora())){
		  Log4j.info("***** PASANDO DEMONIO:  pasaDemonioQuincenal "+Utils.getFechaActualConHoraString()+" horas *****");
		  String[] fechas = Utiles.fechasPrimeraQuincena(Utils.getFechaActualSinHora());
		  tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
	  }
	  //Estamos a dia 1
	  if(Utiles.pasaDemonioMensual(Utils.getFechaActualConHora())){
		  Log4j.info("***** PASANDO DEMONIO:  pasaDemonioMensual "+Utils.getFechaActualConHoraString()+" horas *****");
		  String[] fechas = Utiles.fechasSegundaQuincena(Utils.getFechaActualSinHora());
		  tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
		  fechas = null;
		  fechas = Utiles.fechasMensual(Utils.getFechaActualConHora());
		  tratarFacturas(fechas[0], fechas[1], "MENSUAL");
	  }
  }
  
  public static void main (String args[]){
	  //Estamos a Lunes
	      DemonioFacturacion demon = new DemonioFacturacion();
		  String[] fechas = Utiles.fechasSemana(Utils.getFechaActualConHora());
		  demon.tratarFacturas(fechas[0], fechas[1], "SEMANAL");
	  
	  //Estamos a dia 16
	  
		  fechas = Utiles.fechasPrimeraQuincena(Utils.getFechaActualConHora());
		  demon.tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
	  
	  //Estamos a dia 1
	  
		  fechas = Utiles.fechasSegundaQuincena(Utils.getFechaActualConHora());
		  demon.tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
		  fechas = null;
		  fechas = Utiles.fechasMensual(Utils.getFechaActualConHora());
		  demon.tratarFacturas("01/02/2010", "28/02/2010", "MENSUAL");
	  
  }
  

  public void tratarFacturas(String fechaInicio, String fechaFin, String tipoPeriodo)
  {
	  try{
		  //Primero cogemos todos los clientes de todos los albaranes abiertos entre las fechas
		  ArrayList listaIdsClientes = AlbaranesVenta.getAllAlbaranesVentaDistinctClientesBetweenDias(fechaInicio, fechaFin, tipoPeriodo);
		  //Despues recorremos los clientes, cogemos todos sus albaranes y los pasamos a facturas
		  //Obtenemos el cliente para ver que proporcion de IVA tiene puesta.
		  //Si la proporcion de IVA es 0 o 100 creamos una unica nota, si no creamos 2 notas.
		  for(int i=0; i<listaIdsClientes.size(); i++){
			  String clId = ((String[])listaIdsClientes.get(i))[0];
			  Clientes cliente = Clientes.getClientesByClId(clId);
			  int numFacturas = 2;
			  float proporcionIVA = Float.parseFloat(cliente.getClProporcionIva());
			  if(proporcionIVA==0 || proporcionIVA==1){
				  numFacturas = 1;
			  }
		  
		  
		    FacturasVenta factura = new FacturasVenta();
			//creamos la nota
			factura.setFvFecha(fechaFin,Utils.DATE_SHORT);
			factura.setFvClId(cliente.getClId());
			factura.setFvObservaciones("");
			factura.setFvPagada("0");
			factura.setFvCierre("1");
			factura.setFvIva("A");
			factura.setFvTipo("0");
			String numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(factura.getFvIva(), cliente.getClIdEmpresa()) + 1);
			factura.setFvNumeroFactura(numero);
			factura.setFvIdEmpresa(cliente.getClIdEmpresa());
	
			int fvId = 0;
			int fvId2 = 0;
			if(proporcionIVA!=0)
				fvId = factura.inserta();
			//Insertamos un segundo albaran en su caso
			if(numFacturas==2 || proporcionIVA==0){
				factura.setFvIva("B");
				numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura("B", cliente.getClIdEmpresa()) + 1);
				factura.setFvNumeroFactura(numero);
				fvId2 = factura.inserta();
			}
			
			if(fvId<=0 && fvId2<=0){
				throw new Exception("No se ha insertado la factura correctamente. Demonio.");
			}
			
			//Ahora comenzamos el proceso de añadir todos los detalles
			ArrayList listaFacturasVentaDetalle = 
				FacturasVentaDetalle.getAllFacturasDetalleConversorByAlbaranesVentaDetalle(cliente.getClId(), proporcionIVA, fechaInicio, fechaFin, tipoPeriodo);
			
			for(int j=0; j<listaFacturasVentaDetalle.size(); j++){
				FacturasVentaDetalle facturasVentaDetalle = (FacturasVentaDetalle)listaFacturasVentaDetalle.get(j);
				if(facturasVentaDetalle.getTipo().equals("A"))
					facturasVentaDetalle.setFvdFvId(Integer.toString(fvId));
				else
					facturasVentaDetalle.setFvdFvId(Integer.toString(fvId2));
				facturasVentaDetalle.setFvdLinea(Integer.toString(j));
				
				if(Integer.parseInt(facturasVentaDetalle.getFvdCantidad())>0)
					facturasVentaDetalle.inserta();
			}
			
			//Una vez insertados los detalles le ponemos el total a los albaranes
			if(fvId!=0){
				factura = FacturasVenta.getFacturasVentaByFvId(Integer.toString(fvId));
				factura.setFvTotal(FacturasVenta.getImporteTotalByFvId(Integer.toString(fvId)));
				factura.setFvImportePendiente(factura.getFvTotal());
				if(factura.getFvTotal().equals("0"))
					factura.elimina();
				else factura.actualiza();
			}
			if(fvId2!=0){
				factura = FacturasVenta.getFacturasVentaByFvId(Integer.toString(fvId2));
				factura.setFvTotal(FacturasVenta.getImporteTotalByFvId(Integer.toString(fvId2)));
				factura.setFvImportePendiente(factura.getFvTotal());
				if(factura.getFvTotal().equals("0"))
					factura.elimina();
				else factura.actualiza();
			}
			
			//Cerramos los albaranes
			AlbaranesVenta.cierraAlbaranesClienteDias(cliente.getClId(), fechaInicio, fechaFin);
			
		  }
	  
	  }catch (Exception ex){
		  Log4j.error("Error en el demonio", ex);
	  }
  }
  
}