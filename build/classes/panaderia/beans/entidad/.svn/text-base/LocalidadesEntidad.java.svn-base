package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class LocalidadesEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="localidades";
  public static final String ALL_COLUMNS="localidades.loc_codigo,localidades.loc_id,localidades.loc_nombre,localidades.loc_prov_codigo";
  public static final String LOCCODIGO="loc_codigo";
  public static final String LOCID="loc_id";
  public static final String LOCNOMBRE="loc_nombre";
  public static final String LOCPROVCODIGO="loc_prov_codigo";
  public static final String PRIMARY_KEY="loc_id";

  protected LocalidadesEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE localidades SET loc_codigo=?,loc_id=?,loc_nombre=?,loc_prov_codigo=? WHERE loc_id=?");
    sentencia.setString(1, locCodigo);
    sentencia.setString(2, locId);
    sentencia.setString(3, locNombre);
    sentencia.setString(4, locProvCodigo);
    sentencia.setString(5, locId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO localidades (loc_codigo,loc_id,loc_nombre,loc_prov_codigo) VALUES (?,?,?,?)");
    sentencia.setString(1, locCodigo);
    sentencia.setString(2, locId);
    sentencia.setString(3, locNombre);
    sentencia.setString(4, locProvCodigo);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM localidades WHERE loc_id=?");
    sentencia.setString(1, locId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "localidades["+
      "locCodigo:"+locCodigo+", "+
      "locId:"+locId+", "+
      "locNombre:"+locNombre+", "+
      "locProvCodigo:"+locProvCodigo+
    "]";
  }

  private String locCodigo = "";
  public String getLocCodigo()
  {
    return locCodigo;
  }
  public void setLocCodigo(String locCodigo)
  {
    this.locCodigo = locCodigo;
  }

  private String locId = "";
  public String getLocId()
  {
    return locId;
  }
  public void setLocId(String locId)
  {
    this.locId = locId;
    this.pk_identificador_ = locId;
  }

  private String locNombre = "";
  public String getLocNombre()
  {
    return locNombre;
  }
  public void setLocNombre(String locNombre)
  {
    this.locNombre = locNombre;
  }

  private String locProvCodigo = "";
  public String getLocProvCodigo()
  {
    return locProvCodigo;
  }
  public void setLocProvCodigo(String locProvCodigo)
  {
    this.locProvCodigo = locProvCodigo;
  }
}
