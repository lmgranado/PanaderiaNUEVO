package panaderia.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.Cobradores;
import panaderia.beans.Usuarios;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.Utils;


public class LoginAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		request.getSession().invalidate();
		ActionMessages messages = new ActionMessages();
		Usuarios usuario = (Usuarios) form;
		try{
			String usLogin = Utils.skipNull(request.getParameter("usLogin"));
			String usPassw = Utils.skipNull(request.getParameter("usPassw"));
			usuario = Usuarios.getUsuariosByUsLoginUsPassw( usLogin, usPassw );
			if( usuario != null && !usuario.getUsLogin().equals("") ){
				forward = mapping.findForward("ok");
				request.getSession().setAttribute("usuario", usuario);
			}else{
				//Mensajes
				messages.add("info", new ActionMessage("info.login.usuario"));						
				saveMessages(request.getSession(), messages);	
				forward = mapping.findForward("error");
			}
		
		} catch (Exception ex){
			forward = mapping.findForward("error");
			PanaderiaExceptionHandler.guardaLog(ex,mapping,request);
		}
		return forward;
	}
}