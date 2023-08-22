package panaderia.beans;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.displaytag.pagination.PaginatedListTest;
import panaderia.beans.entidad.FacturasVentaEntidad;
import panaderia.struts.forms.EntidadBean;
import utils.Log4j;
import utils.PoolConexiones;
import utils.UtilesDAO;
import utils.Utils;

public class FacturasVenta_old extends panaderia.beans.entidad.FacturasVentaEntidad
{

    public FacturasVenta_old()
    {
    }

    public static java.util.ArrayList getFacturasVentaNoPagadasByFvClId(java.lang.String fvClId)
    {
        panaderia.beans.FacturasVenta elto = new FacturasVenta();
        java.util.ArrayList lista = elto.consultaAVectorReflexiva((new StringBuilder("SELECT facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref FROM facturas_venta WHERE fv_cl_id='")).append(fvClId).append("' and fv_pagada=0 and (fv_invalida is null or fv_invalida = 0) order by fv_fecha desc").toString());
        return lista;
    }

    public static java.util.ArrayList getParteDiarioCobros(java.lang.String cobId, java.lang.String fecha_desde, java.lang.String fecha_hasta, java.lang.String tipo, java.lang.String estado, java.lang.String fvClId, java.lang.String fecha_pago)
    {
        java.lang.String where = "";
        if(!utils.Utils.skipNull(fecha_desde).equals("") && !utils.Utils.skipNull(fecha_pago).equals(""))
            where = (new StringBuilder(" where fv_fecha BETWEEN str_to_date('")).append(fecha_desde).append("','%d/%m/%Y') AND str_to_date('").append(fecha_hasta).append("','%d/%m/%Y') ").append(" and fv_fecha_pago BETWEEN str_to_date('").append(fecha_pago).append("','%d/%m/%Y') AND str_to_date('").append(fecha_pago).append("','%d/%m/%Y') ").toString();
        else
        if(!utils.Utils.skipNull(fecha_desde).equals(""))
            where = (new StringBuilder(" where fv_fecha BETWEEN str_to_date('")).append(fecha_desde).append("','%d/%m/%Y') AND str_to_date('").append(fecha_hasta).append("','%d/%m/%Y') ").toString();
        else
        if(!utils.Utils.skipNull(fecha_pago).equals(""))
            where = (new StringBuilder(" where fv_fecha_pago BETWEEN str_to_date('")).append(fecha_pago).append("','%d/%m/%Y') AND str_to_date('").append(fecha_pago).append("','%d/%m/%Y') ").toString();
        if(!utils.Utils.empty(cobId))
            where = (new StringBuilder(java.lang.String.valueOf(where))).append(" and cob.cob_id=").append(cobId).toString();
        if(!utils.Utils.empty(tipo))
            where = (new StringBuilder(java.lang.String.valueOf(where))).append(" and fv.fv_iva='").append(tipo).append("'").toString();
        if(!utils.Utils.empty(estado))
            where = (new StringBuilder(java.lang.String.valueOf(where))).append(" and fv.fv_pagada=").append(estado).toString();
        if(!utils.Utils.empty(fvClId))
            where = (new StringBuilder(java.lang.String.valueOf(where))).append(" and fv.fv_cl_id=").append(fvClId).toString();
        java.util.ArrayList lista = panaderia.beans.FacturasVenta.consulta((new StringBuilder("select fv.fv_numero_factura, fv.fv_fecha as fechaFactura, fv.fv_fecha_pago as fechaPago,  fv.fv_iva as tipo,  fv.fv_total as total, fv.fv_importe_pendiente as importe_pendiente, cl.cl_id , cl.cl_nombre_comercial as cliente, cob.cob_id,  concat(cob.cob_nombre,' ',cob.cob_apellidos) as cobrador  from facturas_venta fv\t left join clientes cl on fv.fv_cl_id = cl.cl_id  left join cobradores cob on cl.cl_cob_id = cob.cob_id ")).append(where).append(" and (fv.fv_invalida is null or fv.fv_invalida = 0) ").append(" order by cob.cob_nombre asc, cl.cl_nombre_comercial asc, fv_fecha asc").toString());
        return lista;
    }

