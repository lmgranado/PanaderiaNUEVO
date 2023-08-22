package panaderia.struts.actions;

import java.util.ArrayList;

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
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Familias;
import panaderia.beans.FichasProductos;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.ProductoComposicion;
import panaderia.beans.ProductoFicha;
import panaderia.beans.Productos;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.Utils;


public class CopiaFichaAProductosAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		int resultado = -2;
		try{
			//recogemos el id de la ficha de producto a sobreescribir
			String fprodId = request.getParameter("fprodId");
			String famId = request.getParameter("famId");
			
			//Obtengo los productos de dicha familia a los que tengo que asignarle los datos de la ficha			
			ArrayList productos = Productos.getListaProductosByProdFamId( famId );
			
			//Tomamos los datos de la ficha técnica que queremos replicar en las fichas de los productos
			ProductoFicha productoFicha = ProductoFicha.getConversorFichaProductosAProductoFicha(fprodId);
			ArrayList listaComposicion = ProductoComposicion.getConversorComposicionProductosAProductoComposicion( fprodId );		
			
			for(int i=0; i<productos.size(); i++){
				Productos producto = (Productos) productos.get(i);
				
				//Tomamos las fichas de los productos que hay actualmente y las eliminamos antes de asociarles las nuevas.
				ProductoFicha prodFicha = ProductoFicha.getProductoFichaByProdfProdId( producto.getProdId() );
				ArrayList listaProdComp = ProductoComposicion.getAllComposicionProductosByProdCompprodFId( prodFicha.getProdfId() );
				for(int k=0; k<listaProdComp.size(); k++){
					ProductoComposicion prodComp = (ProductoComposicion) listaProdComp.get(k);
					prodComp.elimina();
				}
				prodFicha.elimina();
				
				//Ahora insertamos los datos de la nueva ficha técnica
				productoFicha.setProdfProdId( producto.getProdId() );
				int prodfId = productoFicha.inserta();
				
				//Ahora insertamos la composicion de dicha ficha con el prodfId del nuevo registro insertado
				for(int j=0; j<listaComposicion.size(); j++){
					//if( !Utils.empty(composicion.getProdcompId()) ){
					ProductoComposicion composicion = (ProductoComposicion) listaComposicion.get(j);
					composicion.setProdcompProdfId( String.valueOf(prodfId) );
					composicion.inserta();
				}
			}
				
			//Actualizamos la familia con su ficha correspondiente
			Familias familia = Familias.getFamiliasByFamId( famId );
			familia.setFamFprodId( fprodId );
			familia.actualiza();
			
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
			
		} catch (Exception ex){
			forward = mapping.findForward("error");
			PanaderiaExceptionHandler.guardaLog(
					  ex,
	        		  mapping,
	        		  request);
		}
		return forward;
	}
}