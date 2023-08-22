package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Familias extends panaderia.beans.entidad.FamiliasEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Familias(){ super(); }

  public static Familias getFamiliasByFamId(String famId)
  {
    Familias elto = new Familias();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM familias WHERE fam_id='"+famId+"'");
    return elto;
  }

  public static ArrayList getAllFamilias()
  {
    Familias elto = new Familias();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM familias order by fam_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Familias familias = new Familias();
  	try{
    	familias.setFamId(rs.getString(FAMID));
    	familias.setFamNombre(rs.getString(FAMNOMBRE));
  	}catch(Exception e){
  		throw e;
  	}
    	return familias;
  }

  public static Object[] getFamiliasLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Familias familias = new Familias();
	String sql = "SELECT * FROM familias " +
				 " WHERE 1 = 1 ";
  	try{
		Familias familiasFiltro = new Familias();
		if(listaPaginada.getRequest().getSession().getAttribute("FFAMILIAS")!=null){
			familiasFiltro = (Familias)listaPaginada.getRequest().getSession().getAttribute("FFAMILIAS");
		}
    	if(!Utils.empty(familiasFiltro.getFamId()))
    		sqlAñadido += "AND " + FAMID + " = '" + familiasFiltro.getFamId() + "'";
    	if(!Utils.empty(familiasFiltro.getFamNombre()))
    		sqlAñadido += "AND UPPER(" + FAMNOMBRE + ") like UPPER('%" + familiasFiltro.getFamNombre() + "%')";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, familias);
  }
}
