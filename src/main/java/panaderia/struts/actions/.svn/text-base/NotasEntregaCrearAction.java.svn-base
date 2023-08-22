package panaderia.struts.actions;

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

import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Stock;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.SQLManager;
import utils.Utils;
import utils.UtilsFacturacion;


public class NotasEntregaCrearAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		SQLManager manager = new SQLManager(); 
		Connection conexion = null;
	  	try{
	  		conexion = manager.getConnection(); 
	  		conexion.setAutoCommit(false);
			int resultado = -2;
			
			//recogemos el id de la entrega
			String entId = request.getParameter("entId");
			String fecha = request.getParameter("fechaNotas");
			
						
			//insertamos los registros en la tabla de notas entrega mediante un bucle
			ArrayList listaNotas = NotasEntrega.getAllNotasEntregaConversorByEntrega(entId,fecha);
			
			
			for(int i=0; i<listaNotas.size(); i++){
				String[] objNota = (String[])listaNotas.get(i);
				NotasEntrega nota = new NotasEntrega();
				//creamos la nota
				nota.setNotFecha(fecha,Utils.DATE_SHORT);
				nota.setNotNombre(objNota[1]);
				nota.setNotRutId(objNota[2]);
				nota.setNotClId(objNota[3]);
				nota.setNotOrden(objNota[4]);
				nota.setNotViaje(objNota[5]);
				nota.setNotCuId(objNota[7]);
				//Luis Miguel --> Hay que poner por defecto el Cierre de la nota de entrega a 0
				nota.setNotCierre("0");

				nota.setNotObservaciones(Utils.skipNull(request.getParameter("notObservaciones")));
				//Antes de insertar la nota de entrega realizamos una ultima comprobacion por si ya existe el orden de dicha orden y se recalcula el correcto
				nota.setNotOrden( NotasEntrega.getOrdenCorrecto(nota) );
				int notId = nota.inserta(conexion);
				if(notId<=0){
					throw new Exception("No se ha insertado la nota de entrega correctamente. Verifique que no esta intentando repetir el registro.");
				}
				String viaje = objNota[5];
				String endId = objNota[6];
				
				//dentro de ese bucle insertamos los detalles de las notas de entrega
				ArrayList listaNotasDetalle = 
					NotasEntregaDetalle.getAllNotasEntregaDetalleConversorByEntregaDetalle(endId, viaje);
				for(int j=0; j<listaNotasDetalle.size(); j++){
					NotasEntregaDetalle notaEntregaDetalle = (NotasEntregaDetalle)listaNotasDetalle.get(j);
					notaEntregaDetalle.setNotdetNotId(Integer.toString(notId));
					
					String lista = NotasEntregaDetalle.getNotasEntregaDetalleByFvdMaxLinea(Integer.toString(notId));
			    	int linea = Integer.parseInt(Utils.skipNullNumero(lista));
					
					String hayStock = Stock.hayStockNombreProducto(notaEntregaDetalle.getNotdetProducto(), "1", notaEntregaDetalle.getNotdetCantidad());
			  		if(Utils.skipNull(hayStock).equals(""))
			  			throw new Exception("No dispone de stock suficiente para introducir este producto en la nota de entrega");
			  		
			  		float cantidadTotal = Float.parseFloat( Utils.skipNullNumero( notaEntregaDetalle.getNotdetCantidad()));
			  		float cantidadParaFinalizar = cantidadTotal;
			  		ArrayList todoStock = Stock.getAllStockNoVacioNoCaducadoByProdNombre(notaEntregaDetalle.getNotdetProducto());
			  		float cantidad = 0;
			  		
			  		for(int k = 0; k<todoStock.size() && cantidadParaFinalizar>0; k++){
			  			Stock stock = (Stock)todoStock.get(k);
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
			  			
			  			notaEntregaDetalle.setNotdetLinea(Integer.toString(linea+k));
			  			notaEntregaDetalle.setNotdetStId(stock.getStId());
			  			notaEntregaDetalle.setNotdetCantidad( String.valueOf(cantidad) );
			  			float importe = UtilsFacturacion.getImporte(Float.parseFloat(notaEntregaDetalle.getNotdetPrecio()), Float.parseFloat(notaEntregaDetalle.getNotdetDescuento()), 0, cantidad);
			  			notaEntregaDetalle.setNotdetImporte( String.valueOf(importe) );
				    	
			  			resultado = notaEntregaDetalle.inserta(conexion);
			  		}
					
					if(resultado<=0){
						throw new Exception("No se ha insertado el detalle de la nota de entrega correctamente");
					}
				}
				conexion.commit();
			}
						
			messages.add("info", new ActionMessage("info.crearnota.ok"));	
			forward = mapping.findForward("mensaje_popup");
    		saveMessages(request.getSession(), messages);
		} catch (Exception ex){
			forward = mapping.findForward("mensaje_error");
			conexion.rollback();
			PanaderiaExceptionHandler.guardaLog(
					  ex,
	        		  mapping,
	        		  request);
		}finally{
		    conexion.rollback();
			manager.closeConnection();  
	  	}
		
		return forward;
	}
}