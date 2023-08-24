package panaderia.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.entidad.ActualizadorPreciosEntidad;
import panaderia.struts.forms.EntidadBean;

ver si actualiza en github
import utils.UtilesDAO;
import utils.Utils;

public class ActualizadorPrecios extends ActualizadorPreciosEntidad{
	  public static Object[] getActualizadorLista(PaginatedListTest listaPaginada) 
	  {
		String sqlA�adido = " ";
		String tipo = listaPaginada.getRequest().getParameter("actTipo");
		String cantidad = listaPaginada.getRequest().getParameter("actCantidad");
		cantidad = quitar_comas(cantidad);
		ActualizadorPrecios actualizador = new ActualizadorPrecios();
		if(tipo.equals("1")){ 
			tipo = " + (pcl_precio * ";
			cantidad = cantidad + "/100)";
		}else{
			tipo = "+";
		}
				
		String sql = "select distinct p.prod_nombre as producto, c.cl_nombre_comercial as cliente, pc.pcl_precio as precio, " + 
						"(pc.pcl_precio " + tipo + " " + cantidad + ") as actualizacion " +
						"from clientes c " +
						"join productos_cliente pc on pc.pcl_cl_id = c.cl_id " +
						"join productos p on pc.pcl_prod_id = p.prod_id " +
						"join entregas_detalle ed on ed.end_cl_id = c.cl_id " +
						"join rutas r ON ed.end_rut_id = r.rut_id";
	  	try{
	    	if(!Utils.empty(listaPaginada.getRequest().getParameter("clId")))
	    		sqlA�adido += "AND c.cl_id = " + listaPaginada.getRequest().getParameter("clId") + " ";
	    	if(!Utils.empty(listaPaginada.getRequest().getParameter("rutId")))
	    		sqlA�adido += "AND r.rut_id = " + listaPaginada.getRequest().getParameter("rutId") + " ";
	    	if(!Utils.empty(listaPaginada.getRequest().getParameter("locId")))
	    		sqlA�adido += "AND c.cl_loc_id = " + listaPaginada.getRequest().getParameter("locId") + " ";
	    	
	  	}catch(Exception e){
	  		System.out.println(e);
	  	}
	    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, actualizador);
	  }
	  
	  public static int actualizar(HttpServletRequest request) 
	  {
		  int n = -2;
		   
		  Connection conexion  = utils.PoolConexiones.conexion(); 
		  try
		    {
			  String sqlA�adido = " ";
				String tipo = request.getParameter("actTipo");
				String cantidad = request.getParameter("actCantidad");
				cantidad = quitar_comas(cantidad);
				if(tipo.equals("1")){ 
					tipo = " + (pcl_precio * ";
					cantidad = cantidad + "/100)";
				}else{
					tipo = "+";
				}
			  String sqlUpdate = "UPDATE productos_cliente SET pcl_precio = " + 
				"(pcl_precio " + tipo + " " + cantidad + ") " +
				"WHERE pcl_id in ( ";
			  
			  String sql = 
				"SELECT distinct pc.pcl_id " +
				"from clientes c " +
				"join productos_cliente pc on pc.pcl_cl_id = c.cl_id " +
				"join productos p on pc.pcl_prod_id = p.prod_id " +
				"join entregas_detalle ed on ed.end_cl_id = c.cl_id " +
				"join rutas r ON ed.end_rut_id = r.rut_id";
			  
				if(!Utils.empty(request.getParameter("clId")))
					sqlA�adido += "AND c.cl_id = " + request.getParameter("clId") + " ";
				if(!Utils.empty(request.getParameter("rutId")))
					sqlA�adido += "AND r.rut_id = " + request.getParameter("rutId") + " ";
				if(!Utils.empty(request.getParameter("locId")))
					sqlA�adido += "AND c.cl_loc_id = " + request.getParameter("locId") + " ";
				
				sql = sql + sqlA�adido;
				String cadenaIn = "";
				ArrayList listaIn = EntidadBean.consulta(sql);
				for(int i=0; i<listaIn.size(); i++){
					String[] obj = (String[])listaIn.get(i);
					cadenaIn = cadenaIn + obj[0] + ",";
				}
				cadenaIn = cadenaIn + "0)";
				
			  PreparedStatement sentencia = conexion.prepareStatement(sqlUpdate+cadenaIn);
		      n = sentencia.executeUpdate();
		      sentencia.close();
		  }
		  catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
		  finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
		  return n;
	  }
	  

	  protected Object rsToBean(ResultSet rs) throws Exception 
	  {
	    ActualizadorPrecios actualizadorPrecios = new ActualizadorPrecios();
	  	try{
	  		actualizadorPrecios.setCliente(rs.getString(CLIENTE));
	  		actualizadorPrecios.setPrecio(rs.getString(PRECIO));
	  		actualizadorPrecios.setProducto(rs.getString(PRODUCTO));
	  		actualizadorPrecios.setActualizacion(rs.getString(ACTUALIZACION));
	 
	    	
	  	}catch(Exception e){
	  		throw e;
	  	}
	    	return actualizadorPrecios;
	  }
}
