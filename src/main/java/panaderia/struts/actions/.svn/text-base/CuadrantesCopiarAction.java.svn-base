package panaderia.struts.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.CuadrantesDetalle;

public class CuadrantesCopiarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		ActionMessages messages = new ActionMessages();
		ActionForward forward = null;
		int resultado = -2;
		try{
			//necesitamos el id del cliente y el id del cuadrante que se van a copiar
			//necesitamos el cliente al que se va a copiar y el nombre de la copia
			String clId = "";
			String[] chkValues = request.getParameterValues("checkList");	
			for(int i=0; i<chkValues.length; i++){
				clId = chkValues[i];
			}
			String cuCopia = request.getParameter("cuCopia");
			String obsCopiar = request.getParameter("obsCopiar");
			
			resultado = CuadrantesDetalle.copiaCuadrante(clId, cuCopia, obsCopiar);
    		    		
			/*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.copiar.no.cuadrante"));
	    		saveMessages(request.getSession(), messages);
	        }else if(resultado>0){
	        	//mensaje y mapeo de guardado o editado correcto
	        	messages.add("info", new ActionMessage("info.copiar.cuadrante"));						
	    		saveMessages(request.getSession(), messages);
	        }
	        /**/
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
		}
		
		
				
		return forward;
	}
}