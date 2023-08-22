// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FacturasVentaFormularioAction.java

package panaderia.struts.formularios.actions;

import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import panaderia.beans.Boton;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Localidades;
import panaderia.beans.Productos;
import panaderia.beans.Provincias;
import panaderia.beans.Rutas;
import panaderia.negocio.bBotoneraFormulario;
import utils.Utils;

public class FacturasVentaFormularioAction_old extends org.apache.struts.action.Action
{

    public FacturasVentaFormularioAction_old()
    {
    }

    public org.apache.struts.action.ActionForward execute(org.apache.struts.action.ActionMapping mapping, org.apache.struts.action.ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
        throws java.lang.Exception
    {
        org.apache.struts.action.ActionForward forward = null;
        try
        {
            java.lang.String fvId = utils.Utils.skipNull(request.getParameter("fvId"));
            java.lang.String fvTipo = utils.Utils.skipNull(request.getParameter("fvTipo"));
            if(fvId.equals(""))
                fvId = utils.Utils.skipNull((java.lang.String)request.getAttribute("fvId"));
            java.lang.String fvClId = utils.Utils.skipNull(request.getParameter("fvClId"));
            java.lang.String fvFecha = utils.Utils.skipNull(request.getParameter("fvFecha"));
            java.lang.String fvObservaciones = utils.Utils.skipNull(request.getParameter("fvObservaciones"));
            panaderia.beans.FacturasVenta facturaventa = new FacturasVenta();
            facturaventa.setFvTipo(fvTipo);
            //Luis Miguel 03/07/14
            //if("".equals(fvClId))
                fvClId = utils.Utils.skipNull((java.lang.String)request.getAttribute("fvClId"));
                fvClId = "1";//Cliente NOTA DE ENTREGA
            panaderia.beans.Clientes cliente = new Clientes();
            java.lang.String clDatosRelacionados[] = new java.lang.String[6];
            if(utils.Utils.empty(fvId))
            {
                cliente = panaderia.beans.Clientes.getClientesByClId(fvClId);
                clDatosRelacionados = new java.lang.String[7];
                clDatosRelacionados[0] = cliente.getClNombre();
                clDatosRelacionados[1] = cliente.getClApellidos();
                clDatosRelacionados[2] = cliente.getClDireccion();
                clDatosRelacionados[3] = cliente.getClLocId();
                clDatosRelacionados[4] = cliente.getClProvId();
                clDatosRelacionados[5] = cliente.getClDescuento();
                clDatosRelacionados[6] = cliente.getClNombreComercial();
                facturaventa.setClDatosRelacionados(clDatosRelacionados);
                facturaventa.setFvFecha(fvFecha, utils.Utils.DATE_SHORT);
                facturaventa.setFvObservaciones(fvObservaciones);
                facturaventa.setFvClId(fvClId);
            } else
            {
                facturaventa = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(fvId);
                request.setAttribute("facturaventa", facturaventa);
            }
            request.setAttribute("facturaventa", facturaventa);
            if(!utils.Utils.skipNull(facturaventa.getFvFvRef()).equals("") && !facturaventa.getFvFvRef().equals("0"))
                request.setAttribute("facturareferencia", panaderia.beans.FacturasVenta.getFacturasVentaByFvId(facturaventa.getFvFvRef()));
            if(!facturaventa.getFvId().equals(""))
                request.setAttribute("LISTADETALLES", panaderia.beans.FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId(facturaventa.getFvId()));
            panaderia.beans.Provincias provincia = panaderia.beans.Provincias.getProvinciasByProvId(facturaventa.getClDatosRelacionados()[4]);
            request.setAttribute("Provincia", provincia);
            panaderia.beans.Localidades localidad = panaderia.beans.Localidades.getLocalidadesByLocId(facturaventa.getClDatosRelacionados()[3]);
            request.setAttribute("Localidad", localidad);
            request.setAttribute("LISTAPRODUCTOS", panaderia.beans.Productos.getAllProductos());
            panaderia.negocio.bBotoneraFormulario botonera = new bBotoneraFormulario();
            int intOrdenBot = 0;
            if(facturaventa.getFvId().equals(""))
                botonera.addBoton(intOrdenBot++, panaderia.negocio.bBotoneraFormulario.GUARDAR);
            if(!facturaventa.getFvCierre().equals("1"))
                botonera.addBoton(intOrdenBot++, panaderia.negocio.bBotoneraFormulario.EDITAR);
            panaderia.beans.Boton boton = panaderia.negocio.bBotoneraFormulario.VOLVER;
            boton.setJavascript("javascript:doVolver('doListadoFacturasVenta.do');");
            botonera.addBoton(intOrdenBot++, boton);
            request.setAttribute("BOTONERA", botonera.getBotones());
            request.setAttribute("RUTASLISTA", panaderia.beans.Rutas.getAllRutas());
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
