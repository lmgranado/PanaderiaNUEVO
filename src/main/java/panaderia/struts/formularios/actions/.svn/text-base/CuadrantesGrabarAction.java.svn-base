package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.Cuadrantes;

public class CuadrantesGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			Cuadrantes cuadrantes = new Cuadrantes();
			if(request.getParameter("delete")==null){
				cuadrantes.cargaDatosFormularioStruts(request);
				resultado = cuadrantes.inserta();
			}else{
				String[] chkValues = request.getParameterValues("checkList");	
				for(int i=0; i<chkValues.length; i++){
					cuadrantes.setCuId(chkValues[i]);
					resultado = cuadrantes.elimina();
				}
			}
				
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	if(request.getParameter("delete")==null)
	        		messages.add("info", new ActionMessage("info.guardar.algunos.duplicado"));
	        	else
	        		messages.add("info", new ActionMessage("info.eliminar.relacionados"));
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