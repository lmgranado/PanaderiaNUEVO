package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Proveedores extends panaderia.beans.entidad.ProveedoresEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Proveedores(){ super(); }

  public static Proveedores getProveedoresByPrFpId(String prFpId)
  {
    Proveedores elto = new Proveedores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_fp_id='"+prFpId+"'");
    return elto;
  }

  public static Proveedores getProveedoresByPrId(String prId)
  {
	    Proveedores elto = new Proveedores();
	    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_id='"+prId+"'");
	    String [] datos = Proveedores.consultarValores( "SELECT" +
		 "	    fp.fp_descripcion, " +
		 "	    pf.pf_descripcion, " +
		 "	    pf.pf_dia, " +
		 "	    loc.loc_nombre, " +
		 "	    prov.prov_nombre " +
		 "	FROM proveedores p " +
		 "	JOIN formas_pago fp ON fp.fp_id = p.pr_fp_id " +
		 "	JOIN periodos_facturacion pf ON pf.pf_id = p.pr_pf_id " +
		 "	JOIN provincias prov ON prov.prov_id = p.pr_prov_id " +
		 "	JOIN localidades loc ON loc.loc_id = p.pr_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
		 " WHERE pr_id='"+prId+"'");
	    
	    //Parte de las tablas relacionadas
		String[] prDatosRelacionados = new String[5];
		if(datos!=null){
			prDatosRelacionados[0] = datos[0];
			prDatosRelacionados[1] = datos[1];
			prDatosRelacionados[2] = datos[2];
			prDatosRelacionados[3] = datos[3];
			prDatosRelacionados[4] = datos[4];
		}
		
		elto.setPrDatosRelacionados( prDatosRelacionados );
		
	    return elto;
  }

  public static Proveedores getProveedoresByPrLocId(String prLocId)
  {
    Proveedores elto = new Proveedores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_loc_id='"+prLocId+"'");
    return elto;
  }

  public static Proveedores getProveedoresByPrNif(String prNif)
  {
    Proveedores elto = new Proveedores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_nif='"+prNif+"'");
    return elto;
  }

  public static Proveedores getProveedoresByPrPfId(String prPfId)
  {
    Proveedores elto = new Proveedores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_pf_id='"+prPfId+"'");
    return elto;
  }

  public static Proveedores getProveedoresByPrProvId(String prProvId)
  {
    Proveedores elto = new Proveedores();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores WHERE pr_prov_id='"+prProvId+"'");
    return elto;
  }

  public static ArrayList getAllProveedores()
  {
    Proveedores elto = new Proveedores();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores order by concat(pr_nombre, pr_apellidos)");
    return lista;
  }
  
  public static ArrayList getAllProveedoresCompra()
  {
    Proveedores elto = new Proveedores();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores where pr_id not in (999,998) order by concat(pr_nombre, pr_apellidos)");
    return lista;
  }
  
  public static ArrayList getAllProveedoresFabricacion()
  {
    Proveedores elto = new Proveedores();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM proveedores where pr_id in (999,998) order by concat(pr_nombre, pr_apellidos)");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Proveedores proveedores = new Proveedores();
  	try{
    	proveedores.setPrApellidos(rs.getString(PRAPELLIDOS));
    	proveedores.setPrCcc(rs.getString(PRCCC));
    	proveedores.setPrCodigoPostal(rs.getString(PRCODIGOPOSTAL));
    	proveedores.setPrDescuento(rs.getString(PRDESCUENTO));
    	proveedores.setPrDireccion(rs.getString(PRDIRECCION));
    	proveedores.setPrEmail(rs.getString(PREMAIL));
    	proveedores.setPrFax(rs.getString(PRFAX));
    	proveedores.setPrFpId(rs.getString(PRFPID));
    	proveedores.setPrId(rs.getString(PRID));
    	proveedores.setPrLocId(rs.getString(PRLOCID));
    	proveedores.setPrMovil(rs.getString(PRMOVIL));
    	proveedores.setPrNif(rs.getString(PRNIF));
    	proveedores.setPrNombre(rs.getString(PRNOMBRE));
    	proveedores.setPrNombreBanco(rs.getString(PRNOMBREBANCO));
    	proveedores.setPrNombreComercial(rs.getString(PRNOMBRECOMERCIAL));
    	proveedores.setPrObservaciones(rs.getString(PROBSERVACIONES));
    	proveedores.setPrPersonaContacto(rs.getString(PRPERSONACONTACTO));
    	proveedores.setPrPfId(rs.getString(PRPFID));
    	proveedores.setPrProvId(rs.getString(PRPROVID));
    	proveedores.setPrTelefono(rs.getString(PRTELEFONO));
    	proveedores.setPrWeb(rs.getString(PRWEB));
    	
    	//Parte de las tablas relacionadas
    	String[] prDatosRelacionados = new String[5];
    	prDatosRelacionados[0] = rs.getString("fp_descripcion");
    	prDatosRelacionados[1] = rs.getString("pf_descripcion");
    	prDatosRelacionados[2] = rs.getString("pf_dia");
    	prDatosRelacionados[3] = rs.getString("loc_nombre");
    	prDatosRelacionados[4] = rs.getString("prov_nombre");
    	proveedores.setPrDatosRelacionados( prDatosRelacionados );
  	}catch(Exception e){
  		throw e;
  	}
    	return proveedores;
  }

  public static Object[] getProveedoresLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	Proveedores proveedores = new Proveedores();
	String sql = "SELECT p.*, " +
	 "	    fp.fp_descripcion, " +
	 "	    pf.pf_descripcion, " +
	 "	    pf.pf_dia, " +
	 "	    loc.loc_nombre, " +
	 "	    prov.prov_nombre " +
	 "	FROM proveedores p " +
	 "	JOIN formas_pago fp ON fp.fp_id = p.pr_fp_id " +
	 "	JOIN periodos_facturacion pf ON pf.pf_id = p.pr_pf_id " +
	 "	JOIN provincias prov ON prov.prov_id = p.pr_prov_id " +
	 "	JOIN localidades loc ON loc.loc_id = p.pr_loc_id AND loc.loc_prov_codigo = prov.prov_codigo " +
	 " WHERE 1 = 1 ";
  	try{
		Proveedores proveedoresFiltro = new Proveedores();
		if(listaPaginada.getRequest().getSession().getAttribute("FPROVEEDORES")!=null){
			proveedoresFiltro = (Proveedores)listaPaginada.getRequest().getSession().getAttribute("FPROVEEDORES");
		}
    	if(!Utils.empty(proveedoresFiltro.getPrApellidos()))
    		sqlAñadido += "AND UPPER(" + PRAPELLIDOS + ") like UPPER('%" + proveedoresFiltro.getPrApellidos() + "%')";
    	if(!Utils.empty(proveedoresFiltro.getPrCcc()))
    		sqlAñadido += "AND " + PRCCC + " = '" + proveedoresFiltro.getPrCcc() + "'";
    	if(!Utils.empty(proveedoresFiltro.getTipo()) && proveedoresFiltro.getTipo().equals("C"))
    		sqlAñadido += "AND " + PRID + " NOT IN (999,998) ";
    	if(!Utils.empty(proveedoresFiltro.getTipo()) && proveedoresFiltro.getTipo().equals("F"))
    		sqlAñadido += "AND " + PRID + " IN (999,998) ";
    	if(!Utils.empty(proveedoresFiltro.getPrCodigoPostal()))
    		sqlAñadido += "AND " + PRCODIGOPOSTAL + " = '" + proveedoresFiltro.getPrCodigoPostal() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrDescuento()))
    		sqlAñadido += "AND " + PRDESCUENTO + " = '" + proveedoresFiltro.getPrDescuento() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrDireccion()))
    		sqlAñadido += "AND UPPER(" + PRDIRECCION + ") like UPPER('%" + proveedoresFiltro.getPrDireccion() + "%')";
    	if(!Utils.empty(proveedoresFiltro.getPrEmail()))
    		sqlAñadido += "AND " + PREMAIL + " = '" + proveedoresFiltro.getPrEmail() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrFax()))
    		sqlAñadido += "AND " + PRFAX + " = '" + proveedoresFiltro.getPrFax() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrFpId()))
    		sqlAñadido += "AND " + PRFPID + " = '" + proveedoresFiltro.getPrFpId() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrId()))
    		sqlAñadido += "AND " + PRID + " = '" + proveedoresFiltro.getPrId() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrLocId()))
    		sqlAñadido += "AND " + PRLOCID + " = '" + proveedoresFiltro.getPrLocId() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrMovil()))
    		sqlAñadido += "AND " + PRMOVIL + " = '" + proveedoresFiltro.getPrMovil() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrNif()))
    		sqlAñadido += "AND UPPER(" + PRNIF + ") like UPPER('%" + proveedoresFiltro.getPrNif() + "%')";
    	if(!Utils.empty(proveedoresFiltro.getPrNombre()))
    		sqlAñadido += "AND UPPER(" + PRNOMBRE + ") like UPPER('%" + proveedoresFiltro.getPrNombre() + "%')";
    	if(!Utils.empty(proveedoresFiltro.getPrNombreBanco()))
    		sqlAñadido += "AND " + PRNOMBREBANCO + " = '" + proveedoresFiltro.getPrNombreBanco() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrNombreComercial()))
    		sqlAñadido += "AND UPPER(" + PRNOMBRECOMERCIAL + ") like UPPER('%" + proveedoresFiltro.getPrNombreComercial() + "%')";
    	if(!Utils.empty(proveedoresFiltro.getPrObservaciones()))
    		sqlAñadido += "AND " + PROBSERVACIONES + " = '" + proveedoresFiltro.getPrObservaciones() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrPersonaContacto()))
    		sqlAñadido += "AND " + PRPERSONACONTACTO + " = '" + proveedoresFiltro.getPrPersonaContacto() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrPfId()))
    		sqlAñadido += "AND " + PRPFID + " = '" + proveedoresFiltro.getPrPfId() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrProvId()))
    		sqlAñadido += "AND " + PRPROVID + " = '" + proveedoresFiltro.getPrProvId() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrTelefono()))
    		sqlAñadido += "AND " + PRTELEFONO + " = '" + proveedoresFiltro.getPrTelefono() + "'";
    	if(!Utils.empty(proveedoresFiltro.getPrWeb()))
    		sqlAñadido += "AND " + PRWEB + " = '" + proveedoresFiltro.getPrWeb() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, proveedores);
  }
}
