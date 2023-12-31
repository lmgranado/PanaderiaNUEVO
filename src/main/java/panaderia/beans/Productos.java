package panaderia.beans;
import java.sql.ResultSet;

import org.displaytag.pagination.PaginatedListTest;

import panaderia.struts.forms.EntidadBean;
import utils.UtilesDAO;
import utils.Utils;

import java.util.ArrayList;
public class Productos extends panaderia.beans.entidad.ProductosEntidad
{
  private static final long serialVersionUID = 1L;
  
  public Productos(){ super(); }

  
  public static ArrayList getListaProductosByProdFamId(String prodFamId)
  {
    Productos elto = new Productos();
    ArrayList lista =  elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos WHERE prod_fam_id='"+prodFamId+"'");
    return lista;
  }
  
  //LUIS MIGUEL 03/07/14
  public static ArrayList getPrecioProductoByProdIdCodigo(String prodId)
  {
	  Productos elto = new Productos();
    ArrayList listado =  elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos WHERE prod_id='"+prodId+"'");
   
    return listado;
  }
  
  public static Productos getProductosByProdFamId(String prodFamId)
  {
    Productos elto = new Productos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM productos WHERE prod_fam_id='"+prodFamId+"'");
    return elto;
  }

  public static Productos getProductosByProdId(String prodId)
  {
    Productos elto = new Productos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM productos WHERE prod_id='"+prodId+"'");
    return elto;
  }

  public static Productos getProductosByProdReferencia(String prodReferencia)
  {
    Productos elto = new Productos();
    elto.consultaReflexiva("SELECT "+ALL_COLUMNS+" FROM productos WHERE prod_referencia='"+prodReferencia+"'");
    return elto;
  }

  public static ArrayList getAllProductos()
  {
    Productos elto = new Productos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos order by prod_nombre");
    return lista;
  }
  
  public static ArrayList getAllProductosCompra()
  {
    Productos elto = new Productos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos where prod_de_venta = '0' order by prod_nombre");
    return lista;
  }
  
  public static ArrayList getAllProductosVenta()
  {
    Productos elto = new Productos();
    ArrayList lista = elto.consultaAVectorReflexiva("SELECT "+ALL_COLUMNS+" FROM productos where prod_de_venta = '1' order by prod_nombre");
    return lista;
  }
  
  public static ArrayList getAllProductosConFamilia(String fam)
  {
	String sqladd = "";
	if(!fam.equals(""))
		sqladd = " where fam_id = '" + fam + "'";
    ArrayList lista = EntidadBean.consulta(" select prod_referencia, prod_nombre, prod_peso_masa, prod_peso_total, fam_nombre, prod_id " +
										   " from productos " +
    									   " join familias ON productos.prod_fam_id = familias.fam_id " + sqladd);
    return lista;
  }
  
  public static ArrayList getAllProductosConFamiliaAndMedia(String fam)
  {
	String sqladd = "";
	if(!fam.equals(""))
		sqladd = " where fam_id = '" + fam + "'";
    ArrayList lista = EntidadBean.consulta( "select prod_referencia, prod_nombre, prod_peso_masa, prod_peso_total, fam_nombre, round( sum(pcl_precio)/count(1),3) as media " +
											"	 from productos  " +
											"	 left join productos_cliente on productos_cliente.pcl_prod_id = productos.prod_id " +
										    " join familias ON productos.prod_fam_id = familias.fam_id  " + sqladd +
											"	 group by prod_id, prod_referencia, prod_nombre, prod_peso_masa, prod_peso_total, fam_nombre ");
    return lista;
  }
  
  public static ArrayList getAllProductosPrecios(String tipo, String cliente, String familia, String producto)
  {
	String sqladd = "";
	String campos = " '','' ";
	String order = "";
	String sqlFamilia = "";
	String sqlProducto = "";
	if(tipo.equals("cliente")){
		sqladd = "left join productos_cliente on productos_cliente.pcl_prod_id = productos.prod_id " +
				 "left join clientes on productos_cliente.pcl_cl_id = clientes.cl_id  where 1=1 ";
		campos = " cl_nombre_comercial, pcl_precio ";
		order = "cl_nombre_comercial, ";
		if(!cliente.equals(""))
			sqladd += " and cl_id = " + cliente;
	}
	if(!Utils.skipNull(familia).equals("")){
		sqlFamilia = "and familias.fam_id = " + familia;
	}
	if(!Utils.skipNull(producto).equals("")){
		sqlProducto = " and productos.prod_id = " + producto + " " ;
	}
    ArrayList lista = EntidadBean.consulta("select prod_referencia, prod_nombre, fam_nombre, prod_precio_general, " + campos + " from productos " +
								    		"join familias on productos.prod_fam_id = familias.fam_id " + sqlFamilia + " " +
								    		sqladd + 
								    		sqlProducto +
								    		" order by " + order + " fam_nombre, prod_referencia");
    return lista;
  }

