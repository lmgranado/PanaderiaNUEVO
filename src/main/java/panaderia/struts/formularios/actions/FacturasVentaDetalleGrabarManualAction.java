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

import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Productos;
import utils.Utils;

public class FacturasVentaDetalleGrabarManualAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		//Par�metro que se introduce para introducir productos de forma manual y no a trav�s del formulario de productos
		String manual = Utils.skipNull(request.getParameter("manual"));		
		
		String fvId = Utils.skipNull(request.getParameter("fvId"));
		FacturasVenta facturaventa = FacturasVenta.getFacturasVentaByFvId(fvId);
		String totalAntes = FacturasVenta.getImporteTotalByFvId(fvId);
		
		boolean existeProducto = FacturasVentaDetalle.existeProducto( fvId, request.getParameter("prodId"), manual );
		//LUIS MIGUEL 04/07/2014 -- CAMBIO PARA QUE PUEDAN A�ADIRSE MAS DE UN PRODUCTO IGUAL EN LA FACTURA
		//if( !existeProducto ){	
		resultado = FacturasVentaDetalle.anadeDetalleALista(request, manual, facturaventa.getFvIva());
		//}
		
		String totalDespues = FacturasVenta.getImporteTotalByFvId(fvId);
		//Si se ha a�adido bien el producto, entonces recalculo los importes
		if(resultado>0 || !existeProducto)
			//M�todo que me recalcula los totales de la factura y el importe pendiente
			FacturasVentaDetalle.recalculaTotalesEImportePendiente( facturaventa, totalAntes, totalDespues, false );
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId(facturaventa.getFvId()) );		
		
		request.setAttribute("fvId", fvId);
		
		//Metemos en la variable la lista de productos
		ArrayList listaProductos = Productos.getAllProductos();
		request.setAttribute("LISTAPRODUCTOS", listaProductos);
		
		//if(resultado == 0 || resultado == -1 || existeProducto){
		if(resultado == 0 || resultado == -1 || resultado==-3){
        	//mensaje de guardado
			//if( existeProducto )
				//messages.add("info", new ActionMessage("info.duplicidad.productos"));
			//else
			//SE HA INTRODUCIDO EN LA FACTURA DE ABONO UNA CANTIDAD DE ABONO SUPERIOR AL ST_SALIDAS. NO SE PUEDE ABONAR MAS DE LO QUE SE HA
			//VENDIDO DE DICHO PRODUCTO-LOTE
			if( resultado == -3){
				messages.add("info", new ActionMessage("info.abono.superior.a.salidas"));
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