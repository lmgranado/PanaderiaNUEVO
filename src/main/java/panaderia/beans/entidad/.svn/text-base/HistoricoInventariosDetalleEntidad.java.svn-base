package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class HistoricoInventariosDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="historico_inventarios_detalle";
  public static final String ALL_COLUMNS="historico_inventarios_detalle.hinvd_hinv_id,historico_inventarios_detalle.hinvd_id,historico_inventarios_detalle.hinvd_lote,historico_inventarios_detalle.hinvd_nombre_producto,historico_inventarios_detalle.hinvd_observaciones,historico_inventarios_detalle.hinvd_prod_id,historico_inventarios_detalle.hinvd_recuento,historico_inventarios_detalle.hinvd_regularizacion";
  public static final String HINVDHINVID="hinvd_hinv_id";
  public static final String HINVDID="hinvd_id";
  public static final String HINVDLOTE="hinvd_lote";
  public static final String HINVDNOMBREPRODUCTO="hinvd_nombre_producto";
  public static final String HINVDOBSERVACIONES="hinvd_observaciones";
  public static final String HINVDPRODID="hinvd_prod_id";
  public static final String HINVDRECUENTO="hinvd_recuento";
  public static final String HINVDREGULARIZACION="hinvd_regularizacion";
  public static final String PRIMARY_KEY="hinvd_id";

  protected HistoricoInventariosDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE historico_inventarios_detalle SET hinvd_hinv_id=?,hinvd_lote=?,hinvd_nombre_producto=?,hinvd_observaciones=?,hinvd_prod_id=?,hinvd_recuento=?,hinvd_regularizacion=? WHERE hinvd_id=?");
    sentencia.setString(1, hinvdHinvId);
    sentencia.setString(2, hinvdLote);
    sentencia.setString(3, hinvdNombreProducto);
    sentencia.setString(4, hinvdObservaciones);
    sentencia.setString(5, hinvdProdId);
    sentencia.setString(6, quitar_comas(hinvdRecuento));
    sentencia.setString(7, quitar_comas(hinvdRegularizacion));
    sentencia.setString(8, hinvdId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO historico_inventarios_detalle (hinvd_hinv_id,hinvd_lote,hinvd_nombre_producto,hinvd_observaciones,hinvd_prod_id,hinvd_recuento,hinvd_regularizacion) VALUES (?,?,?,?,?,?,?)");
    sentencia.setString(1, hinvdHinvId);
    sentencia.setString(2, hinvdLote);
    sentencia.setString(3, hinvdNombreProducto);
    sentencia.setString(4, hinvdObservaciones);
    sentencia.setString(5, hinvdProdId);
    sentencia.setString(6, quitar_comas(hinvdRecuento));
    sentencia.setString(7, quitar_comas(hinvdRegularizacion));
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM historico_inventarios_detalle WHERE hinvd_id=?");
    sentencia.setString(1, hinvdId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "historico_inventarios_detalle["+
      "hinvdHinvId:"+hinvdHinvId+", "+
      "hinvdId:"+hinvdId+", "+
      "hinvdLote:"+hinvdLote+", "+
      "hinvdNombreProducto:"+hinvdNombreProducto+", "+
      "hinvdObservaciones:"+hinvdObservaciones+", "+
      "hinvdProdId:"+hinvdProdId+", "+
      "hinvdRecuento:"+hinvdRecuento+", "+
      "hinvdRegularizacion:"+hinvdRegularizacion+
    "]";
  }

  private String hinvdHinvId = "";
  public String getHinvdHinvId()
  {
    return hinvdHinvId;
  }
  public void setHinvdHinvId(String hinvdHinvId)
  {
    this.hinvdHinvId = hinvdHinvId;
  }

  private String hinvdId = "";
  public String getHinvdId()
  {
    return hinvdId;
  }
  public void setHinvdId(String hinvdId)
  {
    this.hinvdId = hinvdId;
    this.pk_identificador_ = hinvdId;
  }

  private String hinvdLote = "";
  public String getHinvdLote()
  {
    return hinvdLote;
  }
  public void setHinvdLote(String hinvdLote)
  {
    this.hinvdLote = hinvdLote;
  }

  private String hinvdNombreProducto = "";
  public String getHinvdNombreProducto()
  {
    return hinvdNombreProducto;
  }
  public void setHinvdNombreProducto(String hinvdNombreProducto)
  {
    this.hinvdNombreProducto = hinvdNombreProducto;
  }

  private String hinvdObservaciones = "";
  public String getHinvdObservaciones()
  {
    return hinvdObservaciones;
  }
  public void setHinvdObservaciones(String hinvdObservaciones)
  {
    this.hinvdObservaciones = hinvdObservaciones;
  }

  private String hinvdProdId = "";
  public String getHinvdProdId()
  {
    return hinvdProdId;
  }
  public void setHinvdProdId(String hinvdProdId)
  {
    this.hinvdProdId = hinvdProdId;
  }

  private String hinvdRecuento = "";
  public String getHinvdRecuento()
  {
    return hinvdRecuento;
  }
  public void setHinvdRecuento(String hinvdRecuento)
  {
    this.hinvdRecuento = hinvdRecuento;
  }

  private String hinvdRegularizacion = "";
  public String getHinvdRegularizacion()
  {
    return hinvdRegularizacion;
  }
  public void setHinvdRegularizacion(String hinvdRegularizacion)
  {
    this.hinvdRegularizacion = hinvdRegularizacion;
  }
}