  protected Object rsToBean(ResultSet rs) throws Exception 
  {
    Productos productos = new Productos();
  	try{
    	productos.setProdCantidadPorCaja(rs.getString(PRODCANTIDADPORCAJA));
    	productos.setProdDescuento(rs.getString(PRODDESCUENTO));
    	productos.setProdFamId(rs.getString(PRODFAMID));
    	productos.setProdId(rs.getString(PRODID));
    	productos.setProdIva(rs.getString(PRODIVA));
    	productos.setProdNombre(rs.getString(PRODNOMBRE));
    	productos.setProdPesoTotal(rs.getString(PRODPESOTOTAL));
    	productos.setProdPesoMasa(rs.getString(PRODPESOMASA));
    	productos.setProdPrecioGeneral(rs.getString(PRODPRECIOGENERAL));
    	productos.setProdReferencia(rs.getString(PRODREFERENCIA));
    	productos.setProdStock(rs.getString(PRODSTOCK));
    	productos.setProdStockMinimo(rs.getString(PRODSTOCKMINIMO));
    	productos.setProdDeVenta(PRODDEVENTA);
  	}catch(Exception e){
  		throw e;
  	}
    	return productos;
  }

  public static Object[] getProductosLista(PaginatedListTest listaPaginada) 
  {
	String sqlA�adido = " ";
	Productos productos = new Productos();
	String controlaCantidad = "";
	String sql = "SELECT * FROM productos " +
				 " WHERE 1 = 1 ";
  	try{
		Productos productosFiltro = new Productos();
		if(listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOS")!=null){
			productosFiltro = (Productos)listaPaginada.getRequest().getSession().getAttribute("FPRODUCTOS");
		}
    	if(!Utils.empty(productosFiltro.getProdCantidadPorCaja()))
    		sqlA�adido += "AND " + PRODCANTIDADPORCAJA + " = '" + productosFiltro.getProdCantidadPorCaja() + "'";
    	if(!Utils.empty(productosFiltro.getProdDescuento()))
    		sqlA�adido += "AND " + PRODDESCUENTO + " = '" + productosFiltro.getProdDescuento() + "'";
    	if(!Utils.empty(productosFiltro.getProdFamId()))
    		sqlA�adido += "AND " + PRODFAMID + " = '" + productosFiltro.getProdFamId() + "'";
    	if(!Utils.empty(productosFiltro.getProdId()))
    		sqlA�adido += "AND " + PRODID + " = '" + productosFiltro.getProdId() + "'";
    	if(!Utils.empty(productosFiltro.getProdIva()))
    		sqlA�adido += "AND " + PRODIVA + " = '" + productosFiltro.getProdIva() + "'";
    	if(!Utils.empty(productosFiltro.getProdNombre()))
    		sqlA�adido += "AND UPPER(" + PRODNOMBRE + ") like UPPER('%" + productosFiltro.getProdNombre() + "%')";
    	if(!Utils.empty(productosFiltro.getProdPesoTotal())){
    		controlaCantidad = (String)listaPaginada.getRequest().getSession().getAttribute("peso");
    		if(controlaCantidad.equals("mayor")) controlaCantidad = ">"; else controlaCantidad = "<=";
    		sqlA�adido += "AND " + PRODPESOTOTAL + " " + controlaCantidad + " '" + productosFiltro.getProdPesoTotal() + "' ";
    	}
    	if(!Utils.empty(productosFiltro.getProdPrecioGeneral())){
    		controlaCantidad = (String)listaPaginada.getRequest().getSession().getAttribute("precio");
    		if(controlaCantidad.equals("mayor")) controlaCantidad = ">"; else controlaCantidad = "<=";
    		sqlA�adido += "AND " + PRODPRECIOGENERAL + " " + controlaCantidad + " '" + productosFiltro.getProdPrecioGeneral() + "' ";
    	}
    	if(!Utils.empty(productosFiltro.getProdReferencia()))
    		sqlA�adido += "AND UPPER(" + PRODREFERENCIA + ") like UPPER('%" + productosFiltro.getProdReferencia() + "%')";
    	if(!Utils.empty(productosFiltro.getProdStock()))
    		sqlA�adido += "AND " + PRODSTOCK + " = '" + productosFiltro.getProdStock() + "'";
    	if(!Utils.empty(productosFiltro.getProdStockMinimo()))
    		sqlA�adido += "AND " + PRODSTOCKMINIMO + " = '" + productosFiltro.getProdStockMinimo() + "'";
  	}catch(Exception e){
  		System.out.println(e);
  	}
    return UtilesDAO.generaSqlListadoPaginado(sql + sqlA�adido, listaPaginada, productos);
  }
}
