package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Pestana;
import panaderia.beans.Usuarios;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class UsuariosFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String usId = Utils.skipNull(request.getParameter("usId"));
			Usuarios usuario = Usuarios.getUsuariosByUsId(usId);
			request.setAttribute("Usuario", usuario);
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoUsuarios.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
						
			/* Construcción de las pestañas */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("#","Datos Usuarios","","","","1"));
			request.setAttribute("PESTANAS", bPestana.getPestanas());*/
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}