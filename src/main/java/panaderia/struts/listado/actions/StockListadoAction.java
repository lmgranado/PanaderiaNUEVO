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
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasVenta;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Productos;
import panaderia.beans.Proveedores;
import panaderia.beans.Stock;
import panaderia.negocio.bBotoneraListado;


public class StockListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		Stock stock = (Stock) form;
		stock.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
				
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTASTOCK"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.DESC;
	        String sortCriterion = "STID";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        
	        if(request.getParameter("filtro")!=null && !request.getParameter("filtro").equals("")){
				request.getSession().setAttribute("FSTOCK", stock); //El filtro será F+Nombre de la tabla
			}
	        
	        if(request.getSession().getAttribute("FSTOCK")!=null && !request.getSession().getAttribute("FSTOCK").equals("")){
		       
	        	PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
		                empty, tableName, sortDirection, sortCriterion, pageSize); 
		        Object[] consulta = Stock.getStockLista(lista);
		        listado = (ArrayList)consulta[1];
		        
		        //Necesitamos el total de registros que tiene la sql que queremos
		        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
		        lista.setList(listado);
	    		request.setAttribute("LISTASTOCK", lista);
	    		    		 
	        }
    		ArrayList listaproductos = Productos.getAllProductos();
			request.setAttribute("LISTAPRODUCTOS", listaproductos);
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		Boton botonImprimir = new Boton("STOCK POR LOTES","Imprimir Informe de Stock por Lote","img/imprimir.png","boton", "javascript:doImprimirStock();");
		botonera.addBoton(intOrden++, botonImprimir);
				
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}