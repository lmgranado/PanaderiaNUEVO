package panaderia.struts.formularios.actions;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.StringTokenizer;

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
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;

public class FacturasVentaFormularioEditarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		FacturasVenta facturasventa = new FacturasVenta();
		try{
			Enumeration enumera = request.getParameterNames();
			while(enumera.hasMoreElements()){
				String nombreCampo = (String)enumera.nextElement();
				//En el caso que el campo sea de cantidad, tomamos del nombre del campo el 
				//id del detalle del albaran
				StringTokenizer token = new StringTokenizer(nombreCampo, "_");
				if(token.nextToken().equals("cantidad")){
					
					SQLManager manager = new SQLManager(); 
					Connection conexion = null;
				  	try{
				  		conexion = manager.getConnection(); 
				  		conexion.setAutoCommit(false);
				  		
						String fvdId = token.nextToken();
						String valor = FacturasVenta.quitar_comas( request.getParameter(nombreCampo) );
						FacturasVentaDetalle facturasDetalle = FacturasVentaDetalle.getFacturasVentaDetalleByFvdId(fvdId);
						facturasventa = FacturasVenta.getFacturasVentaByFvId(facturasDetalle.getFvdFvId());
						
						if(Utils.skipNull(facturasDetalle.getFvdStId()).equals(""))
							throw new Exception("El producto que intenta editar no tiene lote");
						
						//Primero debemos ver si se puede editar x el tema del stock
						String cantidadACalcularStock = Double.toString( Double.parseDouble( valor ) - Double.parseDouble(facturasDetalle.getFvdCantidad())); //sera la cantidad nueva menos la actual, si es negativo hacemos una cosa, positivo otra
						if(Double.parseDouble(valor) - Double.parseDouble(facturasDetalle.getFvdCantidad()) == 0){
							//No hacemos nada
							continue;
						}else if(Double.parseDouble(valor) - Double.parseDouble(facturasDetalle.getFvdCantidad())<0){
							//Si es negativo a�adimos la cantidad al stock
							Stock stock = Stock.getStockByStId(facturasDetalle.getFvdStId());
							stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + 
													 Double.parseDouble(facturasDetalle.getFvdCantidad()) - 
													 Double.parseDouble(valor)));
							double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) + Double.parseDouble(valor) - Double.parseDouble(facturasDetalle.getFvdCantidad());
			  				stock.setStSalidas(Double.toString(cantidadSalidas));
							stock.actualiza(conexion);
							
						}else{
							//Si es positivo quitamos la parte del stock de la cantidad final
							Stock stock = Stock.getStockByStId(facturasDetalle.getFvdStId());
							if(Double.parseDouble(stock.getStCantidadFinal()) < Double.parseDouble(cantidadACalcularStock))
					  			throw new Exception("No dispone de stock suficiente para editar este producto en la nota de entrega: " + facturasDetalle.getFvdProducto());
							stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) - Double.parseDouble(cantidadACalcularStock)));
							double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) + Double.parseDouble(valor) - Double.parseDouble(facturasDetalle.getFvdCantidad());
			  				stock.setStSalidas(Double.toString(cantidadSalidas));
							stock.actualiza(conexion);
						}
						facturasDetalle.setFvdCantidad(valor);
						//cambiado por luismi 28/06/14
						resultado = FacturasVentaDetalle.editaFacturaVentaDetalle(facturasDetalle,conexion);
						request.setAttribute("fvId", facturasDetalle.getFvdFvId());
						conexion.commit();
						
				  	}catch(Exception e){
				  		conexion.rollback();
				  		throw e;
				  	}finally{
					    conexion.rollback();
						manager.closeConnection();  
				  	}
					
				}
			}
			
			facturasventa.setFvTotal(FacturasVenta.getImporteTotalByFvId(facturasventa.getFvId()));
			facturasventa.actualiza();
				
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.guardar.algunos.duplicado"));						
	    		saveMessages(request.getSession(), messages);
	        }else if(resultado>0){
	        	//mensaje y mapeo de guardado o editado correcto
	        	messages.add("info", new ActionMessage("info.guardar.ok"));						
	    		saveMessages(request.getSession(), messages);
	        }
	        /**/
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}