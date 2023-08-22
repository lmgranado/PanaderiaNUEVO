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
import panaderia.beans.FacturasCompra;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Proveedores;
import panaderia.negocio.bBotoneraListado;





public class FacturasFabricacionListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		FacturasCompra facturas = (FacturasCompra) form;
		bBotoneraListado botonera = new bBotoneraListado();
		facturas.cargaDatosFormularioStruts(request);
		
		request.getSession().removeAttribute( "LISTADETALLEFACTURA" );
		request.getSession().removeAttribute( "LISTADETALLES" );
		request.getSession().removeAttribute( "LISTADETALLESEDICION" );
		request.getSession().removeAttribute( "Factura" );
		request.getSession().removeAttribute( "fcClId" );
		
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAFACTURACOMPRA"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "FCFECHA";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FFACTURAS_COMPRA", facturas); //El filtro será F+Nombre de la tabla
			}
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = FacturasCompra.getFacturasFabricacionLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTAFACTURASCOMPRA", lista);
    		    		 
    		ArrayList proveedores = Proveedores.getAllProveedoresFabricacion();
    		request.setAttribute("LISTAPROVEEDORES", proveedores);
    		    		
    		ArrayList listaPeriodosFacturacion = PeriodosFacturacion.getAllPeriodosFacturacion();
			request.setAttribute("LISTAPERIODOS", listaPeriodosFacturacion);
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		//Boton botonImprimirEtiquetas = new Boton("FABRICACION","Imprimir Etiquetas","img/imprimiretiquetas.png","boton", "javascript:doImprimirEtiquetas();");
		//botonera.addBoton(intOrden++, botonImprimirEtiquetas);
		Boton botonImprimir = new Boton("FABRICACION","Imprimir Fabricacion","img/imprimir.png","boton", "javascript:doImprimirFabricacion();");
		botonera.addBoton(intOrden++, botonImprimir);
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}