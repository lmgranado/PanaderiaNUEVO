package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Cobradores;
import panaderia.beans.Localidades;
import panaderia.beans.Pestana;
import panaderia.beans.Provincias;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class CobradoresFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String cobId = Utils.skipNull(request.getParameter("cobId"));
			Cobradores cobrador = Cobradores.getCobradoresByCobId( cobId );
			request.setAttribute("Cobrador", cobrador);
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			
			ArrayList listaProvincias = Provincias.getAllProvincias();
			request.setAttribute("LISTAPROVINCIAS", listaProvincias);
			
			ArrayList listaLocalidades = Localidades.getLocalidadesByProv( cobrador.getCobProvId() );
			request.setAttribute("LISTALOCALIDADES", listaLocalidades);
			
			
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoCobradores.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
						
			/* Construcci�n de las pesta�as */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("doCobradoresFormulario.do?repId=" + Utils.skipNull(cobId),"Datos Cobradores","","","","1"));			
			request.setAttribute("PESTANAS", bPestana.getPestanas());*/
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}