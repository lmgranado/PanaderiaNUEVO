package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.Clientes;
import panaderia.struts.forms.EntidadBean;
import utils.Utils;

public class ClientesFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String clId = request.getParameter("clId");
			Clientes cliente = Clientes.getClientesByClId(clId);
			cliente.cargaDatosFormularioStruts(request);
			
			if( request.getParameter("clIdEmpresa") == null )
				cliente.setClIdEmpresa( "0" );
			
			//Verificamos el formato del descuento
			float clDescuento = Float.parseFloat(EntidadBean.quitar_comas(Utils.skipNullNumero(cliente.getClDescuento())));
			if(clDescuento>0)
				clDescuento = clDescuento/100;
			
			cliente.setClDescuento(EntidadBean.quitar_comas_invertido(Float.toString(clDescuento)));
			
			//Verificamos el formato de la proporcion
			float clProporcionIva = Float.parseFloat(EntidadBean.quitar_comas(Utils.skipNullNumero(cliente.getClProporcionIva())));
			if(clProporcionIva>0)
				clProporcionIva = clProporcionIva/100;
			
			cliente.setClProporcionIva(EntidadBean.quitar_comas_invertido(Float.toString(clProporcionIva)));
			
			
			if(Utils.skipNull(clId).equals(""))
				resultado = cliente.inserta();
			else
				resultado = cliente.actualiza();
				
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