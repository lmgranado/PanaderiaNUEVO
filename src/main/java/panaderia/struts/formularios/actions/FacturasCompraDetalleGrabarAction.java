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

import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import utils.Utils;

public class FacturasCompraDetalleGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		
		String fcId = Utils.skipNull(request.getParameter("fcId"));
		String lote = Utils.skipNull(request.getParameter("fcdLote"));
		
		FacturasCompra facturacompra = FacturasCompra.getFacturasCompraByFcId(fcId);
		
		//Despues de añadir el detalle debemos añadir/eliminar del stockaje en funcion si es de fabricacion o de compra
		//Si es de compra, solo metenemos stockaje
		//Si es de fabricación calculamos si se puede meter, quitamos los productos utilizados para la fabricación
		//y despues metemos el stockaje de los productos creados
		//Si todo va bien metemos lso productos en la factura
		//Todo eso lo hacemos en el siguiente metodo
		
		boolean existeProducto = FacturasCompraDetalle.existeProducto( fcId, request.getParameter("prodId"), lote );
		//Luis Miguel -> Si no existe el producto, es decir, ni el producto ni el lote del mismo se puede insertar
		if( !existeProducto )	
			
			resultado = FacturasCompraDetalle.anadeDetalleALista(request );
		
		//Si se ha añadido bien el producto, entonces recalculo los importes
		if(resultado>0){
			String total = FacturasCompra.getImporteTotalByFcId(fcId);
			facturacompra.setFcTotal( total );
			facturacompra.actualiza();
		}
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId(facturacompra.getFcId()) );		
		
		request.setAttribute("fcId", fcId);
		request.setAttribute("fcPrId", facturacompra.getFcPrId());
		
		//Metemos en la variable la lista de productos
		ArrayList listaProductos = Productos.getAllProductosCompra();
		request.setAttribute("LISTAPRODUCTOS", listaProductos);
		
		if(resultado == 0 || resultado == -1 || existeProducto){
        	//mensaje de guardado
			if( existeProducto ){	
				messages.add("info", new ActionMessage("info.duplicidad.productos.lote"));
			}
			else{
				messages.add("info", new ActionMessage("info.guardar.algunos"));						
			}
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