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

import panaderia.beans.Clientes;
import panaderia.beans.Localidades;
import panaderia.negocio.bBotoneraListado;
import utils.Utils;

public class ClientesListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		Clientes cliente = (Clientes) form;
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTACLIENTES"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "CLNOMBRE";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        //Eliminamos el filtro del otro listado de clientes
	        request.getSession().removeAttribute("FCLIENTESVN");
	        request.getSession().removeAttribute("FCLIENTESVF");
	        request.getSession().removeAttribute("FCLIENTESVA");
	        
	        //para que mantenga la paginacion
	        if(Utils.skipNull(request.getParameter("session")).equals("1"))
	        	request.setAttribute("session", "1");
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FCLIENTES", cliente); //El filtro será F+Nombre de la tabla
			}
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = Clientes.getClientesLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
	        request.setAttribute("LISTACLIENTES", lista);
	        
    		//Para la ordenacion y las hojas
	        request.getSession().setAttribute("LISTACLIENTES", lista);
	        
    		ArrayList listaLocalidades = Localidades.getAllLocalidades();
    		request.setAttribute("LISTALOCALIDADES", listaLocalidades);
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}