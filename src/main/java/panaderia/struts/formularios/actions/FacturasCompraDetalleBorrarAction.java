package panaderia.struts.formularios.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.ComponentesProducto;
import panaderia.beans.ComposicionFabricacion;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;

public class FacturasCompraDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		
		String[] chkValues = request.getParameterValues("checkList");
		String fcId = Utils.skipNull(request.getParameter("fcId"));
		
		FacturasCompra facturacompra = FacturasCompra.getFacturasCompraByFcId(fcId);
		
		FacturasCompraDetalle detalle = FacturasCompraDetalle.getFacturasCompraDetalleByFcdId( chkValues[0] );
		ArrayList listaDetalles = new ArrayList();
		listaDetalles.add(detalle);
		
		resultado = FacturasCompraDetalle.eliminaDetalleLista( listaDetalles );
		
		//Si se ha eliminado bien el producto, entonces recalculo los importes
		if(resultado>0){
			String total = FacturasCompra.getImporteTotalByFcId(fcId);
			facturacompra.setFcTotal( total );
			facturacompra.actualiza();
		}
		
		request.setAttribute("facturacompra", FacturasCompra.getFacturasCompraByFcId(request.getParameter("fcId")));
		
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
    		
    		//Se intenta eliminar un producto cuyas salidas se convierten en negativa al tener un abono mayor
		}else if(resultado == -3){
            	//mensaje y mapeo de guardado o editado correcto
    			messages.add("info", new ActionMessage("info.eliminar.producto.compra"));				
        		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}