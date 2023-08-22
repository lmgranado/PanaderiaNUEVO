package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;


import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;

import javax.ejb.EnterpriseBean;
public class EntregasDetalle extends panaderia.beans.entidad.EntregasDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public EntregasDetalle(){ super(); }

  public static int recalculaOrdenes( EntregasDetalle entregasDetalle, boolean insertar )
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion();
	  String sql = "update entregas_detalle set end_orden=end_orden-1 where end_ent_id=? and end_orden>? order by end_orden asc";
	  String orden = "asc";
	  int n = -1;
	  try{
		  if( existeOrden(entregasDetalle) ){
				  orden = "desc";
				  sql = "update entregas_detalle set end_orden=end_orden+1 where end_ent_id=? and end_orden>=? order by end_orden "+ orden;
		  }
		  PreparedStatement sentencia = conexion.prepareStatement(sql);
		  sentencia.setString(1, entregasDetalle.getEndEntId() );
		  sentencia.setString(2, entregasDetalle.getEndOrden() );
		      
		  n = sentencia.executeUpdate();
		  sentencia.close();
		  
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static boolean existeOrden( EntregasDetalle entregasDetalle )
  {
	   
	  ResultSet rs = null;
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  boolean existe = false;
	  try{
		 	  
	      PreparedStatement sentencia = conexion.prepareStatement("SELECT end_id FROM entregas_detalle "
	      +" WHERE end_ent_id=? and end_orden=?");
	      sentencia.setString(1, entregasDetalle.getEndEntId());
	      sentencia.setString(2, entregasDetalle.getEndOrden());
	      
	      rs = sentencia.executeQuery();
	      if( rs.next() )
	    	  existe = true;
	      
	      sentencia.close();
	  }catch( SQLException e ){
		  utils.Log4j.error("Error", e); 
		  return existe; 
	  }
	  finally{ 
		  utils.PoolConexiones.cerrarConexion(conexion); 
	  }
	    
	  return existe;
  }
  
  public static EntregasDetalle getEntregasDetalleByEntClId(String entClId)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE ent_cl_id='"+entClId+"'");
    return elto;
  }

  public static EntregasDetalle getEntregasDetalleByEntCuId(String entCuId)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE ent_cu_id='"+entCuId+"'");
    return elto;
  }
  
  
  public static int eliminaDetalles( String endEntId ) 
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  int n = -1;
	  try{
	      PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM entregas_detalle WHERE end_ent_id=?");
	      sentencia.setString(1, endEntId);
	      n = sentencia.executeUpdate();
	      sentencia.close();
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }

  public static EntregasDetalle getEntregasDetalleByEndId(String entId)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE end_id='"+entId+"'");
    return elto;
  }

  /*public static EntregasDetalle getEntregasDetalleByEntNombre(String entNombre)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE ent_nombre='"+entNombre+"'");
    return elto;
  }

  public static EntregasDetalle getEntregasDetalleByEntRutId(String entRutId)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE ent_rut_id='"+entRutId+"'");
    return elto;
  }

  public static EntregasDetalle getEntregasDetalleByEntViaje(String entViaje)
  {
    EntregasDetalle elto = new EntregasDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle WHERE ent_viaje='"+entViaje+"'");
    return elto;
  }*/

  public static ArrayList getAllEntregasDetalle()
  {
    EntregasDetalle elto = new EntregasDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas_detalle");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    EntregasDetalle entregas = new EntregasDetalle();
  	try{
    	entregas.setEndClId(rs.getString(ENDCLID));
    	entregas.setEndCuId(rs.getString(ENDCUID));
    	entregas.setEndId(rs.getString(ENDID));
    	entregas.setEndOrden(rs.getString(ENDORDEN));
    	entregas.setEndRutId(rs.getString(ENDRUTID));
    	entregas.setEndViaje(rs.getString(ENDVIAJE));
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[3];
    	clDatosRelacionados[0] = rs.getString("rut_nombre");
    	clDatosRelacionados[1] = rs.getString("cl_nombre_comercial");
    	clDatosRelacionados[2] = rs.getString("cu_nombre");
    	entregas.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return entregas;
  }

  public static Object[] getEntregasDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	EntregasDetalle entregas = new EntregasDetalle();
	String sql = "SELECT entregas_detalle.*, rut_nombre, cl_nombre_comercial, cu_nombre " +
				 "FROM entregas_detalle " +
				 "JOIN clientes ON cl_id = end_cl_id " + 
				 "JOIN cuadrantes ON cu_id = end_cu_id " +
				 "JOIN rutas ON rut_id = end_rut_id " +
				 "JOIN entregas ON ent_id = end_ent_id " +
				 "WHERE 1 = 1 and cl_activo=1";
  	try{
		EntregasDetalle entregasFiltro = new EntregasDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FENTREGASDETALLE")!=null){
			entregasFiltro = (EntregasDetalle)listaPaginada.getRequest().getSession().getAttribute("FENTREGASDETALLE");
		}
    	if(!Utils.empty(entregasFiltro.getEndClId()))
    		sqlAñadido += "AND " + ENDCLID + " = '" + entregasFiltro.getEndClId() + "'";
    	if(!Utils.empty(entregasFiltro.getEndCuId()))
    		sqlAñadido += "AND " + ENDCUID + " = '" + entregasFiltro.getEndCuId() + "'";
    	if(!Utils.empty(entregasFiltro.getEndId()))
    		sqlAñadido += "AND " + ENDID + " = '" + entregasFiltro.getEndId() + "'";
    	if(!Utils.empty(entregasFiltro.getEndOrden()))
    		sqlAñadido += "AND " + ENDORDEN + " = '" + entregasFiltro.getEndOrden() + "'";
    	if(!Utils.empty(entregasFiltro.getEndRutId()))
    		sqlAñadido += "AND " + ENDRUTID + " = '" + entregasFiltro.getEndRutId() + "'";
    	if(!Utils.empty(entregasFiltro.getEndViaje()))
    		sqlAñadido += "AND " + ENDVIAJE + " = '" + entregasFiltro.getEndViaje() + "'";
    	if(!Utils.empty(entregasFiltro.getEndEntId()))
    		sqlAñadido += "AND " + ENDENTID + " = '" + entregasFiltro.getEndEntId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, entregas);
  }
}
