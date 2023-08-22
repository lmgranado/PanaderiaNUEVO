package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Repartidores extends panaderia.beans.entidad.RepartidoresEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Repartidores(){ super(); }

  public static Repartidores getRepartidoresByRepId(String repId)
  {
    Repartidores elto = new Repartidores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM repartidores WHERE rep_id='"+repId+"'");
    return elto;
  }

  public static Repartidores getRepartidoresByRepLocId(String repLocId)
  {
    Repartidores elto = new Repartidores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM repartidores WHERE rep_loc_id='"+repLocId+"'");
    return elto;
  }

  public static Repartidores getRepartidoresByRepNif(String repNif)
  {
    Repartidores elto = new Repartidores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM repartidores WHERE rep_nif='"+repNif+"'");
    return elto;
  }

  public static Repartidores getRepartidoresByRepProvId(String repProvId)
  {
    Repartidores elto = new Repartidores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM repartidores WHERE rep_prov_id='"+repProvId+"'");
    return elto;
  }

  public static ArrayList getAllRepartidores()
  {
    Repartidores elto = new Repartidores();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM repartidores order by concat(rep_nombre, rep_apellidos)");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Repartidores repartidores = new Repartidores();
  	try{
    	repartidores.setRepApellidos(rs.getString(REPAPELLIDOS));
    	repartidores.setRepCodigoPostal(rs.getString(REPCODIGOPOSTAL));
    	repartidores.setRepDireccion(rs.getString(REPDIRECCION));
    	repartidores.setRepId(rs.getString(REPID));
    	repartidores.setRepLocId(rs.getString(REPLOCID));
    	repartidores.setRepMovil(rs.getString(REPMOVIL));
    	repartidores.setRepNif(rs.getString(REPNIF));
    	repartidores.setRepNombre(rs.getString(REPNOMBRE));
    	repartidores.setRepProvId(rs.getString(REPPROVID));
    	repartidores.setRepTelefono(rs.getString(REPTELEFONO));
  	}catch(Exception e){
  		throw e;
  	}
    	return repartidores;
  }

  public static Object[] getRepartidoresLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Repartidores repartidores = new Repartidores();
	String sql = "SELECT * FROM repartidores " +
				 " WHERE 1 = 1 ";
  	try{
		Repartidores repartidoresFiltro = new Repartidores();
		if(listaPaginada.getRequest().getSession().getAttribute("FREPARTIDORES")!=null){
			repartidoresFiltro = (Repartidores)listaPaginada.getRequest().getSession().getAttribute("FREPARTIDORES");
		}
    	if(!Utils.empty(repartidoresFiltro.getRepApellidos()))
    		sqlAñadido += "AND UPPER(" + REPAPELLIDOS + ") like UPPER('%" + repartidoresFiltro.getRepApellidos() + "%')";
    	if(!Utils.empty(repartidoresFiltro.getRepCodigoPostal()))
    		sqlAñadido += "AND " + REPCODIGOPOSTAL + " = '" + repartidoresFiltro.getRepCodigoPostal() + "'";
    	if(!Utils.empty(repartidoresFiltro.getRepDireccion()))
    		sqlAñadido += "AND UPPER(" + REPDIRECCION + ") like UPPER('%" + repartidoresFiltro.getRepDireccion() + "%')";
    	if(!Utils.empty(repartidoresFiltro.getRepId()))
    		sqlAñadido += "AND " + REPID + " = '" + repartidoresFiltro.getRepId() + "'";
    	if(!Utils.empty(repartidoresFiltro.getRepLocId()))
    		sqlAñadido += "AND " + REPLOCID + " = '" + repartidoresFiltro.getRepLocId() + "'";
    	if(!Utils.empty(repartidoresFiltro.getRepMovil()))
    		sqlAñadido += "AND " + REPMOVIL + " = '" + repartidoresFiltro.getRepMovil() + "'";
    	if(!Utils.empty(repartidoresFiltro.getRepNif()))
    		sqlAñadido += "AND UPPER(" + REPNIF + ") like UPPER('%" + repartidoresFiltro.getRepNif() + "%')";
    	if(!Utils.empty(repartidoresFiltro.getRepNombre()))
    		sqlAñadido += "AND UPPER(" + REPNOMBRE + ") like UPPER('%" + repartidoresFiltro.getRepNombre() + "%')";
    	if(!Utils.empty(repartidoresFiltro.getRepProvId()))
    		sqlAñadido += "AND " + REPPROVID + " = '" + repartidoresFiltro.getRepProvId() + "'";
    	if(!Utils.empty(repartidoresFiltro.getRepTelefono()))
    		sqlAñadido += "AND " + REPTELEFONO + " = '" + repartidoresFiltro.getRepTelefono() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, repartidores);
  }
}
