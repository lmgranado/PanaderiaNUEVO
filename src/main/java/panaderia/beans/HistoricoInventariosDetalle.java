package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import utils.UtilsFacturacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
public class HistoricoInventariosDetalle extends panaderia.beans.entidad.HistoricoInventariosDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public HistoricoInventariosDetalle(){ super(); }

  public static HistoricoInventariosDetalle getHistoricoInventariosDetalleByHinvdId(String hinvdId)
  {
    HistoricoInventariosDetalle elto = new HistoricoInventariosDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios_detalle WHERE hinvd_id='"+hinvdId+"'");
    return elto;
  }
  
  public static ArrayList getHistoricoInventariosDetalleByHinvdHinvId( String hinvId )
  {
    HistoricoInventariosDetalle elto = new HistoricoInventariosDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios_detalle WHERE hinvd_hinv_id='"+hinvId+"'");
    return lista;
  }

  public static HistoricoInventariosDetalle getHistoricoInventariosDetalleByHinvdProdId(String hinvdProdId)
  {
    HistoricoInventariosDetalle elto = new HistoricoInventariosDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios_detalle WHERE hinvd_prod_id='"+hinvdProdId+"'");
    return elto;
  }

  public static ArrayList getAllHistoricoInventariosDetalle()
  {
    HistoricoInventariosDetalle elto = new HistoricoInventariosDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios_detalle");
    return lista;
  }
  
  public static int anadeDetalleInventario( HttpServletRequest request ) throws Exception 
  {
	  int resultado = -2;
	  	try{
	  		String hinvId = request.getParameter("hinvId");
	  		String hinvdProdId = request.getParameter("prodId");
	  		String stId = request.getParameter("hinvdLote");
	  		String hinvdRecuento = request.getParameter("hinvdRecuento");
	  		String sCantidad = request.getParameter("stCantidad");
	  		
	  		float regularizacion = Float.parseFloat(quitar_comas(Utils.skipNull( hinvdRecuento ))) - Float.parseFloat(quitar_comas(Utils.skipNull( sCantidad) ) );
	  		FacturasCompraDetalle facturacompradetalle = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStIdDisctictLote( stId );
	  		
	  		HistoricoInventariosDetalle detalle = new HistoricoInventariosDetalle();

	  		detalle.setHinvdHinvId( hinvId );
	  		detalle.setHinvdProdId( hinvdProdId );
	  		detalle.setHinvdNombreProducto( Productos.getProductosByProdId( hinvdProdId ).getProdNombre() );
	  		detalle.setHinvdLote( facturacompradetalle.getFcdLote() );
	  		detalle.setHinvdRecuento( hinvdRecuento );
	  		detalle.setHinvdRegularizacion( String.valueOf(regularizacion) );
	  		
	  		detalle.inserta();
	  		
	  		//Actualizamos la tabla Stock con la cantidad final correcta y su regularizacion
	  		Stock stock = Stock.getStockByStId( stId );
	  		stock.setStCantidadFinal( hinvdRecuento );
	  		stock.setStRegularizacion( String.valueOf(regularizacion) );
	  		stock.actualiza();
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }
  
  public static int eliminaDetalleInventario( HttpServletRequest request, String[] chkValues ) throws Exception 
  {
	  int resultado = -2;
	  	try{
	  		
	  		HistoricoInventariosDetalle detalle = null;
			for(int i=0; i<chkValues.length; i++){
				
				detalle = HistoricoInventariosDetalle.getHistoricoInventariosDetalleByHinvdId( chkValues[i] );
				
				FacturasCompraDetalle facturacompradetalle = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStIdDisctictProductoLote( detalle.getHinvdNombreProducto(), detalle.getHinvdLote() );
				
		  		//Actualizamos la tabla Stock con la cantidad final correcta y su regularizacion
		  		Stock stock = Stock.getStockByStId( facturacompradetalle.getFcdStId() );
		  		float stCantidadFinal = Float.parseFloat(quitar_comas(Utils.skipNull( stock.getStCantidadFinal() ))) - Float.parseFloat(quitar_comas(Utils.skipNull( detalle.getHinvdRegularizacion()) ) );
		  		float stRegularizacion = Float.parseFloat(quitar_comas(Utils.skipNull( stock.getStRegularizacion() ))) - Float.parseFloat(quitar_comas(Utils.skipNull( detalle.getHinvdRegularizacion()) ) );
		  		stock.setStCantidadFinal( String.valueOf( stCantidadFinal ) );
		  		stock.setStRegularizacion( String.valueOf( stRegularizacion ) );
		  		stock.actualiza();
		  		
		  		//Eliminamos los marcados de nuestra lista de detalles
				resultado = detalle.elimina();		
			}
			
			
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
    	return resultado;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    HistoricoInventariosDetalle historicoinventariosdetalle = new HistoricoInventariosDetalle();
  	try{
    	historicoinventariosdetalle.setHinvdHinvId(rs.getString(HINVDHINVID));
    	historicoinventariosdetalle.setHinvdId(rs.getString(HINVDID));
    	historicoinventariosdetalle.setHinvdLote(rs.getString(HINVDLOTE));
    	historicoinventariosdetalle.setHinvdNombreProducto(rs.getString(HINVDNOMBREPRODUCTO));
    	historicoinventariosdetalle.setHinvdObservaciones(rs.getString(HINVDOBSERVACIONES));
    	historicoinventariosdetalle.setHinvdProdId(rs.getString(HINVDPRODID));
    	historicoinventariosdetalle.setHinvdRecuento(rs.getString(HINVDRECUENTO));
    	historicoinventariosdetalle.setHinvdRegularizacion(rs.getString(HINVDREGULARIZACION));
  	}catch(Exception e){
  		throw e;
  	}
    	return historicoinventariosdetalle;
  }

  public static Object[] getHistoricoInventariosDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	HistoricoInventariosDetalle historicoinventariosdetalle = new HistoricoInventariosDetalle();
	String sql = "SELECT * FROM historico_inventarios_detalle " +
				 " WHERE 1 = 1 ";
  	try{
		HistoricoInventariosDetalle historicoinventariosdetalleFiltro = new HistoricoInventariosDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FHISTORICO_INVENTARIOS_DETALLE")!=null){
			historicoinventariosdetalleFiltro = (HistoricoInventariosDetalle)listaPaginada.getRequest().getSession().getAttribute("FHISTORICO_INVENTARIOS_DETALLE");
		}
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdHinvId()))
    		sqlAñadido += "AND " + HINVDHINVID + " = '" + historicoinventariosdetalleFiltro.getHinvdHinvId() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdId()))
    		sqlAñadido += "AND " + HINVDID + " = '" + historicoinventariosdetalleFiltro.getHinvdId() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdLote()))
    		sqlAñadido += "AND " + HINVDLOTE + " = '" + historicoinventariosdetalleFiltro.getHinvdLote() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdNombreProducto()))
    		sqlAñadido += "AND " + HINVDNOMBREPRODUCTO + " = '" + historicoinventariosdetalleFiltro.getHinvdNombreProducto() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdObservaciones()))
    		sqlAñadido += "AND " + HINVDOBSERVACIONES + " = '" + historicoinventariosdetalleFiltro.getHinvdObservaciones() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdProdId()))
    		sqlAñadido += "AND " + HINVDPRODID + " = '" + historicoinventariosdetalleFiltro.getHinvdProdId() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdRecuento()))
    		sqlAñadido += "AND " + HINVDRECUENTO + " = '" + historicoinventariosdetalleFiltro.getHinvdRecuento() + "'";
    	if(!Utils.empty(historicoinventariosdetalleFiltro.getHinvdRegularizacion()))
    		sqlAñadido += "AND " + HINVDREGULARIZACION + " = '" + historicoinventariosdetalleFiltro.getHinvdRegularizacion() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, historicoinventariosdetalle);
  }
}
