package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class ProveedoresEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="proveedores";
  public static final String ALL_COLUMNS="proveedores.pr_apellidos,proveedores.pr_ccc,proveedores.pr_codigo_postal,proveedores.pr_descuento,proveedores.pr_direccion,proveedores.pr_email,proveedores.pr_fax,proveedores.pr_fp_id,proveedores.pr_id,proveedores.pr_loc_id,proveedores.pr_movil,proveedores.pr_nif,proveedores.pr_nombre,proveedores.pr_nombre_banco,proveedores.pr_nombre_comercial,proveedores.pr_observaciones,proveedores.pr_persona_contacto,proveedores.pr_pf_id,proveedores.pr_prov_id,proveedores.pr_telefono,proveedores.pr_web";
  public static final String PRAPELLIDOS="pr_apellidos";
  public static final String PRCCC="pr_ccc";
  public static final String PRCODIGOPOSTAL="pr_codigo_postal";
  public static final String PRDESCUENTO="pr_descuento";
  public static final String PRDIRECCION="pr_direccion";
  public static final String PREMAIL="pr_email";
  public static final String PRFAX="pr_fax";
  public static final String PRFPID="pr_fp_id";
  public static final String PRID="pr_id";
  public static final String PRLOCID="pr_loc_id";
  public static final String PRMOVIL="pr_movil";
  public static final String PRNIF="pr_nif";
  public static final String PRNOMBRE="pr_nombre";
  public static final String PRNOMBREBANCO="pr_nombre_banco";
  public static final String PRNOMBRECOMERCIAL="pr_nombre_comercial";
  public static final String PROBSERVACIONES="pr_observaciones";
  public static final String PRPERSONACONTACTO="pr_persona_contacto";
  public static final String PRPFID="pr_pf_id";
  public static final String PRPROVID="pr_prov_id";
  public static final String PRTELEFONO="pr_telefono";
  public static final String PRWEB="pr_web";
  
  public static final String PRIMARY_KEY="pr_id";

  protected ProveedoresEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE proveedores SET pr_apellidos=?,pr_ccc=?,pr_codigo_postal=?,pr_descuento=?,pr_direccion=?,pr_email=?,pr_fax=?,pr_fp_id=?,pr_loc_id=?,pr_movil=?,pr_nif=?,pr_nombre=?,pr_nombre_banco=?,pr_nombre_comercial=?,pr_observaciones=?,pr_persona_contacto=?,pr_pf_id=?,pr_prov_id=?,pr_telefono=?,pr_web=? WHERE pr_id=?");
    sentencia.setString(1, prApellidos);
    sentencia.setString(2, prCcc);
    sentencia.setString(3, prCodigoPostal);
    sentencia.setString(4, quitar_comas(prDescuento));
    sentencia.setString(5, prDireccion);
    sentencia.setString(6, prEmail);
    sentencia.setString(7, prFax);
    sentencia.setString(8, prFpId);
    //sentencia.setString(9, prId);
    sentencia.setString(9, prLocId);
    sentencia.setString(10, prMovil);
    sentencia.setString(11, prNif);
    sentencia.setString(12, prNombre);
    sentencia.setString(13, prNombreBanco);
    sentencia.setString(14, prNombreComercial);
    sentencia.setString(15, prObservaciones);
    sentencia.setString(16, prPersonaContacto);
    sentencia.setString(17, prPfId);
    sentencia.setString(18, prProvId);
    sentencia.setString(19, prTelefono);
    sentencia.setString(20, prWeb);
    sentencia.setString(21, prId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO proveedores (pr_apellidos,pr_ccc,pr_codigo_postal,pr_descuento,pr_direccion,pr_email,pr_fax,pr_fp_id,pr_loc_id,pr_movil,pr_nif,pr_nombre,pr_nombre_banco,pr_nombre_comercial,pr_observaciones,pr_persona_contacto,pr_pf_id,pr_prov_id,pr_telefono,pr_web) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, prApellidos);
    sentencia.setString(2, prCcc);
    sentencia.setString(3, prCodigoPostal);
    sentencia.setString(4, quitar_comas(prDescuento));
    sentencia.setString(5, prDireccion);
    sentencia.setString(6, prEmail);
    sentencia.setString(7, prFax);
    sentencia.setString(8, prFpId);
    //sentencia.setString(9, prId);
    sentencia.setString(9, prLocId);
    sentencia.setString(10, prMovil);
    sentencia.setString(11, prNif);
    sentencia.setString(12, prNombre);
    sentencia.setString(13, prNombreBanco);
    sentencia.setString(14, prNombreComercial);
    sentencia.setString(15, prObservaciones);
    sentencia.setString(16, prPersonaContacto);
    sentencia.setString(17, prPfId);
    sentencia.setString(18, prProvId);
    sentencia.setString(19, prTelefono);
    sentencia.setString(20, prWeb);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM proveedores WHERE pr_id=?");
    sentencia.setString(1, prId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "proveedores["+
      "prApellidos:"+prApellidos+", "+
      "prCcc:"+prCcc+", "+
      "prCodigoPostal:"+prCodigoPostal+", "+
      "prDescuento:"+prDescuento+", "+
      "prDireccion:"+prDireccion+", "+
      "prEmail:"+prEmail+", "+
      "prFax:"+prFax+", "+
      "prFpId:"+prFpId+", "+
      "prId:"+prId+", "+
      "prLocId:"+prLocId+", "+
      "prMovil:"+prMovil+", "+
      "prNif:"+prNif+", "+
      "prNombre:"+prNombre+", "+
      "prNombreBanco:"+prNombreBanco+", "+
      "prNombreComercial:"+prNombreComercial+", "+
      "prObservaciones:"+prObservaciones+", "+
      "prPersonaContacto:"+prPersonaContacto+", "+
      "prPfId:"+prPfId+", "+
      "prProvId:"+prProvId+", "+
      "prTelefono:"+prTelefono+", "+
      "prWeb:"+prWeb+
    "]";
  }

  private String prApellidos = "";
  public String getPrApellidos()
  {
    return prApellidos;
  }
  public void setPrApellidos(String prApellidos)
  {
    this.prApellidos = prApellidos;
  }

  private String prCcc = "";
  public String getPrCcc()
  {
    return prCcc;
  }
  public void setPrCcc(String prCcc)
  {
    this.prCcc = prCcc;
  }

  private String prCodigoPostal = "";
  public String getPrCodigoPostal()
  {
    return prCodigoPostal;
  }
  public void setPrCodigoPostal(String prCodigoPostal)
  {
    this.prCodigoPostal = prCodigoPostal;
  }

  private String prDescuento = "";
  public String getPrDescuento()
  {
    return prDescuento;
  }
  public void setPrDescuento(String prDescuento)
  {
    this.prDescuento = prDescuento;
  }

  private String prDireccion = "";
  public String getPrDireccion()
  {
    return prDireccion;
  }
  public void setPrDireccion(String prDireccion)
  {
    this.prDireccion = prDireccion;
  }

  private String prEmail = "";
  public String getPrEmail()
  {
    return prEmail;
  }
  public void setPrEmail(String prEmail)
  {
    this.prEmail = prEmail;
  }

  private String prFax = "";
  public String getPrFax()
  {
    return prFax;
  }
  public void setPrFax(String prFax)
  {
    this.prFax = prFax;
  }

  private String prFpId = "";
  public String getPrFpId()
  {
    return prFpId;
  }
  public void setPrFpId(String prFpId)
  {
    this.prFpId = prFpId;
  }

  private String prId = "";
  public String getPrId()
  {
    return prId;
  }
  public void setPrId(String prId)
  {
    this.prId = prId;
    this.pk_identificador_ = prId;
  }

  private String prLocId = "";
  public String getPrLocId()
  {
    return prLocId;
  }
  public void setPrLocId(String prLocId)
  {
    this.prLocId = prLocId;
  }

  private String prMovil = "";
  public String getPrMovil()
  {
    return prMovil;
  }
  public void setPrMovil(String prMovil)
  {
    this.prMovil = prMovil;
  }

  private String prNif = "";
  public String getPrNif()
  {
    return prNif;
  }
  public void setPrNif(String prNif)
  {
    this.prNif = prNif;
  }

  private String prNombre = "";
  public String getPrNombre()
  {
    return prNombre;
  }
  public void setPrNombre(String prNombre)
  {
    this.prNombre = prNombre;
  }

  private String prNombreBanco = "";
  public String getPrNombreBanco()
  {
    return prNombreBanco;
  }
  public void setPrNombreBanco(String prNombreBanco)
  {
    this.prNombreBanco = prNombreBanco;
  }

  private String prNombreComercial = "";
  public String getPrNombreComercial()
  {
    return prNombreComercial;
  }
  public void setPrNombreComercial(String prNombreComercial)
  {
    this.prNombreComercial = prNombreComercial;
  }

  private String prObservaciones = "";
  public String getPrObservaciones()
  {
    return prObservaciones;
  }
  public void setPrObservaciones(String prObservaciones)
  {
    this.prObservaciones = prObservaciones;
  }

  private String prPersonaContacto = "";
  public String getPrPersonaContacto()
  {
    return prPersonaContacto;
  }
  public void setPrPersonaContacto(String prPersonaContacto)
  {
    this.prPersonaContacto = prPersonaContacto;
  }

  private String prPfId = "";
  public String getPrPfId()
  {
    return prPfId;
  }
  public void setPrPfId(String prPfId)
  {
    this.prPfId = prPfId;
  }

  private String prProvId = "";
  public String getPrProvId()
  {
    return prProvId;
  }
  public void setPrProvId(String prProvId)
  {
    this.prProvId = prProvId;
  }

  private String prTelefono = "";
  public String getPrTelefono()
  {
    return prTelefono;
  }
  public void setPrTelefono(String prTelefono)
  {
    this.prTelefono = prTelefono;
  }

  private String prWeb = "";
  public String getPrWeb()
  {
    return prWeb;
  }
  public void setPrWeb(String prWeb)
  {
    this.prWeb = prWeb;
  }
  
  private String[] prDatosRelacionados;
  public String[] getPrDatosRelacionados()
  {
	  return prDatosRelacionados;
  }
  public void setPrDatosRelacionados(String[] prDatosRelacionados)
  {
    this.prDatosRelacionados = prDatosRelacionados;
  }
  
  private String tipo = "";
  public String getTipo()
  {
    return tipo;
  }
  public void setTipo(String tipo)
  {
    this.tipo = tipo;
  }
}
