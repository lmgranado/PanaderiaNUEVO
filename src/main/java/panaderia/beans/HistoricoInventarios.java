package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class HistoricoInventarios extends panaderia.beans.entidad.HistoricoInventariosEntidad
{
  private static final long serialVersionUID = 1L;
  
  public HistoricoInventarios(){ super(); }

  public static HistoricoInventarios getHistoricoInventariosByHinvId(String hinvId)
  {
    HistoricoInventarios elto = new HistoricoInventarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios WHERE hinv_id='"+hinvId+"'");
    return elto;
  }

  public static HistoricoInventarios getHistoricoInventariosByHinvUsId(String hinvUsId)
  {
    HistoricoInventarios elto = new HistoricoInventarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios WHERE hinv_us_id='"+hinvUsId+"'");
    return elto;
  }

  public static ArrayList getAllHistoricoInventarios()
  {
    HistoricoInventarios elto = new HistoricoInventarios();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM historico_inventarios");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    HistoricoInventarios historicoinventarios = new HistoricoInventarios();
  	try{
    	historicoinventarios.setHinvFecha(rs.getTimestamp(HINVFECHA));
    	historicoinventarios.setHinvId(rs.getString(HINVID));
    	historicoinventarios.setHinvUsId(rs.getString(HINVUSID));
    	
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[1];
    	clDatosRelacionados[0] = rs.getString("us_nombre") + " (" + rs.getString("us_login") + ")";
    	historicoinventarios.setHinvDatosRelacionados( clDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return historicoinventarios;
  }

  public static Object[] getHistoricoInventariosLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	HistoricoInventarios historicoinventarios = new HistoricoInventarios();
	String sql = "SELECT * FROM historico_inventarios hinv" +
				 " JOIN usuarios us ON hinv.hinv_us_id = us.us_id "+
				 " JOIN historico_inventarios_detalle hinvd on hinvd.hinvd_hinv_id = hinv.hinv_id " +
				 " WHERE 1 = 1 ";
  	try{
		HistoricoInventarios historicoinventariosFiltro = new HistoricoInventarios();
		if(listaPaginada.getRequest().getSession().getAttribute("FHISTORICO_INVENTARIOS")!=null){
			historicoinventariosFiltro = (HistoricoInventarios)listaPaginada.getRequest().getSession().getAttribute("FHISTORICO_INVENTARIOS");
		}
    	if( historicoinventariosFiltro.getHinvFecha() != null )
    		sqlAñadido += "AND " + HINVFECHA + " = '" + historicoinventariosFiltro.getHinvFecha() + "'";
    	if(!Utils.empty(historicoinventariosFiltro.getHinvId()))
    		sqlAñadido += "AND " + HINVID + " = '" + historicoinventariosFiltro.getHinvId() + "'";
    	if(!Utils.empty(historicoinventariosFiltro.getHinvUsId()))
    		sqlAñadido += "AND " + HINVUSID + " = '" + historicoinventariosFiltro.getHinvUsId() + "'";
    	
    	//Introducimos busqueda por rango de fechas
    	if( historicoinventariosFiltro.getHinvFechaDesde() !=null && historicoinventariosFiltro.getHinvFechaHasta()!=null )
    		sqlAñadido += "AND " +HINVFECHA+" BETWEEN '"+historicoinventariosFiltro.getHinvFechaDesde()+"' AND '"+historicoinventariosFiltro.getHinvFechaHasta()+"' ";
    	else if( historicoinventariosFiltro.getHinvFechaDesde()!=null ) 
    		sqlAñadido += "AND " + HINVFECHA + " >= '" + historicoinventariosFiltro.getHinvFechaDesde() + "'";
    	else if( historicoinventariosFiltro.getHinvFechaHasta()!=null )
    		sqlAñadido += "AND " + HINVFECHA + " <= '" + historicoinventariosFiltro.getHinvFechaHasta() + "'";
    	
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, historicoinventarios);
  }
}
