package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.Localidades;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Localidades extends panaderia.beans.entidad.LocalidadesEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Localidades(){ super(); }

  public static Localidades getLocalidadesByLocId(String locId)
  {
    Localidades elto = new Localidades();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM localidades WHERE loc_id='"+locId+"'");
    return elto;
  }

  public static ArrayList getLocalidadesByProvIdCodigo(String provId)
  {
    Localidades elto = new Localidades();
    ArrayList listado = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM localidades " +
    												  " JOIN provincias prov ON prov.prov_codigo = localidades.loc_prov_codigo " +
    												  " WHERE prov.prov_id = '"+provId+"' order by loc_nombre");
    return listado;
  }
  

  public static ArrayList getLocalidadesByProv(String locProvCodigo)
  {
    Localidades elto = new Localidades();
    ArrayList listado = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM localidades WHERE loc_prov_codigo='"+locProvCodigo+"' order by loc_nombre");
    return listado;
  }
  
  public static Localidades getLocalidadesByLocProvCodigo(String locProvCodigo)
  {
    Localidades elto = new Localidades();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM localidades WHERE loc_prov_codigo='"+locProvCodigo+"'");
    return elto;
  }

  public static ArrayList getAllLocalidades()
  {
    Localidades elto = new Localidades();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM localidades order by loc_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Localidades localidades = new Localidades();
  	try{
    	localidades.setLocCodigo(rs.getString(LOCCODIGO));
    	localidades.setLocId(rs.getString(LOCID));
    	localidades.setLocNombre(rs.getString(LOCNOMBRE));
    	localidades.setLocProvCodigo(rs.getString(LOCPROVCODIGO));
  	}catch(Exception e){
  		throw e;
  	}
    	return localidades;
  }

  public static Object[] getLocalidadesLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Localidades localidades = new Localidades();
	String sql = "SELECT * FROM localidades " +
				 " WHERE 1 = 1 ";
  	try{
		Localidades localidadesFiltro = new Localidades();
		if(listaPaginada.getRequest().getSession().getAttribute("FLOCALIDADES")!=null){
			localidadesFiltro = (Localidades)listaPaginada.getRequest().getSession().getAttribute("FLOCALIDADES");
		}
    	if(!Utils.empty(localidadesFiltro.getLocCodigo()))
    		sqlAñadido += "AND " + LOCCODIGO + " = '" + localidadesFiltro.getLocCodigo() + "'";
    	if(!Utils.empty(localidadesFiltro.getLocId()))
    		sqlAñadido += "AND " + LOCID + " = '" + localidadesFiltro.getLocId() + "'";
    	if(!Utils.empty(localidadesFiltro.getLocNombre()))
    		sqlAñadido += "AND " + LOCNOMBRE + " = '" + localidadesFiltro.getLocNombre() + "'";
    	if(!Utils.empty(localidadesFiltro.getLocProvCodigo()))
    		sqlAñadido += "AND " + LOCPROVCODIGO + " = '" + localidadesFiltro.getLocProvCodigo() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, localidades);
  }
}
