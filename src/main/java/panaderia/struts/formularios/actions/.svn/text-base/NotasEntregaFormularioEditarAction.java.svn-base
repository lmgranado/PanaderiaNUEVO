package panaderia.struts.formularios.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

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
import panaderia.utilidades.PanaderiaExceptionHandler;
import panaderia.utilidades.Utiles;
import utils.SQLManager;
import utils.Utils;
import utils.UtilsFacturacion;

public class NotasEntregaFormularioEditarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			Enumeration enumera = request.getParameterNames();
			while(enumera.hasMoreElements()){
				String nombreCampo = (String)enumera.nextElement();
				//En el caso que el campo sea de cantidad, tomamos del nombre del campo el 
				//id del detalle de la nota
				StringTokenizer token = new StringTokenizer(nombreCampo, "_");
				if(token.nextToken().equals("cantidad")){
					
					SQLManager manager = new SQLManager(); 
					Connection conexion = null;
				  	try{
				  		conexion = manager.getConnection(); 
				  		conexion.setAutoCommit(false);
				  		
						String notdetId = token.nextToken();
						String valor = request.getParameter(nombreCampo);
						NotasEntregaDetalle notaEntregaDetalle = NotasEntregaDetalle.getNotasEntregaDetalleByNotdetId(notdetId);
						
						if(Utils.skipNull(notaEntregaDetalle.getNotdetStId()).equals(""))
							throw new Exception("El producto que intenta editar no tiene lote");
						
						//Primero debemos ver si se puede editar x el tema del stock
						String cantidadACalcularStock = Double.toString( Double.parseDouble(valor) - Double.parseDouble(notaEntregaDetalle.getNotdetCantidad())); //sera la cantidad nueva menos la actual, si es negativo hacemos una cosa, positivo otra
						if(Double.parseDouble(valor) - Double.parseDouble(notaEntregaDetalle.getNotdetCantidad()) == 0){
							//No hacemos nada
							continue;
						}else if(Double.parseDouble(valor) - Double.parseDouble(notaEntregaDetalle.getNotdetCantidad())<0){
							//Si es negativo añadimos la cantidad al stock
							Stock stock = Stock.getStockByStId(notaEntregaDetalle.getNotdetStId());
							stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) + 
													 Double.parseDouble(notaEntregaDetalle.getNotdetCantidad()) - 
													 Double.parseDouble(valor)));
							double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) + Double.parseDouble(valor) - Double.parseDouble(notaEntregaDetalle.getNotdetCantidad());
			  				stock.setStSalidas(Double.toString(cantidadSalidas));
							stock.actualiza(conexion);
							
						}else{
							//Si es positivo quitamos la parte del stock de la cantidad final
							Stock stock = Stock.getStockByStId(notaEntregaDetalle.getNotdetStId());
							if(Double.parseDouble(stock.getStCantidadFinal()) < Double.parseDouble(cantidadACalcularStock))
					  			throw new Exception("No dispone de stock suficiente para editar este producto en la nota de entrega: " + notaEntregaDetalle.getNotdetProducto());
							stock.setStCantidadFinal(Double.toString(Double.parseDouble(stock.getStCantidadFinal()) - Double.parseDouble(cantidadACalcularStock)));
							double cantidadSalidas = Double.parseDouble(stock.getStSalidas()) + Double.parseDouble(valor) - Double.parseDouble(notaEntregaDetalle.getNotdetCantidad());
			  				stock.setStSalidas(Double.toString(cantidadSalidas));
							stock.actualiza(conexion);
						}
						notaEntregaDetalle.setNotdetCantidad(valor);
						resultado = NotasEntregaDetalle.editaNotaDetalle(notaEntregaDetalle, conexion);
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
			
			//En el caso de incidencias ponemos el key que estamos usando (esta parte del codigo hay
			//que usarla en todos los sitios donde se guarde o edite los detalles
			if(request.getSession().getAttribute("banderaIncidenciasNotas")!=null){
				Map mapaNotas = (Hashtable)request.getSession().getAttribute("mapaNotas");
				if(mapaNotas.containsValue(request.getParameter("notId"))){
					String notIdKey = Utiles.returnKeymapByValue(mapaNotas, request.getParameter("notId"));
					request.setAttribute("notId", notIdKey);
				}
			}
				
			//Se introduce para que pueda modificar las observaciones de la nota de entrega en cualquier momento
			String notObservaciones = request.getParameter( "notObservaciones" );
			NotasEntrega nota = NotasEntrega.getNotasEntregaByNotId( request.getParameter("notId") );
			nota.setNotObservaciones( notObservaciones );
			nota.actualiza();
			
			//Si viene cuadrante, añadimos los productos y sus precios y cantidad en funcion del viaje
			//Aqui tambien hay que añadir el stock
			if(!Utils.skipNull(request.getParameter("cuadran")).equals("")){
				ArrayList listaNotasDetalle = 
					NotasEntregaDetalle.getAllNotasEntregaDetalleConversorByCuadrante(request.getParameter("cuadran"), nota.getNotViaje(), nota.getNotId());
				SQLManager manager = new SQLManager(); 
				Connection conexion = null;
			  	try{
			  		resultado = -1;
			  		conexion = manager.getConnection(); 
			  		conexion.setAutoCommit(false);
					for(int j=0; j<listaNotasDetalle.size(); j++){
						
						NotasEntregaDetalle notaEntregaDetalle = (NotasEntregaDetalle)listaNotasDetalle.get(j);
						
						String hayStock = Stock.hayStockNombreProducto(notaEntregaDetalle.getNotdetProducto(), "1", notaEntregaDetalle.getNotdetCantidad());
				  		if(Utils.skipNull(hayStock).equals(""))
				  			throw new Exception("No dispone de stock suficiente para introducir este producto en la nota de entrega: " + notaEntregaDetalle.getNotdetProducto());
						
				  		
				  		
				  		//Ahora tomamos todo el stock, y finalizamos de insertar cuando la cantidad sea 0
				  		float cantidadTotal = Float.parseFloat(Utils.skipNullNumero( notaEntregaDetalle.getNotdetCantidad()));
				  		float cantidadParaFinalizar = cantidadTotal;
				  		float cantidad = 0;
				  		ArrayList todoStock = Stock.getAllStockNoVacioNoCaducadoByProdNombre(notaEntregaDetalle.getNotdetProducto());
				  		
				  		for(int i = 0; i<todoStock.size() && cantidadParaFinalizar>0; i++){
				  			Stock stock = (Stock)todoStock.get(i);
				  			float cantidadDeEsteStock = Float.parseFloat(stock.getStCantidadFinal());
				  			if(cantidadParaFinalizar>cantidadDeEsteStock){
				  				cantidad = cantidadDeEsteStock;
				  				cantidadParaFinalizar = cantidadParaFinalizar - cantidadDeEsteStock;
				  				stock.setStCantidadFinal("0");
				  				stock.actualiza(conexion);
				  			}else{
				  				cantidad = cantidadParaFinalizar;
				  				stock.setStCantidadFinal(Float.toString(cantidadDeEsteStock - cantidadParaFinalizar));
				  				stock.actualiza(conexion);
				  				cantidadParaFinalizar = 0;
				  			}
	
							notaEntregaDetalle.setNotdetNotId(nota.getNotId());
							notaEntregaDetalle.setNotdetLinea(Integer.toString(j+i));
							notaEntregaDetalle.setNotdetStId(stock.getStId());
							notaEntregaDetalle.setNotdetCantidad( String.valueOf(cantidad) );
							float importe = UtilsFacturacion.getImporte(Float.parseFloat(notaEntregaDetalle.getNotdetPrecio()), Float.parseFloat(notaEntregaDetalle.getNotdetDescuento()), 0, cantidad);
							notaEntregaDetalle.setNotdetImporte( String.valueOf(importe) );
							resultado = notaEntregaDetalle.inserta(conexion);
								
				  		}
					}
					conexion.commit();
			  	}catch(Exception e){
			  		conexion.rollback();
			  		throw e;
			  	}finally{
				    conexion.rollback();
					manager.closeConnection();  
			  	}
			}
			
			
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
			PanaderiaExceptionHandler.guardaLog(
					  e,
	        		  mapping,
	        		  request);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}