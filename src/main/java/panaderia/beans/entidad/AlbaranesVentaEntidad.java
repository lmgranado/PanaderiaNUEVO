package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Stock;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;

public class AlbaranesVentaEntidad extends EntidadBean
{
  private static final long serialVersionUID = 1L;
  
  public static final String TABLE="albaranes_venta";
  public static final String ALL_COLUMNS="albaranes_venta.av_cl_id,albaranes_venta.av_fecha,albaranes_venta.av_id,albaranes_venta.av_iva,albaranes_venta.av_observaciones,albaranes_venta.av_pagado,albaranes_venta.av_total,albaranes_venta.av_cierre,albaranes_venta.av_numero,albaranes_venta.av_tipo";
  public static final String AVCLID="av_cl_id";
  public static final String AVFECHA="av_fecha";
  public static final String AVID="av_id";
  public static final String AVIVA="av_iva";
  public static final String AVOBSERVACIONES="av_observaciones";
  public static final String AVPAGADO="av_pagado";
  public static final String AVTOTAL="av_total";
  public static final String AVCIERRE="av_cierre";
  public static final String AVNUMERO="av_numero";
  public static final String AVTIPO="av_tipo";
  //Datos Relacionados
  public static final String CLNOMBRE="cl_nombre";
  public static final String CLAPELLIDOS="cl_apellidos";
  public static final String CLDIRECCION="cl_direccion";
  public static final String LOCNOMBRE="loc_nombre";
  public static final String PROVNOMBRE="prov_nombre";
  public static final String CLDESCUENTO="cl_descuento";
  public static final String CLNOMBRECOMERCIAL="cl_nombre_comercial";
  public static final String FPDESCRIPCION="fp_descripcion";
  public static final String CLPFID="cl_pf_id";
  
  public static final String PRIMARY_KEY="av_id";

