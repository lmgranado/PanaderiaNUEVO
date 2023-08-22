// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DemonioFacturacion.java

package panaderia.demonio;

import java.util.ArrayList;
import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.utilidades.Utiles;
import utils.Log4j;
import utils.Utils;

// Referenced classes of package panaderia.demonio:
//            Demonio

public class DemonioFacturacion extends panaderia.demonio.Demonio
{

    public DemonioFacturacion()
    {
    }

    public static panaderia.demonio.DemonioFacturacion getInstance()
    {
        if(instance == null)
            instance = new DemonioFacturacion();
        return instance;
    }

    public long getSleepTime()
    {
        long tiempoParada = 0x36ee80L;
        utils.Log4j.info((new StringBuilder("***** Tiempo de parada del demonio: ")).append(tiempoParada / 0x36ee80L).append(" horas *****").toString());
        return tiempoParada;
    }

    public void stopping()
    {
    }

    public void starting()
    {
    }

    public void task()
    {
        if(panaderia.utilidades.Utiles.pasaDemonioSemanal(utils.Utils.getFechaActualConHora()))
        {
            utils.Log4j.info((new StringBuilder("***** PASANDO DEMONIO:  pasaDemonioSemanal ")).append(utils.Utils.getFechaActualConHoraString()).append(" horas *****").toString());
            java.lang.String fechas[] = panaderia.utilidades.Utiles.fechasSemana(utils.Utils.getFechaActualSinHora());
            tratarFacturas(fechas[0], fechas[1], "SEMANAL");
        }
        if(panaderia.utilidades.Utiles.pasaDemonioQuincenal(utils.Utils.getFechaActualConHora()))
        {
            utils.Log4j.info((new StringBuilder("***** PASANDO DEMONIO:  pasaDemonioQuincenal ")).append(utils.Utils.getFechaActualConHoraString()).append(" horas *****").toString());
            java.lang.String fechas[] = panaderia.utilidades.Utiles.fechasPrimeraQuincena(utils.Utils.getFechaActualSinHora());
            tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
        }
        if(panaderia.utilidades.Utiles.pasaDemonioMensual(utils.Utils.getFechaActualConHora()))
        {
            utils.Log4j.info((new StringBuilder("***** PASANDO DEMONIO:  pasaDemonioMensual ")).append(utils.Utils.getFechaActualConHoraString()).append(" horas *****").toString());
            java.lang.String fechas[] = panaderia.utilidades.Utiles.fechasSegundaQuincena(utils.Utils.getFechaActualSinHora());
            tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
            fechas = (java.lang.String[])null;
            fechas = panaderia.utilidades.Utiles.fechasMensual(utils.Utils.getFechaActualConHora());
            tratarFacturas(fechas[0], fechas[1], "MENSUAL");
        }
    }

    public static void main(java.lang.String args[])
    {
        panaderia.demonio.DemonioFacturacion demon = new DemonioFacturacion();
        java.lang.String fechas[] = panaderia.utilidades.Utiles.fechasSemana(utils.Utils.getFechaActualConHora());
        demon.tratarFacturas(fechas[0], fechas[1], "SEMANAL");
        fechas = panaderia.utilidades.Utiles.fechasPrimeraQuincena(utils.Utils.getFechaActualConHora());
        demon.tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
        fechas = panaderia.utilidades.Utiles.fechasSegundaQuincena(utils.Utils.getFechaActualConHora());
        demon.tratarFacturas(fechas[0], fechas[1], "QUINCENAL");
        fechas = (java.lang.String[])null;
        fechas = panaderia.utilidades.Utiles.fechasMensual(utils.Utils.getFechaActualConHora());
        demon.tratarFacturas("01/02/2010", "28/02/2010", "MENSUAL");
    }

    public void tratarFacturas(java.lang.String fechaInicio, java.lang.String fechaFin, java.lang.String tipoPeriodo)
    {
        try
        {
            java.util.ArrayList listaIdsClientes = panaderia.beans.AlbaranesVenta.getAllAlbaranesVentaDistinctClientesBetweenDias(fechaInicio, fechaFin, tipoPeriodo);
            for(int i = 0; i < listaIdsClientes.size(); i++)
            {
                java.lang.String clId = ((java.lang.String[])listaIdsClientes.get(i))[0];
                panaderia.beans.Clientes cliente = panaderia.beans.Clientes.getClientesByClId(clId);
                int numFacturas = 2;
                float proporcionIVA = java.lang.Float.parseFloat(cliente.getClProporcionIva());
                if(proporcionIVA == 0.0F || proporcionIVA == 1.0F)
                    numFacturas = 1;
                panaderia.beans.FacturasVenta factura = new FacturasVenta();
                factura.setFvFecha(fechaFin, utils.Utils.DATE_SHORT);
                factura.setFvClId(cliente.getClId());
                factura.setFvObservaciones("");
                factura.setFvPagada("0");
                factura.setFvCierre("1");
                factura.setFvIva("A");
                factura.setFvTipo("0");
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
                    throw new Exception("No se ha insertado la factura correctamente. Demonio.");
                java.util.ArrayList listaFacturasVentaDetalle = panaderia.beans.FacturasVentaDetalle.getAllFacturasDetalleConversorByAlbaranesVentaDetalle(cliente.getClId(), proporcionIVA, fechaInicio, fechaFin, tipoPeriodo);
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
                panaderia.beans.AlbaranesVenta.cierraAlbaranesClienteDias(cliente.getClId(), fechaInicio, fechaFin);
            }

        }
        catch(java.lang.Exception ex)
        {
            utils.Log4j.error("Error en el demonio", ex);
        }
    }

    private static panaderia.demonio.DemonioFacturacion instance = null;

}
