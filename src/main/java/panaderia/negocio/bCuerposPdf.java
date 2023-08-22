package panaderia.negocio;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.Clientes;
import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import utils.Utils;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

public class bCuerposPdf {
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
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
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);

	public PdfPTable generaCuerpoNota(NotasEntrega nota, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request){
		
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tabla = new PdfPTable(6);
		try{
	      tabla.setWidthPercentage(100);
	      tabla.setWidths(new int[]{16,36,16,6,6,17});

	      Image im = Image.getInstance(ruta + "/img/pine_elipse.gif");;
	      
	      
	      
	      PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      
	      celda.setColspan(6);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(6);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      im.scalePercent(75);
	      celda = new PdfPCell(im);
	      celda.setColspan(2);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("NOTA DE ENTREGA", fuenteNotaGrande));
	      celda.setColspan(3);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("Nº " + nota.getNotId(), fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(6);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      
	
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(2);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase(nota.getNotFecha(Utils.DATE_LONG), fuenteParrafoNegrita));
	      celda.setColspan(4);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(2);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("Cliente: " + cliente.getClNombre() + " " + cliente.getClApellidos(), fuenteParrafoNegrita));
	      celda.setColspan(4);
	      celda.setCellEvent(rectangle);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(6);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      
	
	      celda = new PdfPCell(new Phrase("CANTIDAD", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("CONCEPTO - REFERENCIA", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("PRECIO", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      float precioTotal = 0;
	      
	      celda = new PdfPCell(new Phrase("IVA", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("DTO.", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("IMPORTE", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      float importeTotal = 0;
	      
	      for(int i=0; i<listaDetalles.size(); i++){
	    	  NotasEntregaDetalle detalle = (NotasEntregaDetalle)listaDetalles.get(i);
	    	  celda = new PdfPCell(new Phrase("" + detalle.getNotdetCantidad(), fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          tabla.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(detalle.getNotdetProducto(), fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          tabla.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getNotdetPrecio()))) + "€", fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          celda.setBackgroundColor(new Color(230,230,230));
	          tabla.addCell(celda);
	          precioTotal += Float.parseFloat(detalle.getNotdetPrecio());
	          
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getNotdetIva()) * 100)) + "%", fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          celda.setBackgroundColor(new Color(230,230,230));
	          tabla.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getNotdetDescuento()) * 100)) + "%", fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          celda.setBackgroundColor(new Color(230,230,230));
	          tabla.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getNotdetImporte()))) + "€", fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          celda.setBackgroundColor(new Color(230,230,230));
	          tabla.addCell(celda);
	          importeTotal += Float.parseFloat(detalle.getNotdetImporte());
	      }
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(5);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("TOTAL", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(1);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
        celda.setColspan(5);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setBorderWidth(0);
        tabla.addCell(celda);
        
        celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(importeTotal)), fuenteParrafoNegrita));
        celda.setColspan(1);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setBorderWidth(0); celda.setCellEvent(rectangle);
        celda.setBackgroundColor(new Color(230,230,230));
        tabla.addCell(celda);
		}catch (Exception e) {
			System.out.println(e);
		}
        return tabla;
	}
	
