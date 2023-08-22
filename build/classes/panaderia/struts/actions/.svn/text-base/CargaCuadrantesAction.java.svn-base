package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Cuadrantes;


public class CargaCuadrantesAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		String idCliente = request.getParameter("clId");
		ArrayList listaCuadrantes = Cuadrantes.getCuadrantesByClId(idCliente);
		
		try{
			PrintWriter out = response.getWriter();
			out.print("__");
			out.print("[Seleccionar]"+"~");
			for(int i=0; i < listaCuadrantes.size(); i++){
				Cuadrantes cuadrante = (Cuadrantes)listaCuadrantes.get(i);
				out.print(cuadrante.getCuId()+"__");
				if(i == listaCuadrantes.size())
					out.print(cuadrante.getCuNombre());
				else
					out.print(cuadrante.getCuNombre()+"~");
			}
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}