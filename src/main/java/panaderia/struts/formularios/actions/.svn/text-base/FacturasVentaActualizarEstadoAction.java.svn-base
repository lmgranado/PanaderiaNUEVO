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
import utils.Utils;

public class FacturasVentaActualizarEstadoAction extends Action {
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
			FacturasVenta factura = FacturasVenta.getFacturasVentaByFvId( chkValues[i] );
			if(!"1".equals(factura.getFvPagada())){
				factura.setFvPagada( "1" );
				factura.setFvImportePendiente("0");
				factura.setFvFechaPago(Utils.getFechaActualSinHoraString(), Utils.DATE_SHORT);
				factura.actualiza();
			}
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
		
		//Si el parametro me viene vacio o nulo, estoy en el listado de facturas, sino en el de cobros 
		if( request.getParameter( "listadoCobros" ) != null )
			forward = mapping.findForward("okCobros");
		else
			forward = mapping.findForward("ok");
			
		if(request.getParameter("simp")!=null){
			forward = mapping.findForward("okSimp");
		}
		
		return forward;
	}
}