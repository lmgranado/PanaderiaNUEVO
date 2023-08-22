package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
public class Stock extends panaderia.beans.entidad.StockEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Stock(){ super(); }

  public static ArrayList getStockGroupByProductoLote( HttpServletRequest request){
	  
	  String where = "WHERE 1=1";
	  if( !Utils.empty( request.getParameter( "stProdId" ) ) ){
		  where += " AND " +STPRODID+ " = " +request.getParameter( "stProdId" ) ;
	  }
	  
  	  if( !Utils.empty( request.getParameter( "stFechaDesde" ) ) && !Utils.empty( request.getParameter( "stFechaHasta" ) ) )
  		  where += " AND fc_fecha BETWEEN str_to_date('"+request.getParameter( "stFechaDesde" )+"','%d/%m/%Y') AND str_to_date('"+request.getParameter( "stFechaHasta" )+"','%d/%m/%Y') ";
  	  else if( !Utils.empty( request.getParameter( "stFechaDesde" ) ) ) 
  		  where += " AND fc_fecha >= str_to_date('"+request.getParameter( "stFechaDesde" )+"','%d/%m/%Y')";
  	  else if( !Utils.empty( request.getParameter( "stFechaHasta" ) ) )
  		  where += " AND fc_fecha <= str_to_date('"+request.getParameter( "stFechaHasta" )+"','%d/%m/%Y') ";
  	
  	  if( request.getParameter( "stStockCero" ) == null ){
  		  where += " AND " + STCANTIDADFINAL + " > 0 ";
  	  }
  	
	  ArrayList lista = Stock.consulta( "SELECT st.st_id,st.st_prod_id, st.st_cantidad_inicial, st.st_cantidad_final, st.st_regularizacion,st.st_salidas, st.st_f_caducidad," +
	  									" fc.fc_fecha, fcd.fcd_producto,fcd.fcd_f_entrada, fcd.fcd_f_caducidad,fcd.fcd_lote FROM stock st "+
			  				" JOIN facturas_compra_detalle fcd ON st.st_id=fcd.fcd_st_id " +
			  				" JOIN facturas_compra fc ON fc_id = fcd.fcd_fc_id " +
			  				  where +
			  				" group by fcd.fcd_producto, fcd.fcd_lote " +
			  				" order by fcd.fcd_producto asc " );
		
	  return lista;
  }
  
  public static Stock getStockByStId(String stId)
  {
    Stock elto = new Stock();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM stock WHERE st_id='"+stId+"'");
    return elto;
  }

  //LUIS MIGUEL - 25/08/14
  public static Stock getStockByStIdStProdLote( String stId, String stProdLote)
  {
	  Stock elto = new Stock();
	  elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM stock WHERE st_id='"+stId+"' AND st_prod_lote='" + stProdLote + "'");
	  return elto;
  }

  public static Stock getStockByStProdId(String stProdId)
  {
    Stock elto = new Stock();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM stock WHERE st_prod_id='"+stProdId+"'");
    return elto;
  }
  
  /** RECORDAR METER EL DISTINCT DE LA COLUMNA LOTE CUANDO SE MODIFIQUE LA RESTRICCION DE FACTURAS_COMPRA_DETALLE 
   * UNIQUE KEY `fcd_unico_lote_uk` (`fcd_producto`,`fcd_lote`),*/
  public static ArrayList getListaStockByStProdId( String stProdId )
  {
    Stock elto = new Stock();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock WHERE st_prod_id='"+stProdId+"' AND st_cantidad_final>0");
    return lista;
  }

  
  //Para saber si hay stock vemos si sumando todo el stock del producto, que no este caducado,
  //y que sea superior a la cantidad minima que necesita 1 producto para ser fabricado
  public static String hayStock(String stProdId, String cantidadMinima, String cantidad, String stProdlote)
  {
    return EntidadBean.consultarValor("SELECT sum(st_cantidad_final) as st_cantidad_final FROM stock WHERE st_prod_id='" + stProdId + "' and st_prod_lote='" + stProdlote + "' and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d')  " +
    					   "	and st_cantidad_final >= '" + cantidadMinima + "'" +
    					   "	group by st_prod_id " +
    					   "	having sum(st_cantidad_final)>='" + quitar_comas(cantidad) + "'");
  }
  
  public static String hayStockNombreProducto(String nombreProducto, String cantidadMinima, String cantidad)
  {
    return EntidadBean.consultarValor("SELECT sum(st_cantidad_final) as st_cantidad_final FROM stock " +
    								   "join productos on st_prod_id = prod_id " + 
			    					   "WHERE prod_nombre='" + nombreProducto + "' and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d')  " +
			    					   "	and st_cantidad_final > '" + quitar_comas(cantidadMinima) + "'" +
			    					   "	group by st_prod_id " +
			    					   "	having sum(st_cantidad_final)>='" + quitar_comas(cantidad) + "'");
  }
  
  
  public static ArrayList getAllStockNoVacioNoCaducadoByStProdIdNuevo(String stProdId, String cantidadParaFabricarUno)
  {
    ArrayList lista = Stock.consulta("SELECT st_id, fcd_lote, st_cantidad_final, date_format(st_f_caducidad,'%d/%m/%Y'), floor(st_cantidad_final/" + cantidadParaFabricarUno + ") " +
									"FROM stock  " +
									"left join facturas_compra_detalle on st_id = fcd_st_id " +
									"WHERE st_prod_id= " + stProdId + " " +
									"and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d') " + 
									"and st_cantidad_final > 0  " +
									"order by st_f_caducidad asc");
    return lista;
  }
  
  
  public static boolean esFabricacionMismoProductoYComponentes(ArrayList productosExistentes, ArrayList productosNuevos )
  {
	boolean es = true;
	
	for(int i = 0;  (i<productosExistentes.size() && es); i++){
		
		
	}
	
    return es;
  }
  
