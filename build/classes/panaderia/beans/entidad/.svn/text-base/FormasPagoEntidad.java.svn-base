package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class FormasPagoEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="formas_pago";
  public static final String ALL_COLUMNS="formas_pago.fp_descripcion,formas_pago.fp_id";
  public static final String FPDESCRIPCION="fp_descripcion";
  public static final String FPID="fp_id";
  public static final String PRIMARY_KEY="fp_id";

  protected FormasPagoEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE formas_pago SET fp_descripcion=?,fp_id=? WHERE fp_id=?");
    sentencia.setString(1, fpDescripcion);
    sentencia.setString(2, fpId);
    sentencia.setString(3, fpId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO formas_pago (fp_descripcion) VALUES (?)");
    sentencia.setString(1, fpDescripcion);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM formas_pago WHERE fp_id=?");
    sentencia.setString(1, fpId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "formas_pago["+
      "fpDescripcion:"+fpDescripcion+", "+
      "fpId:"+fpId+
    "]";
  }

  private String fpDescripcion = "";
  public String getFpDescripcion()
  {
    return fpDescripcion;
  }
  public void setFpDescripcion(String fpDescripcion)
  {
    this.fpDescripcion = fpDescripcion;
  }

  private String fpId = "";
  public String getFpId()
  {
    return fpId;
  }
  public void setFpId(String fpId)
  {
    this.fpId = fpId;
    this.pk_identificador_ = fpId;
  }
}
