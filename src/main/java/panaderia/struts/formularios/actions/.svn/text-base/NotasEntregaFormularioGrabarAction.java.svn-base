package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.NotasEntrega;
import utils.Utils;

public class NotasEntregaFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String notId = request.getParameter("notId");
			NotasEntrega notaentrega = new NotasEntrega();
			/* Grabaremos el id del cliente junto con los detalles de la notaentrega en primer lugar */
			if( Utils.skipNull( notId ).equals("") ){
				notaentrega = new NotasEntrega();
				notaentrega.cargaDatosFormularioStruts( request );
				notaentrega.setNotCierre("0");
				notaentrega.setNotCuId(null);
				//Recarculamos los ordenes antes de insertar el nuevo, siempe que exista dicho orden
				NotasEntrega.recalculaOrdenes( notaentrega, true );
				resultado = notaentrega.inserta();
				notaentrega = NotasEntrega.getNotasEntregaByNotId(Integer.toString( resultado ));
			}else{
				notaentrega = new NotasEntrega();
				notaentrega.cargaDatosFormularioStruts( request );
				notaentrega.setNotCierre("0");
				resultado = notaentrega.actualiza();
				notaentrega = NotasEntrega.getNotasEntregaByNotId(notId);
			}
			
			request.setAttribute( "notaentrega" , notaentrega );
			request.setAttribute( "notId" , notaentrega.getNotId() );
			
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