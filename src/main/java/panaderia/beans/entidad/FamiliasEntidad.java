package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class FamiliasEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="familias";
  public static final String ALL_COLUMNS="familias.fam_id,familias.fam_nombre,familias.fam_fprod_id";
  public static final String FAMID="fam_id";
  public static final String FAMNOMBRE="fam_nombre";
  public static final String FAMFPRODID="fam_fprod_id";
  public static final String PRIMARY_KEY="fam_id";

  protected FamiliasEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE familias SET fam_id=?,fam_nombre=?,fam_fprod_id=? WHERE fam_id=?");
    sentencia.setString(1, famId);
    sentencia.setString(2, famNombre);
    sentencia.setString(3, famFprodId);
    sentencia.setString(4, famId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO familias (fam_nombre,fam_fprod_id) VALUES (?,?)");
    sentencia.setString(1, famNombre);
    sentencia.setString(2, famFprodId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM familias WHERE fam_id=?");
    sentencia.setString(1, famId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "familias["+
      "famId:"+famId+", "+
      "famNombre:"+famNombre+
    "]";
  }

  private String famId = "";
  public String getFamId()
  {
    return famId;
  }
  public void setFamId(String famId)
  {
    this.famId = famId;
    this.pk_identificador_ = famId;
  }

  private String famNombre = "";
  public String getFamNombre()
  {
    return famNombre;
  }
  public void setFamNombre(String famNombre)
  {
    this.famNombre = famNombre;
  }
  
  private String famFprodId = "";
  public String getFamFprodId()
  {
    return famFprodId;
  }
  public void setFamFprodId(String famFprodId)
  {
    this.famFprodId = famFprodId;
  }
}
