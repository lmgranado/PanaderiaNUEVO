package panaderia.struts.listado.actions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

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
import panaderia.beans.Entregas;
import panaderia.beans.NotasEntrega;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraListado;
import utils.Utils;

public class NotasEntregaListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		NotasEntrega notas = (NotasEntrega) form;
		notas.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
		
		request.getSession().removeAttribute( "LISTADETALLENOTAENTREGA" );
		request.getSession().removeAttribute( "LISTADETALLES" );
		request.getSession().removeAttribute( "LISTADETALLESEDICION" );
		request.getSession().removeAttribute( "notaentrega" );
		request.getSession().removeAttribute( "notClId" );
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTANOTASENTREGA"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "NOTFECHA,NOTORDEN";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        //para que mantenga la paginacion
	        if(Utils.skipNull(request.getParameter("session")).equals("1"))
	        	request.setAttribute("session", "1");
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FNOTASENTREGA", notas); //El filtro será F+Nombre de la tabla
			}
	        
	        if(request.getSession().getAttribute("FNOTASENTREGA")!=null && !request.getSession().getAttribute("FNOTASENTREGA").equals("")){
		        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = NotasEntrega.getNotasEntregaLista(lista);
		        listado = (ArrayList)consulta[1];
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTANOTASENTREGA", lista);
	    		
	    		
	    		//Para la ordenacion y las hojas
		        request.getSession().setAttribute("LISTANOTASENTREGA", lista);
	        }
    		
    		//Convertimos el listado en un mapa para el tema de las incidencias y lo metemos en session
    		Map mapa = new Hashtable();
    		for(int i = 0; i<listado.size(); i++){
    			NotasEntrega nota = (NotasEntrega)listado.get(i);
    			mapa.put(Integer.toString(i+1), nota.getNotId());
    		}
    		request.getSession().setAttribute("mapaNotas", mapa);
    		request.getSession().removeAttribute("banderaIncidenciasNotas");
    		
    		request.setAttribute("CLIENTESLISTA", Clientes.getAllClientesActivosEInactivos());
    		request.setAttribute("RUTASLISTA", Rutas.getAllRutas());
    		//request.setAttribute("LISTANOMBRESENTREGA", NotasEntrega.getAllNotasEntregaDistict());
    		request.setAttribute("LISTAENTREGAS",Entregas.getAllEntregas());
    		
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		Boton boton = new Boton("ALBARAN","Generar Albaran","img/generarAlbaran.gif","boton", "javascript:doAlbaran();");
		botonera.addBoton(intOrden++, boton);
		boton = new Boton("CERRARDIA","Cerrar Dia","img/cerrardia.png","boton", "javascript:doCerrarDia();");
		botonera.addBoton(intOrden++, boton);
		boton = new Boton("NOTAS","Generar Notas","img/imprimir.png","boton", "javascript:doNotasMasivo();");
		botonera.addBoton(intOrden++, boton);
		boton = new Boton("INCIDENCIAS","Incidencias en las notas","img/incidencias.gif","boton","javascript:doIncidencias();");
		botonera.addBoton(intOrden++, boton);
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}