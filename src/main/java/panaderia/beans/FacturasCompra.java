package panaderia.beans;
import java.sql.ResultSet;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;

import java.util.ArrayList;
public class FacturasCompra extends panaderia.beans.entidad.FacturasCompraEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FacturasCompra(){ super(); }

  /*public static FacturasCompra getFacturasCompraByFcId(String fcId)
  {
    FacturasCompra elto = new FacturasCompra();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra WHERE fc_id='"+fcId+"'");
    return elto;
  }*/

  public static String getImporteTotalByFcId(String fcId)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(fcd_importe),3) " +
								    "FROM facturas_compra_detalle " +
								    "WHERE fcd_fc_id = " + fcId);
    return Utils.skipNullNumero(importe);
  }
  
  public static FacturasCompra getFacturasCompraByFcId(String fcId)
  {
    FacturasCompra elto = new FacturasCompra();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra WHERE fc_id='"+fcId+"'");
    String [] datos = FacturasVenta.consultarValores( "SELECT"+ 
			"	p.pr_nombre,"+
			"	p.pr_apellidos,"+
			"	p.pr_direccion,"+
			"	loc.loc_id,"+
			"	prov.prov_id,"+
			"	p.pr_descuento,"+
			"	p.pr_nombre_comercial,"+
			"	(SELECT CASE WHEN sum(fcd_importe) IS NULL THEN 0 ELSE sum(fcd_importe) END from facturas_compra_detalle where fcd_fc_id = fc.fc_id) as neto " +
			" FROM facturas_compra fc"+ 
			" JOIN proveedores p ON p.pr_id=fc.fc_pr_id"+
			" JOIN provincias prov ON prov.prov_id = p.pr_prov_id"+
			" JOIN localidades loc ON loc.loc_id = p.pr_loc_id AND loc.loc_prov_codigo = prov.prov_codigo WHERE fc_id='"+fcId+"'" );
   			
			//Parte de las tablas relacionadas
			String[] prDatosRelacionados = new String[8];
			prDatosRelacionados[0] = datos[0];
			prDatosRelacionados[1] = datos[1];
			prDatosRelacionados[2] = datos[2];
			prDatosRelacionados[3] = datos[3];
			prDatosRelacionados[4] = datos[4];
			prDatosRelacionados[5] = datos[5];
			prDatosRelacionados[6] = datos[6];
			prDatosRelacionados[7] = datos[7];
			elto.setPrDatosRelacionados( prDatosRelacionados );
			
    return elto;
  }
  
  public static boolean existeFacturaByFcNumeroFactura( String fcNumeroFactura ){
	  
	  boolean existe = false;
	  FacturasCompra fabricacion =  FacturasCompra.getFacturasCompraByFcNumeroFactura(fcNumeroFactura);
	  if( !Utils.skipNull( fabricacion.getFcId() ).equals("") )
		  existe = true;
	  
	  return existe;
  }
  
  
  public static FacturasCompra getFacturasCompraByFcNumeroFactura(String fcNumeroFactura)
  {
    FacturasCompra elto = new FacturasCompra();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra WHERE fc_numero_factura='"+fcNumeroFactura+"'");
    return elto;
  }

  public static FacturasCompra getFacturasCompraByFcPrId(String fcPrId)
  {
    FacturasCompra elto = new FacturasCompra();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra WHERE fc_pr_id='"+fcPrId+"'");
    return elto;
  }

  public static ArrayList getAllFacturasCompra()
  {
    FacturasCompra elto = new FacturasCompra();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM facturas_compra");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FacturasCompra facturascompra = new FacturasCompra();
  	try{
    	facturascompra.setFcFecha(rs.getTimestamp(FCFECHA));
    	facturascompra.setFcId(rs.getString(FCID));
    	facturascompra.setFcIva(rs.getString(FCIVA));
    	facturascompra.setFcNumeroFactura(rs.getString(FCNUMEROFACTURA));
    	facturascompra.setFcObservaciones(rs.getString(FCOBSERVACIONES));
    	facturascompra.setFcPagada(rs.getString(FCPAGADA));
    	facturascompra.setFcPrId(rs.getString(FCPRID));
    	facturascompra.setFcTotal(rs.getString(FCTOTAL));
    	facturascompra.setFcCierre(rs.getString(FCCIERRE));
    	
//    	Parte de las tablas relacionadas
    	String[] prDatosRelacionados = new String[8];
    	prDatosRelacionados[0] = rs.getString("pr_nombre");
    	prDatosRelacionados[1] = rs.getString("pr_apellidos");
    	prDatosRelacionados[2] = rs.getString("pr_direccion");
    	prDatosRelacionados[3] = rs.getString("loc_nombre");
    	prDatosRelacionados[4] = rs.getString("prov_nombre");
    	prDatosRelacionados[5] = rs.getString("pr_descuento");
    	prDatosRelacionados[6] = rs.getString("pr_nombre_comercial");
    	prDatosRelacionados[7] = rs.getString("neto");
    	facturascompra.setPrDatosRelacionados( prDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return facturascompra;
  }

  public static Object[] getFacturasCompraLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FacturasCompra facturascompra = new FacturasCompra();
	String sql = "SELECT fc.*,"+ 
				"	p.pr_nombre,"+
				"	p.pr_apellidos,"+
				"	p.pr_direccion,"+
				"	p.pr_descuento,"+
				"	p.pr_nombre_comercial,"+
				"	loc.loc_nombre,"+
				"	prov.prov_nombre,"+
				"	(SELECT CASE WHEN sum(fcd_importe) IS NULL THEN 0 ELSE sum(fcd_importe) END from facturas_compra_detalle where fcd_fc_id = fc.fc_id) as neto " +
				" FROM facturas_compra fc"+ 
				" JOIN proveedores p ON p.pr_id=fc.fc_pr_id and p.pr_id not in (999, 998) "+
				" JOIN provincias prov ON prov.prov_id = p.pr_prov_id"+
				" JOIN localidades loc ON loc.loc_id = p.pr_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
				" WHERE 1 = 1";
  	try{
		FacturasCompra facturascompraFiltro = new FacturasCompra();
		if(listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA")!=null){
			facturascompraFiltro = (FacturasCompra)listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA");
		}
    	if(facturascompraFiltro.getFcFecha()!=null)
    		sqlAñadido += "AND " + FCFECHA + " = '" + facturascompraFiltro.getFcFecha() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcId()))
    		sqlAñadido += "AND " + FCID + " like '%" + facturascompraFiltro.getFcId() + "%'";
    	if(!Utils.empty(facturascompraFiltro.getFcIva()))
    		sqlAñadido += "AND " + FCIVA + " = '" + facturascompraFiltro.getFcIva() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcNumeroFactura()))
    		sqlAñadido += "AND " + FCNUMEROFACTURA + " like '%" + facturascompraFiltro.getFcNumeroFactura() + "%'";
    	if(!Utils.empty(facturascompraFiltro.getFcObservaciones()))
    		sqlAñadido += "AND UPPER(" + FCOBSERVACIONES + ") like UPPER('%" + facturascompraFiltro.getFcObservaciones() + "%')";
    	if(!Utils.empty(facturascompraFiltro.getFcPagada()))
    		sqlAñadido += "AND " + FCPAGADA + " = '" + facturascompraFiltro.getFcPagada() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcPrId()))
    		sqlAñadido += "AND " + FCPRID + " = '" + facturascompraFiltro.getFcPrId() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcTotal()))
    		sqlAñadido += "AND " + FCTOTAL + " = '" + facturascompraFiltro.getFcTotal() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcCierre()))
    		sqlAñadido += "AND " + FCCIERRE + " = '" + facturascompraFiltro.getFcCierre() + "'";
    	
