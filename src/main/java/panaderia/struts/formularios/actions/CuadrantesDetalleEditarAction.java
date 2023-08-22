package panaderia.struts.formularios.actions;

import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.CuadrantesDetalle;

public class CuadrantesDetalleEditarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			Enumeration enumera = request.getParameterNames();
			while(enumera.hasMoreElements()){
				String nombreCampo = (String)enumera.nextElement();
				//En el caso que el campo sea de cantidad, tomamos del nombre del campo el 
				//id del detalle del cuadrante y el numero del viaje
				StringTokenizer token = new StringTokenizer(nombreCampo, "_");
				if(token.nextToken().equals("cantidad")){
					String cudId = token.nextToken();
					int nViaje = Integer.parseInt(token.nextToken());
					String valor = request.getParameter(nombreCampo);
					CuadrantesDetalle cuadrante = CuadrantesDetalle.getCuadrantesDetalleByCudId(cudId);
					
					switch (nViaje) {
					case 1:
						cuadrante.setCudCantidad1(valor);
						break;
					case 2:
						cuadrante.setCudCantidad2(valor);
						break;
					case 3:
						cuadrante.setCudCantidad3(valor);
						break;
					case 4:
						cuadrante.setCudCantidad4(valor);
						break;
					case 5:
						cuadrante.setCudCantidad5(valor);
						break;

					default:
						break;
					}
									
					resultado = cuadrante.actualiza();
				}
			}
				
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.guardar.algunos.duplicado"));						
	    		saveMessages(request.getSession(), messages);
	        }else if(resultado>0){
	        	//mensaje y mapeo de guardado o editado correcto
	        	messages.add("info", new ActionMessage("info.guardar.ok"));						
	    		saveMessages(request.getSession(), messages);
	        }
	        /**/
	        
			forward = mapping.findForward("ok");
		}catch (Exception e) {
			System.out.println(e);
			forward = mapping.findForward("error");
		}
		return forward;
	}
}