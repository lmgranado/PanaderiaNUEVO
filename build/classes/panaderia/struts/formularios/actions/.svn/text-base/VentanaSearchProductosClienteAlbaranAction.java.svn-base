package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.IPaginatedListTest;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.Clientes;
import panaderia.beans.Localidades;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class VentanaSearchProductosClienteAlbaranAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ArrayList listado = new ArrayList();
		ProductosCliente productosCliente = new ProductosCliente();
		String avClId = request.getParameter("avClId");
		String avId = request.getParameter("avId");
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAPRODUCTOSCLIENTES"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "PCLPRECIO";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        
	        request.setAttribute("avClId", avClId);
	        request.setAttribute("avId", Utils.skipNull(avId));
	        productosCliente.setPclClId( avClId );
			request.getSession().setAttribute("FPRODUCTOSCLIENTE", productosCliente); //El filtro será F+Nombre de la tabla
	        request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());; 
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = ProductosCliente.getProductosClienteLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAPRODUCTOSCLIENTE", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}