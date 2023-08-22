package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Rutas extends panaderia.beans.entidad.RutasEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Rutas(){ super(); }

  public static Rutas getRutasByRutId(String rutId)
  {
    Rutas elto = new Rutas();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM rutas WHERE rut_id='"+rutId+"'");
    return elto;
  }

  public static Rutas getRutasByRutNombre(String rutNombre)
  {
    Rutas elto = new Rutas();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM rutas WHERE rut_nombre='"+rutNombre+"'");
    return elto;
  }

  public static ArrayList getAllRutas()
  {
    Rutas elto = new Rutas();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM rutas order by rut_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Rutas rutas = new Rutas();
  	try{
    	rutas.setRutId(rs.getString(RUTID));
    	rutas.setRutNombre(rs.getString(RUTNOMBRE));
  	}catch(Exception e){
  		throw e;
  	}
    	return rutas;
  }

  public static Object[] getRutasLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Rutas rutas = new Rutas();
	String sql = "SELECT * FROM rutas " +
				 " WHERE 1 = 1 ";
  	try{
		Rutas rutasFiltro = new Rutas();
		if(listaPaginada.getRequest().getSession().getAttribute("FRUTAS")!=null){
			rutasFiltro = (Rutas)listaPaginada.getRequest().getSession().getAttribute("FRUTAS");
		}
    	if(!Utils.empty(rutasFiltro.getRutId()))
    		sqlAñadido += "AND " + RUTID + " = '" + rutasFiltro.getRutId() + "'";
    	if(!Utils.empty(rutasFiltro.getRutNombre()))
    		sqlAñadido += "AND UPPER(" + RUTNOMBRE + ") like UPPER('%" + rutasFiltro.getRutNombre() + "%')";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, rutas);
  }
}
