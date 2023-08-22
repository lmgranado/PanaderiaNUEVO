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
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
public class FacturasCompraDetalle extends panaderia.beans.entidad.FacturasCompraDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FacturasCompraDetalle(){ super(); }

 
  public static String getprecioProductoUltimaCompra(String nombreProducto)
  {
	  return EntidadBean.consultarValor("SELECT fcd_precio_compra FROM facturas_compra_detalle"
			  + " where fcd_id=(select max(fcd_id) id from facturas_compra_detalle where fcd_producto ='"+ nombreProducto + "')" );
  }
  
  public static ArrayList getFacturasCompraDetalleByFcdFcId(String fcdFcId)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    ArrayList lista =  elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle WHERE fcd_fc_id='"+fcdFcId+"'");
    for(int i = 0; i<lista.size(); i++){
    	((FacturasCompraDetalle)lista.get(i)).setComponentesUsados(ComposicionFabricacion.getComposicionFabricacionByCfIdStFabricado(((FacturasCompraDetalle)lista.get(i)).getFcdStId()));
    }
    return lista;
  }
  
  public static int eliminaDetalles( String fcdFcId ) 
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  int n = -1;
	  try{
	      PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_compra_detalle WHERE fcd_fc_id=?");
	      sentencia.setString( 1, fcdFcId );
	      n = sentencia.executeUpdate();
	      sentencia.close();
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  //Luis Miguel 14/07/14 --> Para no dejar que se pueda insertar en una misma factura el mismo producto con el mismo lote
  public static boolean existeProducto ( String fcdId, String prodId, String lote )
  {
	  	boolean existe = false;
	  	String nombreProducto = "";
	  	Productos producto = Productos.getProductosByProdId( prodId );
  		nombreProducto = producto.getProdNombre();
				
		FacturasCompraDetalle elto = new FacturasCompraDetalle();
		ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle where fcd_fc_id='"+fcdId+"' and fcd_producto='"+nombreProducto+"' and fcd_lote='"+lote+"'");
		if( lista.size() > 0 )
			existe = true;
		
	  return existe;
  }
  
  
  public static int eliminaDetalleLista( ArrayList listaDetalles ) throws Exception 
  {
	  
		int resultado = -2;
		Connection conexion = null;
	  	try{
	  		conexion = utils.PoolConexiones.conexion();
	  		conexion.setAutoCommit(false);

			for(int i=0; i<listaDetalles.size(); i++){
				//Eliminamos los marcados de nuestra lista de detalles
				//Ademas de eliminar debemos elimirlo de nuestro stock y añadirlo al resto del stock usado
				FacturasCompraDetalle detalle = (FacturasCompraDetalle) listaDetalles.get(i);
				FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId(detalle.getFcdFcId());
				//Lo hacemos en una transicion
				    	
		    	Stock stock = detalle.getStock();
		    	
		    	//verificamos que se puede eliminar, sabiendo si existen bastantes productos no usados.
		    	if(Double.parseDouble(detalle.getFcdCantidad())>Double.parseDouble(stock.getStCantidadFinal())){
		    		return -3;
		    	}
		    	else{
			    	//Eliminamos el detalle de la factura
			    	resultado = detalle.elimina(conexion);	
			    	
			    	//Actualizamos el stock, tanto la cantidad Inicial como la final al haber quitado cantidad de producto de la factura de compra		    	
			    	String cantidadInicial = Double.toString(Double.parseDouble(stock.getStCantidadInicial()) - Double.parseDouble(detalle.getFcdCantidad()));
			    	stock.setStCantidadInicial( cantidadInicial );
			    	String cantidadFinal = Double.toString(Double.parseDouble(stock.getStCantidadFinal()) - Double.parseDouble(detalle.getFcdCantidad()));
			    	stock.setStCantidadFinal( cantidadFinal );
			    	
			    	//Si la cantidad final es 0 eliminamos
			    	if(Double.parseDouble(stock.getStCantidadFinal())==0)
			    		stock.elimina(conexion);
			    	else
			    		stock.actualiza(conexion);
			    	
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
	  
  public static int anadeDetalleALista( HttpServletRequest request ) throws Exception 
  {
	//Si es de compra, solo metenemos stockaje
		//Si es de fabricación calculamos si se puede meter, quitamos los productos utilizados para la fabricación
		//y despues metemos el stockaje de los productos creados
		//Si todo va bien metemos lso productos en la factura
	  int resultado = -2;
	  	try{
	  		FacturasCompraDetalle facturascompradetalle = new FacturasCompraDetalle();
	  		String fcPrId = request.getParameter("fcPrId");	  		
	  		String prodId = request.getParameter("prodId");
	  		Proveedores proveedor = Proveedores.getProveedoresByPrId( fcPrId );
	  		Productos producto = Productos.getProductosByProdId( prodId );
	  		
	  		float descuento =  Float.parseFloat(Utils.skipNullNumero( proveedor.getPrDescuento() ) );	  		
	  		float precio =  Float.parseFloat(quitar_comas(Utils.skipNull(request.getParameter("prodPrecio"))));
	  		float cantidad =  Float.parseFloat(quitar_comas(Utils.skipNull(request.getParameter("prodCantidad"))));
	  		float iva = Float.parseFloat(Utils.skipNullNumero( producto.getProdIva()));
	  		float importeNeto = UtilsFacturacion.getImporte( precio, descuento, iva, cantidad );
	  		String lote =  Utils.skipNull(request.getParameter("fcdLote"));
	  		String origen =  Utils.skipNull(request.getParameter("fcdOrigen"));
	    	
	  		//LUIS MIGUEL - 14/07/14
	  		//Si ya existe en la tabla Stock dicho producto con ese lote, tendremos que actualizar el registro
	    	//sumandole a la cantidad inicial y final la cantidad nueva que se quiere insertar.
	    	ArrayList listaStockExistente = Stock.getStockByStProdIdAndStProdLote( prodId, lote );
	  		
	  		
	  		//Lo añadimos a la lista y el stock si es de compra
	  		Stock stock = new Stock();
	  		//Proveedor de fabricacion tanto en A como en B
	  		if(!proveedor.getPrId().equals("999") && !proveedor.getPrId().equals("998")){
		    	
		    	if( listaStockExistente.size() > 0 ){
		    		Stock stockExistente =  (Stock) listaStockExistente.get(0);
		    		//Actualizamos el stock existente, sumandole la cantidad nueva del detalle de la factura de compra
		    		stockExistente.setStCantidadInicial( String.valueOf( cantidad + Float.parseFloat(quitar_comas(Utils.skipNull( stockExistente.getStCantidadInicial() ) ))));
		    		stockExistente.setStCantidadFinal( String.valueOf(cantidad + Float.parseFloat(quitar_comas(Utils.skipNull( stockExistente.getStCantidadFinal() ) ))));
		    		stockExistente.setStFCaducidad( request.getParameter("stFCaducidad"), Utils.DATE_SHORT);
		    		//Añadimos el stock existente con la modificacion de las cantidades
			    	facturascompradetalle.setStock(stockExistente);
		    	}
		    	else{
		    		stock.setStProdId(producto.getProdId());
			    	stock.setStCantidadInicial(String.valueOf(cantidad));
			    	stock.setStCantidadFinal(String.valueOf(cantidad));
			    	stock.setStFCaducidad(request.getParameter("stFCaducidad"), Utils.DATE_SHORT);
			    	stock.setStProdLote(lote);
			    	stock.setStProdOrigen(origen);
		    	
		    		facturascompradetalle.setStock(stock);
		    	}
	  		}
	  		
	    	//Añadimos las lineas de Detalle
	    	facturascompradetalle.setFcdReferencia( producto.getProdReferencia() );	
	    	facturascompradetalle.setFcdProducto( producto.getProdNombre() );	    	
	    	facturascompradetalle.setFcdCantidad( String.valueOf(cantidad) );
	    	facturascompradetalle.setFcdPrecioCompra( String.valueOf(precio) );
	    	facturascompradetalle.setFcdDescuento( String.valueOf( descuento ) );
	    	facturascompradetalle.setFcdImporte( String.valueOf(importeNeto) );
	    	facturascompradetalle.setFcdIva( String.valueOf(iva) );
	    	facturascompradetalle.setFcdFcId( request.getParameter("fcId") );
	    	facturascompradetalle.setFcdStId(stock.getStId());
	    	facturascompradetalle.setFcdLote(request.getParameter("fcdLote"));
	    	facturascompradetalle.setFcdProdOrigen(request.getParameter("fcdOrigen"));
	    	
	    	String lista = FacturasCompraDetalle.getFacturasCompraDetalleByFvdMaxLinea(request.getParameter("fcId"));
	    	int linea = Integer.parseInt(Utils.skipNullNumero(lista));
	    	facturascompradetalle.setFcdLinea(Integer.toString(linea + 1));
	    	
	    	if( listaStockExistente.size() > 0 )
	    		resultado = facturascompradetalle.insertaDetalleNuevo();
	    	else
	    		resultado = facturascompradetalle.inserta();
	    		
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }
  

  public static String getFacturasCompraDetalleByFvdMaxLinea(String fcdFcId)
  {
    String resultado = EntidadBean.consultarValor("select max(fcd_linea) from facturas_compra_detalle where fcd_fc_id ='"+fcdFcId+"'");
    return resultado;
  }

  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdId(String fcdId)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle WHERE fcd_id='"+fcdId+"'");
    return elto;
  }

  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdLinea(String fcdLinea)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle WHERE fcd_linea='"+fcdLinea+"'");
    return elto;
  }
  
  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdProductoAndFcdLote(String fcdProducto, String fcdLote)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle WHERE fcd_producto='"+fcdProducto+"' AND fcd_lote ='"+fcdLote+"'");
    return elto;
  }

  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdStId(String fcdStId)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle WHERE fcd_st_id='"+fcdStId+"'");
    return elto;
  }

  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdStIdFechaEntradaMenor( String fcdStId )
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle " +
    		" WHERE fcd_id=( select fcd_id from facturas_compra_detalle where fcd_st_id='"+fcdStId+"' order by fcd_f_entrada desc limit 1)");
    return elto;
  }
  
  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdStIdDisctictLote(String fcdStId)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT distinct(fcd_lote) FROM facturas_compra_detalle WHERE fcd_st_id='"+fcdStId+"'");
    return elto;
  }
  
  public static FacturasCompraDetalle getFacturasCompraDetalleByFcdStIdDisctictProductoLote(String fcdProducto,String fcdLote)
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    elto.consultaReflexiva("SELECT distinct(fcd_st_id) FROM facturas_compra_detalle WHERE fcd_producto='"+fcdProducto+"' and fcd_lote='"+fcdLote+"'");
    return elto;
  }
  
  public static ArrayList getAllFacturasCompraDetalle()
  {
    FacturasCompraDetalle elto = new FacturasCompraDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra_detalle");
    return lista;
  }
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FacturasCompraDetalle facturascompradetalle = new FacturasCompraDetalle();
  	try{
    	facturascompradetalle.setFcdCantidad(rs.getString(FCDCANTIDAD));
    	facturascompradetalle.setFcdDescuento(rs.getString(FCDDESCUENTO));
    	facturascompradetalle.setFcdFCaducidad(rs.getTimestamp(FCDFCADUCIDAD));
    	facturascompradetalle.setFcdFEntrada(rs.getTimestamp(FCDFENTRADA));
    	facturascompradetalle.setFcdFcId(rs.getString(FCDFCID));
    	facturascompradetalle.setFcdId(rs.getString(FCDID));
    	facturascompradetalle.setFcdImporte(rs.getString(FCDIMPORTE));
    	facturascompradetalle.setFcdIva(rs.getString(FCDIVA));
    	facturascompradetalle.setFcdLinea(rs.getString(FCDLINEA));
    	facturascompradetalle.setFcdLote(rs.getString(FCDLOTE));
    	facturascompradetalle.setFcdPrecioCompra(rs.getString(FCDPRECIOCOMPRA));
    	facturascompradetalle.setFcdProducto(rs.getString(FCDPRODUCTO));
    	facturascompradetalle.setFcdReferencia(rs.getString(FCDREFERENCIA));
    	facturascompradetalle.setFcdStId(rs.getString(FCDSTID));
    	facturascompradetalle.setStock(Stock.getStockByStId(facturascompradetalle.getFcdStId()));
  	}catch(Exception e){
  		throw e;
  	}
    	return facturascompradetalle;
  }

  public static Object[] getFacturasCompraDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FacturasCompraDetalle facturascompradetalle = new FacturasCompraDetalle();
	String sql = "SELECT * FROM facturas_compra_detalle " +
				 " WHERE 1 = 1 ";
  	try{
		FacturasCompraDetalle facturascompradetalleFiltro = new FacturasCompraDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA_DETALLE")!=null){
			facturascompradetalleFiltro = (FacturasCompraDetalle)listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA_DETALLE");
		}
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdCantidad()))
    		sqlAñadido += "AND " + FCDCANTIDAD + " = '" + facturascompradetalleFiltro.getFcdCantidad() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdDescuento()))
    		sqlAñadido += "AND " + FCDDESCUENTO + " = '" + facturascompradetalleFiltro.getFcdDescuento() + "'";
    	if(facturascompradetalleFiltro.getFcdFCaducidad()!=null)
    		sqlAñadido += "AND " + FCDFCADUCIDAD + " = '" + facturascompradetalleFiltro.getFcdFCaducidad() + "'";
    	if(facturascompradetalleFiltro.getFcdFEntrada()!=null)
    		sqlAñadido += "AND " + FCDFENTRADA + " = '" + facturascompradetalleFiltro.getFcdFEntrada() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdFcId()))
    		sqlAñadido += "AND " + FCDFCID + " = '" + facturascompradetalleFiltro.getFcdFcId() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdId()))
    		sqlAñadido += "AND " + FCDID + " = '" + facturascompradetalleFiltro.getFcdId() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdImporte()))
    		sqlAñadido += "AND " + FCDIMPORTE + " = '" + facturascompradetalleFiltro.getFcdImporte() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdIva()))
    		sqlAñadido += "AND " + FCDIVA + " = '" + facturascompradetalleFiltro.getFcdIva() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdLinea()))
    		sqlAñadido += "AND " + FCDLINEA + " = '" + facturascompradetalleFiltro.getFcdLinea() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdLote()))
    		sqlAñadido += "AND " + FCDLOTE + " = '" + facturascompradetalleFiltro.getFcdLote() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdPrecioCompra()))
    		sqlAñadido += "AND " + FCDPRECIOCOMPRA + " = '" + facturascompradetalleFiltro.getFcdPrecioCompra() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdProducto()))
    		sqlAñadido += "AND " + FCDPRODUCTO + " = '" + facturascompradetalleFiltro.getFcdProducto() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdReferencia()))
    		sqlAñadido += "AND " + FCDREFERENCIA + " = '" + facturascompradetalleFiltro.getFcdReferencia() + "'";
    	if(!Utils.empty(facturascompradetalleFiltro.getFcdStId()))
    		sqlAñadido += "AND " + FCDSTID + " = '" + facturascompradetalleFiltro.getFcdStId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, facturascompradetalle);
  }
  
  public static ArrayList getDatosTrazabilidad(String FechaDesde, String FechaHasta, String stStockCero, String prodId, String desglosado)
  {
	  
	  String filtro1 = "";
	  String filtro2 = "";
	  String filtro3 = "";
	  String filtroDesglosado = "";
	  
	  if(!Utils.skipNull(prodId).equals("")){
		  filtro1 += " AND prod_id in (0" + prodId + ")";
		  filtro2 += " AND st_prod_id in (0" + prodId + ")";
		  filtro3 += " AND prod_id in (0" + prodId + ")";
	  }
	  
	  if(!Utils.skipNull(FechaDesde).equals("")){
		  filtro1 += " AND fv_fecha >= STR_TO_DATE('" + FechaDesde + "','%d/%m/%Y')";
		  filtro2 += " AND fc_fecha >= STR_TO_DATE('" + FechaDesde + "','%d/%m/%Y')";
		  filtro3 += " AND fc_fecha >= STR_TO_DATE('" + FechaDesde + "','%d/%m/%Y')";
	  }
	  
	  if(!Utils.skipNull(FechaHasta).equals("")){
		  filtro1 += " AND fv_fecha <= STR_TO_DATE('" + FechaHasta + "','%d/%m/%Y')";
		  filtro2 += " AND fc_fecha <= STR_TO_DATE('" + FechaHasta + "','%d/%m/%Y')";
		  filtro3 += " AND fc_fecha <= STR_TO_DATE('" + FechaHasta + "','%d/%m/%Y')";
	  }
	  
	  if(Utils.skipNull(stStockCero).equals("")){
		  filtro2 += " AND st_cantidad_final != 0 ";
		  filtro3 += " AND st_cantidad_final != 0 ";
	  }
	  
	  if(!Utils.skipNull(desglosado).equals("")){
		  filtroDesglosado = " UNION " +
							 "select  " +
							 "      fc_fecha as fecha, " +
							 "      'Fabricacion' as tipo, " +
							 "      fc_numero_factura as documento, " +
							 "      pr_nombre as nombre, " +
							 "      0 as entrada, " +
							 "      cf_cantidad as salidas, " +
							 "      (select fcd_lote from facturas_compra_detalle where fcd_st_id = cf_id_st_usado) as lote, " +
							 "      fcd_lote as lote_fabricado, " +
							 "      fcd_producto as producto_fabricado, " +
							 "      fcd_cantidad as cantidad_fabricada, " +
							 "	    (select prod_nombre from productos where prod_id = st_prod_id) as fcd_producto, " +
							 "		st_regularizacion as regularizacion " +
							 "         from stock " +
							 "         join composicion_fabricacion on st_id = cf_id_st_usado " +
							 "         join facturas_compra_detalle on cf_id_st_fabricado = fcd_st_id " +
							 "         join facturas_compra on fcd_fc_id = fc_id " +
							 "         join proveedores on pr_id = fc_pr_id " +
							 "where 1 = 1 " +
							 filtro2;
	  }
	  
    ArrayList lista =  EntidadBean.consulta("select fv_fecha as fecha, " +
													 "     'Venta 'as tipo, " +
													 "      fv_numero_factura as documento, " +
													 "      cl_nombre as nombre, " +
													 "      0 as entrada, " +
													 "      fvd_cantidad as salidas, " + 
													 "      fcd_lote as lote, " +
													 "      '' as lote_fabricado, " +
													 "      '' as producto_fabricado, " +
													 "      '' as cantidad_fabricada, " +
													 "		fvd_producto ," +
													 "		0 as regularizacion " +	
													 "from facturas_venta_detalle  " +
													 "         join facturas_venta on fvd_fv_id = fv_id " +
													 "         join clientes on fv_cl_id = cl_id " +
													 "         join facturas_compra_detalle on fvd_st_id = fcd_st_id " +
													 "		   join productos on fvd_producto = prod_nombre " + 	
													 "where 1 = 1 " +
													 filtro1 + 
													 filtroDesglosado + 
													 " UNION " +
													 "select fc_fecha as fecha, " +
													 "      case when fc_pr_id in (998,999) then 'Fabricación' else 'Entrada' end as tipo, " +
													 "      fc_numero_factura as documento, " +
													 "      pr_nombre as nombre, " +
													 "      fcd_cantidad as entrada, " +
													 "      0 as salidas,  " +
													 "      fcd_lote as lote, " +
													 "      '' as lote_fabricado, " +
													 "      '' as producto_fabricado, " +
													 "      '' as cantidad_fabricada, " +
													 "		fcd_producto, " +
													 "		st_regularizacion as regularizacion " +
													 "from facturas_compra_detalle  " +
													 "         join facturas_compra on fcd_fc_id = fc_id " +
													 "         join proveedores on fc_pr_id = pr_id " +
													 "         join stock on fcd_st_id = st_id " +
													 "		   join productos on fcd_producto = prod_nombre " + 
												 	 "where 1 = 1 " +
												 	 filtro3 + 
												 	 " order by fvd_producto, tipo");

    return lista;
  }
}
