package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class VacacionesClientesEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="vacaciones_clientes";
  public static final String ALL_COLUMNS="vacaciones_clientes.vac_cl_id,vacaciones_clientes.vac_fecha_desde,vacaciones_clientes.vac_fecha_hasta,vacaciones_clientes.vac_id,vacaciones_clientes.vac_disfrutadas";
  public static final String VACCLID="vac_cl_id";
  public static final String VACFECHADESDE="vac_fecha_desde";
  public static final String VACFECHAHASTA="vac_fecha_hasta";
  public static final String VACID="vac_id";
  public static final String VACDISFRUTADAS="vac_disfrutadas";
  public static final String PRIMARY_KEY="vac_id";

  protected VacacionesClientesEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE vacaciones_clientes SET vac_cl_id=?,vac_fecha_desde=?,vac_fecha_hasta=?,vac_disfrutadas=? WHERE vac_id=?");
    sentencia.setString(1, vacClId);
    sentencia.setTimestamp(2, vacFechaDesde);
    sentencia.setTimestamp(3, vacFechaHasta);
    sentencia.setString(4, vacDisfrutadas);
    sentencia.setString(5, vacId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO vacaciones_clientes (vac_cl_id,vac_fecha_desde,vac_fecha_hasta,vac_disfrutadas) VALUES (?,?,?,?)");
    sentencia.setString(1, vacClId);
    sentencia.setTimestamp(2, vacFechaDesde);
    sentencia.setTimestamp(3, vacFechaHasta);
    sentencia.setString(4, vacDisfrutadas);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM vacaciones_clientes WHERE vac_id=?");
    sentencia.setString(1, vacId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "vacaciones_clientes["+
      "vacClId:"+vacClId+", "+
      "vacFechaDesde:"+utils.Utils.date2String(vacFechaDesde, "dd/MM/yyyy HH:mm:ss")+", "+
      "vacFechaHasta:"+utils.Utils.date2String(vacFechaHasta, "dd/MM/yyyy HH:mm:ss")+", "+
      "vacId:"+vacId+
    "]";
  }

  private String vacClId = "";
  public String getVacClId()
  {
    return vacClId;
  }
  public void setVacClId(String vacClId)
  {
    this.vacClId = vacClId;
  }

  private java.sql.Timestamp vacFechaDesde = null;
  public java.util.Date getVacFechaDesde()
  {
    if(vacFechaDesde==null){ return null; }
    else{ return vacFechaDesde; }
  }
  public void setVacFechaDesde(java.sql.Timestamp vacFechaDesde)
  {
    if(vacFechaDesde==null){ this.vacFechaDesde = null; }
    else{ this.vacFechaDesde = new java.sql.Timestamp(vacFechaDesde.getTime()); }
  }
  public String getVacFechaDesde(String formato)
  {
    return utils.Utils.date2String(vacFechaDesde, formato);
  }
  public void setVacFechaDesde(String vacFechaDesde, String formato)
  {
    if( vacFechaDesde==null || vacFechaDesde.equals("") )
    { this.vacFechaDesde = null; }
    else
    {
      try
      {
        this.vacFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(vacFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private java.sql.Timestamp vacFechaHasta = null;
  public java.util.Date getVacFechaHasta()
  {
    if(vacFechaHasta==null){ return null; }
    else{ return vacFechaHasta; }
  }
  public void setVacFechaHasta(java.sql.Timestamp vacFechaHasta)
  {
    if(vacFechaHasta==null){ this.vacFechaHasta = null; }
    else{ this.vacFechaHasta = new java.sql.Timestamp(vacFechaHasta.getTime()); }
  }
  public String getVacFechaHasta(String formato)
  {
    return utils.Utils.date2String(vacFechaHasta, formato);
  }
  public void setVacFechaHasta(String vacFechaHasta, String formato)
  {
    if( vacFechaHasta==null || vacFechaHasta.equals("") )
    { this.vacFechaHasta = null; }
    else
    {
      try
      {
        this.vacFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(vacFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private String vacId = "";
  public String getVacId()
  {
    return vacId;
  }
  public void setVacId(String vacId)
  {
    this.vacId = vacId;
    this.pk_identificador_ = vacId;
  }
  
  private String vacDisfrutadas = "";
  public String getVacDisfrutadas()
  {
    return vacDisfrutadas;
  }
  public void setVacDisfrutadas(String vacDisfrutadas)
  {
    this.vacDisfrutadas = vacDisfrutadas;
  }
}
