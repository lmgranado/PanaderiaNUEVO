package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;


public class HistoricoAcEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="historico_ac";
  public static final String ALL_COLUMNS="historico_ac.hac_cantidad,historico_ac.hac_cl_id,historico_ac.hac_fecha_entrega,historico_ac.hac_id";
  public static final String HACCANTIDAD="hac_cantidad";
  public static final String HACCLID="hac_cl_id";
  public static final String HACFECHAENTREGA="hac_fecha_entrega";
  public static final String HACID="hac_id";
  public static final String PRIMARY_KEY="hac_id";

//Rango de Fechas para las busquedas
  public static final String HACFECHADESDE="hac_fecha_desde";
  public static final String HACFECHAHASTA="hac_fecha_hasta";
  
  protected HistoricoAcEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE historico_ac SET hac_cantidad=?,hac_cl_id=?,hac_fecha_entrega=?,hac_id=? WHERE hac_id=?");
    sentencia.setString(1, quitar_comas(hacCantidad));
    sentencia.setString(2, hacClId);
    sentencia.setTimestamp(3, hacFechaEntrega);
    sentencia.setString(4, hacId);
    sentencia.setString(5, hacId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO historico_ac (hac_cantidad,hac_cl_id,hac_fecha_entrega) VALUES (?,?,?)");
    sentencia.setString(1, quitar_comas(hacCantidad));
    sentencia.setString(2, hacClId);
    sentencia.setTimestamp(3, hacFechaEntrega);
    //sentencia.setString(4, hacId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM historico_ac WHERE hac_id=?");
    sentencia.setString(1, hacId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "historico_ac["+
      "hacCantidad:"+hacCantidad+", "+
      "hacClId:"+hacClId+", "+
      "hacFechaEntrega:"+utils.Utils.date2String(hacFechaEntrega, "dd/MM/yyyy HH:mm:ss")+", "+
      "hacId:"+hacId+
    "]";
  }

  private String hacCantidad = "";
  public String getHacCantidad()
  {
    return hacCantidad;
  }
  public void setHacCantidad(String hacCantidad)
  {
    this.hacCantidad = hacCantidad;
  }

  private String hacClId = "";
  public String getHacClId()
  {
    return hacClId;
  }
  public void setHacClId(String hacClId)
  {
    this.hacClId = hacClId;
  }

  private java.sql.Timestamp hacFechaEntrega = null;
  public java.util.Date getHacFechaEntrega()
  {
    if(hacFechaEntrega==null){ return null; }
    else{ return hacFechaEntrega; }
  }
  public void setHacFechaEntrega(java.sql.Timestamp hacFechaEntrega)
  {
    if(hacFechaEntrega==null){ this.hacFechaEntrega = null; }
    else{ this.hacFechaEntrega = new java.sql.Timestamp(hacFechaEntrega.getTime()); }
  }
  public String getHacFechaEntrega(String formato)
  {
    return utils.Utils.date2String(hacFechaEntrega, formato);
  }
  public void setHacFechaEntrega(String hacFechaEntrega, String formato)
  {
    if( hacFechaEntrega==null || hacFechaEntrega.equals("") )
    { this.hacFechaEntrega = null; }
    else
    {
      try
      {
        this.hacFechaEntrega = new java.sql.Timestamp(utils.Utils.string2Date(hacFechaEntrega, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  
  private java.sql.Timestamp hacFechaDesde = null;
  public java.util.Date getHacFechaDesde()
  {
    if(hacFechaDesde==null){ return null; }
    else{ return hacFechaDesde; }
  }
  public void setHacFechaDesde(java.sql.Timestamp hacFechaDesde)
  {
    if(hacFechaDesde==null){ this.hacFechaDesde = null; }
    else{ this.hacFechaDesde = new java.sql.Timestamp(hacFechaDesde.getTime()); }
  }
  public String getHacFechaDesde(String formato)
  {
    return utils.Utils.date2String(hacFechaDesde, formato);
  }
  public void setHacFechaDesde(String hacFechaDesde, String formato)
  {
    if( hacFechaDesde==null || hacFechaDesde.equals("") )
    { this.hacFechaDesde = null; }
    else
    {
      try
      {
        this.hacFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(hacFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private java.sql.Timestamp hacFechaHasta = null;
  public java.util.Date getHacFechaHasta()
  {
    if(hacFechaHasta==null){ return null; }
    else{ return hacFechaHasta; }
  }
  public void setHacFechaHasta(java.sql.Timestamp hacFechaHasta)
  {
    if(hacFechaHasta==null){ this.hacFechaHasta = null; }
    else{ this.hacFechaHasta = new java.sql.Timestamp(hacFechaHasta.getTime()); }
  }
  public String getHacFechaHasta(String formato)
  {
    return utils.Utils.date2String(hacFechaHasta, formato);
  }
  public void setHacFechaHasta(String hacFechaHasta, String formato)
  {
    if( hacFechaHasta==null || hacFechaHasta.equals("") )
    { this.hacFechaHasta = null; }
    else
    {
      try
      {
        this.hacFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(hacFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private String hacId = "";
  public String getHacId()
  {
    return hacId;
  }
  public void setHacId(String hacId)
  {
    this.hacId = hacId;
    this.pk_identificador_ = hacId;
  }
}
