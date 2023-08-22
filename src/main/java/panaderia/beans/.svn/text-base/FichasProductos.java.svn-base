package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class FichasProductos extends panaderia.beans.entidad.FichasProductosEntidad
{
  private static final long serialVersionUID = 1L;
  
  public FichasProductos(){ super(); }

  public static FichasProductos getFichasProductosByFprodId(String fprodId)
  {
    FichasProductos elto = new FichasProductos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM fichas_productos WHERE fprod_id='"+fprodId+"'");
    return elto;
  }

  public static ArrayList getAllFichasProductos()
  {
    FichasProductos elto = new FichasProductos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM fichas_productos");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    FichasProductos fichasproductos = new FichasProductos();
  	try{
    	fichasproductos.setFprodCaracteristicaLote(rs.getString(FPRODCARACTERISTICALOTE));
    	fichasproductos.setFprodCaracteristicaProducto(rs.getString(FPRODCARACTERISTICAPRODUCTO));
    	fichasproductos.setFprodClasificacionIndustria(rs.getString(FPRODCLASIFICACIONINDUSTRIA));
    	fichasproductos.setFprodClasificacionLegislacion(rs.getString(FPRODCLASIFICACIONLEGISLACION));
    	fichasproductos.setFprodCondicionAlmacenamiento(rs.getString(FPRODCONDICIONALMACENAMIENTO));
    	fichasproductos.setFprodCondicionTransporte(rs.getString(FPRODCONDICIONTRANSPORTE));
    	fichasproductos.setFprodConsumoPreferente(rs.getString(FPRODCONSUMOPREFERENTE));
    	fichasproductos.setFprodDenominacionComercial(rs.getString(FPRODDENOMINACIONCOMERCIAL));
    	fichasproductos.setFprodDestinoProducto(rs.getString(FPRODDESTINOPRODUCTO));
    	fichasproductos.setFprodEnvasado(rs.getString(FPRODENVASADO));
    	fichasproductos.setFprodEtiquetado(rs.getString(FPRODETIQUETADO));
    	fichasproductos.setFprodFormato(rs.getString(FPRODFORMATO));
    	fichasproductos.setFprodId(rs.getString(FPRODID));
    	fichasproductos.setFprodMarcaComercial(rs.getString(FPRODMARCACOMERCIAL));
    	fichasproductos.setFprodNombreFicha(rs.getString(FPRODNOMBREFICHA));
    	fichasproductos.setFprodProcesado(rs.getString(FPRODPROCESADO));
  	}catch(Exception e){
  		throw e;
  	}
    	return fichasproductos;
  }

  public static Object[] getFichasProductosLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	FichasProductos fichasproductos = new FichasProductos();
	String sql = "SELECT * FROM fichas_productos " +
				 " WHERE 1 = 1 ";
  	try{
		FichasProductos fichasproductosFiltro = new FichasProductos();
		if(listaPaginada.getRequest().getSession().getAttribute("FFICHAS_PRODUCTOS")!=null){
			fichasproductosFiltro = (FichasProductos)listaPaginada.getRequest().getSession().getAttribute("FFICHAS_PRODUCTOS");
		}
    	if(!Utils.empty(fichasproductosFiltro.getFprodCaracteristicaLote()))
    		sqlAñadido += "AND " + FPRODCARACTERISTICALOTE + " = '" + fichasproductosFiltro.getFprodCaracteristicaLote() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodCaracteristicaProducto()))
    		sqlAñadido += "AND " + FPRODCARACTERISTICAPRODUCTO + " = '" + fichasproductosFiltro.getFprodCaracteristicaProducto() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodClasificacionIndustria()))
    		sqlAñadido += "AND " + FPRODCLASIFICACIONINDUSTRIA + " = '" + fichasproductosFiltro.getFprodClasificacionIndustria() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodClasificacionLegislacion()))
    		sqlAñadido += "AND " + FPRODCLASIFICACIONLEGISLACION + " = '" + fichasproductosFiltro.getFprodClasificacionLegislacion() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodCondicionAlmacenamiento()))
    		sqlAñadido += "AND " + FPRODCONDICIONALMACENAMIENTO + " = '" + fichasproductosFiltro.getFprodCondicionAlmacenamiento() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodCondicionTransporte()))
    		sqlAñadido += "AND " + FPRODCONDICIONTRANSPORTE + " = '" + fichasproductosFiltro.getFprodCondicionTransporte() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodConsumoPreferente()))
    		sqlAñadido += "AND " + FPRODCONSUMOPREFERENTE + " = '" + fichasproductosFiltro.getFprodConsumoPreferente() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodDenominacionComercial()))
    		sqlAñadido += "AND " + FPRODDENOMINACIONCOMERCIAL + " like '%" + fichasproductosFiltro.getFprodDenominacionComercial() + "%'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodDestinoProducto()))
    		sqlAñadido += "AND " + FPRODDESTINOPRODUCTO + " = '" + fichasproductosFiltro.getFprodDestinoProducto() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodEnvasado()))
    		sqlAñadido += "AND " + FPRODENVASADO + " = '" + fichasproductosFiltro.getFprodEnvasado() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodEtiquetado()))
    		sqlAñadido += "AND " + FPRODETIQUETADO + " = '" + fichasproductosFiltro.getFprodEtiquetado() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodFormato()))
    		sqlAñadido += "AND " + FPRODFORMATO + " = '" + fichasproductosFiltro.getFprodFormato() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodId()))
    		sqlAñadido += "AND " + FPRODID + " = '" + fichasproductosFiltro.getFprodId() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodMarcaComercial()))
    		sqlAñadido += "AND " + FPRODMARCACOMERCIAL + " = '" + fichasproductosFiltro.getFprodMarcaComercial() + "'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodNombreFicha()))
    		sqlAñadido += "AND " + FPRODNOMBREFICHA + " like '%" + fichasproductosFiltro.getFprodNombreFicha() + "%'";
    	if(!Utils.empty(fichasproductosFiltro.getFprodProcesado()))
    		sqlAñadido += "AND " + FPRODPROCESADO + " = '" + fichasproductosFiltro.getFprodProcesado() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, fichasproductos);
  }
}
