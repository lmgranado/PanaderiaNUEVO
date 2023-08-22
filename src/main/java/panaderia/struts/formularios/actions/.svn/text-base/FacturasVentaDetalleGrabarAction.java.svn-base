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
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import utils.Utils;

public class FacturasVentaDetalleGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		//Parámetro que se introduce para introducir productos de forma manual y no a través del formulario de productos
		String manual = Utils.skipNull(request.getParameter("manual"));		
		request.setAttribute("fvTipo", request.getParameter("fvTipo"));
		
		String fvId = Utils.skipNull(request.getParameter("fvId"));
		FacturasVenta facturaventa = FacturasVenta.getFacturasVentaByFvId(fvId);
		String totalAntes = FacturasVenta.getImporteTotalByFvId(fvId);
		
		resultado = FacturasVentaDetalle.anadeDetalleALista(request, manual, facturaventa.getFvIva());
		
		String totalDespues = FacturasVenta.getImporteTotalByFvId(fvId);
		//Si se ha añadido bien el producto, entonces recalculo los importes
		if(resultado>0)
			//Método que me recalcula los totales de la factura y el importe pendiente
			FacturasVentaDetalle.recalculaTotalesEImportePendiente( facturaventa, totalAntes, totalDespues, false );
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId(facturaventa.getFvId()) );		
		
		request.setAttribute("fvId", fvId);
		request.setAttribute("fvClId", facturaventa.getFvClId());

	    //Metemos en la variable la lista de productos
		ArrayList lista = ProductosCliente.getProductosClienteByPclClId( facturaventa.getFvClId() );
		request.setAttribute("LISTAPRODUCTOSCLIENTE", lista);
		
		//Metemos en la variable la lista de productos
		ArrayList listaProductos = Productos.getAllProductos();
		request.setAttribute("LISTAPRODUCTOS", listaProductos);
		
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
		
		return forward;
	}
}