package panaderia.struts.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.PeriodosFacturacion;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.Utils;


public class FacturasVentaCrearAction_buena extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			//recogemos los ids de los albaranes
			String[] chkValues = request.getParameterValues("checkList");	
			for(int i=0; i<chkValues.length; i++){
				AlbaranesVenta albaranes = AlbaranesVenta.getAlbaranesVentaByAvId(chkValues[i]);
//				Obtenemos el cliente para ver que proporcion de IVA tiene puesta.
				//Si la proporcion de IVA es 0 o 100 creamos una unica nota, si no creamos 2 notas.
				Clientes cliente = Clientes.getClientesByClId(albaranes.getAvClId());
				//Solo se crearán las diarias
				PeriodosFacturacion periodo = PeriodosFacturacion.getPeriodosFacturacionByPfId(cliente.getClPfId());
				if(!periodo.getPfDescripcion().equals("DIARIO"))
					continue; //si no es diario, no se da de alta.
				int numFacturas = 2;
				float proporcionIVA = Float.parseFloat(cliente.getClProporcionIva());
				if(proporcionIVA==0 || proporcionIVA==1){
					numFacturas = 1;
				}
				
				
				FacturasVenta factura = new FacturasVenta();
				//creamos la nota
				factura.setFvFecha(albaranes.getAvFecha(Utils.DATE_SHORT),Utils.DATE_SHORT);
				factura.setFvClId(albaranes.getAvClId());
				factura.setFvObservaciones(albaranes.getAvObservaciones());
				factura.setFvPagada("0");
				//factura.setFvCierre("0");
				//Luismi --> Cambiado ya que se quiere que no se puedan modificar las facturas una vez generadas, solo abono
				factura.setFvCierre("1");
				factura.setFvIva("A");
				String numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(factura.getFvIva(), cliente.getClIdEmpresa()) + 1);
				factura.setFvNumeroFactura(numero);
				factura.setFvIdEmpresa(cliente.getClIdEmpresa());

				int fvId = 0;
				int fvId2 = 0;
				if(proporcionIVA!=0)
					fvId = factura.inserta();
				//Insertamos un segundo albaran en su caso
				if(numFacturas==2 || proporcionIVA==0){
					factura.setFvIva("B");
					numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura("B", cliente.getClIdEmpresa()) + 1);
					factura.setFvNumeroFactura(numero);
					fvId2 = factura.inserta();
				}
				
				if(fvId<=0 && fvId2<=0){
					throw new Exception("No se ha insertado la factura correctamente");
				}else{
					albaranes.setAvCierre("1");
					albaranes.actualiza();
				}

//				dentro de ese bucle insertamos los detalles de los albaranes
				ArrayList listaFacturasVentaDetalle = 
					FacturasVentaDetalle.getAllFacturasDetalleConversorByAlbaranesVentaDetalle(chkValues[i], proporcionIVA);
				
				for(int j=0; j<listaFacturasVentaDetalle.size(); j++){
					FacturasVentaDetalle facturasVentaDetalle = (FacturasVentaDetalle)listaFacturasVentaDetalle.get(j);
					if(facturasVentaDetalle.getTipo().equals("A"))
						facturasVentaDetalle.setFvdFvId(Integer.toString(fvId));
					else
						facturasVentaDetalle.setFvdFvId(Integer.toString(fvId2));
					facturasVentaDetalle.setFvdLinea(Integer.toString(j));
					
					if(Integer.parseInt(facturasVentaDetalle.getFvdCantidad())>0)
						facturasVentaDetalle.inserta();
				}
				
				//Una vez insertados los detalles le ponemos el total a los albaranes
				if(fvId!=0){
					factura = FacturasVenta.getFacturasVentaByFvId(Integer.toString(fvId));
					factura.setFvTotal(FacturasVenta.getImporteTotalByFvId(Integer.toString(fvId)));
					//Luismi, 29/5/2011 --> Faltaria poner la cantidad pendiente igual que la cantidad total
					factura.setFvImportePendiente( factura.getFvTotal() );
					
					if(factura.getFvTotal().equals("0"))
						factura.elimina();
					else factura.actualiza();
				}
				if(fvId2!=0){
					factura = FacturasVenta.getFacturasVentaByFvId(Integer.toString(fvId2));
					factura.setFvTotal(FacturasVenta.getImporteTotalByFvId(Integer.toString(fvId2)));
					//Luismi, 29/5/2011 --> Faltaria poner la cantidad pendiente igual que la cantidad total
					factura.setFvImportePendiente( factura.getFvTotal() );
					
					if(factura.getFvTotal().equals("0"))
						factura.elimina();
					else factura.actualiza();
				}
			}
			forward = mapping.findForward("ok");
		} catch (Exception ex){
			forward = mapping.findForward("error");
			PanaderiaExceptionHandler.guardaLog(
					  ex,
	        		  mapping,
	        		  request);
		}
		return forward;
	}
}