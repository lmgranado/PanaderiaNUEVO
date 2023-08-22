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

import panaderia.beans.Localidades;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import utils.Utils;


public class CargaPrecioProductoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		String idproducto = request.getParameter("producto");
		
		Productos producto = Productos.getProductosByProdId(idproducto);
				
		String stId = request.getParameter("stId");
		Stock stockLote = Stock.getStockByStId( stId );
		
		try{
			PrintWriter out = response.getWriter();
			//out.print( producto.getProdPrecioGeneral() );
			out.print( Utils.formateaCantidadDosDecimales(producto.getProdPrecioGeneral()) + "__" + stockLote.getStCantidadFinal() );
			
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}