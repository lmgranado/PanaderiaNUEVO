package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class ProductoComposicionEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="producto_composicion";
  public static final String ALL_COLUMNS="producto_composicion.prodcomp_descripcion,producto_composicion.prodcomp_id,producto_composicion.prodcomp_medida,producto_composicion.prodcomp_nombre,producto_composicion.prodcomp_peso,producto_composicion.prodcomp_prodf_id";
  public static final String ALL_COLUMNS_CONVERSOR="composicion_productos.comprod_descripcion as prodcomp_descripcion,composicion_productos.comprod_fprod_id as prodcomp_prodf_id,composicion_productos.comprod_id as prodcomp_id,composicion_productos.comprod_medida as prodcomp_medida,composicion_productos.comprod_nombre as prodcomp_nombre,composicion_productos.comprod_peso as prodcomp_peso";
  public static final String PRODCOMPDESCRIPCION="prodcomp_descripcion";
  public static final String PRODCOMPID="prodcomp_id";
  public static final String PRODCOMPMEDIDA="prodcomp_medida";
  public static final String PRODCOMPNOMBRE="prodcomp_nombre";
  public static final String PRODCOMPPESO="prodcomp_peso";
  public static final String PRODCOMPPRODFID="prodcomp_prodf_id";
  public static final String PRIMARY_KEY="prodcomp_id";

  protected ProductoComposicionEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE producto_composicion SET prodcomp_descripcion=?,prodcomp_id=?,prodcomp_medida=?,prodcomp_nombre=?,prodcomp_peso=?,prodcomp_prodf_id=? WHERE prodcomp_id=?");
    sentencia.setString(1, prodcompDescripcion);
    sentencia.setString(2, prodcompId);
    sentencia.setString(3, prodcompMedida);
    sentencia.setString(4, prodcompNombre);
    sentencia.setString(5, quitar_comas(prodcompPeso));
    sentencia.setString(6, prodcompProdfId);
    sentencia.setString(7, prodcompId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO producto_composicion (prodcomp_descripcion,prodcomp_medida,prodcomp_nombre,prodcomp_peso,prodcomp_prodf_id) VALUES (?,?,?,?,?)");
    sentencia.setString(1, prodcompDescripcion);
    //sentencia.setString(2, prodcompId);
    sentencia.setString(2, prodcompMedida);
    sentencia.setString(3, prodcompNombre);
    sentencia.setString(4, quitar_comas(prodcompPeso));
    sentencia.setString(5, prodcompProdfId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM producto_composicion WHERE prodcomp_id=?");
    sentencia.setString(1, prodcompId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "producto_composicion["+
      "prodcompDescripcion:"+prodcompDescripcion+", "+
      "prodcompId:"+prodcompId+", "+
      "prodcompMedida:"+prodcompMedida+", "+
      "prodcompNombre:"+prodcompNombre+", "+
      "prodcompPeso:"+prodcompPeso+", "+
      "prodcompProdfId:"+prodcompProdfId+
    "]";
  }

  private String prodcompDescripcion = "";
  public String getProdcompDescripcion()
  {
    return prodcompDescripcion;
  }
  public void setProdcompDescripcion(String prodcompDescripcion)
  {
    this.prodcompDescripcion = prodcompDescripcion;
  }

  private String prodcompId = "";
  public String getProdcompId()
  {
    return prodcompId;
  }
  public void setProdcompId(String prodcompId)
  {
    this.prodcompId = prodcompId;
    this.pk_identificador_ = prodcompId;
  }

  private String prodcompMedida = "";
  public String getProdcompMedida()
  {
    return prodcompMedida;
  }
  public void setProdcompMedida(String prodcompMedida)
  {
    this.prodcompMedida = prodcompMedida;
  }

  private String prodcompNombre = "";
  public String getProdcompNombre()
  {
    return prodcompNombre;
  }
  public void setProdcompNombre(String prodcompNombre)
  {
    this.prodcompNombre = prodcompNombre;
  }

  private String prodcompPeso = "";
  public String getProdcompPeso()
  {
    return prodcompPeso;
  }
  public void setProdcompPeso(String prodcompPeso)
  {
    this.prodcompPeso = prodcompPeso;
  }

  private String prodcompProdfId = "";
  public String getProdcompProdfId()
  {
    return prodcompProdfId;
  }
  public void setProdcompProdfId(String prodcompProdfId)
  {
    this.prodcompProdfId = prodcompProdfId;
  }
}
