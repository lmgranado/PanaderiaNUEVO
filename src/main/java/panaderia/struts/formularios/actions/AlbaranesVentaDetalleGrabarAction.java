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

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import utils.Utils;

public class AlbaranesVentaDetalleGrabarAction extends Action {
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
		
		String avId = Utils.skipNull(request.getParameter("avId"));
		AlbaranesVenta albaranventa = AlbaranesVenta.getAlbaranesVentaByAvId(avId);
		
		resultado = AlbaranesVentaDetalle.anadeDetalleALista(request, manual);
		
		albaranventa.setAvTotal(AlbaranesVenta.getImporteTotalByAvId(albaranventa.getAvId()));
		albaranventa.actualiza();
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdAvId(albaranventa.getAvId()) );		
		
		request.setAttribute("avId", avId);
		request.setAttribute("avClId", albaranventa.getAvClId());

	    //Metemos en la variable la lista de productos
		ArrayList lista = ProductosCliente.getProductosClienteByPclClId( albaranventa.getAvClId() );
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