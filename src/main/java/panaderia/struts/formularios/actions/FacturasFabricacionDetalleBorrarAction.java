package panaderia.struts.formularios.actions;

import java.math.BigDecimal;
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

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import panaderia.beans.ComponentesProducto;
import panaderia.beans.ComposicionFabricacion;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import utils.SQLManager;


public class FacturasFabricacionDetalleBorrarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		String[] chkValues = request.getParameterValues("checkList");
		boolean hayVentaMasQueCompra = false;
		
		for(int i=0; i<chkValues.length; i++){
			//Eliminamos los marcados de nuestra lista de detalles
			//Ademas de eliminar debemos elimirlo de nuestro stock y añadirlo al resto del stock usado
			FacturasCompraDetalle detalle = FacturasCompraDetalle.getFacturasCompraDetalleByFcdId( chkValues[i] );
			FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId(detalle.getFcdFcId());
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
			    	hayVentaMasQueCompra = Double.parseDouble(detalle.getFcdCantidad()) > Double.parseDouble(stock.getStCantidadFinal());
			    	
			    	if( !hayVentaMasQueCompra ){
			    	
					    	//Ahora actualizamos la composicion de fabricacion
					    	ArrayList listaCp = ComposicionFabricacion.getComposicionFabricacionByCfIdStFabricado(stock.getStId());
					    	for(int w=0; w<listaCp.size(); w++){
					    		ComposicionFabricacion cf = (ComposicionFabricacion)listaCp.get(w);
					    		Stock stockUsado = Stock.getStockByStId(cf.getCfIdStUsado());
						    	ComponentesProducto cp = ComponentesProducto.getComponentesProductoByCpProdIdFabricadoAndUsado(stockUsado.getStProdId(), stock.getStProdId());
						    	
						    	//double cantidadNecesaria = Double.parseDouble(cp.getCpCantidad()) * Double.parseDouble(detalle.getFcdCantidad());
						    	
	  			    			//26/08/2015 -- CAMBIO PARA QUE COJA CORRECTAMENTE LA CANTIDAD CUANDO ES POR EJEMPLO PIMENTON EN BOLSAS DE 5 KILOS,
	  			    			//EL PESO TOTAL LO CONTEMPLAMOS COMO FORMAS DE VENTA PARA REALIZAR BIEN EL CALCULO, SI LA FORMA DE VENTA ES 5
	  			    			// SE DIVIDE LA CANTIDAD A FABRICAR POR EL FORMATO DE VENTA
						 		Productos prod = Productos.getProductosByProdId( cp.getCpProdIdFabricado() ); 
						    	double cantidadNecesaria = Double.parseDouble(cp.getCpCantidad()) * ( Double.parseDouble(detalle.getFcdCantidad()) / Double.parseDouble(prod.getProdPesoTotal()) );
	  			    			
						    							    	
				    			 //REVISAR SI COGE BIEN LOS 4 DECIMALES PARA QUE NO FALLE EL STOCK
				    			 cantidadNecesaria = new BigDecimal(cantidadNecesaria).setScale( 4 ,BigDecimal.ROUND_HALF_DOWN ).doubleValue();
				    			//cantidadNecesaria = Double.parseDouble( utils.Utils.redondea(String.valueOf( cantidadNecesaria ), 4) );
				    			//CAMBIAR A FUNCION REDONDEA A 4 DECIMALES Hacer redondeo de 3 decimales en Java para que no haga negativa la cantidad
				    			//if( ( Double.parseDouble( cf.getCfCantidad() ) - cantidadNecesaria )  < 0 )
				    				//cantidadNecesaria = 0;
				    			
				    			cf.setCfCantidad (Double.toString(Double.parseDouble(cf.getCfCantidad()) - cantidadNecesaria) );
				    			
				    			//HAY ALGUN ERROR EN LA ELIMINACION DE LA COMPOSICION_FABRICACION
				    			if( Double.parseDouble(cf.getCfCantidad()) < 0 )
				    				throw new Exception("HAY ALGUN ERROR EN LA ELIMINACION DE LA COMPOSICION_FABRICACION. CANTIDAD < 0");
				    				
				    			if(Double.parseDouble(cf.getCfCantidad())==0)
				    				cf.elimina(conexion);
				    			else
				    				cf.actualiza(conexion);
				    			
				    			//Ademas debemos actualizar el stock usado
				    			stockUsado.setStCantidadFinal(Double.toString(Double.parseDouble(stockUsado.getStCantidadFinal()) + cantidadNecesaria));
				    			double cantidadSalidas = Double.parseDouble(stockUsado.getStSalidas()) - cantidadNecesaria;
				    			stockUsado.setStSalidas(Double.toString(cantidadSalidas));
				    			stockUsado.actualiza(conexion);
					    		}
					    	
						    	
					    	//Actualizamos el stock
					    	stock.setStCantidadInicial(Double.toString(Double.parseDouble(stock.getStCantidadInicial()) - Double.parseDouble(detalle.getFcdCantidad())));
					    	stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) - Double.parseDouble(detalle.getFcdCantidad())));
					    	
					    	//Si la cantidad final es 0 eliminamos
					    	if(Double.parseDouble(stock.getStCantidadFinal())==0)
					    		resultado = stock.elimina(conexion);
					    	else
					    		resultado = stock.actualiza(conexion);
					    	
					    	conexion.commit();
			    	}
			    }
  			    catch( SQLException e ){ 
  			    	request.setAttribute("excepcion", "Se ha producido un error durante la eliminación de este producto " + e);
  			    	conexion.rollback();
  			    }
  			    finally{
  			    	manager.closeConnection();  
  			    }
		}
		
		request.setAttribute("facturacompra", FacturasCompra.getFacturasCompraByFcId(request.getParameter("fcId")));
		
		
		if(resultado == 0 || resultado == -1){
        	//mensaje de guardado
        	messages.add("info", new ActionMessage("info.delete.algunos"));						
    		saveMessages(request.getSession(), messages);
        }else if(hayVentaMasQueCompra){
        	//mensaje y mapeo de guardado o editado correcto
			messages.add("info", new ActionMessage("info.eliminar.producto.compra"));				
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