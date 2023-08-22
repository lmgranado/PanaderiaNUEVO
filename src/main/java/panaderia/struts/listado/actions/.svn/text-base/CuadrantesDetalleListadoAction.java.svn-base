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

import panaderia.beans.Cuadrantes;
import panaderia.beans.CuadrantesDetalle;
import panaderia.beans.ProductosCliente;
import panaderia.negocio.bBotoneraListado;
import utils.Utils;

public class CuadrantesDetalleListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ArrayList listado = new ArrayList();
		CuadrantesDetalle cuadranteDetalle = new CuadrantesDetalle();
		bBotoneraListado botonera = new bBotoneraListado();
		String sinMenu = request.getParameter("sinMenu");
		request.setAttribute("sinMenu", sinMenu );
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        boolean empty = false;
	        String tableName = "LISTACUADRANTESDETALLE"; // Este es el parámetro que se debe poner en la propiedad "name" del DisplayTag.
	        String sortDirection = IPaginatedListTest.ASC;
	        String sortCriterion = "CUDID";//Este es el nombre de la propiedad de la columna por la que se pagina la primera vez
	        int pageSize = 100;
	        	        
	        request.setAttribute("cudCuId", request.getParameter("cudCuId"));
	        Cuadrantes cuadrante = Cuadrantes.getCuadrantesByCuId(request.getParameter("cudCuId"));
	        
	        cuadranteDetalle.setCudCuId(request.getParameter("cudCuId"));
			request.getSession().setAttribute("FCUADRANTESDETALLE", cuadranteDetalle); //El filtro será F+Nombre de la tabla
	        
	        request.setAttribute("LISTAPRODUCTOSCLIENTES",ProductosCliente.getProductosClienteByPclClId(cuadrante.getCuClId())); 
	        
	        PaginatedListTest lista = PaginatedListTest.getPaginatedList(request,
	                empty, tableName, sortDirection, sortCriterion, pageSize); 
	        Object[] consulta = CuadrantesDetalle.getCuadrantesDetalleLista(lista);
	        listado = (ArrayList)consulta[1];
	        //Necesitamos el total de registros que tiene la sql que queremos
	        lista.setFullListSize(Integer.parseInt((String)consulta[0]));        
	        lista.setList(listado);
    		request.setAttribute("LISTACUADRANTESDETALLE", lista);
    		    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		botonera.addBoton(intOrden++, bBotoneraListado.GUARDAR);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		//if( "".equals(Utils.skipNull(sinMenu)) ) 
		botonera.addBoton(intOrden++, bBotoneraListado.VOLVER);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}