package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Cobradores extends panaderia.beans.entidad.CobradoresEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Cobradores(){ super(); }

  public static Cobradores getCobradoresByCobId(String cobId)
  {
    Cobradores elto = new Cobradores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cobradores WHERE cob_id='"+cobId+"'");
    return elto;
  }

  public static Cobradores getCobradoresByCobLocId(String cobLocId)
  {
    Cobradores elto = new Cobradores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cobradores WHERE cob_loc_id='"+cobLocId+"'");
    return elto;
  }

  public static Cobradores getCobradoresByCobNif(String cobNif)
  {
    Cobradores elto = new Cobradores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cobradores WHERE cob_nif='"+cobNif+"'");
    return elto;
  }

  public static Cobradores getCobradoresByCobProvId(String cobProvId)
  {
    Cobradores elto = new Cobradores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM cobradores WHERE cob_prov_id='"+cobProvId+"'");
    return elto;
  }

  public static ArrayList getAllCobradores()
  {
    Cobradores elto = new Cobradores();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM cobradores order by concat(cob_nombre, cob_apellidos)");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Cobradores cobradores = new Cobradores();
  	try{
    	cobradores.setCobApellidos(rs.getString(COBAPELLIDOS));
    	cobradores.setCobCodigoPostal(rs.getString(COBCODIGOPOSTAL));
    	cobradores.setCobDireccion(rs.getString(COBDIRECCION));
    	cobradores.setCobId(rs.getString(COBID));
    	cobradores.setCobLocId(rs.getString(COBLOCID));
    	cobradores.setCobMovil(rs.getString(COBMOVIL));
    	cobradores.setCobNif(rs.getString(COBNIF));
    	cobradores.setCobNombre(rs.getString(COBNOMBRE));
    	cobradores.setCobProvId(rs.getString(COBPROVID));
    	cobradores.setCobTelefono(rs.getString(COBTELEFONO));
  	}catch(Exception e){
  		throw e;
  	}
    	return cobradores;
  }

  public static Object[] getCobradoresLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Cobradores cobradores = new Cobradores();
	String sql = "SELECT * FROM cobradores " +
				 " WHERE 1 = 1 ";
  	try{
		Cobradores cobradoresFiltro = new Cobradores();
		if(listaPaginada.getRequest().getSession().getAttribute("FCOBRADORES")!=null){
			cobradoresFiltro = (Cobradores)listaPaginada.getRequest().getSession().getAttribute("FCOBRADORES");
		}
    	if(!Utils.empty(cobradoresFiltro.getCobApellidos()))
    		sqlAñadido += "AND UPPER(" + COBAPELLIDOS + ") like UPPER('%" + cobradoresFiltro.getCobApellidos() + "%')";
    	if(!Utils.empty(cobradoresFiltro.getCobCodigoPostal()))
    		sqlAñadido += "AND " + COBCODIGOPOSTAL + " like '%" + cobradoresFiltro.getCobCodigoPostal() + "%'";
    	if(!Utils.empty(cobradoresFiltro.getCobDireccion()))
    		sqlAñadido += "AND UPPER(" + COBDIRECCION + ") like UPPER('%" + cobradoresFiltro.getCobDireccion() + "%')";
    	if(!Utils.empty(cobradoresFiltro.getCobId()))
    		sqlAñadido += "AND " + COBID + " = '" + cobradoresFiltro.getCobId() + "'";
    	if(!Utils.empty(cobradoresFiltro.getCobLocId()))
    		sqlAñadido += "AND " + COBLOCID + " = '" + cobradoresFiltro.getCobLocId() + "'";
    	if(!Utils.empty(cobradoresFiltro.getCobMovil()))
    		sqlAñadido += "AND " + COBMOVIL + " = '" + cobradoresFiltro.getCobMovil() + "'";
    	if(!Utils.empty(cobradoresFiltro.getCobNif()))
    		sqlAñadido += "AND UPPER(" + COBNIF + ") like UPPER('%" + cobradoresFiltro.getCobNif() + "%')";
    	if(!Utils.empty(cobradoresFiltro.getCobNombre()))
    		sqlAñadido += "AND UPPER(" + COBNOMBRE + ") like UPPER('%" + cobradoresFiltro.getCobNombre() + "%')";
    	if(!Utils.empty(cobradoresFiltro.getCobProvId()))
    		sqlAñadido += "AND " + COBPROVID + " = '" + cobradoresFiltro.getCobProvId() + "'";
    	if(!Utils.empty(cobradoresFiltro.getCobTelefono()))
    		sqlAñadido += "AND " + COBTELEFONO + " like '%" + cobradoresFiltro.getCobTelefono() + "%'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, cobradores);
  }
}
