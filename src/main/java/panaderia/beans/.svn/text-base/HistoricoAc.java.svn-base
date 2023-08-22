package panaderia.beans;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;
public class HistoricoAc extends panaderia.beans.entidad.HistoricoAcEntidad
{
  private static final long serialVersionUID = 1L;
  
  public HistoricoAc(){ super(); }

  public static ArrayList getHistoricoAcByHacClId(String hacClId)
  {
    HistoricoAc elto = new HistoricoAc();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_ac WHERE hac_cl_id='"+hacClId+"' ORDER BY hac_fecha_entrega asc");
    return lista;
  }

  public static HistoricoAc getHistoricoAcByHacId(String hacId)
  {
    HistoricoAc elto = new HistoricoAc();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_ac WHERE hac_id='"+hacId+"'");
    return elto;
  }

  public static ArrayList getAllHistoricoAc()
  {
    HistoricoAc elto = new HistoricoAc();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_ac ORDER BY hac_fecha_entrega asc");
    return lista;
  }

  /** Método que resta la cantidad a cuenta que ha dado el cliente, de sus 
   *  facturas pendientes empezando de mas antigua a mas actual */
  public static void restaCantidadAFacturasCliente( HistoricoAc historicoac )
  {
	  float hacCantidad = Float.parseFloat( EntidadBean.quitar_comas(historicoac.getHacCantidad()));
	  String hacClId = historicoac.getHacClId();
	  ArrayList facturas = FacturasVenta.getFacturasVentaNoPagadasByFvClId( hacClId );
	  Iterator it = facturas.iterator();
	  //Recorremos las facturas y vamos restando la cantidad a cuenta del Cliente
	  while( it.hasNext() && hacCantidad > 0 ){
		  FacturasVenta factura = (FacturasVenta) it.next();
		  float importePendiente = Float.parseFloat(factura.getFvImportePendiente());
		  if( hacCantidad >= importePendiente  ){
			  factura.setFvImportePendiente( "0" );
			  factura.setFvPagada( "1" );
			  factura.setFvFechaPago(Utils.getFechaActualSinHoraString(), Utils.DATE_SHORT);
			  factura.actualiza();
			  hacCantidad = hacCantidad - importePendiente;
			  
		  }else{
			  importePendiente = importePendiente - hacCantidad;
			  factura.setFvImportePendiente( String.valueOf(importePendiente) );
			  hacCantidad = 0;
		  }
		  factura.actualiza();
	  }
  }
  
  public static ArrayList getHistoricoAcByCliente( String fechaDesde, String fechaHasta, String hacClId ){
	  HistoricoAc elto = new HistoricoAc();
	  
	  String where = "WHERE "+HACCLID+"="+hacClId;
  	  if( !"".equals(fechaDesde) && !"".equals(fechaHasta) )
  		 where += " AND " +HACFECHAENTREGA+" BETWEEN DATE_FORMAT('"+fechaDesde+"','%Y/%m/%d') AND DATE_FORMAT('"+fechaHasta+"','%Y/%m/%d') ";
  	  else if( !"".equals(fechaDesde) ) 
  		 where += " AND " + HACFECHAENTREGA + " >= DATE_FORMAT('"+fechaDesde+"','%Y/%m/%d') ";
  	  else if( !"".equals(fechaHasta) )
  		 where += " AND " + HACFECHAENTREGA + " <= DATE_FORMAT('"+fechaHasta+"','%Y/%m/%d') ";
  	
  	ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_ac "+where+" ORDER BY hac_fecha_entrega desc");
  	
    return lista;
}
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    HistoricoAc historicoac = new HistoricoAc();
  	try{
    	historicoac.setHacCantidad(rs.getString(HACCANTIDAD));
    	historicoac.setHacClId(rs.getString(HACCLID));
    	historicoac.setHacFechaEntrega(rs.getTimestamp(HACFECHAENTREGA));
    	historicoac.setHacId(rs.getString(HACID));
  	}catch(Exception e){
  		throw e;
  	}
    	return historicoac;
  }

  public static Object[] getHistoricoAcLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	HistoricoAc historicoac = new HistoricoAc();
	String sql = "SELECT * FROM historico_ac " +
				 " WHERE 1 = 1 ";
  	try{
		HistoricoAc historicoacFiltro = new HistoricoAc();
		if(listaPaginada.getRequest().getSession().getAttribute("FHISTORICOAC")!=null){
			historicoacFiltro = (HistoricoAc)listaPaginada.getRequest().getSession().getAttribute("FHISTORICOAC");
		}
    	if(!Utils.empty(historicoacFiltro.getHacCantidad()))
    		sqlAñadido += "AND " + HACCANTIDAD + " = '" + historicoacFiltro.getHacCantidad() + "'";
    	if(!Utils.empty(historicoacFiltro.getHacClId()))
    		sqlAñadido += "AND " + HACCLID + " = '" + historicoacFiltro.getHacClId() + "'";
    	if(historicoacFiltro.getHacFechaEntrega()!=null)
    		sqlAñadido += "AND " + HACFECHAENTREGA + " = '" + historicoacFiltro.getHacFechaEntrega() + "'";
    	if(!Utils.empty(historicoacFiltro.getHacId()))
    		sqlAñadido += "AND " + HACID + " = '" + historicoacFiltro.getHacId() + "'";
    	
//    	Introducimos busqueda por rango de fechas
    	if( historicoacFiltro.getHacFechaDesde()!=null && historicoacFiltro.getHacFechaDesde()!=null )
    		sqlAñadido += "AND " +HACFECHAENTREGA+" BETWEEN '"+historicoacFiltro.getHacFechaDesde()+"' AND '"+historicoacFiltro.getHacFechaDesde()+"' ";
    	else if( historicoacFiltro.getHacFechaDesde()!=null ) 
    		sqlAñadido += "AND " + HACFECHAENTREGA + " >= '" + historicoacFiltro.getHacFechaDesde() + "'";
    	else if( historicoacFiltro.getHacFechaHasta()!=null )
    		sqlAñadido += "AND " + HACFECHAENTREGA + " <= '" + historicoacFiltro.getHacFechaHasta() + "'";
    	
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, historicoac);
  }
}
