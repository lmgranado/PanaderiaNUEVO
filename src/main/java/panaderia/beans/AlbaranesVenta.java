package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;


import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class AlbaranesVenta extends panaderia.beans.entidad.AlbaranesVentaEntidad
{
  private static final long serialVersionUID = 1L;
  
  public AlbaranesVenta(){ super(); }

  public static AlbaranesVenta getAlbaranesVentaByAvClId(String avClId)
  {
    AlbaranesVenta elto = new AlbaranesVenta();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta WHERE av_cl_id='"+avClId+"'");
    return elto;
  }
  
  //El periodo debe ser SEMANAL, MENSUAL, QUINCENAL
  public static ArrayList getAllAlbaranesVentaDistinctClientesBetweenDias(String dia1, String dia2, String periodoFacturacion)
  {
    ArrayList lista = EntidadBean.consulta("SELECT DISTINCT(av_cl_id) FROM albaranes_venta " +
								    	   "join clientes on albaranes_venta.av_cl_id = clientes.cl_id " +
								    	   "join periodos_facturacion ON clientes.cl_pf_id = periodos_facturacion.pf_id " +
    									   "WHERE av_fecha BETWEEN STR_TO_DATE('" + dia1 + "','%d/%m/%Y') AND STR_TO_DATE('" + dia2 + "','%d/%m/%Y') " +
    									   "AND IFNULL(av_cierre,0) != '1' " + 
    									   "AND periodos_facturacion.pf_descripcion = '" + periodoFacturacion + "'");
    return lista;
  }
  
  public static int cierraAlbaranesClienteDias( String avClId, String dia1, String dia2 )
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion();
	  String sql = "UPDATE albaranes_venta set av_cierre=1 " +
	  			   " WHERE av_fecha BETWEEN STR_TO_DATE('" + dia1 + "','%d/%m/%Y') AND STR_TO_DATE('" + dia2 + "','%d/%m/%Y')" +
	  			   " AND av_cl_id = ?";
	  int n = -1;
	  try{
		  PreparedStatement sentencia = conexion.prepareStatement(sql);
		  sentencia.setString(1, avClId );
		      
		  n = sentencia.executeUpdate();
		  sentencia.close();
		  
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static ArrayList getAllPrevisionFabricacionDirecto(String fechaInicio, 
				  											 String fechaFin,
															 String agCliente,
															 String directo,
															 String idCliente,
															 String agFamilia){
	  
	String groupBy = "GROUP BY notas_entrega_detalle.NOTDET_PRODUCTO";
	String groupByA = "GROUP BY albaranes_venta_detalle.AVD_PRODUCTO";
	String groupByF = "GROUP BY facturas_venta_detalle.FVD_PRODUCTO";
	String groupByFinal = "a";
	if(agCliente.equals("1")){groupBy += ",clientes.CL_NOMBRE_COMERCIAL"; 
							  groupByA += ",clientes.CL_NOMBRE_COMERCIAL"; 
							  groupByF += ",clientes.CL_NOMBRE_COMERCIAL"; 
							  groupByFinal += ",d";}
	if(agFamilia.equals("1")){groupBy += ",familias.FAM_NOMBRE"; 
							  groupByA += ",familias.FAM_NOMBRE"; 
							  groupByF += ",familias.FAM_NOMBRE"; 
							  groupByFinal += ",e";}
	
	String campos = "NOTDET_PRODUCTO as a, sum(NOTDET_CANTIDAD) as b, productos.prod_peso_masa as c";
	String camposAlbaran = "AVD_PRODUCTO as a, sum(AVD_CANTIDAD) as b, productos.prod_peso_masa as c";
	String camposFactura = "FVD_PRODUCTO as a, sum(FVD_CANTIDAD) as b, productos.prod_peso_masa as c";
	String camposFinal = "a, sum(b) as b, c";
	if(agCliente.equals("1")){ campos += ",clientes.CL_NOMBRE_COMERCIAL as d"; camposAlbaran += ",clientes.CL_NOMBRE_COMERCIAL as d"; camposFactura += ",clientes.CL_NOMBRE_COMERCIAL as d"; camposFinal += ", d";}
	if(agFamilia.equals("1")){ campos += ",familias.FAM_NOMBRE as e"; camposAlbaran += ",familias.FAM_NOMBRE as e"; camposFactura += ",familias.FAM_NOMBRE as e"; camposFinal += ", e";}
	
	String order = "NOTDET_PRODUCTO";
	String orderDirecto = "1";
	if(agCliente.equals("1")) order = "clientes.CL_NOMBRE_COMERCIAL," + order;
	if(agFamilia.equals("1")) order = "familias.FAM_NOMBRE," + order;
	
	String where = "";
	if(!idCliente.equals("")) where += "AND clientes.CL_ID = " + idCliente + " ";

		String consultaNotas = "SELECT " + campos +
								" FROM notas_entrega " +
								"JOIN notas_entrega_detalle ON notas_entrega_detalle.NOTDET_NOT_ID = notas_entrega.NOT_ID " +
								"JOIN clientes ON notas_entrega.NOT_CL_ID = clientes.CL_ID " +
								"LEFT JOIN productos ON notas_entrega_detalle.notdet_producto = productos.prod_nombre " +
								"LEFT JOIN familias ON productos.prod_fam_id = familias.fam_id " +
								"WHERE NOT_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
								where + " " + 
								groupBy + " ";
		String consultaAlbaranes = "SELECT " + camposAlbaran +
									" FROM albaranes_venta " +
									"JOIN albaranes_venta_detalle ON albaranes_venta_detalle.AVD_AV_ID = albaranes_venta.AV_ID " +
									"JOIN clientes ON albaranes_venta.AV_CL_ID = clientes.CL_ID " +
									"LEFT JOIN productos ON albaranes_venta_detalle.avd_producto = productos.prod_nombre " +
									"LEFT JOIN familias ON productos.prod_fam_id = familias.fam_id " +
									"WHERE AV_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
									"AND av_tipo = 1 " +
									where + " " + 
									groupByA + " ";
		String consultaFacturas = "SELECT " + camposFactura +
									" FROM facturas_venta " +
									"JOIN facturas_venta_detalle ON facturas_venta_detalle.FVD_FV_ID = facturas_venta.FV_ID " +
									"JOIN clientes ON facturas_venta.FV_CL_ID = clientes.CL_ID " +
									"LEFT JOIN productos ON facturas_venta_detalle.fvd_producto = productos.prod_nombre " +
									"LEFT JOIN familias ON productos.prod_fam_id = familias.fam_id " +
									"WHERE FV_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
									"AND FV_TIPO = 1" +
									where + " " + 
									groupByF + " ";
	
	ArrayList lista = new ArrayList();
	if(directo.equals("solo"))
		lista = EntidadBean.consulta("SELECT " + camposFinal + " FROM (" + consultaAlbaranes + " UNION " + consultaFacturas + ") as T GROUP BY " + groupByFinal + " ORDER BY " + orderDirecto);
	else
		lista = EntidadBean.consulta("SELECT " + camposFinal + " FROM (" + consultaNotas + " UNION " + consultaAlbaranes + " UNION " + consultaFacturas + ") as T GROUP BY " + groupByFinal + " ORDER BY " + orderDirecto);
	return lista;
  }

  public static String getImporteTotalByAvId(String avId)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(avd_importe),3) " +
								    "FROM albaranes_venta_detalle " +
								    "WHERE avd_av_id = " + avId);
    return Utils.skipNullNumero(importe);
  }
  
  public static String getImporteTotalByclIdAndFecha(String avdClId, String dia)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(avd_importe),3) " +
											    "FROM albaranes_venta_detalle " +
											    "JOIN albaranes_venta on avd_av_id = av_id " +
											    "WHERE av_cl_id = " + avdClId + " " +
												"AND av_fecha = str_to_date('" + dia + "','%d/%m/%Y') ");
    return Utils.skipNullNumero(importe);
  }
  
  public static int getSiguienteNumeroAlbaran()
  {
    String numero = EntidadBean.consultarValor("SELECT MAX(av_numero) " +
											    "FROM albaranes_venta ");
    return Integer.parseInt(Utils.skipNullNumero(numero));
  }

  public static AlbaranesVenta getAlbaranesVentaByAvId( String avId )
  {
	    AlbaranesVenta elto = new AlbaranesVenta();
	    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta WHERE av_id='"+avId+"'");
	    String [] datos = AlbaranesVenta.consultarValores( "SELECT"+ 
			"	c.cl_nombre,"+
			"	c.cl_apellidos,"+
			"	c.cl_direccion,"+
			"	loc.loc_id,"+
			"	prov.prov_id,"+
			"	c.cl_descuento,"+
			"	c.cl_nombre_comercial"+
			" FROM albaranes_venta av"+ 
			" JOIN clientes c ON c.cl_id=av.av_cl_id"+
			" JOIN provincias prov ON prov.prov_id = c.cl_prov_id"+
			" JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
			" WHERE av_id='"+avId+"'");
	    
	    //  Parte de las tablas relacionadas
	    if(datos!=null){
			String[] clDatosRelacionados = new String[7];
			clDatosRelacionados[0] = datos[0];
			clDatosRelacionados[1] = datos[1];
			clDatosRelacionados[2] = datos[2];
			clDatosRelacionados[3] = datos[3];
			clDatosRelacionados[4] = datos[4];
			clDatosRelacionados[5] = datos[5];
			clDatosRelacionados[6] = datos[6];
			elto.setClDatosRelacionados( clDatosRelacionados );
	    }
	    
	    return elto;
  }

  public static ArrayList getAllAlbaranesVenta()
  {
    AlbaranesVenta elto = new AlbaranesVenta();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM albaranes_venta");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    AlbaranesVenta albaranesventa = new AlbaranesVenta();
  	try{
    	albaranesventa.setAvClId(rs.getString(AVCLID));
    	albaranesventa.setAvFecha(rs.getTimestamp(AVFECHA));
    	albaranesventa.setAvId(rs.getString(AVID));
    	albaranesventa.setAvIva(rs.getString(AVIVA));
    	albaranesventa.setAvObservaciones(rs.getString(AVOBSERVACIONES));
    	albaranesventa.setAvPagado(rs.getString(AVPAGADO));
    	albaranesventa.setAvTotal(rs.getString(AVTOTAL));
    	albaranesventa.setAvCierre(rs.getString(AVCIERRE));
    	albaranesventa.setAvNumero(rs.getString(AVNUMERO));
    	albaranesventa.setAvTipo(rs.getString(AVTIPO));
    	
    	// Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[7];
    	clDatosRelacionados[0] = rs.getString("cl_nombre");
    	clDatosRelacionados[1] = rs.getString("cl_apellidos");
    	clDatosRelacionados[2] = rs.getString("cl_direccion");
    	clDatosRelacionados[3] = rs.getString("loc_nombre");
    	clDatosRelacionados[4] = rs.getString("prov_nombre");
    	clDatosRelacionados[5] = rs.getString("cl_descuento");
    	clDatosRelacionados[6] = rs.getString("cl_nombre_comercial");
    	albaranesventa.setClDatosRelacionados( clDatosRelacionados );
    	
  	}catch(Exception e){
  		throw e;
  	}
    	return albaranesventa;
  }

  public static Object[] getAlbaranesVentaLista(PaginatedListTest listaPaginada) 
  {
		String sqlA�adido = " ";
		AlbaranesVenta albaranesventa = new AlbaranesVenta();
		String sql = "SELECT av.*,"+ 
				"	c.cl_nombre,"+
				"	c.cl_apellidos,"+
				"	c.cl_direccion,"+
				"	c.cl_descuento,"+
				"	c.cl_nombre_comercial,"+
				"	loc.loc_nombre,"+
				"	prov.prov_nombre,"+
				"	(SELECT CASE WHEN sum(avd_importe) IS NULL THEN 0 ELSE sum(avd_importe) END from albaranes_venta_detalle where avd_av_id = av.av_id) as neto " +
				" FROM albaranes_venta av"+ 
				" JOIN clientes c ON c.cl_id=av.av_cl_id"+
				" JOIN provincias prov ON prov.prov_id = c.cl_prov_id"+
				" JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo"+
				" WHERE 1 = 1";
	
  	try{
		AlbaranesVenta albaranesventaFiltro = new AlbaranesVenta();
		if(listaPaginada.getRequest().getSession().getAttribute("FALBARANES_VENTA")!=null){
			albaranesventaFiltro = (AlbaranesVenta)listaPaginada.getRequest().getSession().getAttribute("FALBARANES_VENTA");
		}
    	if(!Utils.empty(albaranesventaFiltro.getAvClId()))
    		sqlA�adido += "AND " + AVCLID + " = '" + albaranesventaFiltro.getAvClId() + "'";
    	if( albaranesventaFiltro.getAvFecha() != null )
    		sqlA�adido += "AND " + AVFECHA + " = '" + albaranesventaFiltro.getAvFecha() + "'";
    	if(!Utils.empty(albaranesventaFiltro.getAvId()))
    		sqlA�adido += "AND " + AVID + " like '%" + albaranesventaFiltro.getAvId() + "%'";
    	if(!Utils.empty(albaranesventaFiltro.getAvIva()))
    		sqlA�adido += "AND " + AVIVA + " = '" + albaranesventaFiltro.getAvIva() + "'";
    	if(!Utils.empty(albaranesventaFiltro.getAvObservaciones()))
    		sqlA�adido += "AND UPPER(" + AVOBSERVACIONES + ") like UPPER('%" + albaranesventaFiltro.getAvObservaciones() + "%')";
    	if(!Utils.empty(albaranesventaFiltro.getAvPagado()))
    		sqlA�adido += "AND " + AVPAGADO + " = '" + albaranesventaFiltro.getAvPagado() + "'";
    	if(!Utils.empty(albaranesventaFiltro.getAvTotal()))
    		sqlA�adido += "AND " + AVTOTAL + " = '" + albaranesventaFiltro.getAvTotal() + "'";
    	if( !Utils.empty( albaranesventaFiltro.getClPfId() ) )
    		sqlA�adido += "AND " + CLPFID + " = '" + ( albaranesventaFiltro.getClPfId())+ "'";
    	if( !Utils.empty( albaranesventaFiltro.getAvCierre() ) )
    		sqlA�adido += "AND " + AVCIERRE + " = '" + ( albaranesventaFiltro.getAvCierre())+ "'";
    	if( !Utils.empty( albaranesventaFiltro.getAvNumero() ) )
    		sqlA�adido += "AND " + AVNUMERO + " = '" + ( albaranesventaFiltro.getAvNumero())+ "'";
    	if( !Utils.empty( albaranesventaFiltro.getAvTipo() ) )
    		sqlA�adido += "AND " + AVTIPO + " = '" + ( albaranesventaFiltro.getAvTipo())+ "'";
    	
    	// Introducimos busqueda por rango de fechas
    	if( albaranesventaFiltro.getAvFechaDesde()!=null && albaranesventaFiltro.getAvFechaHasta()!=null )
    		sqlA�adido += "AND " +AVFECHA+" BETWEEN '"+albaranesventaFiltro.getAvFechaDesde()+"' AND '"+albaranesventaFiltro.getAvFechaHasta()+"' ";
    	else if( albaranesventaFiltro.getAvFechaDesde()!=null ) 
    		sqlA�adido += "AND " + AVFECHA + " >= '" + albaranesventaFiltro.getAvFechaDesde() + "'";
    	else if( albaranesventaFiltro.getAvFechaHasta()!=null )
    		sqlA�adido += "AND " + AVFECHA + " <= '" + albaranesventaFiltro.getAvFechaHasta() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, albaranesventa);
  }
}
