package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class PeriodosFacturacion extends panaderia.beans.entidad.PeriodosFacturacionEntidad
{
  private static final long serialVersionUID = 1L;
  
  public PeriodosFacturacion(){ super(); }

  public static PeriodosFacturacion getPeriodosFacturacionByPfId(String pfId)
  {
    PeriodosFacturacion elto = new PeriodosFacturacion();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM periodos_facturacion WHERE pf_id='"+pfId+"'");
    return elto;
  }

  public static ArrayList getAllPeriodosFacturacion()
  {
    PeriodosFacturacion elto = new PeriodosFacturacion();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM periodos_facturacion order by pf_descripcion");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    PeriodosFacturacion periodosfacturacion = new PeriodosFacturacion();
  	try{
    	periodosfacturacion.setPfDescripcion(rs.getString(PFDESCRIPCION));
    	periodosfacturacion.setPfDia(rs.getString(PFDIA));
    	periodosfacturacion.setPfId(rs.getString(PFID));
  	}catch(Exception e){
  		throw e;
  	}
    	return periodosfacturacion;
  }

  public static Object[] getPeriodosFacturacionLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	PeriodosFacturacion periodosfacturacion = new PeriodosFacturacion();
	String sql = "SELECT * FROM periodos_facturacion " +
				 " WHERE 1 = 1 ";
  	try{
		PeriodosFacturacion periodosfacturacionFiltro = new PeriodosFacturacion();
		if(listaPaginada.getRequest().getSession().getAttribute("FPERIODOSFACTURACION")!=null){
			periodosfacturacionFiltro = (PeriodosFacturacion)listaPaginada.getRequest().getSession().getAttribute("FPERIODOSFACTURACION");
		}
    	if(!Utils.empty(periodosfacturacionFiltro.getPfDescripcion()))
    		sqlAñadido += "AND UPPER(" + PFDESCRIPCION + ") like UPPER('%" + periodosfacturacionFiltro.getPfDescripcion() + "%')";
    	if(!Utils.empty(periodosfacturacionFiltro.getPfDia()))
    		sqlAñadido += "AND " + PFDIA + " = '" + periodosfacturacionFiltro.getPfDia() + "'";
    	if(!Utils.empty(periodosfacturacionFiltro.getPfId()))
    		sqlAñadido += "AND " + PFID + " = '" + periodosfacturacionFiltro.getPfId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, periodosfacturacion);
  }
}
