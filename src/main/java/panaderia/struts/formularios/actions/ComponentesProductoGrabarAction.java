package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.ComponentesProducto;
import panaderia.beans.ProductosCliente;
import panaderia.beans.Rutas;

public class ComponentesProductoGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			ComponentesProducto componentesproductos = new ComponentesProducto();
			
			if(request.getParameter("delete")==null){
				componentesproductos.cargaDatosFormularioStruts(request);
				resultado = componentesproductos.inserta();
			}else{
				String[] chkValues = request.getParameterValues("checkList");	
				for(int i=0; i<chkValues.length; i++){
					componentesproductos.setCpId( chkValues[i] );
					resultado = componentesproductos.elimina();
				}
			}
				
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
//	        	mensaje de guardado
	        	if( request.getParameter("delete")==null )
	        		messages.add("info", new ActionMessage("info.guardar.algunos.duplicado"));
	        	else
	        		messages.add("info", new ActionMessage("info.eliminar.relacionados"));					
	    		saveMessages(request.getSession(), messages);
	        }else if(resultado>0){
	        	//mensaje y mapeo de guardado o editado correcto
	        	if(request.getParameter("delete")==null)
	        		messages.add("info", new ActionMessage("info.guardar.ok"));	
	        	else
	        		messages.add("info", new ActionMessage("info.delete.ok"));
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