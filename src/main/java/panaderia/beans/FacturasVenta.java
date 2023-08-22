package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.EntityBean;

import javax.servlet.http.HttpServletRequest;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;

import utils.UtilesDAO;
import utils.Utils;
public class FacturasVenta extends panaderia.beans.entidad.FacturasVentaEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FacturasVenta(){ super(); }

  public static ArrayList getFacturasVentaNoPagadasByFvClId(String fvClId)
  {
    FacturasVenta elto = new FacturasVenta();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta WHERE fv_cl_id='"+fvClId+"' and fv_pagada=0 and (fv_invalida is null or fv_invalida = 0) order by fv_fecha desc");
    return lista;
  }

  public static ArrayList getParteDiarioCobros( String cobId, String fecha_desde, String fecha_hasta, String tipo, String estado, String fvClId, String fecha_pago ){
	  
	  String where = "";
	  if(!Utils.skipNull(fecha_desde).equals("") && !Utils.skipNull(fecha_pago).equals(""))
		  where = " where fv_fecha BETWEEN str_to_date('"+fecha_desde+"','%d/%m/%Y') AND str_to_date('"+fecha_hasta+"','%d/%m/%Y') " +
		  		  " and fv_fecha_pago BETWEEN str_to_date('"+fecha_pago+"','%d/%m/%Y') AND str_to_date('"+fecha_pago+"','%d/%m/%Y') ";
	  else if(!Utils.skipNull(fecha_desde).equals(""))
		  where = " where fv_fecha BETWEEN str_to_date('"+fecha_desde+"','%d/%m/%Y') AND str_to_date('"+fecha_hasta+"','%d/%m/%Y') ";
	  else if(!Utils.skipNull(fecha_pago).equals(""))
		  where = " where fv_fecha_pago BETWEEN str_to_date('"+fecha_pago+"','%d/%m/%Y') AND str_to_date('"+fecha_pago+"','%d/%m/%Y') ";

	  
	  if( !Utils.empty(cobId) )
		  where += " and cob.cob_id="+cobId;
	  if( !Utils.empty(tipo) )
		  where += " and fv.fv_iva='"+tipo+"'";
	  if( !Utils.empty(estado) )
		  where += " and fv.fv_pagada="+estado;
	  if( !Utils.empty(fvClId) )
		  where += " and fv.fv_cl_id="+fvClId;
	  
	  ArrayList lista = FacturasVenta.consulta("select fv.fv_numero_factura, fv.fv_fecha as fechaFactura, fv.fv_fecha_pago as fechaPago,  fv.fv_iva as tipo, " +
	  		    " fv.fv_total as total, fv.fv_importe_pendiente as importe_pendiente, cl.cl_id , cl.cl_nombre_comercial as cliente, cob.cob_id, " +
	  		    " concat(cob.cob_nombre,' ',cob.cob_apellidos) as cobrador " + 
				" from facturas_venta fv	" +
				" left join clientes cl on fv.fv_cl_id = cl.cl_id " +
				" left join cobradores cob on cl.cl_cob_id = cob.cob_id " +
				where +
				" and (fv.fv_invalida is null or fv.fv_invalida = 0) " +
				" order by cob.cob_nombre asc, cl.cl_nombre_comercial asc, fv_fecha asc");
	  
	  return lista;
  }					
	  					
  public static FacturasVenta getFacturasVentaByFvId(String fvId)
  {
    FacturasVenta elto = new FacturasVenta();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta WHERE fv_id='"+fvId+"'");
    String [] datos = FacturasVenta.consultarValores( "SELECT"+ 
			"	c.cl_nombre,"+
			"	c.cl_apellidos,"+
			"	c.cl_direccion,"+			
			"	loc.loc_id,"+
			"	prov.prov_id,"+
			"	c.cl_descuento,"+
			"	c.cl_nombre_comercial"+
			" FROM facturas_venta fv"+ 
			" JOIN clientes c ON c.cl_id=fv.fv_cl_id"+
			" JOIN provincias prov ON prov.prov_id = c.cl_prov_id"+
			" JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo WHERE fv_id='"+fvId+"'" );
   			
			//Parte de las tablas relacionadas
			String[] clDatosRelacionados = new String[7];
			clDatosRelacionados[0] = datos[0];
			clDatosRelacionados[1] = datos[1];
			clDatosRelacionados[2] = datos[2];
			clDatosRelacionados[3] = datos[3];
			clDatosRelacionados[4] = datos[4];
			clDatosRelacionados[5] = datos[5];
			clDatosRelacionados[6] = datos[6];
			elto.setClDatosRelacionados( clDatosRelacionados );
			
    return elto;
  }

  public static int getSiguienteNumeroFactura(String tipo, String dondeFabrica)
  {
	if(Utils.skipNull(dondeFabrica).equals(""))
		dondeFabrica = "0";
	
    String numero = EntidadBean.consultarValor("SELECT MAX(fv_numero_factura) " +
									    	    "FROM facturas_venta " +
									    	    "JOIN clientes on fv_cl_id = cl_id " +
									    	    "WHERE fv_iva = '" + tipo + "' " +
									    	    " and fv_tipo != '2' " +
									    	    "and cl_id_empresa = '" + dondeFabrica + "' ");
    return Integer.parseInt(Utils.skipNullNumero(numero));
  }
  
  public static int getSiguienteNumeroFacturaAbono(String tipo, String dondeFabrica)
  {
	if(Utils.skipNull(dondeFabrica).equals(""))
		dondeFabrica = "0";
    String numero = EntidadBean.consultarValor("SELECT MAX(fv_numero_factura) " +
									    	    "FROM facturas_venta " +
									    	    "JOIN clientes on fv_cl_id = cl_id " +
									    	    "WHERE fv_iva = '" + tipo + "' " +
									    	    " and fv_tipo = '2' " +
									    	    "and cl_id_empresa = '" + dondeFabrica + "' ");
    return Integer.parseInt(Utils.skipNullNumero(numero));
  }
  
  public static FacturasVenta getFacturasVentaByFvNumeroFactura(String fvNumeroFactura)
  {
    FacturasVenta elto = new FacturasVenta();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta WHERE fv_numero_factura='"+fvNumeroFactura+"'");
    return elto;
  }
  

  public static String getImporteTotalByFvId(String fvId)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(fvd_importe),3) " +
								    "FROM facturas_venta_detalle " +
								    "WHERE fvd_fv_id = " + fvId);
    return Utils.skipNullNumero(importe);
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
  
  
  public static String getImportesPendientesByFvClId( String fvClId ){
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(fv_importe_pendiente),3) " +
								    "FROM facturas_venta " +
								    "WHERE fv_cl_id = " + fvClId+" and fv_pagada=0 " +
								    "and (fv_invalida is null or fv_invalida = 0)");
    return Utils.skipNullNumero(importe);
  }
  
  public static ArrayList getAllFacturasVenta()
  {
    FacturasVenta elto = new FacturasVenta();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_venta order by fv_fecha desc");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FacturasVenta facturasventa = new FacturasVenta();
  	try{
    	facturasventa.setFvClId(rs.getString(FVCLID));
    	facturasventa.setFvFecha(rs.getTimestamp(FVFECHA));
    	facturasventa.setFvId(rs.getString(FVID));
    	facturasventa.setFvIva(rs.getString(FVIVA));
    	facturasventa.setFvNumeroFactura(rs.getString(FVNUMEROFACTURA));
    	facturasventa.setFvObservaciones(rs.getString(FVOBSERVACIONES));
    	facturasventa.setFvPagada(rs.getString(FVPAGADA));
    	facturasventa.setFvTotal(rs.getString(FVTOTAL));
    	facturasventa.setFvCierre(rs.getString(FVCIERRE));
    	facturasventa.setFvImportePendiente(rs.getString(FVIMPORTEPENDIENTE));
    	facturasventa.setFvFechaPago(rs.getTimestamp(FVFECHAPAGO));
    	facturasventa.setFvTipo(rs.getString(FVTIPO));
    	facturasventa.setFvFvRef(rs.getString(FVFVREF));
    	facturasventa.setFvInvalida(rs.getString(FVINVALIDA));
    	facturasventa.setFvIdEmpresa(rs.getString(FVIDEMPRESA));
    	
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[8];
    	clDatosRelacionados[0] = rs.getString("cl_nombre");
    	clDatosRelacionados[1] = rs.getString("cl_apellidos");
    	clDatosRelacionados[2] = rs.getString("cl_direccion");
    	clDatosRelacionados[3] = rs.getString("loc_nombre");
    	clDatosRelacionados[4] = rs.getString("prov_nombre");
    	clDatosRelacionados[5] = rs.getString("cl_descuento");
    	clDatosRelacionados[6] = rs.getString("cl_nombre_comercial");
    	try{
    		clDatosRelacionados[7] = rs.getString("cl_pf_id");
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	facturasventa.setClDatosRelacionados( clDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return facturasventa;
  }
  
  public static String getFacturasVentaTotalPendienteFiltro(HttpServletRequest filtro) 
  {
	String sqlA�adido = " ";
	String sql = "SELECT sum(fv.fv_importe_pendiente)"+
			" FROM facturas_venta fv"+ 
			" JOIN clientes c ON c.cl_id=fv.fv_cl_id"+
			" JOIN provincias prov ON prov.prov_id = c.cl_prov_id"+
			" JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
			" WHERE (fv.fv_invalida is null or fv.fv_invalida = 0) ";
  	try{
		FacturasVenta facturasventaFiltro = new FacturasVenta();
		if(filtro.getSession().getAttribute("FFACTURAS_VENTA")!=null){
			facturasventaFiltro = (FacturasVenta)filtro.getSession().getAttribute("FFACTURAS_VENTA");
		}
    	if(!Utils.empty(facturasventaFiltro.getFvClId()))
    		sqlA�adido += "AND " + FVCLID + " = '" + facturasventaFiltro.getFvClId() + "'";
    	if(facturasventaFiltro.getFvFecha()!=null)
    		sqlA�adido += "AND " + FVFECHA + " = '" + facturasventaFiltro.getFvFecha() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvId()))
    		sqlA�adido += "AND " + FVID + " like '%" + facturasventaFiltro.getFvId() + "%'";
    	if(!Utils.empty(facturasventaFiltro.getFvIva()))
    		sqlA�adido += "AND " + FVIVA + " = '" + facturasventaFiltro.getFvIva() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvNumeroFactura()))
    		sqlA�adido += "AND " + FVNUMEROFACTURA + " like '%" + facturasventaFiltro.getFvNumeroFactura() + "%'";
    	if(!Utils.empty(facturasventaFiltro.getFvPagada()))
    		sqlA�adido += "AND " + FVPAGADA + " = '" + facturasventaFiltro.getFvPagada() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvTotal()))
    		sqlA�adido += "AND " + FVTOTAL + " = '" + facturasventaFiltro.getFvTotal() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvImportePendiente()))
    		sqlA�adido += "AND " + FVIMPORTEPENDIENTE + " = '" + facturasventaFiltro.getFvImportePendiente() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvObservaciones()))
    		sqlA�adido += "AND UPPER(" + FVOBSERVACIONES + ") like UPPER('%" + facturasventaFiltro.getFvObservaciones() + "%')";
    	if( facturasventaFiltro.getClDatosRelacionados()!= null && !Utils.empty( ( facturasventaFiltro.getClDatosRelacionados()[0] ) ) )
    		sqlA�adido += "AND UPPER(" + CLNOMBRE + ") LIKE UPPER('%" + ( facturasventaFiltro.getClDatosRelacionados()[0])+ "%')";
    	if( !Utils.empty( facturasventaFiltro.getClPfId() ) )
    		sqlA�adido += "AND " + CLPFID + " = '" + ( facturasventaFiltro.getClPfId())+ "'";
    	if( !Utils.empty( facturasventaFiltro.getFvCierre() ) )
    		sqlA�adido += "AND " + FVCIERRE + " = '" + ( facturasventaFiltro.getFvCierre())+ "'";
    	//Nuevo
    	if( !Utils.empty( facturasventaFiltro.getClCobId() ) )
    		sqlA�adido += "AND " + CLCOBID + " = '" + ( facturasventaFiltro.getClCobId())+ "'";
    	
    	//Introducimos busqueda por rango de fechas
    	if( facturasventaFiltro.getFvFechaDesde()!=null && facturasventaFiltro.getFvFechaHasta()!=null )
    		sqlA�adido += "AND " +FVFECHA+" BETWEEN '"+facturasventaFiltro.getFvFechaDesde()+"' AND '"+facturasventaFiltro.getFvFechaHasta()+"' ";
    	else if( facturasventaFiltro.getFvFechaDesde()!=null ) 
    		sqlA�adido += "AND " + FVFECHA + " >= '" + facturasventaFiltro.getFvFechaDesde() + "'";
    	else if( facturasventaFiltro.getFvFechaHasta()!=null )
    		sqlA�adido += "AND " + FVFECHA + " <= '" + facturasventaFiltro.getFvFechaHasta() + "'";
    	
    	if( facturasventaFiltro.getFvFechaPago()!=null )
    		sqlA�adido += "AND " + FVFECHAPAGO + " = '" + facturasventaFiltro.getFvFechaPago() + "'";
    	
    	
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return EntidadBean.consultarValor(sql + sqlA�adido);
  }

  public static Object[] getFacturasVentaLista(PaginatedListTest listaPaginada) 
  {
	String sqlA�adido = " ";
	FacturasVenta facturasventa = new FacturasVenta();
	String sql = "SELECT fv.*,"+ 
			"	c.cl_nombre,"+
			"	c.cl_apellidos,"+
			"	c.cl_direccion,"+
			"	c.cl_descuento,"+
			"	c.cl_nombre_comercial,"+
			"	loc.loc_nombre,"+
			"	prov.prov_nombre,"+
			"	c.cl_pf_id"+
			" FROM facturas_venta fv"+ 
			" JOIN clientes c ON c.cl_id=fv.fv_cl_id"+
			" JOIN provincias prov ON prov.prov_id = c.cl_prov_id"+
			" JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
			" WHERE (fv_invalida is null or fv_invalida = 0) ";
  	try{
		FacturasVenta facturasventaFiltro = new FacturasVenta();
		if(listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_VENTA")!=null){
			facturasventaFiltro = (FacturasVenta)listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_VENTA");
		}
    	if(!Utils.empty(facturasventaFiltro.getFvClId()))
    		sqlA�adido += "AND " + FVCLID + " = '" + facturasventaFiltro.getFvClId() + "'";
    	if(facturasventaFiltro.getFvFecha()!=null)
    		sqlA�adido += "AND " + FVFECHA + " = '" + facturasventaFiltro.getFvFecha() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvId()))
    		sqlA�adido += "AND " + FVID + " like '%" + facturasventaFiltro.getFvId() + "%'";
    	if(!Utils.empty(facturasventaFiltro.getFvIva()))
    		sqlA�adido += "AND " + FVIVA + " = '" + facturasventaFiltro.getFvIva() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvNumeroFactura()))
    		sqlA�adido += "AND " + FVNUMEROFACTURA + " like '%" + facturasventaFiltro.getFvNumeroFactura() + "%'";
    	if(!Utils.empty(facturasventaFiltro.getFvPagada()))
    		sqlA�adido += "AND " + FVPAGADA + " = '" + facturasventaFiltro.getFvPagada() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvTotal()))
    		sqlA�adido += "AND " + FVTOTAL + " = '" + facturasventaFiltro.getFvTotal() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvImportePendiente()))
    		sqlA�adido += "AND " + FVIMPORTEPENDIENTE + " = '" + facturasventaFiltro.getFvImportePendiente() + "'";
    	if(!Utils.empty(facturasventaFiltro.getFvObservaciones()))
    		sqlA�adido += "AND UPPER(" + FVOBSERVACIONES + ") like UPPER('%" + facturasventaFiltro.getFvObservaciones() + "%')";
    	if( facturasventaFiltro.getClDatosRelacionados()!= null && !Utils.empty( ( facturasventaFiltro.getClDatosRelacionados()[0] ) ) )
    		sqlA�adido += "AND UPPER(" + CLNOMBRE + ") LIKE UPPER('%" + ( facturasventaFiltro.getClDatosRelacionados()[0])+ "%')";
    	if( !Utils.empty( facturasventaFiltro.getClPfId() ) )
    		sqlA�adido += "AND " + CLPFID + " = '" + ( facturasventaFiltro.getClPfId())+ "'";
    	if( !Utils.empty( facturasventaFiltro.getFvCierre() ) )
    		sqlA�adido += "AND " + FVCIERRE + " = '" + ( facturasventaFiltro.getFvCierre())+ "'";
    	//Nuevo
    	if( !Utils.empty( facturasventaFiltro.getClCobId() ) )
    		sqlA�adido += "AND " + CLCOBID + " = '" + ( facturasventaFiltro.getClCobId())+ "'";
    	
    	//Introducimos busqueda por rango de fechas
    	if( facturasventaFiltro.getFvFechaDesde()!=null && facturasventaFiltro.getFvFechaHasta()!=null )
    		sqlA�adido += "AND " +FVFECHA+" BETWEEN '"+facturasventaFiltro.getFvFechaDesde()+"' AND '"+facturasventaFiltro.getFvFechaHasta()+"' ";
    	else if( facturasventaFiltro.getFvFechaDesde()!=null ) 
    		sqlA�adido += "AND " + FVFECHA + " >= '" + facturasventaFiltro.getFvFechaDesde() + "'";
    	else if( facturasventaFiltro.getFvFechaHasta()!=null )
    		sqlA�adido += "AND " + FVFECHA + " <= '" + facturasventaFiltro.getFvFechaHasta() + "'";
    	
    	//Introducimos la fecha de pago
    	if( facturasventaFiltro.getFvFechaPago()!=null )
    		sqlA�adido += "AND " + FVFECHAPAGO + " = '" + facturasventaFiltro.getFvFechaPago() + "'";
    	
    	if( !Utils.empty(facturasventaFiltro.getFvTipo()) && !facturasventaFiltro.getFvTipo().equals("3"))
    		sqlA�adido += "AND " + FVTIPO + " = '" + facturasventaFiltro.getFvTipo() + "'";
    	
    	if( !Utils.empty(facturasventaFiltro.getFvTipo()) && facturasventaFiltro.getFvTipo().equals("3"))
    		sqlA�adido += "AND " + FVFVREF + " IS NOT NULL ";
    	
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, facturasventa);
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

  
  public int crearFacturaRectificativa(Connection conexion, String fvId) throws SQLException
  {
	//Primero creamos la factura (base)
	FacturasVenta facturaInicio = FacturasVenta.getFacturasVentaByFvId(fvId);
	//Clientes cliente = Clientes.getClientesByClId(facturaInicio.getFvClId());
	//String numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(facturaInicio.getFvIva(), cliente.getClIdEmpresa()) + 1);
	PreparedStatement sentencia = 
		conexion.prepareStatement("INSERT INTO facturas_venta (fv_cl_id,fv_fecha,fv_iva,fv_numero_factura,fv_observaciones,fv_pagada,fv_total,fv_cierre,fv_fecha_pago,fv_tipo,fv_fv_ref,fv_importe_pendiente,fv_id_empresa) " +
								  "SELECT fv_cl_id,str_to_date('" + facturaInicio.getFvFecha(Utils.DATE_SHORT) + "','%d/%m/%Y'),fv_iva," + facturaInicio.getFvNumeroFactura() + ",fv_observaciones,fv_pagada,fv_total,'0',fv_fecha_pago,fv_tipo,?,fv_importe_pendiente," + facturaInicio.getFvIdEmpresa() +  
								  " from facturas_venta where fv_id = ?");

    sentencia.setString(1, fvId);
    sentencia.setString(2, fvId);
    sentencia.executeUpdate();
    int autoIncKeyFromApi = -1;

    ResultSet rs = sentencia.getGeneratedKeys();

    if (rs.next()) {
        autoIncKeyFromApi = rs.getInt(1);
    }
    
	//Segundo creamos los detalles
	sentencia = 
		conexion.prepareStatement("INSERT INTO facturas_venta_detalle (fvd_cantidad,fvd_fv_id,fvd_importe,fvd_iva,fvd_linea,fvd_precio_venta,fvd_producto,fvd_descuento,fvd_fv_id, fvd_st_id) " +
								  "SELECT fvd_cantidad," + autoIncKeyFromApi + ",fvd_importe,fvd_iva,fvd_linea,fvd_precio_venta,fvd_producto,fvd_descuento,fvd_fv_id, fvd_st_id " + 
								  "from facturas_venta_detalle where fvd_fv_id = ?");

    sentencia.setString(1, fvId);
    int n = sentencia.executeUpdate();
    sentencia.close();
    
    //Tercero, ponemos la factura antigua como deshabilitada
    facturaInicio.setFvInvalida("1");
    if(n>0)
    	n = facturaInicio.actualiza();
    
    return autoIncKeyFromApi;
  }
}
