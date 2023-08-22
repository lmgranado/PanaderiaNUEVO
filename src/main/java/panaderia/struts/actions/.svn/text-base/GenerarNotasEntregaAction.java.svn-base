package panaderia.struts.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.negocio.bCuerposNotasPdf;
import panaderia.negocio.bCuerposPdf;
import utils.Utils;

public class GenerarNotasEntregaAction extends Action{
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.TIMES_ROMAN, BaseFont.NOT_EMBEDDED);
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
    
    public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.NORMAL,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
		  String notId = request.getParameter("notId");
		  Rectangle pagina = new Rectangle(420,370).rotate();
	      Document document = new Document( pagina, 20, 20, 10, 10);	
	      document.setPageSize( pagina );
	      //document.setPageSize(new Rectangle(370,420));
	      
	      	      
	      RoundRectangle rectangle = new RoundRectangle();
	      ByteArrayOutputStream buffer    = new ByteArrayOutputStream();
	      PdfWriter             writer    = PdfWriter.getInstance( document, buffer );
	            
	      document.addCreationDate();
	      document.addCreator("GESTOR PANADERIA");
	      document.addProducer();
	      document.open();
	      
	      PdfContentByte cb = writer.getDirectContent();      
	      
	      HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
	      pie.setBorderWidth(0);
	      document.setFooter(pie);
	
	     // bCuerposPdf cuerpo = new bCuerposPdf();
	      bCuerposNotasPdf cuerpo = new bCuerposNotasPdf();
	      
	      PdfPCell celdaCompleta = new PdfPCell(new Phrase("", fuenteParrafoNegrita));  
	      celdaCompleta.setColspan(6);
	      celdaCompleta.setCellEvent(rectangle);
	      celdaCompleta.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaCompleta.setBorderWidth(0);
	      
		  if(notId!=null){
			  celdaCompleta = new PdfPCell(new Phrase("", fuenteParrafoNegrita));  
			  NotasEntrega nota = NotasEntrega.getNotasEntregaByNotId(notId);
			  Clientes cliente = Clientes.getClientesByClId(nota.getNotClId());
			  ArrayList listaDetalles = NotasEntregaDetalle.getNotasEntregaDetalleByNotdetNotIdWithStock(notId);
			  /*PdfPTable tabla = cuerpo.generaCuerpoNota(nota, cliente, listaDetalles, request);
			  
			  celdaCompleta.addElement(tabla);
		      
		      PdfPTable tablaCompleta = new PdfPTable(1);
		      tablaCompleta.setWidthPercentage(100);
		      tablaCompleta.addCell(celdaCompleta);
		      document.add(tablaCompleta);
		      document.newPage();*/
			  //PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( nota, cliente, listaDetalles, request );
			  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( nota, cliente, listaDetalles, request );
			  
			  //Añadimos la cabecera
			  //document.add(tablaCabecera);
		      document.add(tablaCabeceraDatosClientes);
		      
		      //Añadimos el cuerpo de la factura
			  cuerpo.generaCuerpoFactura( nota, cliente, listaDetalles, request, document );
		 
		  }else{
			  String[] chkValues = request.getParameterValues("checkListImpresion");	
			  for(int i=0; i<chkValues.length; i++){
				  celdaCompleta = new PdfPCell(new Phrase("", fuenteParrafoNegrita));  
				  NotasEntrega nota = NotasEntrega.getNotasEntregaByNotId(chkValues[i]);
				  Clientes cliente = Clientes.getClientesByClId(nota.getNotClId());
				  ArrayList listaDetalles = NotasEntregaDetalle.getNotasEntregaDetalleByNotdetNotId(chkValues[i]);
				  /*PdfPTable tabla = cuerpo.generaCuerpoNota(nota, cliente, listaDetalles, request);
				  
				  celdaCompleta.addElement(tabla);
			      
			      PdfPTable tablaCompleta = new PdfPTable(1);
			      tablaCompleta.setWidthPercentage(100);
			      tablaCompleta.addCell(celdaCompleta);
			      document.add(tablaCompleta);
			      document.newPage();*/
				  //PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( nota, cliente, listaDetalles, request );
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( nota, cliente, listaDetalles, request );
				  
				  //Añadimos la cabecera
				  //document.add(tablaCabecera);
			      document.add(tablaCabeceraDatosClientes);
			      
			      //Añadimos el cuerpo de la factura
				  cuerpo.generaCuerpoFactura( nota, cliente, listaDetalles, request, document );
			    
				  //Pasamos a la siguiente factura
			      document.newPage();
			  }
		  }
	      
	      
	      document.close();
	      
	      response.setHeader("Content-type", "application/pdf");
	      response.setHeader("Content-Disposition","inline; filename=\"notaEntrega.pdf\"");
	
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