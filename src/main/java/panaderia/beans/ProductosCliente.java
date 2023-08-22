package panaderia.beans;
import java.sql.ResultSet;
import org.displaytag.pagination.PaginatedListTest;
import utils.UtilesDAO;
import utils.Utils;
import java.util.ArrayList;
public class ProductosCliente extends panaderia.beans.entidad.ProductosClienteEntidad
{
  private static final long serialVersionUID = 1L;
  
  public ProductosCliente(){ super(); }

  public static ArrayList getProductosClienteByPclClId(String pclClId)
  {
	ProductosCliente elto = new ProductosCliente();
	ArrayList lista = elto.consultaAVectorReflexivaRsToBean("SELECT productos_cliente.*, productos.prod_nombre " +
													 "FROM productos_cliente " +
													 "JOIN productos ON productos.prod_id = productos_cliente.pcl_prod_id " +
													 "WHERE pcl_cl_id='"+pclClId+"'");
	
    return lista;
  }

  public static ProductosCliente getProductosClienteByPclId(String pclId)
  {
    ProductosCliente elto = new ProductosCliente();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM productos_cliente WHERE pcl_id='"+pclId+"'");
    return elto;
  }

  public static ProductosCliente getProductosClienteByPclProdId(String pclProdId)
  {
    ProductosCliente elto = new ProductosCliente();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM productos_cliente WHERE pcl_prod_id='"+pclProdId+"'");
    return elto;
  }

  public static ArrayList getAllProductosCliente()
  {
    ProductosCliente elto = new ProductosCliente();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos_cliente");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    ProductosCliente productoscliente = new ProductosCliente();
  	try{
    	productoscliente.setPclClId(rs.getString(PCLCLID));
    	productoscliente.setPclId(rs.getString(PCLID));
    	productoscliente.setPclPrecio(rs.getString(PCLPRECIO));
    	productoscliente.setPclProdId(rs.getString(PCLPRODID));
    	//Parte de las tablas relacionadas
    	String[] clDatosRelacionados = new String[5];
    	clDatosRelacionados[0] = rs.getString("prod_nombre");
    	productoscliente.setClDatosRelacionados(clDatosRelacionados);
  	}catch(Exception e){
  		throw e;
  	}
    	return productoscliente;
  }

  public static Object[] getProductosClienteLista(PaginatedListTest listaPaginada) 
  {
	String sqlAñadido = " ";
	ProductosCliente productoscliente = new ProductosCliente();
	String sql = "SELECT productos_cliente.*, productos.prod_nombre " +
				 "FROM productos_cliente " +
				 "JOIN productos ON productos.prod_id = productos_cliente.pcl_prod_id " +
				 "WHERE 1 = 1 ";
  	try{
		ProductosCliente productosclienteFiltro = new ProductosCliente();
		if(listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOSCLIENTE")!=null){
			productosclienteFiltro = (ProductosCliente)listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOSCLIENTE");
		}
    	if(!Utils.empty(productosclienteFiltro.getPclClId()))
    		sqlAñadido += "AND " + PCLCLID + " = '" + productosclienteFiltro.getPclClId() + "'";
    	if(!Utils.empty(productosclienteFiltro.getPclId()))
    		sqlAñadido += "AND " + PCLID + " = '" + productosclienteFiltro.getPclId() + "'";
    	if(!Utils.empty(productosclienteFiltro.getPclPrecio()))
    		sqlAñadido += "AND " + PCLPRECIO + " = '" + productosclienteFiltro.getPclPrecio() + "'";
    	if(!Utils.empty(productosclienteFiltro.getPclProdId()))
    		sqlAñadido += "AND " + PCLPRODID + " = '" + productosclienteFiltro.getPclProdId() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlAñadido, listaPaginada, productoscliente);
  }
}
