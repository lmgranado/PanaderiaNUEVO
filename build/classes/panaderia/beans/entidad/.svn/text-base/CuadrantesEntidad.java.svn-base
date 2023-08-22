package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class CuadrantesEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="cuadrantes";
  public static final String ALL_COLUMNS="cuadrantes.cu_cl_id,cuadrantes.cu_id,cuadrantes.cu_nombre,cuadrantes.cu_observaciones";
  public static final String CUCLID="cu_cl_id";
  public static final String CUID="cu_id";
  public static final String CUNOMBRE="cu_nombre";
  public static final String CUOBSERVACIONES="cu_observaciones";
  public static final String PRIMARY_KEY="cu_id";

  protected CuadrantesEntidad(){}

  public int actualiza()
  {
     
    Connection conexion  = utils.PoolConexiones.conexion(); 
    try
    {
      return actualiza(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }

  public int actualiza(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE cuadrantes SET cu_cl_id=?,cu_id=?,cu_nombre=?,cu_observaciones=? WHERE cu_id=?");
    sentencia.setString(1, cuClId);
    sentencia.setString(2, cuId);
    sentencia.setString(3, cuNombre);
    sentencia.setString(4, cuObservaciones);
    sentencia.setString(5, cuId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public int inserta()
  {
     
    Connection conexion  = utils.PoolConexiones.conexion(); 
    try
    {
      return inserta(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }

  public int inserta(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO cuadrantes (cu_cl_id,cu_nombre,cu_observaciones) VALUES (?,?,?)");
    sentencia.setString(1, cuClId);
    sentencia.setString(2, cuNombre);
    sentencia.setString(3, cuObservaciones);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public int elimina()
  {
     
    Connection conexion  = utils.PoolConexiones.conexion(); 
    try
    {
      return elimina(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
  }

  public int elimina(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM cuadrantes WHERE cu_id=?");
    sentencia.setString(1, cuId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "cuadrantes["+
      "cuClId:"+cuClId+", "+
      "cuId:"+cuId+", "+
      "cuNombre:"+cuNombre+", "+
      "cuObservaciones:"+cuObservaciones+" "+
    "]";
  }

  private String cuClId = "";
  public String getCuClId()
  {
    return cuClId;
  }
  public void setCuClId(String cuClId)
  {
    this.cuClId = cuClId;
  }

  private String cuId = "";
  public String getCuId()
  {
    return cuId;
  }
  public void setCuId(String cuId)
  {
    this.cuId = cuId;
    this.pk_identificador_ = cuId;
  }

  private String cuNombre = "";
  public String getCuNombre()
  {
    return cuNombre;
  }
  public void setCuNombre(String cuNombre)
  {
    this.cuNombre = cuNombre;
  }

  private String cuObservaciones = "";
  public String getCuObservaciones()
  {
    return cuObservaciones;
  }
  public void setCuObservaciones(String cuObservaciones)
  {
    this.cuObservaciones = cuObservaciones;
  }
  
  private String[] cuDatosRelacionados;
  public String[] getCuDatosRelacionados()
  {
	  return cuDatosRelacionados;
  }
  public void setCuDatosRelacionados(String[] cuDatosRelacionados)
  {
    this.cuDatosRelacionados = cuDatosRelacionados;
  }
}
