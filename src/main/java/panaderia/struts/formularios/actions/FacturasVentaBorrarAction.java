package panaderia.struts.formularios.actions;



import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;

public class FacturasVentaBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String fvFecha = Utils.skipNull( request.getParameter("fvFecha") );
		
		SQLManager manager = new SQLManager(); 
		Connection conexion = null;
		try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);		
			//Preguntar a ivan si esto va fuera de aqui para que la conexion la coja en la clase			
			String[] chkFacturas = request.getParameterValues("checkList");	
			for(int i=0; i<chkFacturas.length; i++){
				FacturasVenta factura = FacturasVenta.getFacturasVentaByFvId( chkFacturas[i] );
				ArrayList detallesFacturas = FacturasVentaDetalle.getAllFacturasVentaDetalleByFvdFvId ( chkFacturas[i] );
				
				//Eliminamos los detalles referidos a dicha factura y actualizamos los stock			
				for(int j=0; j<detallesFacturas.size(); j++){
					//Eliminamos los marcados de nuestra lista de detalles
					FacturasVentaDetalle detalle = (FacturasVentaDetalle) detallesFacturas.get(j);
					//Primero el stock 
					if(!Utils.skipNull(detalle.getFvdStId()).equals("")){
						Stock stock = Stock.getStockByStId(detalle.getFvdStId());
						stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + Double.parseDouble(detalle.getFvdCantidad())));
						double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) - Double.parseDouble(detalle.getFvdCantidad());
						//Si las salidas son negativas quiere decir que se esta intentando eliminar un detalle de factura
						//que no se puede ya que tendra algún abono con cantidad superior
						if( cantidadSalidas < 0 ){
							resultado = -3;
							i = detallesFacturas.size();
							j = chkFacturas.length;
						}
	  					stock.setStSalidas(Double.toString(cantidadSalidas));
	  					stock.actualiza(conexion);
					}
					
					if( resultado != -3){
						resultado = detalle.elimina(conexion);		
					}
				}
			
				if( resultado != -3){
					conexion.commit();
					resultado = factura.elimina();
				}
			}
		
		//Lo ponemos arriba porque sino no nos hace el commit, solo hace el ultimo
  		//conexion.commit();
  	}catch(Exception e){
  		conexion.rollback();
  		throw e;
  	}finally{
	    conexion.rollback();
		manager.closeConnection();  
  	}
			
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
    	//Se intenta eliminar un producto cuyas salidas se convierten en negativa al tener un abono mayor
		}else if(resultado == -3){
            	//mensaje y mapeo de guardado o editado correcto
    			messages.add("info", new ActionMessage("info.abono.superior.a.salidas"));				
        		saveMessages(request.getSession(), messages);
        }else if(resultado>0){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
        }
		
		request.setAttribute("fvFecha", fvFecha);
		
		forward = mapping.findForward("ok");
		if(request.getParameter("simp")!=null){
			forward = mapping.findForward("okSimp");
		}
		
		return forward;
	}
}