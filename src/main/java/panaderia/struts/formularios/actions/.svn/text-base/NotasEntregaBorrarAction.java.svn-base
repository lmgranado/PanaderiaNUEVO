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

public class NotasEntregaBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		
		String[] chkValues = request.getParameterValues("checkList");	
		for(int i=0; i<chkValues.length; i++){
			NotasEntrega nota = new NotasEntrega();
			nota.setNotId(chkValues[i]);
			nota = NotasEntrega.getNotasEntregaByNotId( nota.getNotId() );
			resultado = nota.elimina();
			//Si se ha eliminado correctamente, recalculamos los ordenes
			if( resultado>0 )
				NotasEntrega.recalculaOrdenes( nota, false );
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
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}