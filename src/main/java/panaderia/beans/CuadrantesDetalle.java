package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;


import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class CuadrantesDetalle extends panaderia.beans.entidad.CuadrantesDetalleEntidad
{
  private static final long serialVersionUID = 1L;
  
  public CuadrantesDetalle(){ super(); }

  public static CuadrantesDetalle getCuadrantesDetalleByCudCuId(String cudCuId)
  {
    CuadrantesDetalle elto = new CuadrantesDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes_detalle WHERE cud_cu_id='"+cudCuId+"'");
    return elto;
  }

  public static CuadrantesDetalle getCuadrantesDetalleByCudId(String cudId)
  {
    CuadrantesDetalle elto = new CuadrantesDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes_detalle WHERE cud_id='"+cudId+"'");
    return elto;
  }

  public static CuadrantesDetalle getCuadrantesDetalleByCudPclId(String cudPclId)
  {
    CuadrantesDetalle elto = new CuadrantesDetalle();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes_detalle WHERE cud_pcl_id='"+cudPclId+"'");
    return elto;
  }

  public static ArrayList getAllCuadrantesDetalle()
  {
    CuadrantesDetalle elto = new CuadrantesDetalle();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes_detalle");
    return lista;
  }
  

  public static int copiaCuadrante(String idCuadrante, String nombreCopiar, String obsCopiar)
  {
     
    Connection conexion  = utils.PoolConexiones.conexion(); 
    try
    {
    	PreparedStatement sentencia = conexion.prepareStatement("insert into cuadrantes (cu_nombre, cu_cl_id, cu_observaciones) " +
																"select '" + nombreCopiar + "', cu_cl_id, '" + obsCopiar + "' from cuadrantes " + 
																"where cu_id = '" + idCuadrante + "'");
        int n = sentencia.executeUpdate();
        int autoIncKeyFromApi = -1;
    	
	    ResultSet rs = sentencia.getGeneratedKeys();
	
	    if (rs.next()) {
	        autoIncKeyFromApi = rs.getInt(1);
	    } else {
	
	        // throw an exception from here
	    }
        sentencia.close();
        
        //se ha creado correctamente el cuadrante, ahora vamos a por los detalles
        if(n>0){
        	sentencia = conexion.prepareStatement("insert into cuadrantes_detalle (cud_cu_id, cud_pcl_id, cud_cantidad1, cud_cantidad2, cud_cantidad3, cud_cantidad4, cud_cantidad5) " +
									        	  "select " + autoIncKeyFromApi + " ,cud_pcl_id, cud_cantidad1, cud_cantidad2, cud_cantidad3, cud_cantidad4, cud_cantidad5 " +
									        	  "from cuadrantes_detalle where cud_cu_id ='" + idCuadrante + "'");
			n = sentencia.executeUpdate();
			sentencia.close();
			
        }
        
        return n;
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }


  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    CuadrantesDetalle cuadrantesdetalle = new CuadrantesDetalle();
  	try{
    	cuadrantesdetalle.setCudCantidad1(rs.getString(CUDCANTIDAD1));
    	cuadrantesdetalle.setCudCantidad2(rs.getString(CUDCANTIDAD2));
    	cuadrantesdetalle.setCudCantidad3(rs.getString(CUDCANTIDAD3));
    	cuadrantesdetalle.setCudCantidad4(rs.getString(CUDCANTIDAD4));
    	cuadrantesdetalle.setCudCantidad5(rs.getString(CUDCANTIDAD5));
    	cuadrantesdetalle.setCudCuId(rs.getString(CUDCUID));
    	cuadrantesdetalle.setCudId(rs.getString(CUDID));
    	cuadrantesdetalle.setCudPclId(rs.getString(CUDPCLID));
//    	Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[5];
    	clDatosRelacionados[0] = rs.getString("prod_nombre");
    	cuadrantesdetalle.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return cuadrantesdetalle;
  }

  public static Object[] getCuadrantesDetalleLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	CuadrantesDetalle cuadrantesdetalle = new CuadrantesDetalle();
	String sql = "SELECT cuadrantes_detalle.*, productos.prod_nombre FROM cuadrantes_detalle " +
				 "JOIN productos_cliente ON cud_pcl_id = pcl_id " +
				 "JOIN productos ON prod_id = pcl_prod_id " +
				 "WHERE 1 = 1 ";
  	try{
		CuadrantesDetalle cuadrantesdetalleFiltro = new CuadrantesDetalle();
		if(listaPaginada.getRequest().getSession().getAttribute("FCUADRANTESDETALLE")!=null){
			cuadrantesdetalleFiltro = (CuadrantesDetalle)listaPaginada.getRequest().getSession().getAttribute("FCUADRANTESDETALLE");
		}
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCantidad1()))
    		sqlAñadido += "AND " + CUDCANTIDAD1 + " = '" + cuadrantesdetalleFiltro.getCudCantidad1() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCantidad2()))
    		sqlAñadido += "AND " + CUDCANTIDAD2 + " = '" + cuadrantesdetalleFiltro.getCudCantidad2() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCantidad3()))
    		sqlAñadido += "AND " + CUDCANTIDAD3 + " = '" + cuadrantesdetalleFiltro.getCudCantidad3() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCantidad4()))
    		sqlAñadido += "AND " + CUDCANTIDAD4 + " = '" + cuadrantesdetalleFiltro.getCudCantidad4() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCantidad5()))
    		sqlAñadido += "AND " + CUDCANTIDAD5 + " = '" + cuadrantesdetalleFiltro.getCudCantidad5() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudCuId()))
    		sqlAñadido += "AND " + CUDCUID + " = '" + cuadrantesdetalleFiltro.getCudCuId() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudId()))
    		sqlAñadido += "AND " + CUDID + " = '" + cuadrantesdetalleFiltro.getCudId() + "'";
    	if(!Utils.empty(cuadrantesdetalleFiltro.getCudPclId()))
    		sqlAñadido += "AND " + CUDPCLID + " = '" + cuadrantesdetalleFiltro.getCudPclId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, cuadrantesdetalle);
  }
}
