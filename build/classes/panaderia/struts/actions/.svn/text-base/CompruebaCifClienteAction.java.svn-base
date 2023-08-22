package panaderia.struts.actions;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.Localidades;
import utils.Utils;


public class CompruebaCifClienteAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		
		try{
			PrintWriter out = response.getWriter();
			int existe = 0;
			String clId = request.getParameter( "clId" );
			String clNif = request.getParameter( "clNif" );
			//Estoy editando
			if(!Utils.skipNull(clId).equals("") ){
				Clientes cliente = Clientes.getClientesByClNifYClId( clNif, clId );
				if(!Utils.skipNull(cliente.getClId()).equals("") ){ existe = 1; }
			}
			//Estoy en nuevo
			else{
				Clientes cliente = Clientes.getClientesByClNif( clNif );
				if(!Utils.skipNull(cliente.getClId()).equals("")) { existe = 0; }
			}
			out.print( existe );
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");	
		}
		
		return forward;
	}
}