    public static panaderia.beans.FacturasVenta getFacturasVentaByFvId(java.lang.String fvId)
    {
        panaderia.beans.FacturasVenta elto = new FacturasVenta();
        elto.consultaReflexiva((new StringBuilder("SELECT facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref,facturas_venta.fv_id_empresa FROM facturas_venta WHERE fv_id='")).append(fvId).append("'").toString());
        java.lang.String datos[] = panaderia.beans.FacturasVenta.consultarValores((new StringBuilder("SELECT\tc.cl_nombre,\tc.cl_apellidos,\tc.cl_direccion,\tloc.loc_id,\tprov.prov_id,\tc.cl_descuento,\tc.cl_nombre_comercial FROM facturas_venta fv JOIN clientes c ON c.cl_id=fv.fv_cl_id JOIN provincias prov ON prov.prov_id = c.cl_prov_id JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo WHERE fv_id='")).append(fvId).append("'").toString());
        java.lang.String clDatosRelacionados[] = new java.lang.String[7];
        clDatosRelacionados[0] = datos[0];
        clDatosRelacionados[1] = datos[1];
        clDatosRelacionados[2] = datos[2];
        clDatosRelacionados[3] = datos[3];
        clDatosRelacionados[4] = datos[4];
        clDatosRelacionados[5] = datos[5];
        clDatosRelacionados[6] = datos[6];
        elto.setClDatosRelacionados(clDatosRelacionados);
        return elto;
    }

    public static int getSiguienteNumeroFactura(String tipo, String dondeFabrica)
    {
  	if(Utils.skipNull(dondeFabrica).equals(""))
  		dondeFabrica = "0";
  	
      String numero = EntidadBean.consultarValor("SELECT MAX(fv_numero_factura) " +
  									    	    "FROM facturas_venta " +
  									    	    "WHERE fv_iva = '" + tipo + "' " +
  									    	    "and fv_tipo != '2' " +
  									    	    "and fv_id_empresa = '" + dondeFabrica + "' ");
      return Integer.parseInt(Utils.skipNullNumero(numero));
    }
    
    public static int getSiguienteNumeroFacturaAbono(String tipo, String dondeFabrica)
    {
  	if(Utils.skipNull(dondeFabrica).equals(""))
  		dondeFabrica = "0";
      String numero = EntidadBean.consultarValor("SELECT MAX(fv_numero_factura) " +
  									    	    "FROM facturas_venta " +
  									    	    "WHERE fv_iva = '" + tipo + "' " +
  									    	    "and fv_tipo = '2' " +
  									    	    "and fv_id_empresa = '" + dondeFabrica + "' ");
      return Integer.parseInt(Utils.skipNullNumero(numero));
    }
    
    public static String getImporteTotalBaseByFvId(String fvId)
    {
      String importe = EntidadBean.consultarValor("SELECT ROUND(sum(fvd_cantidad*fvd_precio_venta),3) " +
  								    			"FROM facturas_venta_detalle " +
  								    			"WHERE fvd_fv_id = " + fvId);
      return Utils.skipNullNumero(importe);
    }

    public static ArrayList getImporteIvaTotalByFvId(String fvId)
    {
      ArrayList listaIvas = EntidadBean.consulta("SELECT round(fvd_iva*100,0), ROUND(sum(fvd_importe - (fvd_cantidad*fvd_precio_venta)),3) " +
      											"FROM facturas_venta_detalle " +
      											"WHERE fvd_fv_id = " + fvId +
      											" group by fvd_iva");
      return listaIvas;
    }

    public static panaderia.beans.FacturasVenta getFacturasVentaByFvNumeroFactura(java.lang.String fvNumeroFactura)
    {
        panaderia.beans.FacturasVenta elto = new FacturasVenta();
        elto.consultaReflexiva((new StringBuilder("SELECT facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref FROM facturas_venta WHERE fv_numero_factura='")).append(fvNumeroFactura).append("'").toString());
        return elto;
    }

    public static java.lang.String getImporteTotalByFvId(java.lang.String fvId)
    {
        java.lang.String importe = panaderia.struts.forms.EntidadBean.consultarValor((new StringBuilder("SELECT ROUND(sum(fvd_importe),3) FROM facturas_venta_detalle WHERE fvd_fv_id = ")).append(fvId).toString());
        return utils.Utils.skipNullNumero(importe);
    }

    public static java.lang.String getImportesPendientesByFvClId(java.lang.String fvClId)
    {
        java.lang.String importe = panaderia.struts.forms.EntidadBean.consultarValor((new StringBuilder("SELECT ROUND(sum(fv_importe_pendiente),3) FROM facturas_venta WHERE fv_cl_id = ")).append(fvClId).append(" and fv_pagada=0 ").append("and (fv_invalida is null or fv_invalida = 0)").toString());
        return utils.Utils.skipNullNumero(importe);
    }

