package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
public class VacacionesClientes extends panaderia.beans.entidad.VacacionesClientesEntidad
{
  private static final long serialVersionUID = 1L;
  
  public VacacionesClientes(){ super(); }

  public static VacacionesClientes getVacacionesClientesByVacClId(String vacClId)
  {
    VacacionesClientes elto = new VacacionesClientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM vacaciones_clientes WHERE vac_cl_id='"+vacClId+"'");
    return elto;
  }

  public static VacacionesClientes getVacacionesClientesByVacId(String vacId)
  {
    VacacionesClientes elto = new VacacionesClientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM vacaciones_clientes WHERE vac_id='"+vacId+"'");
    return elto;
  }

  public static ArrayList getAllVacacionesClientes()
  {
    VacacionesClientes elto = new VacacionesClientes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM vacaciones_clientes");
    return lista;
  }

  public static ArrayList getAllVacacionesClientesDisfrutadas(String fechaActual)
  {
    VacacionesClientes elto = new VacacionesClientes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM vacaciones_clientes WHERE vac_fecha_hasta < DATE_FORMAT('"+fechaActual+"','%Y/%m/%d') AND vac_disfrutadas=0");
   
    return lista;
  }
  
  public static ArrayList getAllVacacionesClientesDisfrutandolas(String fechaActual)
  {
    VacacionesClientes elto = new VacacionesClientes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM vacaciones_clientes WHERE "+VACFECHADESDE + " <= DATE_FORMAT('"+fechaActual+"','%Y/%m/%d') AND "+VACFECHAHASTA + " >= DATE_FORMAT('"+fechaActual+"','%Y/%m/%d')");
    return lista;
  }
  
  public static void actualizaVacacionesADisfrutadas( ArrayList disfrutadas ){
	  Iterator it = disfrutadas.iterator();
	  while( it.hasNext() ){
		  VacacionesClientes disfrutada = (VacacionesClientes) it.next();
		  disfrutada.setVacDisfrutadas("1");
		  disfrutada.actualiza();
		  //Ponemos el cliente activo ya que ya ha disfrutado sus vacaciones
		  Clientes cliente = Clientes.getClientesByClId( disfrutada.getVacClId() );
		  cliente.setClActivo("1");
		  cliente.actualiza();
	  }
  }
  
  public static void inactivaClientes( ArrayList disfrutandolas ){
	  Iterator it = disfrutandolas.iterator();
	  while( it.hasNext() ){
		  VacacionesClientes disfrutada = (VacacionesClientes) it.next();
		  //Ponemos el cliente inactivo ya que esta disfrutando las vacaciones
		  Clientes cliente = Clientes.getClientesByClId( disfrutada.getVacClId() );
		  cliente.setClActivo("0");
		  cliente.actualiza();
	  }
  }
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    VacacionesClientes vacacionesclientes = new VacacionesClientes();
  	try{
    	vacacionesclientes.setVacClId(rs.getString(VACCLID));
    	vacacionesclientes.setVacFechaDesde( rs.getTimestamp(VACFECHADESDE) );
    	vacacionesclientes.setVacFechaHasta(rs.getTimestamp(VACFECHAHASTA) );
    	vacacionesclientes.setVacId(rs.getString(VACID));
    	vacacionesclientes.setVacDisfrutadas( rs.getString(VACDISFRUTADAS) );
  	}catch(Exception e){
  		throw e;
  	}
    	return vacacionesclientes;
  }

  public static Object[] getVacacionesClientesLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	VacacionesClientes vacacionesclientes = new VacacionesClientes();
	String sql = "SELECT * FROM vacaciones_clientes " +
				 " WHERE 1 = 1 ";
  	try{
		VacacionesClientes vacacionesclientesFiltro = new VacacionesClientes();
		if(listaPaginada.getRequest().getSession().getAttribute("FVACACIONES_CLIENTES")!=null){
			vacacionesclientesFiltro = (VacacionesClientes)listaPaginada.getRequest().getSession().getAttribute("FVACACIONES_CLIENTES");
		}
    	if(!Utils.empty(vacacionesclientesFiltro.getVacClId()))
    		sqlAñadido += "AND " + VACCLID + " = '" + vacacionesclientesFiltro.getVacClId() + "'";
    	if( vacacionesclientesFiltro.getVacFechaDesde()!=null )
    		sqlAñadido += "AND " + VACFECHADESDE + " = '" + vacacionesclientesFiltro.getVacFechaDesde() + "'";
    	if( vacacionesclientesFiltro.getVacFechaHasta() !=null )
    		sqlAñadido += "AND " + VACFECHAHASTA + " = '" + vacacionesclientesFiltro.getVacFechaHasta() + "'";
    	if(!Utils.empty(vacacionesclientesFiltro.getVacId()))
    		sqlAñadido += "AND " + VACID + " = '" + vacacionesclientesFiltro.getVacId() + "'";
    	if(!Utils.empty(vacacionesclientesFiltro.getVacDisfrutadas()))
    		sqlAñadido += "AND " + VACDISFRUTADAS + " = '" + vacacionesclientesFiltro.getVacDisfrutadas() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, vacacionesclientes);
  }
}
