package panaderia.struts.informes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Productos;
import panaderia.informes.GenerarListadoPreciosPDF;

public class InformeListadoPreciosAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		try {
			String tipo = request.getParameter("tipo");
			String cliente = request.getParameter("cliente");
			String familia = request.getParameter("familia");
			String producto = request.getParameter("producto");
			ArrayList productos = Productos.getAllProductosPrecios(tipo, cliente, familia, producto);
			GenerarListadoPreciosPDF listado = new GenerarListadoPreciosPDF(tipo);
			listado.generaListadoPrecios(request, response, productos);
		} catch (Exception e) {
			System.out.println(e);
		}
		return forward;
	}
}