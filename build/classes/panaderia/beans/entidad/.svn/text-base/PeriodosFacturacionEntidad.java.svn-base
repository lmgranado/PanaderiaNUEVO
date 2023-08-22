package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class PeriodosFacturacionEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="periodos_facturacion";
  public static final String ALL_COLUMNS="periodos_facturacion.pf_descripcion,periodos_facturacion.pf_dia,periodos_facturacion.pf_id";
  public static final String PFDESCRIPCION="pf_descripcion";
  public static final String PFDIA="pf_dia";
  public static final String PFID="pf_id";
  public static final String PRIMARY_KEY="pf_id";

  protected PeriodosFacturacionEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE periodos_facturacion SET pf_descripcion=?,pf_dia=?,pf_id=? WHERE pf_id=?");
    sentencia.setString(1, pfDescripcion);
    sentencia.setString(2, pfDia);
    sentencia.setString(3, pfId);
    sentencia.setString(4, pfId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO periodos_facturacion (pf_descripcion,pf_dia) VALUES (?,?)");
    sentencia.setString(1, pfDescripcion);
    sentencia.setString(2, pfDia);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM periodos_facturacion WHERE pf_id=?");
    sentencia.setString(1, pfId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "periodos_facturacion["+
      "pfDescripcion:"+pfDescripcion+", "+
      "pfDia:"+pfDia+", "+
      "pfId:"+pfId+
    "]";
  }

  private String pfDescripcion = "";
  public String getPfDescripcion()
  {
    return pfDescripcion;
  }
  public void setPfDescripcion(String pfDescripcion)
  {
    this.pfDescripcion = pfDescripcion;
  }

  private String pfDia = "";
  public String getPfDia()
  {
    return pfDia;
  }
  public void setPfDia(String pfDia)
  {
    this.pfDia = pfDia;
  }

  private String pfId = "";
  public String getPfId()
  {
    return pfId;
  }
  public void setPfId(String pfId)
  {
    this.pfId = pfId;
    this.pk_identificador_ = pfId;
  }
}
