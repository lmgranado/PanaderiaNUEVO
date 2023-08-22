package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Boton;
import panaderia.beans.Familias;
import panaderia.beans.Productos;
import panaderia.beans.Pestana;
import panaderia.negocio.bBotoneraFormulario;
import panaderia.negocio.bPestanaFormulario;

import utils.Utils;


public class ProductosFormularioAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		try{
			String prodId = Utils.skipNull(request.getParameter("prodId"));
			Productos producto = Productos.getProductosByProdId(prodId);
			request.setAttribute("Producto", producto);
			bBotoneraFormulario botonera = new bBotoneraFormulario();	
			request.setAttribute("LISTAFAMILIAS", Familias.getAllFamilias());
						
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
			bPestana.addPestana(intOrden++, new Pestana("doProductosFormulario.do?prodId=" + Utils.skipNull(prodId),"Productos","","","","1"));
			if( !Utils.empty(prodId) && producto.getProdDeVenta().equals("1")){
				bPestana.addPestana(intOrden++, new Pestana("doListadoComponentesProducto.do?cpProdIdFabricado=" + Utils.skipNull(prodId),"Componentes","","","",""));
				bPestana.addPestana(intOrden++, new Pestana("doProductoFichaFormulario.do?prodfProdId=" + Utils.skipNull(prodId),"Ficha","","","",""));
				
			}
			request.setAttribute("PESTANAS", bPestana.getPestanas());
			
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}