package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class FacturasCompraEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="facturas_compra";
  public static final String ALL_COLUMNS="facturas_compra.fc_fecha,facturas_compra.fc_id,facturas_compra.fc_iva,facturas_compra.fc_numero_factura,facturas_compra.fc_observaciones,facturas_compra.fc_pagada,facturas_compra.fc_pr_id,facturas_compra.fc_total,facturas_compra.fc_cierre";
  public static final String FCFECHA="fc_fecha";
  public static final String FCID="fc_id";
  public static final String FCIVA="fc_iva";
  public static final String FCNUMEROFACTURA="fc_numero_factura";
  public static final String FCOBSERVACIONES="fc_observaciones";
  public static final String FCPAGADA="fc_pagada";
  public static final String FCPRID="fc_pr_id";
  public static final String FCTOTAL="fc_total";
  public static final String FCCIERRE="fc_cierre";
  
  public static final String PRAPELLIDOS="pr_apellidos";
  public static final String PRDIRECCION="pr_direccion";
  public static final String LOCNOMBRE="loc_nombre";
  public static final String PROVNOMBRE="prov_nombre";
  public static final String PRDESCUENTO="pr_descuento";
  public static final String PRNOMBRECOMERCIAL="pr_nombre_comercial";
  public static final String FPDESCRIPCION="fp_descripcion";
  public static final String PRIMARY_KEY="fc_id";
  
  //Rango de Fechas para las busquedas
  public static final String FCFECHADESDE="fc_fecha_desde";
  public static final String FCFECHAHASTA="fc_fecha_hasta";
  
  
  protected FacturasCompraEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE facturas_compra SET fc_fecha=?,fc_iva=?,fc_observaciones=?,fc_pagada=?,fc_pr_id=?,fc_total=?,fc_cierre=? WHERE fc_id=?");
    sentencia.setTimestamp(1, fcFecha);
    //sentencia.setString(2, fcId);
    sentencia.setString(2, quitar_comas(fcIva));
    //sentencia.setString(3, fcNumeroFactura);
    sentencia.setString(3, fcObservaciones);
    sentencia.setString(4, fcPagada);
    sentencia.setString(5, fcPrId);
    sentencia.setString(6, quitar_comas(fcTotal));
    sentencia.setString(7, fcCierre);
    sentencia.setString(8, fcId);
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
    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO facturas_compra (fc_fecha,fc_iva,fc_numero_factura,fc_observaciones,fc_pagada,fc_pr_id,fc_total,fc_cierre) VALUES (?,?,?,?,?,?,?,?)");
    sentencia.setTimestamp(1, fcFecha);
    //sentencia.setString(2, fcId);
    sentencia.setString(2, quitar_comas(fcIva));
    sentencia.setString(3, fcNumeroFactura);
    sentencia.setString(4, fcObservaciones);
    sentencia.setString(5, fcPagada);
    sentencia.setString(6, fcPrId);
    sentencia.setString(7, quitar_comas(fcTotal));
    sentencia.setString(8, fcCierre);
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
    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_compra WHERE fc_id=?");
    sentencia.setString(1, fcId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    return n;
  }

  public String toString()
  {
    return "facturas_compra["+
      "fcFecha:"+utils.Utils.date2String(fcFecha, "dd/MM/yyyy HH:mm:ss")+", "+
      "fcId:"+fcId+", "+
      "fcIva:"+fcIva+", "+
      "fcNumeroFactura:"+fcNumeroFactura+", "+
      "fcObservaciones:"+fcObservaciones+", "+
      "fcPagada:"+fcPagada+", "+
      "fcPrId:"+fcPrId+", "+
      "fcTotal:"+fcTotal+
    "]";
  }

  private java.sql.Timestamp fcFecha = null;
  public java.util.Date getFcFecha()
  {
    if(fcFecha==null){ return null; }
    else{ return fcFecha; }
  }
  public void setFcFecha(java.sql.Timestamp fcFecha)
  {
    if(fcFecha==null){ this.fcFecha = null; }
    else{ this.fcFecha = new java.sql.Timestamp(fcFecha.getTime()); }
  }
  public String getFcFecha(String formato)
  {
    return utils.Utils.date2String(fcFecha, formato);
  }
  public void setFcFecha(String fcFecha, String formato)
  {
    if( fcFecha==null || fcFecha.equals("") )
    { this.fcFecha = null; }
    else
    {
      try
      {
        this.fcFecha = new java.sql.Timestamp(utils.Utils.string2Date(fcFecha, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  
  
  private java.sql.Timestamp fcFechaDesde = null;
  public java.util.Date getFcFechaDesde()
  {
    if(fcFechaDesde==null){ return null; }
    else{ return fcFechaDesde; }
  }
  public void setFcFechaDesde(java.sql.Timestamp fcFechaDesde)
  {
    if(fcFechaDesde==null){ this.fcFechaDesde = null; }
    else{ this.fcFechaDesde = new java.sql.Timestamp(fcFechaDesde.getTime()); }
  }
  public String getFcFechaDesde(String formato)
  {
    return utils.Utils.date2String(fcFechaDesde, formato);
  }
  public void setFcFechaDesde(String fcFechaDesde, String formato)
  {
    if( fcFechaDesde==null || fcFechaDesde.equals("") )
    { this.fcFechaDesde = null; }
    else
    {
      try
      {
        this.fcFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(fcFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private java.sql.Timestamp fcFechaHasta = null;
  public java.util.Date getFcFechaHasta()
  {
    if(fcFechaHasta==null){ return null; }
    else{ return fcFechaHasta; }
  }
  public void setFcFechaHasta(java.sql.Timestamp fcFechaHasta)
  {
    if(fcFechaHasta==null){ this.fcFechaHasta = null; }
    else{ this.fcFechaHasta = new java.sql.Timestamp(fcFechaHasta.getTime()); }
  }
  public String getFcFechaHasta(String formato)
  {
    return utils.Utils.date2String(fcFechaHasta, formato);
  }
  public void setFcFechaHasta(String fcFechaHasta, String formato)
  {
    if( fcFechaHasta==null || fcFechaHasta.equals("") )
    { this.fcFechaHasta = null; }
    else
    {
      try
      {
        this.fcFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(fcFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  
  private String fcId = "";
  public String getFcId()
  {
    return fcId;
  }
  public void setFcId(String fcId)
  {
    this.fcId = fcId;
    this.pk_identificador_ = fcId;
  }

  private String fcIva = "";
  public String getFcIva()
  {
    return fcIva;
  }
  public void setFcIva(String fcIva)
  {
    this.fcIva = fcIva;
  }


  private String fcCierre = "";
  public String getFcCierre()
  {
    return fcCierre;
  }
  public void setFcCierre(String fcCierre)
  {
    this.fcCierre = fcCierre;
  }
  
  private String fcNumeroFactura = "";
  public String getFcNumeroFactura()
  {
    return fcNumeroFactura;
  }
  public void setFcNumeroFactura(String fcNumeroFactura)
  {
    this.fcNumeroFactura = fcNumeroFactura;
  }

  private String fcObservaciones = "";
  public String getFcObservaciones()
  {
    return fcObservaciones;
  }
  public void setFcObservaciones(String fcObservaciones)
  {
    this.fcObservaciones = fcObservaciones;
  }

  private String fcPagada = "";
  public String getFcPagada()
  {
    return fcPagada;
  }
  public void setFcPagada(String fcPagada)
  {
    this.fcPagada = fcPagada;
  }

  private String fcPrId = "";
  public String getFcPrId()
  {
    return fcPrId;
  }
  public void setFcPrId(String fcPrId)
  {
    this.fcPrId = fcPrId;
  }

  private String fcTotal = "";
  public String getFcTotal()
  {
    return fcTotal;
  }
  public void setFcTotal(String fcTotal)
  {
    this.fcTotal = fcTotal;
  }
  

	
  private String[] prDatosRelacionados;
  public String[] getPrDatosRelacionados()
  {
	  return prDatosRelacionados;
  }
  public void setPrDatosRelacionados(String[] prDatosRelacionados)
  {
    this.prDatosRelacionados = prDatosRelacionados;
  } 
}
