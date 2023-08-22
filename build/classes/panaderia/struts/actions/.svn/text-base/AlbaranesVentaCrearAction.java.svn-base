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


public class AlbaranesVentaCrearAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			int resultado = -2;
			
			//recogemos los ids de las notas de entrega
			String[] chkValues = request.getParameterValues("checkList");	
			for(int i=0; i<chkValues.length; i++){
				NotasEntrega nota = NotasEntrega.getNotasEntregaByNotId(chkValues[i]);
				AlbaranesVenta albaran = new AlbaranesVenta();
				//creamos la nota
				albaran.setAvFecha(nota.getNotFecha(Utils.DATE_SHORT),Utils.DATE_SHORT);
				albaran.setAvClId(nota.getNotClId());
				albaran.setAvObservaciones(Utils.skipNull(request.getParameter("avObservaciones")));
				albaran.setAvPagado("0");
				albaran.setAvCierre("0");
				String numero = Integer.toString(AlbaranesVenta.getSiguienteNumeroAlbaran() + 1);
				albaran.setAvNumero(numero);
				int avId = 0;
				avId = albaran.inserta();
				
				if(avId<=0){
					throw new Exception("No se ha insertado el albarán correctamente");
				}else{
					nota.setNotCierre("1");
					nota.actualiza();
				}

				//dentro de ese bucle insertamos los detalles de los albaranes
				ArrayList listaAlbaranesVentaDetalle = 
					AlbaranesVentaDetalle.getAllAlbaranesDetalleConversorByNotasEntregaDetalle(chkValues[i]);
				
				for(int j=0; j<listaAlbaranesVentaDetalle.size(); j++){
					AlbaranesVentaDetalle albaranesVentaDetalle = (AlbaranesVentaDetalle)listaAlbaranesVentaDetalle.get(j);
					albaranesVentaDetalle.setAvdAvId(Integer.toString(avId));
					albaranesVentaDetalle.setAvdLinea(Integer.toString(j));
					resultado = albaranesVentaDetalle.inserta();
					if(resultado<=0){
						throw new Exception("No se ha insertado el detalle del albaran correctamente");
					}
				}
				
				//Una vez insertados los detalles le ponemos el total a los albaranes
				if(avId!=0){
					albaran = AlbaranesVenta.getAlbaranesVentaByAvId(Integer.toString(avId));
					albaran.setAvTotal(AlbaranesVenta.getImporteTotalByAvId(Integer.toString(avId)));
					albaran.actualiza();
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