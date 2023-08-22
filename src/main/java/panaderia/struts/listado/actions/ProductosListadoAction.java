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
import panaderia.beans.Familias;
import panaderia.beans.Productos;
import panaderia.negocio.bBotoneraListado;

public class ProductosListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		Productos producto = (Productos) form;
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAPRODUCTOS"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "PRODNOMBRE";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FPRODUCTOS", producto); //El filtro será F+Nombre de la tabla
				request.getSession().setAttribute("peso", request.getParameter("peso"));
				request.getSession().setAttribute("precio", request.getParameter("precio"));
			}
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = Productos.getProductosLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAPRODUCTOS", lista);
    		
    		request.setAttribute("LISTAFAMILIAS", Familias.getAllFamilias());
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		//Boton botonImprimirFichas = new Boton("PRODUCTOS LISTADO","Imprimir Fichas","img/imprimirfichas.png","boton", "javascript:doImprimirFichas();");
		//botonera.addBoton(intOrden++, botonImprimirFichas);
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}