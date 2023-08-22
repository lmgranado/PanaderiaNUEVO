package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Rutas;


public class CargaRutasAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		ArrayList listaRutas = Rutas.getAllRutas();
		
		try{
			PrintWriter out = response.getWriter();
			out.print("__");
			out.print("[Seleccionar]"+"~");
			for(int i=0; i < listaRutas.size(); i++){
				Rutas ruta = (Rutas)listaRutas.get(i);
				out.print(ruta.getRutId()+"__");
				if(i == listaRutas.size())
					out.print(ruta.getRutNombre());
				else
					out.print(ruta.getRutNombre()+"~");
			}
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}