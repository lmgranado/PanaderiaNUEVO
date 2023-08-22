package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class FacturasVentaDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="facturas_venta_detalle";
  public static final String ALL_COLUMNS="facturas_venta_detalle.fvd_descuento,facturas_venta_detalle.fvd_cantidad,facturas_venta_detalle.fvd_fv_id,facturas_venta_detalle.fvd_id,facturas_venta_detalle.fvd_importe,facturas_venta_detalle.fvd_iva,facturas_venta_detalle.fvd_linea,facturas_venta_detalle.fvd_precio_venta,facturas_venta_detalle.fvd_producto,facturas_venta_detalle.fvd_st_id,facturas_venta_detalle.fvd_lote";
  public static final String FVDCANTIDAD="fvd_cantidad";
  public static final String FVDFVID="fvd_fv_id";
  public static final String FVDID="fvd_id";
  public static final String FVDIMPORTE="fvd_importe";
  public static final String FVDIVA="fvd_iva";
  public static final String FVDLINEA="fvd_linea";
  public static final String FVDPRECIOVENTA="fvd_precio_venta";
  public static final String FVDREFERENCIA="fvd_referencia";
  public static final String FVDDESCUENTO="fvd_descuento";
  public static final String FVDPRODUCTO="fvd_producto";
  public static final String FVDPRODID="fvd_prod_id";
  public static final String PRODNOMBRE="prod_nombre";
  public static final String PRODREFERENCIA="prod_referencia";
  public static final String PRODPRECIOGENERAL="prod_precio_general";
  public static final String FVDSTID="fvd_st_id";
  //Luis Miguel
  public static final String FVDLOTE="fvd_lote";

  public static final String TIPO = "tipo"; //No esta en base de datos, solo lo usamos para completar el bean
  
  public static final String PRIMARY_KEY="fvd_id";

  protected FacturasVentaDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE facturas_venta_detalle SET fvd_cantidad=?,fvd_fv_id=?,fvd_importe=?,fvd_iva=?,fvd_linea=?,fvd_precio_venta=?,fvd_producto=?,fvd_referencia=?,fvd_descuento=?,fvd_st_id=?,fvd_lote=? WHERE fvd_id=?");
    sentencia.setString(1, quitar_comas(fvdCantidad));
    sentencia.setString(2, fvdFvId);
    //sentencia.setString(3, fvdId);
    sentencia.setString(3, quitar_comas(fvdImporte));
    sentencia.setString(4, quitar_comas(fvdIva));
    sentencia.setString(5, fvdLinea);
    sentencia.setString(6, quitar_comas(fvdPrecioVenta));
    sentencia.setString(7, fvdProducto);
    sentencia.setString(8, fvdReferencia);
    sentencia.setString(9, quitar_comas(fvdDescuento));
    sentencia.setString(10, fvdStId);
    sentencia.setString(11, fvdLote);
    sentencia.setString(12, fvdId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO facturas_venta_detalle (fvd_cantidad,fvd_fv_id,fvd_importe,fvd_iva,fvd_linea,fvd_precio_venta,fvd_producto,fvd_descuento,fvd_st_id,fvd_lote) VALUES (?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, quitar_comas(fvdCantidad));
    sentencia.setString(2, fvdFvId);
    //sentencia.setString(3, fvdId);
    sentencia.setString(3, quitar_comas(fvdImporte));
    sentencia.setString(4, quitar_comas(fvdIva));
    sentencia.setString(5, fvdLinea);
    sentencia.setString(6, quitar_comas(fvdPrecioVenta));
    sentencia.setString(7, fvdProducto);
    sentencia.setString(8, quitar_comas(fvdDescuento));
    sentencia.setString(9, fvdStId);
    sentencia.setString(10, fvdLote);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_venta_detalle WHERE fvd_id=?");
    sentencia.setString(1, fvdId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "facturas_venta_detalle["+
      "fvdCantidad:"+fvdCantidad+", "+
      "fvdFvId:"+fvdFvId+", "+
      "fvdId:"+fvdId+", "+
      "fvdImporte:"+fvdImporte+", "+
      "fvdIva:"+fvdIva+", "+
      "fvdLinea:"+fvdLinea+", "+
      "fvdPrecioVenta:"+fvdPrecioVenta+", "+
      "fvdProducto:"+fvdProducto+
    "]";
  }

  
  private String fvdProdId = "";
  public String getFvdProdId()
  {
    return fvdProdId;
  }
  public void setFvdProdId(String fvdProdId)
  {
    this.fvdProdId = fvdProdId;
  }
  
  private String fvdCantidad = "";
  public String getFvdCantidad()
  {
    return fvdCantidad;
  }
  public void setFvdCantidad(String fvdCantidad)
  {
    this.fvdCantidad = fvdCantidad;
  }

  private String fvdFvId = "";
  public String getFvdFvId()
  {
    return fvdFvId;
  }
  public void setFvdFvId(String fvdFvId)
  {
    this.fvdFvId = fvdFvId;
  }

  private String fvdId = "";
  public String getFvdId()
  {
    return fvdId;
  }
  public void setFvdId(String fvdId)
  {
    this.fvdId = fvdId;
    this.pk_identificador_ = fvdId;
  }

  private String fvdImporte = "";
  public String getFvdImporte()
  {
    return fvdImporte;
  }
  public void setFvdImporte(String fvdImporte)
  {
    this.fvdImporte = fvdImporte;
  }

  private String fvdIva = "";
  public String getFvdIva()
  {
    return fvdIva;
  }
  public void setFvdIva(String fvdIva)
  {
    this.fvdIva = fvdIva;
  }

  private String fvdLinea = "";
  public String getFvdLinea()
  {
    return fvdLinea;
  }
  public void setFvdLinea(String fvdLinea)
  {
    this.fvdLinea = fvdLinea;
  }

  private String fvdPrecioVenta = "";
  public String getFvdPrecioVenta()
  {
    return fvdPrecioVenta;
  }
  public void setFvdPrecioVenta(String fvdPrecioVenta)
  {
    this.fvdPrecioVenta = fvdPrecioVenta;
  }

  private String fvdProducto = "";
  public String getFvdProducto()
  {
    return fvdProducto;
  }
  public void setFvdProducto(String fvdProducto)
  {
    this.fvdProducto = fvdProducto;
  }
  
    private String fvdReferencia = "";
	public String getFvdReferencia() {
		return fvdReferencia;
	}
	
	public void setFvdReferencia(String fvdReferencia) {
		this.fvdReferencia = fvdReferencia;
	}
	
	
	private String fvdDescuento = "";
	public String getFvdDescuento() {
		return fvdDescuento;
	}
	
	public void setFvdDescuento(String fvdDescuento) {
		this.fvdDescuento = fvdDescuento;
	}
		
		
	private String[] prodDatosRelacionados;
	  public String[] getProdDatosRelacionados()
	  {
		  return prodDatosRelacionados;
	  }
	  public void setProdDatosRelacionados(String[] prodDatosRelacionados)
	  {
	    this.prodDatosRelacionados = prodDatosRelacionados;
	  } 
	  

	private String tipo = "";

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	private String fvdStId = "";

	public String getFvdStId() {
		return fvdStId;
	}

	public void setFvdStId(String fvdStId) {
		this.fvdStId = fvdStId;
	}
	
	  private String fvdLote;
	  public String getFvdLote()
	  {
	    return fvdLote;
	  }
	  public void setFvdLote(String fvdLote)
	  {
	    this.fvdLote = fvdLote;
	  }
	  
	  //Este se utiliza de una manera especial para insertar el lote desde la tabla stock
	  //en el metodo por ejemplo getFacturasVentaDetalleByFvdFvIdWithStock
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