    public static java.util.ArrayList getAllFacturasVenta()
    {
        panaderia.beans.FacturasVenta elto = new FacturasVenta();
        java.util.ArrayList lista = elto.consultaAVectorReflexiva("SELECT facturas_venta.fv_cl_id,facturas_venta.fv_fecha,facturas_venta.fv_id,facturas_venta.fv_iva,facturas_venta.fv_numero_factura,facturas_venta.fv_observaciones,facturas_venta.fv_pagada,facturas_venta.fv_total,facturas_venta.fv_cierre,facturas_venta.fv_importe_pendiente,facturas_venta.fv_fecha_pago,facturas_venta.fv_tipo,facturas_venta.fv_invalida,facturas_venta.fv_fv_ref, facturas_venta.fv_id_empresa  FROM facturas_venta order by fv_fecha desc");
        return lista;
    }

    protected java.lang.Object rsToBean(java.sql.ResultSet rs)
        throws java.lang.Exception
    {
        panaderia.beans.FacturasVenta facturasventa = new FacturasVenta();
        try
        {
            facturasventa.setFvClId(rs.getString("fv_cl_id"));
            facturasventa.setFvFecha(rs.getTimestamp("fv_fecha"));
            facturasventa.setFvId(rs.getString("fv_id"));
            facturasventa.setFvIva(rs.getString("fv_iva"));
            facturasventa.setFvNumeroFactura(rs.getString("fv_numero_factura"));
            facturasventa.setFvObservaciones(rs.getString("fv_observaciones"));
            facturasventa.setFvPagada(rs.getString("fv_pagada"));
            facturasventa.setFvTotal(rs.getString("fv_total"));
            facturasventa.setFvCierre(rs.getString("fv_cierre"));
            facturasventa.setFvImportePendiente(rs.getString("fv_importe_pendiente"));
            facturasventa.setFvFechaPago(rs.getTimestamp("fv_fecha_pago"));
            facturasventa.setFvTipo(rs.getString("fv_tipo"));
            facturasventa.setFvFvRef(rs.getString("fv_fv_ref"));
            facturasventa.setFvInvalida(rs.getString("fv_invalida"));
            facturasventa.setFvIdEmpresa(rs.getString("fv_id_empresa"));
            java.lang.String clDatosRelacionados[] = new java.lang.String[8];
            clDatosRelacionados[0] = rs.getString("cl_nombre");
            clDatosRelacionados[1] = rs.getString("cl_apellidos");
            clDatosRelacionados[2] = rs.getString("cl_direccion");
            clDatosRelacionados[3] = rs.getString("loc_nombre");
            clDatosRelacionados[4] = rs.getString("prov_nombre");
            clDatosRelacionados[5] = rs.getString("cl_descuento");
            clDatosRelacionados[6] = rs.getString("cl_nombre_comercial");
            try
            {
                clDatosRelacionados[7] = rs.getString("cl_pf_id");
            }
            catch(java.lang.Exception exception) { }
            facturasventa.setClDatosRelacionados(clDatosRelacionados);
        }
        catch(java.lang.Exception e)
        {
            throw e;
        }
        return facturasventa;
    }

