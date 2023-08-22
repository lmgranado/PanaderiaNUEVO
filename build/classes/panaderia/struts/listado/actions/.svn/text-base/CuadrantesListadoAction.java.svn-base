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
import panaderia.beans.Cuadrantes;
import panaderia.beans.Pestana;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bBotoneraListado;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;

public class CuadrantesListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		Cuadrantes cuadrantes = new Cuadrantes();
		bBotoneraListado botonera = new bBotoneraListado();
		
		String sinMenu = request.getParameter("sinMenu");
		request.setAttribute("sinMenu", sinMenu );
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTACUADRANTES"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "CUNOMBRE";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        String cuClId = "";
	        if(request.getParameter("cuClId")!=null){
	        	request.getSession().setAttribute("cuClId", request.getParameter("cuClId"));
	        	cuClId = request.getParameter("cuClId");
	        }else{
	        	cuClId = (String)request.getSession().getAttribute("cuClId");
	        }
	        
	        request.setAttribute("Cliente", Clientes.getClientesByClId(cuClId));
	        cuadrantes.setCuClId(cuClId);
			request.getSession().setAttribute("FCUADRANTES", cuadrantes); //El filtro será F+Nombre de la tabla
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = Cuadrantes.getCuadrantesLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTACUADRANTES", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		//BOTONES
		if( Utils.empty( sinMenu ) ){
			/* Construcción de la botonera */
			int intOrden = 0;
			botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolverListado('doListadoClientes.do?session=1');");
			botonera.addBoton(intOrden++, boton);
		}else{
			int intOrden = 0;
			botonera.addBoton(intOrden++, bBotoneraFormulario.PASARCUADRANTE);
		}
		
		//PESTANAS
		int intOrden = 0;
		bPestanaFormulario bPestana = new bPestanaFormulario();
		if( Utils.empty( sinMenu ) ){
			bPestana.addPestana(intOrden++, new Pestana("doClientesFormulario.do?clId=" + Utils.skipNull(request.getParameter("cuClId")),"Datos Cliente","","","",""));
			bPestana.addPestana(intOrden++, new Pestana("doListadoProductosCliente.do?pclClId=" + Utils.skipNull(request.getParameter("cuClId")),"Productos","","","",""));
			bPestana.addPestana(intOrden++, new Pestana("doListadoCuadrantes.do?cuClId=" + Utils.skipNull(request.getParameter("cuClId")),"Cuadrantes","","","","1"));
			bPestana.addPestana(intOrden++, new Pestana("doListadoHistoricoAc.do?hacClId=" + Utils.skipNull(request.getParameter("cuClId")),"Historico Ac","","","",""));
			bPestana.addPestana(intOrden++, new Pestana("doListadoVacacionesClientes.do?vacClId=" + Utils.skipNull(request.getParameter("cuClId")),"Vacaciones","","","",""));
		}
		else{
			bPestana.addPestana(intOrden++, new Pestana("doListadoCuadrantes.do?cuClId=" + Utils.skipNull(request.getParameter("cuClId"))+"&sinMenu="+sinMenu,"Cuadrantes","","","","1"));
		}
		
		request.setAttribute("PESTANAS", bPestana.getPestanas());
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}