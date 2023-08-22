package panaderia.struts.informes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Stock;
import panaderia.informes.GenerarListadoStockPorLote;

public class InformeStockPorLoteAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
		      GenerarListadoStockPorLote cuerpo = new GenerarListadoStockPorLote();
			 
			  ArrayList listaStock = Stock.getStockGroupByProductoLote( request );
			  cuerpo.generaInformeStockLote( listaStock, request, response );
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}