    public static java.lang.String getFacturasVentaTotalPendienteFiltro(javax.servlet.http.HttpServletRequest filtro)
    {
        java.lang.String sqlAF1adido = " ";
        java.lang.String sql = "SELECT sum(fv.fv_importe_pendiente) FROM facturas_venta fv JOIN clientes c ON c.cl_id=fv.fv_cl_id JOIN provincias prov ON prov.prov_id = c.cl_prov_id JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo WHERE (fv.fv_invalida is null or fv.fv_invalida = 0) ";
        try
        {
            panaderia.beans.FacturasVenta facturasventaFiltro = new FacturasVenta();
            if(filtro.getSession().getAttribute("FFACTURAS_VENTA") != null)
                facturasventaFiltro = (panaderia.beans.FacturasVenta)filtro.getSession().getAttribute("FFACTURAS_VENTA");
            if(!utils.Utils.empty(facturasventaFiltro.getFvClId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_cl_id = '").append(facturasventaFiltro.getFvClId()).append("'").toString();
            if(facturasventaFiltro.getFvFecha() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha = '").append(facturasventaFiltro.getFvFecha()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_id like '%").append(facturasventaFiltro.getFvId()).append("%'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvIva()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_iva = '").append(facturasventaFiltro.getFvIva()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvNumeroFactura()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_numero_factura like '%").append(facturasventaFiltro.getFvNumeroFactura()).append("%'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvPagada()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_pagada = '").append(facturasventaFiltro.getFvPagada()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvTotal()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_total = '").append(facturasventaFiltro.getFvTotal()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvImportePendiente()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_importe_pendiente = '").append(facturasventaFiltro.getFvImportePendiente()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvObservaciones()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND UPPER(fv_observaciones) like UPPER('%").append(facturasventaFiltro.getFvObservaciones()).append("%')").toString();
            if(facturasventaFiltro.getClDatosRelacionados() != null && !utils.Utils.empty(facturasventaFiltro.getClDatosRelacionados()[0]))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND UPPER(cl_nombre) LIKE UPPER('%").append(facturasventaFiltro.getClDatosRelacionados()[0]).append("%')").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getClPfId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND cl_pf_id = '").append(facturasventaFiltro.getClPfId()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvCierre()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_cierre = '").append(facturasventaFiltro.getFvCierre()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getClCobId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND cl_cob_id = '").append(facturasventaFiltro.getClCobId()).append("'").toString();
            if(facturasventaFiltro.getFvFechaDesde() != null && facturasventaFiltro.getFvFechaHasta() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha BETWEEN '").append(facturasventaFiltro.getFvFechaDesde()).append("' AND '").append(facturasventaFiltro.getFvFechaHasta()).append("' ").toString();
            else
            if(facturasventaFiltro.getFvFechaDesde() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha >= '").append(facturasventaFiltro.getFvFechaDesde()).append("'").toString();
            else
            if(facturasventaFiltro.getFvFechaHasta() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha <= '").append(facturasventaFiltro.getFvFechaHasta()).append("'").toString();
            if(facturasventaFiltro.getFvFechaPago() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha_pago = '").append(facturasventaFiltro.getFvFechaPago()).append("'").toString();
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return panaderia.struts.forms.EntidadBean.consultarValor((new StringBuilder(java.lang.String.valueOf(sql))).append(sqlAF1adido).toString());
    }

    public static java.lang.Object[] getFacturasVentaLista(org.displaytag.pagination.PaginatedListTest listaPaginada)
    {
        java.lang.String sqlAF1adido = " ";
        panaderia.beans.FacturasVenta facturasventa = new FacturasVenta();
        java.lang.String sql = "SELECT fv.*,\tc.cl_nombre,\tc.cl_apellidos,\tc.cl_direccion,\tc.cl_descuento,\tc.cl_nombre_comercial,\tloc.loc_nombre,\tprov.prov_nombre,\tc.cl_pf_id FROM facturas_venta fv JOIN clientes c ON c.cl_id=fv.fv_cl_id JOIN provincias prov ON prov.prov_id = c.cl_prov_id JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo WHERE (fv_invalida is null or fv_invalida = 0) ";
        try 
        {
            panaderia.beans.FacturasVenta facturasventaFiltro = new FacturasVenta();
            if(listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_VENTA") != null)
                facturasventaFiltro = (panaderia.beans.FacturasVenta)listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_VENTA");
            if(!utils.Utils.empty(facturasventaFiltro.getFvClId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_cl_id = '").append(facturasventaFiltro.getFvClId()).append("'").toString();
            if(facturasventaFiltro.getFvFecha() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha = '").append(facturasventaFiltro.getFvFecha()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_id like '%").append(facturasventaFiltro.getFvId()).append("%'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvIva()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_iva = '").append(facturasventaFiltro.getFvIva()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvNumeroFactura()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_numero_factura like '%").append(facturasventaFiltro.getFvNumeroFactura()).append("%'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvPagada()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_pagada = '").append(facturasventaFiltro.getFvPagada()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvTotal()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_total = '").append(facturasventaFiltro.getFvTotal()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvImportePendiente()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_importe_pendiente = '").append(facturasventaFiltro.getFvImportePendiente()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvObservaciones()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND UPPER(fv_observaciones) like UPPER('%").append(facturasventaFiltro.getFvObservaciones()).append("%')").toString();
            if(facturasventaFiltro.getClDatosRelacionados() != null && !utils.Utils.empty(facturasventaFiltro.getClDatosRelacionados()[0]))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND UPPER(cl_nombre) LIKE UPPER('%").append(facturasventaFiltro.getClDatosRelacionados()[0]).append("%')").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getClPfId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND cl_pf_id = '").append(facturasventaFiltro.getClPfId()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvCierre()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_cierre = '").append(facturasventaFiltro.getFvCierre()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getClCobId()))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND cl_cob_id = '").append(facturasventaFiltro.getClCobId()).append("'").toString();
            if(facturasventaFiltro.getFvFechaDesde() != null && facturasventaFiltro.getFvFechaHasta() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha BETWEEN '").append(facturasventaFiltro.getFvFechaDesde()).append("' AND '").append(facturasventaFiltro.getFvFechaHasta()).append("' ").toString();
            else
            if(facturasventaFiltro.getFvFechaDesde() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha >= '").append(facturasventaFiltro.getFvFechaDesde()).append("'").toString();
            else
            if(facturasventaFiltro.getFvFechaHasta() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha <= '").append(facturasventaFiltro.getFvFechaHasta()).append("'").toString();
            if(facturasventaFiltro.getFvFechaPago() != null)
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fecha_pago = '").append(facturasventaFiltro.getFvFechaPago()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvTipo()) && !facturasventaFiltro.getFvTipo().equals("3"))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_tipo = '").append(facturasventaFiltro.getFvTipo()).append("'").toString();
            if(!utils.Utils.empty(facturasventaFiltro.getFvTipo()) && facturasventaFiltro.getFvTipo().equals("3"))
                sqlAF1adido = (new StringBuilder(java.lang.String.valueOf(sqlAF1adido))).append("AND fv_fv_ref IS NOT NULL ").toString();
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return utils.UtilesDAO.generaSqlListadoPaginado((new StringBuilder(java.lang.String.valueOf(sql))).append(sqlAF1adido).toString(), listaPaginada, facturasventa);
    }

  public int crearFacturaRectificativa(String fvId)
  {
     
    Connection conexion  = utils.PoolConexiones.conexion(); 
    try
    {
      return crearFacturaRectificativa(conexion, fvId);
    }
    catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
    finally{ utils.PoolConexiones.cerrarConexion(conexion);  }
  }

    public int crearFacturaRectificativa(java.sql.Connection conexion, java.lang.String fvId)
        throws java.sql.SQLException
    {
        panaderia.beans.FacturasVenta facturaInicio = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(fvId);
    	//Clientes cliente = Clientes.getClientesByClId(facturaInicio.getFvClId());
    	//Dejamos el numero que tenia la antigua y la fecha
    	//String numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(facturaInicio.getFvIva(), cliente.getClIdEmpresa()) + 1);
        java.sql.PreparedStatement sentencia = conexion.prepareStatement((new StringBuilder("INSERT INTO facturas_venta (fv_cl_id,fv_fecha,fv_iva,fv_numero_factura,fv_observaciones,fv_pagada,fv_total,fv_cierre,fv_fecha_pago,fv_tipo,fv_fv_ref,fv_importe_pendiente,fv_id_empresa) SELECT fv_cl_id,str_to_date('")).append(facturaInicio.getFvFecha(Utils.DATE_SHORT)).append("','%d/%m/%Y'),fv_iva,").append(facturaInicio.getFvNumeroFactura()).append(",fv_observaciones,fv_pagada,fv_total,'0',fv_fecha_pago,fv_tipo,?,fv_importe_pendiente,").append(facturaInicio.getFvIdEmpresa()).append(" ").append("from facturas_venta where fv_id = ?").toString());
        sentencia.setString(1, fvId);
        sentencia.setString(2, fvId);
        sentencia.executeUpdate();
        int autoIncKeyFromApi = -1;
        java.sql.ResultSet rs = sentencia.getGeneratedKeys();
        if(rs.next())
            autoIncKeyFromApi = rs.getInt(1);
        sentencia = conexion.prepareStatement((new StringBuilder("INSERT INTO facturas_venta_detalle (fvd_cantidad,fvd_fv_id,fvd_importe,fvd_iva,fvd_linea,fvd_precio_venta,fvd_producto,fvd_descuento) SELECT fvd_cantidad,")).append(autoIncKeyFromApi).append(",fvd_importe,fvd_iva,fvd_linea,fvd_precio_venta,fvd_producto,fvd_descuento ").append("from facturas_venta_detalle where fvd_fv_id = ?").toString());
        sentencia.setString(1, fvId);
        int n = sentencia.executeUpdate();
        sentencia.close();
        facturaInicio.setFvInvalida("1");
        if(n > 0)
            n = facturaInicio.actualiza();
        return autoIncKeyFromApi;
    }

    private static final long serialVersionUID = 1L;
}
