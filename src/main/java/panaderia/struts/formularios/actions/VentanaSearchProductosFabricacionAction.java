package panaderia.struts.formularios.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;

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
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import utils.SQLManager;
import utils.Utils;


public class VentanaSearchProductosFabricacionAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		
		try{
	        //1.- Obtenemos el producto y la cantidad que queremos fabricar
			ArrayList listaFinal = new ArrayList();
			String productoAFabricar = request.getParameter("prodId");
			String cantidadAFabricar = request.getParameter("prodCantidad");
			String stFCaducidad = request.getParameter("stFCaducidad");
			String fcId = request.getParameter("fcId");
			String fcdLote = request.getParameter("fcdLote");
			Productos productoF = Productos.getProductosByProdId(productoAFabricar);
			//FacturasCompraDetalle fcd = FacturasCompraDetalle.getFacturasCompraDetalleByFcdProductoAndFcdLote(productoF.getProdNombre(), fcdLote);
			
			//Luis Miguel 040814 -> para que se pueda añadir mas de un producto a la lista
			/*if(!Utils.skipNull( fcd.getFcdId()).equals("")){
				request.setAttribute("excepcion", "Se ha producido un error durante la fabricación de este producto. Ya existe ese lote para ese producto.");
				return mapping.findForward("ok");
			}*/
			//2.- Cogemos todos los productos necesarios para la fabricación 
			ArrayList listaComposiciom = ComponentesProducto.getComponentesProductoByCpProdIdFabricado(productoAFabricar, cantidadAFabricar);
  			if( listaComposiciom.size()==0 )
  				throw new Exception("El producto no tiene configurados sus componentes para la fabricación.");
  			
			for(int i = 0;  i<listaComposiciom.size(); i++){
  				String[] cproducto = (String[])listaComposiciom.get(i); //0 producto, 1 cantidad individual, 2 cantidad total
  				
  				//3.- Mandamos un listado con cada uno de esos productos, la cantidad necesario para fabricar uno y la cantidad que se puede fabricar con él
  				Productos prod = Productos.getProductosByProdId(cproducto[0]);
  				
  				//Creamos un array con el nombre del producto, el id del mismo, y la lista con todo el stock de ese producto para que lo seleccione
  				
  				//Cambio de linea Luis Miguel -> 26/07/14
  				//ArrayList listaStock = Stock.getAllStockNoVacioNoCaducadoByStProdId(cproducto[0], cproducto[1]);
  				
  				//Si no existe Stock de cualquiera de los componentes que tiene el producto a fabricar, elevamos una excepcion
  				ArrayList listaStock = Stock.getAllStockNoVacioNoCaducadoByStProdId(cproducto[0]);
  				if( listaStock.size() == 0 )
  					throw new Exception("No tiene Stock del producto: " + prod.getProdNombre());
  				
  				Object array[] = {prod.getProdNombre(), prod.getProdId(), listaStock};//{idDelStock, lote, cantidadTotalDelStock,fcaducidad,cantidadquesepuedefabricar}
  				listaFinal.add(array); 
  			}
			
			
  			request.setAttribute("listaParaFabricar",listaFinal);
  			request.setAttribute("prodCantidad", cantidadAFabricar);
  			request.setAttribute("prodId", productoAFabricar);
  			request.setAttribute("fcId", fcId);
  			request.setAttribute("stFCaducidad", stFCaducidad);
  			request.setAttribute("fcdLote", fcdLote);
  			
  			//Comprobamos si viene el formulario y vamos a fabricacion
  			if( Utils.skipNull(request.getParameter("accion")).equals("fabricar") ){
  				//COMENZAMOS A GUARDAR LOS DATOS
  				
  				//todo esto debemos hacerlo en una transacción, si algo falla rollback.
  				SQLManager manager = new SQLManager(); 
  				Connection conexion = manager.getConnection(); 
  				ActionMessages messages = new ActionMessages();
  				int resultado = -2;
  			    try
  			    {
  			    	conexion.setAutoCommit(false);
  			    	
  			    	ArrayList listaStock = Stock.getStockByStProdIdAndStProdLote( productoAFabricar, fcdLote );
  			    	
  			    	Stock nuevoStock = new Stock();
  			    	int idNuevoStock = -1;
  			    	
  			    	/* Si ya existe el producto a Fabricar con el mismo lote, y sus productos utilizados 
  			    	 * tambien son los mismos, se actualiza tanto la TABLA STOCK como la TABLA COMPOSICION_FABRICACION.
  			    	 * Sin embargo, si el lote es el mismo pero los productos utilizados para la fabricacion son diferentes
  			    	 * entonces creamos un nuevo registro tanto en la TABLA DE STOCK como en la TABLA DE COMPOSICION_FABRICACION 
  			    	*/
  			    	
  			    	//Si el producto y lote ya existen en la tabla de Stock
  			    	if( listaStock.size() > 0 ){
  			    	    //Antes de nada, como necesitamos el id, insertamos el nuevo producto
  			    		Stock stockExistente = (Stock) listaStock.get(0);
  			    		idNuevoStock = Integer.parseInt( stockExistente.getStId() );
  			    		
  			    		//Comprobamos si los componentes y lotes de dichos productos son los mismos que vamos a fabricar y entonces
  			    		//actualizamos tanto el producto en la tabla stock como sus componentes a la Tabla Composicion_fabricacion. En caso
  			    		//contrario elevamos una excepcion ya que no puede existir la fabricacion de un mismo producto que tenga el mismo lote
  			    		//y que tenga una composicion de productos de diferentes lotes.
  			    		if( !ComposicionFabricacion.tieneLosMismosComponentes( request, idNuevoStock) )
  			    			throw new Exception("El producto con dicho Lote ya existe y su composicion tiene productos con lotes diferentes.");
  			    			
  			    		
  			    		//CAMBIAR PARA SUMAR CONVERTIRLOS EN DOUBLE
  			    		double cantidadtotalInicial  =  Double.parseDouble(cantidadAFabricar) + Double.parseDouble(stockExistente.getStCantidadInicial());
  			    		double cantidadtotalFinal = Double.parseDouble(cantidadAFabricar) + Double.parseDouble(stockExistente.getStCantidadFinal());
  			    		
  			    		stockExistente.setStCantidadInicial( String.valueOf(cantidadtotalInicial) );
  			    		stockExistente.setStCantidadFinal( String.valueOf(cantidadtotalFinal) );
  			    		stockExistente.setStFCaducidad( stFCaducidad, Utils.DATE_SHORT );
  			    		stockExistente.setStProdLote( fcdLote );
  			    		stockExistente.actualiza(conexion);
  			    		
  			    	}else{
  			    	//Antes de nada, como necesitamos el id, insertamos el nuevo producto
	  			    	nuevoStock.setStCantidadInicial(cantidadAFabricar);
	  			    	nuevoStock.setStCantidadFinal(cantidadAFabricar);
	  			    	nuevoStock.setStFCaducidad(stFCaducidad, Utils.DATE_SHORT);
	  			    	nuevoStock.setStProdId(productoAFabricar);
	  			    	nuevoStock.setStProdLote( fcdLote );
	  			    	idNuevoStock = nuevoStock.inserta(conexion);
  			    	}
  			    	
  			    	//Modificamos el stock de los elementos usados
  			    	Enumeration enumera = request.getParameterNames();
  			    	while(enumera.hasMoreElements()){
  			    		String nombrePS = (String)enumera.nextElement();
  			    		
  			    		if( nombrePS.indexOf("producto_") != -1 ){
  			    			
  			    			Stock stockElegido = Stock.getStockByStId(request.getParameter(nombrePS));
  			    			
  			    			//Le quitamos al stock la cantidad Usado
  			    			double cantidadFinalActual = Double.parseDouble(stockElegido.getStCantidadFinal());
  			    			
  			    			//Ahora vemos cuanto hace falta de este stock para fabricar
  			    			ComponentesProducto cp = ComponentesProducto.getComponentesProductoByCpProdIdFabricadoAndUsado(nombrePS.replaceAll("producto_", ""), productoAFabricar);
  			    			
  			    			//double cantidadNecesaria = Double.parseDouble(cp.getCpCantidad()) * Integer.parseInt(cantidadAFabricar);
  			    			//26/08/2015 -- CAMBIO PARA QUE COJA CORRECTAMENTE LA CANTIDAD CUANDO ES POR EJEMPLO PIMENTON EN BOLSAS DE 5 KILOS,
  			    			//EL PESO TOTAL LO CONTEMPLAMOS COMO FORMAS DE VENTA PARA REALIZAR BIEN EL CALCULO, SI LA FORMA DE VENTA ES 5
  			    			// SE DIVIDE LA CANTIDAD A FABRICAR POR EL FORMATO DE VENTA
  			    			Productos prod = Productos.getProductosByProdId( cp.getCpProdIdFabricado() ); 			    			
  			    			double cantidadNecesaria = Double.parseDouble(cp.getCpCantidad()) * ( Double.parseDouble(cantidadAFabricar) / Double.parseDouble(prod.getProdPesoTotal()) );
  			    			
  			    			//Si no hay suficiente cantidad no se puede crear el producto fabricado y lanzamos una excepcion
  			    			if( cantidadNecesaria > cantidadFinalActual ){
  			    				 Productos producto = Productos.getProductosByProdId( stockElegido.getStProdId() );
  			    				 throw new Exception("No existe suficiente cantidad de <b>"+ producto.getProdNombre() +"</b> del LOTE <b>" +stockElegido.getStProdLote() + "</b>");
  			    			}
  			    			
  			    			stockElegido.setStCantidadFinal(Double.toString(cantidadFinalActual-cantidadNecesaria));
  			    			double cantidadSalidas = Double.parseDouble(stockElegido.getStSalidas()) + cantidadNecesaria;
  			    			stockElegido.setStSalidas(Double.toString(cantidadSalidas));
  			    			stockElegido.actualiza(conexion);
  			    			
  			    			//Si existe el producto actualizamos su tabla de composicionFabricacion
  			    			if( listaStock.size() > 0 ){
	  			    			ComposicionFabricacion cfExistente = ComposicionFabricacion.getComposicionFabricacionByCfIdStFabricadoyCfIdStUsado( String.valueOf(idNuevoStock), request.getParameter(nombrePS) );
	  			    			cfExistente.setCfCantidad( Double.toString( ( cantidadNecesaria) + Double.valueOf(cfExistente.getCfCantidad()).doubleValue() ) );
	  			    			cfExistente.actualiza(conexion);
  			    				
	  			    		//En caso contrario insertamos un nuevo registro en la tabla ComposicionFabricacion	
  			    			}else{
  			    				ComposicionFabricacion cf = new ComposicionFabricacion();
	  			    			cf.setCfCantidad( Double.toString(cantidadNecesaria) );
	  			    			cf.setCfIdStFabricado( Double.toString(idNuevoStock) );
	  			    			cf.setCfIdStUsado( request.getParameter(nombrePS) );
	  			    			cf.inserta(conexion);
  			    			}
  			    		}
  			    	}
  			    	
  			    	//POR ULTIMO INSERTAMOS LOS ELEMENTOS EN LA FACTURA DE COMPRA
  			    	FacturasCompraDetalle detalleFactura = new FacturasCompraDetalle();
  			    	Productos prod = Productos.getProductosByProdId(productoAFabricar);
  			    	detalleFactura.setFcdCantidad(cantidadAFabricar);
  			    	detalleFactura.setFcdFCaducidad(stFCaducidad, Utils.DATE_SHORT);
  			    	detalleFactura.setFcdFEntrada(new Timestamp(Utils.getFechaActualSinHora().getTime()));
  			    	detalleFactura.setFcdLote(fcdLote);
  			    	detalleFactura.setFcdProducto(prod.getProdNombre());
  			    	detalleFactura.setFcdStId(Integer.toString(idNuevoStock));
  			    	detalleFactura.setFcdFcId(fcId);
  			    	detalleFactura.setFcdReferencia( prod.getProdReferencia() );	
  			    	String lista = FacturasCompraDetalle.getFacturasCompraDetalleByFvdMaxLinea(request.getParameter("fcId"));
  			    	int linea = Integer.parseInt(Utils.skipNullNumero(lista));
  			    	detalleFactura.setFcdLinea(Integer.toString(linea + 1));
  			    	resultado = detalleFactura.inserta(conexion);
  			    	
  			    	//Al final hacemos commit;
  			    	conexion.commit();
  			    }
  			    catch( SQLException e ){ 
  			    	request.setAttribute("excepcion", "Se ha producido un error durante la fabricación de este producto");
  			    	if(e.toString().indexOf("fcd_unico_lote_uk")!=-1)
  			    		request.setAttribute("excepcion", "Se ha producido un error durante la fabricación de este producto. Ya existe ese producto con ese mismo lote.");
  			    	conexion.rollback();
  			    }
  			    finally{
  			    	conexion.rollback();
  			    	manager.closeConnection();  
  			    }
  			    
  				if(resultado == 0 || resultado == -1){
  		        	//mensaje de guardado
  		        	messages.add("info", new ActionMessage("info.guardar.algunos"));						
  		    		saveMessages(request.getSession(), messages);
  		        }else if(resultado>0){
  		        	//mensaje y mapeo de guardado o editado correcto
  		        	messages.add("info", new ActionMessage("info.guardar.ok"));						
  		    		saveMessages(request.getSession(), messages);
  		        }
  			}
    		
		}catch (Exception e) {
			request.setAttribute("excepcion", e.toString());
			System.out.println(e);
		}
		
		ActionForward forward = null;
		forward = mapping.findForward("ok");
		
		return forward;
	}
}