package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class EntregasDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="entregas_detalle";
  public static final String ALL_COLUMNS="entregas_detalle.end_cl_id,entregas_detalle.end_cu_id,entregas_detalle.end_id,entregas_detalle.end_orden,entregas_detalle.end_rut_id,entregas_detalle.end_viaje,entregas_detalle.end_ent_id";
  public static final String ENDCLID="end_cl_id";
  public static final String ENDCUID="end_cu_id";
  public static final String ENDID="end_id";
  public static final String ENDORDEN="end_orden";
  public static final String ENDRUTID="end_rut_id";
  public static final String ENDVIAJE="end_viaje";
  public static final String ENDENTID="end_ent_id";
  public static final String PRIMARY_KEY="end_id";

  protected EntregasDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE entregas_detalle SET end_cl_id=?,end_cu_id=?,end_id=?,end_orden=?,end_rut_id=?,end_viaje=?,end_ent_id=? WHERE end_id=?");
    sentencia.setString(1, endClId);
    sentencia.setString(2, endCuId);
    sentencia.setString(3, endId);
    sentencia.setString(4, endOrden);
    sentencia.setString(5, endRutId);
    sentencia.setString(6, endViaje);
    sentencia.setString(7, endEntId);
    sentencia.setString(8, endId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO entregas_detalle (end_cl_id,end_cu_id,end_orden,end_rut_id,end_viaje,end_ent_id) VALUES (?,?,?,?,?,?)");
    sentencia.setString(1, endClId);
    sentencia.setString(2, endCuId);
    sentencia.setString(3, endOrden);
    sentencia.setString(4, endRutId);
    sentencia.setString(5, endViaje);
    sentencia.setString(6, endEntId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM entregas_detalle WHERE end_id=?");
    sentencia.setString(1, endId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "entregas_detalle["+
      "endClId:"+endClId+", "+
      "endCuId:"+endCuId+", "+
      "endId:"+endId+", "+
      "endOrden:"+endOrden+", "+
      "endRutId:"+endRutId+", "+
      "endViaje:"+endViaje+
    "]";
  }

  private String endClId = "";
  public String getEndClId()
  {
    return endClId;
  }
  public void setEndClId(String endClId)
  {
    this.endClId = endClId;
  }

  private String endCuId = "";
  public String getEndCuId()
  {
    return endCuId;
  }
  public void setEndCuId(String endCuId)
  {
    this.endCuId = endCuId;
  }

  private String endId = "";
  public String getEndId()
  {
    return endId;
  }
  public void setEndId(String endId)
  {
    this.endId = endId;
    this.pk_identificador_ = endId;
  }

  private String endOrden = "";
  public String getEndOrden()
  {
    return endOrden;
  }
  public void setEndOrden(String endOrden)
  {
    this.endOrden = endOrden;
  }

  private String endRutId = "";
  public String getEndRutId()
  {
    return endRutId;
  }
  public void setEndRutId(String endRutId)
  {
    this.endRutId = endRutId;
  }

  private String endViaje = "";
  public String getEndViaje()
  {
    return endViaje;
  }
  public void setEndViaje(String endViaje)
  {
    this.endViaje = endViaje;
  }
  
  private String endEntId = "";
  public String getEndEntId()
  {
    return endEntId;
  }
  public void setEndEntId(String endEntId)
  {
    this.endEntId = endEntId;
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
