package panaderia.utilidades;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import utils.Log4j;

public class PanaderiaExceptionHandler extends ExceptionHandler {
	      
    public ActionForward execute(Exception e, ExceptionConfig eConf, ActionMapping mapping,
            ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
       
        // Informamos mediante logging acerca de la excepción ocurrida
    	Log4j.error("**********");
        Log4j.error("Global Error: ", e);
        if(request.getAttribute("nProy")!=null) Log4j.error("Global Proyecto: " + request.getAttribute("nProy"));
        if(request.getParameter("nProy")!=null) Log4j.error("Global Proyecto: " + request.getParameter("nProy"));
        Log4j.error("Global Error Mapeo: " + mapping );
        Log4j.error("Global Error Direccion: " + mapping.getInputForward());
        Log4j.error("**********");
        
        request.setAttribute("ServletException", e);
        return new ActionForward(eConf.getPath());
    }
    
    public static void guardaLog(Exception e, ActionMapping mapping, HttpServletRequest request){
    	//Informamos mediante logging acerca de la excepción ocurrida
    	Log4j.error("**********");
        Log4j.error("Global Error: ", e);
        if(request.getAttribute("nProy")!=null) Log4j.error("Global Proyecto: " + request.getAttribute("nProy"));
        if(request.getParameter("nProy")!=null) Log4j.error("Global Proyecto: " + request.getParameter("nProy"));
        Log4j.error("Global Error Mapeo: " + mapping );
        Log4j.error("Global Error Direccion: " + mapping.getInputForward());
        Log4j.error("**********");
        request.setAttribute("ServletException", e);
    }
}