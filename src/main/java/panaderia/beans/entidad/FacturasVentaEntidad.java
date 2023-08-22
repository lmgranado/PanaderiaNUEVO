// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FacturasVentaEntidad.java

package panaderia.beans.entidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import panaderia.struts.forms.EntidadBean;
import utils.Log4j;
import utils.PoolConexiones;
import utils.Utils;
import utils.UtilsFacturacion;

public class FacturasVentaEntidad extends panaderia.struts.forms.EntidadBean
{

    protected FacturasVentaEntidad()
    {
        fvClId = "";
        fvFecha = null;
        fvFechaDesde = null;
        fvFechaHasta = null;
        fvId = "";
        fvIva = "";
        fvCierre = "";
        fvNumeroFactura = "";
        fvObservaciones = "";
        fvPagada = "";
        fvTotal = "";
        fvImportePendiente = "";
        fvTipo = "";
        clPfId = "";
        clCobId = "";
        fvFechaPago = null;
        fvFvRef = "";
        fvInvalida = "";
        fvIdEmpresa = "";
        fvDescuento="";
    }

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

    public int actualiza(java.sql.Connection conexion)
        throws java.sql.SQLException
    {
        if(fvTipo.equals(""))
            fvTipo = "0";
        java.sql.PreparedStatement sentencia = conexion.prepareStatement("UPDATE facturas_venta SET fv_cl_id=?,fv_fecha=?,fv_iva=?,fv_observaciones=?,fv_pagada=?,fv_total=?,fv_cierre=?,fv_importe_pendiente=?,fv_fecha_pago=?,fv_tipo=?,fv_invalida=?,fv_id_empresa=?,fv_descuento=? WHERE fv_id=?");
        sentencia.setString(1, fvClId);
        sentencia.setTimestamp(2, fvFecha);
        sentencia.setString(3, fvIva);
        sentencia.setString(4, fvObservaciones);
        sentencia.setString(5, fvPagada);
        sentencia.setString(6, panaderia.beans.entidad.FacturasVentaEntidad.quitar_comas(fvTotal));
        sentencia.setString(7, fvCierre);
        sentencia.setString(8, panaderia.beans.entidad.FacturasVentaEntidad.quitar_comas(fvImportePendiente));
        sentencia.setTimestamp(9, fvFechaPago);
        sentencia.setString(10, fvTipo);
        sentencia.setString(11, fvInvalida);
        sentencia.setString(12, fvIdEmpresa);
        sentencia.setString(13, fvDescuento);
        sentencia.setString(14, fvId);
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

    public int inserta(java.sql.Connection conexion)
        throws java.sql.SQLException
    {
        if(fvTipo.equals(""))
            fvTipo = "0";
        java.sql.PreparedStatement sentencia = conexion.prepareStatement("INSERT INTO facturas_venta (fv_cl_id,fv_fecha,fv_iva,fv_numero_factura,fv_observaciones,fv_pagada,fv_total,fv_cierre,fv_fecha_pago,fv_tipo,fv_id_empresa,fv_descuento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        sentencia.setString(1, fvClId);
        sentencia.setTimestamp(2, fvFecha);
        sentencia.setString(3, fvIva);
        sentencia.setString(4, fvNumeroFactura);
        sentencia.setString(5, fvObservaciones);
        sentencia.setString(6, fvPagada);
        sentencia.setString(7, panaderia.beans.entidad.FacturasVentaEntidad.quitar_comas(fvTotal));
        sentencia.setString(8, fvCierre);
        sentencia.setTimestamp(9, fvFechaPago);
        sentencia.setString(10, fvTipo);
        sentencia.setString(11, fvIdEmpresa);
        sentencia.setString(12, Utils.skipNullNumero(fvDescuento) );
        sentencia.executeUpdate();
        int autoIncKeyFromApi = -1;
        java.sql.ResultSet rs = sentencia.getGeneratedKeys();
        if(rs.next())
            autoIncKeyFromApi = rs.getInt(1);
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

    public int elimina(java.sql.Connection conexion)
        throws java.sql.SQLException
    {
        java.sql.PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM facturas_venta WHERE fv_id=?");
        sentencia.setString(1, fvId);
        int n = sentencia.executeUpdate();
        sentencia.close();
        return n;
    }

    public java.lang.String toString()
    {
        return (new StringBuilder("facturas_venta[fvClId:")).append(fvClId).append(", ").append("fvFecha:").append(utils.Utils.date2String(fvFecha, "dd/MM/yyyy HH:mm:ss")).append(", ").append("fvId:").append(fvId).append(", ").append("fvIva:").append(fvIva).append(", ").append("fvNumeroFactura:").append(fvNumeroFactura).append(", ").append("fvObservaciones:").append(fvObservaciones).append(", ").append("fvPagada:").append(fvPagada).append(", ").append("fvTotal:").append(fvTotal).append(", ").append("fvImportePendiente:").append(fvImportePendiente).append("]").toString();
    }

    public java.lang.String getFvClId()
    {
        return fvClId;
    }

    public void setFvClId(java.lang.String fvClId)
    {
        this.fvClId = fvClId;
    }

    public java.util.Date getFvFecha()
    {
        if(fvFecha == null)
            return null;
        else
            return fvFecha;
    }

    public void setFvFecha(java.sql.Timestamp fvFecha)
    {
        if(fvFecha == null)
            this.fvFecha = null;
        else
            this.fvFecha = new Timestamp(fvFecha.getTime());
    }

    public java.lang.String getFvFecha(java.lang.String formato)
    {
        return utils.Utils.date2String(fvFecha, formato);
    }

    public void setFvFecha(java.lang.String fvFecha, java.lang.String formato)
    {
        if(fvFecha == null || fvFecha.equals(""))
            this.fvFecha = null;
        else
            try
            {
                this.fvFecha = new Timestamp(utils.Utils.string2Date(fvFecha, formato).getTime());
            }
            catch(java.text.ParseException e)
            {
                utils.Log4j.error("Error", e);
            }
    }

    public java.util.Date getFvFechaDesde()
    {
        if(fvFechaDesde == null)
            return null;
        else
            return fvFechaDesde;
    }

    public void setFvFechaDesde(java.sql.Timestamp fvFechaDesde)
    {
        if(fvFechaDesde == null)
            this.fvFechaDesde = null;
        else
            this.fvFechaDesde = new Timestamp(fvFechaDesde.getTime());
    }

    public java.lang.String getFvFechaDesde(java.lang.String formato)
    {
        return utils.Utils.date2String(fvFechaDesde, formato);
    }

    public void setFvFechaDesde(java.lang.String fvFechaDesde, java.lang.String formato)
    {
        if(fvFechaDesde == null || fvFechaDesde.equals(""))
            this.fvFechaDesde = null;
        else
            try
            {
                this.fvFechaDesde = new Timestamp(utils.Utils.string2Date(fvFechaDesde, formato).getTime());
            }
            catch(java.text.ParseException e)
            {
                utils.Log4j.error("Error", e);
            }
    }

    public java.util.Date getFvFechaHasta()
    {
        if(fvFechaHasta == null)
            return null;
        else
            return fvFechaHasta;
    }

    public void setFvFechaHasta(java.sql.Timestamp fvFechaHasta)
    {
        if(fvFechaHasta == null)
            this.fvFechaHasta = null;
        else
            this.fvFechaHasta = new Timestamp(fvFechaHasta.getTime());
    }

    public java.lang.String getFvFechaHasta(java.lang.String formato)
    {
        return utils.Utils.date2String(fvFechaHasta, formato);
    }

    public void setFvFechaHasta(java.lang.String fvFechaHasta, java.lang.String formato)
    {
        if(fvFechaHasta == null || fvFechaHasta.equals(""))
            this.fvFechaHasta = null;
        else
            try
            {
                this.fvFechaHasta = new Timestamp(utils.Utils.string2Date(fvFechaHasta, formato).getTime());
            }
            catch(java.text.ParseException e)
            {
                utils.Log4j.error("Error", e);
            }
    }

    public java.lang.String getFvId()
    {
        return fvId;
    }

    public void setFvId(java.lang.String fvId)
    {
        this.fvId = fvId;
        pk_identificador_ = fvId;
    }

    public java.lang.String getFvIva()
    {
        return fvIva;
    }

    public void setFvIva(java.lang.String fvIva)
    {
        this.fvIva = fvIva;
    }

    public java.lang.String getFvCierre()
    {
        return fvCierre;
    }

    public void setFvCierre(java.lang.String fvCierre)
    {
        this.fvCierre = fvCierre;
    }

    public java.lang.String getFvNumeroFactura()
    {
        return fvNumeroFactura;
    }

    public void setFvNumeroFactura(java.lang.String fvNumeroFactura)
    {
        this.fvNumeroFactura = fvNumeroFactura;
    }

    public java.lang.String getFvObservaciones()
    {
        return fvObservaciones;
    }

    public void setFvObservaciones(java.lang.String fvObservaciones)
    {
        this.fvObservaciones = fvObservaciones;
    }

    public java.lang.String getFvPagada()
    {
        return fvPagada;
    }

    public void setFvPagada(java.lang.String fvPagada)
    {
        this.fvPagada = fvPagada;
    }

    public java.lang.String getFvTotal()
    {
        return fvTotal;
    }

    public void setFvTotal(java.lang.String fvTotal)
    {
        this.fvTotal = fvTotal;
    }

    public java.lang.String getFvImportePendiente()
    {
        return fvImportePendiente;
    }

    public void setFvImportePendiente(java.lang.String fvImportePendiente)
    {
        this.fvImportePendiente = fvImportePendiente;
    }

    public java.lang.String getFvTipo()
    {
        return fvTipo;
    }

    public void setFvTipo(java.lang.String fvTipo)
    {
        this.fvTipo = fvTipo;
    }

    public java.lang.String getClPfId()
    {
        return clPfId;
    }

    public void setClPfId(java.lang.String clPfId)
    {
        this.clPfId = clPfId;
    }

    public java.lang.String getClCobId()
    {
        return clCobId;
    }

    public void setClCobId(java.lang.String clCobId)
    {
        this.clCobId = clCobId;
    }

    public java.lang.String[] getClDatosRelacionados()
    {
        return clDatosRelacionados;
    }

    public void setClDatosRelacionados(java.lang.String clDatosRelacionados[])
    {
        this.clDatosRelacionados = clDatosRelacionados;
    }

    public java.util.Date getFvFechaPago()
    {
        if(fvFechaPago == null)
            return null;
        else
            return fvFechaPago;
    }

    public void setFvFechaPago(java.sql.Timestamp fvFechaPago)
    {
        if(fvFechaPago == null)
            this.fvFechaPago = null;
        else
            this.fvFechaPago = new Timestamp(fvFechaPago.getTime());
    }

    public java.lang.String getFvFechaPago(java.lang.String formato)
    {
        return utils.Utils.date2String(fvFechaPago, formato);
    }

    public void setFvFechaPago(java.lang.String fvFechaPago, java.lang.String formato)
    {
        if(fvFechaPago == null || fvFechaPago.equals(""))
            this.fvFechaPago = null;
        else
            try
            {
                this.fvFechaPago = new Timestamp(utils.Utils.string2Date(fvFechaPago, formato).getTime());
            }
            catch(java.text.ParseException e)
            {
                utils.Log4j.error("Error", e);
            }
    }

    public java.lang.String getFvFvRef()
    {
        return fvFvRef;
    }

    public void setFvFvRef(java.lang.String fvFvRef)
    {
        this.fvFvRef = fvFvRef;
    }

    public java.lang.String getFvInvalida()
    {
        return fvInvalida;
    }

    public void setFvInvalida(java.lang.String fvInvalida)
    {
        this.fvInvalida = fvInvalida;
    }
    
	private String fvIdEmpresa = "";
  	public String getFvIdEmpresa() {
		return fvIdEmpresa;
	}

	public void setFvIdEmpresa(String fvIdEmpresa) {
		this.fvIdEmpresa = fvIdEmpresa;
	}
	
	private String fvDescuento = "";
  	public String getFvDescuento() {
		return fvDescuento;
	}

	public void setFvDescuento(String fvDescuento) {
		this.fvDescuento = fvDescuento;
	}

    private static final long serialVersionUID = 1L;
    public static final java.lang.String TABLE = "facturas_venta";
    public static final java.lang.String ALL_COLUMNS = "facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref,facturas_venta.fv_id_empresa,facturas_venta.fv_descuento";
    public static final java.lang.String FVCLID = "fv_cl_id";
    public static final java.lang.String FVFECHA = "fv_fecha";
    public static final java.lang.String FVID = "fv_id";
    public static final java.lang.String FVIVA = "fv_iva";
    public static final java.lang.String FVNUMEROFACTURA = "fv_numero_factura";
    public static final java.lang.String FVOBSERVACIONES = "fv_observaciones";
    public static final java.lang.String FVPAGADA = "fv_pagada";
    public static final java.lang.String FVTOTAL = "fv_total";
    public static final java.lang.String CLNOMBRE = "cl_nombre";
    public static final java.lang.String CLAPELLIDOS = "cl_apellidos";
    public static final java.lang.String CLDIRECCION = "cl_direccion";
    public static final java.lang.String LOCNOMBRE = "loc_nombre";
    public static final java.lang.String PROVNOMBRE = "prov_nombre";
    public static final java.lang.String CLDESCUENTO = "cl_descuento";
    public static final java.lang.String CLNOMBRECOMERCIAL = "cl_nombre_comercial";
    public static final java.lang.String FPDESCRIPCION = "fp_descripcion";
    public static final java.lang.String CLPFID = "cl_pf_id";
    public static final java.lang.String FVCIERRE = "fv_cierre";
    public static final java.lang.String FVIMPORTEPENDIENTE = "fv_importe_pendiente";
    public static final java.lang.String CLCOBID = "cl_cob_id";
    public static final java.lang.String PRIMARY_KEY = "fv_id";
    public static final java.lang.String FVFECHAPAGO = "fv_fecha_pago";
    public static final java.lang.String FVTIPO = "fv_tipo";
    public static final java.lang.String FVINVALIDA = "fv_invalida";
    public static final java.lang.String FVFVREF = "fv_fv_ref";
    public static final java.lang.String FVFECHADESDE = "fv_fecha_desde";
    public static final java.lang.String FVFECHAHASTA = "fv_fecha_hasta";
    public static final String FVIDEMPRESA="fv_id_empresa";
    public static final String FVDESCUENTO="fv_descuento";
    
    private java.lang.String fvClId;
    private java.sql.Timestamp fvFecha;
    private java.sql.Timestamp fvFechaDesde;
    private java.sql.Timestamp fvFechaHasta;
    private java.lang.String fvId;
    private java.lang.String fvIva;
    private java.lang.String fvCierre;
    private java.lang.String fvNumeroFactura;
    private java.lang.String fvObservaciones;
    private java.lang.String fvPagada;
    private java.lang.String fvTotal;
    private java.lang.String fvImportePendiente;
    private java.lang.String fvTipo;
    private java.lang.String clPfId;
    private java.lang.String clCobId;
    private java.lang.String clDatosRelacionados[];
    private java.sql.Timestamp fvFechaPago;
    private java.lang.String fvFvRef;
    private java.lang.String fvInvalida;
}
