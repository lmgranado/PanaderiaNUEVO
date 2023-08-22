package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.pagination.IPaginatedListTest;
import org.displaytag.pagination.PaginatedListTest;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Localidades;
import panaderia.beans.Proveedores;
import panaderia.negocio.bBotoneraListado;
import utils.Utils;


public class VentanaImprimirEtiquetasVentasAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ArrayList detalles = new ArrayList();
		bBotoneraListado botonera = new bBotoneraListado();
		String fvId = Utils.skipNull( request.getParameter("fvId") );
		
		try{
			FacturasVenta factura = FacturasVenta.getFacturasVentaByFvId( fvId );
			detalles = FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId( fvId );
			
    		request.setAttribute("LISTADETALLES", detalles);
    		request.setAttribute("fvId", Utils.skipNull(fvId));
    		
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcci�n de la botonera */
		int intOrden = 0;
		botonera.addBoton(intOrden++, bBotoneraListado.NUEVO);
		botonera.addBoton(intOrden++, bBotoneraListado.BORRAR);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		request.setAttribute("LOG_ESCRITURA", "true");
		request.setAttribute("LOG_LECTURA", "true");
		
		return forward;
	}
}