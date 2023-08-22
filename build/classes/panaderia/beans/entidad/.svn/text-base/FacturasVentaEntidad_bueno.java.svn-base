package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;
import utils.Utils;


public class FacturasVentaEntidad_bueno extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="facturas_venta";
  public static final String ALL_COLUMNS="facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref,facturas_venta.fv_id_empresa";
  public static final String FVCLID="fv_cl_id";
  public static final String FVFECHA="fv_fecha";
  public static final String FVID="fv_id";
  public static final String FVIVA="fv_iva";
  public static final String FVNUMEROFACTURA="fv_numero_factura";
  public static final String FVOBSERVACIONES="fv_observaciones";
  public static final String FVPAGADA="fv_pagada";
  public static final String FVTOTAL="fv_total";
  public static final String CLNOMBRE="cl_nombre";
  public static final String CLAPELLIDOS="cl_apellidos";
  public static final String CLDIRECCION="cl_direccion";
  public static final String LOCNOMBRE="loc_nombre";
  public static final String PROVNOMBRE="prov_nombre";
  public static final String CLDESCUENTO="cl_descuento";
  public static final String CLNOMBRECOMERCIAL="cl_nombre_comercial";
  public static final String FPDESCRIPCION="fp_descripcion";
  public static final String CLPFID="cl_pf_id";
  public static final String FVCIERRE="fv_cierre";
  public static final String FVIMPORTEPENDIENTE="fv_importe_pendiente";
  public static final String CLCOBID="cl_cob_id";
  public static final String PRIMARY_KEY="fv_id";
  public static final String FVFECHAPAGO="fv_fecha_pago";
  public static final String FVTIPO="fv_tipo";
  public static final String FVINVALIDA="fv_invalida";
  public static final String FVFVREF="fv_fv_ref";
  public static final String FVIDEMPRESA="fv_id_empresa";
  
  //Rango de Fechas para las busquedas
  public static final String FVFECHADESDE="fv_fecha_desde";
  public static final String FVFECHAHASTA="fv_fecha_hasta";
  
  protected FacturasVentaEntidad_bueno(){}

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
	  if(fvTipo.equals(""))
	    	fvTipo = "0";
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE facturas_venta SET fv_cl_id=?,fv_fecha=?,fv_iva=?,fv_observaciones=?,fv_pagada=?,fv_total=?,fv_cierre=?,fv_importe_pendiente=?,fv_fecha_pago=?,fv_tipo=?,fv_invalida=?,fv_id_empresa=? WHERE fv_id=?");
    sentencia.setString(1, fvClId);
    sentencia.setTimestamp(2, fvFecha);
    //sentencia.setString(3, fvId);
    sentencia.setString(3, fvIva);
    //sentencia.setString(4, fvNumeroFactura);
    sentencia.setString(4, fvObservaciones);
    sentencia.setString(5, fvPagada);
    sentencia.setString(6, quitar_comas(fvTotal));
    sentencia.setString(7, fvCierre);
    sentencia.setString(8, quitar_comas(fvImportePendiente));
    sentencia.setTimestamp(9, fvFechaPago);
    sentencia.setString(10, fvTipo);
    sentencia.setString(11, fvInvalida);
    sentencia.setString(12, fvIdEmpresa);
    sentencia.setString(13, fvId);
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
    if(fvTipo.equals(""))
    	fvTipo = "0";
	PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO facturas_venta (fv_cl_id,fv_fecha,fv_iva,fv_numero_factura,fv_observaciones,fv_pagada,fv_total,fv_cierre,fv_fecha_pago,fv_tipo,fv_id_empresa) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
    sentencia.setString(1, fvClId);
    sentencia.setTimestamp(2, fvFecha);
    //sentencia.setString(3, fvId);
    sentencia.setString(3, fvIva);
    sentencia.setString(4, fvNumeroFactura);
    sentencia.setString(5, fvObservaciones);
    sentencia.setString(6, fvPagada);
    sentencia.setString(7, quitar_comas(fvTotal));
    sentencia.setString(8, fvCierre);
    sentencia.setTimestamp(9, fvFechaPago);
    sentencia.setString(10, fvTipo);
    sentencia.setString(11, fvIdEmpresa);
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
	  ArrayList listaDetalles = FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId(fvId);
		int resultado = 0;
	    for(int i=0; i<listaDetalles.size(); i++){
	    	FacturasVentaDetalle fvDetalle = (FacturasVentaDetalle)listaDetalles.get(i);
	    	//Primero el stock 
	    	if(!Utils.skipNull(fvDetalle.getFvdStId()).equals("")){
				Stock stock = Stock.getStockByStId(fvDetalle.getFvdStId());
				stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(fvDetalle.getFvdCantidad())));
				stock.actualiza(conexion);
	    	}
	    	resultado = fvDetalle.elimina(conexion);
	    }
	    int n = 0;
	    if(resultado>=0){
		    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_venta WHERE fv_id=?");
		    sentencia.setString(1, fvId);
		    n = sentencia.executeUpdate();
		    sentencia.close();
	    }
    return n;
  }

  public String toString()
  {
    return "facturas_venta["+
      "fvClId:"+fvClId+", "+
      "fvFecha:"+utils.Utils.date2String(fvFecha, "dd/MM/yyyy HH:mm:ss")+", "+
      "fvId:"+fvId+", "+
      "fvIva:"+fvIva+", "+
      "fvNumeroFactura:"+fvNumeroFactura+", "+
      "fvObservaciones:"+fvObservaciones+", "+
      "fvPagada:"+fvPagada+", "+
      "fvTotal:"+fvTotal+", "+
      "fvImportePendiente:"+fvImportePendiente+
    "]";
  }

  private String fvClId = "";
  public String getFvClId()
  {
    return fvClId;
  }
  public void setFvClId(String fvClId)
  {
    this.fvClId = fvClId;
  }

  private java.sql.Timestamp fvFecha = null;
  public java.util.Date getFvFecha()
  {
    if(fvFecha==null){ return null; }
    else{ return fvFecha; }
  }
  public void setFvFecha(java.sql.Timestamp fvFecha)
  {
    if(fvFecha==null){ this.fvFecha = null; }
    else{ this.fvFecha = new java.sql.Timestamp(fvFecha.getTime()); }
  }
  public String getFvFecha(String formato)
  {
    return utils.Utils.date2String(fvFecha, formato);
  }
  public void setFvFecha(String fvFecha, String formato)
  {
    if( fvFecha==null || fvFecha.equals("") )
    { this.fvFecha = null; }
    else
    {
      try
      {
        this.fvFecha = new java.sql.Timestamp(utils.Utils.string2Date(fvFecha, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  
  private java.sql.Timestamp fvFechaDesde = null;
  public java.util.Date getFvFechaDesde()
  {
    if(fvFechaDesde==null){ return null; }
    else{ return fvFechaDesde; }
  }
  public void setFvFechaDesde(java.sql.Timestamp fvFechaDesde)
  {
    if(fvFechaDesde==null){ this.fvFechaDesde = null; }
    else{ this.fvFechaDesde = new java.sql.Timestamp(fvFechaDesde.getTime()); }
  }
  public String getFvFechaDesde(String formato)
  {
    return utils.Utils.date2String(fvFechaDesde, formato);
  }
  public void setFvFechaDesde(String fvFechaDesde, String formato)
  {
    if( fvFechaDesde==null || fvFechaDesde.equals("") )
    { this.fvFechaDesde = null; }
    else
    {
      try
      {
        this.fvFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(fvFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private java.sql.Timestamp fvFechaHasta = null;
  public java.util.Date getFvFechaHasta()
  {
    if(fvFechaHasta==null){ return null; }
    else{ return fvFechaHasta; }
  }
  public void setFvFechaHasta(java.sql.Timestamp fvFechaHasta)
  {
    if(fvFechaHasta==null){ this.fvFechaHasta = null; }
    else{ this.fvFechaHasta = new java.sql.Timestamp(fvFechaHasta.getTime()); }
  }
  public String getFvFechaHasta(String formato)
  {
    return utils.Utils.date2String(fvFechaHasta, formato);
  }
  public void setFvFechaHasta(String fvFechaHasta, String formato)
  {
    if( fvFechaHasta==null || fvFechaHasta.equals("") )
    { this.fvFechaHasta = null; }
    else
    {
      try
      {
        this.fvFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(fvFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  private String fvId = "";
  public String getFvId()
  {
    return fvId;
  }
  public void setFvId(String fvId)
  {
    this.fvId = fvId;
    this.pk_identificador_ = fvId;
  }

  private String fvIva = "";
  public String getFvIva()
  {
    return fvIva;
  }
  public void setFvIva(String fvIva)
  {
    this.fvIva = fvIva;
  }

  private String fvCierre = "";
  public String getFvCierre()
  {
    return fvCierre;
  }
  public void setFvCierre(String fvCierre)
  {
    this.fvCierre = fvCierre;
  }
  
  private String fvNumeroFactura = "";
  public String getFvNumeroFactura()
  {
    return fvNumeroFactura;
  }
  public void setFvNumeroFactura(String fvNumeroFactura)
  {
    this.fvNumeroFactura = fvNumeroFactura;
  }

  private String fvObservaciones = "";
  public String getFvObservaciones()
  {
    return fvObservaciones;
  }
  public void setFvObservaciones(String fvObservaciones)
  {
    this.fvObservaciones = fvObservaciones;
  }

  private String fvPagada = "";
  public String getFvPagada()
  {
    return fvPagada;
  }
  public void setFvPagada(String fvPagada)
  {
    this.fvPagada = fvPagada;
  }

  private String fvTotal = "";
  public String getFvTotal()
  {
    return fvTotal;
  }
  public void setFvTotal(String fvTotal)
  {
    this.fvTotal = fvTotal;
  }
  
  private String fvImportePendiente = "";
  public String getFvImportePendiente()
  {
    return fvImportePendiente;
  }
  public void setFvImportePendiente(String fvImportePendiente)
  {
    this.fvImportePendiente = fvImportePendiente;
  }
  
  private String fvTipo = "";
  public String getFvTipo()
  {
    return fvTipo;
  }
  public void setFvTipo(String fvTipo)
  {
    this.fvTipo = fvTipo;
  }
  
  
  	private String clPfId = "";
  	public String getClPfId() {
		return clPfId;
	}

	public void setClPfId(String clPfId) {
		this.clPfId = clPfId;
	}
  
	private String clCobId = "";
  	public String getClCobId() {
		return clCobId;
	}

	public void setClCobId(String clCobId) {
		this.clCobId = clCobId;
	}
	
	
  private String[] clDatosRelacionados;
  public String[] getClDatosRelacionados()
  {
	  return clDatosRelacionados;
  }
  public void setClDatosRelacionados(String[] clDatosRelacionados)
  {
    this.clDatosRelacionados = clDatosRelacionados;
  }
  

  private java.sql.Timestamp fvFechaPago = null;
  public java.util.Date getFvFechaPago()
  {
    if(fvFechaPago==null){ return null; }
    else{ return fvFechaPago; }
  }
  public void setFvFechaPago(java.sql.Timestamp fvFechaPago)
  {
    if(fvFechaPago==null){ this.fvFechaPago = null; }
    else{ this.fvFechaPago = new java.sql.Timestamp(fvFechaPago.getTime()); }
  }
  public String getFvFechaPago(String formato)
  {
    return utils.Utils.date2String(fvFechaPago, formato);
  }
  public void setFvFechaPago(String fvFechaPago, String formato)
  {
    if( fvFechaPago==null || fvFechaPago.equals("") )
    { this.fvFechaPago = null; }
    else
    {
      try
      {
        this.fvFechaPago = new java.sql.Timestamp(utils.Utils.string2Date(fvFechaPago, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
	private String fvFvRef = "";
  	public String getFvFvRef() {
		return fvFvRef;
	}

	public void setFvFvRef(String fvFvRef) {
		this.fvFvRef = fvFvRef;
	}
	
	private String fvInvalida = "";
  	public String getFvInvalida() {
		return fvInvalida;
	}

	public void setFvInvalida(String fvInvalida) {
		this.fvInvalida = fvInvalida;
	}

	private String fvIdEmpresa = "";
  	public String getFvIdEmpresa() {
		return fvIdEmpresa;
	}

	public void setFvIdEmpresa(String fvIdEmpresa) {
		this.fvIdEmpresa = fvIdEmpresa;
	}
}