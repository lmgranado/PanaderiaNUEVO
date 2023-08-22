package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;


public class NotasEntregaDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="notas_entrega_detalle";
  public static final String ALL_COLUMNS="notas_entrega_detalle.notdet_cantidad,notas_entrega_detalle.notdet_id,notas_entrega_detalle.notdet_importe,notas_entrega_detalle.notdet_linea,notas_entrega_detalle.notdet_not_id,notas_entrega_detalle.notdet_precio,notas_entrega_detalle.notdet_producto,notas_entrega_detalle.notdet_iva,notas_entrega_detalle.notdet_descuento,notas_entrega_detalle.notdet_st_id";
  public static final String NOTDETCANTIDAD="notdet_cantidad";
  public static final String NOTDETID="notdet_id";
  public static final String NOTDETIMPORTE="notdet_importe";
  public static final String NOTDETLINEA="notdet_linea";
  public static final String NOTDETNOTID="notdet_not_id";
  public static final String NOTDETPRECIO="notdet_precio";
  public static final String NOTDETPRODUCTO="notdet_producto";
  public static final String NOTDETIVA="notdet_iva";
  public static final String NOTDETDESCUENTO="notdet_descuento";
  public static final String NOTDETSTID="notdet_st_id";
  public static final String PRIMARY_KEY="notdet_id";

  protected NotasEntregaDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE notas_entrega_detalle SET notdet_cantidad=?,notdet_id=?,notdet_importe=?,notdet_linea=?,notdet_not_id=?,notdet_precio=?,notdet_producto=?,notdet_iva=?,notdet_descuento=?,notdet_st_id=? WHERE notdet_id=?");
    sentencia.setString(1, quitar_comas(notdetCantidad));
    sentencia.setString(2, notdetId);
    sentencia.setString(3, quitar_comas(notdetImporte));
    sentencia.setString(4, notdetLinea);
    sentencia.setString(5, notdetNotId);
    sentencia.setString(6, quitar_comas(notdetPrecio));
    sentencia.setString(7, notdetProducto);
    sentencia.setString(8, quitar_comas(notdetIva));
    sentencia.setString(9, quitar_comas(notdetDescuento));
    sentencia.setString(10, notdetStId);
    sentencia.setString(11, notdetId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO notas_entrega_detalle (notdet_cantidad,notdet_importe,notdet_linea,notdet_not_id,notdet_precio,notdet_producto,notdet_iva,notdet_descuento,notdet_st_id) VALUES (?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, quitar_comas(notdetCantidad));
    sentencia.setString(2, quitar_comas(notdetImporte));
    sentencia.setString(3, notdetLinea);
    sentencia.setString(4, notdetNotId);
    sentencia.setString(5, quitar_comas(notdetPrecio));
    sentencia.setString(6, notdetProducto);
    sentencia.setString(7, quitar_comas(notdetIva));
    sentencia.setString(8, quitar_comas(notdetDescuento));
    sentencia.setString(9, notdetStId);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM notas_entrega_detalle WHERE notdet_id=?");
    sentencia.setString(1, notdetId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "notas_entrega_detalle["+
      "notdetCantidad:"+notdetCantidad+", "+
      "notdetId:"+notdetId+", "+
      "notdetImporte:"+notdetImporte+", "+
      "notdetLinea:"+notdetLinea+", "+
      "notdetNotId:"+notdetNotId+", "+
      "notdetPrecio:"+notdetPrecio+", "+
      "notdetProducto:"+notdetProducto+
    "]";
  }

  private String notdetCantidad = "";
  public String getNotdetCantidad()
  {
    return notdetCantidad;
  }
  public void setNotdetCantidad(String notdetCantidad)
  {
    this.notdetCantidad = notdetCantidad;
  }

  private String notdetId = "";
  public String getNotdetId()
  {
    return notdetId;
  }
  public void setNotdetId(String notdetId)
  {
    this.notdetId = notdetId;
    this.pk_identificador_ = notdetId;
  }

  private String notdetImporte = "";
  public String getNotdetImporte()
  {
    return notdetImporte;
  }
  public void setNotdetImporte(String notdetImporte)
  {
    this.notdetImporte = notdetImporte;
  }

  private String notdetLinea = "";
  public String getNotdetLinea()
  {
    return notdetLinea;
  }
  public void setNotdetLinea(String notdetLinea)
  {
    this.notdetLinea = notdetLinea;
  }

  private String notdetNotId = "";
  public String getNotdetNotId()
  {
    return notdetNotId;
  }
  public void setNotdetNotId(String notdetNotId)
  {
    this.notdetNotId = notdetNotId;
  }

  private String notdetPrecio = "";
  public String getNotdetPrecio()
  {
    return notdetPrecio;
  }
  public void setNotdetPrecio(String notdetPrecio)
  {
    this.notdetPrecio = notdetPrecio;
  }

  private String notdetProducto = "";
  public String getNotdetProducto()
  {
    return notdetProducto;
  }
  public void setNotdetProducto(String notdetProducto)
  {
    this.notdetProducto = notdetProducto;
  }

  private String notdetIva = "";
  public String getNotdetIva()
  {
    return notdetIva;
  }
  public void setNotdetIva(String notdetIva)
  {
    this.notdetIva = notdetIva;
  }
  

  private String notdetDescuento = "";
  public String getNotdetDescuento()
  {
    return notdetDescuento;
  }
  public void setNotdetDescuento(String notdetDescuento)
  {
    this.notdetDescuento = notdetDescuento;
  }

  private String notdetStId = "";
  public String getNotdetStId()
  {
    return notdetStId;
  }
  public void setNotdetStId(String notdetStId)
  {
    this.notdetStId = notdetStId;
  }
  
  private String lote;
  public String getLote()
  {
    return lote;
  }
  public void setLote(String lote)
  {
    this.lote = lote;
  }
}
