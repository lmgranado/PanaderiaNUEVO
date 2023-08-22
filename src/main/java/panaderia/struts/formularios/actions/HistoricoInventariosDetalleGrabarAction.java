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

import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.HistoricoInventarios;
import panaderia.beans.HistoricoInventariosDetalle;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import panaderia.beans.Usuarios;
import utils.Utils;

public class HistoricoInventariosDetalleGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		
		String hinvId = Utils.skipNull(request.getParameter("hinvId"));
		HistoricoInventarios historicoinventarios = HistoricoInventarios.getHistoricoInventariosByHinvId( hinvId );
		
		/*boolean existeProducto = FacturasVentaDetalle.existeProducto( fvId, request.getParameter("prodId"), manual );
		if( !existeProducto ){	
			resultado = FacturasVentaDetalle.anadeDetalleALista(request, manual, facturaventa.getFvIva());
		}*/
		resultado = HistoricoInventariosDetalle.anadeDetalleInventario(request);
		request.setAttribute("hinvId", hinvId);
		
//		mandamos el listado de detalles en el caso que ya este creada 
		if(!historicoinventarios.getHinvId().equals(""))
			request.setAttribute("LISTADETALLES", HistoricoInventariosDetalle.getHistoricoInventariosDetalleByHinvdHinvId(historicoinventarios.getHinvId()));
		
					
		request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
		request.setAttribute("LISTAUSUARIOS",Usuarios.getAllUsuarios());
		request.setAttribute("LISTALOTES",new ArrayList() );
		
		//if(resultado == 0 || resultado == -1 || existeProducto){
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
			/*if( existeProducto )
				messages.add("info", new ActionMessage("info.duplicidad.productos"));
			else*/
				messages.add("info", new ActionMessage("info.guardar.algunos"));						
    		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.guardar.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}