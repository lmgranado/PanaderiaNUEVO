package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class ComposicionProductosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="composicion_productos";
  public static final String ALL_COLUMNS="composicion_productos.comprod_descripcion,composicion_productos.comprod_fprod_id,composicion_productos.comprod_id,composicion_productos.comprod_medida,composicion_productos.comprod_nombre,composicion_productos.comprod_peso";
  public static final String COMPRODDESCRIPCION="comprod_descripcion";
  public static final String COMPRODFPRODID="comprod_fprod_id";
  public static final String COMPRODID="comprod_id";
  public static final String COMPRODMEDIDA="comprod_medida";
  public static final String COMPRODNOMBRE="comprod_nombre";
  public static final String COMPRODPESO="comprod_peso";
  public static final String PRIMARY_KEY="comprod_id";

  protected ComposicionProductosEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE composicion_productos SET comprod_descripcion=?,comprod_fprod_id=?,comprod_id=?,comprod_medida=?,comprod_nombre=?,comprod_peso=? WHERE comprod_id=?");
    sentencia.setString(1, comprodDescripcion);
    sentencia.setString(2, comprodFprodId);
    sentencia.setString(3, comprodId);
    sentencia.setString(4, comprodMedida);
    sentencia.setString(5, comprodNombre);
    sentencia.setString(6, quitar_comas(comprodPeso));
    sentencia.setString(7, comprodId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO composicion_productos (comprod_descripcion,comprod_fprod_id,comprod_medida,comprod_nombre,comprod_peso) VALUES (?,?,?,?,?)");
    sentencia.setString(1, comprodDescripcion);
    sentencia.setString(2, comprodFprodId);
    //sentencia.setString(3, comprodId);
    sentencia.setString(3, comprodMedida);
    sentencia.setString(4, comprodNombre);
    sentencia.setString(5, quitar_comas(comprodPeso));
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM composicion_productos WHERE comprod_id=?");
    sentencia.setString(1, comprodId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "composicion_productos["+
      "comprodDescripcion:"+comprodDescripcion+", "+
      "comprodFprodId:"+comprodFprodId+", "+
      "comprodId:"+comprodId+", "+
      "comprodMedida:"+comprodMedida+", "+
      "comprodNombre:"+comprodNombre+", "+
      "comprodPeso:"+comprodPeso+
    "]";
  }

  private String comprodDescripcion = "";
  public String getComprodDescripcion()
  {
    return comprodDescripcion;
  }
  public void setComprodDescripcion(String comprodDescripcion)
  {
    this.comprodDescripcion = comprodDescripcion;
  }

  private String comprodFprodId = "";
  public String getComprodFprodId()
  {
    return comprodFprodId;
  }
  public void setComprodFprodId(String comprodFprodId)
  {
    this.comprodFprodId = comprodFprodId;
  }

  private String comprodId = "";
  public String getComprodId()
  {
    return comprodId;
  }
  public void setComprodId(String comprodId)
  {
    this.comprodId = comprodId;
    this.pk_identificador_ = comprodId;
  }

  private String comprodMedida = "";
  public String getComprodMedida()
  {
    return comprodMedida;
  }
  public void setComprodMedida(String comprodMedida)
  {
    this.comprodMedida = comprodMedida;
  }

  private String comprodNombre = "";
  public String getComprodNombre()
  {
    return comprodNombre;
  }
  public void setComprodNombre(String comprodNombre)
  {
    this.comprodNombre = comprodNombre;
  }

  private String comprodPeso = "";
  public String getComprodPeso()
  {
    return comprodPeso;
  }
  public void setComprodPeso(String comprodPeso)
  {
    this.comprodPeso = comprodPeso;
  }
}
