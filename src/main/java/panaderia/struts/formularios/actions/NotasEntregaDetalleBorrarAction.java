package panaderia.struts.formularios.actions;


import java.sql.Connection;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Stock;
import panaderia.utilidades.Utiles;
import utils.SQLManager;
import utils.Utils;

public class NotasEntregaDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String[] chkValues = request.getParameterValues("checkList");

		SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);		
	  		
			for(int i=0; i<chkValues.length; i++){
				//Eliminamos los marcados de nuestra lista de detalles
				NotasEntregaDetalle detalle = NotasEntregaDetalle.getNotasEntregaDetalleByNotdetId( chkValues[i] );
				//Primero el stock 
				if(!Utils.skipNull(detalle.getNotdetStId()).equals("")){
					Stock stock = Stock.getStockByStId(detalle.getNotdetStId());
					stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(detalle.getNotdetCantidad())));
					double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) - Double.parseDouble(detalle.getNotdetCantidad());
	  				stock.setStSalidas(Double.toString(cantidadSalidas));
					stock.actualiza(conexion);
				}
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
  	
		request.setAttribute("notaentrega", NotasEntrega.getNotasEntregaByNotId(request.getParameter("notId")));
		
//		En el caso de incidencias ponemos el key que estamos usando (esta parte del codigo hay
		//que usarla en todos los sitios donde se guarde o edite los detalles
		if(request.getSession().getAttribute("banderaIncidenciasNotas")!=null){
			Map mapaNotas = (Hashtable)request.getSession().getAttribute("mapaNotas");
			if(mapaNotas.containsValue(request.getParameter("notId"))){
				String notIdKey = Utiles.returnKeymapByValue(mapaNotas, request.getParameter("notId"));
				request.setAttribute("notId", notIdKey);
			}
		}
		
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