//    	Introducimos busqueda por rango de fechas
    	if( facturascompraFiltro.getFcFechaDesde()!=null && facturascompraFiltro.getFcFechaHasta()!=null )
    		sqlAñadido += "AND " +FCFECHA+" BETWEEN '"+facturascompraFiltro.getFcFechaDesde()+"' AND '"+facturascompraFiltro.getFcFechaHasta()+"' ";
    	else if( facturascompraFiltro.getFcFechaDesde()!=null ) 
    		sqlAñadido += "AND " + FCFECHA + " >= '" + facturascompraFiltro.getFcFechaDesde() + "'";
    	else if( facturascompraFiltro.getFcFechaHasta()!=null )
    		sqlAñadido += "AND " + FCFECHA + " <= '" + facturascompraFiltro.getFcFechaHasta() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, facturascompra);
  }
  
  /*
   * La diferencia esta en el proveedor
   */
  public static Object[] getFacturasFabricacionLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FacturasCompra facturascompra = new FacturasCompra();
	String sql = "SELECT fc.*,"+ 
				"	p.pr_nombre,"+
				"	p.pr_apellidos,"+
				"	p.pr_direccion,"+
				"	p.pr_descuento,"+
				"	p.pr_nombre_comercial,"+
				"	loc.loc_nombre,"+
				"	prov.prov_nombre,"+
				"	(SELECT CASE WHEN sum(fcd_importe) IS NULL THEN 0 ELSE sum(fcd_importe) END from facturas_compra_detalle where fcd_fc_id = fc.fc_id) as neto " +
				" FROM facturas_compra fc"+ 
				" JOIN proveedores p ON p.pr_id=fc.fc_pr_id and p.pr_id in (999, 998) "+
				" JOIN provincias prov ON prov.prov_id = p.pr_prov_id"+
				" JOIN localidades loc ON loc.loc_id = p.pr_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
				" WHERE 1 = 1";
  	try{
		FacturasCompra facturascompraFiltro = new FacturasCompra();
		if(listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA")!=null){
			facturascompraFiltro = (FacturasCompra)listaPaginada.getRequest().getSession().getAttribute("FFACTURAS_COMPRA");
		}
    	if(facturascompraFiltro.getFcFecha()!=null)
    		sqlAñadido += "AND " + FCFECHA + " = '" + facturascompraFiltro.getFcFecha() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcId()))
    		sqlAñadido += "AND " + FCID + " like '%" + facturascompraFiltro.getFcId() + "%'";
    	if(!Utils.empty(facturascompraFiltro.getFcIva()))
    		sqlAñadido += "AND " + FCIVA + " = '" + facturascompraFiltro.getFcIva() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcNumeroFactura()))
    		sqlAñadido += "AND " + FCNUMEROFACTURA + " like '%" + facturascompraFiltro.getFcNumeroFactura() + "%'";
    	if(!Utils.empty(facturascompraFiltro.getFcObservaciones()))
    		sqlAñadido += "AND UPPER(" + FCOBSERVACIONES + ") like UPPER('%" + facturascompraFiltro.getFcObservaciones() + "%')";
    	if(!Utils.empty(facturascompraFiltro.getFcPagada()))
    		sqlAñadido += "AND " + FCPAGADA + " = '" + facturascompraFiltro.getFcPagada() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcPrId()))
    		sqlAñadido += "AND " + FCPRID + " = '" + facturascompraFiltro.getFcPrId() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcTotal()))
    		sqlAñadido += "AND " + FCTOTAL + " = '" + facturascompraFiltro.getFcTotal() + "'";
    	if(!Utils.empty(facturascompraFiltro.getFcCierre()))
    		sqlAñadido += "AND " + FCCIERRE + " = '" + facturascompraFiltro.getFcCierre() + "'";
    	
//    	Introducimos busqueda por rango de fechas
    	if( facturascompraFiltro.getFcFechaDesde()!=null && facturascompraFiltro.getFcFechaHasta()!=null )
    		sqlAñadido += "AND " +FCFECHA+" BETWEEN '"+facturascompraFiltro.getFcFechaDesde()+"' AND '"+facturascompraFiltro.getFcFechaHasta()+"' ";
    	else if( facturascompraFiltro.getFcFechaDesde()!=null ) 
    		sqlAñadido += "AND " + FCFECHA + " >= '" + facturascompraFiltro.getFcFechaDesde() + "'";
    	else if( facturascompraFiltro.getFcFechaHasta()!=null )
    		sqlAñadido += "AND " + FCFECHA + " <= '" + facturascompraFiltro.getFcFechaHasta() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, facturascompra);
  }
}
