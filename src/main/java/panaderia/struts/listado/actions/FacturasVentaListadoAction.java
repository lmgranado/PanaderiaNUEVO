package panaderia.struts.listado.actions;

import java.sql.Date;
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
import panaderia.beans.Cobradores;
import panaderia.beans.FacturasVenta;
import panaderia.beans.Localidades;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Usuarios;
import panaderia.negocio.bBotoneraListado;





public class FacturasVentaListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		FacturasVenta facturas = (FacturasVenta) form;
		facturas.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
		
		request.getSession().removeAttribute( "LISTADETALLEFACTURA" );
		request.getSession().removeAttribute( "LISTADETALLES" );
		request.getSession().removeAttribute( "LISTADETALLESEDICION" );
		request.getSession().removeAttribute( "Factura" );
		request.getSession().removeAttribute( "fvClId" );
		
		try{
	        // Par�metros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTAFACTURASVENTA"; // Este es el par�metro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "FVFECHA";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FFACTURAS_VENTA", facturas); //El filtro ser� F+Nombre de la tabla
			}
	        
	        if(request.getSession().getAttribute("FFACTURAS_VENTA")!=null && !request.getSession().getAttribute("FFACTURAS_VENTA").equals("")){
		        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = FacturasVenta.getFacturasVentaLista(lista);
		        listado = (ArrayList)consulta[1];
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTAFACTURASVENTA", lista);
	        }
    		    		   		
    		ArrayList clientes = Clientes.getAllClientesActivosEInactivos();
    		request.setAttribute("LISTACLIENTES", clientes);
    		
    		ArrayList cobradores = Cobradores.getAllCobradores();
    		request.setAttribute("LISTACOBRADORES", cobradores);
    		
    		ArrayList listaPeriodosFacturacion = PeriodosFacturacion.getAllPeriodosFacturacion();
			request.setAttribute("LISTAPERIODOS", listaPeriodosFacturacion);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcci�n de la botonera */
		int intOrden = 0;
		//Boton botonImprimirAgrupados = new Boton("FACTURAS AGRUPADAS","Imprimir Facturas Agrupadas","img/imprimir_agru.png","boton", "javascript:doImprimirAgrupadas();");
		//botonera.addBoton(intOrden++, botonImprimirAgrupados);
		Boton botonImprimir = new Boton("FACTURAS VENTA","ver Facturas","img/imprimir.png","boton", "javascript:doVerFactura();");
		botonera.addBoton(intOrden++, botonImprimir);
		//Boton botonImprimirRtf = new Boton("FACTURAS VENTA","Imprimir Factura","img/imprimir.png","boton", "javascript:doImprimirFactura();");
		//botonera.addBoton(intOrden++, botonImprimirRtf);
		
		Usuarios usuario = (Usuarios) request.getSession().getAttribute("usuario");
		if( "1".equals(usuario.getUsAdministrador()) ){
			//Boton botonEstado = new Boton("ESTADO","Actualizar Estado","img/actualizaEstado2.png","boton", "javascript:doActualizaFactura();");
			//botonera.addBoton(intOrden++, botonEstado);	
			//botonEstado = new Boton("CERRAR","Cerrar factura","img/cerrarfactura.png","boton", "javascript:doCerrarFactura();");
			//botonera.addBoton(intOrden++, botonEstado);	
			//botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
			botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		}

		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}