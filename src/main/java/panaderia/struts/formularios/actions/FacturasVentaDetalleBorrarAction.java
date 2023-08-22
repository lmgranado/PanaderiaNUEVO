package panaderia.struts.formularios.actions;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;

public class FacturasVentaDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String fvId = request.getParameter("fvId");
		FacturasVenta facturaventa = FacturasVenta.getFacturasVentaByFvId(fvId);
		String totalAntes = FacturasVenta.getImporteTotalByFvId(fvId);
		
		String[] chkValues = request.getParameterValues("checkList");
		
		SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);		
	  		
			for(int i=0; i<chkValues.length; i++){
				//Eliminamos los marcados de nuestra lista de detalles
				FacturasVentaDetalle detalle = FacturasVentaDetalle.getFacturasVentaDetalleByFvdId( chkValues[i] );
				//Primero el stock 
				if(!Utils.skipNull(detalle.getFvdStId()).equals("")){
					Stock stock = Stock.getStockByStId(detalle.getFvdStId());
					stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(detalle.getFvdCantidad())));
					double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) - Double.parseDouble(detalle.getFvdCantidad());
					//Si las salidas son negativas quiere decir que se esta intentando eliminar un detalle de factura
					//que no se puede ya que tendra algún abono con cantidad superior
					if( cantidadSalidas < 0 ){
						resultado = -3;
						i = chkValues.length;
					}
	  				stock.setStSalidas(Double.toString(cantidadSalidas));
					stock.actualiza(conexion);
				}
				if( resultado != -3){
					fvId = detalle.getFvdFvId();
					resultado = detalle.elimina(conexion);
				}
			}
			
		    //Si se pone aqui el commit solo se actualiza el ultimo registro y no se porque
			if( resultado != -3){
				conexion.commit();
			}
	  	}catch(Exception e){
	  		conexion.rollback();
	  		throw e;
	  	}finally{
		    conexion.rollback();
			manager.closeConnection();  
	  	}
		
		String totalDespues = FacturasVenta.getImporteTotalByFvId(fvId);
		
		//Si se ha añadido bien el producto, entonces recalculo los importes
		if(resultado>0)
			//Método que me recalcula los totales de la factura y el importe pendiente
			FacturasVentaDetalle.recalculaTotalesEImportePendiente( facturaventa, totalAntes, totalDespues, true );
		
		request.setAttribute("facturaventa", FacturasVenta.getFacturasVentaByFvId(fvId));
		
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
        }
		//Se intenta eliminar un producto cuyas salidas se convierten en negativa al tener un abono mayor
		else if(resultado == -3){
        	//mensaje y mapeo de guardado o editado correcto
			messages.add("info", new ActionMessage("info.abono.superior.a.salidas"));				
    		saveMessages(request.getSession(), messages);
        }
		else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}