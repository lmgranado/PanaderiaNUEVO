package panaderia.struts.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.IPaginatedListTest;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.ActualizadorPrecios;
import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.Localidades;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraListado;
import utils.Utils;

public class ActualizadorPreciosAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              

		bBotoneraListado botonera = new bBotoneraListado();
			
		try{
	        // Par�metros por defecto para la primera vez que se muestra la lista.
	        
    		request.setAttribute("CLIENTESLISTA", Clientes.getAllClientesActivosEInactivos());
    		request.setAttribute("RUTASLISTA", Rutas.getAllRutas());
    		request.setAttribute("LOCALIDADESLISTA", Localidades.getAllLocalidades());

    		if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
    			ArrayList listado = new ArrayList();
    			// Par�metros por defecto para la primera vez que se muestra la lista.
		        boolean empty = false;
		        String tableName = "LISTAACTUALIZADORPRECIOS"; // Este es el par�metro que se debe poner en la propiedad "name" del DisplayTag.
		        String sortDirection = IPaginatedListTest.ASC;
		        String sortCriterion = "PRODUCTO";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
		        int pageSize = 100;
		        
		        request.setAttribute("clId", Utils.skipNull(request.getParameter("clId")));
		        request.setAttribute("rutId", Utils.skipNull(request.getParameter("rutId")));
		        request.setAttribute("locId", Utils.skipNull(request.getParameter("locId")));
		        request.setAttribute("actTipo", Utils.skipNull(request.getParameter("actTipo")));
		        request.setAttribute("actCantidad", Utils.skipNull(request.getParameter("actCantidad")));
		        	        
		        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = ActualizadorPrecios.getActualizadorLista(lista);
		        listado = (ArrayList)consulta[1];
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTAACTUALIZADORPRECIOS", lista);
    		}
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcci�n de la botonera */
		int intOrden = 0;
		Boton boton = new Boton("ACTUALIZAR","Actualizar Precios","img/actualizaPrecios.png","boton", "javascript:doActualizar();");
		botonera.addBoton(intOrden++, boton);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		
		return forward;
	}
}