package panaderia.struts.formularios.actions;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.ComponentesProducto;
import panaderia.beans.ComposicionFabricacion;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;

public class FacturasFabricacionBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		boolean noTieneProductosFabricados = true;
		
		String[] chkValues = request.getParameterValues("checkList");	
		for(int i=0; i<chkValues.length && noTieneProductosFabricados; i++){
			FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId(chkValues[i]);
						
			//Tomamos todos los detalles de la factura
			ArrayList listaDetalles = FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId(chkValues[i]);
			
			//Si tiene productos fabricados, no dejamos borrar la fabricacion. Primero se deben de eliminar los productos
			//uno a uno
			if( listaDetalles.size() > 0 )
				noTieneProductosFabricados = false;
			else
				resultado = factura.elimina();				
			
			
					/* for(int w=0; w<listaDetalles.size(); w++){
						FacturasCompraDetalle detalle = (FacturasCompraDetalle)listaDetalles.get(w);
						//Lo hacemos en una transicion
						SQLManager manager = new SQLManager(); 
							Connection conexion = manager.getConnection(); 
						    try
						    {
						    	conexion.setAutoCommit(false);
						    	//Eliminamos el detalle de la factura
						    	resultado = detalle.elimina(conexion);	
						    	
						    	Stock stock = detalle.getStock();
							    	
						    	//verificamos que se puede eliminar, sabiendo si existen bastantes productos no usados.
						    	if(Double.parseDouble(detalle.getFcdCantidad())>Double.parseDouble(stock.getStCantidadFinal())){
						    		throw new Exception("No se puede eliminar más cantidad de la que ya se ha usado/vendido");
						    	}
						    	
						    	//Ahora actualizamos la composicion de fabricacion
						    	ArrayList listaCp = ComposicionFabricacion.getComposicionFabricacionByCfIdStFabricado(stock.getStId());
						    	for(int k=0; k<listaCp.size(); k++){
						    		ComposicionFabricacion cf = (ComposicionFabricacion)listaCp.get(k);
						    		Stock stockUsado = Stock.getStockByStId(cf.getCfIdStUsado());
							    	ComponentesProducto cp = ComponentesProducto.getComponentesProductoByCpProdIdFabricadoAndUsado(stockUsado.getStProdId(), stock.getStProdId());
					    			double cantidadNecesaria = Double.parseDouble(cp.getCpCantidad()) * Double.parseDouble(detalle.getFcdCantidad());
					    			cf.setCfCantidad(Double.toString(Double.parseDouble(cf.getCfCantidad()) - cantidadNecesaria));
					    			if(Double.parseDouble(cf.getCfCantidad())==0)
					    				cf.elimina(conexion);
					    			else
					    				cf.actualiza(conexion);
					    			//Ademas debemos actualizar el stock usado
					    			stockUsado.setStCantidadFinal(Double.toString(Double.parseDouble(stockUsado.getStCantidadFinal()) + cantidadNecesaria));
					    			stockUsado.actualiza(conexion);
						    	}
						    	
							    	
						    	//Actualizamos el stock
						    	stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) - Double.parseDouble(detalle.getFcdCantidad())));
						    	//Si la cantidad final es 0 eliminamos
						    	if(Double.parseDouble(stock.getStCantidadFinal())==0)
						    		stock.elimina(conexion);
						    	else
						    		stock.actualiza(conexion);
						    	
						    	conexion.commit();
						    }
			  			    catch( SQLException e ){ 
			  			    	conexion.rollback();
			  			    	throw new Exception( "Se ha producido un error durante la eliminación de esta produccion " + e);
			  			    	
			  			    }
			  			    finally{ manager.closeConnection();  }
					}
					
					if( resultado != -1 ){
						//Eliminamos el padre
						resultado = factura.elimina();
					}
				
				}else{
					noTieneProductosFabricados = false;
				}*/
				
				

		}
		
		if( resultado == 0 || resultado == -1 ){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
    		
        }else if( resultado > 0 ){
        	//mensaje y mapeo de guardado o editado correcto
        	messages.add("info", new ActionMessage("info.delete.ok"));						
    		saveMessages(request.getSession(), messages);
    		
        }else if( !noTieneProductosFabricados ){
        	//mensaje de tiene productos fabricados y hay que eliminar primero dichos productos antes que la fabricacion
        	messages.add("info", new ActionMessage("info.eliminar.fabricacion.productos.fabricados"));						
    		saveMessages(request.getSession(), messages);
        }
		
			forward = mapping.findForward("ok");
			if(request.getParameter("simp")!=null){
				forward = mapping.findForward("okSimp");
			}
		
		return forward;
	}
}