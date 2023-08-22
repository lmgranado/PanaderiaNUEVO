package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.FacturasVenta;
import panaderia.beans.HistoricoAc;
import panaderia.beans.HistoricoInventarios;
import panaderia.beans.Usuarios;

public class HistoricoInventariosFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			HistoricoInventarios historicoinventarios = new HistoricoInventarios();
			historicoinventarios.cargaDatosFormularioStruts(request);
			Usuarios usuario = (Usuarios) request.getSession().getAttribute( "usuario" );
			historicoinventarios.setHinvUsId( usuario.getUsId() );
			resultado = historicoinventarios.inserta();
			
			historicoinventarios = HistoricoInventarios.getHistoricoInventariosByHinvId(Integer.toString( resultado ));
			
			request.setAttribute( "historicoinventarios" , historicoinventarios );
			request.setAttribute( "hinvId" , historicoinventarios.getHinvId() );
			
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