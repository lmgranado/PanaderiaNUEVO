package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class UsuariosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="usuarios";
  public static final String ALL_COLUMNS="usuarios.us_apellidos,usuarios.us_codigo_postal,usuarios.us_direccion,usuarios.us_email,usuarios.us_id,usuarios.us_login,usuarios.us_movil,usuarios.us_nif,usuarios.us_nombre,usuarios.us_passw,usuarios.us_telefono,usuarios.us_administrador";
  public static final String USAPELLIDOS="us_apellidos";
  public static final String USCODIGOPOSTAL="us_codigo_postal";
  public static final String USDIRECCION="us_direccion";
  public static final String USEMAIL="us_email";
  public static final String USID="us_id";
  public static final String USLOGIN="us_login";
  public static final String USMOVIL="us_movil";
  public static final String USNIF="us_nif";
  public static final String USNOMBRE="us_nombre";
  public static final String USPASSW="us_passw";
  public static final String USTELEFONO="us_telefono";
  public static final String USADMINISTRADOR="us_administrador";
  public static final String PRIMARY_KEY="us_id";

  protected UsuariosEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE usuarios SET us_apellidos=?,us_codigo_postal=?,us_direccion=?,us_email=?,us_login=?,us_movil=?,us_nif=?,us_nombre=?,us_passw=?,us_telefono=?,us_administrador=? WHERE us_id=?");
    sentencia.setString(1, usApellidos);
    sentencia.setString(2, usCodigoPostal);
    sentencia.setString(3, usDireccion);
    sentencia.setString(4, usEmail);
    //sentencia.setString(5, usId);
    sentencia.setString(5, usLogin);
    sentencia.setString(6, usMovil);
    sentencia.setString(7, usNif);
    sentencia.setString(8, usNombre);
    sentencia.setString(9, usPassw);
    sentencia.setString(10, usTelefono);
    sentencia.setString(11, usAdministrador);
    sentencia.setString(12, usId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO usuarios (us_apellidos,us_codigo_postal,us_direccion,us_email,us_login,us_movil,us_nif,us_nombre,us_passw,us_telefono,us_administrador) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, usApellidos);
    sentencia.setString(2, usCodigoPostal);
    sentencia.setString(3, usDireccion);
    sentencia.setString(4, usEmail);
    //sentencia.setString(5, usId);
    sentencia.setString(5, usLogin);
    sentencia.setString(6, usMovil);
    sentencia.setString(7, usNif);
    sentencia.setString(8, usNombre);
    sentencia.setString(9, usPassw);
    sentencia.setString(10, usTelefono);
    sentencia.setString(11, usAdministrador);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM usuarios WHERE us_id=?");
    sentencia.setString(1, usId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "usuarios["+
      "usApellidos:"+usApellidos+", "+
      "usCodigoPostal:"+usCodigoPostal+", "+
      "usDireccion:"+usDireccion+", "+
      "usEmail:"+usEmail+", "+
      "usId:"+usId+", "+
      "usLogin:"+usLogin+", "+
      "usMovil:"+usMovil+", "+
      "usNif:"+usNif+", "+
      "usNombre:"+usNombre+", "+
      "usPassw:"+usPassw+", "+
      "usTelefono:"+usTelefono+
    "]";
  }

  private String usApellidos = "";
  public String getUsApellidos()
  {
    return usApellidos;
  }
  public void setUsApellidos(String usApellidos)
  {
    this.usApellidos = usApellidos;
  }

  private String usCodigoPostal = "";
  public String getUsCodigoPostal()
  {
    return usCodigoPostal;
  }
  public void setUsCodigoPostal(String usCodigoPostal)
  {
    this.usCodigoPostal = usCodigoPostal;
  }

  private String usDireccion = "";
  public String getUsDireccion()
  {
    return usDireccion;
  }
  public void setUsDireccion(String usDireccion)
  {
    this.usDireccion = usDireccion;
  }

  private String usEmail = "";
  public String getUsEmail()
  {
    return usEmail;
  }
  public void setUsEmail(String usEmail)
  {
    this.usEmail = usEmail;
  }

  private String usId = "";
  public String getUsId()
  {
    return usId;
  }
  public void setUsId(String usId)
  {
    this.usId = usId;
    this.pk_identificador_ = usId;
  }

  private String usLogin = "";
  public String getUsLogin()
  {
    return usLogin;
  }
  public void setUsLogin(String usLogin)
  {
    this.usLogin = usLogin;
  }

  private String usMovil = "";
  public String getUsMovil()
  {
    return usMovil;
  }
  public void setUsMovil(String usMovil)
  {
    this.usMovil = usMovil;
  }

  private String usNif = "";
  public String getUsNif()
  {
    return usNif;
  }
  public void setUsNif(String usNif)
  {
    this.usNif = usNif;
  }

  private String usNombre = "";
  public String getUsNombre()
  {
    return usNombre;
  }
  public void setUsNombre(String usNombre)
  {
    this.usNombre = usNombre;
  }

  private String usPassw = "";
  public String getUsPassw()
  {
    return usPassw;
  }
  public void setUsPassw(String usPassw)
  {
    this.usPassw = usPassw;
  }

  private String usTelefono = "";
  public String getUsTelefono()
  {
    return usTelefono;
  }
  public void setUsTelefono(String usTelefono)
  {
    this.usTelefono = usTelefono;
  }
  

  private String usAdministrador = "";
  public String getUsAdministrador()
  {
    return usAdministrador;
  }
  public void setUsAdministrador(String usAdministrador)
  {
    this.usAdministrador = usAdministrador;
  }
}
