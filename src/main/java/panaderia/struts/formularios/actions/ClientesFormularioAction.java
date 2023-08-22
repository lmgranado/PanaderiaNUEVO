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
import panaderia.beans.FormasPago;
import panaderia.beans.Localidades;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Pestana;
import panaderia.beans.Provincias;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;


public class ClientesFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String clId = Utils.skipNull(request.getParameter("clId"));
			Clientes cliente = Clientes.getClientesByClId(clId);			
			request.setAttribute("Cliente", cliente);
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			
			/*Listas necesarias para las opciones de los SELECTS*/
			ArrayList listaFormaspago = FormasPago.getAllFormasPago();
			request.setAttribute("LISTAFORMASPAGO", listaFormaspago);
	
			ArrayList listaPeriodosFacturacion = PeriodosFacturacion.getAllPeriodosFacturacion();
			request.setAttribute("LISTAPERIODOS", listaPeriodosFacturacion);
			
			ArrayList listaCobradores = Cobradores.getAllCobradores();
			request.setAttribute("LISTACOBRADORES", listaCobradores);
			
			ArrayList listaProvincias = Provincias.getAllProvincias();
			request.setAttribute("LISTAPROVINCIAS", listaProvincias);
			ArrayList listaLocalidades = Localidades.getLocalidadesByProv(cliente.getClProvId());
			request.setAttribute("LISTALOCALIDADES", listaLocalidades);
			
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoClientes.do?session=1');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
						
			/* Construcción de las pestañas */
			int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("doClientesFormulario.do?clId=" + Utils.skipNull(clId),"Datos Cliente","","","","1"));
			if(!Utils.skipNull(clId).equals("")){
				bPestana.addPestana(intOrden++, new Pestana("doListadoProductosCliente.do?pclClId=" + Utils.skipNull(clId),"Productos","","","",""));
				bPestana.addPestana(intOrden++, new Pestana("doListadoCuadrantes.do?cuClId=" + Utils.skipNull(clId),"Cuadrantes","","","",""));
				bPestana.addPestana(intOrden++, new Pestana("doListadoHistoricoAc.do?hacClId=" + Utils.skipNull(clId),"Historico Ac","","","",""));
				bPestana.addPestana(intOrden++, new Pestana("doListadoVacacionesClientes.do?vacClId=" + Utils.skipNull(request.getParameter("clId")),"Vacaciones","","","",""));
			}
			request.setAttribute("PESTANAS", bPestana.getPestanas());
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}