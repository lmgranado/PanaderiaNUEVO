package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.Boton;
import panaderia.beans.HistoricoInventarios;
import panaderia.beans.HistoricoInventariosDetalle;
import panaderia.beans.Localidades;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.beans.Usuarios;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class HistoricoInventariosFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{		
			String hinvId = Utils.skipNull(request.getParameter("hinvId"));			
			if( hinvId.equals("") ){
				hinvId = Utils.skipNull((String) request.getAttribute("hinvId"));
			}
			String hinvFecha = Utils.skipNull( request.getParameter("hinvFecha") );
			String hinvUsId = Utils.skipNull( request.getParameter("usId") );
			
			HistoricoInventarios historicoinventarios = new HistoricoInventarios();	
			
			//Añado cuando vengo editar inventario			
			if( Utils.empty( hinvId ) ){				
				historicoinventarios.setHinvFecha( hinvFecha, Utils.DATE_SHORT );
				historicoinventarios.setHinvUsId( hinvUsId );
			}else{
				historicoinventarios = HistoricoInventarios.getHistoricoInventariosByHinvId( hinvId );
			}		
			
			//Añadimos la facturaventa a la respuesta
			request.setAttribute("historicoinventarios", historicoinventarios);
			
			//mandamos el listado de detalles en el caso que ya este creada 
			if(!historicoinventarios.getHinvId().equals(""))
				request.setAttribute("LISTADETALLES", HistoricoInventariosDetalle.getHistoricoInventariosDetalleByHinvdHinvId(historicoinventarios.getHinvId()));
			
    					
			request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
			request.setAttribute("LISTAUSUARIOS",Usuarios.getAllUsuarios());
			request.setAttribute("LISTALOTES",new ArrayList() );
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();						
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			//Si voy a visualizar la facturaventa, no inserto el boton de guardar, solo el de volver
			if( historicoinventarios.getHinvId().equals("") )
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
						
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoHistoricoInventarios.do');");
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