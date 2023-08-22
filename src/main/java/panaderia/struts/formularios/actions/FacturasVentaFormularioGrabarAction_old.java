// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FacturasVentaFormularioGrabarAction.java

package panaderia.struts.formularios.actions;

import java.io.PrintStream;
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

public class FacturasVentaFormularioGrabarAction_old extends org.apache.struts.action.Action
{

    public FacturasVentaFormularioGrabarAction_old()
    {
    }

    public org.apache.struts.action.ActionForward execute(org.apache.struts.action.ActionMapping mapping, org.apache.struts.action.ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
        throws java.lang.Exception
    {
        org.apache.struts.action.ActionForward forward = null;
        org.apache.struts.action.ActionMessages messages = new ActionMessages();
        int resultado = -2;
        try
        {
            java.lang.String fvId = request.getParameter("fvId");
            panaderia.beans.FacturasVenta facturaventa = new FacturasVenta();
            facturaventa.cargaDatosFormularioStruts(request);
            facturaventa.setFvCierre("0");
            Clientes cliente = Clientes.getClientesByClId(facturaventa.getFvClId());
            if(utils.Utils.skipNull(fvId).equals(""))
            {
                java.lang.String numero = null;
                String idEmpresa = cliente.getClIdEmpresa();
                if(facturaventa.getFvIva().equals("A") && (cliente.getClIdEmpresa().equals("") || cliente.getClIdEmpresa().equals("0")))
                	idEmpresa = "1";
                	
                if(facturaventa.getFvTipo().equals("2"))
                	numero = Integer.toString(FacturasVenta.getSiguienteNumeroFacturaAbono(facturaventa.getFvIva(), idEmpresa) + 1);
                else
                	numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(facturaventa.getFvIva(), idEmpresa) + 1);
                facturaventa.setFvNumeroFactura(numero);
                facturaventa.setFvIdEmpresa(idEmpresa);
                resultado = facturaventa.inserta();
                facturaventa = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(java.lang.Integer.toString(resultado));
            } else
            {
                resultado = facturaventa.actualiza();
                facturaventa = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(fvId);
            }
            request.setAttribute("facturaventa", facturaventa);
            request.setAttribute("fvId", facturaventa.getFvId());
            if(resultado == 0 || resultado == -1)
            {
                messages.add("info", new ActionMessage("info.guardar.algunos"));
                saveMessages(request.getSession(), messages);
            } else
            if(resultado > 0)
            {
                messages.add("info", new ActionMessage("info.guardar.ok"));
                saveMessages(request.getSession(), messages);
            }
            forward = mapping.findForward("ok");
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
            forward = mapping.findForward("error");
        }
        return forward;
    }
}
