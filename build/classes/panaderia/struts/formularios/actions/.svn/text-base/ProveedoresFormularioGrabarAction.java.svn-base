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
import panaderia.beans.Proveedores;
import panaderia.struts.forms.EntidadBean;
import utils.Utils;

public class ProveedoresFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String prId = request.getParameter("prId");
			Proveedores proveedor = Proveedores.getProveedoresByPrId( prId );
			proveedor.cargaDatosFormularioStruts(request);
			
//			Verificamos el formato del descuento
			float prDescuento = Float.parseFloat(EntidadBean.quitar_comas(Utils.skipNullNumero(proveedor.getPrDescuento())));
			if(prDescuento>0)
				prDescuento = prDescuento/100;
			
			proveedor.setPrDescuento(EntidadBean.quitar_comas_invertido(Float.toString(prDescuento)));
			
			if(Utils.skipNull(prId).equals(""))
				resultado = proveedor.inserta();
			else
				resultado = proveedor.actualiza();
				
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