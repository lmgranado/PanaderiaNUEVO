package panaderia.struts.informes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Proveedores;
import panaderia.informes.GenerarListadoProveedoresPDF;

public class InformeListadoProveedoresAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		try {
			ArrayList proveedores = Proveedores.getAllProveedores();
			GenerarListadoProveedoresPDF listado = new GenerarListadoProveedoresPDF();
			listado.generaListadoProveedores(request, response, proveedores);
		} catch (Exception e) {
			System.out.println(e);
		}
		return forward;
	}
}