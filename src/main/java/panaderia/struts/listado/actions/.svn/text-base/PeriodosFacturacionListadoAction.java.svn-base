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

import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;

public class PeriodosFacturacionListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		PeriodosFacturacion periodos = new PeriodosFacturacion();
		bBotoneraListado botonera = new bBotoneraListado();
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAPERIODOSFACTURACION"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "PFDESCRIPCION";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        request.setAttribute("pfId", request.getParameter("pfId"));
	        periodos.setPfId( request.getParameter("pfId") );
			request.getSession().setAttribute("FPERIODOSFACTURACION", periodos); //El filtro será F+Nombre de la tabla
	        
	        request.setAttribute("LISTAPERIODOSFACTURACION", PeriodosFacturacion.getAllPeriodosFacturacion() ); 
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = PeriodosFacturacion.getPeriodosFacturacionLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAPERIODOSFACTURACION", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}
