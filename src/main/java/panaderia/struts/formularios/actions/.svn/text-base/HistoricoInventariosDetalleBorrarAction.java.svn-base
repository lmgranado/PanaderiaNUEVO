package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.HistoricoInventarios;
import panaderia.beans.HistoricoInventariosDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import panaderia.beans.Usuarios;

public class HistoricoInventariosDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String hinvId = request.getParameter("hinvId");
		
		
		String[] chkValues = request.getParameterValues("checkList");
		HistoricoInventariosDetalle.eliminaDetalleInventario( request, chkValues );
		
		//Actualizamos los detalles del stock
		request.setAttribute("LISTADETALLES", HistoricoInventariosDetalle.getHistoricoInventariosDetalleByHinvdHinvId( hinvId ) );
		request.setAttribute("historicoinventarios", HistoricoInventarios.getHistoricoInventariosByHinvId(hinvId) );
		
		request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
		request.setAttribute("LISTAUSUARIOS",Usuarios.getAllUsuarios());
		request.setAttribute("LISTALOTES",new ArrayList() );
		
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