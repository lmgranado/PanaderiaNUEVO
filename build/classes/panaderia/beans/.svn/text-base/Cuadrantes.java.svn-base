package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Cuadrantes extends panaderia.beans.entidad.CuadrantesEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Cuadrantes(){ super(); }

  public static Cuadrantes getCuadrantesByCuClId(String cuClId)
  {
    Cuadrantes elto = new Cuadrantes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes WHERE cu_cl_id='"+cuClId+"'");
    return elto;
  }

  public static Cuadrantes getCuadrantesByCuId(String cuId)
  {
    Cuadrantes elto = new Cuadrantes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes WHERE cu_id='"+cuId+"'");
    return elto;
  }
  
  public static ArrayList getCuadrantesByClId(String clId)
  {
    Cuadrantes elto = new Cuadrantes();
    ArrayList lista = elto.consultaAVectorReflexiva("select cuadrantes.* " +
										    		  "from clientes " +
										    		  "join cuadrantes on cuadrantes.cu_cl_id = clientes.cl_id " +
										    		  "where clientes.cl_id = " + clId);
    return lista;
  }

  public static ArrayList getAllCuadrantes()
  {
    Cuadrantes elto = new Cuadrantes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM cuadrantes order by cu_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Cuadrantes cuadrantes = new Cuadrantes();
  	try{
    	cuadrantes.setCuClId(rs.getString(CUCLID));
    	cuadrantes.setCuId(rs.getString(CUID));
    	cuadrantes.setCuNombre(rs.getString(CUNOMBRE));
    	cuadrantes.setCuObservaciones(rs.getString(CUOBSERVACIONES));
  	}catch(Exception e){
  		throw e;
  	}
    	return cuadrantes;
  }

  public static Object[] getCuadrantesLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Cuadrantes cuadrantes = new Cuadrantes();
	String sql = "SELECT cuadrantes.* " +
				 "FROM cuadrantes " +
				 "WHERE 1 = 1 ";
  	try{
		Cuadrantes cuadrantesFiltro = new Cuadrantes();
		if(listaPaginada.getRequest().getSession().getAttribute("FCUADRANTES")!=null){
			cuadrantesFiltro = (Cuadrantes)listaPaginada.getRequest().getSession().getAttribute("FCUADRANTES");
		}
    	if(!Utils.empty(cuadrantesFiltro.getCuClId()))
    		sqlAñadido += "AND " + CUCLID + " = '" + cuadrantesFiltro.getCuClId() + "'";
    	if(!Utils.empty(cuadrantesFiltro.getCuId()))
    		sqlAñadido += "AND " + CUID + " = '" + cuadrantesFiltro.getCuId() + "'";
    	if(!Utils.empty(cuadrantesFiltro.getCuNombre()))
    		sqlAñadido += "AND " + CUNOMBRE + " = '" + cuadrantesFiltro.getCuNombre() + "'";
    	if(!Utils.empty(cuadrantesFiltro.getCuObservaciones()))
    		sqlAñadido += "AND " + CUOBSERVACIONES + " = '" + cuadrantesFiltro.getCuObservaciones() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, cuadrantes);
  }
}
