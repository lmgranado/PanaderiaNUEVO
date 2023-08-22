package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class ClientesEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="clientes";
  public static final String ALL_COLUMNS="clientes.cl_apellidos,clientes.cl_ccc,clientes.cl_cob_id,clientes.cl_codigo_postal,clientes.cl_descuento,clientes.cl_direccion,clientes.cl_email,clientes.cl_fax,clientes.cl_fp_id,clientes.cl_id,clientes.cl_loc_id,clientes.cl_movil,clientes.cl_nif,clientes.cl_nombre,clientes.cl_nombre_banco,clientes.cl_nombre_comercial,clientes.cl_persona_contacto,clientes.cl_pf_id,clientes.cl_prov_id,clientes.cl_telefono,clientes.cl_web,clientes.cl_activo,clientes.cl_proporcion_iva,clientes.cl_id_empresa,clientes.cl_pertenencia";
  public static final String CLAPELLIDOS="cl_apellidos";
  public static final String CLCCC="cl_ccc";
  public static final String CLCOBID="cl_cob_id";
  public static final String CLCODIGOPOSTAL="cl_codigo_postal";
  public static final String CLDESCUENTO="cl_descuento";
  public static final String CLDIRECCION="cl_direccion";
  public static final String CLEMAIL="cl_email";
  public static final String CLFAX="cl_fax";
  public static final String CLFPID="cl_fp_id";
  public static final String CLID="cl_id";
  public static final String CLLOCID="cl_loc_id";
  public static final String CLMOVIL="cl_movil";
  public static final String CLNIF="cl_nif";
  public static final String CLNOMBRE="cl_nombre";
  public static final String CLNOMBREBANCO="cl_nombre_banco";
  public static final String CLNOMBRECOMERCIAL="cl_nombre_comercial";
  public static final String CLPERSONACONTACTO="cl_persona_contacto";
  public static final String CLPFID="cl_pf_id";
  public static final String CLPROVID="cl_prov_id";
  public static final String CLTELEFONO="cl_telefono";
  public static final String CLWEB="cl_web";
  public static final String CLACTIVO="cl_activo";
  public static final String CLPROPORCIONIVA="cl_proporcion_iva";
  public static final String CLIDEMPRESA="cl_id_empresa";
  public static final String CLPERTENENCIA="cl_pertenencia";
  
  public static final String PRIMARY_KEY="cl_id";

  protected ClientesEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE clientes SET cl_apellidos=?,cl_ccc=?,cl_cob_id=?,cl_codigo_postal=?,cl_descuento=?,cl_direccion=?,cl_email=?,cl_fax=?,cl_fp_id=?,cl_id=?,cl_loc_id=?,cl_movil=?,cl_nif=?,cl_nombre=?,cl_nombre_banco=?,cl_nombre_comercial=?,cl_persona_contacto=?,cl_pf_id=?,cl_prov_id=?,cl_telefono=?,cl_web=?,cl_activo=?,cl_proporcion_iva=?,cl_id_empresa=?,cl_pertenencia=? WHERE cl_id=?");
    sentencia.setString(1, clApellidos);
    sentencia.setString(2, clCcc);
    sentencia.setString(3, clCobId);
    sentencia.setString(4, clCodigoPostal);
    sentencia.setString(5, quitar_comas(clDescuento));
    sentencia.setString(6, clDireccion);
    sentencia.setString(7, clEmail);
    sentencia.setString(8, clFax);
    sentencia.setString(9, clFpId);
    sentencia.setString(10, clId);
    sentencia.setString(11, clLocId);
    sentencia.setString(12, clMovil);
    sentencia.setString(13, clNif);
    sentencia.setString(14, clNombre);
    sentencia.setString(15, clNombreBanco);
    sentencia.setString(16, clNombreComercial);
    sentencia.setString(17, clPersonaContacto);
    sentencia.setString(18, clPfId);
    sentencia.setString(19, clProvId);
    sentencia.setString(20, clTelefono);
    sentencia.setString(21, clWeb);
    sentencia.setString(22, clActivo);
    sentencia.setString(23, quitar_comas(clProporcionIva));
    sentencia.setString(24, clIdEmpresa);
    sentencia.setString(25, clPertenencia);
    sentencia.setString(26, clId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO clientes (cl_apellidos,cl_ccc,cl_cob_id,cl_codigo_postal,cl_descuento,cl_direccion,cl_email,cl_fax,cl_fp_id,cl_loc_id,cl_movil,cl_nif,cl_nombre,cl_nombre_banco,cl_nombre_comercial,cl_persona_contacto,cl_pf_id,cl_prov_id,cl_telefono,cl_web,cl_activo,cl_proporcion_iva,cl_id_empresa,cl_pertenencia) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, clApellidos);
    sentencia.setString(2, clCcc);
    sentencia.setString(3, clCobId);
    sentencia.setString(4, clCodigoPostal);
    sentencia.setString(5, quitar_comas(clDescuento));
    sentencia.setString(6, clDireccion);
    sentencia.setString(7, clEmail);
    sentencia.setString(8, clFax);
    sentencia.setString(9, clFpId);
    sentencia.setString(10, clLocId);
    sentencia.setString(11, clMovil);
    sentencia.setString(12, clNif);
    sentencia.setString(13, clNombre);
    sentencia.setString(14, clNombreBanco);
    sentencia.setString(15, clNombreComercial);
    sentencia.setString(16, clPersonaContacto);
    sentencia.setString(17, clPfId);
    sentencia.setString(18, clProvId);
    sentencia.setString(19, clTelefono);
    sentencia.setString(20, clWeb);
    sentencia.setString(21, clActivo);
    sentencia.setString(22, quitar_comas(clProporcionIva));
    sentencia.setString(23, clIdEmpresa);
    sentencia.setString(24, clPertenencia);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM clientes WHERE cl_id=?");
    sentencia.setString(1, clId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "clientes["+
      "clApellidos:"+clApellidos+", "+
      "clCcc:"+clCcc+", "+
      "clCobId:"+clCobId+", "+
      "clCodigoPostal:"+clCodigoPostal+", "+
      "clDescuento:"+clDescuento+", "+
      "clDireccion:"+clDireccion+", "+
      "clEmail:"+clEmail+", "+
      "clFax:"+clFax+", "+
      "clFpId:"+clFpId+", "+
      "clId:"+clId+", "+
      "clLocId:"+clLocId+", "+
      "clMovil:"+clMovil+", "+
      "clNif:"+clNif+", "+
      "clNombre:"+clNombre+", "+
      "clNombreBanco:"+clNombreBanco+", "+
      "clNombreComercial:"+clNombreComercial+", "+
      "clPersonaContacto:"+clPersonaContacto+", "+
      "clPfId:"+clPfId+", "+
      "clProvId:"+clProvId+", "+
      "clTelefono:"+clTelefono+", "+
      "clWeb:"+clWeb+", "+
      "clActivo:"+clActivo+", "+
      "clProporcionIva:"+clProporcionIva+", "+
      "clIdEmpresa:"+clIdEmpresa+", "+
      "clPertenencia:"+clPertenencia+
    "]";
  }

  private String clApellidos = "";
  public String getClApellidos()
  {
    return clApellidos;
  }
  public void setClApellidos(String clApellidos)
  {
    this.clApellidos = clApellidos;
  }

  private String clCcc = "";
  public String getClCcc()
  {
    return clCcc;
  }
  public void setClCcc(String clCcc)
  {
    this.clCcc = clCcc;
  }

  private String clCobId = "";
  public String getClCobId()
  {
    return clCobId;
  }
  public void setClCobId(String clCobId)
  {
    this.clCobId = clCobId;
  }

  private String clCodigoPostal = "";
  public String getClCodigoPostal()
  {
    return clCodigoPostal;
  }
  public void setClCodigoPostal(String clCodigoPostal)
  {
    this.clCodigoPostal = clCodigoPostal;
  }

  private String clDescuento = "";
  public String getClDescuento()
  {
    return clDescuento;
  }
  public void setClDescuento(String clDescuento)
  {
    this.clDescuento = clDescuento;
  }
  

  private String clProporcionIva = "";
  public String getClProporcionIva()
  {
    return clProporcionIva;
  }
  public void setClProporcionIva(String clProporcionIva)
  {
    this.clProporcionIva = clProporcionIva;
  }

  private String clDireccion = "";
  public String getClDireccion()
  {
    return clDireccion;
  }
  public void setClDireccion(String clDireccion)
  {
    this.clDireccion = clDireccion;
  }

  private String clEmail = "";
  public String getClEmail()
  {
    return clEmail;
  }
  public void setClEmail(String clEmail)
  {
    this.clEmail = clEmail;
  }

  private String clFax = "";
  public String getClFax()
  {
    return clFax;
  }
  public void setClFax(String clFax)
  {
    this.clFax = clFax;
  }

  private String clFpId = "";
  public String getClFpId()
  {
    return clFpId;
  }
  public void setClFpId(String clFpId)
  {
    this.clFpId = clFpId;
  }

  private String clId = "";
  public String getClId()
  {
    return clId;
  }
  public void setClId(String clId)
  {
    this.clId = clId;
    this.pk_identificador_ = clId;
  }

  private String clLocId = "";
  public String getClLocId()
  {
    return clLocId;
  }
  public void setClLocId(String clLocId)
  {
    this.clLocId = clLocId;
  }

  private String clMovil = "";
  public String getClMovil()
  {
    return clMovil;
  }
  public void setClMovil(String clMovil)
  {
    this.clMovil = clMovil;
  }

  private String clNif = "";
  public String getClNif()
  {
    return clNif;
  }
  public void setClNif(String clNif)
  {
    this.clNif = clNif;
  }

  private String clNombre = "";
  public String getClNombre()
  {
    return clNombre;
  }
  public void setClNombre(String clNombre)
  {
    this.clNombre = clNombre;
  }

  private String clNombreBanco = "";
  public String getClNombreBanco()
  {
    return clNombreBanco;
  }
  public void setClNombreBanco(String clNombreBanco)
  {
    this.clNombreBanco = clNombreBanco;
  }

  private String clNombreComercial = "";
  public String getClNombreComercial()
  {
    return clNombreComercial;
  }
  public void setClNombreComercial(String clNombreComercial)
  {
    this.clNombreComercial = clNombreComercial;
  }

  private String clPersonaContacto = "";
  public String getClPersonaContacto()
  {
    return clPersonaContacto;
  }
  public void setClPersonaContacto(String clPersonaContacto)
  {
    this.clPersonaContacto = clPersonaContacto;
  }

  private String clPfId = "";
  public String getClPfId()
  {
    return clPfId;
  }
  public void setClPfId(String clPfId)
  {
    this.clPfId = clPfId;
  }

  private String clProvId = "";
  public String getClProvId()
  {
    return clProvId;
  }
  public void setClProvId(String clProvId)
  {
    this.clProvId = clProvId;
  }

  private String clTelefono = "";
  public String getClTelefono()
  {
    return clTelefono;
  }
  public void setClTelefono(String clTelefono)
  {
    this.clTelefono = clTelefono;
  }

  private String clWeb = "";
  public String getClWeb()
  {
    return clWeb;
  }
  public void setClWeb(String clWeb)
  {
    this.clWeb = clWeb;
  }
  
  private String clActivo = "";
  public String getClActivo()
  {
    return clActivo;
  }
  public void setClActivo(String clActivo)
  {
    this.clActivo = clActivo;
  }
  
  private String clIdEmpresa = "";
  public String getClIdEmpresa()
  {
    return clIdEmpresa;
  }
  public void setClIdEmpresa(String clIdEmpresa)
  {
    this.clIdEmpresa = clIdEmpresa;
  }
  
  private String clPertenencia = "";
  public String getClPertenencia()
  {
    return clPertenencia;
  }
  public void setClPertenencia(String clPertenencia)
  {
    this.clPertenencia = clPertenencia;
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
