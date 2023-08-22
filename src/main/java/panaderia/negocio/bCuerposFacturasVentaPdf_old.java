package panaderia.negocio;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.utilidades.Empresa;
import utils.Utils;

public class bCuerposFacturasVentaPdf_old extends com.lowagie.text.pdf.PdfPageEventHelper
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


    public bCuerposFacturasVentaPdf_old()
    {
        fuenteParrafo = new Font(helvetica(), 10F, 0);
        fuenteParrafo9 = new Font(helvetica(), 9F, 0);
        fuenteParrafoCursiva = new Font(helvetica(), 9F, 2);
        fuenteParrafoNegrita = new Font(helvetica(), 9F, 1);
        fuenteNotaGrande = new Font(helvetica(), 14F, 1, java.awt.Color.orange);
        fuenteParrafoNegritaCursiva = new Font(helvetica(), 9F, 3);
        fuenteParrafoSubrayado = new Font(helvetica(), 9F, 4);
        fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9F, 6);
        fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9F, 5);
        fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9F, 7);
        fuenteNotaMediana = new Font(helvetica(), 11F, 1);
        numProductos = 20;
        tamanyoTablaCuerpo = 450F;
    }

    public com.lowagie.text.pdf.BaseFont helvetica()
    {
        com.lowagie.text.pdf.BaseFont fuente = null;
        try
        {
            java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
            java.lang.String rutaFuente = (new StringBuilder(java.lang.String.valueOf(ruta))).append("/fuentes/").append("VERDANA.TTF").toString();
            fuente = com.lowagie.text.FontFactory.getFont(rutaFuente, 12F, 4).getBaseFont();
        }
        catch(java.lang.Exception exception) { }
        return fuente;
    }

    public void anadeImagen(javax.servlet.http.HttpServletRequest request, java.lang.String nombreImagen, com.lowagie.text.Document document)
        throws com.lowagie.text.DocumentException, java.net.MalformedURLException, java.io.IOException
    {
        java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
        int transparency[] = {
            255, 255
        };
        com.lowagie.text.Image img = com.lowagie.text.Image.getInstance((new StringBuilder(java.lang.String.valueOf(ruta))).append("/img/").append(nombreImagen).toString());
        img.scaleToFit(200F, 200F);
        img.setAbsolutePosition(135F, 300F);
        img.setTransparency(transparency);
        img.setAlignment(13);
        document.add(img);
    }

    public com.lowagie.text.pdf.PdfPTable generaCabeceraFactura(panaderia.beans.FacturasVenta factura, Clientes cliente, java.util.ArrayList listaDetalles, javax.servlet.http.HttpServletRequest request)
        throws com.lowagie.text.DocumentException, java.io.IOException
    {
        java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
        RoundRectangle rectangle = new RoundRectangle();
        com.lowagie.text.pdf.PdfPTable tablaCabecera = new PdfPTable(2);
        tablaCabecera.setHorizontalAlignment(1);
        tablaCabecera.setSpacingAfter(12F);
        tablaCabecera.getDefaultCell().setBorder(0);
        try
        {
            tablaCabecera.setWidthPercentage(95F);
            tablaCabecera.setWidths(new int[] {
                50, 50
            });
            com.lowagie.text.Image im = com.lowagie.text.Image.getInstance((new StringBuilder(java.lang.String.valueOf(ruta))).append("/img/logo_solo.jpg").toString());
            im.setAlignment(8);
            com.lowagie.text.pdf.PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
            im.scalePercent(100F);
            celda = new PdfPCell(im);
            celda.setHorizontalAlignment(0);
            celda.setBorderWidth(0.0F);
            tablaCabecera.addCell(celda);
            com.lowagie.text.pdf.PdfPTable datosEmpresa = new PdfPTable(1);
            panaderia.utilidades.Empresa empresa = new Empresa(factura.getFvIdEmpresa());
            com.lowagie.text.pdf.PdfPCell celdaDatos = new PdfPCell(new Phrase(empresa.getNombreComercial(), fuenteNotaGrande));
            celdaDatos.setHorizontalAlignment(1);
            celdaDatos.setBorderWidth(0.0F);
            datosEmpresa.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(empresa.getDireccion1(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(1);
            celdaDatos.setBorderWidth(0.0F);
            datosEmpresa.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(empresa.getCodigoPostal()))).append(" ").append(empresa.getLocalidad()).append(" (").append(empresa.getProvincia()).append(")").toString(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(1);
            celdaDatos.setBorderWidth(0.0F);
            datosEmpresa.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase((new StringBuilder("F\341brica: ")).append(empresa.getTelefonoFabrica()).toString(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(1);
            celdaDatos.setBorderWidth(0.0F);
            datosEmpresa.addCell(celdaDatos);
            if(!utils.Utils.empty(empresa.getDireccion2()))
            {
                celdaDatos = new PdfPCell(new Phrase(empresa.getDireccion2(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(1);
                celdaDatos.setBorderWidth(0.0F);
                datosEmpresa.addCell(celdaDatos);
            }
            if(!utils.Utils.empty(empresa.getTelefonoOficina()))
            {
                celdaDatos = new PdfPCell(new Phrase((new StringBuilder("Oficina: ")).append(empresa.getTelefonoOficina()).toString(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(1);
                celdaDatos.setBorderWidth(0.0F);
                datosEmpresa.addCell(celdaDatos);
            }
            celdaDatos = new PdfPCell(new Phrase((new StringBuilder("CIF : ")).append(empresa.getCif()).toString(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(1);
            celdaDatos.setBorderWidth(0.0F);
            datosEmpresa.addCell(celdaDatos);
            tablaCabecera.addCell(datosEmpresa);
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return tablaCabecera;
    }

    public com.lowagie.text.pdf.PdfPTable generaCabeceraDatosCliente(panaderia.beans.FacturasVenta factura, panaderia.beans.Clientes cliente, java.util.ArrayList listaDetalles, javax.servlet.http.HttpServletRequest request)
        throws com.lowagie.text.DocumentException, java.io.IOException
    {
        java.lang.String nombre = "NO FAC";
        if("A".equals(factura.getFvIva()))
        {
            numProductos = 20;
            nombre = "N\260 FRA";
            tamanyoTablaCuerpo = 450F;
        } else
        {
            numProductos = 34;
            tamanyoTablaCuerpo = 600F;
        }
        RoundRectangle rectangle = new RoundRectangle();
        java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
        com.lowagie.text.pdf.PdfPTable tablaCabecera = new PdfPTable(2);
        tablaCabecera.setHorizontalAlignment(1);
        tablaCabecera.setSpacingAfter(12F);
        tablaCabecera.getDefaultCell().setBorder(0);
        tablaCabecera.getDefaultCell().setCellEvent(rectangle);
        try
        {
            tablaCabecera.setWidthPercentage(100F);
            tablaCabecera.setWidths(new int[] {
                35, 65
            });
            com.lowagie.text.pdf.PdfPTable tablaIzquierda = new PdfPTable(2);
            tablaIzquierda.setWidths(new int[] {
                50, 50
            });
            com.lowagie.text.pdf.PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
            celdaDatos = new PdfPCell(new Phrase(nombre, fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingTop(7F);
            celdaDatos.setPaddingBottom(6F);
            tablaIzquierda.addCell(celdaDatos);
            //celdaDatos = new PdfPCell(new Phrase(Calendar.getInstance().get(Calendar.YEAR) + "-" + factura.getFvNumeroFactura(), fuenteParrafo));
  	        celdaDatos = new PdfPCell(new Phrase(factura.getFvNumeroFactura(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingTop(7F);
            celdaDatos.setPaddingBottom(6F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("COD CLIENTE", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingBottom(7F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(cliente.getClId(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setPaddingBottom(6F);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("FECHA", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(factura.getFvFecha("dd/MM/yyyy"), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            tablaCabecera.addCell(tablaIzquierda);
            com.lowagie.text.pdf.PdfPTable tablaDerecha = new PdfPTable(1);
            celdaDatos = new PdfPCell(new Phrase("Datos Cliente", fuenteNotaMediana));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            if("A".equals(factura.getFvIva()))
            {
                celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(cliente.getClNombre()))).append(" ").append(cliente.getClApellidos()).append(" (").append(cliente.getClNombreComercial()).append(")").toString(), fuenteParrafo9));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
                celdaDatos = new PdfPCell(new Phrase(cliente.getClNif(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
                celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
                celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(cliente.getClCodigoPostal()))).append(" ").append(cliente.getClDatosRelacionados()[3]).append(" (").append(cliente.getClDatosRelacionados()[4]).append(")").toString(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
            } else
            {
                celdaDatos = new PdfPCell(new Phrase(cliente.getClNombreComercial(), fuenteParrafo9));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
                celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
                celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(cliente.getClCodigoPostal()))).append(" ").append(cliente.getClDatosRelacionados()[3]).append(" (").append(cliente.getClDatosRelacionados()[4]).append(")").toString(), fuenteParrafo));
                celdaDatos.setHorizontalAlignment(0);
                celdaDatos.setBorderWidth(0.0F);
                celdaDatos.setPaddingLeft(15F);
                tablaDerecha.addCell(celdaDatos);
            }
            tablaCabecera.addCell(tablaDerecha);
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return tablaCabecera;
    }

    public com.lowagie.text.pdf.PdfPTable generaCabeceraDatosCliente(java.util.ArrayList fechas, panaderia.beans.Clientes cliente, java.util.ArrayList listaDetalles, javax.servlet.http.HttpServletRequest request)
        throws com.lowagie.text.DocumentException, java.io.IOException
    {
        RoundRectangle rectangle = new RoundRectangle();
        java.lang.String ruta = java.util.ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
        java.lang.String fechaInicio = ((java.lang.String[])fechas.get(0))[1];
        java.lang.String fechaFin = ((java.lang.String[])fechas.get(0))[0];
        com.lowagie.text.pdf.PdfPTable tablaCabecera = new PdfPTable(2);
        tablaCabecera.setHorizontalAlignment(1);
        tablaCabecera.setSpacingAfter(12F);
        tablaCabecera.getDefaultCell().setBorder(0);
        tablaCabecera.getDefaultCell().setCellEvent(rectangle);
        try
        {
            tablaCabecera.setWidthPercentage(100F);
            tablaCabecera.setWidths(new int[] {
                30, 65
            });
            com.lowagie.text.pdf.PdfPTable tablaIzquierda = new PdfPTable(2);
            tablaIzquierda.setWidths(new int[] {
                65, 35
            });
            com.lowagie.text.pdf.PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
            celdaDatos = new PdfPCell(new Phrase("FACTURA AGRUPADA", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingTop(7F);
            celdaDatos.setPaddingBottom(6F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("CODIGO CLIENTE", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingBottom(7F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(cliente.getClId(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("FECHA INICIO", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(utils.Utils.date2String(utils.Utils.string2Date(fechaInicio, utils.Utils.DATE_MYSQL_SHORT), utils.Utils.DATE_SHORT), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase("FECHA FIN", fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(utils.Utils.date2String(utils.Utils.string2Date(fechaFin, utils.Utils.DATE_MYSQL_SHORT), utils.Utils.DATE_SHORT), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(2);
            celdaDatos.setBorderWidth(0.0F);
            tablaIzquierda.addCell(celdaDatos);
            tablaCabecera.addCell(tablaIzquierda);
            com.lowagie.text.pdf.PdfPTable tablaDerecha = new PdfPTable(1);
            celdaDatos = new PdfPCell(new Phrase("Datos Cliente", fuenteNotaMediana));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(cliente.getClNombre()))).append(" ").append(cliente.getClApellidos()).append(" (").append(cliente.getClNombreComercial()).append(")").toString(), fuenteParrafo9));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(cliente.getClNif(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            celdaDatos = new PdfPCell(new Phrase((new StringBuilder(java.lang.String.valueOf(cliente.getClCodigoPostal()))).append(" ").append(cliente.getClDatosRelacionados()[3]).append(" (").append(cliente.getClDatosRelacionados()[4]).append(")").toString(), fuenteParrafo));
            celdaDatos.setHorizontalAlignment(0);
            celdaDatos.setBorderWidth(0.0F);
            celdaDatos.setPaddingLeft(15F);
            tablaDerecha.addCell(celdaDatos);
            tablaCabecera.addCell(tablaDerecha);
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
        return tablaCabecera;
    }

    public void generaCuerpoFactura(panaderia.beans.FacturasVenta factura, panaderia.beans.Clientes cliente, java.util.ArrayList listaDetalles, javax.servlet.http.HttpServletRequest request, com.lowagie.text.Document document, java.util.ArrayList fechas)
        throws com.lowagie.text.DocumentException, java.io.IOException
    {
        RoundRectangle rectangle = new RoundRectangle();
        com.lowagie.text.pdf.PdfPTable tablaExterior = new PdfPTable(1);
        tablaExterior.setWidthPercentage(100F);
        tablaExterior.getDefaultCell().setFixedHeight(tamanyoTablaCuerpo);
        tablaExterior.setHorizontalAlignment(1);
        tablaExterior.getDefaultCell().setBorder(0);
        tablaExterior.getDefaultCell().setCellEvent(rectangle);
        int contador = 1;
        float numero_piezas = 0;
        com.lowagie.text.pdf.PdfPTable tablaCuerpo = new PdfPTable(6);
        try
        {
            tablaCuerpo.setWidthPercentage(100F);
            tablaCuerpo.setWidths(new int[]{10,49,11,10,10,10});
            tablaCuerpo.getDefaultCell().setBorder(0);
            com.lowagie.text.pdf.PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
            celda = new PdfPCell(new Phrase("UND.", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            celda = new PdfPCell(new Phrase("DESCRIPCION", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            celda = new PdfPCell(new Phrase("PRECIO", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            celda = new PdfPCell(new Phrase("BASE", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            float precioTotal = 0.0F;
            celda = new PdfPCell(new Phrase("IVA%", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            celda = new PdfPCell(new Phrase("IVA", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            celda.setBorderWidthRight(0.5F);
            tablaCuerpo.addCell(celda);
            /*celda = new PdfPCell(new Phrase("Dto.%", fuenteParrafo));
            celda.setColspan(1);
            celda.setPaddingBottom(5F);
            celda.setHorizontalAlignment(1);
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5F);
            tablaCuerpo.addCell(celda);*/
            for(int i = 0; i < listaDetalles.size(); i++)
            {
                panaderia.beans.FacturasVentaDetalle detalle = (panaderia.beans.FacturasVentaDetalle)listaDetalles.get(i);
                float cantidad = Float.parseFloat( detalle.getFvdCantidad() );
                numero_piezas += cantidad;
                celda = new PdfPCell(new Phrase((new StringBuilder()).append(detalle.getFvdCantidad()).toString(), fuenteParrafo));
                celda.setColspan(1);
                celda.setBorderWidthRight(0.5F);
                celda.setHorizontalAlignment(1);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                celda.setPadding(4F);
                tablaCuerpo.addCell(celda);
                celda = new PdfPCell(new Phrase(detalle.getFvdProducto(), fuenteParrafo));
                celda.setColspan(1);
                celda.setHorizontalAlignment(0);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                celda.setBorderWidthRight(0.5F);
                celda.setPaddingLeft(10F);
                tablaCuerpo.addCell(celda);
                //PRECIO
                celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getFvdPrecioVenta()))), fuenteParrafo));
  	            celda.setColspan(1);
  	            celda.setHorizontalAlignment(Element.ALIGN_CENTER);celda.setFixedHeight(20);
  	            celda.setBorderWidth(0); 
  	            celda.setBorderWidthRight( 0.5f );
  	          ////celda.setCellEvent(rectangle);
  	          //celda.setBackgroundColor(new Color(230,230,230));
  	          tablaCuerpo.addCell(celda);
                //BASE
                double base = Double.parseDouble(detalle.getFvdPrecioVenta()) * Double.parseDouble(detalle.getFvdCantidad());
  	            celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Double.toString(base)), fuenteParrafo));
                celda.setColspan(1);
                celda.setHorizontalAlignment(1);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                celda.setBorderWidthRight(0.5F);
                tablaCuerpo.addCell(celda);
                precioTotal += Double.parseDouble( detalle.getFvdPrecioVenta() );
  	            String iva = redondea(Double.toString( Double.parseDouble(detalle.getFvdIva())*100 ), 0);
  	            //IVA DEL PRODUCTO
  	            celda = new PdfPCell(new Phrase( iva + "%", fuenteParrafo));
                celda.setColspan(1);
                celda.setHorizontalAlignment(1);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                celda.setBorderWidthRight(0.5F);
                tablaCuerpo.addCell(celda);
                
                double ivaLinea = Double.parseDouble(detalle.getFvdImporte()) - base;
  	            String ivaLineaPinta = Utils.formateaCantidad(Double.toString(ivaLinea));
  	            //PORCENTAJE DEL IVA
  	            celda = new PdfPCell( new Phrase( ivaLineaPinta, fuenteParrafo) );
                celda.setColspan(1);
                celda.setHorizontalAlignment(1);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                celda.setBorderWidthRight(0.5F);
                tablaCuerpo.addCell(celda);
                /*java.lang.String descuento = utils.Utils.formateaCantidad(java.lang.Float.toString(java.lang.Float.parseFloat(utils.Utils.skipNullNumero(detalle.getFvdDescuento())) * 100F));
                celda = new PdfPCell(new Phrase(descuento, fuenteParrafo));
                celda.setColspan(1);
                celda.setHorizontalAlignment(1);
                celda.setFixedHeight(20F);
                celda.setBorderWidth(0.0F);
                tablaCuerpo.addCell(celda);*/
                if("A".equals(factura.getFvIva()))
                    numProductos = 20;
                else
                    numProductos = 25;
                if(contador == numProductos && i != listaDetalles.size() - 1)
                {
                    tablaExterior.addCell(tablaCuerpo);
                    document.add(tablaExterior);
                    document.newPage();
                    com.lowagie.text.pdf.PdfPTable cabecera = generaCabeceraFactura(factura, cliente, listaDetalles, request);
                    com.lowagie.text.pdf.PdfPTable datosCliente = null;
                    if(utils.Utils.skipNull(factura.getFvId()).equals(""))
                        datosCliente = generaCabeceraDatosCliente(fechas, cliente, listaDetalles, request);
                    else
                        datosCliente = generaCabeceraDatosCliente(factura, cliente, listaDetalles, request);
                    if("A".equals(factura.getFvIva()))
                        document.add(cabecera);
                    document.add(datosCliente);
                    tablaExterior = new PdfPTable(1);
                    tablaExterior.setWidthPercentage(100F);
                    tablaExterior.getDefaultCell().setFixedHeight(tamanyoTablaCuerpo);
                    tablaExterior.setHorizontalAlignment(1);
                    tablaExterior.getDefaultCell().setBorder(0);
                    //tablaExterior.getDefaultCell().setCellEvent(rectangle);
                    tablaCuerpo = new PdfPTable(6);
                    tablaCuerpo.setWidthPercentage(100F);
                    tablaCuerpo.setWidths(new int[] {
                        16, 42, 15, 18, 9, 9
                    });
                    tablaCuerpo.getDefaultCell().setPaddingLeft(10F);
                    celda = new PdfPCell(new Phrase("", fuenteParrafo));
                    contador = 0;
                }
                contador++;
            }

            tablaExterior.addCell(tablaCuerpo);
            document.add(tablaExterior);
            generaPieFactura(factura, cliente, numero_piezas, request, document);
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
    }

    public void generaPieFactura(panaderia.beans.FacturasVenta factura, panaderia.beans.Clientes cliente, float numero_piezas, javax.servlet.http.HttpServletRequest request, com.lowagie.text.Document document)
    {
        com.lowagie.text.pdf.PdfPTable tablaPie = new PdfPTable(6);
        RoundRectangle rectangle = new RoundRectangle();
        try
        {
            tablaPie.setWidthPercentage(100F);
            tablaPie.setSpacingBefore(5F);
            tablaPie.setWidths(new int[] {
                22, 16, 4, 20, 16, 17
            });
            com.lowagie.text.pdf.PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo9));
            celda = new PdfPCell(new Phrase("PIEZAS TOTALES", fuenteParrafo9));
            celda.setColspan(1);
            celda.setHorizontalAlignment(1);
            celda.setBorderWidth(0.0F);
            //celda.setCellEvent(rectangle);
            celda.setPaddingBottom(2F);
            tablaPie.addCell(celda);
            celda = new PdfPCell(new Phrase((new StringBuilder()).append(numero_piezas).toString(), fuenteParrafo9));
            celda.setColspan(1);
            celda.setHorizontalAlignment(2);
            celda.setBorderWidth(0.0F);
            ////celda.setCellEvent(rectangle);
            celda.setPaddingBottom(4F);
            tablaPie.addCell(celda);
            celda = new PdfPCell(new Phrase("", fuenteParrafo9));
            celda.setColspan(2);
            celda.setHorizontalAlignment(2);
            celda.setBorderWidth(0.0F);
            tablaPie.addCell(celda);
            
            if("A".equals(factura.getFvIva())){
       
	            //Parte base imponible
	 	       celda = new PdfPCell(new Phrase( "TOTAL BASE", fuenteParrafo9) );
	 	       celda.setColspan(1);
	 	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	       celda.setBorderWidth(0); 
	 	       //celda.setCellEvent(rectangle);
	 	       celda.setPaddingBottom( 2f );
	 	       tablaPie.addCell(celda);
	 	       
	 	       String totalBase = FacturasVenta.getImporteTotalBaseByFvId(factura.getFvId());
	 	       celda = new PdfPCell( new Phrase( ( Utils.formateaCantidad(totalBase)  ), fuenteParrafo9));
	 	       celda.setColspan(1);
	 	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 	       celda.setBorderWidth(0); 
	 	       //celda.setCellEvent(rectangle);
	 	       celda.setPaddingBottom( 2f );
	 	       tablaPie.addCell(celda);
	 	       
	 	       document.add( tablaPie );
	 	       
	 	       //Parte de los IVAS
	 	       ArrayList listaIvas = FacturasVenta.getImporteIvaTotalByFvId(factura.getFvId());
	 	       
	 	       for(int j= 0; j<listaIvas.size(); j++){
	 	    	   tablaPie = new PdfPTable(6);
	 	    	   tablaPie.setWidthPercentage(100);
	 	    	   tablaPie.setSpacingBefore( 5f );
	 	    	   tablaPie.setWidths(new int[]{22,16,4,16,20,17});
	 	    	   
	 	    	   String[] elto = (String[])listaIvas.get(j);
	 	    	   if(Utils.formateaCantidad(elto[1]).equals("0.0"))
	 	    		   continue;
	 	    	   
	 	    	   celda = new PdfPCell(new Phrase("", fuenteParrafo9));
	 	 		   celda.setColspan(4);
	 	 		   celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 	 		   celda.setBorderWidth(0);
	 	 		   tablaPie.addCell(celda);
	 	    	   
	 		       celda = new PdfPCell(new Phrase( "TOTAL IVA " + elto[0] + "%", fuenteParrafo9) );
	 		       celda.setColspan(1);
	 		       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		       celda.setBorderWidth(0); 
	 		       //celda.setCellEvent(rectangle);
	 		       celda.setPaddingBottom( 2f );
	 		       tablaPie.addCell(celda);
	 		       
	 		       celda = new PdfPCell( new Phrase( ( Utils.formateaCantidad(elto[1])  ), fuenteParrafo9));
	 		       celda.setColspan(1);
	 		       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 		       celda.setBorderWidth(0); 
	 		       //celda.setCellEvent(rectangle);
	 		       celda.setPaddingBottom( 2f );
	 		       tablaPie.addCell(celda);
	 		       
	 		       document.add( tablaPie );
	 	       }
	        }
 		  
 	       //TOTAL
 	       tablaPie = new PdfPTable(6);
     	   tablaPie.setWidthPercentage(100);
     	   tablaPie.setSpacingBefore( 5f );
     	   tablaPie.setWidths(new int[]{22,16,4,16,20,17});
     	   
 	       celda = new PdfPCell(new Phrase("", fuenteParrafo9));
 	       celda.setColspan(4);
 	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	       celda.setBorderWidth(0);
 	       tablaPie.addCell(celda);
 	       
 	       celda = new PdfPCell(new Phrase( "TOTAL", fuenteParrafo9) );
 	       celda.setColspan(1);
 	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
 	       celda.setBorderWidth(0); 
 	       //celda.setCellEvent(rectangle);
 	       celda.setPaddingBottom( 2f );
 	       //celda.setBackgroundColor(new Color(251,243,232));
 	       tablaPie.addCell(celda);
 	       
 	       //double totalFactura = importe_con_descuento + totalIva;
 	       
 	       String totalFactura = factura.getFvTotal();
 	       celda = new PdfPCell( new Phrase( ( Utils.formateaCantidad(totalFactura)  ), fuenteParrafo9));
 	       celda.setColspan(1);
 	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	       celda.setBorderWidth(0); 
 	       //celda.setCellEvent(rectangle);
 	       celda.setPaddingBottom( 2f );
 	       //celda.setBackgroundColor(new Color(230,230,230));
 	       tablaPie.addCell(celda);
        
        document.add( tablaPie );
        }
        catch(java.lang.Exception e)
        {
            java.lang.System.out.println(e);
        }
    }

    public com.lowagie.text.Font fuenteParrafo;
    public com.lowagie.text.Font fuenteParrafo9;
    public com.lowagie.text.Font fuenteParrafoCursiva;
    public com.lowagie.text.Font fuenteParrafoNegrita;
    public com.lowagie.text.Font fuenteNotaGrande;
    public com.lowagie.text.Font fuenteParrafoNegritaCursiva;
    public com.lowagie.text.Font fuenteParrafoSubrayado;
    public com.lowagie.text.Font fuenteParrafoCursivaSubrayado;
    public com.lowagie.text.Font fuenteParrafoNegritaSubrayado;
    public com.lowagie.text.Font fuenteParrafoNegritaCursivaSubrayado;
    public com.lowagie.text.Font fuenteNotaMediana;
    private int numProductos;
    private float tamanyoTablaCuerpo;
    
    public static String redondea( String num, int decimales )
    {
  	  	BigDecimal resultado = new BigDecimal("0").setScale( decimales,BigDecimal.ROUND_HALF_DOWN );
  	    if( num != null && num.length() != 0 )
  	      resultado = new BigDecimal(num).setScale( decimales ,BigDecimal.ROUND_HALF_DOWN );
  	    
  	    return resultado.toString();
    }
}
