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
import panaderia.beans.FacturasVenta;
import panaderia.beans.HistoricoAc;
import panaderia.beans.HistoricoInventarios;
import panaderia.beans.Pestana;
import panaderia.beans.Usuarios;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;

public class HistoricoInventariosListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		HistoricoInventarios historicoinventarios = (HistoricoInventarios) form;
		historicoinventarios.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAHISTORICOINVENTARIOS"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "HINVFECHA";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        request.setAttribute("hinvId", request.getParameter("hinvId"));
	        request.setAttribute( "LISTAUSUARIOS", Usuarios.getAllUsuarios() );
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
	        	request.getSession().setAttribute("FHISTORICO_INVENTARIOS", historicoinventarios); //El filtro será F+Nombre de la tabla
	        }
	        
	        if(request.getSession().getAttribute("FHISTORICO_INVENTARIOS")!=null && !request.getSession().getAttribute("FHISTORICO_INVENTARIOS").equals("")){
		        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = HistoricoInventarios.getHistoricoInventariosLista(lista);
		        listado = (ArrayList)consulta[1];
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTAHISTORICOINVENTARIOS", lista);
	        }		
	        
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		if( listado.size() > 0 ){
			Boton botonImprimir = new Boton("HISTORICO INVENTARIOS","Imprimir historico inventarios","img/imprimir.png","boton", "javascript:doImprimir();");
			botonera.addBoton(intOrden++, botonImprimir);
		}
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		//botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
				
		return forward;
	}
}