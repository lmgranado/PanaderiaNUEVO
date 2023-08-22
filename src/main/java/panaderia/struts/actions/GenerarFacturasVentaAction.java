package panaderia.struts.actions;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.ColorSupported;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.negocio.bCuerposFacturasVentaPdf;
import panaderia.negocio.bCuerposFacturasVentaPdf_old;
import utils.Utils;
import utils.UtilsFacturacion;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;

public class GenerarFacturasVentaAction extends Action{
	
	private static String rutaFacturas = "";
	
	static{
		ResourceBundle config = ResourceBundle.getBundle( "informes" );
		rutaFacturas = config.getString( "ruta_facturas" );
	}
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			//fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			String ruta = ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
            String rutaFuente = ruta + "/fuentes/" + "VERDANA.TTF";
            fuente = BaseFont.createFont(rutaFuente, rutaFuente, false);
		}catch (Exception e) {
			
		}
		return fuente;
	}
	
	
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
	
	    
    public Font fuenteParrafo = new Font(helvetica(), 10, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
		  String fvId = request.getParameter("fvId");
		  String agrupados = request.getParameter("agrupados");
		  boolean copia = false;
		  
	      Document document = new Document();    
	      document.setPageSize(PageSize.A4);
	      ByteArrayOutputStream buffer    = new ByteArrayOutputStream();
	      PdfWriter             writer    = PdfWriter.getInstance( document, buffer );
		  
	      document.open();
	      
	      PdfContentByte cb = writer.getDirectContent();      
	      
	      HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
	      pie.setBorderWidth(0);
	      document.setFooter(pie);
	
	      bCuerposFacturasVentaPdf cuerpo = new bCuerposFacturasVentaPdf();
	      
		  if( !"".equals(Utils.skipNull( fvId )) ){
			  FacturasVenta factura = FacturasVenta.getFacturasVentaByFvId( fvId );
			  Clientes cliente = Clientes.getClientesByClId( factura.getFvClId() );
			  ArrayList listaDetalles = FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvIdWithStock( fvId );
			
			  //Para realizar 3 copias
			  for(int j=1; j<=3; j++){
				  if(j!=1) copia = true;
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( factura, cliente, listaDetalles, request );
				  document.add(tablaCabeceraDatosClientes);
				 
			      //Añadimos el cuerpo de la factura
				  cuerpo.generaCuerpoFactura( factura, cliente, listaDetalles, request, document, new ArrayList(), copia);
			   
				  //Pasamos a la siguiente factura
				  document.newPage();
			  }
			
		  }else if( !"".equals(Utils.skipNull( agrupados )) ){
			  //Para la parte de agrupados
			  //Cogemos todas las facturas marcadas
			  String[] chkValues = request.getParameterValues("checkList");
			  String idsFacturasAgrupadas = Utils.arrayToComaString(chkValues);
			  //Obtenemos los diferentes clientes de las facturas
			  ArrayList listaClientes = FacturasVentaDetalle.getIdsClientesByFvdFvIds(idsFacturasAgrupadas);
			  
			  for(int i=0; i<listaClientes.size(); i++){
				  String idCliente = ((String[])listaClientes.get(i))[0];
				  //Tenemos que ver que datos de la factura mostramos. Vamos a necesitar el nombre, y las fechas entre y hasta
				  ArrayList fechas = FacturasVentaDetalle.getFechasAgrupadas(idsFacturasAgrupadas, idCliente);
				  
				  Clientes cliente = Clientes.getClientesByClId(idCliente );
				  ArrayList listaDetalles = FacturasVentaDetalle.getFacturasVentaDetalleAgrupadasByFvdFvIdsAndFvClId(idsFacturasAgrupadas, idCliente);
				  
				  PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( new FacturasVenta(), listaDetalles, request );
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( fechas, cliente, listaDetalles, request );
				  
				  //Añadimos la cabecera
				  document.add(tablaCabecera);
			      document.add(tablaCabeceraDatosClientes);
			      
			      //Añadimos el cuerpo de la factura
				  cuerpo.generaCuerpoFactura( new FacturasVenta(), cliente, listaDetalles, request, document, fechas, copia );
			    
				  //Pasamos a la siguiente factura
			      document.newPage();
			  }
		  }else{
			  String[] chkValues = request.getParameterValues("checkList");	
			  for(int i=0; i<chkValues.length; i++){
				  copia = false;
				  FacturasVenta factura = FacturasVenta.getFacturasVentaByFvId( chkValues[i] );
				  Clientes cliente = Clientes.getClientesByClId( factura.getFvClId() );
				  ArrayList listaDetalles = FacturasVentaDetalle.getFacturasVentaDetalleByFvdFvIdWithStock( chkValues[i] );
				
				  //Para realizar 3 copias
				  for(int j=1; j<=3; j++){
					  if(j!=1) copia = true;
					  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( factura, cliente, listaDetalles, request );
					  document.add(tablaCabeceraDatosClientes);
					 
				      //Añadimos el cuerpo de la factura
					  cuerpo.generaCuerpoFactura( factura, cliente, listaDetalles, request, document, new ArrayList(), copia);
				   
					  //Pasamos a la siguiente factura
					  document.newPage();
				  }
			  }
		  }
	      
		  
		  //Añade codigo de barras
		  //document.add(UtilsFacturacion.getBarcode(document, writer, "MJZ", "2363123"));
	      
	      document.close();
	      
	      response.setHeader("Content-type", "application/pdf");
	      response.setHeader("Content-Disposition","inline; filename=\"FacturasVenta.pdf\"");
	      
	      DataOutput output = new DataOutputStream(response.getOutputStream());
	      output.write( buffer.toByteArray());
	      	
			
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}