package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class ComposicionFabricacionEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="composicion_fabricacion";
  public static final String ALL_COLUMNS="composicion_fabricacion.cf_cantidad,composicion_fabricacion.cf_id,composicion_fabricacion.cf_id_st_fabricado,composicion_fabricacion.cf_id_st_usado";
  public static final String CFCANTIDAD="cf_cantidad";
  public static final String CFID="cf_id";
  public static final String CFIDSTFABRICADO="cf_id_st_fabricado";
  public static final String CFIDSTUSADO="cf_id_st_usado";
  public static final String PRIMARY_KEY="cf_id";

  protected ComposicionFabricacionEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE composicion_fabricacion SET cf_cantidad=?,cf_id_st_fabricado=?,cf_id_st_usado=? WHERE cf_id=?");
    sentencia.setString(1, quitar_comas(cfCantidad));
    //sentencia.setString(2, cfId);
    sentencia.setString(2, cfIdStFabricado);
    sentencia.setString(3, cfIdStUsado);
    sentencia.setString(4, cfId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO composicion_fabricacion (cf_cantidad,cf_id_st_fabricado,cf_id_st_usado) VALUES (?,?,?)");
    sentencia.setString(1, quitar_comas(cfCantidad));
    //sentencia.setString(2, cfId);
    sentencia.setString(2, cfIdStFabricado);
    sentencia.setString(3, cfIdStUsado);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM composicion_fabricacion WHERE cf_id=?");
    sentencia.setString(1, cfId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "composicion_fabricacion["+
      "cfCantidad:"+cfCantidad+", "+
      "cfId:"+cfId+", "+
      "cfIdStFabricado:"+cfIdStFabricado+", "+
      "cfIdStUsado:"+cfIdStUsado+
    "]";
  }

  private String cfCantidad = "";
  public String getCfCantidad()
  {
    return cfCantidad;
  }
  public void setCfCantidad(String cfCantidad)
  {
    this.cfCantidad = cfCantidad;
  }

  private String cfId = "";
  public String getCfId()
  {
    return cfId;
  }
  public void setCfId(String cfId)
  {
    this.cfId = cfId;
    this.pk_identificador_ = cfId;
  }

  private String cfIdStFabricado = "";
  public String getCfIdStFabricado()
  {
    return cfIdStFabricado;
  }
  public void setCfIdStFabricado(String cfIdStFabricado)
  {
    this.cfIdStFabricado = cfIdStFabricado;
  }

  private String cfIdStUsado = "";
  public String getCfIdStUsado()
  {
    return cfIdStUsado;
  }
  public void setCfIdStUsado(String cfIdStUsado)
  {
    this.cfIdStUsado = cfIdStUsado;
  }
  

  private String nombreProdUsado = "";
  public String getNombreProdUsado()
  {
    return nombreProdUsado;
  }
  public void setNombreProdUsado(String nombreProdUsado)
  {
    this.nombreProdUsado = nombreProdUsado;
  }
  

  private String lote = "";
  public String getLote()
  {
    return lote;
  }
  public void setLote(String lote)
  {
    this.lote = lote;
  }
}
