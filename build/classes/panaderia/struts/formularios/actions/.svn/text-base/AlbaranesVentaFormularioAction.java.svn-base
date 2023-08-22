package panaderia.struts.formularios.actions;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Boton;
import panaderia.beans.Localidades;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import panaderia.utilidades.Utiles;
import utils.Utils;


public class AlbaranesVentaFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{		
			String avId = Utils.skipNull(request.getParameter("avId"));
			if(avId.equals("")){avId = Utils.skipNull((String)request.getAttribute("avId"));}
			
			//Para el caso de las incidencias tomamos el id del mapa
			if(request.getParameter("banderaIncidenciasAlbaranes")!=null || request.getSession().getAttribute("banderaIncidenciasAlbaranes")!=null){
				request.getSession().setAttribute("banderaIncidenciasAlbaranes", "1");
				//Para este caso, realmente el notid es el key del mapa
				Map mapaAlbaranes = (Hashtable)request.getSession().getAttribute("mapaAlbaranes");
				if(request.getParameter("keyMapaActual")!=null){ //si viene el buscar productos, por el tema del refresco usamos el parametro
					avId = request.getParameter("keyMapaActual");
				}
				//En el caso que la key sea menor a 1 o mayor que el tamaño del mapa debemos coger el último o el primero
				int intMapAvId = Integer.parseInt(avId);
				if(intMapAvId<1){
					avId = Integer.toString(mapaAlbaranes.size());
				}else if(intMapAvId -1 == mapaAlbaranes.size()){
					avId = "1";
				}else if(mapaAlbaranes.containsValue(avId)){
					avId = Utiles.returnKeymapByValue(mapaAlbaranes, avId);
				}
				//Primero mandamos la key actual del mapa para pintar la menor y la mayor
				request.setAttribute("keyMapaActual", avId);
				//Asi ya tenemos el verdadero notId
				avId = (String)mapaAlbaranes.get(avId);
				
			}
			
			String avClId = Utils.skipNull( request.getParameter("avClId") );
			String notFecha = Utils.skipNull( request.getParameter("avFecha") );
			String notObservaciones = Utils.skipNull( request.getParameter("avObservaciones") );
			
			AlbaranesVenta albaranventa = new AlbaranesVenta();			
			
			if(  "".equals(avClId) ){
				avClId = Utils.skipNull( (String) request.getAttribute("avClId") );
			}
			
			Clientes cliente = new Clientes();
			String[] clDatosRelacionados = new String[6];
			//Añado Datos cliente cuando vengo de la ventana pop-pup ó estoy en editar albaranventa			
			if( Utils.empty( avId ) ){					 
				cliente = Clientes.getClientesByClId( avClId );
				clDatosRelacionados = new String[7];
				clDatosRelacionados[0] = cliente.getClNombre();
				clDatosRelacionados[1] = cliente.getClApellidos();
				clDatosRelacionados[2] = cliente.getClDireccion();
				clDatosRelacionados[3] = cliente.getClLocId();
				clDatosRelacionados[4] = cliente.getClProvId();
				clDatosRelacionados[5] = cliente.getClDescuento();
				clDatosRelacionados[6] = cliente.getClNombreComercial();
				albaranventa.setClDatosRelacionados( clDatosRelacionados );
				albaranventa.setAvFecha( notFecha, Utils.DATE_SHORT );
				albaranventa.setAvObservaciones( notObservaciones );
				albaranventa.setAvClId(avClId);
			}else{
				albaranventa = AlbaranesVenta.getAlbaranesVentaByAvId(avId);
				request.setAttribute("albaranventa", albaranventa);
			}
			
			//Añadimos la albaranventa a la respuesta
			request.setAttribute("albaranventa", albaranventa);
			
			//mandamos el listado de detalles en el caso que ya este creada la nota de entrega
			if(!albaranventa.getAvId().equals(""))
				request.setAttribute("LISTADETALLES", AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdAvIdWithStock(albaranventa.getAvId()));
			
    		Provincias provincia = Provincias.getProvinciasByProvId( albaranventa.getClDatosRelacionados()[4]  );
			request.setAttribute("Provincia", provincia);
			
			Localidades localidad = Localidades.getLocalidadesByLocId( albaranventa.getClDatosRelacionados()[3] );
			request.setAttribute("Localidad", localidad);
			
			request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();						
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			//Si voy a visualizar la albaranventa, no inserto el boton de guardar, solo el de volver
			if(albaranventa.getAvId().equals("")){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			}else if(!albaranventa.getAvCierre().equals("1")){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.EDITAR);
			}
			if(request.getParameter("banderaIncidenciasAlbaranes")!=null || request.getSession().getAttribute("banderaIncidenciasAlbaranes")!=null){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.ANTERIOR);
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.POSTERIOR);
			}
			
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoAlbaranesVenta.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
			    		
			/* Construcción de las pestañas */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("#","Detalle de Albaran de venta a Cliente","","","","1"));
			request.setAttribute("PESTANAS", bPestana.getPestanas());*/
	        request.setAttribute("RUTASLISTA", Rutas.getAllRutas());
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}