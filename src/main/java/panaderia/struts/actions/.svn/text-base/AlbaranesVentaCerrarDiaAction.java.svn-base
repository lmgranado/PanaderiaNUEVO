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
import panaderia.beans.NotasEntrega;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.Utils;


public class AlbaranesVentaCerrarDiaAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			int resultado = -2;
			
			//1.- recogemos todos los clientes que tienen nota de entrega para el dia de hoy
			//2.- para cada uno de esos clientes, calculamos el avTotal y creamos un albaran
			//3.- a ese albaran le insertamos esos detalles para ese cliente
			//4.- cerramos todas las notas
			ArrayList chkValues = NotasEntrega.getAllNotasEntregaDistinctClientesDia(request.getParameter("notFecha"));
			if(!Utils.skipNull(request.getParameter("notClId")).equals("")){
				chkValues = new ArrayList();
				String cliente[] = {request.getParameter("notClId")};
				chkValues.add(cliente);
			}
			for(int i=0; i<chkValues.size(); i++){
				//1.-obtenemos el cliente
				String clId = ((String[])chkValues.get(i))[0];
				AlbaranesVenta albaran = new AlbaranesVenta();
				//creamos el albaran
				albaran.setAvFecha(request.getParameter("notFecha"), Utils.DATE_SHORT);
				albaran.setAvClId(clId);
				albaran.setAvPagado("0");
				albaran.setAvCierre("0");
				String numero = Integer.toString(AlbaranesVenta.getSiguienteNumeroAlbaran() + 1);
				albaran.setAvNumero(numero);
				int avId = albaran.inserta();
				if(avId<=0){
					throw new Exception("No se ha insertado el albarán correctamente");
				}

				//dentro de ese bucle insertamos los detalles de los albaranes
				ArrayList listaAlbaranesVentaDetalle = 
					AlbaranesVentaDetalle.getAllAlbaranesDetalleConversorByNotasEntregaDetalleCierraDia(clId,request.getParameter("notFecha"));
				for(int j=0; j<listaAlbaranesVentaDetalle.size(); j++){
					AlbaranesVentaDetalle albaranesVentaDetalle = (AlbaranesVentaDetalle)listaAlbaranesVentaDetalle.get(j);
					albaranesVentaDetalle.setAvdAvId(Integer.toString(avId));
					albaranesVentaDetalle.setAvdLinea(Integer.toString(j));
					resultado = albaranesVentaDetalle.inserta();
					if(resultado<=0){
						throw new Exception("No se ha insertado el detalle del albaran correctamente");
					}
				}
				
				//4.-Le ponemos el total a los albaranes
				if(avId!=0){
					albaran = AlbaranesVenta.getAlbaranesVentaByAvId(Integer.toString(avId));
					albaran.setAvTotal(NotasEntrega.getImporteTotalByClienteDia(clId,request.getParameter("notFecha")));
					albaran.actualiza();
				}
				
				//5.- debemos cerrar las notas asociadas
				NotasEntrega.cierraClienteDia(clId,request.getParameter("notFecha"));
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