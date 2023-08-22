package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class ProvinciasEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="provincias";
  public static final String ALL_COLUMNS="provincias.prov_codigo,provincias.prov_id,provincias.prov_nombre";
  public static final String PROVCODIGO="prov_codigo";
  public static final String PROVID="prov_id";
  public static final String PROVNOMBRE="prov_nombre";
  public static final String PRIMARY_KEY="prov_id";

  protected ProvinciasEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE provincias SET prov_codigo=?,prov_id=?,prov_nombre=? WHERE prov_id=?");
    sentencia.setString(1, provCodigo);
    sentencia.setString(2, provId);
    sentencia.setString(3, provNombre);
    sentencia.setString(4, provId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO provincias (prov_codigo,prov_id,prov_nombre) VALUES (?,?,?)");
    sentencia.setString(1, provCodigo);
    sentencia.setString(2, provId);
    sentencia.setString(3, provNombre);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM provincias WHERE prov_id=?");
    sentencia.setString(1, provId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "provincias["+
      "provCodigo:"+provCodigo+", "+
      "provId:"+provId+", "+
      "provNombre:"+provNombre+
    "]";
  }

  private String provCodigo = "";
  public String getProvCodigo()
  {
    return provCodigo;
  }
  public void setProvCodigo(String provCodigo)
  {
    this.provCodigo = provCodigo;
  }

  private String provId = "";
  public String getProvId()
  {
    return provId;
  }
  public void setProvId(String provId)
  {
    this.provId = provId;
    this.pk_identificador_ = provId;
  }

  private String provNombre = "";
  public String getProvNombre()
  {
    return provNombre;
  }
  public void setProvNombre(String provNombre)
  {
    this.provNombre = provNombre;
  }
}
