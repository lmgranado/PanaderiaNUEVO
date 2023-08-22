package panaderia.struts.formularios.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.Cobradores;
import panaderia.beans.ComposicionProductos;
import panaderia.beans.FichasProductos;
import panaderia.beans.FormasPago;
import panaderia.beans.Localidades;
import panaderia.beans.PeriodosFacturacion;
import panaderia.beans.Pestana;
import panaderia.beans.ProductoComposicion;
import panaderia.beans.ProductoFicha;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;
import panaderia.struts.forms.EntidadBean;

import utils.Utils;


public class ProductoFichaFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			//String prodfId = Utils.skipNull(request.getParameter("prodfId"));
			String prodfProdId = Utils.skipNull(request.getParameter("prodfProdId"));
			request.setAttribute("prodfProdId", request.getParameter("prodfProdId"));
			
			/*if(prodfId.equals("")){
				prodfId = Utils.skipNull((String)request.getAttribute("prodfId"));
			}*/
			

			Productos producto = Productos.getProductosByProdId(prodfProdId);
			request.setAttribute("Producto", producto);	 
			
			
			ProductoFicha fichaproducto = ProductoFicha.getProductoFichaByProdfProdId(prodfProdId);
			//fichaproducto.setProdfProdId( prodfProdId );
			
			request.setAttribute("FichaProducto", fichaproducto);
			
			/*Listas necesarias para las opciones de los SELECTS*/
			if( !Utils.empty(fichaproducto.getProdfId()) ){
				ArrayList listaComposicionFicha = ProductoComposicion.getAllComposicionProductosByProdCompprodFId( fichaproducto.getProdfId() );
				request.setAttribute("LISTACOMPOSICIONFICHA", listaComposicionFicha);
			}
			
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			/* Construccion de la botonera*/
			int intOrdenBot = 0;
			botonera.addBoton(intOrdenBot++, bBotoneraFormulario.GUARDAR);
			Boton boton = bBotoneraFormulario.VOLVER;
			boton.setJavascript("javascript:doVolver('doListadoProductos.do');");
			botonera.addBoton(intOrdenBot++, boton);
			request.setAttribute("BOTONERA", botonera.getBotones());
						
			/* Construcción de las pestañas */
			int intOrden = 0;
			bPestanaFormulario bPestana = new bPestanaFormulario();
			bPestana.addPestana(intOrden++, new Pestana("doProductosFormulario.do?prodId=" + Utils.skipNull(prodfProdId),"Productos","","","",""));
			bPestana.addPestana(intOrden++, new Pestana("doListadoComponentesProducto.do?cpProdIdFabricado=" + Utils.skipNull(prodfProdId),"Componentes","","","",""));
			bPestana.addPestana(intOrden++, new Pestana("doProductoFichaFormulario.do?prodfProdId=" + Utils.skipNull(prodfProdId),"Ficha","","","","1"));

			request.setAttribute("PESTANAS", bPestana.getPestanas());
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}