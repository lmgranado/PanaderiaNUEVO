package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class HistoricoInventariosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="historico_inventarios";
  public static final String ALL_COLUMNS="historico_inventarios.hinv_fecha,historico_inventarios.hinv_id,historico_inventarios.hinv_us_id";
  public static final String HINVFECHA="hinv_fecha";
  public static final String HINVID="hinv_id";
  public static final String HINVUSID="hinv_us_id";
  public static final String PRIMARY_KEY="hinv_id";
  
  //Rango de Fechas para las busquedas
  public static final String HINVFECHADESDE="hinv_fecha_desde";
  public static final String HINVFECHAHASTA="hinv_fecha_hasta";

  protected HistoricoInventariosEntidad(){}

  public int actualiza()
  {
    SQLManager manager = new SQLManager(); 
    Connection conexion = manager.getConnection(); 
    try
    {
      return actualiza(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ manager.closeConnection();  }
  }

  public int actualiza(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE historico_inventarios SET hinv_fecha=?,hinv_id=?,hinv_us_id=? WHERE hinv_id=?");
    sentencia.setTimestamp(1, hinvFecha);
    sentencia.setString(2, hinvId);
    sentencia.setString(3, hinvUsId);
    sentencia.setString(4, hinvId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public int inserta()
  {
    SQLManager manager = new SQLManager(); 
    Connection conexion = manager.getConnection(); 
    try
    {
      return inserta(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ manager.closeConnection();  }
  }

  public int inserta(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO historico_inventarios (hinv_fecha,hinv_us_id) VALUES (?,?)");
    sentencia.setTimestamp(1, hinvFecha);
    sentencia.setString(2, hinvUsId);
    sentencia.executeUpdate();
    
    int autoIncKeyFromApi = -1;

    ResultSet rs = sentencia.getGeneratedKeys();

    if (rs.next()) {
        autoIncKeyFromApi = rs.getInt(1);
    } else {

        // throw an exception from here
    }

    sentencia.close();
    return autoIncKeyFromApi;
  }

  public int elimina()
  {
    SQLManager manager = new SQLManager(); 
    Connection conexion = manager.getConnection(); 
    try
    {
      return elimina(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ manager.closeConnection(); }
  }

  public int elimina(Connection conexion) throws SQLException
  {
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM historico_inventarios WHERE hinv_id=?");
    sentencia.setString(1, hinvId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "historico_inventarios["+
      "hinvFecha:"+utils.Utils.date2String(hinvFecha, "dd/MM/yyyy HH:mm:ss")+", "+
      "hinvId:"+hinvId+", "+
      "hinvUsId:"+hinvUsId+
    "]";
  }

  private java.sql.Timestamp hinvFecha = null;
  public java.util.Date getHinvFecha()
  {
    if(hinvFecha==null){ return null; }
    else{ return hinvFecha; }
  }
  public void setHinvFecha(java.sql.Timestamp hinvFecha)
  {
    if(hinvFecha==null){ this.hinvFecha = null; }
    else{ this.hinvFecha = new java.sql.Timestamp(hinvFecha.getTime()); }
  }
  public String getHinvFecha(String formato)
  {
    return utils.Utils.date2String(hinvFecha, formato);
  }
  public void setHinvFecha(String hinvFecha, String formato)
  {
    if( hinvFecha==null || hinvFecha.equals("") )
    { this.hinvFecha = null; }
    else
    {
      try
      {
        this.hinvFecha = new java.sql.Timestamp(utils.Utils.string2Date(hinvFecha, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private String hinvId = "";
  public String getHinvId()
  {
    return hinvId;
  }
  public void setHinvId(String hinvId)
  {
    this.hinvId = hinvId;
    this.pk_identificador_ = hinvId;
  }

  private String hinvUsId = "";
  public String getHinvUsId()
  {
    return hinvUsId;
  }
  public void setHinvUsId(String hinvUsId)
  {
    this.hinvUsId = hinvUsId;
  }
  
  

  private java.sql.Timestamp hinvFechaDesde = null;
  public java.util.Date getHinvFechaDesde()
  {
    if(hinvFechaDesde==null){ return null; }
    else{ return hinvFechaDesde; }
  }
  public void setHinvFechaDesde(java.sql.Timestamp hinvFechaDesde)
  {
    if(hinvFechaDesde==null){ this.hinvFechaDesde = null; }
    else{ this.hinvFechaDesde = new java.sql.Timestamp(hinvFechaDesde.getTime()); }
  }
  public String getHinvFechaDesde(String formato)
  {
    return utils.Utils.date2String(hinvFechaDesde, formato);
  }
  public void setHinvFechaDesde(String hinvFechaDesde, String formato)
  {
    if( hinvFechaDesde==null || hinvFechaDesde.equals("") )
    { this.hinvFechaDesde = null; }
    else
    {
      try
      {
        this.hinvFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(hinvFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  
  private java.sql.Timestamp HinvFechaHasta = null;
  public java.util.Date getHinvFechaHasta()
  {
    if(HinvFechaHasta==null){ return null; }
    else{ return HinvFechaHasta; }
  }
  public void setHinvFechaHasta(java.sql.Timestamp HinvFechaHasta)
  {
    if(HinvFechaHasta==null){ this.HinvFechaHasta = null; }
    else{ this.HinvFechaHasta = new java.sql.Timestamp(HinvFechaHasta.getTime()); }
  }
  public String getHinvFechaHasta(String formato)
  {
    return utils.Utils.date2String(HinvFechaHasta, formato);
  }
  public void setHinvFechaHasta(String HinvFechaHasta, String formato)
  {
    if( HinvFechaHasta==null || HinvFechaHasta.equals("") )
    { this.HinvFechaHasta = null; }
    else
    {
      try
      {
        this.HinvFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(HinvFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private String[] hinvDatosRelacionados;
  public String[] getHinvDatosRelacionados()
  {
	  return hinvDatosRelacionados;
  }
  public void setHinvDatosRelacionados(String[] hinvDatosRelacionados)
  {
    this.hinvDatosRelacionados = hinvDatosRelacionados;
  }
}
