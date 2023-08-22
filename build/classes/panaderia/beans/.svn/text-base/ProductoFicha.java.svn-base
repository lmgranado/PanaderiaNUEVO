package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class ProductoFicha extends panaderia.beans.entidad.ProductoFichaEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ProductoFicha(){ super(); }

  public static ProductoFicha getProductoFichaByProdfProdId(String prodfProdId)
  {
    ProductoFicha elto = new ProductoFicha();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_ficha WHERE prodf_prod_id='"+prodfProdId+"'");
    return elto;
  }
  
  public static ProductoFicha getProductoFichaByProdfId(String prodfId)
  {
    ProductoFicha elto = new ProductoFicha();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_ficha WHERE prodf_id='"+prodfId+"'");
    return elto;
  }

  public static ArrayList getAllProductoFicha()
  {
    ProductoFicha elto = new ProductoFicha();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM producto_ficha");
    return lista;
  }

  
  public static ProductoFicha getConversorFichaProductosAProductoFicha( String fprodId )
  {
	  ProductoFicha elto = new ProductoFicha();
	  elto.consultaReflexiva( "SELECT "+ALL_COLUMNS_CONVERSOR+" FROM fichas_productos WHERE fprod_id='"+fprodId+"'");
	  
	  return elto;
  }
  
  
  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ProductoFicha productoficha = new ProductoFicha();
  	try{
    	productoficha.setProdfCaracteristicaLote(rs.getString(PRODFCARACTERISTICALOTE));
    	productoficha.setProdfCaracteristicaProducto(rs.getString(PRODFCARACTERISTICAPRODUCTO));
    	productoficha.setProdfClasificacionIndustria(rs.getString(PRODFCLASIFICACIONINDUSTRIA));
    	productoficha.setProdfClasificacionLegislacion(rs.getString(PRODFCLASIFICACIONLEGISLACION));
    	productoficha.setProdfCondicionAlmacenamiento(rs.getString(PRODFCONDICIONALMACENAMIENTO));
    	productoficha.setProdfCondicionTransporte(rs.getString(PRODFCONDICIONTRANSPORTE));
    	productoficha.setProdfConsumoPreferente(rs.getString(PRODFCONSUMOPREFERENTE));
    	productoficha.setProdfDenominacionComercial(rs.getString(PRODFDENOMINACIONCOMERCIAL));
    	productoficha.setProdfDestinoProducto(rs.getString(PRODFDESTINOPRODUCTO));
    	productoficha.setProdfEnvasado(rs.getString(PRODFENVASADO));
    	productoficha.setProdfEtiquetado(rs.getString(PRODFETIQUETADO));
    	productoficha.setProdfFormato(rs.getString(PRODFFORMATO));
    	productoficha.setProdfId(rs.getString(PRODFID));
    	productoficha.setProdfMarcaComercial(rs.getString(PRODFMARCACOMERCIAL));
    	productoficha.setProdfNombreFicha(rs.getString(PRODFNOMBREFICHA));
    	productoficha.setProdfProcesado(rs.getString(PRODFPROCESADO));
  	}catch(Exception e){
  		throw e;
  	}
    	return productoficha;
  }

  public static Object[] getProductoFichaLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	ProductoFicha productoficha = new ProductoFicha();
	String sql = "SELECT * FROM productoficha " +
				 " WHERE 1 = 1 ";
  	try{
		ProductoFicha productofichaFiltro = new ProductoFicha();
		if(listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOFICHA")!=null){
			productofichaFiltro = (ProductoFicha)listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOFICHA");
		}
    	if(!Utils.empty(productofichaFiltro.getProdfCaracteristicaLote()))
    		sqlAñadido += "AND " + PRODFCARACTERISTICALOTE + " = '" + productofichaFiltro.getProdfCaracteristicaLote() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfCaracteristicaProducto()))
    		sqlAñadido += "AND " + PRODFCARACTERISTICAPRODUCTO + " = '" + productofichaFiltro.getProdfCaracteristicaProducto() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfClasificacionIndustria()))
    		sqlAñadido += "AND " + PRODFCLASIFICACIONINDUSTRIA + " = '" + productofichaFiltro.getProdfClasificacionIndustria() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfClasificacionLegislacion()))
    		sqlAñadido += "AND " + PRODFCLASIFICACIONLEGISLACION + " = '" + productofichaFiltro.getProdfClasificacionLegislacion() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfCondicionAlmacenamiento()))
    		sqlAñadido += "AND " + PRODFCONDICIONALMACENAMIENTO + " = '" + productofichaFiltro.getProdfCondicionAlmacenamiento() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfCondicionTransporte()))
    		sqlAñadido += "AND " + PRODFCONDICIONTRANSPORTE + " = '" + productofichaFiltro.getProdfCondicionTransporte() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfConsumoPreferente()))
    		sqlAñadido += "AND " + PRODFCONSUMOPREFERENTE + " = '" + productofichaFiltro.getProdfConsumoPreferente() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfDenominacionComercial()))
    		sqlAñadido += "AND " + PRODFDENOMINACIONCOMERCIAL + " = '" + productofichaFiltro.getProdfDenominacionComercial() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfDestinoProducto()))
    		sqlAñadido += "AND " + PRODFDESTINOPRODUCTO + " = '" + productofichaFiltro.getProdfDestinoProducto() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfEnvasado()))
    		sqlAñadido += "AND " + PRODFENVASADO + " = '" + productofichaFiltro.getProdfEnvasado() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfEtiquetado()))
    		sqlAñadido += "AND " + PRODFETIQUETADO + " = '" + productofichaFiltro.getProdfEtiquetado() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfFormato()))
    		sqlAñadido += "AND " + PRODFFORMATO + " = '" + productofichaFiltro.getProdfFormato() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfId()))
    		sqlAñadido += "AND " + PRODFID + " = '" + productofichaFiltro.getProdfId() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfMarcaComercial()))
    		sqlAñadido += "AND " + PRODFMARCACOMERCIAL + " = '" + productofichaFiltro.getProdfMarcaComercial() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfNombreFicha()))
    		sqlAñadido += "AND " + PRODFNOMBREFICHA + " = '" + productofichaFiltro.getProdfNombreFicha() + "'";
    	if(!Utils.empty(productofichaFiltro.getProdfProcesado()))
    		sqlAñadido += "AND " + PRODFPROCESADO + " = '" + productofichaFiltro.getProdfProcesado() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, productoficha);
  }
}
