package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.AlbaranesVenta;
import utils.Utils;

public class AlbaranesVentaFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String avId = request.getParameter("avId");
			AlbaranesVenta albaranesventa = new AlbaranesVenta();
			/* Grabaremos el id del cliente junto con los detalles de la albaranesventa en primer lugar */
			if( Utils.skipNull( avId ).equals("") ){
				albaranesventa = new AlbaranesVenta();
				albaranesventa.cargaDatosFormularioStruts( request );
				albaranesventa.setAvCierre("0");
				String numero = Integer.toString(AlbaranesVenta.getSiguienteNumeroAlbaran() + 1);
				albaranesventa.setAvNumero(numero);
				albaranesventa.setAvTipo("1"); //Albaran directo
				resultado = albaranesventa.inserta();
				albaranesventa = AlbaranesVenta.getAlbaranesVentaByAvId(Integer.toString( resultado ));
			}else{
				albaranesventa = new AlbaranesVenta();
				albaranesventa.cargaDatosFormularioStruts( request );
				albaranesventa.setAvCierre("0");
				resultado = albaranesventa.actualiza();
				albaranesventa = AlbaranesVenta.getAlbaranesVentaByAvId(avId);
			}
			
			request.setAttribute( "albaranventa" , albaranesventa );
			request.setAttribute( "avId" , albaranesventa.getAvId() );
			
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