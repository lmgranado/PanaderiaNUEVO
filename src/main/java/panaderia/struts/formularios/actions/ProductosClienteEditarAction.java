package panaderia.struts.formularios.actions;

import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.ProductosCliente;

public class ProductosClienteEditarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			Enumeration enumera = request.getParameterNames();
			while(enumera.hasMoreElements()){
				String nombreCampo = (String)enumera.nextElement();
				//En el caso que el campo sea de precio, tomamos del nombre del campo el 
				//id del producto-cliente
				StringTokenizer token = new StringTokenizer(nombreCampo, "_");
				if(token.nextToken().equals("precio")){
					String pclId = token.nextToken();
					String valor = request.getParameter(nombreCampo);
					ProductosCliente pCliente = ProductosCliente.getProductosClienteByPclId(pclId);

					pCliente.setPclPrecio(valor);
					resultado = pCliente.actualiza();
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