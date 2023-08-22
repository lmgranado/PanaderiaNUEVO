package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.Cobradores;
import panaderia.beans.ComposicionProductos;
import panaderia.beans.FichasProductos;
import panaderia.beans.FormasPago;
import panaderia.beans.Localidades;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;


public class FichasProductosFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String fprodId = Utils.skipNull(request.getParameter("fprodId"));
			if(fprodId.equals("")){
				fprodId = Utils.skipNull((String)request.getAttribute("fprodId"));
			}
			FichasProductos ficha = FichasProductos.getFichasProductosByFprodId( fprodId );			
			request.setAttribute("Ficha", ficha);
			
			/*Listas necesarias para las opciones de los SELECTS*/
			if( !Utils.empty(fprodId) ){
				ArrayList listaComposicionFicha = ComposicionProductos.getAllComposicionProductosByComprodFprodId( fprodId );
				request.setAttribute("LISTACOMPOSICIONFICHA", listaComposicionFicha);
			}
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();						
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoFichasProductos.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}