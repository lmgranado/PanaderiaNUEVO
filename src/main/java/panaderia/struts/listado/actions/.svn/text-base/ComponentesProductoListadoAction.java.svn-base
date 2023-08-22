package panaderia.struts.listado.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.displaytag.pagination.IPaginatedListTest;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.ComponentesProducto;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;

public class ComponentesProductoListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		ComponentesProducto componentesProducto = new ComponentesProducto();
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTACOMPONENTESPRODUCTO"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "CPID";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        String cpProdIdFabricado = request.getParameter("cpProdIdFabricado");
	        request.setAttribute("cpProdIdFabricado", cpProdIdFabricado );
	        
			Productos producto = Productos.getProductosByProdId(cpProdIdFabricado);
			request.setAttribute("Producto", producto);	        	    
	        
			componentesProducto.setCpProdIdFabricado(cpProdIdFabricado);
			request.getSession().setAttribute("FCOMPONENTESPRODUCTO", componentesProducto); //El filtro será F+Nombre de la tabla
	        
	        request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());; 
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = ComponentesProducto.getComponentesProductoLista(lista);
	        listado = (ArrayList)consulta[1];
	        
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
	        
	        
    		request.setAttribute("LISTACOMPONENTESPRODUCTO", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construccion de la botonera*/
		int intOrdenBot = 0;
		botonera.addBoton(intOrdenBot++, bBotoneraListado.BORRAR);
		Boton boton = bBotoneraFormulario.VOLVER;
		boton.setJavascript("javascript:doVolverListado('doListadoProductos.do');");
		botonera.addBoton(intOrdenBot++, boton);
		request.setAttribute("BOTONERA", botonera.getBotones());
					
		/* Construcción de las pestañas */
		int intOrden = 0;
		bPestanaFormulario bPestana = new bPestanaFormulario();
		bPestana.addPestana(intOrden++, new Pestana("doProductosFormulario.do?prodId=" + Utils.skipNull(request.getParameter("cpProdIdFabricado")),"Productos","","","",""));
		if( !Utils.empty(request.getParameter("cpProdIdFabricado")) ){
			bPestana.addPestana(intOrden++, new Pestana("doListadoComponentesProducto.do?cpProdIdFabricado=" + Utils.skipNull(request.getParameter("cpProdIdFabricado")),"Componentes","","","","1"));
			bPestana.addPestana(intOrden++, new Pestana("doProductoFichaFormulario.do?prodfProdId=" + Utils.skipNull(request.getParameter("cpProdIdFabricado")),"Ficha","","","",""));
			
		}
		request.setAttribute("PESTANAS", bPestana.getPestanas());
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}