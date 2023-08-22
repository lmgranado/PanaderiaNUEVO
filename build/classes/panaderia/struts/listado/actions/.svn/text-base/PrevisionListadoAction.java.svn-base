package panaderia.struts.listado.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.Entregas;
import panaderia.beans.NotasEntrega;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraListado;

public class PrevisionListadoAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              
		NotasEntrega notas = (NotasEntrega) form;
		notas.cargaDatosFormularioStruts(request);
		bBotoneraListado botonera = new bBotoneraListado();
			
		try{
	        // Parámetros por defecto para la primera vez que se muestra la lista.
	        
    		request.setAttribute("CLIENTESLISTA", Clientes.getAllClientes());
    		request.setAttribute("RUTASLISTA", Rutas.getAllRutas());
    		request.setAttribute("ENTREGASLISTA", Entregas.getAllEntregas());

		}catch (Exception e) {
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		/* Construcción de la botonera */
		int intOrden = 0;
		Boton boton = new Boton("IMPRIMIR","Generar Prevision","img/imprimir.png","boton", "javascript:doPrevision();");
		botonera.addBoton(intOrden++, boton);
		
		request.setAttribute("BOTONERA", botonera.getBotones());
		
		return forward;
	}
}