  //Rango de Fechas para las busquedas
  public static final String AVFECHADESDE="av_fecha_desde";
  public static final String AVFECHAHASTA="av_fecha_hasta";
  
  
  protected AlbaranesVentaEntidad(){}

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
    PreparedStatement sentencia = conexion.prepareStatement("UPDATE albaranes_venta SET av_cl_id=?,av_fecha=?,av_iva=?,av_observaciones=?,av_pagado=?,av_total=?,av_cierre=?,av_numero=? WHERE av_id=?");
    sentencia.setString(1, avClId);
    sentencia.setTimestamp(2, avFecha);
    //sentencia.setString(3, avId);
    sentencia.setString(3, avIva);
    sentencia.setString(4, avObservaciones);
    sentencia.setString(5, avPagado);
    sentencia.setString(6, quitar_comas(avTotal));
    sentencia.setString(7, avCierre);
    sentencia.setString(8, avNumero);
    sentencia.setString(9, avId);
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
	    PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO albaranes_venta (av_cl_id,av_fecha,av_iva,av_observaciones,av_pagado,av_total,av_cierre,av_numero,av_tipo) VALUES (?,?,?,?,?,?,?,?,?)");
	    sentencia.setString(1, avClId);
	    sentencia.setTimestamp(2, avFecha);
	    //sentencia.setString(3, avId);
	    sentencia.setString(3, avIva);
	    sentencia.setString(4, avObservaciones);
	    sentencia.setString(5, avPagado);
	    sentencia.setString(6, quitar_comas(avTotal));
	    sentencia.setString(7, avCierre);
	    sentencia.setString(8, avNumero);
	    sentencia.setString(9, avTipo);
	    
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
	  ArrayList listaDetalles = AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdAvId(avId);
		int resultado = 0;
	    for(int i=0; i<listaDetalles.size(); i++){
	    	AlbaranesVentaDetalle avDetalle = (AlbaranesVentaDetalle)listaDetalles.get(i);
	    	//Primero el stock 
	    	if(!Utils.skipNull(avDetalle.getAvdStId()).equals("")){
				Stock stock = Stock.getStockByStId(avDetalle.getAvdStId());
				stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(avDetalle.getAvdCantidad())));
				stock.actualiza(conexion);
	    	}
	    	resultado = avDetalle.elimina(conexion);
	    }
	    int n = 0;
	    if(resultado>=0){
		    PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM albaranes_venta WHERE av_id=?");
		    sentencia.setString(1, avId);
		    n = sentencia.executeUpdate();
		    sentencia.close();
	    }
    return n;
  }

  public String toString()
  {
    return "albaranes_venta["+
      "avClId:"+avClId+", "+
      "avFecha:"+utils.Utils.date2String(avFecha, "dd/MM/yyyy HH:mm:ss")+", "+
      "avId:"+avId+", "+
      "avIva:"+avIva+", "+
      "avObservaciones:"+avObservaciones+", "+
      "avPagado:"+avPagado+", "+
      "avNumero:"+avNumero+", "+
      "avTotal:"+avTotal+
    "]";
  }

  private String avClId = "";
  public String getAvClId()
  {
    return avClId;
  }
  public void setAvClId(String avClId)
  {
    this.avClId = avClId;
  }

  private java.sql.Timestamp avFecha = null;
  public java.util.Date getAvFecha()
  {
    if(avFecha==null){ return null; }
    else{ return avFecha; }
  }
  public void setAvFecha(java.sql.Timestamp avFecha)
  {
    if(avFecha==null){ this.avFecha = null; }
    else{ this.avFecha = new java.sql.Timestamp(avFecha.getTime()); }
  }
  public String getAvFecha(String formato)
  {
    return utils.Utils.date2String(avFecha, formato);
  }
  public void setAvFecha(String avFecha, String formato)
  {
    if( avFecha==null || avFecha.equals("") )
    { this.avFecha = null; }
    else
    {
      try
      {
        this.avFecha = new java.sql.Timestamp(utils.Utils.string2Date(avFecha, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }

  private String avId = "";
  public String getAvId()
  {
    return avId;
  }
  public void setAvId(String avId)
  {
    this.avId = avId;
    this.pk_identificador_ = avId;
  }

  private String avIva = "";
  public String getAvIva()
  {
    return avIva;
  }
  public void setAvIva(String avIva)
  {
    this.avIva = avIva;
  }

  private String avObservaciones = "";
  public String getAvObservaciones()
  {
    return avObservaciones;
  }
  public void setAvObservaciones(String avObservaciones)
  {
    this.avObservaciones = avObservaciones;
  }

  private String avPagado = "";
  public String getAvPagado()
  {
    return avPagado;
  }
  public void setAvPagado(String avPagado)
  {
    this.avPagado = avPagado;
  }


  private String avCierre = "";
  public String getAvCierre()
  {
    return avCierre;
  }
  public void setAvCierre(String avCierre)
  {
    this.avCierre = avCierre;
  }
  
  private String avTotal = "";
  public String getAvTotal()
  {
    return avTotal;
  }
  public void setAvTotal(String avTotal)
  {
    this.avTotal = avTotal;
  }
  
  
  private java.sql.Timestamp avFechaDesde = null;
  public java.util.Date getAvFechaDesde()
  {
    if(avFechaDesde==null){ return null; }
    else{ return avFechaDesde; }
  }
  
  public void setAvFechaDesde(java.sql.Timestamp avFechaDesde)
  {
    if(avFechaDesde==null){ this.avFechaDesde = null; }
    else{ this.avFechaDesde = new java.sql.Timestamp(avFechaDesde.getTime()); }
  }
  
  public String getAvFechaDesde(String formato)
  {
    return utils.Utils.date2String(avFechaDesde, formato);
  }
  
  public void setAvFechaDesde(String avFechaDesde, String formato)
  {
    if( avFechaDesde==null || avFechaDesde.equals("") )
    { this.avFechaDesde = null; }
    else
    {
      try
      {
        this.avFechaDesde = new java.sql.Timestamp(utils.Utils.string2Date(avFechaDesde, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private java.sql.Timestamp avFechaHasta = null;
  public java.util.Date getAvFechaHasta()
  {
    if(avFechaHasta==null){ return null; }
    else{ return avFechaHasta; }
  }
  
  public void setAvFechaHasta(java.sql.Timestamp avFechaHasta)
  {
    if(avFechaHasta==null){ this.avFechaHasta = null; }
    else{ this.avFechaHasta = new java.sql.Timestamp(avFechaHasta.getTime()); }
  }
  
  public String getAvFechaHasta(String formato)
  {
    return utils.Utils.date2String(avFechaHasta, formato);
  }
  
  public void setAvFechaHasta(String avFechaHasta, String formato)
  {
    if( avFechaHasta==null || avFechaHasta.equals("") )
    { this.avFechaHasta = null; }
    else
    {
      try
      {
        this.avFechaHasta = new java.sql.Timestamp(utils.Utils.string2Date(avFechaHasta, formato).getTime());
      }
      catch(java.text.ParseException e){ utils.Log4j.error("Error", e); }
    }
  }
  
  private String clPfId = "";
	public String getClPfId() {
		return clPfId;
	}

	public void setClPfId(String clPfId) {
		this.clPfId = clPfId;
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
  
  
  private String avNumero = "";
	public String getAvNumero() {
		return avNumero;
	}

	public void setAvNumero(String avNumero) {
		this.avNumero = avNumero;
	}
	
	private String avTipo = "";
	public String getAvTipo() {
		return avTipo;
	}

	public void setAvTipo(String avTipo) {
		this.avTipo = avTipo;
	}
}
