package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class FormasPago extends panaderia.beans.entidad.FormasPagoEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FormasPago(){ super(); }

  public static FormasPago getFormasPagoByFpId(String fpId)
  {
    FormasPago elto = new FormasPago();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM formas_pago WHERE fp_id='"+fpId+"'");
    return elto;
  }

  public static ArrayList getAllFormasPago()
  {
    FormasPago elto = new FormasPago();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM formas_pago order by fp_descripcion");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FormasPago formaspago = new FormasPago();
  	try{
    	formaspago.setFpDescripcion(rs.getString(FPDESCRIPCION));
    	formaspago.setFpId(rs.getString(FPID));
  	}catch(Exception e){
  		throw e;
  	}
    	return formaspago;
  }

  public static Object[] getFormasPagoLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FormasPago formaspago = new FormasPago();
	String sql = "SELECT * FROM formas_pago " +
				 " WHERE 1 = 1 ";
  	try{
		FormasPago formaspagoFiltro = new FormasPago();
		if(listaPaginada.getRequest().getSession().getAttribute("FFORMASPAGO")!=null){
			formaspagoFiltro = (FormasPago)listaPaginada.getRequest().getSession().getAttribute("FFORMASPAGO");
		}
    	if(!Utils.empty(formaspagoFiltro.getFpDescripcion()))
    		sqlAñadido += "AND UPPER(" + FPDESCRIPCION + ") like UPPER('%" + formaspagoFiltro.getFpDescripcion() + "%')";
    	if(!Utils.empty(formaspagoFiltro.getFpId()))
    		sqlAñadido += "AND " + FPID + " = '" + formaspagoFiltro.getFpId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, formaspago);
  }
}
