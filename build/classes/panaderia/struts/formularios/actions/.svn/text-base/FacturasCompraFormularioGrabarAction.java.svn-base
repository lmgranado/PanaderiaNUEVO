package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.FacturasCompra;
import utils.Utils;

public class FacturasCompraFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String fcId = request.getParameter("fcId");
			FacturasCompra facturacompra = new FacturasCompra();
			/* Grabaremos el id del cliente junto con los detalles de la facturacompra en primer lugar */
			if( Utils.skipNull( fcId ).equals("") ){
				facturacompra = new FacturasCompra();
				facturacompra.cargaDatosFormularioStruts( request );
				facturacompra.setFcCierre("0");
				resultado = facturacompra.inserta();
				facturacompra = FacturasCompra.getFacturasCompraByFcId(Integer.toString( resultado ));
			}else{
				facturacompra = new FacturasCompra();
				facturacompra.cargaDatosFormularioStruts( request );
				facturacompra.setFcCierre("0");
				resultado = facturacompra.actualiza();
				facturacompra = FacturasCompra.getFacturasCompraByFcId(fcId);
			}
			
			request.setAttribute( "facturacompra" , facturacompra );
			request.setAttribute( "fcId" , facturacompra.getFcId() );
			
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.guardar.algunos"));						
	    		saveMessages(request.getSession(), messages);
	        }else if(resultado>0){
	        	//mensaje y mapeo de guardado o editado correcto
	        	messages.add("info", new ActionMessage("info.guardar.ok"));						
	    		saveMessages(request.getSession(), messages);

	        }
	        /**/
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}