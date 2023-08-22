package panaderia.beans;
import java.sql.Connection;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;
import utils.UtilesDAO;
import utils.Utils;
import utils.UtilsFacturacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
public class NotasEntregaDetalle extends panaderia.beans.entidad.NotasEntregaDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public NotasEntregaDetalle(){ super(); }

  public static NotasEntregaDetalle getNotasEntregaDetalleByNotdetId(String notdetId)
  {
    NotasEntregaDetalle elto = new NotasEntregaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle WHERE notdet_id='"+notdetId+"'");
    return elto;
  }

  public static NotasEntregaDetalle getNotasEntregaDetalleByNotdetLinea(String notdetLinea)
  {
    NotasEntregaDetalle elto = new NotasEntregaDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle WHERE notdet_linea='"+notdetLinea+"'");
    return elto;
  }

  public static ArrayList getNotasEntregaDetalleByNotdetNotId(String notdetNotId)
  {
	NotasEntregaDetalle elto = new NotasEntregaDetalle();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle WHERE notdet_not_id='"+notdetNotId+"'");
    return lista;
  }
  
  public static ArrayList getNotasEntregaDetalleByNotdetNotIdWithStock(String notdetNotId)
  {
	NotasEntregaDetalle elto = new NotasEntregaDetalle();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle WHERE notdet_not_id='"+notdetNotId+"'");
	for(int i =0; i<lista.size(); i++){
		NotasEntregaDetalle detalle = (NotasEntregaDetalle)lista.get(i);
		FacturasCompraDetalle fcd = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStId(detalle.getNotdetStId());
		detalle.setLote(fcd.getFcdLote());
	}
    return lista;
  }

  public static ArrayList getAllNotasEntregaDetalle()
  {
    NotasEntregaDetalle elto = new NotasEntregaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle");
    return lista;
  }
  
  public static boolean existeProducto( String notId, String prodId, String manual )
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
		
		NotasEntregaDetalle elto = new NotasEntregaDetalle();
		ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega_detalle where notdet_not_id='"+notId+"' and notdet_producto='"+nombreProducto+"'");
		if( lista.size() > 0 )
			existe = true;
		
	  return existe;
  }
  
  public static String getNotasEntregaDetalleByFvdMaxLinea(String notdetNotId)
  {
    String resultado = EntidadBean.consultarValor("select max(notdet_linea) from notas_entrega_detalle where notdet_not_id ='"+notdetNotId+"'");
    return resultado;
  }
    
  
  public static int anadeDetalleALista(HttpServletRequest request, String manual ) throws Exception 
  {
	  int resultado = -2;
	  	SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);
	  		
	  		NotasEntregaDetalle notasentregadetalle = new NotasEntregaDetalle();
	  		String prodId = request.getParameter("prodId");
	  		String notClId = request.getParameter("notClId");
	  		
	  		String nombreProducto = "";
	  		float cantidad = 0;
	  		float precio = 0;
	  		float iva = 0;
	  		float descuento = 0;
	  		
	  		//Lo primero que debemos ver es si hay en stock cantidad suficiente de ese producto
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
	  		
	  		//CAmbio por luismiguel, no interfiere en lo mio
	  		//String hayStock = Stock.hayStock(producto.getProdId(), "1", parameterCantidad);
	  		String hayStock = Stock.hayStock(producto.getProdId(), "1", parameterCantidad, "");
	  		if(Utils.skipNull(hayStock).equals(""))
	  			throw new Exception("No dispone de stock suficiente para introducir este producto en la nota de entrega");
	  		
	  		//Ahora tomamos todo el stock, y finalizamos de insertar cuando la cantidad sea 0
	  		float cantidadTotal = Float.parseFloat( quitar_comas(Utils.skipNullNumero( parameterCantidad)));
	  		float cantidadParaFinalizar = cantidadTotal;
	  		ArrayList todoStock = Stock.getAllStockNoVacioNoCaducadoByStProdId(producto.getProdId());
	  		
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
		  			//cantidad =  Float.parseFloat( quitar_comas(Utils.skipNullNumero( request.getParameter("cantidad"))));
		  			precio = Float.parseFloat(Utils.skipNullNumero( productoCliente.getPclPrecio()));
		  			//A las notas no le ponemos IVA
		  			iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva())) ;
		  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
		  		}
		  		else{
		  			Clientes cliente = Clientes.getClientesByClId(notClId);
		  			//cantidad =  Float.parseFloat(quitar_comas(Utils.skipNullNumero( request.getParameter("prodCantidad"))));	    	
		  			precio = Float.parseFloat(quitar_comas(Utils.skipNullNumero(request.getParameter("prodPrecio"))));
		  			nombreProducto = producto.getProdNombre();
		  			iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
		  			descuento = Float.parseFloat(Utils.skipNullNumero( cliente.getClDescuento()));
		  		}
		  		
		  		//En las notas en IVA sera 0, solo se pondra en IVA en las facturas
		    	float importe = UtilsFacturacion.getImporte(precio, descuento, 0, cantidad);
		    	//Añadimos las lineas de Detalle
		    	//facturasventadetalle.setFvdReferencia( producto.getProdReferencia() );	
		    	notasentregadetalle.setNotdetProducto( nombreProducto );	    	
		    	notasentregadetalle.setNotdetCantidad( String.valueOf(cantidad) );
		    	notasentregadetalle.setNotdetPrecio( String.valueOf(precio) );
		    	notasentregadetalle.setNotdetImporte( String.valueOf(importe) );
		    	notasentregadetalle.setNotdetIva(String.valueOf(iva));
		    	notasentregadetalle.setNotdetDescuento(String.valueOf(descuento));
		    	notasentregadetalle.setNotdetNotId(request.getParameter("notId"));
		    	notasentregadetalle.setNotdetStId(stock.getStId());
		    	
		    	notasentregadetalle.setNotdetLinea(Integer.toString(linea));
		    	//Lo añadimos a la lista
		    	resultado = notasentregadetalle.inserta(conexion);
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
  
  
  public static int editaNotaDetalle(NotasEntregaDetalle notasentregadetalle, Connection conexion ) throws Exception 
  {
	  int resultado = -2;
	  	try{	  		
	    	float importe = UtilsFacturacion.getImporte(Float.parseFloat(notasentregadetalle.getNotdetPrecio()), 
										    			Float.parseFloat(notasentregadetalle.getNotdetDescuento()), 
										    			//Float.parseFloat(notasentregadetalle.getNotdetIva()), A las notas no le ponemos IVA
										    			0,
										    			Float.parseFloat(notasentregadetalle.getNotdetCantidad()));
	    	//Añadimos las lineas de Detalle
	    	notasentregadetalle.setNotdetImporte(String.valueOf(importe));
	    	//Lo añadimos a la lista
	    	resultado = notasentregadetalle.actualiza(conexion);
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }

  public static ArrayList eliminaDetalleDeLista( ArrayList detalles, String[] chkValues ) throws Exception 
  {	
	  	//Comprobamos si la lista de detalles viene vacia, por si las moscas para evitar errores
	  	if( !detalles.isEmpty() ){
			for(int i=0; i<chkValues.length; i++){
				//Eliminamos los marcados de nuestra lista de detalles
				NotasEntregaDetalle detalle = (NotasEntregaDetalle) detalles.get( Integer.parseInt(chkValues[i]) - i );
				detalles.remove( detalle );			
			}
	  	}
	    return detalles;
  }
  
  
  public static ArrayList getAllNotasEntregaDetalleConversorByEntregaDetalle(String endId, String viaje)
  {
    NotasEntregaDetalle elto = new NotasEntregaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
								"select prod_nombre as notdet_producto, " +
								"			 CASE WHEN cud_cantidad" + viaje + " = '' THEN '0' ELSE cud_cantidad" + viaje + " END as notdet_cantidad, " +
								"			 pcl_precio as notdet_precio, " +
								"			 round((CASE WHEN cud_cantidad" + viaje + " = '' THEN '0' ELSE cud_cantidad" + viaje + "  END) * (pcl_precio - (pcl_precio * cl_descuento)),3) as notdet_importe, " +
								"			 prod_iva as notdet_iva, " +
								"			 cl_descuento as notdet_descuento " +
								"from cuadrantes " +
								"join cuadrantes_detalle on cu_id = cud_cu_id " +
								"join productos_cliente on pcl_id = cud_pcl_id " +
								"join productos on prod_id = pcl_prod_id " +
								"join entregas_detalle on entregas_detalle.end_cu_id = cuadrantes.cu_id " +
								"join entregas ON entregas_detalle.end_ent_id = entregas.ent_id " +
								"join clientes ON cuadrantes.cu_cl_id = clientes.cl_id " +
								"where end_id = " + endId + " " +
								"and cud_cantidad" + viaje + " != '' and cud_cantidad" + viaje + " != '0'");
    return lista;
  }
  
  public static ArrayList getAllNotasEntregaDetalleConversorByCuadrante(String cudCuId, String viaje, String notId)
  {
    NotasEntregaDetalle elto = new NotasEntregaDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva(
								"select prod_nombre as notdet_producto, " +
								"			 CASE WHEN cud_cantidad" + viaje + " = '' THEN '0' ELSE cud_cantidad" + viaje + " END as notdet_cantidad, " +
								"			 pcl_precio as notdet_precio, " +
								"			 round((CASE WHEN cud_cantidad" + viaje + " = '' THEN '0' ELSE cud_cantidad" + viaje + "  END) * (pcl_precio - (pcl_precio * cl_descuento)),3) as notdet_importe, " +
								"			 prod_iva as notdet_iva, " +
								"			 cl_descuento as notdet_descuento " +
								"from cuadrantes " +
								"join cuadrantes_detalle on cu_id = cud_cu_id " +
								"join productos_cliente on pcl_id = cud_pcl_id " +
								"join productos on prod_id = pcl_prod_id " +
								"join clientes ON cuadrantes.cu_cl_id = clientes.cl_id " +
								"where cud_cu_id = " + cudCuId + " " +
								"and prod_nombre not in (select notdet_producto from notas_entrega_detalle where notdet_not_id = " + notId + ") " +
								"and cud_cantidad" + viaje + " != '' and cud_cantidad" + viaje + " != '0'");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    NotasEntregaDetalle notasentregadetalle = new NotasEntregaDetalle();
  	try{
    	notasentregadetalle.setNotdetCantidad(rs.getString(NOTDETCANTIDAD));
    	notasentregadetalle.setNotdetId(rs.getString(NOTDETID));
    	notasentregadetalle.setNotdetImporte(rs.getString(NOTDETIMPORTE));
    	notasentregadetalle.setNotdetLinea(rs.getString(NOTDETLINEA));
    	notasentregadetalle.setNotdetNotId(rs.getString(NOTDETNOTID));
    	notasentregadetalle.setNotdetPrecio(rs.getString(NOTDETPRECIO));
    	notasentregadetalle.setNotdetProducto(rs.getString(NOTDETPRODUCTO));
    	notasentregadetalle.setNotdetIva(rs.getString(NOTDETIVA));
    	notasentregadetalle.setNotdetDescuento(rs.getString(NOTDETDESCUENTO));
    	notasentregadetalle.setNotdetStId(rs.getString(NOTDETSTID));
  	}catch(Exception e){
  		throw e;
  	}
    	return notasentregadetalle;
  }

  public static Object[] getNotasEntregaDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	NotasEntregaDetalle notasentregadetalle = new NotasEntregaDetalle();
	String sql = "SELECT * FROM notasentregadetalle " +
				 " WHERE 1 = 1 ";
  	try{
		NotasEntregaDetalle notasentregadetalleFiltro = new NotasEntregaDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FNOTASENTREGADETALLE")!=null){
			notasentregadetalleFiltro = (NotasEntregaDetalle)listaPaginada.getRequest().getSession().getAttribute("FNOTASENTREGADETALLE");
		}
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetCantidad()))
    		sqlAñadido += "AND " + NOTDETCANTIDAD + " = '" + notasentregadetalleFiltro.getNotdetCantidad() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetId()))
    		sqlAñadido += "AND " + NOTDETID + " = '" + notasentregadetalleFiltro.getNotdetId() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetImporte()))
    		sqlAñadido += "AND " + NOTDETIMPORTE + " = '" + notasentregadetalleFiltro.getNotdetImporte() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetLinea()))
    		sqlAñadido += "AND " + NOTDETLINEA + " = '" + notasentregadetalleFiltro.getNotdetLinea() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetNotId()))
    		sqlAñadido += "AND " + NOTDETNOTID + " = '" + notasentregadetalleFiltro.getNotdetNotId() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetPrecio()))
    		sqlAñadido += "AND " + NOTDETPRECIO + " = '" + notasentregadetalleFiltro.getNotdetPrecio() + "'";
    	if(!Utils.empty(notasentregadetalleFiltro.getNotdetProducto()))
    		sqlAñadido += "AND " + NOTDETPRODUCTO + " = '" + notasentregadetalleFiltro.getNotdetProducto() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, notasentregadetalle);
  }
}
