package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Localidades;
import panaderia.beans.Productos;
import panaderia.beans.Rutas;
import panaderia.beans.Stock;


public class CargaLotesAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		String prodId = request.getParameter("prodId");
		
		ArrayList listastock = Stock.getListaStockByStProdId( prodId );		
		

		
		try{
			PrintWriter out = response.getWriter();
			out.print("__");
			out.print("[Seleccionar]"+"~");
			for(int i=0; i < listastock.size(); i++){
				Stock stock = (Stock) listastock.get(i);
				String idStock = stock.getStId();
				out.print(idStock +"__");
				FacturasCompraDetalle detalle = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStId( idStock );
				
				if(i == listastock.size())			
					out.print(detalle.getFcdLote());
				else
					out.print(detalle.getFcdLote()+"~");
				
				
			}
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}