package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Localidades;


public class CargaProvinciasAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		String provincia = request.getParameter("provincia");
		ArrayList listaLocalidades = Localidades.getLocalidadesByProvIdCodigo(provincia);
		
		try{
			PrintWriter out = response.getWriter();
			for(int i=0; i < listaLocalidades.size(); i++){
				Localidades localidad = (Localidades)listaLocalidades.get(i);
				out.print(localidad.getLocId()+"__");
				if(i == listaLocalidades.size())
					out.print(localidad.getLocNombre());
				else
					out.print(localidad.getLocNombre()+"~");
			}
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}