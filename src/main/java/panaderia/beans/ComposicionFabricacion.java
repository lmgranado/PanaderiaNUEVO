package panaderia.beans;
import java.sql.ResultSet;

import org.displaytag.pagination.PaginatedListTest;

import utils.UtilesDAO;
import utils.Utils;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
public class ComposicionFabricacion extends panaderia.beans.entidad.ComposicionFabricacionEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ComposicionFabricacion(){ super(); }

  public static ComposicionFabricacion getComposicionFabricacionByCfId(String cfId)
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_fabricacion WHERE cf_id='"+cfId+"'");
    return elto;
  }

  
 /* public static ArrayList getComposicionFabricacionByCfIdStFabricado(String cfIdStFabricado)
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+",prod_nombre as nombre_prod_usado, fcd.fcd_lote as lote FROM composicion_fabricacion " +
										    		"JOIN stock ON st_id = cf_id_st_usado " +						    				
										    		"JOIN productos ON prod_id = st_prod_id " +
										    		"JOIN facturas_compra_detalle fcd on fcd.fcd_st_id = cf_id_st_usado " +
						    						"WHERE cf_id_st_fabricado='"+cfIdStFabricado+"'");
    return lista;
  }*/
  
  
  //CLASE CAMBIADA POR LA DE ARRIBA PARA QUE PILLASE BIEN LOS PRODUCTOS DE LA COMPOSICION
  public static ArrayList getComposicionFabricacionByCfIdStFabricado(String cfIdStFabricado)
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+",prod_nombre as nombre_prod_usado, stock.st_prod_lote as lote FROM composicion_fabricacion " +
										    		"JOIN stock ON st_id = cf_id_st_usado " +						    				
										    		"JOIN productos ON prod_id = st_prod_id " +
										    		//"JOIN facturas_compra_detalle fcd on fcd.fcd_st_id = cf_id_st_usado " +
						    						"WHERE cf_id_st_fabricado='"+cfIdStFabricado+"'");
    return lista;
  }
  
  //LUIS MIGUEL -> 060814 - TOMA CADA PRODUCTO DE LA TABLA COMPOSICION FABRICACION PARA ACTUALIZARLE LOS CAMPOS
  public static ComposicionFabricacion getComposicionFabricacionByCfIdStFabricadoyCfIdStUsado(String cfIdStFabricado, String cfIdStUsado)
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+",prod_nombre as nombre_prod_usado, fcd.fcd_lote as lote FROM composicion_fabricacion " +
										    		"JOIN stock ON st_id = cf_id_st_usado " +						    				
										    		"JOIN productos ON prod_id = st_prod_id " +
										    		"JOIN facturas_compra_detalle fcd on fcd.fcd_st_id = cf_id_st_usado " +
						    						"WHERE cf_id_st_fabricado='"+cfIdStFabricado+"' and cf_id_st_usado='" +cfIdStUsado + "'");
    return elto;
  }
  

  public static ArrayList getComposicionFabricacionListaStUsadosByStidFabricado( String cfIdStFabricado ){
	 
	  ArrayList lista = ComposicionFabricacion.consulta("SELECT "+CFIDSTUSADO+" FROM composicion_fabricacion WHERE cf_id_st_fabricado='"+cfIdStFabricado+"'");
	  return lista;
  }
  
  public static ComposicionFabricacion getComposicionFabricacionByCfIdStUsado(String cfIdStUsado)
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_fabricacion WHERE cf_id_st_usado='"+cfIdStUsado+"'");
    return elto;
  }

  public static ArrayList getAllComposicionFabricacion()
  {
    ComposicionFabricacion elto = new ComposicionFabricacion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_fabricacion");
    return lista;
  }
  
  public static boolean tieneLosMismosComponentes( HttpServletRequest request, int stId )
  {
	  	boolean mismosComponentes = true;
	  	boolean encontrado = false;
	  	//Tomamos los stIdUsados del Producto existente en el stock para comprobar que 
	  	//son iguales los lotes con los nuevos utilizados
	  	ArrayList stUsadosExistentes = ComposicionFabricacion.getComposicionFabricacionListaStUsadosByStidFabricado( String.valueOf(stId) );
	  	
	  	Enumeration enumera = request.getParameterNames();
	  	while( enumera.hasMoreElements() && mismosComponentes ){
	  		String nombrePS = (String)enumera.nextElement();
	  		encontrado = false;
	  		
    		if( nombrePS.indexOf("producto_") != -1 ){
    			
    			Stock stockElegido = Stock.getStockByStId( request.getParameter(nombrePS) );
    			//En cuanto detectemos que se utiliza un producto con un lote diferente a los existentes terminamos
    			for( int i=0; i<stUsadosExistentes.size() && !encontrado; i++){
    				String [] valor = (String[]) stUsadosExistentes.get(i);
    				if( valor[0].equals(stockElegido.getStId()) )
    					encontrado = true;    				
    					
    			}
    				
    			if( !encontrado )
        			mismosComponentes = false;
    		}
    		
    		
	  	}
	  	
    return mismosComponentes;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ComposicionFabricacion composicionfabricacion = new ComposicionFabricacion();
  	try{
    	composicionfabricacion.setCfCantidad(rs.getString(CFCANTIDAD));
    	composicionfabricacion.setCfId(rs.getString(CFID));
    	composicionfabricacion.setCfIdStFabricado(rs.getString(CFIDSTFABRICADO));
    	composicionfabricacion.setCfIdStUsado(rs.getString(CFIDSTUSADO));
  	}catch(Exception e){
  		throw e;
  	}
    	return composicionfabricacion;
  }

  public static Object[] getComposicionFabricacionLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	ComposicionFabricacion composicionfabricacion = new ComposicionFabricacion();
	String sql = "SELECT * FROM composicionfabricacion " +
				 " WHERE 1 = 1 ";
  	try{
		ComposicionFabricacion composicionfabricacionFiltro = new ComposicionFabricacion();
		if(listaPaginada.getRequest().getSession().getAttribute("FCOMPOSICIONFABRICACION")!=null){
			composicionfabricacionFiltro = (ComposicionFabricacion)listaPaginada.getRequest().getSession().getAttribute("FCOMPOSICIONFABRICACION");
		}
    	if(!Utils.empty(composicionfabricacionFiltro.getCfCantidad()))
    		sqlAñadido += "AND " + CFCANTIDAD + " = '" + composicionfabricacionFiltro.getCfCantidad() + "'";
    	if(!Utils.empty(composicionfabricacionFiltro.getCfId()))
    		sqlAñadido += "AND " + CFID + " = '" + composicionfabricacionFiltro.getCfId() + "'";
    	if(!Utils.empty(composicionfabricacionFiltro.getCfIdStFabricado()))
    		sqlAñadido += "AND " + CFIDSTFABRICADO + " = '" + composicionfabricacionFiltro.getCfIdStFabricado() + "'";
    	if(!Utils.empty(composicionfabricacionFiltro.getCfIdStUsado()))
    		sqlAñadido += "AND " + CFIDSTUSADO + " = '" + composicionfabricacionFiltro.getCfIdStUsado() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, composicionfabricacion);
  }
}
