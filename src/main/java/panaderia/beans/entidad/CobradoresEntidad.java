package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class CobradoresEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="cobradores";
  public static final String ALL_COLUMNS="cobradores.cob_apellidos,cobradores.cob_codigo_postal,cobradores.cob_direccion,cobradores.cob_id,cobradores.cob_loc_id,cobradores.cob_movil,cobradores.cob_nif,cobradores.cob_nombre,cobradores.cob_prov_id,cobradores.cob_telefono";
  public static final String COBAPELLIDOS="cob_apellidos";
  public static final String COBCODIGOPOSTAL="cob_codigo_postal";
  public static final String COBDIRECCION="cob_direccion";
  public static final String COBID="cob_id";
  public static final String COBLOCID="cob_loc_id";
  public static final String COBMOVIL="cob_movil";
  public static final String COBNIF="cob_nif";
  public static final String COBNOMBRE="cob_nombre";
  public static final String COBPROVID="cob_prov_id";
  public static final String COBTELEFONO="cob_telefono";
  public static final String PRIMARY_KEY="cob_id";

  protected CobradoresEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE cobradores SET cob_apellidos=?,cob_codigo_postal=?,cob_direccion=?,cob_loc_id=?,cob_movil=?,cob_nif=?,cob_nombre=?,cob_prov_id=?,cob_telefono=? WHERE cob_id=?");
    sentencia.setString(1, cobApellidos);
    sentencia.setString(2, cobCodigoPostal);
    sentencia.setString(3, cobDireccion);
    //sentencia.setString(4, cobId);
    sentencia.setString(4, cobLocId);
    sentencia.setString(5, cobMovil);
    sentencia.setString(6, cobNif);
    sentencia.setString(7, cobNombre);
    sentencia.setString(8, cobProvId);
    sentencia.setString(9, cobTelefono);
    sentencia.setString(10, cobId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO cobradores (cob_apellidos,cob_codigo_postal,cob_direccion,cob_loc_id,cob_movil,cob_nif,cob_nombre,cob_prov_id,cob_telefono) VALUES (?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, cobApellidos);
    sentencia.setString(2, cobCodigoPostal);
    sentencia.setString(3, cobDireccion);
    //sentencia.setString(4, cobId);
    sentencia.setString(4, cobLocId);
    sentencia.setString(5, cobMovil);
    sentencia.setString(6, cobNif);
    sentencia.setString(7, cobNombre);
    sentencia.setString(8, cobProvId);
    sentencia.setString(9, cobTelefono);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM cobradores WHERE cob_id=?");
    sentencia.setString(1, cobId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "cobradores["+
      "cobApellidos:"+cobApellidos+", "+
      "cobCodigoPostal:"+cobCodigoPostal+", "+
      "cobDireccion:"+cobDireccion+", "+
      "cobId:"+cobId+", "+
      "cobLocId:"+cobLocId+", "+
      "cobMovil:"+cobMovil+", "+
      "cobNif:"+cobNif+", "+
      "cobNombre:"+cobNombre+", "+
      "cobProvId:"+cobProvId+", "+
      "cobTelefono:"+cobTelefono+
    "]";
  }

  private String cobApellidos = "";
  public String getCobApellidos()
  {
    return cobApellidos;
  }
  public void setCobApellidos(String cobApellidos)
  {
    this.cobApellidos = cobApellidos;
  }

  private String cobCodigoPostal = "";
  public String getCobCodigoPostal()
  {
    return cobCodigoPostal;
  }
  public void setCobCodigoPostal(String cobCodigoPostal)
  {
    this.cobCodigoPostal = cobCodigoPostal;
  }

  private String cobDireccion = "";
  public String getCobDireccion()
  {
    return cobDireccion;
  }
  public void setCobDireccion(String cobDireccion)
  {
    this.cobDireccion = cobDireccion;
  }

  private String cobId = "";
  public String getCobId()
  {
    return cobId;
  }
  public void setCobId(String cobId)
  {
    this.cobId = cobId;
    this.pk_identificador_ = cobId;
  }

  private String cobLocId = "";
  public String getCobLocId()
  {
    return cobLocId;
  }
  public void setCobLocId(String cobLocId)
  {
    this.cobLocId = cobLocId;
  }

  private String cobMovil = "";
  public String getCobMovil()
  {
    return cobMovil;
  }
  public void setCobMovil(String cobMovil)
  {
    this.cobMovil = cobMovil;
  }

  private String cobNif = "";
  public String getCobNif()
  {
    return cobNif;
  }
  public void setCobNif(String cobNif)
  {
    this.cobNif = cobNif;
  }

  private String cobNombre = "";
  public String getCobNombre()
  {
    return cobNombre;
  }
  public void setCobNombre(String cobNombre)
  {
    this.cobNombre = cobNombre;
  }

  private String cobProvId = "";
  public String getCobProvId()
  {
    return cobProvId;
  }
  public void setCobProvId(String cobProvId)
  {
    this.cobProvId = cobProvId;
  }

  private String cobTelefono = "";
  public String getCobTelefono()
  {
    return cobTelefono;
  }
  public void setCobTelefono(String cobTelefono)
  {
    this.cobTelefono = cobTelefono;
  }
}
