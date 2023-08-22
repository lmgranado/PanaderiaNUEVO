package panaderia.beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.Log4j;

import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class NotasEntrega extends panaderia.beans.entidad.NotasEntregaEntidad
{
  private static final long serialVersionUID = 1L;
  
  public NotasEntrega(){ super(); }

  
  public static int recalculaOrdenes( NotasEntrega notaentrega, boolean insertar )
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion();
	  String sql = "update notas_entrega set not_orden=not_orden-1 where not_fecha=? and not_nombre=? and not_orden>? order by not_orden asc";
	  String orden = "asc";
	  int n = -1;
	  try{
		  if( existeOrden(notaentrega) ){
				  orden = "desc";
				  sql = "update notas_entrega set not_orden=not_orden+1 where not_fecha=? and not_nombre=? and not_orden>=? order by not_orden "+ orden;
		  }
		  PreparedStatement sentencia = conexion.prepareStatement(sql);
		  sentencia.setTimestamp( 1, new java.sql.Timestamp(notaentrega.getNotFecha().getTime() ) );
		  sentencia.setString(2, notaentrega.getNotNombre() );
		  sentencia.setString(3, notaentrega.getNotOrden() );
		      
		  n = sentencia.executeUpdate();
		  sentencia.close();
		  
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static int cierraClienteDia( String avdClId, String dia )
  {
	   
	  Connection conexion  = utils.PoolConexiones.conexion();
	  String sql = "update notas_entrega set not_cierre=1 where not_fecha = STR_TO_DATE('" + dia + "','%d/%m/%Y') and not_cl_id = ?";
	  int n = -1;
	  try{
		  PreparedStatement sentencia = conexion.prepareStatement(sql);
		  sentencia.setString(1, avdClId );
		      
		  n = sentencia.executeUpdate();
		  sentencia.close();
		  
	  }catch( SQLException e ){ utils.Log4j.error("Error", e); return -1; }
	    finally{ utils.PoolConexiones.cerrarConexion(conexion); }
	    
	  return n;
  }
  
  public static boolean existeOrden( NotasEntrega notaentrega )
  {
	   
	  ResultSet rs = null;
	  Connection conexion  = utils.PoolConexiones.conexion(); 
	  boolean existe = false;
	  try{
		 	  
	      PreparedStatement sentencia = conexion.prepareStatement("SELECT not_id FROM notas_entrega "
	      +" WHERE not_fecha=? and not_nombre=? and not_orden=?");
	      sentencia.setTimestamp( 1, new java.sql.Timestamp(notaentrega.getNotFecha().getTime() ) );
	      sentencia.setString(2, notaentrega.getNotNombre() );
	      sentencia.setString(3, notaentrega.getNotOrden() );
	      
	      rs = sentencia.executeQuery();
	      if( rs.next() )
	    	  existe = true;
	      
	      sentencia.close();
	  }catch( SQLException e ){
		  utils.Log4j.error("Error", e); 
		  return existe; 
	  }
	  finally{ 
		  utils.PoolConexiones.cerrarConexion(conexion); 
	  }
	    
	  return existe;
  }
  
  /** Este método en primer luegar me comprueba si existe una nota con dicho orden, si existe ese orden, entonces me incrementa en uno dicho orden
   *  y sigue comprobando hasta que encuentre uno libre. Esto se hace ya que si en la generación automática de notas de entregas, pueden existir 
   *  notas individuales que ya contengan un orden igual al que se quiere insertar */
  public static String getOrdenCorrecto( NotasEntrega notaentrega )
  {
	  //Se inicializa con el orden que trae, ya que sino existe el orden se inserta de modo normal
	  int ordenCorrecto = Integer.parseInt( notaentrega.getNotOrden() );
	  try{
		  while( NotasEntrega.existeOrden(notaentrega) ){
			  ordenCorrecto++;
			  notaentrega.setNotOrden( String.valueOf( ordenCorrecto) );
		  }
		  
	  }catch( Exception e ){
		  utils.Log4j.error("Error", e); 
		  return notaentrega.getNotOrden(); 
	  }
	    
	  return notaentrega.getNotOrden();
  }
  
  
  public static NotasEntrega getNotasEntregaByNotClId(String notClId)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_cl_id='"+notClId+"'");
    return elto;
  }

  public static NotasEntrega getNotasEntregaByNotFecha(String notFecha)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_fecha='"+notFecha+"'");
    return elto;
  }


  public static String getImporteTotalByNotId(String notId)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(NOTDET_IMPORTE),3) " +
								    "FROM notas_entrega_detalle " +
								    "WHERE NOTDET_NOT_ID = " + notId);
    //return Utils.formateaCantidad(Utils.skipNullNumero(importe));
    return Utils.skipNullNumero(importe);
  }
  
  public static String getImporteTotalByClienteDia(String avClId, String dia)
  {
    String importe = EntidadBean.consultarValor("SELECT ROUND(sum(NOTDET_IMPORTE),3) " + 
											    "FROM notas_entrega_detalle " + 
												" JOIN notas_entrega on notas_entrega_detalle.notdet_not_id = notas_entrega.not_id " +
											    "WHERE not_cl_id = '" + avClId + "' AND not_fecha = STR_TO_DATE('" + dia + "','%d/%m/%Y') AND IFNULL(not_cierre,0) != '1'");
    //return Utils.formateaCantidad(Utils.skipNullNumero(importe));
    return Utils.skipNullNumero(importe);
  }
  
  
	
  
  public static NotasEntrega getNotasEntregaByNotId(String notId)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_id='"+notId+"'");
    String [] datos = FacturasVenta.consultarValores("SELECT cl_nombre, cl_apellidos, cl_direccion, loc.loc_id, prov.prov_id, clientes.cl_descuento, rutas.rut_nombre, clientes.cl_nombre_comercial " +
													 "FROM notas_entrega " +
													 "JOIN clientes on cl_id = not_cl_id " +
													 "JOIN rutas on rut_id = not_rut_id " +
													 " JOIN provincias prov ON prov.prov_id = clientes.cl_prov_id"+
													 " JOIN localidades loc ON loc.loc_id = clientes.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
										    		 "WHERE not_id='"+notId+"'");
    
		
	//Parte de las tablas relacionadas
	String[] clDatosRelacionados = new String[8];
	if(datos!=null){
		clDatosRelacionados[0] = datos[0];
		clDatosRelacionados[1] = datos[1];
		clDatosRelacionados[2] = datos[2];
		clDatosRelacionados[3] = datos[3];
		clDatosRelacionados[4] = datos[4];
		clDatosRelacionados[5] = datos[5];
		clDatosRelacionados[6] = datos[6];
		clDatosRelacionados[7] = datos[7];
		elto.setClDatosRelacionados( clDatosRelacionados );
	}
    return elto;
  }
  
  
  public static NotasEntrega getNotasEntregaDiaByCliente(String notClId, String dia)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_cl_id='"+notClId+"' AND not_fecha = STR_TO_DATE('" + dia + "','%d/%m/%Y')");
    String [] datos = FacturasVenta.consultarValores("SELECT cl_nombre, cl_apellidos, cl_direccion, loc.loc_id, prov.prov_id, clientes.cl_descuento, rutas.rut_nombre,clientes.cl_nombre_comercial " +
													 "FROM notas_entrega " +
													 "JOIN clientes on cl_id = " + notClId + " " +
													 "JOIN rutas on rut_id = not_rut_id " +
													 " JOIN provincias prov ON prov.prov_id = clientes.cl_prov_id"+
													 " JOIN localidades loc ON loc.loc_id = clientes.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
										    		 "WHERE not_fecha = STR_TO_DATE('" + dia + "','%d/%m/%Y') ");
    
		
	//Parte de las tablas relacionadas
	String[] clDatosRelacionados = new String[7];
	if(datos!=null){
		clDatosRelacionados[0] = datos[0];
		clDatosRelacionados[1] = datos[1];
		clDatosRelacionados[2] = datos[2];
		clDatosRelacionados[3] = datos[3];
		clDatosRelacionados[4] = datos[4];
		clDatosRelacionados[5] = datos[5];
		clDatosRelacionados[6] = datos[6];
		clDatosRelacionados[6] = datos[7];
		elto.setClDatosRelacionados( clDatosRelacionados );
	}
    return elto;
  }

  public static NotasEntrega getNotasEntregaByNotNombre(String notNombre)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_nombre='"+notNombre+"'");
    return elto;
  }

  public static NotasEntrega getNotasEntregaByNotRutId(String notRutId)
  {
    NotasEntrega elto = new NotasEntrega();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega WHERE not_rut_id='"+notRutId+"'");
    return elto;
  }

  public static ArrayList getAllNotasEntrega()
  {
    NotasEntrega elto = new NotasEntrega();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM notas_entrega");
    return lista;
  }
  
  public static ArrayList getAllNotasEntregaDistict()
  {
    NotasEntrega elto = new NotasEntrega();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT DISTINCT(not_nombre) FROM notas_entrega order by not_nombre asc");
    return lista;
  }
  
  
  public static ArrayList getAllNotasEntregaDistinctClientesDia(String dia)
  {
    ArrayList lista = EntidadBean.consulta("SELECT DISTINCT(not_cl_id) FROM notas_entrega WHERE not_fecha = STR_TO_DATE('" + dia + "','%d/%m/%Y') and IFNULL(not_cierre,0) != '1' ");
    return lista;
  }
  
  public static ArrayList getAllNotasEntregaConversorByEntrega(String entId, String fecha)
  {
     /*ArrayList lista = EntidadBean.consulta("select sysdate() as not_fecha, ent_nombre as not_nombre, end_rut_id as not_rut_id, end_cl_id as not_cl_id, end_orden as not_orden, end_viaje, end_id, end_cu_id from entregas " +
													"join entregas_detalle on ent_id = end_ent_id " +
													"where ent_id = " + entId);*/
	  //Hemos introducido cl_activo=1 porque sino te generaba las notas de los clientes inactivos también.
	  /*ArrayList lista = EntidadBean.consulta("select sysdate() as not_fecha, ent_nombre as not_nombre, end_rut_id as not_rut_id, end_cl_id as not_cl_id, end_orden as not_orden, end_viaje, end_id, end_cu_id from entregas " +
				" join entregas_detalle on ent_id = end_ent_id " +
				" join clientes on end_cl_id = cl_id and cl_activo=1 " +
				" where ent_id = " + entId);*/
	  
	  //Le sumamos tambien a la lista la fecha de generacion de las notas de entrega, ya que por norma el viernes se dejan hechas las notas del
	  //SABADO, DOMINGO Y LUNES, pudiendo ser que el cliente ya el lunes no este de vacaciones y por lo tanto no se generaria su nota y estaria incorrecto,
	  //Por tanto se comprueba si en esa fecha sigue de vacaciones el cliente de dicha entrega y sino se adjunta a la lista de generacion de notas.
	  ArrayList lista = EntidadBean.consulta("select sysdate() as not_fecha, ent_nombre as not_nombre, end_rut_id as not_rut_id, end_cl_id as not_cl_id, end_orden as not_orden, end_viaje, end_id, end_cu_id from entregas " +
				" join entregas_detalle on ent_id = end_ent_id " +
				" join clientes on end_cl_id = cl_id and cl_activo=1 " +
				" where ent_id = " + entId + 
				" union "+
	  			" select distinct sysdate(), ent_nombre as not_nombre, end_rut_id as not_rut_id, end_cl_id as not_cl_id, end_orden as not_orden, end_viaje, end_id, end_cu_id" +
				" from entregas" + 
				" join entregas_detalle on ent_id = end_ent_id" + 
				" join clientes on end_cl_id = cl_id and cl_activo=0" +
				" join vacaciones_clientes on cl_id = vac_cl_id" +
				" where ent_id = " + entId + " AND STR_TO_DATE('"+fecha+"','%d/%m/%Y') NOT BETWEEN vac_fecha_desde AND vac_fecha_hasta");
	     
	  return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    NotasEntrega notasentrega = new NotasEntrega();
  	try{
    	notasentrega.setNotClId(rs.getString(NOTCLID));
    	notasentrega.setNotFecha(rs.getTimestamp(NOTFECHA));
    	notasentrega.setNotId(rs.getString(NOTID));
    	notasentrega.setNotNombre(rs.getString(NOTNOMBRE));
    	notasentrega.setNotObservaciones(rs.getString(NOTOBSERVACIONES));
    	notasentrega.setNotOrden(rs.getString(NOTORDEN));
    	notasentrega.setNotRutId(rs.getString(NOTRUTID));
    	notasentrega.setNotCierre(rs.getString(NOTCIERRE));
    	notasentrega.setNotViaje(rs.getString(NOTVIAJE));
    	notasentrega.setNotCuId(rs.getString(NOTCUID));
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[7];
    	clDatosRelacionados[0] = rs.getString("cl_nombre");
    	clDatosRelacionados[1] = rs.getString("rut_nombre");
    	clDatosRelacionados[2] = rs.getString("cl_direccion");
    	clDatosRelacionados[3] = rs.getString("loc_id");
    	clDatosRelacionados[4] = rs.getString("prov_id");
    	clDatosRelacionados[5] = rs.getString("cl_descuento");
    	//clDatosRelacionados[6] = rs.getString("cl_nombre_comercial");
    	notasentrega.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return notasentrega;
  }

  public static Object[] getNotasEntregaLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	NotasEntrega notasentrega = new NotasEntrega();
	String sql = "SELECT notas_entrega.*, clientes.cl_nombre_comercial as cl_nombre, rutas.rut_nombre, loc.loc_id, prov.prov_id, clientes.cl_descuento, clientes.cl_direccion " +
				 "FROM notas_entrega " +
				 "JOIN clientes on cl_id = not_cl_id " +
				 "JOIN rutas on rut_id = not_rut_id " +
				 " JOIN provincias prov ON prov.prov_id = clientes.cl_prov_id"+
				 " JOIN localidades loc ON loc.loc_id = clientes.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
				 " WHERE 1 = 1 ";
  	try{
		NotasEntrega notasentregaFiltro = new NotasEntrega();
		if(listaPaginada.getRequest().getSession().getAttribute("FNOTASENTREGA")!=null){
			notasentregaFiltro = (NotasEntrega)listaPaginada.getRequest().getSession().getAttribute("FNOTASENTREGA");
		}
    	if(!Utils.empty(notasentregaFiltro.getNotClId()))
    		sqlAñadido += "AND " + NOTCLID + " = '" + notasentregaFiltro.getNotClId() + "'";
    	if(notasentregaFiltro.getNotFecha()!=null)
    		sqlAñadido += "AND " + NOTFECHA + " = '" + notasentregaFiltro.getNotFecha() + "'";
    	if(!Utils.empty(notasentregaFiltro.getNotId()))
    		sqlAñadido += "AND " + NOTID + " = '" + notasentregaFiltro.getNotId() + "'";
    	if(!Utils.empty(notasentregaFiltro.getNotNombre()))
    		//sqlAñadido += "AND UPPER(" + NOTNOMBRE + ") like UPPER('%" + notasentregaFiltro.getNotNombre() + "%')";
    		sqlAñadido += "AND UPPER(" + NOTNOMBRE + ") = UPPER('" + notasentregaFiltro.getNotNombre() + "')";
    	if(!Utils.empty(notasentregaFiltro.getNotObservaciones()))
    		sqlAñadido += "AND UPPER(" + NOTOBSERVACIONES + ") like UPPER('%" + notasentregaFiltro.getNotObservaciones() + "%')";
    	if(!Utils.empty(notasentregaFiltro.getNotOrden()))
    		sqlAñadido += "AND " + NOTORDEN + " = '" + notasentregaFiltro.getNotOrden() + "'";
    	if(!Utils.empty(notasentregaFiltro.getNotRutId()))
    		sqlAñadido += "AND " + NOTRUTID + " = '" + notasentregaFiltro.getNotRutId() + "'";
    	if(!Utils.empty(notasentregaFiltro.getNotCierre())){
    		if(notasentregaFiltro.getNotCierre().equals("1"))
    			sqlAñadido += "AND " + NOTCIERRE + " = '" + notasentregaFiltro.getNotCierre() + "'";
    		else
    			sqlAñadido += "AND (" + NOTCIERRE + " = '" + notasentregaFiltro.getNotCierre() + "' " +
    						  " OR " + NOTCIERRE + " IS NULL) ";
    	}
  	}catch(Exception e){
  		System.out.println(e);
  	}
  	
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, notasentrega);
  }
  
  //****************** Vamos a poner aqui los metodos para la prevision de fabricacion ***********************
  public static ArrayList getAllPrevisionFabricacion(String fechaInicio, String fechaFin,
		  											 String agCliente,
		  											 String agRuta,
		  											 String idCliente,
		  											 String idRuta,
		  											 String agFamilia)
  {
	String groupBy = "GROUP BY notas_entrega_detalle.NOTDET_PRODUCTO";
	if(agCliente.equals("1")) groupBy += ",clientes.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) groupBy += ",rutas.RUT_NOMBRE";
	if(agFamilia.equals("1")) groupBy += ",familias.FAM_NOMBRE";
	
	String campos = "NOTDET_PRODUCTO, sum(NOTDET_CANTIDAD) ";
	if(agCliente.equals("1")) campos += ",clientes.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) campos += ",rutas.RUT_NOMBRE";
	if(agFamilia.equals("1")) campos += ",familias.FAM_NOMBRE";
	
	String order = "NOTDET_PRODUCTO";
	if(agCliente.equals("1")) order = "clientes.CL_NOMBRE_COMERCIAL," + order;
	if(agRuta.equals("1")) order = "rutas.RUT_NOMBRE," + order;
	if(agFamilia.equals("1")) order = "familias.FAM_NOMBRE," + order;
	
	String where = "";
	if(!idCliente.equals("")) where += "AND clientes.CL_ID = " + idCliente + " ";
	if(!idRuta.equals("")) where += "AND rutas.RUT_ID = " + idRuta + " ";
	
	campos += ", productos.prod_peso_masa";
	
    ArrayList lista = EntidadBean.consulta("SELECT " + campos +
    										" FROM notas_entrega " +
								    		"JOIN notas_entrega_detalle ON notas_entrega_detalle.NOTDET_NOT_ID = notas_entrega.NOT_ID " +
								    		"JOIN clientes ON notas_entrega.NOT_CL_ID = clientes.CL_ID " +
								    		"JOIN rutas ON notas_entrega.NOT_RUT_ID = rutas.RUT_ID " +
								    		"LEFT JOIN productos ON notas_entrega_detalle.notdet_producto = productos.prod_nombre " +
								    		"LEFT JOIN familias ON productos.prod_fam_id = familias.fam_id " +
								    		"WHERE NOT_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
								    		where + " " + 
								    		groupBy + " " + 
								    		" ORDER BY " + order);

    return lista;
  }
  
  //****************** Vamos a poner aqui los metodos para la prevision de fabricacion ***********************
  public static ArrayList getAllPrevisionFabricacionRutas(String fechaInicio, String fechaFin,
		  											 String agCliente,
		  											 String agRuta,
		  											 String idCliente,
		  											 String idsRuta,
		  											 String agFamilia)
  {
	String groupBy = "GROUP BY notas_entrega_detalle.NOTDET_PRODUCTO";
	if(agCliente.equals("1")) groupBy += ",clientes.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) groupBy += ",rutas.RUT_NOMBRE";
	if(agFamilia.equals("1")) groupBy += ",familias.FAM_NOMBRE";
	
	String campos = "NOTDET_PRODUCTO, sum(NOTDET_CANTIDAD) ";
	if(agCliente.equals("1")) campos += ",clientes.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) campos += ",rutas.RUT_NOMBRE";
	if(agFamilia.equals("1")) campos += ",familias.FAM_NOMBRE";
	
	String order = "NOTDET_PRODUCTO";
	if(agCliente.equals("1")) order = "clientes.CL_NOMBRE_COMERCIAL," + order;
	if(agRuta.equals("1")) order = "rutas.RUT_NOMBRE," + order;
	if(agFamilia.equals("1")) order = "familias.FAM_NOMBRE," + order;
	
	String where = "";
	if(!idCliente.equals("")) where += "AND clientes.CL_ID = " + idCliente + " ";
	if(!idsRuta.equals("")) where += "AND rutas.RUT_ID in ( " + idsRuta + ") ";
	
	campos += ", productos.prod_peso_masa";
	
    ArrayList lista = EntidadBean.consulta("SELECT " + campos +
    										" FROM notas_entrega " +
								    		"JOIN notas_entrega_detalle ON notas_entrega_detalle.NOTDET_NOT_ID = notas_entrega.NOT_ID " +
								    		"JOIN clientes ON notas_entrega.NOT_CL_ID = clientes.CL_ID " +
								    		"JOIN rutas ON notas_entrega.NOT_RUT_ID = rutas.RUT_ID " +
								    		"LEFT JOIN productos ON notas_entrega_detalle.notdet_producto = productos.prod_nombre " +
								    		"LEFT JOIN familias ON productos.prod_fam_id = familias.fam_id " +
								    		"WHERE NOT_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
								    		where + " " + 
								    		groupBy + " " + 
								    		" ORDER BY " + order);
    return lista;
  }
  
  
	  public static ArrayList getAllGraficaVentas( String fechaInicio, String fechaFin, String agrupacion, String pagada, String empresa){
		  
			String groupByFv = "GROUP BY ";
			String groupByAv = "GROUP BY ";
			String groupBy = "";
			String where = "";
			String whereIva = "";
			if(agrupacion.equals("0")){
				groupByAv  += "clientes.CL_NOMBRE_COMERCIAL";
				groupByFv  += "clientes.CL_NOMBRE_COMERCIAL";
				groupBy = " group by CL_NOMBRE_COMERCIAL";
			}else{
				groupByAv += "albaranes_venta_detalle.AVD_PRODUCTO";
				groupByFv += "facturas_venta_detalle.FVD_PRODUCTO";
				groupBy = " group by producto";
			}
			
			
			if(empresa.equals("1")){
				where = "and clientes.cl_id_empresa = '1' ";
				whereIva = "and clientes.cl_id_empresa = '999' ";
			}else if(empresa.equals("2")){
				where = "and clientes.cl_id_empresa = '2' ";
				whereIva = "and clientes.cl_id_empresa = '999' ";
			}
			String camposAv = "";
			String camposFv = "";
			String campos = "";
			if(agrupacion.equals("0")){ 
				camposAv += ",clientes.CL_NOMBRE_COMERCIAL,clientes.cl_id ";
				camposFv += ",clientes.CL_NOMBRE_COMERCIAL,clientes.cl_id ";
				campos = ",cl_nombre_comercial, cl_id ";
			}else{
				camposAv += ",albaranes_venta_detalle.AVD_PRODUCTO as producto,'' ";
				camposFv += ",facturas_venta_detalle.FVD_PRODUCTO as producto,'' ";
				campos = ",producto ";
			}
		
			//Para las facturas directas no es necesario hacer la proporcion de iva y sin iva
			//por cliente, ya que la factura es unica.
			//En cambio para los albaranes hay que hacer lo mismo que cuando se hace el paso a factura
			//es decir, coger la parte con Iva (si ha filtrado por una empresa) o ambas (si no ha filtrado por ninguna empresa)
			//debemos restar las facturas de abono
			ArrayList lista = EntidadBean.consulta("SELECT sum(importe)" + campos + " from (" +
													" SELECT SUM(FVD_IMPORTE) as importe" + camposFv +
	  												" FROM facturas_venta " +
										    		"JOIN facturas_venta_detalle ON facturas_venta_detalle.FVD_FV_ID = facturas_venta.FV_ID " +
										    		"JOIN clientes ON facturas_venta.FV_CL_ID = clientes.CL_ID " +
										    		"WHERE FV_FECHA BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " +
										    		where +
										    		"AND (facturas_venta.fv_tipo in ('1','2') OR clientes.cl_pf_id = 1) " + //tomamos solo las facturas directas, y las de abono y las diarias
										    		" and (facturas_venta.fv_invalida is null or facturas_venta.fv_invalida = 0) " +
										    		groupByFv + 
										    		" UNION " +
/*Aqui comienza la parte de los albaranes*/    		"select " +
/*Parte con IVA*/									"			 CASE WHEN abs(round(avd_cantidad* clientes.cl_proporcion_iva) - (avd_cantidad* clientes.cl_proporcion_iva))=  clientes.cl_proporcion_iva " + 
													"			 		  THEN sum(FLOOR(avd_cantidad* clientes.cl_proporcion_iva)*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) " +
													"						ELSE sum(round(avd_cantidad* clientes.cl_proporcion_iva)*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * avd_iva))) END as importe "  +
													camposAv +
													"from albaranes_venta  " +
													"join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id " +  
													"join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
													"join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " + 
													"where av_fecha BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " + 
													"and clientes.cl_pf_id != 1 " +
													where +
													groupByAv +
													"	union " +
/*Parte sin IVA*/									"select " +
													"			 CASE WHEN abs(round(avd_cantidad* (1 - clientes.cl_proporcion_iva) ) - (avd_cantidad* (1 - clientes.cl_proporcion_iva) ))= (1 - clientes.cl_proporcion_iva) " + 
													"			 			THEN sum(CEILING(avd_cantidad* (1 - clientes.cl_proporcion_iva) )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) " +
													"						ELSE sum(round(avd_cantidad* (1 - clientes.cl_proporcion_iva) )*((avd_precio_venta - (avd_precio_venta * avd_descuento)) + (avd_precio_venta * 0))) END as importe " +   
													camposAv +
													"from albaranes_venta  " +
													"join albaranes_venta_detalle ON albaranes_venta_detalle.avd_av_id = albaranes_venta.av_id " +  
													"join clientes ON clientes.cl_id = albaranes_venta.av_cl_id " +
													"join periodos_facturacion ON periodos_facturacion.pf_id = clientes.cl_pf_id " + 
													"where av_fecha BETWEEN DATE_FORMAT('" + fechaInicio + "','%Y/%m/%d') AND DATE_FORMAT('" + fechaFin + "','%Y/%m/%d') " + 
													"and clientes.cl_pf_id != 1 " +
													where +
													groupByAv +
										    		") as tabla " + groupBy + " ORDER BY 2 ASC");
			return lista;
	  }
}
