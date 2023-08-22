package panaderia.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;


import utils.SQLManager;
import utils.UtilesDAO;
import utils.Utils;
import utils.UtilsFacturacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class AlbaranesVentaDetalle extends panaderia.beans.entidad.AlbaranesVentaDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public AlbaranesVentaDetalle(){ super(); }

  public static ArrayList getAllAlbaranesDetalleConversorByNotasEntregaDetalle(String notId)
  {
	  AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
								"select notdet_producto as avd_producto, " +
								"			 notdet_cantidad as avd_cantidad, " +
								"			 notdet_precio as avd_precio_venta, " +
								"			 notdet_importe as avd_importe, " +
								"			 notdet_descuento as avd_descuento, " +
								"			 notdet_iva as avd_iva, " +
								"			 not_viaje as avd_viaje, " +
								"			 notdet_st_id as avd_st_id " +
								"from notas_entrega " +
								"join notas_entrega_detalle ON notas_entrega_detalle.notdet_not_id = notas_entrega.not_id " +
								"where not_id = " + notId);
    return lista;
  }
  
  public static ArrayList getAllAlbaranesDetalleConversorByNotasEntregaDetalle(String notId, float proporcion)
  {
	AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
								    		"select notdet_producto as avd_producto, " +
								    		"				 CASE WHEN abs(round(notdet_cantidad*" + proporcion + ") - (notdet_cantidad*" + proporcion + "))=" + proporcion + " " +
								    		"				 			THEN FLOOR(notdet_cantidad*" + proporcion + ")  " +
								    		"							ELSE round(notdet_cantidad*" + proporcion + ") END as avd_cantidad,  " +
								    		"				 notdet_precio as avd_precio_venta,  " +
								    		"				 CASE WHEN abs(round(notdet_cantidad*" + proporcion + ") - (notdet_cantidad*" + proporcion + "))=" + proporcion + " " +
								    		"				 		    THEN FLOOR(notdet_cantidad*" + proporcion + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * notdet_iva)) " +
								    		"							ELSE round(notdet_cantidad*" + proporcion + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * notdet_iva)) END as avd_importe,  " +
								    		"				 notdet_descuento as avd_descuento,  " +
								    		"				 notdet_iva as avd_iva,  " +
								    		"				 not_viaje as avd_viaje , " +
								    		"			 	 notdet_st_id as avd_st_id, " +
								    		"				 'A' as tipo " +
								    		"	from notas_entrega  " +
								    		"	join notas_entrega_detalle ON notas_entrega_detalle.notdet_not_id = notas_entrega.not_id  " +
								    		"	where not_id =  " + notId + 
								    		"	union " +
								    		"select notdet_producto as avd_producto,  " +
								    		"				 CASE WHEN abs(round(notdet_cantidad*" + (1 - proporcion) + ") - (notdet_cantidad*" + (1 - proporcion) + "))=" + (1 - proporcion) + " " +
								    		"				 			THEN CEILING(notdet_cantidad*" + (1 - proporcion) + ")  " +
								    		"							ELSE round(notdet_cantidad*" + (1 - proporcion) + ") END as avd_cantidad,  " +
								    		"				 notdet_precio as avd_precio_venta,  " +
								    		"				 CASE WHEN abs(round(notdet_cantidad*" + (1 - proporcion) + ") - (notdet_cantidad*" + (1 - proporcion) + "))=" + (1 - proporcion) + " " +
								    		"				 			THEN CEILING(notdet_cantidad*" + (1 - proporcion) + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * 0)) " +
								    		"							ELSE round(notdet_cantidad*" + (1 - proporcion) + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * 0)) END as avd_importe,   " +
								    		"				 notdet_descuento as avd_descuento,  " +
								    		"				 0 as avd_iva,  " +
								    		"				 not_viaje as avd_viaje , " +
								    		"			 	 notdet_st_id as avd_st_id, " +
								    		"				 'B' as tipo " +
								    		"	from notas_entrega  " +
								    		"	join notas_entrega_detalle ON notas_entrega_detalle.notdet_not_id = notas_entrega.not_id  " +
								    		"	where not_id = " + notId +
								    		"	order by tipo asc ");
    return lista;
  }
  
  public static ArrayList getAllAlbaranesDetalleConversorByNotasEntregaDetalleCierraDia(String avdClId, String dia)
  {
	  AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
	  ArrayList lista = elto.consultaAVectorReflexiva(
									"select not_fecha, " + 
									"		not_cl_id, " +
									"		notdet_producto as avd_producto, " + 
									"		not_viaje as avd_viaje, " +
									"		sum(notdet_cantidad) as avd_cantidad, " +
									"		notdet_precio as avd_precio_venta, " + 
									"		sum(notdet_importe) as avd_importe, " + 
									"		notdet_descuento as avd_descuento, " + 
									"		notdet_iva as avd_iva, " +
									"		notdet_st_id as avd_st_id " +
									"from notas_entrega " + 
									"join notas_entrega_detalle on notas_entrega_detalle.notdet_not_id = notas_entrega.not_id " + 
									"where ifnull(not_cierre,0) != '1' " +
									"and not_cl_id = " + avdClId + " " +
									"and not_fecha = str_to_date('" + dia + "','%d/%m/%Y') " +
									"group by not_fecha,not_cl_id,notdet_producto, not_viaje, notdet_descuento, notdet_iva, notdet_precio order by not_viaje");

    return lista;
  }
  
  public static ArrayList getAllAlbaranesDetalleConversorByNotasEntregaDetalleCierraDia_old(String avdClId, String dia, float proporcion)
  {
	  AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
	  ArrayList lista = elto.consultaAVectorReflexiva(
								    "select not_fecha,not_cl_id,notdet_producto as avd_producto, " +
						    		"				 CASE WHEN abs(round(notdet_cantidad*" + proporcion + ") - (notdet_cantidad*" + proporcion + "))=" + proporcion + " " +
						    		"				 			THEN sum(FLOOR(notdet_cantidad*" + proporcion + "))  " +
						    		"							ELSE sum(round(notdet_cantidad*" + proporcion + ")) END as avd_cantidad,  " +
						    		"				 notdet_precio as avd_precio_venta,  " +
						    		"				 CASE WHEN abs(round(notdet_cantidad*" + proporcion + ") - (notdet_cantidad*" + proporcion + "))=" + proporcion + " " +
						    		"				 		    THEN sum(FLOOR(notdet_cantidad*" + proporcion + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * notdet_iva))) " +
						    		"							ELSE sum(round(notdet_cantidad*" + proporcion + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * notdet_iva))) END as avd_importe,  " +
						    		"				 notdet_descuento as avd_descuento,  " +
						    		"				 notdet_iva as avd_iva,  " +
						    		"				 not_viaje as avd_viaje , " +
						    		"			 	 notdet_st_id as avd_st_id, " +
						    		"				 'A' as tipo " +
						    		"	from notas_entrega  " +
						    		"	join notas_entrega_detalle ON notas_entrega_detalle.notdet_not_id = notas_entrega.not_id  " +
						    		"	where ifnull(not_cierre,0) != '1' " +
									"	and not_cl_id = " + avdClId + " " +
									"	and not_fecha = str_to_date('" + dia + "','%d/%m/%Y') " +
									"	group by not_fecha,not_cl_id,notdet_producto, not_viaje, notdet_descuento, notdet_iva, notdet_precio" +
						    		"	union " +
						    		"select not_fecha,not_cl_id,notdet_producto as avd_producto,  " +
						    		"				 CASE WHEN abs(round(notdet_cantidad*" + (1 - proporcion) + ") - (notdet_cantidad*" + (1 - proporcion) + "))=" + (1 - proporcion) + " " +
						    		"				 			THEN sum(CEILING(notdet_cantidad*" + (1 - proporcion) + "))  " +
						    		"							ELSE sum(round(notdet_cantidad*" + (1 - proporcion) + ")) END as avd_cantidad,  " +
						    		"				 notdet_precio as avd_precio_venta,  " +
						    		"				 CASE WHEN abs(round(notdet_cantidad*" + (1 - proporcion) + ") - (notdet_cantidad*" + (1 - proporcion) + "))=" + (1 - proporcion) + " " +
						    		"				 			THEN sum(CEILING(notdet_cantidad*" + (1 - proporcion) + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * 0))) " +
						    		"							ELSE sum(round(notdet_cantidad*" + (1 - proporcion) + ")*((notdet_precio - (notdet_precio * notdet_descuento)) + (notdet_precio * 0))) END as avd_importe,   " +
						    		"				 notdet_descuento as avd_descuento,  " +
						    		"				 0 as avd_iva,  " +
						    		"				 not_viaje as avd_viaje , " +
						    		"			 	 notdet_st_id as avd_st_id, " +
						    		"				 'B' as tipo " +
						    		"	from notas_entrega  " +
						    		"	join notas_entrega_detalle ON notas_entrega_detalle.notdet_not_id = notas_entrega.not_id  " +
						    		"	where ifnull(not_cierre,0) != '1' " +
									"	and not_cl_id = " + avdClId + " " +
									"	and not_fecha = str_to_date('" + dia + "','%d/%m/%Y') " +
									"	group by not_fecha,not_cl_id,notdet_producto, not_viaje, notdet_descuento, notdet_iva, notdet_precio" +
						    		"	order by tipo asc ");

    return lista;
  }
  
  
  public static ArrayList getAlbaranesVentaDetalleAgrupadosByAvdAvId(String AvId)
  {
    ArrayList lista = EntidadBean.consulta(
					    		"select avd_producto as fvd_producto, " +
								"(select ifnull(sum(avd_cantidad),0) from albaranes_venta_detalle avd " +
								" 	where albaranes_venta_detalle.avd_producto = avd.avd_producto " +
								"	and albaranes_venta_detalle.avd_av_id = avd.avd_av_id " +
								"	and avd.avd_viaje = 1" +
								"   and avd.avd_precio_venta = albaranes_venta_detalle.avd_precio_venta) as fvd_cantidad1, " +
								"(select ifnull(sum(avd_cantidad),0) from albaranes_venta_detalle avd " +
								" 	where albaranes_venta_detalle.avd_producto = avd.avd_producto " +
								"	and albaranes_venta_detalle.avd_av_id = avd.avd_av_id " +
								"	and avd.avd_viaje = 2" +
								"   and avd.avd_precio_venta = albaranes_venta_detalle.avd_precio_venta) as fvd_cantidad2, " +
								"(select ifnull(sum(avd_cantidad),0) from albaranes_venta_detalle avd " +
								" 	where albaranes_venta_detalle.avd_producto = avd.avd_producto " +
								"	and albaranes_venta_detalle.avd_av_id = avd.avd_av_id " +
								"	and avd.avd_viaje = 3" +
								"   and avd.avd_precio_venta = albaranes_venta_detalle.avd_precio_venta) as fvd_cantidad3, " +
								"(select ifnull(sum(avd_cantidad),0) from albaranes_venta_detalle avd " +
								" 	where albaranes_venta_detalle.avd_producto = avd.avd_producto " +
								"	and albaranes_venta_detalle.avd_av_id = avd.avd_av_id " +
								"	and avd.avd_viaje = 4" +
								"   and avd.avd_precio_venta = albaranes_venta_detalle.avd_precio_venta) as fvd_cantidad4, " +
								"(select ifnull(sum(avd_cantidad),0) from albaranes_venta_detalle avd " +
								" 	where albaranes_venta_detalle.avd_producto = avd.avd_producto " +
								"	and albaranes_venta_detalle.avd_av_id = avd.avd_av_id " +
								"	and avd.avd_viaje = 5" +
								"   and avd.avd_precio_venta = albaranes_venta_detalle.avd_precio_venta) as fvd_cantidad5, " +
								"								 sum(avd_cantidad), " +
								"								 avd_precio_venta as fvd_precio_venta,  " +
								"								 sum(avd_importe) as fvd_importe,  " +
								"								 avd_descuento as fvd_descuento, " + 
								"								 avd_iva as fvd_iva,  " +
								"								 avd_st_id as fvd_st_iva   " +
								"					from albaranes_venta  " +
								"					join albaranes_venta_detalle on albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id " + 
								"					where av_id = " + AvId +
								"					group by fvd_producto, avd_descuento, avd_iva, avd_precio_venta ");

    return lista;
  }
  
  /** Dado el número de factura, me devuelve las lineas de detalle de dicha factura */
  public static ArrayList getAlbaranesVentaDetalleByAvdAvId( String AvId )
  {
	  AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle WHERE avd_av_id='"+AvId+"' ORDER BY AVD_PRODUCTO, AVD_VIAJE");
    return lista;
  }
  
  public static ArrayList getAlbaranesVentaDetalleByAvdAvIdWithStock(String notdetNotId)
  {
	  AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle WHERE avd_av_id='"+notdetNotId+"' ORDER BY AVD_PRODUCTO, AVD_VIAJE");
	for(int i =0; i<lista.size(); i++){
		AlbaranesVentaDetalle detalle = (AlbaranesVentaDetalle)lista.get(i);
		FacturasCompraDetalle fcd = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStId(detalle.getAvdStId());
		detalle.setLote(fcd.getFcdLote());
	}
    return lista;
  }
  
  
  public static int editaAlbaranVentaDetalle(AlbaranesVentaDetalle albaranDetalle, Connection conexion ) throws Exception 
  {
	  int resultado = -2;
	  	try{	  		
	    	float importe = UtilsFacturacion.getImporte(Float.parseFloat(albaranDetalle.getAvdPrecioVenta()), 
										    			Float.parseFloat(albaranDetalle.getAvdDescuento()), 
										    			0, 
										    			Float.parseFloat(albaranDetalle.getAvdCantidad()));
	    	//Añadimos las lineas de Detalle
	    	albaranDetalle.setAvdImporte(String.valueOf(importe));
	    	//Lo añadimos a la lista
	    	resultado = albaranDetalle.actualiza(conexion);
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }

  public static AlbaranesVentaDetalle getAlbaranesVentaDetalleByAvdId(String avdId)
  {
    AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle WHERE avd_id='"+avdId+"'");
    return elto;
  }

  public static AlbaranesVentaDetalle getAlbaranesVentaDetalleByAvdLinea(String avdLinea)
  {
    AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle WHERE avd_linea='"+avdLinea+"'");
    return elto;
  }

  public static ArrayList getAllAlbaranesVentaDetalle()
  {
    AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle");
    return lista;
  }

  public static int eliminaDetalles( String avdAvId ) 
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  int n = -1;
	  try{
	      PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM albaranes_venta_detalle WHERE avd_av_id=?");
	      sentencia.setString(1, avdAvId);
	      n = sentencia.executeUpdate();
	      sentencia.close();
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static boolean existeProducto( String notId, String prodId, String manual, String viaje )
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
		
		AlbaranesVentaDetalle elto = new AlbaranesVentaDetalle();
		ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta_detalle where avd_av_id='"+notId+"' and avd_producto='"+nombreProducto+"'  and avd_viaje='"+viaje+"'");
		if( lista.size() > 0 )
			existe = true;
		
	  return existe;
  }
  
  public static String getAlbaranesVentaDetalleByFvdMaxLinea(String avdAvId)
  {
    String resultado = EntidadBean.consultarValor("select max(avd_linea) from albaranes_venta_detalle where avd_av_id ='"+avdAvId+"'");
    return resultado;
  }
    
  public static int anadeDetalleALista( HttpServletRequest request, String manual) throws Exception 
  {
	  int resultado = -2;
	  	SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);
	  		
	  		AlbaranesVentaDetalle albaranesventadetalle = new AlbaranesVentaDetalle();
	  		String prodId = request.getParameter("prodId");
	  		String avClId = request.getParameter("avClId");	  
	  		
	  		String parameterCantidad = request.getParameter("cantidad");
	  		if(parameterCantidad==null)
	  			parameterCantidad = request.getParameter("prodCantidad");
	  		
	  		Productos producto = null;
	  		ProductosCliente productoCliente = null;
	  		if( Utils.skipNull(manual).equals("") ){
	  			 productoCliente = ProductosCliente.getProductosClienteByPclId( prodId );
	  			 producto =  Productos.getProductosByProdId( productoCliente.getPclProdId() );
	  		}else{
	  			 producto = Productos.getProductosByProdId( prodId );
	  		}
	  		
	  		//String hayStock = Stock.hayStock(producto.getProdId(), "1", parameterCantidad);
	  		//Luis Miguel Cambio
	  		String hayStock = Stock.hayStock(producto.getProdId(), "1", parameterCantidad,"");
	  		if(Utils.skipNull(hayStock).equals(""))
	  			throw new Exception("No dispone de stock suficiente para introducir este producto en el albaran");

	  		//Ahora tomamos todo el stock, y finalizamos de insertar cuando la cantidad sea 0
	  		float cantidadTotal = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad)));
	  		float cantidadParaFinalizar = cantidadTotal;
	  		ArrayList todoStock = Stock.getAllStockNoVacioNoCaducadoByStProdId(producto.getProdId());	
	  		
	  		String nombreProducto = "";
	  		float cantidad = 0;
	  		float precio = 0;
	  		float iva = 0;
	  		float descuento = 0;
	  		
	  		String lista = NotasEntregaDetalle.getNotasEntregaDetalleByFvdMaxLinea(request.getParameter("notId"));
	    	int linea = Integer.parseInt(Utils.skipNullNumero(lista));
	  		
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
	  			
		  		//Si estoy metiendo productos de forma automática
		  		if( Utils.skipNull(manual).equals("") ){
		  			Clientes cliente = Clientes.getClientesByClId(productoCliente.getPclClId());
		  			nombreProducto = producto.getProdNombre();
		  			//cantidad = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad) ));
		  			iva = Float.parseFloat( Utils.skipNullNumero( producto.getProdIva()) ) ;
		  			precio = Float.parseFloat( Utils.skipNullNumero( productoCliente.getPclPrecio())) ;
		  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
		  		}
		  		else{
		  			Clientes cliente = Clientes.getClientesByClId(avClId);
		  			//cantidad = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad) ) );	    	
		  			iva = Float.parseFloat( Utils.skipNullNumero( producto.getProdIva()) );
		  			precio = Float.parseFloat( quitar_comas(Utils.skipNullNumero(request.getParameter("prodPrecio")))) ;
		  			descuento = Float.parseFloat( Utils.skipNullNumero( cliente.getClDescuento()));
		  			nombreProducto = producto.getProdNombre();
		  		}
		  		
		  		//El IVA solo se utiliza para el calculo en las facturas
		  		float importe = UtilsFacturacion.getImporte( precio, descuento, 0, cantidad );
		    	//Añadimos las lineas de Detalle
		  		albaranesventadetalle.setAvdProducto( nombreProducto );	    	
		  		albaranesventadetalle.setAvdCantidad( String.valueOf(cantidad) );
		  		albaranesventadetalle.setAvdPrecioVenta( String.valueOf(precio) );
		  		albaranesventadetalle.setAvdDescuento( String.valueOf(descuento) );
		  		albaranesventadetalle.setAvdImporte( String.valueOf(importe) );
		  		albaranesventadetalle.setAvdIva( String.valueOf( iva ) );
		  		albaranesventadetalle.setAvdAvId(request.getParameter("avId"));
		  		albaranesventadetalle.setAvdViaje(request.getParameter("avdViaje"));
		  		
		    	albaranesventadetalle.setAvdLinea(Integer.toString(linea));
		    	albaranesventadetalle.setAvdStId(stock.getStId());
		    	//Lo añadimos a la lista
		  		resultado = albaranesventadetalle.inserta(conexion);
	  		}
	  		conexion.commit();
	  	}catch(Exception e){
	  		conexion.rollback();
	  		throw e;
	  	}finally{
		    conexion.rollback();
			manager.closeConnection();  
	  	}
    	return resultado;
  }

  public static ArrayList eliminaDetalleDeLista( ArrayList detalles, String[] chkValues ) throws Exception 
  {	
	  	//Comprobamos si la lista de detalles viene vacia, por si las moscas para evitar errores
	  	if( !detalles.isEmpty() ){
			for(int i=0; i<chkValues.length; i++){
				detalles.remove( Integer.parseInt(chkValues[i]) - i );		
			}
	  	}
	    return detalles;
  }
  
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    AlbaranesVentaDetalle albaranesventadetalle = new AlbaranesVentaDetalle();
  	try{
    	albaranesventadetalle.setAvdAvId(rs.getString(AVDAVID));
    	albaranesventadetalle.setAvdCantidad(rs.getString(AVDCANTIDAD));
    	albaranesventadetalle.setAvdId(rs.getString(AVDID));
    	albaranesventadetalle.setAvdImporte(rs.getString(AVDIMPORTE));
    	albaranesventadetalle.setAvdIva(rs.getString(AVDIVA));
    	albaranesventadetalle.setAvdLinea(rs.getString(AVDLINEA));
    	albaranesventadetalle.setAvdPrecioVenta(rs.getString(AVDPRECIOVENTA));
    	albaranesventadetalle.setAvdProducto(rs.getString(AVDPRODUCTO));
    	albaranesventadetalle.setAvdReferencia(rs.getString(AVDREFERENCIA));
    	albaranesventadetalle.setAvdDescuento(rs.getString(AVDDESCUENTO));
    	albaranesventadetalle.setAvdViaje(rs.getString(AVDVIAJE));
  	}catch(Exception e){
  		throw e;
  	}
    	return albaranesventadetalle;
  }

  public static Object[] getAlbaranesVentaDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	AlbaranesVentaDetalle albaranesventadetalle = new AlbaranesVentaDetalle();
	String sql = "SELECT * FROM albaranes_venta_detalle " +
				 " WHERE 1 = 1 ";
  	try{
		AlbaranesVentaDetalle albaranesventadetalleFiltro = new AlbaranesVentaDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FALBARANESVENTADETALLE")!=null){
			albaranesventadetalleFiltro = (AlbaranesVentaDetalle)listaPaginada.getRequest().getSession().getAttribute("FALBARANESVENTADETALLE");
		}
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdAvId()))
    		sqlAñadido += "AND " + AVDAVID + " = '" + albaranesventadetalleFiltro.getAvdAvId() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdCantidad()))
    		sqlAñadido += "AND " + AVDCANTIDAD + " = '" + albaranesventadetalleFiltro.getAvdCantidad() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdId()))
    		sqlAñadido += "AND " + AVDID + " = '" + albaranesventadetalleFiltro.getAvdId() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdImporte()))
    		sqlAñadido += "AND " + AVDIMPORTE + " = '" + albaranesventadetalleFiltro.getAvdImporte() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdIva()))
    		sqlAñadido += "AND " + AVDIVA + " = '" + albaranesventadetalleFiltro.getAvdIva() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdLinea()))
    		sqlAñadido += "AND " + AVDLINEA + " = '" + albaranesventadetalleFiltro.getAvdLinea() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdPrecioVenta()))
    		sqlAñadido += "AND " + AVDPRECIOVENTA + " = '" + albaranesventadetalleFiltro.getAvdPrecioVenta() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdProducto()))
    		sqlAñadido += "AND " + AVDPRODUCTO + " = '" + albaranesventadetalleFiltro.getAvdProducto() + "'";
    	if(!Utils.empty(albaranesventadetalleFiltro.getAvdReferencia()))
    		sqlAñadido += "AND UPPER(" + AVDREFERENCIA + ") like UPPER('%" + albaranesventadetalleFiltro.getAvdReferencia() + "%')";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, albaranesventadetalle);
  }
}
