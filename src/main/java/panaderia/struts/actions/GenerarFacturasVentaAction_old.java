// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GenerarFacturasVentaAction.java

package panaderia.struts.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import panaderia.beans.FacturasVenta;
import panaderia.negocio.bCuerposFacturasVentaPdf_old;

public class GenerarFacturasVentaAction_old extends org.apache.struts.action.Action
{
	class RoundRectangle implements PdfPCellEvent {  
		 public void cellLayout(PdfPCell cell, Rectangle rect,  
		 PdfContentByte[] canvas) {  
		 PdfContentByte cb = canvas[PdfPTable.LINECANVAS];  
		 cb.setColorStroke(Color.BLACK);  
		 cb.roundRectangle(rect.getLeft() + 0, rect.getBottom(), rect.getWidth() - 0,  
				 rect.getHeight() - 0, 4);  
		 cb.stroke();  
		 }  
	}    


    public GenerarFacturasVentaAction_old()
    {
        fuenteParrafo = new Font(helvetica(), 12F, 0);
        fuenteParrafoCursiva = new Font(helvetica(), 9F, 2);
        fuenteParrafoNegrita = new Font(helvetica(), 9F, 1);
        fuenteNotaGrande = new Font(helvetica(), 13F, 1, java.awt.Color.orange);
        fuenteParrafoNegritaCursiva = new Font(helvetica(), 9F, 3);
        fuenteParrafoSubrayado = new Font(helvetica(), 9F, 4);
        fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9F, 6);
        fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9F, 5);
        fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9F, 7);
    }

    public com.lowagie.text.pdf.BaseFont helvetica()
    {
        com.lowagie.text.pdf.BaseFont fuente = null;
        try
        {
            java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
            java.lang.String rutaFuente = (new StringBuilder(java.lang.String.valueOf(ruta))).append("/fuentes/").append("VERDANA.TTF").toString();
            fuente = com.lowagie.text.pdf.BaseFont.createFont(rutaFuente, rutaFuente, false);
        }
        catch(java.lang.Exception exception) { }
        return fuente;
    }

    public org.apache.struts.action.ActionForward execute(org.apache.struts.action.ActionMapping mapping, org.apache.struts.action.ActionForm form, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
        throws java.lang.Exception
    {
        org.apache.struts.action.ActionForward forward = null;
        try
        {
            java.lang.String fvId = request.getParameter("fvId");
            java.lang.String agrupados = request.getParameter("agrupados");
            com.lowagie.text.Document document = new Document();
            document.setPageSize(com.lowagie.text.PageSize.A4);
            java.io.ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            com.lowagie.text.pdf.PdfWriter writer = com.lowagie.text.pdf.PdfWriter.getInstance(document, buffer);
            document.addCreationDate();
            document.addCreator("GESTOR PANADERIA");
            document.addProducer();
            document.open();
            com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();
            com.lowagie.text.HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
            pie.setBorderWidth(0.0F);
            document.setFooter(pie);
            panaderia.negocio.bCuerposFacturasVentaPdf_old cuerpo = new bCuerposFacturasVentaPdf_old();
            if("".equals(utils.Utils.skipNull(fvId)))
                if(!"".equals(utils.Utils.skipNull(agrupados)))
                {
                    java.lang.String chkValues[] = request.getParameterValues("checkList");
                    java.lang.String idsFacturasAgrupadas = utils.Utils.arrayToComaString(chkValues);
                    java.util.ArrayList listaClientes = panaderia.beans.FacturasVentaDetalle.getIdsClientesByFvdFvIds(idsFacturasAgrupadas);
                    for(int i = 0; i < listaClientes.size(); i++)
                    {
                        java.lang.String idCliente = ((java.lang.String[])listaClientes.get(i))[0];
                        java.util.ArrayList fechas = panaderia.beans.FacturasVentaDetalle.getFechasAgrupadas(idsFacturasAgrupadas, idCliente);
                        panaderia.beans.Clientes cliente = panaderia.beans.Clientes.getClientesByClId(idCliente);
                        java.util.ArrayList listaDetalles = panaderia.beans.FacturasVentaDetalle.getFacturasVentaDetalleAgrupadasByFvdFvIdsAndFvClId(idsFacturasAgrupadas, idCliente);
                        com.lowagie.text.pdf.PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura(new FacturasVenta(), cliente, listaDetalles, request);
                        com.lowagie.text.pdf.PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente(fechas, cliente, listaDetalles, request);
                        document.add(tablaCabecera);
                        document.add(tablaCabeceraDatosClientes);
                        cuerpo.generaCuerpoFactura(new FacturasVenta(), cliente, listaDetalles, request, document, fechas);
                        document.newPage();
                    }

                } else
                {
                    java.lang.String chkValues[] = request.getParameterValues("checkList");
                    for(int i = 0; i < chkValues.length; i++)
                    {
                        panaderia.beans.FacturasVenta factura = panaderia.beans.FacturasVenta.getFacturasVentaByFvId(chkValues[i]);
                        panaderia.beans.Clientes cliente = panaderia.beans.Clientes.getClientesByClId(factura.getFvClId());
                        java.util.ArrayList listaDetalles = panaderia.beans.FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvId(chkValues[i]);
                        if("A".equals(factura.getFvIva()))
                        {
                            com.lowagie.text.pdf.PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura(factura, cliente, listaDetalles, request);
                            document.add(tablaCabecera);
                        }
                        com.lowagie.text.pdf.PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente(factura, cliente, listaDetalles, request);
                        document.add(tablaCabeceraDatosClientes);
                        cuerpo.generaCuerpoFactura(factura, cliente, listaDetalles, request, document, new ArrayList());
                        document.newPage();
                    }

                }
            document.close();
            response.setHeader("Content-type", "application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"FacturasVenta.pdf\"");
            java.io.DataOutput output = new DataOutputStream(response.getOutputStream());
            output.write(buffer.toByteArray());
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return forward;
    }

    public com.lowagie.text.Font fuenteParrafo;
    public com.lowagie.text.Font fuenteParrafoCursiva;
    public com.lowagie.text.Font fuenteParrafoNegrita;
    public com.lowagie.text.Font fuenteNotaGrande;
    public com.lowagie.text.Font fuenteParrafoNegritaCursiva;
    public com.lowagie.text.Font fuenteParrafoSubrayado;
    public com.lowagie.text.Font fuenteParrafoCursivaSubrayado;
    public com.lowagie.text.Font fuenteParrafoNegritaSubrayado;
    public com.lowagie.text.Font fuenteParrafoNegritaCursivaSubrayado;
}
