package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Clientes extends panaderia.beans.entidad.ClientesEntidad
{
  private static final long serialVersionUID = 1L;
  private static final String estado = "and cl_activo=1";
  
  public Clientes(){ super(); }

  public static Clientes getClientesByClCobId(String clCobId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_cob_id='"+clCobId+"' "+estado);
    return elto;
  }

  public static Clientes getClientesByClFpId(String clFpId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_fp_id='"+clFpId+"' "+estado);
    return elto;
  }

  public static Clientes getClientesByClId(String clId)
  {
	    Clientes elto = new Clientes();
	    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_id='"+clId+"'");
	    String [] datos = Clientes.consultarValores( "SELECT" +
		 "	    fp.fp_descripcion, " +
		 "	    pf.pf_descripcion, " +
		 "	    pf.pf_dia, " +
		 "	    loc.loc_nombre, " +
		 "	    prov.prov_nombre " +
		 "	FROM clientes c " +
		 "	JOIN formas_pago fp ON fp.fp_id = c.cl_fp_id " +
		 "	JOIN periodos_facturacion pf ON pf.pf_id = c.cl_pf_id " +
		 "	JOIN provincias prov ON prov.prov_id = c.cl_prov_id " +
		 "	JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
		 " WHERE cl_id='"+clId+"'");
	    
	    //Parte de las tablas relacionadas
		String[] clDatosRelacionados = new String[5];
		if(datos!=null){
			clDatosRelacionados[0] = datos[0];
			clDatosRelacionados[1] = datos[1];
			clDatosRelacionados[2] = datos[2];
			clDatosRelacionados[3] = datos[3];
			clDatosRelacionados[4] = datos[4];
			elto.setClDatosRelacionados(clDatosRelacionados);
		}
	
	return elto;
  }

  public static Clientes getClientesByClLocId(String clLocId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_loc_id='"+clLocId+"' "+estado);
    return elto;
  }

  public static Clientes getClientesByClNif(String clNif)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_nif='"+clNif+"' "+estado);
    return elto;
  }

  public static Clientes getClientesByClNifYClId(String clNif, String clId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_nif='"+clNif+"' and cl_id<>'"+clId+"' "+estado);
    return elto;
  }
  
  public static Clientes getClientesByClPfId(String clPfId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_pf_id='"+clPfId+"' "+estado);
    return elto;
  }

  public static Clientes getClientesByClProvId(String clProvId)
  {
    Clientes elto = new Clientes();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes WHERE cl_prov_id='"+clProvId+"' "+estado);
    return elto;
  }

  public static ArrayList getAllClientes()
  {
    Clientes elto = new Clientes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes where cl_activo=1 order by cl_nombre_comercial");
    return lista;
  }
  
  public static ArrayList getAllClientesActivosEInactivos()
  {
    Clientes elto = new Clientes();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM clientes order by cl_nombre_comercial");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Clientes clientes = new Clientes();
  	try{
    	clientes.setClApellidos(rs.getString(CLAPELLIDOS));
    	clientes.setClCcc(rs.getString(CLCCC));
    	clientes.setClCobId(rs.getString(CLCOBID));
    	clientes.setClCodigoPostal(rs.getString(CLCODIGOPOSTAL));
    	clientes.setClDescuento(rs.getString(CLDESCUENTO));
    	clientes.setClDireccion(rs.getString(CLDIRECCION));
    	clientes.setClEmail(rs.getString(CLEMAIL));
    	clientes.setClFax(rs.getString(CLFAX));
    	clientes.setClFpId(rs.getString(CLFPID));
    	clientes.setClId(rs.getString(CLID));
    	clientes.setClLocId(rs.getString(CLLOCID));
    	clientes.setClMovil(rs.getString(CLMOVIL));
    	clientes.setClNif(rs.getString(CLNIF));
    	clientes.setClNombre(rs.getString(CLNOMBRE));
    	clientes.setClNombreBanco(rs.getString(CLNOMBREBANCO));
    	clientes.setClNombreComercial(rs.getString(CLNOMBRECOMERCIAL));
    	clientes.setClPersonaContacto(rs.getString(CLPERSONACONTACTO));
    	clientes.setClPfId(rs.getString(CLPFID));
    	clientes.setClProvId(rs.getString(CLPROVID));
    	clientes.setClTelefono(rs.getString(CLTELEFONO));
    	clientes.setClWeb(rs.getString(CLWEB));
    	clientes.setClActivo(rs.getString(CLACTIVO));
    	clientes.setClProporcionIva(rs.getString(CLPROPORCIONIVA));
    	clientes.setClIdEmpresa(rs.getString(CLIDEMPRESA));
    	clientes.setClPertenencia(rs.getString(CLPERTENENCIA));
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[5];
    	clDatosRelacionados[0] = rs.getString("fp_descripcion");
    	clDatosRelacionados[1] = rs.getString("pf_descripcion");
    	clDatosRelacionados[2] = rs.getString("pf_dia");
    	clDatosRelacionados[3] = rs.getString("loc_nombre");
    	clDatosRelacionados[4] = rs.getString("prov_nombre");
    	clientes.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return clientes;
  }

  public static Object[] getClientesLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Clientes clientes = new Clientes();
	String sql = "SELECT c.*, " +
				 "	    fp.fp_descripcion, " +
				 "	    pf.pf_descripcion, " +
				 "	    pf.pf_dia, " +
				 "	    loc.loc_nombre, " +
				 "	    prov.prov_nombre " +
				 "	FROM clientes c " +
				 "	JOIN formas_pago fp ON fp.fp_id = c.cl_fp_id " +
				 "	JOIN periodos_facturacion pf ON pf.pf_id = c.cl_pf_id " +
				 "	JOIN provincias prov ON prov.prov_id = c.cl_prov_id " +
				 "	JOIN localidades loc ON loc.loc_id = c.cl_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
				 " WHERE 1 = 1 ";
  	try{
		Clientes clientesFiltro = new Clientes();
		if(listaPaginada.getRequest().getSession().getAttribute("FCLIENTES")!=null){
			clientesFiltro = (Clientes)listaPaginada.getRequest().getSession().getAttribute("FCLIENTES");
		}
		if(listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVF")!=null){
			clientesFiltro = (Clientes)listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVF");
		}
		if(listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVA")!=null){
			clientesFiltro = (Clientes)listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVA");
		}
		if(listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVN")!=null){
			clientesFiltro = (Clientes)listaPaginada.getRequest().getSession().getAttribute("FCLIENTESVN");
		}
		
    	if(!Utils.empty(clientesFiltro.getClApellidos()))
    		sqlAñadido += "AND UPPER(" + CLAPELLIDOS + ") like UPPER('%" + clientesFiltro.getClApellidos() + "%')";
    	if(!Utils.empty(clientesFiltro.getClCcc()))
    		sqlAñadido += "AND " + CLCCC + " = '" + clientesFiltro.getClCcc() + "'";
    	if(!Utils.empty(clientesFiltro.getClCobId()))
    		sqlAñadido += "AND " + CLCOBID + " = '" + clientesFiltro.getClCobId() + "'";
    	if(!Utils.empty(clientesFiltro.getClCodigoPostal()))
    		sqlAñadido += "AND UPPER(" + CLCODIGOPOSTAL + ") like UPPER('%" + clientesFiltro.getClCodigoPostal() + "%)";
    	if(!Utils.empty(clientesFiltro.getClDescuento()))
    		sqlAñadido += "AND " + CLDESCUENTO + " = '" + clientesFiltro.getClDescuento() + "'";
    	if(!Utils.empty(clientesFiltro.getClDireccion()))
    		sqlAñadido += "AND UPPER(" + CLDIRECCION + ") like UPPER('%" + clientesFiltro.getClDireccion() + "%')";
    	if(!Utils.empty(clientesFiltro.getClEmail()))
    		sqlAñadido += "AND UPPER(" + CLEMAIL + ") like UPPER('%" + clientesFiltro.getClEmail() + "%')";
    	if(!Utils.empty(clientesFiltro.getClFax()))
    		sqlAñadido += "AND UPPER(" + CLFAX + ") like UPPER('%" + clientesFiltro.getClFax() + "%')";
    	if(!Utils.empty(clientesFiltro.getClFpId()))
    		sqlAñadido += "AND " + CLFPID + " = '" + clientesFiltro.getClFpId() + "'";
    	if(!Utils.empty(clientesFiltro.getClId()))
    		sqlAñadido += "AND " + CLID + " = '" + clientesFiltro.getClId() + "'";
    	if(!Utils.empty(clientesFiltro.getClLocId()))
    		sqlAñadido += "AND " + CLLOCID + " = '" + clientesFiltro.getClLocId() + "'";
    	if(!Utils.empty(clientesFiltro.getClMovil()))
    		sqlAñadido += "AND " + CLMOVIL + " like '%" + clientesFiltro.getClMovil() + "%'";
    	if(!Utils.empty(clientesFiltro.getClNif()))
    		sqlAñadido += "AND UPPER(" + CLNIF + ") like UPPER('%" + clientesFiltro.getClNif() + "%')";
    	if(!Utils.empty(clientesFiltro.getClNombre()))
    		sqlAñadido += "AND UPPER(" + CLNOMBRE + ") like UPPER('%" + clientesFiltro.getClNombre() + "%')";
    	if(!Utils.empty(clientesFiltro.getClNombreBanco()))
    		sqlAñadido += "AND " + CLNOMBREBANCO + " = '" + clientesFiltro.getClNombreBanco() + "'";
    	if(!Utils.empty(clientesFiltro.getClNombreComercial()))
    		sqlAñadido += "AND UPPER(" + CLNOMBRECOMERCIAL + ") like UPPER('%" + clientesFiltro.getClNombreComercial() + "%')";
    	if(!Utils.empty(clientesFiltro.getClPersonaContacto()))
    		sqlAñadido += "AND UPPER(" + CLPERSONACONTACTO + ") like UPPER('%" + clientesFiltro.getClPersonaContacto() + "%')";
    	if(!Utils.empty(clientesFiltro.getClPfId()))
    		sqlAñadido += "AND " + CLPFID + " = '" + clientesFiltro.getClPfId() + "'";
    	if(!Utils.empty(clientesFiltro.getClProvId()))
    		sqlAñadido += "AND " + CLPROVID + " = '" + clientesFiltro.getClProvId() + "'";
    	if(!Utils.empty(clientesFiltro.getClTelefono()))
    		sqlAñadido += "AND " + CLTELEFONO + " like '%" + clientesFiltro.getClTelefono() + "%'";
    	if(!Utils.empty(clientesFiltro.getClWeb()))
    		sqlAñadido += "AND UPPER(" + CLWEB + ") like UPPER('%" + clientesFiltro.getClWeb() + "%')";
    	
    	String estado = "1";
    	if(!Utils.empty(clientesFiltro.getClActivo())){
    		estado = clientesFiltro.getClActivo();   		
    	}
    	sqlAñadido += "AND " + CLACTIVO + " = '" + estado + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, clientes);
  }
}
