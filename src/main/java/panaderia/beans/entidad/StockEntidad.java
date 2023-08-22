package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;
import utils.SQLManager;

public class StockEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="stock";
  public static final String ALL_COLUMNS="stock.st_cantidad_final,stock.st_cantidad_inicial,stock.st_f_caducidad,stock.st_id,stock.st_prod_id,stock.st_regularizacion,stock.st_salidas,stock.st_prod_lote,stock.st_prod_origen";
  public static final String STCANTIDADFINAL="st_cantidad_final";
  public static final String STCANTIDADINICIAL="st_cantidad_inicial";
  public static final String STFCADUCIDAD="st_f_caducidad";
  public static final String STID="st_id";
  public static final String STPRODID="st_prod_id";
  public static final String STREGULARIZACION="st_regularizacion";
  public static final String STSALIDAS="st_salidas";
  public static final String STPRODLOTE="st_prod_lote";
  public static final String STPRODORIGEN="st_prod_origen";
  public static final String PRIMARY_KEY="st_id";

//Rango de Fechas para las busquedas
  public static final String STFECHADESDE="st_fecha_desde";
  public static final String STFECHAHASTA="st_fecha_hasta";
  public static final String STSTOCKCERO = "st_stock_cero";
  
  protected StockEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE stock SET st_cantidad_final=?,st_cantidad_inicial=?,st_f_caducidad=?,st_prod_id=?,st_regularizacion=?,st_salidas=?, st_prod_lote=?, st_prod_origen=? WHERE st_id=?");
    sentencia.setString(1, quitar_comas(stCantidadFinal));
    sentencia.setString(2, quitar_comas(stCantidadInicial));
    sentencia.setTimestamp(3, stFCaducidad);
    //sentencia.setString(4, stId);
    sentencia.setString(4, stProdId);
    sentencia.setString(5, quitar_comas(stRegularizacion));
    sentencia.setString(6, quitar_comas(stSalidas));
    sentencia.setString(7, stProdLote);
    sentencia.setString(8, stProdOrigen);
    sentencia.setString(9, stId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO stock (st_cantidad_final,st_cantidad_inicial,st_f_caducidad,st_prod_id,st_regularizacion,st_salidas,st_prod_lote,st_prod_origen) VALUES (?,?,?,?,?,?,?,?)");
    sentencia.setString(1, quitar_comas(stCantidadFinal));
    sentencia.setString(2, quitar_comas(stCantidadInicial));
    sentencia.setTimestamp(3, stFCaducidad);
    //sentencia.setString(4, stId);
    sentencia.setString(4, stProdId);
    sentencia.setString(5, quitar_comas(stRegularizacion));
    sentencia.setString(6, quitar_comas(stSalidas));
    sentencia.setString(7, stProdLote);
    sentencia.setString(8, stProdOrigen);
    sentencia.executeUpdate();
    
    int autoIncKeyFromApi = -1;
	
    ResultSet rs = sentencia.getGeneratedKeys();

    if (rs.next()) {
        autoIncKeyFromApi = rs.getInt(1);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM stock WHERE st_id=?");
    sentencia.setString(1, stId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "stock["+
      "stCantidadFinal:"+stCantidadFinal+", "+
      "stCantidadInicial:"+stCantidadInicial+", "+
      "stFCaducidad:"+utils.Utils.date2String(stFCaducidad, "dd/MM/yyyy HH:mm:ss")+", "+
      "stId:"+stId+", "+
      "stProdId:"+stProdId+", "+
      "stRegularizacion:"+stRegularizacion+", "+
      "stSalidas:"+stSalidas+
      "stProdLote:"+stProdLote+
      "stProdLote:"+stProdOrigen+
    "]";
  }

  private String stCantidadFinal = "";
  public String getStCantidadFinal()
  {
    return stCantidadFinal;
  }
  public void setStCantidadFinal(String stCantidadFinal)
  {
    this.stCantidadFinal = stCantidadFinal;
  }

  private String stCantidadInicial = "";
  public String getStCantidadInicial()
  {
    return stCantidadInicial;
  }
  public void setStCantidadInicial(String stCantidadInicial)
  {
    this.stCantidadInicial = stCantidadInicial;
  }

  private java.sql.Timestamp stFCaducidad = null;
  public java.util.Date getStFCaducidad()
  {
    if(stFCaducidad==null){ return null; }
    else{ return stFCaducidad; }
  }
  public void setStFCaducidad(java.sql.Timestamp stFCaducidad)
  {
    if(stFCaducidad==null){ this.stFCaducidad = null; }
    else{ this.stFCaducidad = new java.sql.Timestamp(stFCaducidad.getTime()); }
  }
  public String getStFCaducidad(String formato)
  {
    return utils.Utils.date2String(stFCaducidad, formato);
  }
  public void setStFCaducidad(String stFCaducidad, String formato)
  {
    if( stFCaducidad==null || stFCaducidad.equals("") )
    { this.stFCaducidad = null; }
    else
    {
      try
      {
        this.stFCaducidad = new java.sql.Timestamp(utils.Utils.string2Date(stFCaducidad, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private String stId = "";
  public String getStId()  
  {
    return stId;
  }
  public void setStId(String stId)
  {
    this.stId = stId;
    this.pk_identificador_ = stId;
  }

  private String stProdId = "";
  public String getStProdId()
  {
    return stProdId;
  }
  public void setStProdId(String stProdId)
  {
    this.stProdId = stProdId;
  }

  private String stRegularizacion = "";
  public String getStRegularizacion()
  {
    return stRegularizacion;
  }
  public void setStRegularizacion(String stRegularizacion)
  {
    this.stRegularizacion = stRegularizacion;
  }

  private String stSalidas = "";
  public String getStSalidas()
  {
    return stSalidas;
  }
  public void setStSalidas(String stSalidas)
  {
    this.stSalidas = stSalidas;
  }
  
  
  private java.sql.Timestamp stFechaDesde = null;
  public java.util.Date getStFechaDesde()
  {
    if(stFechaDesde==null){ return null; }
    else{ return stFechaDesde; }
  }
  public void setStFechaDesde(java.sql.Timestamp stFechaDesde)
  {
    if(stFechaDesde==null){ this.stFechaDesde = null; }
    else{ this.stFechaDesde = new java.sql.Timestamp(stFechaDesde.getTime()); }
  }
  public String getStFechaDesde(String formato)
  {
    return utils.Utils.date2String(stFechaDesde, formato);
  }
  public void setStFechaDesde(String stFechaDesde, String formato)
  {
    if( stFechaDesde==null || stFechaDesde.equals("") )
    { this.stFechaDesde = null; }
    else
    {
      try
      {
        this.stFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(stFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private java.sql.Timestamp stFechaHasta = null;
  public java.util.Date getStFechaHasta()
  {
    if(stFechaHasta==null){ return null; }
    else{ return stFechaHasta; }
  }
  public void setStFechaHasta(java.sql.Timestamp stFechaHasta)
  {
    if(stFechaHasta==null){ this.stFechaHasta = null; }
    else{ this.stFechaHasta = new java.sql.Timestamp(stFechaHasta.getTime()); }
  }
  public String getStFechaHasta(String formato)
  {
    return utils.Utils.date2String(stFechaHasta, formato);
  }
  public void setStFechaHasta(String stFechaHasta, String formato)
  {
    if( stFechaHasta==null || stFechaHasta.equals("") )
    { this.stFechaHasta = null; }
    else
    {
      try
      {
        this.stFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(stFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private String[] fcDatosRelacionados;
  public String[] getFcDatosRelacionados()
  {
	  return fcDatosRelacionados;
  }
  public void setFcDatosRelacionados(String[] fcDatosRelacionados)
  {
    this.fcDatosRelacionados = fcDatosRelacionados;
  }
  
  private String[] fcdDatosRelacionados;
  public String[] getFcdDatosRelacionados()
  {
	  return fcdDatosRelacionados;
  }
  public void setFcdDatosRelacionados(String[] fcdDatosRelacionados)
  {
    this.fcdDatosRelacionados = fcdDatosRelacionados;
  }
  
  
  private String stStockCero = "off";
  public String getStStockCero()
  {
    return stStockCero;
  }
  public void setStStockCero(String stStockCero)
  {
    this.stStockCero = stStockCero;
  }
  
  private String stProdLote = "off";
  public String getStProdLote()
  {
    return stProdLote;
  }
  public void setStProdLote(String stProdLote)
  {
    this.stProdLote = stProdLote;
  }
  
  private String stProdOrigen = "";
  public String getStProdOrigen()
  {
    return stProdOrigen;
  }
  public void setStProdOrigen(String stProdOrigen)
  {
    this.stProdOrigen = stProdOrigen;
  }
}
