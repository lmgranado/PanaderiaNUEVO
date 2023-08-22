package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Productos;
import utils.Utils;

public class FacturasFabricacionDetalleGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		
		String fcId = Utils.skipNull(request.getParameter("fcId"));
		FacturasCompra facturacompra = FacturasCompra.getFacturasCompraByFcId(fcId);
		
		//Est clase de inicio no tiene uso
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId(facturacompra.getFcId()) );		
		
		request.setAttribute("fcId", fcId);
		request.setAttribute("fcPrId", facturacompra.getFcPrId());
		
		//Metemos en la variable la lista de productos
		ArrayList listaProductos = Productos.getAllProductosVenta();
		request.setAttribute("LISTAPRODUCTOS", listaProductos);
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.guardar.algunos"));						
    		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.guardar.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}