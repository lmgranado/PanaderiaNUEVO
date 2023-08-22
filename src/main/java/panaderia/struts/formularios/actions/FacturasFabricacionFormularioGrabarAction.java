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

public class FacturasFabricacionFormularioGrabarAction extends Action {
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
			facturacompra.cargaDatosFormularioStruts( request );
			facturacompra.setFcCierre("0");
			
			/* Grabaremos el id del cliente junto con los detalles de la facturacompra en primer lugar */
			if( Utils.skipNull( fcId ).equals("") ){
				//Comprobamos si existe el numero de fabricacion ya en la tabla de base de datos
				if( !FacturasCompra.existeFacturaByFcNumeroFactura( facturacompra.getFcNumeroFactura() ) ){
					resultado = facturacompra.inserta();
					facturacompra = FacturasCompra.getFacturasCompraByFcId(Integer.toString( resultado ));
				}
			}else{
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
	        }else if(resultado == -2){
	        	//mensaje y mapeo de guardado o editado correcto
	        	messages.add("info", new ActionMessage("info.existe.numerofactura.fabricacion"));						
	    		saveMessages(request.getSession(), messages);
	        }
	        else if(resultado>0){
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