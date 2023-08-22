package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;


import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class ComposicionProductos extends panaderia.beans.entidad.ComposicionProductosEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ComposicionProductos(){ super(); }

  public static int eliminaAllComposicionProductosByComprodFprodId( String comprodFprodId ) 
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  int n = -1;
	  try{
	      PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM composicion_productos WHERE comprod_fprod_id=?");
	      sentencia.setString(1, comprodFprodId);
	      n = sentencia.executeUpdate();
	      sentencia.close();
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static ComposicionProductos getComposicionProductosByComprodId(String comprodId)
  {
    ComposicionProductos elto = new ComposicionProductos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_productos WHERE comprod_id='"+comprodId+"'");
    return elto;
  }

  public static ArrayList getAllComposicionProductos()
  {
    ComposicionProductos elto = new ComposicionProductos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_productos");
    return lista;
  }

  public static ArrayList getAllComposicionProductosByComprodFprodId( String fprodId )
  {
    ComposicionProductos elto = new ComposicionProductos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_productos where comprod_fprod_id="+fprodId);
    return lista;
  }
  
  
  public static ComposicionProductos getComposicionProductosByComprodFprodId(String comprodFprodId)
  {
    ComposicionProductos elto = new ComposicionProductos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM composicion_productos WHERE comprod_fprod_id='"+comprodFprodId+"'");
    return elto;
  }
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ComposicionProductos composicionproductos = new ComposicionProductos();
  	try{
    	composicionproductos.setComprodDescripcion(rs.getString(COMPRODDESCRIPCION));
    	composicionproductos.setComprodFprodId(rs.getString(COMPRODFPRODID));
    	composicionproductos.setComprodId(rs.getString(COMPRODID));
    	composicionproductos.setComprodMedida(rs.getString(COMPRODMEDIDA));
    	composicionproductos.setComprodNombre(rs.getString(COMPRODNOMBRE));
    	composicionproductos.setComprodPeso(rs.getString(COMPRODPESO));
  	}catch(Exception e){
  		throw e;
  	}
    	return composicionproductos;
  }

  public static Object[] getComposicionProductosLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	ComposicionProductos composicionproductos = new ComposicionProductos();
	String sql = "SELECT * FROM composicionproductos " +
				 " WHERE 1 = 1 ";
  	try{
		ComposicionProductos composicionproductosFiltro = new ComposicionProductos();
		if(listaPaginada.getRequest().getSession().getAttribute("FCOMPOSICIONPRODUCTOS")!=null){
			composicionproductosFiltro = (ComposicionProductos)listaPaginada.getRequest().getSession().getAttribute("FCOMPOSICIONPRODUCTOS");
		}
    	if(!Utils.empty(composicionproductosFiltro.getComprodDescripcion()))
    		sqlAñadido += "AND " + COMPRODDESCRIPCION + " = '" + composicionproductosFiltro.getComprodDescripcion() + "'";
    	if(!Utils.empty(composicionproductosFiltro.getComprodFprodId()))
    		sqlAñadido += "AND " + COMPRODFPRODID + " = '" + composicionproductosFiltro.getComprodFprodId() + "'";
    	if(!Utils.empty(composicionproductosFiltro.getComprodId()))
    		sqlAñadido += "AND " + COMPRODID + " = '" + composicionproductosFiltro.getComprodId() + "'";
    	if(!Utils.empty(composicionproductosFiltro.getComprodMedida()))
    		sqlAñadido += "AND " + COMPRODMEDIDA + " = '" + composicionproductosFiltro.getComprodMedida() + "'";
    	if(!Utils.empty(composicionproductosFiltro.getComprodNombre()))
    		sqlAñadido += "AND " + COMPRODNOMBRE + " = '" + composicionproductosFiltro.getComprodNombre() + "'";
    	if(!Utils.empty(composicionproductosFiltro.getComprodPeso()))
    		sqlAñadido += "AND " + COMPRODPESO + " = '" + composicionproductosFiltro.getComprodPeso() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, composicionproductos);
  }
}
