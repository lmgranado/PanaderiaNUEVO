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
import panaderia.beans.Productos;
import panaderia.beans.EntregasDetalle;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bBotoneraListado;

public class EntregasDetalleListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		EntregasDetalle entregasDetalle = new EntregasDetalle();
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAENTREGASDETALLE"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "ENDORDEN";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        request.setAttribute("endEntId", request.getParameter("endEntId"));
	        request.setAttribute("endEntNombre", request.getParameter("endEntNombre"));
	        entregasDetalle.setEndEntId(request.getParameter("endEntId"));
			request.getSession().setAttribute("FENTREGASDETALLE", entregasDetalle); //El filtro será F+Nombre de la tabla
     
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = EntregasDetalle.getEntregasDetalleLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAENTREGASDETALLE", lista);
    		    		
    		//Enviamos lo necesario para los desplegables
    		request.setAttribute("LISTACLIENTES",Clientes.getAllClientes());
    			//Las rutas dependen del cuadrante y el cuadrante depende del cliente
    			//por lo tanto hasta que no se elija el cliente no podemos saber las rutas y cuadrantes
    			//que debemos cargar. Despues una vez elejida la ruta debemos filtrar los cuadrantes
    			//(AJAX)
    		request.setAttribute("LISTARUTAS",new ArrayList());
    		request.setAttribute("LISTACUADRANTES",new ArrayList());
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		Boton boton = bBotoneraFormulario.VOLVER;
		boton.setJavascript("javascript:doVolverListado('doListadoEntregas.do');");
		botonera.addBoton(intOrden++, boton);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}