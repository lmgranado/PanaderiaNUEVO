package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class CuadrantesDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="cuadrantes_detalle";
  public static final String ALL_COLUMNS="cuadrantes_detalle.cud_cantidad1,cuadrantes_detalle.cud_cantidad2,cuadrantes_detalle.cud_cantidad3,cuadrantes_detalle.cud_cantidad4,cuadrantes_detalle.cud_cantidad5,cuadrantes_detalle.cud_cu_id,cuadrantes_detalle.cud_id,cuadrantes_detalle.cud_pcl_id";
  public static final String CUDCANTIDAD1="cud_cantidad1";
  public static final String CUDCANTIDAD2="cud_cantidad2";
  public static final String CUDCANTIDAD3="cud_cantidad3";
  public static final String CUDCANTIDAD4="cud_cantidad4";
  public static final String CUDCANTIDAD5="cud_cantidad5";
  public static final String CUDCUID="cud_cu_id";
  public static final String CUDID="cud_id";
  public static final String CUDPCLID="cud_pcl_id";
  public static final String PRIMARY_KEY="cud_id";

  protected CuadrantesDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE cuadrantes_detalle SET cud_cantidad1=?,cud_cantidad2=?,cud_cantidad3=?,cud_cantidad4=?,cud_cantidad5=?,cud_cu_id=?,cud_id=?,cud_pcl_id=? WHERE cud_id=?");
    sentencia.setString(1, cudCantidad1);
    sentencia.setString(2, cudCantidad2);
    sentencia.setString(3, cudCantidad3);
    sentencia.setString(4, cudCantidad4);
    sentencia.setString(5, cudCantidad5);
    sentencia.setString(6, cudCuId);
    sentencia.setString(7, cudId);
    sentencia.setString(8, cudPclId);
    sentencia.setString(9, cudId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO cuadrantes_detalle (cud_cantidad1,cud_cantidad2,cud_cantidad3,cud_cantidad4,cud_cantidad5,cud_cu_id,cud_pcl_id) VALUES (?,?,?,?,?,?,?)");
    sentencia.setString(1, cudCantidad1);
    sentencia.setString(2, cudCantidad2);
    sentencia.setString(3, cudCantidad3);
    sentencia.setString(4, cudCantidad4);
    sentencia.setString(5, cudCantidad5);
    sentencia.setString(6, cudCuId);
    sentencia.setString(7, cudPclId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM cuadrantes_detalle WHERE cud_id=?");
    sentencia.setString(1, cudId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "cuadrantes_detalle["+
      "cudCantidad1:"+cudCantidad1+", "+
      "cudCantidad2:"+cudCantidad2+", "+
      "cudCantidad3:"+cudCantidad3+", "+
      "cudCantidad4:"+cudCantidad4+", "+
      "cudCantidad5:"+cudCantidad5+", "+
      "cudCuId:"+cudCuId+", "+
      "cudId:"+cudId+", "+
      "cudPclId:"+cudPclId +
    "]";
  }

  private String cudCantidad1 = "";
  public String getCudCantidad1()
  {
    return cudCantidad1;
  }
  public void setCudCantidad1(String cudCantidad1)
  {
    this.cudCantidad1 = cudCantidad1;
  }

  private String cudCantidad2 = "";
  public String getCudCantidad2()
  {
    return cudCantidad2;
  }
  public void setCudCantidad2(String cudCantidad2)
  {
    this.cudCantidad2 = cudCantidad2;
  }

  private String cudCantidad3 = "";
  public String getCudCantidad3()
  {
    return cudCantidad3;
  }
  public void setCudCantidad3(String cudCantidad3)
  {
    this.cudCantidad3 = cudCantidad3;
  }

  private String cudCantidad4 = "";
  public String getCudCantidad4()
  {
    return cudCantidad4;
  }
  public void setCudCantidad4(String cudCantidad4)
  {
    this.cudCantidad4 = cudCantidad4;
  }

  private String cudCantidad5 = "";
  public String getCudCantidad5()
  {
    return cudCantidad5;
  }
  public void setCudCantidad5(String cudCantidad5)
  {
    this.cudCantidad5 = cudCantidad5;
  }

  private String cudCuId = "";
  public String getCudCuId()
  {
    return cudCuId;
  }
  public void setCudCuId(String cudCuId)
  {
    this.cudCuId = cudCuId;
  }

  private String cudId = "";
  public String getCudId()
  {
    return cudId;
  }
  public void setCudId(String cudId)
  {
    this.cudId = cudId;
    this.pk_identificador_ = cudId;
  }

  private String cudPclId = "";
  public String getCudPclId()
  {
    return cudPclId;
  }
  public void setCudPclId(String cudPclId)
  {
    this.cudPclId = cudPclId;
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
