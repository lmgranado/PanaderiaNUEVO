package panaderia.struts.formularios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import utils.Utils;

public class FacturasVentaFormularioGrabarAction extends Action {
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception 
	{              		
		ActionForward forward = null;
		ActionMessages messages = new ActionMessages();
		
		int resultado = -2;
		try{
			String fvId = request.getParameter("fvId");
			FacturasVenta facturaventa = new FacturasVenta();
			facturaventa.cargaDatosFormularioStruts( request );
			facturaventa.setFvCierre( "0" );
			Clientes cliente = Clientes.getClientesByClId(facturaventa.getFvClId());
			/* Grabaremos el id del cliente junto con los detalles de la facturaventa en primer lugar */
			if( Utils.skipNull( fvId ).equals("") ){
				//facturaventa = new FacturasVenta();
				//facturaventa.cargaDatosFormularioStruts( request );
				//facturaventa.setFvCierre("0");
				String idEmpresa = cliente.getClIdEmpresa();
				if(facturaventa.getFvIva().equals("A") && (cliente.getClIdEmpresa().equals("") || cliente.getClIdEmpresa().equals("0")))
                	idEmpresa = "1";
                
				String numero = null;
				if( facturaventa.getFvTipo().equals("2") ){
					numero = Integer.toString(FacturasVenta.getSiguienteNumeroFacturaAbono(facturaventa.getFvIva(), idEmpresa) + 1);

				}else{
					numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(facturaventa.getFvIva(), idEmpresa) + 1);
				
				}
				facturaventa.setFvNumeroFactura(numero);
				facturaventa.setFvIdEmpresa(idEmpresa);
				resultado = facturaventa.inserta();
				facturaventa = FacturasVenta.getFacturasVentaByFvId(Integer.toString( resultado ));
			}else{
				//facturaventa = new FacturasVenta();
				//facturaventa.cargaDatosFormularioStruts( request );
				//facturaventa.setFvCierre("0");
				if(  !Utils.skipNull( facturaventa.getFvDescuento() ).equals("") ){
					double total = Double.parseDouble(facturaventa.getFvTotal());
					double descuento = Double.parseDouble(facturaventa.getFvDescuento());
					total = (float) (total - (total * (descuento*0.01)) );
					facturaventa.setFvTotal( String.valueOf(total) );
				}
					
				resultado = facturaventa.actualiza();
				facturaventa = FacturasVenta.getFacturasVentaByFvId(fvId);
			}
			
			request.setAttribute( "facturaventa" , facturaventa );
			request.setAttribute( "fvId" , facturaventa.getFvId() );
	        /*Zona de mensajes y mapeo*/
	        if(resultado == 0 || resultado == -1){
	        	//mensaje de guardado
	        	messages.add("info", new ActionMessage("info.guardar.algunos"));						
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