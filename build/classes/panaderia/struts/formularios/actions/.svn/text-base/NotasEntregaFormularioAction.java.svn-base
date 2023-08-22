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
import panaderia.beans.Entregas;
import panaderia.beans.NotasEntrega;
import panaderia.beans.Boton;
import panaderia.beans.Localidades;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.utilidades.Utiles;
import utils.Utils;


public class NotasEntregaFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{		
			String notId = Utils.skipNull((String)request.getAttribute("notId"));
			if(notId.equals("")){notId = Utils.skipNull((String)request.getParameter("notId"));}
			
			//Para el caso de las incidencias tomamos el id del mapa
			if(request.getParameter("banderaIncidenciasNotas")!=null || request.getSession().getAttribute("banderaIncidenciasNotas")!=null){
				request.getSession().setAttribute("banderaIncidenciasNotas", "1");
				//Para este caso, realmente el notid es el key del mapa
				Map mapaNotas = (Hashtable)request.getSession().getAttribute("mapaNotas");
				if(request.getParameter("keyMapaActual")!=null){ //si viene el buscar productos, por el tema del refresco usamos el parametro
					notId = request.getParameter("keyMapaActual");
				}
				//En el caso que la key sea menor a 1 o mayor que el tamaño del mapa debemos coger el último o el primero
				int intMapNotId = Integer.parseInt(notId);
				if(intMapNotId<1){
					notId = Integer.toString(mapaNotas.size());
				}else if(intMapNotId -1 == mapaNotas.size()){
					notId = "1";
				}else if(mapaNotas.containsValue(notId)){
					notId = Utiles.returnKeymapByValue(mapaNotas, notId);
				}
				
				
				//Primero mandamos la key actual del mapa para pintar la menor y la mayor
				request.setAttribute("keyMapaActual", notId);
				//Asi ya tenemos el verdadero notId
				notId = (String)mapaNotas.get(notId);
				
			}
			
			String notClId = Utils.skipNull( request.getParameter("notClId") );
			String notFecha = Utils.skipNull( request.getParameter("notFecha") );
			String notObservaciones = Utils.skipNull( request.getParameter("notObservaciones") );
			
			NotasEntrega notaentrega = new NotasEntrega();			
			
			if(  "".equals(notClId) ){
				notClId = Utils.skipNull( (String) request.getAttribute("notClId") );
			}
			
			Clientes cliente = new Clientes();
			String[] clDatosRelacionados = new String[8];
			//Añado Datos cliente cuando vengo de la ventana pop-pup ó estoy en editar notaentrega			
			if( Utils.empty( notId ) ){					 
				cliente = Clientes.getClientesByClId( notClId );
				clDatosRelacionados = new String[8];
				clDatosRelacionados[0] = cliente.getClNombre();
				clDatosRelacionados[1] = cliente.getClApellidos();
				clDatosRelacionados[2] = cliente.getClDireccion();
				clDatosRelacionados[3] = cliente.getClLocId();
				clDatosRelacionados[4] = cliente.getClProvId();
				clDatosRelacionados[5] = cliente.getClDescuento();
				clDatosRelacionados[7] = cliente.getClNombreComercial();
				notaentrega.setClDatosRelacionados( clDatosRelacionados );
				notaentrega.setNotFecha( notFecha, Utils.DATE_SHORT );
				notaentrega.setNotObservaciones( notObservaciones );
				notaentrega.setNotClId(notClId);
			}else{
				notaentrega = NotasEntrega.getNotasEntregaByNotId(notId);
				request.setAttribute("notaentrega", notaentrega);
			}
			
			//Añadimos la notaentrega a la respuesta
			request.setAttribute("notaentrega", notaentrega);
			
			//mandamos el listado de detalles en el caso que ya este creada la nota de entrega
			if(!notaentrega.getNotId().equals(""))
				request.setAttribute("LISTADETALLES", NotasEntregaDetalle.getNotasEntregaDetalleByNotdetNotIdWithStock(notaentrega.getNotId()));
			
    		Provincias provincia = Provincias.getProvinciasByProvId( notaentrega.getClDatosRelacionados()[4]  );
			request.setAttribute("Provincia", provincia);
			
			Localidades localidad = Localidades.getLocalidadesByLocId( notaentrega.getClDatosRelacionados()[3] );
			request.setAttribute("Localidad", localidad);
			
			request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
			request.setAttribute("LISTAENTREGAS",Entregas.getAllEntregas());
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();						
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			//Si voy a visualizar la notaentrega, no inserto el boton de guardar, solo el de volver
			if(notaentrega.getNotId().equals("")){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			}else if(!notaentrega.getNotCierre().equals("1")){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.EDITAR);
			}
			
			//if(!notaentrega.getNotCuId().equals("")){
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.NOTASCUADRANTE);
			//}
			
			if(request.getParameter("banderaIncidenciasNotas")!=null || request.getSession().getAttribute("banderaIncidenciasNotas")!=null){
				//botonera.addBoton(intOrdenBot++, bBotoneraFormulario.NOTASCUADRANTE);
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.ANTERIOR);
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.POSTERIOR);
			}
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoNotasEntrega.do?session=1');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
			    		
			/* Construcción de las pestañas */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("#","Detalle de Nota de Entrega a Cliente","","","","1"));
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