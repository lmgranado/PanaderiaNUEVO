package panaderia.struts.actions;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.FacturasVenta;
import panaderia.negocio.bCuerpoParteDiarioPdf;

public class GenerarParteDiarioAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
		  String cobId = request.getParameter("clCobId");
		  String fvClId = request.getParameter("fvClId");
		  String fechaDesde = request.getParameter("fvFechaDesde");
		  String fechaHasta = request.getParameter("fvFechaHasta");
		  String tipo = request.getParameter("fvIva");
		  String estado = request.getParameter("fvPagada");
		  String fvFechaPago = request.getParameter("fvFechaPago");
		  
		  ArrayList lista = FacturasVenta.getParteDiarioCobros( cobId, fechaDesde, fechaHasta, tipo, estado, fvClId, fvFechaPago );
		  
		  bCuerpoParteDiarioPdf cuerpo = new bCuerpoParteDiarioPdf();
	      cuerpo.generaInformeCobros(request, response, lista );
	      
		  
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}