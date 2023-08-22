package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.FacturasCompra;
import panaderia.beans.Boton;
import panaderia.beans.Localidades;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Pestana;
import panaderia.beans.Productos;
import panaderia.beans.Proveedores;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.beans.Stock;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import utils.Utils;


public class FacturasCompraFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{		
			String fcId = Utils.skipNull(request.getParameter("fcId"));
			if(fcId.equals("")){fcId = Utils.skipNull((String)request.getAttribute("fcId"));}
			String fcPrId = Utils.skipNull( request.getParameter("fcPrId") );
			String fcFecha = Utils.skipNull( request.getParameter("fcFecha") );
			String fcObservaciones = Utils.skipNull( request.getParameter("fcObservaciones") );
			
			FacturasCompra facturacompra = new FacturasCompra();			
			
			if(  "".equals(fcPrId) ){
				fcPrId = Utils.skipNull( (String) request.getAttribute("fcPrId") );
			}
			
			//CAMBIO LUIS MIGUEL 03/07/14
			Proveedores proveedor = new Proveedores();
			//ESTO ES PARA HACE QUE COJA UNO POR DEFECTO
			//fcPrId = "1";
			String[] clDatosRelacionados = new String[6];
			proveedor = Proveedores.getProveedoresByPrId( fcPrId );
			clDatosRelacionados = new String[6];
			clDatosRelacionados[0] = proveedor.getPrNombre();
			clDatosRelacionados[1] = proveedor.getPrApellidos();
			clDatosRelacionados[2] = proveedor.getPrDireccion();
			clDatosRelacionados[3] = proveedor.getPrLocId();
			clDatosRelacionados[4] = proveedor.getPrProvId();
			clDatosRelacionados[5] = proveedor.getPrDescuento();
			facturacompra.setPrDatosRelacionados( clDatosRelacionados );
			facturacompra.setFcFecha( fcFecha, Utils.DATE_SHORT );
			facturacompra.setFcObservaciones( fcObservaciones );
			facturacompra.setFcPrId(fcPrId);
			
			
			
			//A�ado Datos cliente cuando vengo de la ventana pop-pup � estoy en editar facturacompra			
			if( Utils.empty( fcId ) ){					 
				proveedor = Proveedores.getProveedoresByPrId( fcPrId );
				clDatosRelacionados = new String[6];
				clDatosRelacionados[0] = proveedor.getPrNombre();
				clDatosRelacionados[1] = proveedor.getPrApellidos();
				clDatosRelacionados[2] = proveedor.getPrDireccion();
				clDatosRelacionados[3] = proveedor.getPrLocId();
				clDatosRelacionados[4] = proveedor.getPrProvId();
				clDatosRelacionados[5] = proveedor.getPrDescuento();
				facturacompra.setPrDatosRelacionados( clDatosRelacionados );
				facturacompra.setFcFecha( fcFecha, Utils.DATE_SHORT );
				facturacompra.setFcObservaciones( fcObservaciones );
				facturacompra.setFcPrId(fcPrId);
			}else{
				facturacompra = FacturasCompra.getFacturasCompraByFcId(fcId);
				request.setAttribute("facturacompra", facturacompra);
			}
			
			//A�adimos la facturacompra a la respuesta
			request.setAttribute("facturacompra", facturacompra);
			
			//mandamos el listado de detalles en el caso que ya este creada la nota de entrega
			if(!facturacompra.getFcId().equals("")){
				request.setAttribute("LISTADETALLES", FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId(facturacompra.getFcId()));
			}
			
    		Provincias provincia = Provincias.getProvinciasByProvId( facturacompra.getPrDatosRelacionados()[4]  );
			request.setAttribute("Provincia", provincia);
			
			Localidades localidad = Localidades.getLocalidadesByLocId( facturacompra.getPrDatosRelacionados()[3] );
			request.setAttribute("Localidad", localidad);
			
			request.setAttribute("LISTAPRODUCTOS",Productos.getAllProductosCompra());
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();						
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			
			//Si voy a visualizar la facturacompra, no inserto el boton de guardar, solo el de volver
			if(facturacompra.getFcId().equals(""))
				botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			
			if( !facturacompra.getFcId().equals("") ){
				//botonera.addBoton(intOrdenBot++, bBotoneraFormulario.IMPRIMIRETIQUETAS);
				Boton botonImprimir = new Boton("FACTURAS COMPRA","imprimir Factura","img/imprimir.png","boton", "javascript:doImprimirFactura();");
				botonera.addBoton(intOrdenBot++, botonImprimir);
			}
			
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoFacturasCompra.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
			    		
			/* Construcci�n de las pesta�as */
			/*int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("#","Detalle de Factura de Proveedor","","","","1"));
			request.setAttribute("PESTANAS", bPestana.getPestanas());*/
	        request.setAttribute("RUTASLISTA", Rutas.getAllRutas());
	        
	        request.setAttribute("prodId", request.getParameter("prodId"));
	        request.setAttribute("prodPrecio", request.getParameter("prodPrecio"));
	        request.setAttribute("prodCantidad", request.getParameter("prodCantidad"));
	        request.setAttribute("fcdLote", request.getParameter("fcdLote"));
	        request.setAttribute("fcdOrigen", request.getParameter("fcdOrigen"));
	        request.setAttribute("stFCaducidad", request.getParameter("stFCaducidad"));
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}