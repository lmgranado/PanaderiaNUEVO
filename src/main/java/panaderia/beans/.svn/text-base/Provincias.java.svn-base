package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Provincias extends panaderia.beans.entidad.ProvinciasEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Provincias(){ super(); }

  public static Provincias getProvinciasByProvCodigo(String provCodigo)
  {
    Provincias elto = new Provincias();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM provincias WHERE prov_codigo='"+provCodigo+"'");
    return elto;
  }

  public static Provincias getProvinciasByProvId(String provId)
  {
    Provincias elto = new Provincias();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM provincias WHERE prov_id='"+provId+"'");
    return elto;
  }

  public static ArrayList getAllProvincias()
  {
    Provincias elto = new Provincias();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM provincias order by prov_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Provincias provincias = new Provincias();
  	try{
    	provincias.setProvCodigo(rs.getString(PROVCODIGO));
    	provincias.setProvId(rs.getString(PROVID));
    	provincias.setProvNombre(rs.getString(PROVNOMBRE));
  	}catch(Exception e){
  		throw e;
  	}
    	return provincias;
  }

  public static Object[] getProvinciasLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Provincias provincias = new Provincias();
	String sql = "SELECT * FROM provincias " +
				 " WHERE 1 = 1 ";
  	try{
		Provincias provinciasFiltro = new Provincias();
		if(listaPaginada.getRequest().getSession().getAttribute("FPROVINCIAS")!=null){
			provinciasFiltro = (Provincias)listaPaginada.getRequest().getSession().getAttribute("FPROVINCIAS");
		}
    	if(!Utils.empty(provinciasFiltro.getProvCodigo()))
    		sqlAñadido += "AND " + PROVCODIGO + " = '" + provinciasFiltro.getProvCodigo() + "'";
    	if(!Utils.empty(provinciasFiltro.getProvId()))
    		sqlAñadido += "AND " + PROVID + " = '" + provinciasFiltro.getProvId() + "'";
    	if(!Utils.empty(provinciasFiltro.getProvNombre()))
    		sqlAñadido += "AND " + PROVNOMBRE + " = '" + provinciasFiltro.getProvNombre() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, provincias);
  }
}
