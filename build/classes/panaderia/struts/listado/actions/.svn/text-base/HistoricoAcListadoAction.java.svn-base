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
import panaderia.beans.Pestana;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;

public class HistoricoAcListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		HistoricoAc historicoac = new HistoricoAc();
		bBotoneraListado botonera = new bBotoneraListado();
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAHISTORICOAC"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "HACFECHAENTREGA";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        request.setAttribute("hacClId", request.getParameter("hacClId"));
	        request.setAttribute("Cliente", Clientes.getClientesByClId(request.getParameter("hacClId")));
	        request.setAttribute("sumaImportesFacturas", FacturasVenta.getImportesPendientesByFvClId( request.getParameter("hacClId") ) );
	        historicoac.setHacClId(request.getParameter("hacClId"));
	        
			request.getSession().setAttribute("FHISTORICOAC", historicoac); //El filtro será F+Nombre de la tabla
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = HistoricoAc.getHistoricoAcLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAHISTORICOAC", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		if( listado.size() > 0 ){
			Boton botonImprimir = new Boton("HISTORICO AC","Imprimir historico AC","img/imprimir.png","boton", "javascript:doImprimir();");
			botonera.addBoton(intOrden++, botonImprimir);
		}
		Boton boton = bBotoneraFormulario.VOLVER;
		boton.setJavascript("javascript:doVolverListado('doListadoClientes.do?session=1');");
		botonera.addBoton(intOrden++, boton);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		/* Construcción de las pestañas */
		intOrden = 0;
		bPestanaFormulario bPestana = new bPestanaFormulario();
		bPestana.addPestana(intOrden++, new Pestana("doClientesFormulario.do?clId=" + Utils.skipNull(request.getParameter("hacClId")),"Datos Cliente","","","",""));
		bPestana.addPestana(intOrden++, new Pestana("doListadoProductosCliente.do?pclClId=" + Utils.skipNull(request.getParameter("hacClId")),"Productos","","","",""));
		bPestana.addPestana(intOrden++, new Pestana("doListadoCuadrantes.do?cuClId=" + Utils.skipNull(request.getParameter("hacClId")),"Cuadrantes","","","",""));
		bPestana.addPestana(intOrden++, new Pestana("doListadoHistoricoAc.do?hacClId=" + Utils.skipNull(request.getParameter("hacClId")),"Historico Ac","","","","1"));
		bPestana.addPestana(intOrden++, new Pestana("doListadoVacacionesClientes.do?vacClId=" + Utils.skipNull(request.getParameter("hacClId")),"Vacaciones","","","",""));
		request.setAttribute("PESTANAS", bPestana.getPestanas());
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		
		return forward;
	}
}