// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FacturasVentaCrearAction.java

package panaderia.struts.actions;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.PeriodosFacturacion;
import panaderia.utilidades.PanaderiaExceptionHandler;
import utils.Utils;

public class FacturasVentaCrearAction extends org.apache.struts.action.Action
{

    public FacturasVentaCrearAction()
    {
    }

    public org.apache.struts.action.ActionForward execute(org.apache.struts.action.ActionMapping mapping, org.apache.struts.action.ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
        throws java.lang.Exception
    {
        org.apache.struts.action.ActionForward forward = null;
        try
        {
            java.lang.String chkValues[] = request.getParameterValues("checkList");
            for(int i = 0; i < chkValues.length; i++)
            {
                panaderia.beans.AlbaranesVenta albaranes = panaderia.beans.AlbaranesVenta.getAlbaranesVentaByAvId(chkValues[i]);
                panaderia.beans.Clientes cliente = panaderia.beans.Clientes.getClientesByClId(albaranes.getAvClId());
                panaderia.beans.PeriodosFacturacion periodo = panaderia.beans.PeriodosFacturacion.getPeriodosFacturacionByPfId(cliente.getClPfId());
                if(periodo.getPfDescripcion().equals("DIARIO"))
                {
                    int numFacturas = 2;
                    float proporcionIVA = java.lang.Float.parseFloat(cliente.getClProporcionIva());
                    if(proporcionIVA == 0.0F || proporcionIVA == 1.0F)
                        numFacturas = 1;
                    panaderia.beans.FacturasVenta factura = new FacturasVenta();
                    factura.setFvFecha(albaranes.getAvFecha(utils.Utils.DATE_SHORT), utils.Utils.DATE_SHORT);
                    factura.setFvClId(albaranes.getAvClId());
                    factura.setFvObservaciones(albaranes.getAvObservaciones());
                    factura.setFvPagada("0");
                    factura.setFvCierre("1");
                    factura.setFvIva("A");
                    String numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura(factura.getFvIva(), cliente.getClIdEmpresa()) + 1);
                    factura.setFvNumeroFactura(numero);
                    factura.setFvIdEmpresa(cliente.getClIdEmpresa());
                    int fvId = 0;
                    int fvId2 = 0;
                    if(proporcionIVA != 0.0F)
                        fvId = factura.inserta();
                    if(numFacturas == 2 || proporcionIVA == 0.0F)
                    {
                        factura.setFvIva("B");
                        numero = Integer.toString(FacturasVenta.getSiguienteNumeroFactura("B", cliente.getClIdEmpresa()) + 1);
                        factura.setFvNumeroFactura(numero);
                        fvId2 = factura.inserta();
                    }
                    if(fvId <= 0 && fvId2 <= 0)
                        throw new Exception("No se ha insertado la factura correctamente");
                    albaranes.setAvCierre("1");
                    albaranes.actualiza();
                    java.util.ArrayList listaFacturasVentaDetalle = panaderia.beans.FacturasVentaDetalle.getAllFacturasDetalleConversorByAlbaranesVentaDetalle(chkValues[i], proporcionIVA);
                    for(int j = 0; j < listaFacturasVentaDetalle.size(); j++)
                    {
                        panaderia.beans.FacturasVentaDetalle facturasVentaDetalle = (panaderia.beans.FacturasVentaDetalle)listaFacturasVentaDetalle.get(j);
                        if(facturasVentaDetalle.getTipo().equals("A"))
                            facturasVentaDetalle.setFvdFvId(java.lang.Integer.toString(fvId));
                        else
                            facturasVentaDetalle.setFvdFvId(java.lang.Integer.toString(fvId2));
                        facturasVentaDetalle.setFvdLinea(java.lang.Integer.toString(j));
                        if(java.lang.Integer.parseInt(facturasVentaDetalle.getFvdCantidad()) > 0)
                            facturasVentaDetalle.inserta();
                    }

                    if(fvId != 0)
                    {
                        factura = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(java.lang.Integer.toString(fvId));
                        factura.setFvTotal(panaderia.beans.FacturasVenta.getImporteTotalByFvId(java.lang.Integer.toString(fvId)));
                        factura.setFvImportePendiente(factura.getFvTotal());
                        if(factura.getFvTotal().equals("0"))
                            factura.elimina();
                        else
                            factura.actualiza();
                    }
                    if(fvId2 != 0)
                    {
                        factura = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(java.lang.Integer.toString(fvId2));
                        factura.setFvTotal(panaderia.beans.FacturasVenta.getImporteTotalByFvId(java.lang.Integer.toString(fvId2)));
                        factura.setFvImportePendiente(factura.getFvTotal());
                        if(factura.getFvTotal().equals("0"))
                            factura.elimina();
                        else
                            factura.actualiza();
                    }
                }
            }

            forward = mapping.findForward("ok");
        }
        catch(java.lang.Exception ex)
        {
            forward = mapping.findForward("error");
            panaderia.utilidades.PanaderiaExceptionHandler.guardaLog(ex, mapping, request);
        }
        return forward;
    }
}
