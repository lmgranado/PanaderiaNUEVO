package panaderia.struts.informes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Productos;
import panaderia.informes.GenerarListadoProductosPDF;

public class InformeListadoProductosAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		try {
			String fam = request.getParameter("prodFamId");
			String media = request.getParameter("media");
			ArrayList productos = new ArrayList(); 
			if(media.equals(""))
				productos = Productos.getAllProductosConFamilia(fam);
			else
				productos = Productos.getAllProductosConFamiliaAndMedia(fam);
			GenerarListadoProductosPDF listado = new GenerarListadoProductosPDF(media);
			listado.generaListadoProductos(request, response, productos);
		} catch (Exception e) {
			System.out.println(e);
		}
		return forward;
	}
}