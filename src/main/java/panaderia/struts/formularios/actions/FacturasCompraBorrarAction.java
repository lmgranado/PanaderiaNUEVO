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

import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;

public class FacturasCompraBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String[] chkValues = request.getParameterValues("checkList");	
		
	  	//Aunque haya un bucle, a traves del facturas_compra.jsp solo se deja eliminar una factura a la vez	
		for(int i=0; i<chkValues.length; i++){
			
			FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId(chkValues[i]);
			
			//Eliminamos todos los detalles de la factura
			ArrayList listaDetalles = FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId(chkValues[i]);
			resultado = FacturasCompraDetalle.eliminaDetalleLista( listaDetalles );
			
			//Si no ha ocurrido ningun error resultado != -1 && resultado != -3 m�s cantidad de la que ya se ha usado
			if( resultado != -1 && resultado != -3){
				//Eliminamos el padre
				resultado = factura.elimina();
			}
		}
			
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
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
			if(request.getParameter("simp")!=null){
				forward = mapping.findForward("okSimp");
		}
		
		return forward;
	}
}