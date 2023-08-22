package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.ejb.EntityBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.HistoricoAc;
import panaderia.beans.Localidades;
import panaderia.struts.forms.EntidadBean;
import utils.Utils;


public class CompruebaImportePendienteFacturasAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		HistoricoAc historicoac = new HistoricoAc();
		try{
			PrintWriter out = response.getWriter();
			int resultado = 0;
			historicoac.cargaDatosFormularioStruts(request);
			
			float hacCantidad = Float.parseFloat(  EntidadBean.quitar_comas(historicoac.getHacCantidad()) );
			String hacClId = historicoac.getHacClId();
			float importePendiente = Float.parseFloat(FacturasVenta.getImportesPendientesByFvClId( hacClId ));
			
			if( hacCantidad > importePendiente )
				resultado = 1;
			
			out.print( resultado );
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}