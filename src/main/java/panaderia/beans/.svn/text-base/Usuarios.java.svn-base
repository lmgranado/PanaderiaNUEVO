package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Usuarios extends panaderia.beans.entidad.UsuariosEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Usuarios(){ super(); }

  public static Usuarios getUsuariosByUsId(String usId)
  {
    Usuarios elto = new Usuarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios WHERE us_id='"+usId+"'");
    return elto;
  }

  public static Usuarios getUsuariosByUsLoginUsPassw(String usLogin, String usPassw)
  {
    Usuarios elto = new Usuarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios WHERE us_login='"+usLogin+"' AND us_passw='"+usPassw+"'" );
    return elto;
  }
  
  public static Usuarios getUsuariosByUsLogin(String usLogin)
  {
    Usuarios elto = new Usuarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios WHERE us_login='"+usLogin+"'");
    return elto;
  }

  public static Usuarios getUsuariosByUsNif(String usNif)
  {
    Usuarios elto = new Usuarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios WHERE us_nif='"+usNif+"'");
    return elto;
  }

  public static Usuarios getUsuariosByUsPassw(String usPassw)
  {
    Usuarios elto = new Usuarios();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios WHERE us_passw='"+usPassw+"'");
    return elto;
  }

  public static ArrayList getAllUsuarios()
  {
    Usuarios elto = new Usuarios();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM usuarios order by us_login");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Usuarios usuarios = new Usuarios();
  	try{
    	usuarios.setUsApellidos(rs.getString(USAPELLIDOS));
    	usuarios.setUsCodigoPostal(rs.getString(USCODIGOPOSTAL));
    	usuarios.setUsDireccion(rs.getString(USDIRECCION));
    	usuarios.setUsEmail(rs.getString(USEMAIL));
    	usuarios.setUsId(rs.getString(USID));
    	usuarios.setUsLogin(rs.getString(USLOGIN));
    	usuarios.setUsMovil(rs.getString(USMOVIL));
    	usuarios.setUsNif(rs.getString(USNIF));
    	usuarios.setUsNombre(rs.getString(USNOMBRE));
    	usuarios.setUsPassw(rs.getString(USPASSW));
    	usuarios.setUsTelefono(rs.getString(USTELEFONO));
  	}catch(Exception e){
  		throw e;
  	}
    	return usuarios;
  }

  public static Object[] getUsuariosLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Usuarios usuarios = new Usuarios();
	String sql = "SELECT * FROM usuarios " +
				 " WHERE 1 = 1 ";
  	try{
		Usuarios usuariosFiltro = new Usuarios();
		if(listaPaginada.getRequest().getSession().getAttribute("FUSUARIOS")!=null){
			usuariosFiltro = (Usuarios)listaPaginada.getRequest().getSession().getAttribute("FUSUARIOS");
		}
    	if(!Utils.empty(usuariosFiltro.getUsApellidos()))
    		sqlAñadido += "AND UPPER(" + USAPELLIDOS + ") like UPPER('%" + usuariosFiltro.getUsApellidos() + "%')";
    	if(!Utils.empty(usuariosFiltro.getUsCodigoPostal()))
    		sqlAñadido += "AND " + USCODIGOPOSTAL + " = '" + usuariosFiltro.getUsCodigoPostal() + "'";
    	if(!Utils.empty(usuariosFiltro.getUsDireccion()))
    		sqlAñadido += "AND UPPER(" + USDIRECCION + ") like UPPER('%" + usuariosFiltro.getUsDireccion() + "%')";
    	if(!Utils.empty(usuariosFiltro.getUsEmail()))
    		sqlAñadido += "AND " + USEMAIL + " = '" + usuariosFiltro.getUsEmail() + "'";
    	if(!Utils.empty(usuariosFiltro.getUsId()))
    		sqlAñadido += "AND " + USID + " = '" + usuariosFiltro.getUsId() + "'";
    	if(!Utils.empty(usuariosFiltro.getUsLogin()))
    		sqlAñadido += "AND UPPER(" + USLOGIN + ") like UPPER('%" + usuariosFiltro.getUsLogin() + "%')";
    	if(!Utils.empty(usuariosFiltro.getUsMovil()))
    		sqlAñadido += "AND " + USMOVIL + " = '" + usuariosFiltro.getUsMovil() + "'";
    	if(!Utils.empty(usuariosFiltro.getUsNif()))
    		sqlAñadido += "AND UPPER(" + USNIF + ") like UPPER('%" + usuariosFiltro.getUsNif() + "%')";
    	if(!Utils.empty(usuariosFiltro.getUsNombre()))
    		sqlAñadido += "AND UPPER(" + USNOMBRE + ") like UPPER('%" + usuariosFiltro.getUsNombre() + "%')";
    	if(!Utils.empty(usuariosFiltro.getUsPassw()))
    		sqlAñadido += "AND " + USPASSW + " = '" + usuariosFiltro.getUsPassw() + "'";
    	if(!Utils.empty(usuariosFiltro.getUsTelefono()))
    		sqlAñadido += "AND " + USTELEFONO + " = '" + usuariosFiltro.getUsTelefono() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, usuarios);
  }
}
