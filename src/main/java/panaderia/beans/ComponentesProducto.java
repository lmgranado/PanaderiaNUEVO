package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class ComponentesProducto extends panaderia.beans.entidad.ComponentesProductoEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ComponentesProducto(){ super(); }

  public static ComponentesProducto getComponentesProductoByCpId(String cpId)
  {
    ComponentesProducto elto = new ComponentesProducto();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM componentes_producto WHERE cp_id='"+cpId+"'");
    return elto;
  }

  public static ArrayList getComponentesProductoByCpProdIdFabricado(String cpProdIdFabricado, String unidades)
  {
	  unidades = Utils.skipNullNumero( unidades );
	  return EntidadBean.consulta("SELECT cp_prod_id_usado, cp_cantidad, cp_cantidad*"+ unidades +" as cantidad_total FROM componentes_producto WHERE cp_prod_id_fabricado='"+cpProdIdFabricado+"'");
  }
  
  public static ComponentesProducto getComponentesProductoByCpProdIdFabricadoAndUsado(String cpProdIdUsado, String cpProdIdFabricado)
  {
    ComponentesProducto elto = new ComponentesProducto();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM componentes_producto WHERE cp_prod_id_usado='"+cpProdIdUsado+"' AND  cp_prod_id_fabricado='"+cpProdIdFabricado+"'" );
    return elto;
  }

  public static ComponentesProducto getComponentesProductoByCpProdIdUsado(String cpProdIdUsado)
  {
    ComponentesProducto elto = new ComponentesProducto();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM componentes_producto WHERE cp_prod_id_usado='"+cpProdIdUsado+"'");
    return elto;
  }

  public static ArrayList getAllComponentesProducto()
  {
    ComponentesProducto elto = new ComponentesProducto();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM componentes_producto");
    return lista;
  }
  
  public static ArrayList getAllComponentesProductoByProdId( String cpProdIdFabricado )
  {
    ComponentesProducto elto = new ComponentesProducto();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM componentes_producto WHERE cp_prod_id_fabricado='"+cpProdIdFabricado+"'");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ComponentesProducto componentesproducto = new ComponentesProducto();
  	try{
    	componentesproducto.setCpCantidad(rs.getString(CPCANTIDAD));
    	componentesproducto.setCpId(rs.getString(CPID));
    	componentesproducto.setCpProdIdFabricado(rs.getString(CPPRODIDFABRICADO));
    	componentesproducto.setCpProdIdUsado(rs.getString(CPPRODIDUSADO));
    	
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[1];
    	clDatosRelacionados[0] = rs.getString("prod_nombre");
    	
    	componentesproducto.setClDatosRelacionados( clDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return componentesproducto;
  }

  public static Object[] getComponentesProductoLista(PaginatedListTest listaPaginada) 
  {
	String sqlA�adido = " ";
	ComponentesProducto componentesproducto = new ComponentesProducto();
	String sql = "SELECT * FROM componentes_producto cp " +
				 " JOIN productos p ON cp.cp_prod_id_usado=p.prod_id "+
				 " WHERE 1 = 1";
  	try{
		ComponentesProducto componentesproductoFiltro = new ComponentesProducto();
		if(listaPaginada.getRequest().getSession().getAttribute("FCOMPONENTESPRODUCTO")!=null){
			componentesproductoFiltro = (ComponentesProducto)listaPaginada.getRequest().getSession().getAttribute("FCOMPONENTESPRODUCTO");
		}
    	if(!Utils.empty(componentesproductoFiltro.getCpCantidad()))
    		sqlA�adido += "AND " + CPCANTIDAD + " = '" + componentesproductoFiltro.getCpCantidad() + "'";
    	if(!Utils.empty(componentesproductoFiltro.getCpId()))
    		sqlA�adido += "AND " + CPID + " = '" + componentesproductoFiltro.getCpId() + "'";
    	if(!Utils.empty(componentesproductoFiltro.getCpProdIdFabricado()))
    		sqlA�adido += "AND " + CPPRODIDFABRICADO + " = '" + componentesproductoFiltro.getCpProdIdFabricado() + "'";
    	if(!Utils.empty(componentesproductoFiltro.getCpProdIdUsado()))
    		sqlA�adido += "AND " + CPPRODIDUSADO + " = '" + componentesproductoFiltro.getCpProdIdUsado() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, componentesproducto);
  }
}
