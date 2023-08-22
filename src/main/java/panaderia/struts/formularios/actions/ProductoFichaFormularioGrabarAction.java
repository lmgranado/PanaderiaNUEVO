package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.ComposicionProductos;
import panaderia.beans.FacturasVenta;
import panaderia.beans.Familias;
import panaderia.beans.FichasProductos;
import panaderia.beans.ProductoFicha;
import panaderia.beans.ProductosCliente;
import panaderia.beans.Rutas;
import utils.Utils;

public class ProductoFichaFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String prodfId = request.getParameter("prodfId");
			ProductoFicha ficha = new ProductoFicha();
			ficha.cargaDatosFormularioStruts( request );
						
			if(Utils.skipNull(prodfId).equals("")){				
				resultado = ficha.inserta();
				ficha = ProductoFicha.getProductoFichaByProdfId( Integer.toString( resultado ) );
			}else{
				resultado = ficha.actualiza();
				ficha = ProductoFicha.getProductoFichaByProdfId( prodfId );
			}
			
			request.setAttribute( "FichaProducto" , ficha );
			request.setAttribute( "prodfId" , ficha.getProdfId() );
			
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
			
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}