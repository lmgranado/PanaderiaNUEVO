package panaderia.struts.actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.utilidades.PanaderiaExceptionHandler;


public class InicioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			//Lo primero que hacemos es eliminar una posible sesion existente
			Enumeration enumerador = request.getSession().getAttributeNames();
			while(enumerador.hasMoreElements()){
				String eltoSesion = (String)enumerador.nextElement();
				if(!eltoSesion.equals("org.apache.struts.action.ACTION_MESSAGE")){
					request.getSession().removeAttribute(eltoSesion);
				}
			}

			forward = mapping.findForward("ok");
		
		} catch (Exception ex){
			forward = mapping.findForward("error");
			PanaderiaExceptionHandler.guardaLog(
					  ex,
	        		  mapping,
	        		  request);
		}
		return forward;
	}
}