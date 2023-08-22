package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class ProductosEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="productos";
  public static final String ALL_COLUMNS="productos.prod_cantidad_por_caja,productos.prod_descuento,productos.prod_fam_id,productos.prod_id,productos.prod_iva,productos.prod_nombre,productos.prod_peso_total,productos.prod_peso_masa,productos.prod_precio_general,productos.prod_referencia,productos.prod_stock,productos.prod_stock_minimo,productos.prod_de_venta,productos.prod_codigo_barras,productos.prod_observaciones";
  public static final String PRODCANTIDADPORCAJA="prod_cantidad_por_caja";
  public static final String PRODDESCUENTO="prod_descuento";
  public static final String PRODFAMID="prod_fam_id";
  public static final String PRODID="prod_id";
  public static final String PRODIVA="prod_iva";
  public static final String PRODNOMBRE="prod_nombre";
  public static final String PRODPESOTOTAL="prod_peso_total";
  public static final String PRODPESOMASA="prod_peso_masa";
  public static final String PRODPRECIOGENERAL="prod_precio_general";
  public static final String PRODREFERENCIA="prod_referencia";
  public static final String PRODSTOCK="prod_stock";
  public static final String PRODSTOCKMINIMO="prod_stock_minimo";
  public static final String PRODDEVENTA="prod_de_venta";
  public static final String PRODCODIGOBARRAS="prod_codigo_barras";
  public static final String PRODOBSERVACIONES="prod_observaciones";
  public static final String PRIMARY_KEY="prod_id";

  protected ProductosEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE productos SET prod_cantidad_por_caja=?,prod_descuento=?,prod_fam_id=?,prod_id=?,prod_iva=?,prod_nombre=?,prod_peso_total=?,prod_precio_general=?,prod_referencia=?,prod_stock=?,prod_stock_minimo=?,prod_peso_masa=?,prod_de_venta=?,prod_codigo_barras=?,prod_observaciones=? WHERE prod_id=?");
    sentencia.setString(1, quitar_comas(prodCantidadPorCaja));
    sentencia.setString(2, quitar_comas(prodDescuento));
    sentencia.setString(3, prodFamId);
    sentencia.setString(4, prodId);
    sentencia.setString(5, quitar_comas(prodIva));
    sentencia.setString(6, prodNombre);
    sentencia.setString(7, quitar_comas(prodPesoTotal));
    sentencia.setString(8, quitar_comas(prodPrecioGeneral));
    sentencia.setString(9, prodReferencia);
    sentencia.setString(10, quitar_comas(prodStock));
    sentencia.setString(11, quitar_comas(prodStockMinimo));
    sentencia.setString(12, quitar_comas(prodPesoMasa));
    sentencia.setString(13, prodDeVenta);
    sentencia.setString(14, prodCodigoBarras);
    sentencia.setString(15, prodObservaciones);
    sentencia.setString(16, prodId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO productos (prod_cantidad_por_caja,prod_descuento,prod_fam_id,prod_iva,prod_nombre,prod_peso_total,prod_precio_general,prod_referencia,prod_stock,prod_stock_minimo,prod_peso_masa,prod_de_venta, prod_codigo_barras,prod_observaciones) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, quitar_comas(prodCantidadPorCaja));
    sentencia.setString(2, quitar_comas(prodDescuento));
    sentencia.setString(3, prodFamId);
    //sentencia.setString(4, prodId);
    sentencia.setString(4, quitar_comas(prodIva));
    sentencia.setString(5, prodNombre);
    sentencia.setString(6, quitar_comas(prodPesoTotal));
    sentencia.setString(7, quitar_comas(prodPrecioGeneral));
    sentencia.setString(8, prodReferencia);
    sentencia.setString(9, quitar_comas(prodStock));
    sentencia.setString(10, quitar_comas(prodStockMinimo));
    sentencia.setString(11, quitar_comas(prodPesoMasa));
    sentencia.setString(12, prodDeVenta);
    sentencia.setString(13, prodCodigoBarras);
    sentencia.setString(14, prodObservaciones);
    
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM productos WHERE prod_id=?");
    sentencia.setString(1, prodId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "productos["+
      "prodCantidadPorCaja:"+prodCantidadPorCaja+", "+
      "prodDescuento:"+prodDescuento+", "+
      "prodFamId:"+prodFamId+", "+
      "prodId:"+prodId+", "+
      "prodIva:"+prodIva+", "+
      "prodNombre:"+prodNombre+", "+
      "prodPesoTotal:"+prodPesoTotal+", "+
      "prodPrecioGeneral:"+prodPrecioGeneral+", "+
      "prodReferencia:"+prodReferencia+", "+
      "prodStock:"+prodStock+", "+
      "prodStockMinimo:"+prodStockMinimo+", "+
      "prodCodigoBarras:"+prodCodigoBarras+", "+
      "prodObservaciones:"+prodObservaciones+
    "]";
  }

  private String prodCantidadPorCaja = "";
  public String getProdCantidadPorCaja()
  {
    return prodCantidadPorCaja;
  }
  public void setProdCantidadPorCaja(String prodCantidadPorCaja)
  {
    this.prodCantidadPorCaja = prodCantidadPorCaja;
  }

  private String prodDescuento = "";
  public String getProdDescuento()
  {
    return prodDescuento;
  }
  public void setProdDescuento(String prodDescuento)
  {
    this.prodDescuento = prodDescuento;
  }

  private String prodFamId = "";
  public String getProdFamId()
  {
    return prodFamId;
  }
  public void setProdFamId(String prodFamId)
  {
    this.prodFamId = prodFamId;
  }

  private String prodId = "";
  public String getProdId()
  {
    return prodId;
  }
  public void setProdId(String prodId)
  {
    this.prodId = prodId;
    this.pk_identificador_ = prodId;
  }

  private String prodIva = "";
  public String getProdIva()
  {
    return prodIva;
  }
  public void setProdIva(String prodIva)
  {
    this.prodIva = prodIva;
  }

  private String prodNombre = "";
  public String getProdNombre()
  {
    return prodNombre;
  }
  public void setProdNombre(String prodNombre)
  {
    this.prodNombre = prodNombre;
  }

  private String prodPesoTotal = "";
  public String getProdPesoTotal()
  {
    return prodPesoTotal;
  }
  public void setProdPesoTotal(String prodPesoTotal)
  {
    this.prodPesoTotal = prodPesoTotal;
  }

  private String prodPesoMasa = "";
  public String getProdPesoMasa()
  {
    return prodPesoMasa;
  }
  public void setProdPesoMasa(String prodPesoMasa)
  {
    this.prodPesoMasa = prodPesoMasa;
  }
  
  private String prodPrecioGeneral = "";
  public String getProdPrecioGeneral()
  {
    return prodPrecioGeneral;
  }
  public void setProdPrecioGeneral(String prodPrecioGeneral)
  {
    this.prodPrecioGeneral = prodPrecioGeneral;
  }

  private String prodReferencia = "";
  public String getProdReferencia()
  {
    return prodReferencia;
  }
  public void setProdReferencia(String prodReferencia)
  {
    this.prodReferencia = prodReferencia;
  }

  private String prodStock = "";
  public String getProdStock()
  {
    return prodStock;
  }
  public void setProdStock(String prodStock)
  {
    this.prodStock = prodStock;
  }

  private String prodStockMinimo = "";
  public String getProdStockMinimo()
  {
    return prodStockMinimo;
  }
  public void setProdStockMinimo(String prodStockMinimo)
  {
    this.prodStockMinimo = prodStockMinimo;
  }
  
  private String prodDeVenta = "";
  public String getProdDeVenta()
  {
    return prodDeVenta;
  }
  public void setProdDeVenta(String prodDeVenta)
  {
    this.prodDeVenta = prodDeVenta;
  }
  
  private String prodCodigoBarras = "";
  public String getProdCodigoBarras()
  {
    return prodCodigoBarras;
  }
  public void setProdCodigoBarras(String prodCodigoBarras)
  {
    this.prodCodigoBarras = prodCodigoBarras;
  }
  
  private String prodObservaciones = "";
  public String getProdObservaciones()
  {
    return prodObservaciones;
  }
  public void setProdObservaciones(String prodObservaciones)
  {
    this.prodObservaciones = prodObservaciones;
  }
}
