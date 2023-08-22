package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class RutasEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="rutas";
  public static final String ALL_COLUMNS="rutas.rut_id,rutas.rut_nombre";
  public static final String RUTID="rut_id";
  public static final String RUTNOMBRE="rut_nombre";
  public static final String PRIMARY_KEY="rut_id";

  protected RutasEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE rutas SET rut_id=?,rut_nombre=? WHERE rut_id=?");
    sentencia.setString(1, rutId);
    sentencia.setString(2, rutNombre);
    sentencia.setString(3, rutId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO rutas (rut_nombre) VALUES (?)");
    //sentencia.setString(1, rutId);
    sentencia.setString(1, rutNombre);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM rutas WHERE rut_id=?");
    sentencia.setString(1, rutId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "rutas["+
      "rutId:"+rutId+", "+
      "rutNombre:"+rutNombre+
    "]";
  }

  private String rutId = "";
  public String getRutId()
  {
    return rutId;
  }
  public void setRutId(String rutId)
  {
    this.rutId = rutId;
    this.pk_identificador_ = rutId;
  }

  private String rutNombre = "";
  public String getRutNombre()
  {
    return rutNombre;
  }
  public void setRutNombre(String rutNombre)
  {
    this.rutNombre = rutNombre;
  }
}
