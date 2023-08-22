package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class FacturasCompraDetalleEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="facturas_compra_detalle";
  public static final String ALL_COLUMNS="facturas_compra_detalle.fcd_cantidad,facturas_compra_detalle.fcd_descuento,facturas_compra_detalle.fcd_f_caducidad,facturas_compra_detalle.fcd_f_entrada,facturas_compra_detalle.fcd_fc_id,facturas_compra_detalle.fcd_id,facturas_compra_detalle.fcd_importe,facturas_compra_detalle.fcd_iva,facturas_compra_detalle.fcd_linea,facturas_compra_detalle.fcd_lote,facturas_compra_detalle.fcd_precio_compra,facturas_compra_detalle.fcd_producto,facturas_compra_detalle.fcd_referencia,facturas_compra_detalle.fcd_st_id,facturas_compra_detalle.fcd_prod_origen";
  public static final String FCDCANTIDAD="fcd_cantidad";
  public static final String FCDDESCUENTO="fcd_descuento";
  public static final String FCDFCADUCIDAD="fcd_f_caducidad";
  public static final String FCDFENTRADA="fcd_f_entrada";
  public static final String FCDFCID="fcd_fc_id";
  public static final String FCDID="fcd_id";
  public static final String FCDIMPORTE="fcd_importe";
  public static final String FCDIVA="fcd_iva";
  public static final String FCDLINEA="fcd_linea";
  public static final String FCDLOTE="fcd_lote";
  public static final String FCDPRECIOCOMPRA="fcd_precio_compra";
  public static final String FCDPRODUCTO="fcd_producto";
  public static final String FCDREFERENCIA="fcd_referencia";
  public static final String FCDPRODORIGEN="fcd_prod_origen";
  public static final String FCDSTID="fcd_st_id";
  public static final String PRIMARY_KEY="fcd_id";

  protected FacturasCompraDetalleEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE facturas_compra_detalle SET fcd_cantidad=?,fcd_descuento=?,fcd_f_caducidad=?,fcd_f_entrada=?,fcd_fc_id=?,fcd_importe=?,fcd_iva=?,fcd_linea=?,fcd_lote=?,fcd_precio_compra=?,fcd_producto=?,fcd_referencia=?,fcd_st_id=?,fcd_prod_origen=? WHERE fcd_id=?");
    sentencia.setString(1, quitar_comas(fcdCantidad));
    sentencia.setString(2, quitar_comas(fcdDescuento));
    sentencia.setTimestamp(3, fcdFCaducidad);
    sentencia.setTimestamp(4, fcdFEntrada);
    sentencia.setString(5, fcdFcId);
    //sentencia.setString(6, fcdId);
    sentencia.setString(6, quitar_comas(fcdImporte));
    sentencia.setString(7, quitar_comas(fcdIva));
    sentencia.setString(8, fcdLinea);
    sentencia.setString(9, fcdLote);
    sentencia.setString(10, quitar_comas(fcdPrecioCompra));
    sentencia.setString(11, fcdProducto);
    sentencia.setString(12, fcdReferencia);
    sentencia.setString(13, fcdStId);
    sentencia.setString(14, fcdProdOrigen);
    sentencia.setString(15, fcdId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  
  //Luis Miguel -> Funcion que inserta el detalle de la factura de compra, pero actualiza en la tabla
  //de Stock el registro, al existir ya dicho producto y lote 
  public int insertaDetalleNuevo()
  {
    SQLManager manager = new SQLManager(); 
    Connection conexion = manager.getConnection(); 
    try
    {
      //Se actualiza el registro de stock existente y luego insertamos el detalle
      int idStock = getStock().actualiza(conexion);
      //Insertamos el st_id para que se pueda realizar inserccion del detalla en la tabla facturas_compra_detalle
      this.setFcdStId(  getStock().getStId() ) ;
      return inserta(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); 
    	try{
    		conexion.rollback(); 
    	}catch (Exception esql) {
			// TODO: handle exception
		}
    	return -1; 
    }
    finally{ manager.closeConnection();  }
  }
  
  public int inserta()
  {
    SQLManager manager = new SQLManager(); 
    Connection conexion = manager.getConnection(); 
    try
    {
      //Controlamos que se insertar ambas cosas para que se haga commit
      int idStock = getStock().inserta(conexion);
      this.setFcdStId(Integer.toString(idStock));
      return inserta(conexion);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); 
    	try{
    		conexion.rollback(); 
    	}catch (Exception esql) {
			// TODO: handle exception
		}
    	return -1; 
    }
    finally{ manager.closeConnection();  }
  }

  public int inserta(Connection conexion) throws SQLException
  {
	
	//Antes de insertar este detalle, insertamos el stock  
	  
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO facturas_compra_detalle (fcd_cantidad,fcd_descuento,fcd_f_caducidad,fcd_f_entrada,fcd_fc_id,fcd_importe,fcd_iva,fcd_linea,fcd_lote,fcd_precio_compra,fcd_producto,fcd_referencia,fcd_st_id,fcd_prod_origen) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, quitar_comas(fcdCantidad) );
    sentencia.setString(2, quitar_comas(fcdDescuento));
    sentencia.setTimestamp(3, fcdFCaducidad);
    sentencia.setTimestamp(4, fcdFEntrada);
    sentencia.setString(5, fcdFcId);
    //sentencia.setString(6, fcdId);
    sentencia.setString(6, quitar_comas(fcdImporte));
    sentencia.setString(7, quitar_comas(fcdIva));
    sentencia.setString(8, fcdLinea);
    sentencia.setString(9, fcdLote);
    sentencia.setString(10, quitar_comas(fcdPrecioCompra));
    sentencia.setString(11, fcdProducto);
    sentencia.setString(12, fcdReferencia);
    sentencia.setString(13, fcdStId);
    sentencia.setString(14, fcdProdOrigen);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_compra_detalle WHERE fcd_id=?");
    sentencia.setString(1, fcdId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "facturas_compra_detalle["+
      "fcdCantidad:"+fcdCantidad+", "+
      "fcdDescuento:"+fcdDescuento+", "+
      "fcdFCaducidad:"+utils.Utils.date2String(fcdFCaducidad, "dd/MM/yyyy HH:mm:ss")+", "+
      "fcdFEntrada:"+utils.Utils.date2String(fcdFEntrada, "dd/MM/yyyy HH:mm:ss")+", "+
      "fcdFcId:"+fcdFcId+", "+
      "fcdId:"+fcdId+", "+
      "fcdImporte:"+fcdImporte+", "+
      "fcdIva:"+fcdIva+", "+
      "fcdLinea:"+fcdLinea+", "+
      "fcdLote:"+fcdLote+", "+
      "fcdPrecioCompra:"+fcdPrecioCompra+", "+
      "fcdProducto:"+fcdProducto+", "+
      "fcdReferencia:"+fcdReferencia+", "+
      "fcdStId:"+fcdStId+
      "fcdProdOrigen:"+fcdProdOrigen+
    "]";
  }

  private String fcdCantidad = "";
  public String getFcdCantidad()
  {
    return fcdCantidad;
  }
  public void setFcdCantidad(String fcdCantidad)
  {
    this.fcdCantidad = fcdCantidad;
  }

  private String fcdDescuento = "";
  public String getFcdDescuento()
  {
    return fcdDescuento;
  }
  public void setFcdDescuento(String fcdDescuento)
  {
    this.fcdDescuento = fcdDescuento;
  }

  private java.sql.Timestamp fcdFCaducidad = null;
  public java.util.Date getFcdFCaducidad()
  {
    if(fcdFCaducidad==null){ return null; }
    else{ return fcdFCaducidad; }
  }
  public void setFcdFCaducidad(java.sql.Timestamp fcdFCaducidad)
  {
    if(fcdFCaducidad==null){ this.fcdFCaducidad = null; }
    else{ this.fcdFCaducidad = new java.sql.Timestamp(fcdFCaducidad.getTime()); }
  }
  public String getFcdFCaducidad(String formato)
  {
    return utils.Utils.date2String(fcdFCaducidad, formato);
  }
  public void setFcdFCaducidad(String fcdFCaducidad, String formato)
  {
    if( fcdFCaducidad==null || fcdFCaducidad.equals("") )
    { this.fcdFCaducidad = null; }
    else
    {
      try
      {
        this.fcdFCaducidad = new java.sql.Timestamp(utils.Utils.string2Date(fcdFCaducidad, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private java.sql.Timestamp fcdFEntrada = null;
  public java.util.Date getFcdFEntrada()
  {
    if(fcdFEntrada==null){ return null; }
    else{ return fcdFEntrada; }
  }
  public void setFcdFEntrada(java.sql.Timestamp fcdFEntrada)
  {
    if(fcdFEntrada==null){ this.fcdFEntrada = null; }
    else{ this.fcdFEntrada = new java.sql.Timestamp(fcdFEntrada.getTime()); }
  }
  public String getFcdFEntrada(String formato)
  {
    return utils.Utils.date2String(fcdFEntrada, formato);
  }
  public void setFcdFEntrada(String fcdFEntrada, String formato)
  {
    if( fcdFEntrada==null || fcdFEntrada.equals("") )
    { this.fcdFEntrada = null; }
    else
    {
      try
      {
        this.fcdFEntrada = new java.sql.Timestamp(utils.Utils.string2Date(fcdFEntrada, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private String fcdFcId = "";
  public String getFcdFcId()
  {
    return fcdFcId;
  }
  public void setFcdFcId(String fcdFcId)
  {
    this.fcdFcId = fcdFcId;
  }

  private String fcdId = "";
  public String getFcdId()
  {
    return fcdId;
  }
  public void setFcdId(String fcdId)
  {
    this.fcdId = fcdId;
    this.pk_identificador_ = fcdId;
  }

  private String fcdImporte = "";
  public String getFcdImporte()
  {
    return fcdImporte;
  }
  public void setFcdImporte(String fcdImporte)
  {
    this.fcdImporte = fcdImporte;
  }

  private String fcdIva = "";
  public String getFcdIva()
  {
    return fcdIva;
  }
  public void setFcdIva(String fcdIva)
  {
    this.fcdIva = fcdIva;
  }

  private String fcdLinea = "";
  public String getFcdLinea()
  {
    return fcdLinea;
  }
  public void setFcdLinea(String fcdLinea)
  {
    this.fcdLinea = fcdLinea;
  }

  private String fcdLote = "";
  public String getFcdLote()
  {
    return fcdLote;
  }
  public void setFcdLote(String fcdLote)
  {
    this.fcdLote = fcdLote;
  }


  private String fcdPrecioCompra = "";
  public String getFcdPrecioCompra()
  {
    return fcdPrecioCompra;
  }
  public void setFcdPrecioCompra(String fcdPrecioCompra)
  {
    this.fcdPrecioCompra = fcdPrecioCompra;
  }

  private String fcdProducto = "";
  public String getFcdProducto()
  {
    return fcdProducto;
  }
  public void setFcdProducto(String fcdProducto)
  {
    this.fcdProducto = fcdProducto;
  }

  private String fcdReferencia = "";
  public String getFcdReferencia()
  {
    return fcdReferencia;
  }
  public void setFcdReferencia(String fcdReferencia)
  {
    this.fcdReferencia = fcdReferencia;
  }

  private String fcdStId = "";
  public String getFcdStId()
  {
    return fcdStId;
  }
  public void setFcdStId(String fcdStId)
  {
    this.fcdStId = fcdStId;
  }
  
  private Stock stock = new  Stock();
  public Stock getStock()
  {
	if(!stock.getStId().equals("") || !stock.getStProdId().equals(""))  
		return stock;
	else
		return Stock.getStockByStId(this.fcdStId);
  }
  public void setStock(Stock stock)
  {
    this.stock = stock;
  }
  
  private String fcdProdOrigen = "";
  public String getFcdProdOrigen()
  {
    return fcdProdOrigen;
  }
  public void setFcdProdOrigen(String fcdProdOrigen)
  {
    this.fcdProdOrigen = fcdProdOrigen;
  }
  
  private Object cou = new  Object();
  public Object getComponentesUsados()
  {
	return cou;
  }
  public void setComponentesUsados(Object cou){
	  this.cou = cou;
  }
}
