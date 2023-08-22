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
import panaderia.beans.Localidades;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class FacturasVentaFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{		
			String fvId = Utils.skipNull(request.getParameter("fvId"));
			String fvTipo = Utils.skipNull(request.getParameter("fvTipo"));
			if(fvId.equals("")){
				fvId = Utils.skipNull((String)request.getAttribute("fvId"));
			}
			//String fvClId = Utils.skipNull( request.getParameter("fvClId") );
			//CAMBIO LUIS MIGUEL 03/07/14
			String fvClId = "1"; //CLIENTE NOTA ENTREGA
			Clientes cliente = Clientes.getClientesByClId( fvClId );
			cliente = Clientes.getClientesByClId( fvClId );
			String[]  clDatosRelacionados = new String[7];
			clDatosRelacionados[0] = cliente.getClNombre();
			clDatosRelacionados[1] = cliente.getClApellidos();
			clDatosRelacionados[2] = cliente.getClDireccion();
			clDatosRelacionados[3] = cliente.getClLocId();
			clDatosRelacionados[4] = cliente.getClProvId();
			clDatosRelacionados[5] = cliente.getClDescuento();
			clDatosRelacionados[6] = cliente.getClNombreComercial();
			
			
			String fvFecha = Utils.skipNull( request.getParameter("fvFecha") );
			String fvObservaciones = Utils.skipNull( request.getParameter("fvObservaciones") );
			
			FacturasVenta facturaventa = new FacturasVenta();	
			facturaventa.setFvTipo( fvTipo );

			if(  "".equals(fvClId) ){
				fvClId = Utils.skipNull( (String) request.getAttribute("fvClId") );
			}
			

			//Añado Datos cliente cuando vengo de la ventana pop-pup ó estoy en editar facturaventa			
			if( Utils.empty( fvId ) ){	
				cliente = Clientes.getClientesByClId( fvClId );
				clDatosRelacionados = new String[7];
				clDatosRelacionados[0] = cliente.getClNombre();
				clDatosRelacionados[1] = cliente.getClApellidos();
				clDatosRelacionados[2] = cliente.getClDireccion();
				clDatosRelacionados[3] = cliente.getClLocId();
				clDatosRelacionados[4] = cliente.getClProvId();
				clDatosRelacionados[5] = cliente.getClDescuento();
				clDatosRelacionados[6] = cliente.getClNombreComercial();
				facturaventa.setClDatosRelacionados( clDatosRelacionados );
				facturaventa.setFvFecha( fvFecha, Utils.DATE_SHORT );
				facturaventa.setFvObservaciones( fvObservaciones );
				facturaventa.setFvClId(fvClId);
			}else{
				facturaventa = FacturasVenta.getFacturasVentaByFvId(fvId);
				request.setAttribute("facturaventa", facturaventa);
			}
			
			//Añadimos la facturaventa a la respuesta
			request.setAttribute("facturaventa", facturaventa);
			if(!Utils.skipNull(facturaventa.getFvFvRef()).equals("") && !facturaventa.getFvFvRef().equals("0")){
				request.setAttribute("facturareferencia", FacturasVenta.getFacturasVentaByFvId(facturaventa.getFvFvRef()));
			}

			//mandamos el listado de detalles en el caso que ya este creada la nota de entrega
			if(!facturaventa.getFvId().equals(""))
				request.setAttribute("LISTADETALLES", FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvIdWithStock(facturaventa.getFvId()));
			
    		Provincias provincia = Provincias.getProvinciasByProvId( facturaventa.getClDatosRelacionados()[4]  );
			request.setAttribute("Provincia", provincia);
			Localidades localidad = Localidades.getLocalidadesByLocId( facturaventa.getClDatosRelacionados()[3] );
			request.setAttribute("Localidad", localidad);
			
			request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductos());
			request.setAttribute("LISTALOTES", new ArrayList());
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();		
			
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			//Si voy a visualizar la facturaventa, no inserto el boton de guardar, solo el de volver
			if( facturaventa.getFvId().equals("") )
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			
			if( !facturaventa.getFvId().equals("") ){
				//botonera.addBoton(intOrdenBot++, bBotoneraFormulario.IMPRIMIRETIQUETAS);
				Boton botonImprimir = new Boton("FACTURAS VENTA","ver Facturas","img/imprimir.png","boton", "javascript:doImprimirFactura();");
				botonera.addBoton(intOrdenBot++, botonImprimir);
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			}
				
			//Luis Miguel -> 23/07/14 
			//if(!facturaventa.getFvCierre().equals("1") && !fvId.equals("") ){
				//botonera.addBoton(intOrdenBot++, bBotoneraFormulario.EDITAR);
			//}
			
			//Boton boton = bBotoneraFormulario.VOLVER;
			//boton.setJavascript("javascript:doVolver('doListadoFacturasVenta.do');");
			//botonera.addBoton(intOrdenBot++, boton);
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