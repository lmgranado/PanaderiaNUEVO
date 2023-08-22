package panaderia.struts.informes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Familias;
import panaderia.negocio.bBotoneraListado;

public class InformesProductosFListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		bBotoneraListado botonera = new bBotoneraListado();
			
		try{
			request.setAttribute("FAMILIAS", Familias.getAllFamilias());

		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		Boton boton = new Boton("IMPRIMIR","Generar Gráficas","img/imprimir.png","boton", "javascript:doInformes();");
		botonera.addBoton(intOrden++, boton);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		
		return forward;
	}
}