public PdfPTable generaCuerpoPrevision(String agCliente, String agRuta, String agEntrega, String agFamilia, ArrayList listaPrevision, HttpServletRequest request){
		
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tabla = new PdfPTable(7);
		try{
	      tabla.setWidthPercentage(100);
	      tabla.setWidths(new int[]{16,16,16,6,16,16,14});

	      Image im = Image.getInstance(ruta + "/img/pine_elipse.gif");;
	      
	      
	      
	      PdfPCell celda = new PdfPCell(new Phrase("\n\n", fuenteParrafoNegrita));
	      
	      im.scalePercent(75);
	      celda = new PdfPCell(im);
	      celda.setColspan(3);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("Previsión de Fabricación", fuenteNotaGrande));
	      celda.setColspan(4);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(7);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      
	
	      celda = new PdfPCell(new Phrase("Fecha de Inicio", fuenteParrafoNegrita));
	      celda.setColspan(3);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase(request.getParameter("notFechaInicio"), fuenteParrafoNegrita));
	      celda.setColspan(4);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("Fecha de Fin", fuenteParrafoNegrita));
	      celda.setColspan(3);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase(request.getParameter("notFechaFin"), fuenteParrafoNegrita));
	      celda.setColspan(4);
	      celda.setCellEvent(rectangle);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(7);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      tabla.addCell(celda);
	      
	      
	      
	      if(agCliente.equals("1")){
		      celda = new PdfPCell(new Phrase("CLIENTE", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		      celda.setBackgroundColor(new Color(251,243,232));
		      tabla.addCell(celda);
	      }
	      if(agRuta.equals("1")){
		      celda = new PdfPCell(new Phrase("RUTA", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		      celda.setBackgroundColor(new Color(251,243,232));
		      tabla.addCell(celda);
	      }
	      if(agEntrega.equals("1")){
		      celda = new PdfPCell(new Phrase("ENTREGA", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		      celda.setBackgroundColor(new Color(251,243,232));
		      tabla.addCell(celda);
	      }
	      if(agFamilia.equals("1")){
		      celda = new PdfPCell(new Phrase("FAMILIA", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		      celda.setBackgroundColor(new Color(251,243,232));
		      tabla.addCell(celda);
	      }
	      
	      
	      int colSpan = 1; 
	      if(agEntrega.equals("0")) colSpan+=1;
	      if(agCliente.equals("0")) colSpan+=1;
	      if(agRuta.equals("0")) colSpan+=1;
	      if(agFamilia.equals("0")) colSpan+=1;
	      
	      celda = new PdfPCell(new Phrase("PRODUCTO", fuenteParrafoNegrita));
	      celda.setColspan(colSpan);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("CANTIDAD", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase("PESO MASA", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor(new Color(251,243,232));
	      tabla.addCell(celda);
	      
	      for(int i=0; i<listaPrevision.size(); i++){
	    	  String[] prevision = (String[])listaPrevision.get(i);
	    	  
	    	  if(agCliente.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[3], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }
	    	  
	    	  if(agCliente.equals("1") && agRuta.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[3], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(agCliente.equals("0") && agRuta.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[2], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }
	    	  
	    	  if(agCliente.equals("1") && agRuta.equals("1") && agEntrega.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[4], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(((agCliente.equals("0") && agRuta.equals("1")) || (agCliente.equals("1") && agRuta.equals("0"))) && agEntrega.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[3], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(agCliente.equals("0") && agRuta.equals("0") && agEntrega.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[2], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }
	    	  
	    	  if(agCliente.equals("1") && agRuta.equals("1") && agEntrega.equals("1") && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[5], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(((agCliente.equals("0") && agRuta.equals("1") && agEntrega.equals("1")) || 
	    			  	(agCliente.equals("1") && agRuta.equals("0") && agEntrega.equals("1")) ||
	    			  	(agCliente.equals("1") && agRuta.equals("1") && agEntrega.equals("0"))) && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[4], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(((agCliente.equals("0") && agRuta.equals("0") && agEntrega.equals("1")) || 
	    			  	(agCliente.equals("1") && agRuta.equals("0") && agEntrega.equals("0")) ||
	    			  	(agCliente.equals("0") && agRuta.equals("1") && agEntrega.equals("0"))) && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[4], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(agCliente.equals("0") && agRuta.equals("0") && agFamilia.equals("1") && agEntrega.equals("0")){
		    	  celda = new PdfPCell(new Phrase(prevision[3], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }
 	  
	          
	          celda = new PdfPCell(new Phrase(prevision[0], fuenteParrafoNegrita));
	          celda.setColspan(colSpan);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          tabla.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(prevision[1], fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	          celda.setBackgroundColor(new Color(230,230,230));
	          tabla.addCell(celda);
	          
	    	  
	    	  if(agCliente.equals("1") && agRuta.equals("1") && agEntrega.equals("1") && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[6], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(((agCliente.equals("0") && agRuta.equals("1") && agEntrega.equals("1")) || 
	    			  	(agCliente.equals("1") && agRuta.equals("0") && agEntrega.equals("1")) ||
	    			  	(agCliente.equals("1") && agRuta.equals("1") && agEntrega.equals("0"))) && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[5], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(((agCliente.equals("0") && agRuta.equals("0") && agEntrega.equals("1")) || 
	    			  	(agCliente.equals("1") && agRuta.equals("0") && agEntrega.equals("0")) ||
	    			  	(agCliente.equals("0") && agRuta.equals("1") && agEntrega.equals("0"))) && agFamilia.equals("1")){
		    	  celda = new PdfPCell(new Phrase(prevision[2], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(agCliente.equals("0") && agRuta.equals("0") && agFamilia.equals("1") && agEntrega.equals("0")){
		    	  celda = new PdfPCell(new Phrase(prevision[3], fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }else if(agCliente.equals("0") && agRuta.equals("0") && agFamilia.equals("0") && agEntrega.equals("0")){
		    	  celda = new PdfPCell(new Phrase(Utils.parseaCadenaEsp(prevision[2]), fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		          celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		          tabla.addCell(celda);
	    	  }
	    	  

	      }
	      
	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      celda.setColspan(6);
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorderWidth(0);
	      
	      tabla.addCell(celda);
		}catch (Exception e) {
			System.out.println(e);
		}
        return tabla;
	}
}
