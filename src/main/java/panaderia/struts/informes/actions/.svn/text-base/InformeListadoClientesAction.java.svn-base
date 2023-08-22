package panaderia.struts.informes.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.informes.GenerarListadoClientesPDF;

public class InformeListadoClientesAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		
		ActionForward forward = null;
		try {
	      ArrayList clientes = Clientes.getAllClientes();
	      GenerarListadoClientesPDF listado = new GenerarListadoClientesPDF();      
	      listado.generaListadoClientes( request, response, clientes ); 
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}