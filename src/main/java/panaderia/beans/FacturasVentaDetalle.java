package panaderia.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;


import utils.SQLManager;
import utils.UtilesDAO;
import utils.Utils;
import utils.UtilsFacturacion;

public class FacturasVentaDetalle extends panaderia.beans.entidad.FacturasVentaDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FacturasVentaDetalle(){ super(); }
  										
  
  public static ArrayList getAllFacturasVentaDetalleByFvdFvId( String fvd_fv_id )
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle where fvd_fv_id= " + fvd_fv_id);
    return lista;
  }
  
  public static int editaFacturaVentaDetalle(FacturasVentaDetalle facturaDetalle, Connection conexion ) throws Exception 
  {
	  int resultado = -2;
	  	try{	  		
	    	float importe = UtilsFacturacion.getImporte(Float.parseFloat(facturaDetalle.getFvdPrecioVenta()), 
										    			Float.parseFloat(facturaDetalle.getFvdDescuento()), 
										    			Float.parseFloat(facturaDetalle.getFvdIva()), 
										    			Float.parseFloat(facturaDetalle.getFvdCantidad()));
	    	//Añadimos las lineas de Detalle
	    	facturaDetalle.setFvdImporte(String.valueOf(importe));
	    	//Lo añadimos a la lista
	    	resultado = facturaDetalle.actualiza(conexion);
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }

  public static void recalculaTotalesEImportePendiente( FacturasVenta facturaventa, String totalAntes, String totalDespues, boolean borradoDeDetalle ){
	  //Una vez que tenemos el total de la factura, para ver que hemos incrementado, tomamos el total actual y le restamos
	  //el anterior y obtenemos la cantidad en euros que hemos añadido
	  float tot_antes = Float.parseFloat(EntidadBean.quitar_comas(totalAntes));
	  float tot_despues = Float.parseFloat(EntidadBean.quitar_comas(totalDespues));
	  float impPendiente = Float.parseFloat(EntidadBean.quitar_comas(facturaventa.getFvImportePendiente()));
	  float cantidadModificada = 0;
	  float descuento = Float.parseFloat(EntidadBean.quitar_comas(facturaventa.getFvDescuento()));
	  
	  if( borradoDeDetalle ){
		  cantidadModificada = tot_antes - tot_despues;
		  impPendiente = impPendiente - cantidadModificada;
	  }	 
	  else{
		  cantidadModificada = tot_despues - tot_antes;
		  impPendiente = impPendiente + cantidadModificada;
	  }
		
	  //Actualizamos la facturas con el total despues de insertar el producto y su importe Pendiente
	  //Introducimos el descuento para que sea correcta la cantidad
	  if( descuento != 0 )
		  tot_despues = (float) (tot_despues - (tot_despues * (descuento*0.01)) );
	  
	  facturaventa.setFvTotal( String.valueOf(tot_despues) );
	  facturaventa.setFvImportePendiente( String.valueOf(impPendiente) );
	  //Actualizamos la factura
	  facturaventa.actualiza(); 
	  
  }
  
  public static FacturasVentaDetalle getFacturasVentaDetalleByFvdId(String fvdId)
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_id='"+fvdId+"'");
    return elto;
  }
  
  
  public static ArrayList getAllFacturasDetalleConversorByAlbaranesVentaDetalle(String avId)
  {
	  FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
								"select avd_producto as fvd_producto, " +
								"			 sum(avd_cantidad) as fvd_cantidad, " +
								"			 avd_precio_venta as fvd_precio_venta, " +
								"			 sum(avd_importe) as fvd_importe, " +
								"			 avd_descuento as fvd_descuento, " +
								"			 avd_iva as fvd_iva " +
								"from albaranes_venta " +
								"join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id " +
								"where av_id = " + avId + 
								" group by fvd_producto, avd_descuento, avd_iva, avd_precio_venta");
    return lista;
  }
  
  
  public static ArrayList getAllFacturasDetalleConversorByAlbaranesVentaDetalle(String avId, float proporcion)
  {
	FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
    		"										   select avd_producto as fvd_producto, " +
    		"								    						 CASE WHEN abs(round(avd_cantidad* " + proporcion + ") - (avd_cantidad* " + proporcion + "))= " + proporcion + 
    		"								    						 			THEN sum(FLOOR(avd_cantidad* " + proporcion + " ))  " +
    		"								    									ELSE sum(round(avd_cantidad* " + proporcion + " )) END as fvd_cantidad,  " +
    		"								    						 avd_precio_venta as fvd_precio_venta,  " +
    		"								    						 CASE WHEN abs(round(avd_cantidad* " + proporcion + " ) - (avd_cantidad* " + proporcion + " ))= " + proporcion +
    		"								    						 		    THEN sum(FLOOR(avd_cantidad* " + proporcion + " )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) " +
    		"								    									ELSE sum(round(avd_cantidad* " + proporcion + " )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) END as fvd_importe,  " +
    		"								    						 avd_descuento as fvd_descuento,  " +
    		"								    						 avd_iva as fvd_iva,  " +
    		"								    						 avd_st_id as fvd_st_id,  " +
    		"								    						 'A' as tipo " +
    		"								    			from albaranes_venta  " +
    		"								    			join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
    		"								    			where avd_av_id = " + avId +
    		"												group by avd_producto, avd_descuento, avd_iva, avd_precio_venta " +
    		"								    			union " +
    		"								    		select avd_producto as fvd_producto,  " +
    		"								    						 CASE WHEN abs(round(avd_cantidad* (1 - " + proporcion + ") ) - (avd_cantidad* (1 - " + proporcion + ") ))= (1 - " + proporcion + ")" +
    		"								    						 			THEN sum(CEILING(avd_cantidad* (1 - " + proporcion + ")  ))  " +
    		"								    									ELSE sum(round(avd_cantidad* (1 - " + proporcion + ") )) END as fvd_cantidad,  " +
    		"								    						 avd_precio_venta as fvd_precio_venta,  " +
    		"								    						 CASE WHEN abs(round(avd_cantidad* (1 - " + proporcion + ") ) - (avd_cantidad* (1 - " + proporcion + ") ))= (1 - " + proporcion + ") " +
    		"								    						 			THEN sum(CEILING(avd_cantidad* (1 - " + proporcion + ") )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) " +
    		"								    									ELSE sum(round(avd_cantidad* (1 - " + proporcion + ") )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) END as fvd_importe,   " +
    		"								    						 avd_descuento as fvd_descuento,  " +
    		"								    						 0 as fvd_iva,  " +
    		"								    						 avd_st_id as fvd_st_id,  " +
    		"								    						 'B' as tipo " +
    		"								    			from albaranes_venta  " +
    		"								    			join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
    		"								    			where avd_av_id = " + avId +
    		"												group by avd_producto, avd_descuento, avd_iva, avd_precio_venta " +
    		"								    			order by tipo asc ");
    return lista;
  }
  
  public static ArrayList getAllFacturasDetalleConversorByAlbaranesVentaDetalle(String clId, float proporcion, String dia1, String dia2, String tipoPeriodo)
  {
	FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
    							"select avd_producto as fvd_producto, " +
					    		"				 CASE WHEN abs(round(avd_cantidad*  " + proporcion + ") - (avd_cantidad*  " + proporcion + "))= " + proporcion + " " +
					    		"				 			THEN sum(FLOOR(avd_cantidad*  " + proporcion + " ))  " +
					    		"							ELSE sum(round(avd_cantidad*  " + proporcion + " )) END as fvd_cantidad,  " +
					    		"				 avd_precio_venta as fvd_precio_venta,  " +
					    		"				 CASE WHEN abs(round(avd_cantidad*  " + proporcion + " ) - (avd_cantidad*  " + proporcion + " ))= " + proporcion + " " +
					    		"				 		  THEN sum(FLOOR(avd_cantidad*  " + proporcion + " )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) " +
					    		"							ELSE sum(round(avd_cantidad*  " + proporcion + " )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) END as fvd_importe,  " +
					    		"				 avd_descuento as fvd_descuento,  " +
					    		"				 avd_iva as fvd_iva,  " +
					    		"				 avd_st_id as fvd_st_id,  " +
					    		"				 'A' as tipo " +
					    		"	from albaranes_venta  " +
					    		"	join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
					    		" join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
					    		" join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " +
					    		"	where ifnull(av_cierre,0) != '1' " +
					    		"   and pf_descripcion = '" + tipoPeriodo + "' " +
					    		"	  and av_cl_id = " + clId +
					    		"		and av_fecha BETWEEN str_to_date('" + dia1 + "','%d/%m/%Y') AND str_to_date('" + dia2 + "','%d/%m/%Y') " +
					    		"		group by av_cl_id,avd_producto, avd_descuento, avd_iva, avd_precio_venta " +
					    		"	union " +
					    		"select avd_producto as fvd_producto,  " +
					    		"				 CASE WHEN abs(round(avd_cantidad* (1 -  " + proporcion + ") ) - (avd_cantidad* (1 -  " + proporcion + ") ))= (1 -  " + proporcion + ") " +
					    		"				 			THEN sum(CEILING(avd_cantidad* (1 -  " + proporcion + "))  )  " +
					    		"							ELSE sum(round(avd_cantidad* (1 -  " + proporcion + ")) ) END as fvd_cantidad,  " +
					    		"				 avd_precio_venta as fvd_precio_venta,  " +
					    		"				 CASE WHEN abs(round(avd_cantidad* (1 -  " + proporcion + ") ) - (avd_cantidad* (1 -  " + proporcion + ") ))= (1 -  " + proporcion + ") " +
					    		"				 			THEN sum(CEILING(avd_cantidad* (1 -  " + proporcion + ") )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) " +
					    		"							ELSE sum(round(avd_cantidad* (1 -  " + proporcion + ") )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) END as fvd_importe,   " +
					    		"				 avd_descuento as fvd_descuento,  " +
					    		"				 0 as fvd_iva,  " +
					    		"				 avd_st_id as fvd_st_id,  " +
					    		"				 'B' as tipo " +
					    		"	from albaranes_venta  " +
					    		"	join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
					    		" join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
					    		" join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " +
					    		"	where ifnull(av_cierre,0) != '1' " +
					    		"   and pf_descripcion = '" + tipoPeriodo + "' " +
					    		"	  and av_cl_id = " + clId +
					    		"		and av_fecha BETWEEN str_to_date('" + dia1 + "','%d/%m/%Y') AND str_to_date('" + dia2 + "','%d/%m/%Y') " +
					    		"		group by av_cl_id,avd_producto, avd_descuento, avd_iva, avd_precio_venta " +
					    		"	order by tipo asc ");
    return lista;
  }
  
  public static ArrayList getImporteTotalByAlbaranes(String dia1, String dia2)
  {
	FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
    							"select " +
					    		"				 CASE WHEN abs(round(avd_cantidad* clientes.cl_proporcion_iva) - (avd_cantidad* clientes.cl_proporcion_iva))=  clientes.cl_proporcion_iva " +
					    		"				 		  THEN sum(FLOOR(avd_cantidad* clientes.cl_proporcion_iva)*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) " +
					    		"							ELSE sum(round(avd_cantidad* clientes.cl_proporcion_iva)*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) END as fvd_importe  " +
					    		"	from albaranes_venta  " +
					    		"	join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
					    		" join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
					    		" join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " +
					    		"	where ifnull(av_cierre,0) != '1' " +
					    		"		and av_fecha BETWEEN str_to_date('" + dia1 + "','%d/%m/%Y') AND str_to_date('" + dia2 + "','%d/%m/%Y') " +
					    		"	union " +
					    		"select " +
					    		"				 CASE WHEN abs(round(avd_cantidad* (1 - clientes.cl_proporcion_iva) ) - (avd_cantidad* (1 - clientes.cl_proporcion_iva) ))= (1 - clientes.cl_proporcion_iva) " +
					    		"				 			THEN sum(CEILING(avd_cantidad* (1 - clientes.cl_proporcion_iva) )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) " +
					    		"							ELSE sum(round(avd_cantidad* (1 - clientes.cl_proporcion_iva) )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) END as fvd_importe   " +
					    		"	from albaranes_venta  " +
					    		"	join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id  " +
					    		" join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
					    		" join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " +
					    		"	where ifnull(av_cierre,0) != '1' " +
					    		"		and av_fecha BETWEEN str_to_date('" + dia1 + "','%d/%m/%Y') AND str_to_date('" + dia2 + "','%d/%m/%Y') "
					    		);
    return lista;
  }
  
  public static int eliminaDetalles( String fvdFvId ) 
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  int n = -1;
	  try{
	      PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_venta_detalle WHERE fvd_fv_id=?");
	      sentencia.setString(1, fvdFvId);
	      n = sentencia.executeUpdate();
	      sentencia.close();
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }

  public static FacturasVentaDetalle getFacturasVentaDetalleByFvdLinea(String fvdLinea)
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_linea='"+fvdLinea+"'");
    return elto;
  }
  
  public static String getFacturasVentaDetalleByFvdMaxLinea(String fvdFvId)
  {
    String resultado = EntidadBean.consultarValor("select max(fvd_linea) from facturas_venta_detalle where fvd_fv_id ='"+fvdFvId+"'");
    return resultado;
  }

  public static ArrayList getAllFacturasVentaDetalle()
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle");
    return lista;
  }
  
  /** Dado el número de factura, me devuelve las lineas de detalle de dicha factura */
  public static ArrayList getFacturasVentaDetalleByFvNumeroFactura( String fvNumeroFactura )
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_fv_numero_factura='"+fvNumeroFactura+"'");
    return lista;
  }
  
  /** Dado el número de factura, me devuelve las lineas de detalle de dicha factura */
  public static ArrayList getFacturasVentaDetalleByFvdFvId( String fvId )
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_fv_id='"+fvId+"'");
    return lista;
  }
  
  public static ArrayList getFacturasVentaDetalleByFvdFvIdWithStock(String notdetNotId)
  {
	  FacturasVentaDetalle elto = new FacturasVentaDetalle();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_fv_id='"+notdetNotId+"' ORDER BY FVD_PRODUCTO");
	for(int i =0; i<lista.size(); i++){
		FacturasVentaDetalle detalle = (FacturasVentaDetalle)lista.get(i);
		FacturasCompraDetalle fcd = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStId(detalle.getFvdStId());
		detalle.setLote(fcd.getFcdLote());
	}
    return lista;
  }
  
  /** Facturas por el id del cliente y varios de ids de las facturas (para la agrupacion) */
  public static ArrayList getFacturasVentaDetalleAgrupadasByFvdFvIdsAndFvClId( String fvIds , String fvClId)
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(" select " +
													"			 fvd_producto, " +
													"			 sum(fvd_cantidad) as fvd_cantidad, " + 
													"			 fvd_precio_venta as fvd_precio_venta, " + 
													"			 fvd_iva, " + 
													"			 sum(fvd_importe) as fvd_importe, " + 
													"			 fvd_descuento " +
													" from facturas_venta_detalle " +
													" join facturas_venta ON facturas_venta_detalle.fvd_fv_id = facturas_venta.fv_id " +
													" where fv_cl_id = " + fvClId +
													" and facturas_venta.fv_id in (" + fvIds + ") " +
													" and (facturas_venta.fv_invalida is null or facturas_venta.fv_invalida = 0) " +
													" group by fvd_producto, fvd_iva, fvd_descuento, fvd_precio_venta");
    return lista;
  }
  
  public static ArrayList getFechasAgrupadas( String fvIds , String fvClId)
  {
    ArrayList lista = EntidadBean.consulta("select max(fv_fecha), min(fv_fecha) " +
								    	   "	from facturas_venta " +
								    	   "	where fv_cl_id = " + fvClId +
								    	   " 	and (facturas_venta.fv_invalida is null or facturas_venta.fv_invalida = 0) " +
								    	   "	and fv_id in (" + fvIds + ")");
    return lista;
  }
  
  public static ArrayList getIdsClientesByFvdFvIds( String fvIds)
  {
    ArrayList lista = EntidadBean.consulta("select distinct fv_cl_id " +
								    		"from facturas_venta_detalle " +
								    		"join facturas_venta ON facturas_venta_detalle.fvd_fv_id = facturas_venta.fv_id " +
								    		"where facturas_venta.fv_id in (" + fvIds + ")" +
								    		" and (facturas_venta.fv_invalida is null or facturas_venta.fv_invalida = 0) ");
    return lista;
  }
  
  public static FacturasVentaDetalle getFacturasVentaDetalleByFvdProdId(String fvdProdId)
  {
    FacturasVentaDetalle elto = new FacturasVentaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle WHERE fvd_prod_id='"+fvdProdId+"'");
    return elto;
  }
  
  public static boolean existeProducto( String fvdId, String prodId, String manual )
  {
	  	boolean existe = false;
	  	String nombreProducto = "";
	  	//Si estoy metiendo productos de forma automática
		if( Utils.skipNull(manual).equals("") ){
			ProductosCliente producto = ProductosCliente.getProductosClienteByPclId( prodId );
  			nombreProducto = Productos.getProductosByProdId( producto.getPclProdId() ).getProdNombre();
		}
		else{
			Productos producto = Productos.getProductosByProdId( prodId );
  			nombreProducto = producto.getProdNombre();
		}
		
		FacturasVentaDetalle elto = new FacturasVentaDetalle();
		ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta_detalle where fvd_fv_id='"+fvdId+"' and fvd_producto='"+nombreProducto+"'");
		if( lista.size() > 0 )
			existe = true;
		
	  return existe;
  }
  
  public static int anadeDetalleALista(HttpServletRequest request, String manual, String tipo) throws Exception 
  {
	  	int resultado = -2;
		Connection conexion = null;
	  	try{
	  		conexion = utils.PoolConexiones.conexion();
	  		conexion.setAutoCommit(false);
	  		FacturasVentaDetalle facturasventadetalle = new FacturasVentaDetalle();
	  		String prodId = request.getParameter("prodId");
	  		String fvClId = request.getParameter("fvClId");	  		
	  		Clientes cliente = Clientes.getClientesByClId( fvClId );
	  		
	  		String nombreProducto = "";
	  		float cantidad = 0;
	  		float precio = 0;
	  		float iva = 0;
	  		float descuento = 0;
	  		
	  		String parameterCantidad = request.getParameter("cantidad");
	  		if(parameterCantidad==null)
	  			parameterCantidad = request.getParameter("prodCantidad");
	  		
	  		//Luis Miguel 10/07/14 -> Para que coja el campo nuevo de lote de la tabla stock
	  		String parameterLote = request.getParameter("hinvdLote");	  		
	  		if(parameterLote!=null){
	  			Stock stock = Stock.getStockByStId(parameterLote);
	  			parameterLote =  stock.getStProdLote();
	  		}
	  		
	  		
	  		Productos producto = null;
	  		ProductosCliente productoCliente = null;
	  		if( Utils.skipNull(manual).equals("") ){
	  			 productoCliente = ProductosCliente.getProductosClienteByPclId( prodId );
	  			 producto =  Productos.getProductosByProdId( productoCliente.getPclProdId() );
	  		}else{
	  			 producto = Productos.getProductosByProdId( prodId );
	  		}
	  		
	  		//COMPROBAR QUE SE PUEDE PONER EN LUGAR DE "1" A "0" PORQUE PUEDE HABER 0.250 GRAMOS POR EJEMPLO
	  		String hayStock = Stock.hayStock(producto.getProdId(), "1", parameterCantidad, parameterLote);
	  		if(Utils.skipNull(hayStock).equals(""))
	  			throw new Exception("No dispone de stock suficiente para introducir este producto en la factura o dicho producto esta caducado");

	  		//Ahora tomamos todo el stock, y finalizamos de insertar cuando la cantidad sea 0
	  		float cantidadTotal = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad)));
	  		float cantidadParaFinalizar = cantidadTotal;
	  		//Vemos si es Factura de Abono ó Factura normal
	  		int tipoFactura = Integer.parseInt( Utils.skipNullNumero( request.getParameter("fvTipo") ) );
	  			
	  		ArrayList todoStock = Stock.getAllStockNoVacioNoCaducadoByStProdId( producto.getProdId(), parameterLote ); 	
	  		
	  		String lista = FacturasVentaDetalle.getFacturasVentaDetalleByFvdMaxLinea(request.getParameter("fvId"));
	    	int linea = Integer.parseInt(Utils.skipNullNumero(lista));
	  		
	    	//Si es factura de Abono, se recalculan los stock de manera diferente
	    	if( tipoFactura == 2 ){
		  		cantidadParaFinalizar = cantidadParaFinalizar * -1;
		  		
	    		for(int i = 0; i<todoStock.size() && cantidadParaFinalizar>0; i++){
		  			linea++;
		  			Stock stock = (Stock)todoStock.get(i);
		  			float cantidadDeEsteStock = Float.parseFloat(stock.getStCantidadFinal());
		  			//float cantidadDeEsteStock = Float.parseFloat(stock.getStCantidadFinal());
		  			//SI SE HA INTRODUCIDO EN LA FACTURA DE ABONO UNA CANTIDAD DE ABONO SUPERIOR AL ST_SALIDAS. NO SE PUEDE ABONAR MAS DE LO QUE SE HA
					//VENDIDO DE DICHO PRODUCTO-LOTE
		  			if( cantidadParaFinalizar >  Float.parseFloat(stock.getStSalidas()) ){
		  				return -3;
		  			}
		  			//Luis Miguel -> 22/07/14
		  			else{
		  				cantidad = cantidadParaFinalizar;
		  				stock.setStCantidadFinal(Float.toString(cantidadDeEsteStock + cantidadParaFinalizar));
		  				float cantidadSalidas = Float.parseFloat(stock.getStSalidas()) - cantidad;
		  				stock.setStSalidas(Float.toString(cantidadSalidas));
		  				stock.actualiza(conexion);
		  				cantidadParaFinalizar = 0;
		  			}
		  			/*float cantidadDeEsteStock = Float.parseFloat(stock.getStCantidadFinal());
		  			if(cantidadParaFinalizar>cantidadDeEsteStock){
		  				cantidad = cantidadDeEsteStock;
		  				cantidadParaFinalizar = cantidadParaFinalizar + cantidadDeEsteStock;
		  				stock.setStCantidadFinal("0");
		  				float cantidadSalidas = Float.parseFloat(stock.getStSalidas()) - cantidad;
		  				stock.setStSalidas(Float.toString(cantidadSalidas));
		  				stock.actualiza(conexion);
		  			}else{
		  				cantidad = cantidadParaFinalizar;
		  				stock.setStCantidadFinal(Float.toString(cantidadDeEsteStock + cantidadParaFinalizar));
		  				float cantidadSalidas = Float.parseFloat(stock.getStSalidas()) - cantidad;
		  				stock.setStSalidas(Float.toString(cantidadSalidas));
		  				stock.actualiza(conexion);
		  				cantidadParaFinalizar = 0;
		  			}*/
		  		
			  		//Si estoy metiendo productos de forma AUTOMATICA
			  		if( Utils.skipNull(manual).equals("") ){
			  			nombreProducto = producto.getProdNombre();
			  			//cantidad = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad)));
			  			if(tipo.equals("A"))
			  				iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
			  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
			  			precio = Float.parseFloat(Utils.skipNullNumero( productoCliente.getPclPrecio()));
			  		}
			  		//Si estoy metiendo productos de forma MANUAL
			  		else{
			  			//cantidad = Float.parseFloat(quitar_comas(Utils.skipNullNumero( parameterCantidad)));	    	
			  			if(tipo.equals("A"))
			  				iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
			  			precio = Float.parseFloat(quitar_comas(Utils.skipNullNumero(request.getParameter("prodPrecio"))));
			  			nombreProducto = producto.getProdNombre();
			  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
			  		}
		  		
			  		//Convertimos la cantidad en negativo para poder hacer correctamente los importes
			  		cantidad = cantidad * -1;
			  		float importeNeto = UtilsFacturacion.getImporte( precio, descuento, iva, cantidad );
			    	//Añadimos las lineas de Detalle
			    	facturasventadetalle.setFvdProducto( nombreProducto );	    	
			    	facturasventadetalle.setFvdCantidad( String.valueOf(cantidad) );
			    	facturasventadetalle.setFvdPrecioVenta( String.valueOf(precio) );
			    	facturasventadetalle.setFvdDescuento( String.valueOf(descuento) );
			    	facturasventadetalle.setFvdImporte( String.valueOf(importeNeto) );
			    	facturasventadetalle.setFvdIva( String.valueOf( iva ) );
			    	facturasventadetalle.setFvdFvId(request.getParameter("fvId"));
			    	//Lo añadimos a la lista
			    	facturasventadetalle.setFvdLinea(Integer.toString(linea));
			    	facturasventadetalle.setFvdStId(stock.getStId());
			    	//Luis Miguel - lote en facturasVentaDetalle
			    	facturasventadetalle.setFvdLote( stock.getStProdLote() );
			    	//Lo añadimos a la lista
			    	resultado = facturasventadetalle.inserta(conexion);
		    	
		  		}
	    	    	
	  		}
	    	//Si es Factura Normal
	    	else{
	    		
	    		for(int i = 0; i<todoStock.size() && cantidadParaFinalizar>0; i++){
		  			linea++;
		  			Stock stock = (Stock)todoStock.get(i);
		  			float cantidadDeEsteStock = Float.parseFloat(stock.getStCantidadFinal());
		  			if(cantidadParaFinalizar>cantidadDeEsteStock){
		  				cantidad = cantidadDeEsteStock;
		  				cantidadParaFinalizar = cantidadParaFinalizar - cantidadDeEsteStock;
		  				stock.setStCantidadFinal("0");
		  				float cantidadSalidas = Float.parseFloat(stock.getStSalidas()) + cantidad;
		  				stock.setStSalidas(Float.toString(cantidadSalidas));
		  				stock.actualiza(conexion);
		  			}else{
		  				cantidad = cantidadParaFinalizar;
		  				stock.setStCantidadFinal(Float.toString(cantidadDeEsteStock - cantidadParaFinalizar));
		  				float cantidadSalidas = Float.parseFloat(stock.getStSalidas()) + cantidad;
		  				stock.setStSalidas(Float.toString(cantidadSalidas));
		  				stock.actualiza(conexion);
		  				cantidadParaFinalizar = 0;
		  			}
		  		
			  		//Si estoy metiendo productos de forma AUTOMATICA
			  		if( Utils.skipNull(manual).equals("") ){
			  			nombreProducto = producto.getProdNombre();
			  			//cantidad = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad)));
			  			if(tipo.equals("A"))
			  				iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
			  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
			  			precio = Float.parseFloat(Utils.skipNullNumero( productoCliente.getPclPrecio()));
			  		}
			  		//Si estoy metiendo productos de forma MANUAL
			  		else{
			  			//cantidad = Float.parseFloat(quitar_comas(Utils.skipNullNumero( parameterCantidad)));	    	
			  			if(tipo.equals("A"))
			  				iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
			  			precio = Float.parseFloat(quitar_comas(Utils.skipNullNumero(request.getParameter("prodPrecio"))));
			  			nombreProducto = producto.getProdNombre();
			  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
			  		}
		  		
			  		float importeNeto = UtilsFacturacion.getImporte( precio, descuento, iva, cantidad );
			    	//Añadimos las lineas de Detalle
			    	facturasventadetalle.setFvdProducto( nombreProducto );	    	
			    	facturasventadetalle.setFvdCantidad( String.valueOf(cantidad) );
			    	facturasventadetalle.setFvdPrecioVenta( String.valueOf(precio) );
			    	facturasventadetalle.setFvdDescuento( String.valueOf(descuento) );
			    	facturasventadetalle.setFvdImporte( String.valueOf(importeNeto) );
			    	facturasventadetalle.setFvdIva( String.valueOf( iva ) );
			    	facturasventadetalle.setFvdFvId(request.getParameter("fvId"));
			    	//Lo añadimos a la lista
			    	facturasventadetalle.setFvdLinea(Integer.toString(linea));
			    	facturasventadetalle.setFvdStId(stock.getStId());
			    	//Luis Miguel - lote en facturasVentaDetalle
			    	facturasventadetalle.setFvdLote( stock.getStProdLote() );
			    	//Lo añadimos a la lista
			    	resultado = facturasventadetalle.inserta(conexion);
		    	
		  		}
	    	}
	    	
	  		
	  		conexion.commit();
	  	}catch(Exception e){
	  		conexion.rollback();
	  		throw e;
	  	}finally{
		    conexion.rollback();
		    utils.PoolConexiones.cerrarConexion(conexion);
	  	}
    	return resultado;
  }

  public static ArrayList eliminaDetalleDeLista( ArrayList detalles, String[] chkValues ) throws Exception 
  {	
	  	//Comprobamos si la lista de detalles viene vacia, por si las moscas para evitar errores
	  	if( !detalles.isEmpty() ){
			for(int i=0; i<chkValues.length; i++){
				//Eliminamos los marcados de nuestra lista de detalles
				//FacturasVentaDetalle detalle = new FacturasVentaDetalle();
				//detalle = (FacturasVentaDetalle) detalles.get( Integer.parseInt(chkValues[i]) - i );
				//detalles.remove( detalles.get( Integer.parseInt(chkValues[i]) - i ) );	
				detalles.remove( Integer.parseInt(chkValues[i]) - i );	
			}
	  	}
	    return detalles;
  }
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FacturasVentaDetalle facturasventadetalle = new FacturasVentaDetalle();
  	try{
    	facturasventadetalle.setFvdCantidad(rs.getString(FVDCANTIDAD));
    	facturasventadetalle.setFvdFvId(rs.getString(FVDFVID));
    	facturasventadetalle.setFvdId(rs.getString(FVDID));
    	facturasventadetalle.setFvdImporte(rs.getString(FVDIMPORTE));
    	facturasventadetalle.setFvdIva(rs.getString(FVDIVA));
    	facturasventadetalle.setFvdLinea(rs.getString(FVDLINEA));
    	facturasventadetalle.setFvdPrecioVenta(rs.getString(FVDPRECIOVENTA));
    	facturasventadetalle.setFvdProducto(rs.getString(FVDPRODUCTO));
    	facturasventadetalle.setFvdStId(rs.getString(FVDSTID));
    	
//    	Parte de las tablas relacionadas
    	String[] prodDatosRelacionados = new String[6];
    	prodDatosRelacionados[0] = rs.getString("prod_referencia");
    	prodDatosRelacionados[1] = rs.getString("prod_nombre");
    	prodDatosRelacionados[2] = rs.getString("prod_precio_general");
    	prodDatosRelacionados[3] = rs.getString("prod_peso");
    	prodDatosRelacionados[4] = rs.getString("prod_descuento");
    	prodDatosRelacionados[5] = rs.getString("prod_iva");
    	facturasventadetalle.setProdDatosRelacionados( prodDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return facturasventadetalle;
  }

  public static Object[] getFacturasVentaDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FacturasVentaDetalle facturasventadetalle = new FacturasVentaDetalle();
	String sql = "SELECT fvd.*,"+ 
					"   prod.prod_referencia,"+
					"   prod.prod_nombre,"+
					"   prod.prod_precio_general,"+
					"   prod.prod_peso,"+
					"   prod.prod_descuento,"+
					"   prod.prod_iva,"+
					"   prod.prod_referencia"+
				" FROM facturas_venta_detalle fvd"+
				" JOIN productos prod ON prod.prod_id=fvd.fvd_prod_id WHERE 1 = 1";
  	try{
		FacturasVentaDetalle facturasventadetalleFiltro = new FacturasVentaDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FFACTURASVENTADETALLE")!=null){
			facturasventadetalleFiltro = (FacturasVentaDetalle)listaPaginada.getRequest().getSession().getAttribute("FFACTURASVENTADETALLE");
		}
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdCantidad()))
    		sqlAñadido += "AND " + FVDCANTIDAD + " = '" + facturasventadetalleFiltro.getFvdCantidad() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdFvId()))
    		sqlAñadido += "AND " + FVDFVID + " = '" + facturasventadetalleFiltro.getFvdFvId() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdId()))
    		sqlAñadido += "AND " + FVDID + " = '" + facturasventadetalleFiltro.getFvdId() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdImporte()))
    		sqlAñadido += "AND " + FVDIMPORTE + " = '" + facturasventadetalleFiltro.getFvdImporte() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdIva()))
    		sqlAñadido += "AND " + FVDIVA + " = '" + facturasventadetalleFiltro.getFvdIva() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdLinea()))
    		sqlAñadido += "AND " + FVDLINEA + " = '" + facturasventadetalleFiltro.getFvdLinea() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdPrecioVenta()))
    		sqlAñadido += "AND " + FVDPRECIOVENTA + " = '" + facturasventadetalleFiltro.getFvdPrecioVenta() + "'";
    	if(!Utils.empty(facturasventadetalleFiltro.getFvdProducto()))
    		sqlAñadido += "AND UPPER(" + FVDPRODUCTO + ") like UPPER('%" + facturasventadetalleFiltro.getFvdProducto() + "%')";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, facturasventadetalle);
  }
}
