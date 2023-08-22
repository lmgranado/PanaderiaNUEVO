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
import panaderia.beans.AlbaranesVentaDetalle;
import utils.Utils;

public class AlbaranesVentaBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String avFecha = Utils.skipNull( request.getParameter("avFecha") );
		
		String[] chkValues = request.getParameterValues("checkList");	
		for(int i=0; i<chkValues.length; i++){
			AlbaranesVenta albaran = new AlbaranesVenta();
			albaran.setAvId( chkValues[i] );
			resultado = albaran.elimina();
		}
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		request.setAttribute("avFecha", avFecha);
		
		forward = mapping.findForward("ok");
		if(request.getParameter("simp")!=null){
			forward = mapping.findForward("okSimp");
		}
		
		return forward;
	}
}