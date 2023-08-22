package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.PrecisionRecord;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Localidades;
import panaderia.beans.Productos;


public class CargaPrecioProductoUltimaCompraAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		String idproducto = request.getParameter("producto");
		
		
		Productos producto = Productos.getProductosByProdId(idproducto);
				
		String precioProducto = FacturasCompraDetalle.getprecioProductoUltimaCompra ( producto.getProdNombre() );
		//Si no existe una compra inicial de dicho producto lo ponemos a cero	
		if( precioProducto == null )
			precioProducto = "0";
		try{
			PrintWriter out = response.getWriter();
			out.print(  precioProducto  );
			
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}