//LUIS MIGUEL 04/07/14 --> Funcion que se utiliza a la hora de insertar un detalle en las facturas de compra, para
  //ver si existe un registro con dicho producto y lote, y asi no insertarlo sino actualizar sus cantidades
  public static ArrayList getStockByStProdIdAndStProdLote(String stProdId, String lote )
  {
	Stock elto = new Stock();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock  " +
													  "WHERE st_prod_id= " + stProdId + " " +
													  "and st_prod_lote= '" + lote + "' " +													  													  
													  "order by st_f_caducidad asc");
    return lista;
  }
  
  
//LUIS MIGUEL 04/07/14 
  public static ArrayList getAllStockNoVacioNoCaducadoByStProdId( String stProdId )
  {
	Stock elto = new Stock();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock  " +
													  "WHERE st_prod_id= " + stProdId + " " +
													  "and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d') " + 
													  "and st_cantidad_final > 0  " +
													  "order by st_f_caducidad asc");
    return lista;
  }
  
  //LUIS MIGUEL 04/07/14 
  public static ArrayList getAllStockNoVacioNoCaducadoByStProdId(String stProdId, String lote )
  {
	Stock elto = new Stock();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock  " +
													  "WHERE st_prod_id= " + stProdId + " " +
													  "and st_prod_lote= '" + lote + "' " +
													  "and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d') " + 
													  "and st_cantidad_final > 0  " +
													  "order by st_f_caducidad asc");
    return lista;
  }
  
  public static ArrayList getAllStockNoVacioNoCaducadoByProdNombre(String prodNombre)
  {
	Stock elto = new Stock();
	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock  " +
													  "join productos on st_prod_id = prod_id " +
													  "WHERE prod_nombre= '" + prodNombre + "' " +
													  "and date_format(st_f_caducidad,'%Y/%m/%d') >= date_format(sysdate(),'%Y/%m/%d') " + 
													  "and st_cantidad_final > 0  " +
													  "order by st_f_caducidad asc");
    return lista;
  }


  public static ArrayList getAllStock()
  {
    Stock elto = new Stock();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM stock");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Stock stock = new Stock();
  	try{
    	stock.setStCantidadFinal(rs.getString(STCANTIDADFINAL));
    	stock.setStCantidadInicial(rs.getString(STCANTIDADINICIAL));
    	stock.setStFCaducidad(rs.getTimestamp(STFCADUCIDAD));
    	stock.setStId(rs.getString(STID));
    	stock.setStProdId(rs.getString(STPRODID));
    	stock.setStRegularizacion(rs.getString(STREGULARIZACION));
    	stock.setStSalidas(rs.getString(STSALIDAS));
    	//Ver como hacer porque entra en un bucle, ya que cuando se crea facturascompradetalle se crea objeto stock y si hago esto entra en bucle
    	//stock.setFacturasCompraDetalle( FacturasCompraDetalle.getFacturasCompraDetalleByFcdStIdFechaEntradaMenor( stock.getStId()) );
    	
    	//Parte de las tabla facturas_compra
    	String[] fcDatosRelacionados = new String[4];
    	fcDatosRelacionados[0] = rs.getString("fc_id");
    	fcDatosRelacionados[1] = rs.getString("fc_numero_factura");
    	fcDatosRelacionados[2] = rs.getString("fc_fecha");
    	fcDatosRelacionados[3] = rs.getString("fc_pr_id");
    	stock.setFcDatosRelacionados(fcDatosRelacionados);
    	
    	//Parte de las tablas factura_compra_detalle
    	String[] fcdDatosRelacionados = new String[3];
    	fcdDatosRelacionados[0] = rs.getString("fcd_id");
    	fcdDatosRelacionados[1] = rs.getString("fcd_producto");
    	fcdDatosRelacionados[2] = rs.getString("fcd_lote");
    	stock.setFcdDatosRelacionados(fcdDatosRelacionados);
    	
    	
  	}catch(Exception e){
  		throw e;
  	}
    	return stock;
  }

  public static Object[] getStockLista(PaginatedListTest listaPaginada) 
  {
	String sqlA�adido = " ";
	Stock stock = new Stock();
	String sql = "SELECT st.*, fc.*, fcd.* FROM stock st " +
					" JOIN facturas_compra_detalle fcd ON st.st_id=fcd.fcd_st_id and fcd_id=( select fcd_id from facturas_compra_detalle where fcd_st_id=st_id order by fcd_f_entrada desc limit 1)"+
					" JOIN facturas_compra fc ON fc_id = fcd.fcd_fc_id"+
					" WHERE 1 = 1";
	
  	try{
		Stock stockFiltro = new Stock();
		if(listaPaginada.getRequest().getSession().getAttribute("FSTOCK")!=null){
			stockFiltro = (Stock)listaPaginada.getRequest().getSession().getAttribute("FSTOCK");
		}
    	if(!Utils.empty(stockFiltro.getStCantidadFinal()))
    		sqlA�adido += "AND " + STCANTIDADFINAL + " = '" + stockFiltro.getStCantidadFinal() + "'";
    	if(!Utils.empty(stockFiltro.getStCantidadInicial()))
    		sqlA�adido += "AND " + STCANTIDADINICIAL + " = '" + stockFiltro.getStCantidadInicial() + "'";
    	if(stockFiltro.getStFCaducidad()!=null)
    		sqlA�adido += "AND " + STFCADUCIDAD + " = '" + stockFiltro.getStFCaducidad() + "'";
    	if(!Utils.empty(stockFiltro.getStId()))
    		sqlA�adido += "AND " + STID + " = '" + stockFiltro.getStId() + "'";
    	if(!Utils.empty(stockFiltro.getStProdId()))
    		sqlA�adido += "AND " + STPRODID + " = '" + stockFiltro.getStProdId() + "'";
    	if(!Utils.empty(stockFiltro.getStRegularizacion()))
    		sqlA�adido += "AND " + STREGULARIZACION + " = '" + stockFiltro.getStRegularizacion() + "'";
    	if(!Utils.empty(stockFiltro.getStSalidas()))
    		sqlA�adido += "AND " + STSALIDAS + " = '" + stockFiltro.getStSalidas() + "'";
    	
    	if( stockFiltro.getStStockCero().equals("off") ){
    		sqlA�adido += "AND " + STCANTIDADFINAL + " > 0 ";
    	}
    	
    	
//    	Introducimos busqueda por rango de fechas
    	if( stockFiltro.getStFechaDesde()!=null && stockFiltro.getStFechaHasta()!=null )
    		sqlA�adido += "AND fc_fecha BETWEEN '"+stockFiltro.getStFechaDesde()+"' AND '"+stockFiltro.getStFechaHasta()+"' ";
    	else if( stockFiltro.getStFechaDesde()!=null ) 
    		sqlA�adido += "AND fc_fecha >= '" + stockFiltro.getStFechaDesde() + "'";
    	else if( stockFiltro.getStFechaHasta()!=null )
    		sqlA�adido += "AND fc_fecha <= '" + stockFiltro.getStFechaHasta() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, stock);
  }
}
