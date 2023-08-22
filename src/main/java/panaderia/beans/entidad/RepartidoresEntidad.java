package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class RepartidoresEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="repartidores";
  public static final String ALL_COLUMNS="repartidores.rep_apellidos,repartidores.rep_codigo_postal,repartidores.rep_direccion,repartidores.rep_id,repartidores.rep_loc_id,repartidores.rep_movil,repartidores.rep_nif,repartidores.rep_nombre,repartidores.rep_prov_id,repartidores.rep_telefono";
  public static final String REPAPELLIDOS="rep_apellidos";
  public static final String REPCODIGOPOSTAL="rep_codigo_postal";
  public static final String REPDIRECCION="rep_direccion";
  public static final String REPID="rep_id";
  public static final String REPLOCID="rep_loc_id";
  public static final String REPMOVIL="rep_movil";
  public static final String REPNIF="rep_nif";
  public static final String REPNOMBRE="rep_nombre";
  public static final String REPPROVID="rep_prov_id";
  public static final String REPTELEFONO="rep_telefono";
  public static final String PRIMARY_KEY="rep_id";

  protected RepartidoresEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE repartidores SET rep_apellidos=?,rep_codigo_postal=?,rep_direccion=?,rep_id=?,rep_loc_id=?,rep_movil=?,rep_nif=?,rep_nombre=?,rep_prov_id=?,rep_telefono=? WHERE rep_id=?");
    sentencia.setString(1, repApellidos);
    sentencia.setString(2, repCodigoPostal);
    sentencia.setString(3, repDireccion);
    sentencia.setString(4, repId);
    sentencia.setString(5, repLocId);
    sentencia.setString(6, repMovil);
    sentencia.setString(7, repNif);
    sentencia.setString(8, repNombre);
    sentencia.setString(9, repProvId);
    sentencia.setString(10, repTelefono);
    sentencia.setString(11, repId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO repartidores (rep_apellidos,rep_codigo_postal,rep_direccion,rep_loc_id,rep_movil,rep_nif,rep_nombre,rep_prov_id,rep_telefono) VALUES (?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, repApellidos);
    sentencia.setString(2, repCodigoPostal);
    sentencia.setString(3, repDireccion);
    //sentencia.setString(4, repId);
    sentencia.setString(4, repLocId);
    sentencia.setString(5, repMovil);
    sentencia.setString(6, repNif);
    sentencia.setString(7, repNombre);
    sentencia.setString(8, repProvId);
    sentencia.setString(9, repTelefono);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM repartidores WHERE rep_id=?");
    sentencia.setString(1, repId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "repartidores["+
      "repApellidos:"+repApellidos+", "+
      "repCodigoPostal:"+repCodigoPostal+", "+
      "repDireccion:"+repDireccion+", "+
      "repId:"+repId+", "+
      "repLocId:"+repLocId+", "+
      "repMovil:"+repMovil+", "+
      "repNif:"+repNif+", "+
      "repNombre:"+repNombre+", "+
      "repProvId:"+repProvId+", "+
      "repTelefono:"+repTelefono+
    "]";
  }

  private String repApellidos = "";
  public String getRepApellidos()
  {
    return repApellidos;
  }
  public void setRepApellidos(String repApellidos)
  {
    this.repApellidos = repApellidos;
  }

  private String repCodigoPostal = "";
  public String getRepCodigoPostal()
  {
    return repCodigoPostal;
  }
  public void setRepCodigoPostal(String repCodigoPostal)
  {
    this.repCodigoPostal = repCodigoPostal;
  }

  private String repDireccion = "";
  public String getRepDireccion()
  {
    return repDireccion;
  }
  public void setRepDireccion(String repDireccion)
  {
    this.repDireccion = repDireccion;
  }

  private String repId = "";
  public String getRepId()
  {
    return repId;
  }
  public void setRepId(String repId)
  {
    this.repId = repId;
    this.pk_identificador_ = repId;
  }

  private String repLocId = "";
  public String getRepLocId()
  {
    return repLocId;
  }
  public void setRepLocId(String repLocId)
  {
    this.repLocId = repLocId;
  }

  private String repMovil = "";
  public String getRepMovil()
  {
    return repMovil;
  }
  public void setRepMovil(String repMovil)
  {
    this.repMovil = repMovil;
  }

  private String repNif = "";
  public String getRepNif()
  {
    return repNif;
  }
  public void setRepNif(String repNif)
  {
    this.repNif = repNif;
  }

  private String repNombre = "";
  public String getRepNombre()
  {
    return repNombre;
  }
  public void setRepNombre(String repNombre)
  {
    this.repNombre = repNombre;
  }

  private String repProvId = "";
  public String getRepProvId()
  {
    return repProvId;
  }
  public void setRepProvId(String repProvId)
  {
    this.repProvId = repProvId;
  }

  private String repTelefono = "";
  public String getRepTelefono()
  {
    return repTelefono;
  }
  public void setRepTelefono(String repTelefono)
  {
    this.repTelefono = repTelefono;
  }
}
