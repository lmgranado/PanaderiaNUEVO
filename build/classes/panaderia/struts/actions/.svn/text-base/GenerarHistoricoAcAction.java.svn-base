package panaderia.struts.actions;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.HistoricoAc;
import panaderia.beans.NotasEntrega;
import panaderia.negocio.bCuerposGraficaVentasPdf;
import panaderia.negocio.bCuerposHistoricoAcPdf;
import utils.Utils;

public class GenerarHistoricoAcAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
			  String hacClId = request.getParameter("hacClId");
			  String hacFechaDesde = request.getParameter("hacFechaDesde");
			  String hacFechaHasta = request.getParameter("hacFechaHasta");
			  
		      bCuerposHistoricoAcPdf cuerpo = new bCuerposHistoricoAcPdf();
		      
		      Date fechaDesdeDate = Utils.string2Date(hacFechaDesde, Utils.DATE_SHORT);
		      Date fechaHastaDate = Utils.string2Date(hacFechaHasta, Utils.DATE_SHORT);
		      
		      Clientes cliente = Clientes.getClientesByClId( hacClId );
			  ArrayList lista = HistoricoAc.getHistoricoAcByCliente( Utils.date2String(fechaDesdeDate, Utils.DATE_MYSQL_SHORT), 
					  											  Utils.date2String(fechaHastaDate, Utils.DATE_MYSQL_SHORT), 
					  											  hacClId );
			  byte[] content = null;
			  
			  //GENERAMOS EL INFORME
			  content = cuerpo.generaHistoricoAc( request, response, lista, cliente );
			  response.setHeader("Content-type", "application/pdf");				
			  response.setHeader("Content-Disposition","inline; filename=\"ListadoHistoricoAc"+cliente.getClNombreComercial()+".pdf\"");
			  
			  OutputStream output =  response.getOutputStream();
			  output.write( content );
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
	
}