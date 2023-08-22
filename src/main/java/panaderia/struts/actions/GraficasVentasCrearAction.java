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

import panaderia.beans.NotasEntrega;
import panaderia.negocio.bCuerposGraficaVentasPdf;
import utils.Utils;

public class GraficasVentasCrearAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
			  String agrupacion = request.getParameter("agrupacion");
			  String fechaInicio = request.getParameter("FechaInicio");
			  String fechaFin = request.getParameter("FechaFin");
			  String pagada = request.getParameter("pagada");
			  String empresa = request.getParameter("empresa");
			  //MessageResources params = getResources( request, "recursos" );
			  //String carpeta = params.getMessage( "ruta_graficas" );
			  
		      bCuerposGraficaVentasPdf cuerpo = new bCuerposGraficaVentasPdf();
		      
		      Date fechaInicioDate = Utils.string2Date(fechaInicio, Utils.DATE_SHORT);
		      Date fechaFinDate = Utils.string2Date(fechaFin, Utils.DATE_SHORT);
		      
			  ArrayList lista = NotasEntrega.getAllGraficaVentas( Utils.date2String(fechaInicioDate, Utils.DATE_MYSQL_SHORT), 
					  											  Utils.date2String(fechaFinDate, Utils.DATE_MYSQL_SHORT), 
					  											  agrupacion,
					  											  pagada,
					  											  empresa);
			  byte[] content = null;
			  
			  //GENERAMOS LOS GRAFICOS NECESARIOS
			  GeneraGraficos graficos = new GeneraGraficos( lista );
			  ArrayList graficaBarras = graficos.crearGraficoBarras( agrupacion );
				
			  //GENERAMOS EL INFORME
			  content = cuerpo.generaInformeGraficas( request, response, lista, graficaBarras );
			  response.setHeader("Content-type", "application/pdf");				
			  response.setHeader("Content-Disposition","inline; filename=\"InformesVenta"+new Date().getMonth()+".pdf\"");
			  
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