package panaderia.struts.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.DefinitionsFactory;
import org.apache.struts.tiles.TilesRequestProcessor;

import panaderia.beans.Usuarios;

/**
 * Esta clase ha sido necesaria generarla para poder utilizar
 * el RequestProcessor de Tiles y 
 * @author imunoz
 *
 */
public class ControllerManager extends TilesRequestProcessor {
	
	/** Procesa los privilegios del usuario y actua en consecuencia
	 * 
	 */	
	protected boolean processRoles(HttpServletRequest request, 
								   HttpServletResponse response, 
								   ActionMapping mapping) 
								   throws IOException, ServletException {
		
		// Variables
		boolean isSesionIniciada = false;
		String[] roles = mapping.getRoleNames();
		String strAccion="";
		String requestAction = "";
		boolean isPopup = false;
		
		try {
			//Recogemos el action del que proviene, para saber si es popup y en caso de que así sea mostrar una pagina de error de popup
			requestAction = request.getRequestURI();
			if(requestAction.indexOf("doDocumentosPendientes")!=-1
			   ){
				isPopup = true;
			}
			
			// Recogemos el identificador de accion
			if (roles == null || roles.length == 0){
				/* Obligatoriamente, todos los actions deben tener asignado 
				 * algún tipo de Rol. En caso de que no lo haya, se informa
				 * en consola, y se manda a la página de error.
				 */
				rolNoAjustado(request, response, mapping, isPopup);
			} else {
				// Recogemos el rol asociado a la accion
				strAccion = roles[0];
				
				if("logado".equals(strAccion)){
					// ZONA PRIVADA
					/*
					 * Debe estar logado
					 * */
					Usuarios usuario = (Usuarios)request.getSession().getAttribute("usuario");
					if (usuario==null){
				          /* Si el usuario es nulo entonces no está logado, con lo establecemos el mensaje
				          * de no logado y el forward de login
				          */
				          envioUsuarioNoAutorizado(request, response, mapping, isPopup);		           			          
				          isSesionIniciada = false;
				     } else {
				    	 isSesionIniciada = true;
				     }
				}else if("inicio".equals(strAccion)){
					isSesionIniciada = true;
				}else{
					envioUsuarioNoAutorizado(request, response, mapping, isPopup);
				}
			}
		} catch (Exception ex){
			envioUsuarioNoAutorizado(request, response, mapping, isPopup);
			isSesionIniciada = false;
		}
		if(!isSesionIniciada) accesoDenegado(request, response, mapping, isPopup);
	
		return isSesionIniciada;
    }
	
	private void rolNoAjustado(HttpServletRequest request,
						HttpServletResponse response, 
						ActionMapping mapping,
						boolean isPopup) throws ServletException, IOException
	{		
		RequestDispatcher rd = null;        
		if(!isPopup)
			rd = request.getRequestDispatcher(mapping.findForward("error").getPath());
		else
			rd = request.getRequestDispatcher(mapping.findForward("error_popup").getPath());
		// Se define la variable donde se irá almacenando los diferentes mensajes
		//ActionMessages mensajes = new  ActionMessages();
		//mensajes.add("pepe", new ActionMessage("rol_no_ajustado"));
		// Se almacena el mensage en el request
		//request.setAttribute("MESSAGES",mensajes);
		
		// Se realiza el request dispatcher
		rd.forward(request,response);	  
	}	
	
	/**
	 * @param request
	 * @param mapping
	 * @throws IOException
	 * @throws ServletException
	 */
	private void accesoDenegado(HttpServletRequest request,
										HttpServletResponse response, 
										ActionMapping mapping,
										boolean isPopup) 
										throws ServletException, IOException 
	{
	    // Dispatcher
		RequestDispatcher rd = null;
	    
		// Redirigimos a la pagina de acceso denegado
		if(!isPopup)
			rd = request.getRequestDispatcher(mapping.findForward("acceso_denegado").getPath());
		else
			rd = request.getRequestDispatcher(mapping.findForward("acceso_denegado_popup").getPath());
		// Realizamos el request dispatcher, forward
		rd.forward(request,response);	  
	}
	
	
	/**
	 * @param request
	 * @param mapping
	 * @throws IOException
	 * @throws ServletException
	 */
	private void envioUsuarioNoAutorizado(HttpServletRequest request,
										HttpServletResponse response, 
										ActionMapping mapping,
										boolean isPopup) 
										throws ServletException, IOException 
	{
	        
		// Dispatcher
		RequestDispatcher rd = null;
	    
		// Redirigimos a la pagina de logado
		if(!isPopup)
			rd = request.getRequestDispatcher(mapping.findForward("error_login").getPath());
		else
			rd = request.getRequestDispatcher(mapping.findForward("error_login_popup").getPath());  
	}
	
   public void init(ActionServlet servlet, ModuleConfig appConfig) throws ServletException
   {
   		super.init(servlet, appConfig);
   }
   	
	protected boolean processTilesDefinition(String definitionName, 
											 boolean contextRelative, 
											 HttpServletRequest request, 
											 HttpServletResponse response)
	throws IOException, ServletException
   {
		return super.processTilesDefinition(definitionName, contextRelative, request, response);
   }

	protected void doForward(String uri, 
							 HttpServletRequest request, 
							 HttpServletResponse response) 
							 throws IOException, ServletException
   {
		super.doForward(uri, request, response);
   }

	protected void processForwardConfig(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ForwardConfig forward)
									throws IOException, ServletException
   {
		super.processForwardConfig(request, response, forward);
   }

	protected void internalModuleRelativeForward(
			String uri, 
			HttpServletRequest request,
			HttpServletResponse response)
	    	throws IOException, ServletException
	{
		super.internalModuleRelativeForward(uri, request, response);
	}

	protected void internalModuleRelativeInclude(
			String uri, 
			HttpServletRequest request,
			HttpServletResponse response)
	    	throws IOException, ServletException
	{
		super.internalModuleRelativeInclude(uri, request, response);
	}

	public DefinitionsFactory getDefinitionsFactory()
	{
		return super.getDefinitionsFactory();
	}
}
