package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class EntregasEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="entregas";
  public static final String ALL_COLUMNS="entregas.ent_id,entregas.ent_nombre";
  public static final String ENTID="ent_id";
  public static final String ENTNOMBRE="ent_nombre";
  public static final String PRIMARY_KEY="ent_id";

  protected EntregasEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE entregas SET ent_id=?,ent_nombre=? WHERE ent_id=?");
    sentencia.setString(1, entId);
    sentencia.setString(2, entNombre);
    sentencia.setString(3, entId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO entregas (ent_nombre) VALUES (?)");
    sentencia.setString(1, entNombre);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM entregas WHERE ent_id=?");
    sentencia.setString(1, entId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "entregas["+
      "entId:"+entId+", "+
      "entNombre:"+entNombre+
    "]";
  }

  private String entId = "";
  public String getEntId()
  {
    return entId;
  }
  public void setEntId(String entId)
  {
    this.entId = entId;
    this.pk_identificador_ = entId;
  }

  private String entNombre = "";
  public String getEntNombre()
  {
    return entNombre;
  }
  public void setEntNombre(String entNombre)
  {
    this.entNombre = entNombre;
  }
  
  private String[] clDatosRelacionados;
  public String[] getClDatosRelacionados()
  {
	  return clDatosRelacionados;
  }
  public void setClDatosRelacionados(String[] clDatosRelacionados)
  {
    this.clDatosRelacionados = clDatosRelacionados;
  }
}
