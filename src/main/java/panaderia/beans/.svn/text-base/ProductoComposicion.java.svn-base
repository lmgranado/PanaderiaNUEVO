package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;


import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class ProductoComposicion extends panaderia.beans.entidad.ProductoComposicionEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ProductoComposicion(){ super(); }

  public static ArrayList getAllComposicionProductosByProdCompprodFId( String prodcompProdfId )
  {
    ProductoComposicion elto = new ProductoComposicion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_composicion where prodcomp_prodf_id="+prodcompProdfId);
    return lista;
  }
  
  public static ProductoComposicion getProductoComposicionByProdcompId(String prodcompId)
  {
    ProductoComposicion elto = new ProductoComposicion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_composicion WHERE prodcomp_id='"+prodcompId+"'");
    return elto;
  }

  public static ProductoComposicion getProductoComposicionByProdcompProdfId(String prodcompProdfId)
  {
    ProductoComposicion elto = new ProductoComposicion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_composicion WHERE prodcomp_prodf_id='"+prodcompProdfId+"'");
    return elto;
  }

  public static ArrayList getAllProductoComposicion()
  {
    ProductoComposicion elto = new ProductoComposicion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_composicion");
    return lista;
  }
  
  public static ArrayList getConversorComposicionProductosAProductoComposicion( String fprodId )
  {
	  ProductoComposicion elto = new ProductoComposicion();
	  ArrayList lista = elto.consultaAVectorReflexiva( "SELECT "+ALL_COLUMNS_CONVERSOR+" FROM composicion_productos WHERE comprod_fprod_id='"+fprodId+"'");
	  
	  return lista;
  }
  
  public static int eliminaComposicionByProdCompprodFId( String prodcompProdfId )
  {
  	   
  	  Connection conexion  = utils.PoolConexiones.conexion();
  	  String sql = "DELETE FROM producto_composicion where prodcomp_prodf_id=?";
  	  int n = -1;
  	  try{
  		  PreparedStatement sentencia = conexion.prepareStatement(sql);
  		  sentencia.setString(1, prodcompProdfId );
  		      
  		  n = sentencia.executeUpdate();
  		  sentencia.close();
  		  
  	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
  	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
  	    
  	  return n;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ProductoComposicion productocomposicion = new ProductoComposicion();
  	try{
    	productocomposicion.setProdcompDescripcion(rs.getString(PRODCOMPDESCRIPCION));
    	productocomposicion.setProdcompId(rs.getString(PRODCOMPID));
    	productocomposicion.setProdcompMedida(rs.getString(PRODCOMPMEDIDA));
    	productocomposicion.setProdcompNombre(rs.getString(PRODCOMPNOMBRE));
    	productocomposicion.setProdcompPeso(rs.getString(PRODCOMPPESO));
    	productocomposicion.setProdcompProdfId(rs.getString(PRODCOMPPRODFID));
  	}catch(Exception e){
  		throw e;
  	}
    	return productocomposicion;
  }

  public static Object[] getProductoComposicionLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	ProductoComposicion productocomposicion = new ProductoComposicion();
	String sql = "SELECT * FROM producto_composicion " +
				 " WHERE 1 = 1 ";
  	try{
		ProductoComposicion productocomposicionFiltro = new ProductoComposicion();
		if(listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOCOMPOSICION")!=null){
			productocomposicionFiltro = (ProductoComposicion)listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOCOMPOSICION");
		}
    	if(!Utils.empty(productocomposicionFiltro.getProdcompDescripcion()))
    		sqlAñadido += "AND " + PRODCOMPDESCRIPCION + " = '" + productocomposicionFiltro.getProdcompDescripcion() + "'";
    	if(!Utils.empty(productocomposicionFiltro.getProdcompId()))
    		sqlAñadido += "AND " + PRODCOMPID + " = '" + productocomposicionFiltro.getProdcompId() + "'";
    	if(!Utils.empty(productocomposicionFiltro.getProdcompMedida()))
    		sqlAñadido += "AND " + PRODCOMPMEDIDA + " = '" + productocomposicionFiltro.getProdcompMedida() + "'";
    	if(!Utils.empty(productocomposicionFiltro.getProdcompNombre()))
    		sqlAñadido += "AND " + PRODCOMPNOMBRE + " = '" + productocomposicionFiltro.getProdcompNombre() + "'";
    	if(!Utils.empty(productocomposicionFiltro.getProdcompPeso()))
    		sqlAñadido += "AND " + PRODCOMPPESO + " = '" + productocomposicionFiltro.getProdcompPeso() + "'";
    	if(!Utils.empty(productocomposicionFiltro.getProdcompProdfId()))
    		sqlAñadido += "AND " + PRODCOMPPRODFID + " = '" + productocomposicionFiltro.getProdcompProdfId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, productocomposicion);
  }
}
