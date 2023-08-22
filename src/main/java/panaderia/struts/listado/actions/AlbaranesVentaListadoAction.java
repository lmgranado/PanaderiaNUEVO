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

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.Localidades;
import panaderia.beans.NotasEntrega;
import panaderia.beans.PeriodosFacturacion;
import panaderia.negocio.bBotoneraListado;

public class AlbaranesVentaListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		AlbaranesVenta albaranes = (AlbaranesVenta) form;
		albaranes.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
		
		request.getSession().removeAttribute( "LISTADETALLEALBARAN" );
		request.getSession().removeAttribute( "LISTADETALLES" );
		request.getSession().removeAttribute( "LISTADETALLESEDICION" );
		request.getSession().removeAttribute( "Albaran" );
		request.getSession().removeAttribute( "avClId" );
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAALBARANESVENTA"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "AVFECHA";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FALBARANES_VENTA", albaranes); //El filtro será F+Nombre de la tabla
			}
	        
	        if(request.getSession().getAttribute("mapaAlbaranes")!=null && !request.getSession().getAttribute("mapaAlbaranes").equals("") &&
	        		request.getSession().getAttribute("FALBARANES_VENTA")!=null && !request.getSession().getAttribute("FALBARANES_VENTA").equals("")){
		        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = AlbaranesVenta.getAlbaranesVentaLista(lista);
		        listado = (ArrayList)consulta[1];
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTAALBARANESVENTA", lista);
	        }
    		
    		//Convertimos el listado en un mapa para el tema de las incidencias y lo metemos en session
    		Map mapa = new Hashtable();
    		for(int i = 0; i<listado.size(); i++){
    			AlbaranesVenta albaran = (AlbaranesVenta)listado.get(i);
    			mapa.put(Integer.toString(i+1), albaran.getAvId());
    		}
    		request.getSession().setAttribute("mapaAlbaranes", mapa);
    		request.getSession().removeAttribute("banderaIncidenciasAlbaranes");
    		    		   		
    		ArrayList clientes = Clientes.getAllClientesActivosEInactivos();
    		request.setAttribute("LISTACLIENTES", clientes);
    		
    		ArrayList listaPeriodosFacturacion = PeriodosFacturacion.getAllPeriodosFacturacion();
			request.setAttribute("LISTAPERIODOS", listaPeriodosFacturacion);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		Boton boton = new Boton("FACTURA","Generar Factuta","img/generarFactura.gif","boton", "javascript:doFactura();");
		botonera.addBoton(intOrden++, boton);
		Boton botonImprimir = new Boton("IMPRIMIR ALBARAN","Imprimir Albarán","img/imprimir.png","boton", "javascript:doImprimirAlbaran();");
		botonera.addBoton(intOrden++, botonImprimir);
		boton = new Boton("INCIDENCIAS","Incidencias en los albaranes","img/incidencias.gif","boton","javascript:doIncidencias();");
		botonera.addBoton(intOrden++, boton);
		//Boton botonEstado = new Boton("ESTADO","Actualizar Estado","img/actualizaEstado.png","boton", "javascript:doActualizaAlbaran();");
		//botonera.addBoton(intOrden++, botonEstado);		
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}