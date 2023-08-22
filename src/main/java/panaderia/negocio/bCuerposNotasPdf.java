package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.Clientes;
import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

public class bCuerposNotasPdf {
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.TIMES_ROMAN, BaseFont.NOT_CACHED);
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
	
	public Font fuenteParrafo = new Font(helvetica(), 7, Font.NORMAL);
	public Font fuenteParrafo9 = new Font(helvetica(), 7, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 8, Font.NORMAL);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.NORMAL,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 7, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 9, Font.NORMAL);

    
    private int numProductos = 15;
    private float tamanyoTablaCuepro = 220;
	
	
	public PdfPTable generaCabeceraDatosCliente(NotasEntrega nota, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		StringBuffer ruta = request.getRequestURL();
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		
		
		try{
			
		  tablaCabecera.setWidthPercentage(100);
		  tablaCabecera.setWidths(new int[]{40,60});
			
		  
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{40,60});
	      tablaIzquierda.getDefaultCell().setBorder(0);
	      
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafoNegrita));	      
	      celdaDatos = new PdfPCell(new Phrase("Nº NOTA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 6f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(nota.getNotId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 5f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	     	      
	      
	      celdaDatos = new PdfPCell(new Phrase("FECHA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(nota.getNotFecha("dd/MM/yyyy"), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("ORDEN", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 5f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(nota.getNotOrden(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 5f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("RUTA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 5f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(nota.getClDatosRelacionados()[6], fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 5f );
	      celdaDatos.setPaddingBottom( 5f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("Datos Cliente" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNombreComercial(), fuenteParrafo9));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClTelefono(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase( cliente.getClCodigoPostal() + " " + cliente.getClDatosRelacionados()[3] + " ("+cliente.getClDatosRelacionados()[4]+")" , fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	     
	      
	      tablaCabecera.addCell( tablaDerecha );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
	
    
    
	public void generaCuerpoFactura(NotasEntrega nota, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 100 );
		tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaExterior.getDefaultCell().setBorder( 0 );
		
		int numero_piezas = 0;
		int contador = 1;
		PdfPTable tablaCuerpo = new PdfPTable(3);
		
		try{
			tablaCuerpo.setWidthPercentage(100);
			
			
			tablaCuerpo.setWidths( new int[]{12,37,12} );
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			
			
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	         
			  

			  celda = new PdfPCell(new Phrase("CANTIDAD", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      
		      
		      
		      
		      tablaCuerpo.addCell(celda);
		      
		      
		      celda = new PdfPCell(new Phrase("DESCRIPCION", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      
		      
		      
		      
		      
		      tablaCuerpo.addCell(celda);

		      
		      
		      celda = new PdfPCell(new Phrase("LOTE", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      
		      
		      
		      tablaCuerpo.addCell(celda);
	    	  
	  
			 
		   
		      float importeTotal = 0;
		      
	      for(int i=0; i<listaDetalles.size(); i++){
	    	  float precioTotal = 0;
	    	   	  
	    	  NotasEntregaDetalle detalle = (NotasEntregaDetalle)listaDetalles.get(i);
	    	  
	    	  numero_piezas += Integer.parseInt( detalle.getNotdetCantidad() );
	    		  
	    	  
	    	  celda = new PdfPCell(new Phrase("" + detalle.getNotdetCantidad(), fuenteParrafoNegrita));
	          celda.setColspan(1);
	          
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);celda.setFixedHeight(12);
	          celda.setBorderWidth(0); 
	          
	          
	          tablaCuerpo.addCell(celda);
	          
	          
	          celda = new PdfPCell(new Phrase(detalle.getNotdetProducto(), fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(12);
	          celda.setBorderWidth(0); 
	          
	          
	          
	          tablaCuerpo.addCell(celda);
	  	          
	          
	          
	         
	          
	          precioTotal += Float.parseFloat(detalle.getNotdetPrecio());
	          
	          
	          
	          celda = new PdfPCell(new Phrase(detalle.getLote(), fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);celda.setFixedHeight(12);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0 );
	          
	          
	          tablaCuerpo.addCell(celda);
	          
	          importeTotal += Float.parseFloat(detalle.getNotdetImporte());
	          
	          
	          
	          numProductos = 16;
	          if( contador == numProductos && i != listaDetalles.size() - 1 ){
	        	  tablaExterior.addCell( tablaCuerpo );
	    	      
	    	      document.add(  tablaExterior ); 
	    	      
	    	      document.newPage();
	    	      
	    	      
	    	      PdfPTable datosCliente = this.generaCabeceraDatosCliente( nota, cliente, listaDetalles, request );
	    	      
	    	      
	    	      document.add( datosCliente );
	    	      
	    	      
	    	      tablaExterior = new PdfPTable(1);
	    		  tablaExterior.setWidthPercentage( 100 );
	    		  tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
	    		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		  tablaExterior.getDefaultCell().setBorder( 0 );
	    		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
	    		  
	    		  
	    		  
	    		  tablaCuerpo = new PdfPTable(3);
	  			  tablaCuerpo.setWidths( new int[]{12,37,12} );
			      tablaCuerpo.setWidthPercentage(100);
				  tablaCuerpo.getDefaultCell().setPaddingLeft( 10f );
				  
				  
	    	      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	 	         
				  
	    	      
	  			  celda = new PdfPCell(new Phrase("CANTIDAD", fuenteParrafoNegrita));
	  		      celda.setColspan(1);
	  		      celda.setPaddingBottom( 5 );
	  		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	  		      celda.setBorder( 0 );
	  		      celda.setBorderWidthBottom( 0.5f );
	  		      celda.setBorderWidthRight( 0.5f );
	  		      
	  		      
	  		      tablaCuerpo.addCell(celda);
	  		      
	  		      
	  		      celda = new PdfPCell(new Phrase("DESCRIPCION", fuenteParrafoNegrita));
	  		      celda.setColspan(1);
	  		      celda.setPaddingBottom( 5 );
	  		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	  		      celda.setBorder( 0 );
	  		      celda.setBorderWidthBottom( 0.5f );
	  		      celda.setBorderWidthRight( 0.5f );
	  		      
	  		      
	  		      tablaCuerpo.addCell(celda);

	  		      
	  		      
	  		      celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	  		      celda.setColspan(1);
	  		      celda.setPaddingBottom( 5 );
	  		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	  		      celda.setBorder( 0 );
	  		      celda.setBorderWidthBottom( 0.5f );
	  		      
	  		      
	  		      tablaCuerpo.addCell(celda);
				  
				  
				  contador = 0;
	          }
	          
	          contador ++;
	      }
	      
	      
	      
	      tablaExterior.addCell( tablaCuerpo );
	      document.add(  tablaExterior );
	      
	      
	      this.generaPieFactura( nota, cliente, importeTotal, numero_piezas, request, document );
		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
	
	
	public void generaPieFactura( NotasEntrega nota, Clientes cliente, float importeTotal, int numero_piezas, HttpServletRequest request, Document document ){
		
		RoundRectangle rectangle = new RoundRectangle();
		try{
			PdfPTable tablaObservacion = new PdfPTable(1);
			
			tablaObservacion.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaObservacion.setSpacingAfter(2f);
			tablaObservacion.setSpacingBefore(2f);
			tablaObservacion.getDefaultCell().setBorder( 0 );
			
			tablaObservacion.getDefaultCell().setFixedHeight( 40 );
			tablaObservacion.setWidthPercentage(100);
			
			PdfPTable tablaObs = new PdfPTable(1);
			tablaObs.setWidths(new int[]{100});
		      
		      
			PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafoNegrita));	      
			celdaDatos = new PdfPCell(new Phrase("Observaciones:", fuenteParrafoNegrita));
			celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
			celdaDatos.setBorderWidth(0);
			celdaDatos.setPaddingTop( 2f );
			celdaDatos.setPaddingBottom( 2f );
			
			tablaObs.addCell(celdaDatos);
		
			celdaDatos = new PdfPCell(new Phrase( nota.getNotObservaciones(), fuenteParrafoNegrita));
			celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
			celdaDatos.setBorderWidth(0);
			celdaDatos.setPaddingTop( 2f );
			celdaDatos.setPaddingBottom( 2f );
			
			tablaObs.addCell(celdaDatos);
			
			tablaObservacion.addCell( tablaObs );
		      
		      
			PdfPTable tablaPie = new PdfPTable(6);
			tablaPie.setWidthPercentage(100);
			tablaPie.setSpacingBefore( 2f );
			tablaPie.setWidths(new int[]{24,16,4,20,16,17});
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
			
			celda = new PdfPCell(new Phrase( "PIEZAS TOTALES", fuenteParrafoNegrita) );
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorderWidth(0); 
			
			celda.setPaddingBottom( 2f );	
			celda.setPaddingTop( 2f );	
			
			tablaPie.addCell(celda);
	    
	      
		   celda = new PdfPCell(new Phrase("" + numero_piezas, fuenteParrafoNegrita));
		   celda.setColspan(1);
		   celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		   celda.setBorderWidth(0); 
		   
		   celda.setPaddingBottom( 4f );	
		   celda.setPaddingTop( 2f );	
		   
		   tablaPie.addCell(celda);
		   
	       celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	       celda.setColspan(4);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0);
	       tablaPie.addCell(celda);
       
	       document.add( tablaObservacion );
	       document.add( tablaPie );
       
		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
		
}
