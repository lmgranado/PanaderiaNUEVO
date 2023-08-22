package panaderia.struts.formularios.actions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.ProductosCliente;
import panaderia.utilidades.Utiles;
import utils.Utils;

public class NotasEntregaDetalleGrabarAction extends Action {
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
		
		String notId = Utils.skipNull(request.getParameter("notId"));
		NotasEntrega notaentrega = NotasEntrega.getNotasEntregaByNotId(notId);
		
		boolean existeProducto = NotasEntregaDetalle.existeProducto( notId, request.getParameter("prodId"), manual );
		if( !existeProducto ){			
			resultado = NotasEntregaDetalle.anadeDetalleALista( request, manual );
		}
		
		//Metemos la lista en session
		request.setAttribute( "LISTADETALLES", NotasEntregaDetalle.getNotasEntregaDetalleByNotdetNotId(notaentrega.getNotId()) );		
		
		request.setAttribute("notId", notId);
		request.setAttribute("notClId", notaentrega.getNotClId());

	    //Metemos en la variable la lista de productos
		ArrayList lista = ProductosCliente.getProductosClienteByPclClId( notaentrega.getNotClId() );
		request.setAttribute("LISTAPRODUCTOSCLIENTE", lista);
		
		//Metemos en la variable la lista de productos
		ArrayList listaProductos = Productos.getAllProductos();
		request.setAttribute("LISTAPRODUCTOS", listaProductos);
		
//		En el caso de incidencias ponemos el key que estamos usando (esta parte del codigo hay
		//que usarla en todos los sitios donde se guarde o edite los detalles
		if(request.getSession().getAttribute("banderaIncidenciasNotas")!=null){
			Map mapaNotas = (Hashtable)request.getSession().getAttribute("mapaNotas");
			if(mapaNotas.containsValue(request.getParameter("notId"))){
				String notIdKey = Utiles.returnKeymapByValue(mapaNotas, request.getParameter("notId"));
				//request.setAttribute("notId", notIdKey);
			}
		}
		
		if(resultado == 0 || resultado == -1 || existeProducto ){
        	//mensaje de guardado
			if( existeProducto )
				messages.add("info", new ActionMessage("info.duplicidad.productos"));
			else
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