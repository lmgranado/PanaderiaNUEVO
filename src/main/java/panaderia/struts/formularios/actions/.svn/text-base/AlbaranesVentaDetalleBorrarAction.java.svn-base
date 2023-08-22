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

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;

public class AlbaranesVentaDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String[] chkValues = request.getParameterValues("checkList");
		String avId = "";
		
		SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);		
	  		
			for(int i=0; i<chkValues.length; i++){
				//Eliminamos los marcados de nuestra lista de detalles
				AlbaranesVentaDetalle detalle = AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdId( chkValues[i] );
				//Primero el stock 
				if(!Utils.skipNull(detalle.getAvdStId()).equals("")){
					Stock stock = Stock.getStockByStId(detalle.getAvdStId());
					stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(detalle.getAvdCantidad())));
					double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) - Double.parseDouble(detalle.getAvdCantidad());
	  				stock.setStSalidas(Double.toString(cantidadSalidas));
					stock.actualiza(conexion);
				}
				avId = detalle.getAvdAvId();
				resultado = detalle.elimina(conexion);		
			}
		
	  		conexion.commit();
	  	}catch(Exception e){
	  		conexion.rollback();
	  		throw e;
	  	}finally{
		    conexion.rollback();
			manager.closeConnection();  
	  	}
		
		AlbaranesVenta albaran = AlbaranesVenta.getAlbaranesVentaByAvId(avId);
		albaran.setAvTotal(AlbaranesVenta.getImporteTotalByAvId(avId));
		albaran.actualiza();
		
		request.setAttribute("albaranesventa", AlbaranesVenta.getAlbaranesVentaByAvId(request.getParameter("avId")));
				
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		forward = mapping.findForward("ok");
		
		return forward;
	}
}