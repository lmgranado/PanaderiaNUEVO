package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class Entregas extends panaderia.beans.entidad.EntregasEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Entregas(){ super(); }

  public static Entregas getEntregasByEntId(String entId)
  {
    Entregas elto = new Entregas();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas WHERE ent_id='"+entId+"'");
    return elto;
  }

  public static Entregas getEntregasByEntNombre(String entNombre)
  {
    Entregas elto = new Entregas();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas WHERE ent_nombre='"+entNombre+"'");
    return elto;
  }

  public static ArrayList getAllEntregas()
  {
    Entregas elto = new Entregas();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM entregas order by ent_nombre");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Entregas entregas = new Entregas();
  	try{
    	entregas.setEntId(rs.getString(ENTID));
    	entregas.setEntNombre(rs.getString(ENTNOMBRE));
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[5];
    	clDatosRelacionados[0] = rs.getString("conDetalles");
    	entregas.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return entregas;
  }

  public static Object[] getEntregasLista(PaginatedListTest listaPaginada) 
  {
	String sqlA�adido = " ";
	Entregas entregas = new Entregas();
	String sql = "SELECT entregas.*, count(entregas_detalle.end_ent_id) as conDetalles FROM entregas " + 
				 "	left join entregas_detalle on entregas_detalle.end_ent_id = entregas.ent_id " +
				 "	WHERE 1 = 1 ";
  	try{
		Entregas entregasFiltro = new Entregas();
		if(listaPaginada.getRequest().getSession().getAttribute("FENTREGAS")!=null){
			entregasFiltro = (Entregas)listaPaginada.getRequest().getSession().getAttribute("FENTREGAS");
		}
    	if(!Utils.empty(entregasFiltro.getEntId()))
    		sqlA�adido += "AND " + ENTID + " = '" + entregasFiltro.getEntId() + "'";
    	if(!Utils.empty(entregasFiltro.getEntNombre()))
    		sqlA�adido += "AND UPPER(" + ENTNOMBRE + ") like UPPER('%" + entregasFiltro.getEntNombre() + "%')";
  	}catch(Exception e){
  		System.out.println(e);
  	}
  	sqlA�adido += " group by entregas.ent_id ";
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, entregas);
  }
  
  //****************** Vamos a poner aqui los metodos para la prevision de fabricacion po entregas ***********************
  public static ArrayList getAllPrevisionFabricacion(String agCliente,
		  											 String agRuta,
		  											 String agEntrega,
		  											 String agFamilia,
		  											 String idsEntregas)
  {
	String groupBy = "";
	if(agCliente.equals("1")) groupBy += ",c.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) groupBy += ",r.RUT_NOMBRE";
	if(agEntrega.equals("1")) groupBy += ",e.ENT_NOMBRE";
	if(agFamilia.equals("1")) groupBy += ",f.FAM_NOMBRE";
	
	String campos = "";
	if(agCliente.equals("1")) campos += ",c.CL_NOMBRE_COMERCIAL";
	if(agRuta.equals("1")) campos += ",r.RUT_NOMBRE";
	if(agEntrega.equals("1")) campos += ",e.ENT_NOMBRE";
	if(agFamilia.equals("1")) campos += ",f.FAM_NOMBRE";
	
	campos +=  ", p.prod_peso_masa";
	
	if(!idsEntregas.equals("")){
		idsEntregas = "	and e.ent_id in (" + idsEntregas + ") ";
	}
		
    ArrayList lista = EntidadBean.consulta("select p.prod_nombre, " +
							    		   " sum((CASE WHEN ed.end_viaje = 1 THEN cud.cud_cantidad1 ELSE 0 END) + " + 						  
							    		   "	(CASE WHEN ed.end_viaje = 2 THEN cud.cud_cantidad2 ELSE 0 END) + " +						  
							    		   "	(CASE WHEN ed.end_viaje = 3 THEN cud.cud_cantidad3 ELSE 0 END) + " + 						  
							    		   "	(CASE WHEN ed.end_viaje = 4 THEN cud.cud_cantidad4 ELSE 0 END) + " + 						  
							    		   "	(CASE WHEN ed.end_viaje = 5 THEN cud.cud_cantidad5 ELSE 0 END)) " +
										        campos +
										   "	from entregas e " +
										   "	join entregas_detalle ed on ed.end_ent_id = e.ent_id " +
										   "	join clientes c ON ed.end_cl_id = c.cl_id " +
										   "	join rutas r ON ed.end_rut_id = r.rut_id " +
										   "	join cuadrantes cu ON ed.end_cu_id = cu.cu_id " +
										   "	join cuadrantes_detalle cud ON cud.cud_cu_id = cu.cu_id " +
										   "	join productos_cliente pc ON cud.cud_pcl_id = pc.pcl_id " +
										   "	left join productos p ON pc.pcl_prod_id = p.prod_id " +
										   "	left join familias f ON p.prod_fam_id = f.fam_id " +
										   "	where 1 = 1 " +
										   "    AND ((CASE WHEN ed.end_viaje = 1 THEN cud.cud_cantidad1 ELSE 0 END) + " + 						  
							    		   "		(CASE WHEN ed.end_viaje = 2 THEN cud.cud_cantidad2 ELSE 0 END) + " +						  
							    		   "		(CASE WHEN ed.end_viaje = 3 THEN cud.cud_cantidad3 ELSE 0 END) + " + 						  
							    		   "		(CASE WHEN ed.end_viaje = 4 THEN cud.cud_cantidad4 ELSE 0 END) + " + 						  
							    		   "		(CASE WHEN ed.end_viaje = 5 THEN cud.cud_cantidad5 ELSE 0 END)) > 0 " +
										        idsEntregas +
										   "	group by p.prod_nombre" + groupBy);
    return lista;
  }
}
