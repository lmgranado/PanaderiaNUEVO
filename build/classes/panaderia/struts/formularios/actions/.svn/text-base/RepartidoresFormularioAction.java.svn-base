package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Localidades;
import panaderia.beans.Pestana;
import panaderia.beans.Provincias;
import panaderia.beans.Repartidores;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class RepartidoresFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String repId = Utils.skipNull(request.getParameter("repId"));
			Repartidores repartidor = Repartidores.getRepartidoresByRepId( repId );
			request.setAttribute("Repartidor", repartidor);
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			
			/*Listas necesarias para las opciones de los SELECTS*/
			ArrayList listaProvincias = Provincias.getAllProvincias();
			request.setAttribute("LISTAPROVINCIAS", listaProvincias);
			
			ArrayList listaLocalidades = Localidades.getLocalidadesByProv( repartidor.getRepProvId() );
			request.setAttribute("LISTALOCALIDADES", listaLocalidades);
			
			
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoRepartidores.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
						
			/* Construcción de las pestañas */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("doRepartidoresFormulario.do?repId=" + Utils.skipNull(repId),"Datos Repartidores","","","","1"));			
			request.setAttribute("PESTANAS", bPestana.getPestanas());*/
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}