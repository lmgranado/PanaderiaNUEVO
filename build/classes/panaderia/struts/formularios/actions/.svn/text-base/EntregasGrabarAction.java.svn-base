package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.Entregas;
import panaderia.beans.EntregasDetalle;

public class EntregasGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			Entregas entregas = new Entregas();
			
			if(request.getParameter("delete")==null){
				entregas.cargaDatosFormularioStruts(request);
				resultado = entregas.inserta();
			}else{
				String[] chkValues = request.getParameterValues("checkList");	
				for(int i=0; i<chkValues.length; i++){
					resultado = EntregasDetalle.eliminaDetalles( chkValues[i] ); 
					if( resultado != -1 ){
						//Eliminamos el padre
						entregas.setEntId(chkValues[i]);
						resultado = entregas.elimina();
					}
				}
			}
				
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.guardar.algunos.duplicado"));						
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