package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import utils.Utils;

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

public class bCuerposTrazabilidadPdf extends PdfPageEventHelper{
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			
			String ruta = ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
            String rutaFuente = ruta + "/fuentes/" + "VERDANA.TTF";
            fuente = FontFactory.getFont(rutaFuente, 12F, 4).getBaseFont();
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
	
	public Font fuenteParrafo = new Font(helvetica(), 12, Font.NORMAL);
	public Font fuenteParrafo9 = new Font(helvetica(), 9, Font.NORMAL);
	public Font fuenteParrafo8 = new Font(helvetica(), 8, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoCursiva8 = new Font(helvetica(), 8, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteParrafoCursivaNegrita8 = new Font(helvetica(), 8, Font.BOLDITALIC);
    public Font fuenteNotaGrande = new Font(helvetica(), 14, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 11, Font.BOLD);
    
    
    
    public void anadeImagen( HttpServletRequest request,String nombreImagen, Document document ) throws DocumentException, MalformedURLException, IOException{
    	String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
    	int transparency[] = {255, 255};
		Image img = Image.getInstance(ruta + "/img/"+nombreImagen);
    	img.scaleToFit(200, 200); 
    	img.setAbsolutePosition(135, 300);
    	img.setTransparency( transparency );
    	img.setAlignment(Image.ALIGN_MIDDLE | Image.UNDERLYING);
		document.add( img );
    	
    }
    
    
	
	public void generaCabeceraFabricacion(ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
		
		try{
			  
		      PdfPTable datosEmpresa = new PdfPTable(1);
		      datosEmpresa.setWidthPercentage(100);
		      datosEmpresa.setWidths(new int[]{100});
				
		      PdfPCell celdaDatos = new PdfPCell(new Phrase( "PANADERIA PINEPAN S.L.", fuenteNotaMediana));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorderWidth(0);
		      datosEmpresa.addCell(celdaDatos);
		      
		      PdfPTable tableTitulo = new PdfPTable(1);
		      tableTitulo.setWidthPercentage(30);
		      tableTitulo.setWidths(new int[]{30});
		      tableTitulo.setSpacingAfter(10f);
		      
		      celdaDatos = new PdfPCell(new Phrase( "PLAN DE TRAZABILIDAD", fuenteNotaMediana));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidthBottom( 2 );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 15 );
		      tableTitulo.addCell(celdaDatos);      
		      
		      document.add( datosEmpresa );
		      document.add( tableTitulo );
		      
		      
			}catch (Exception e) {
				System.out.println(e);
			}
	        
	}
	    
    
	public void generaCuerpoFabricacion(ArrayList listaDetalles, HttpServletRequest request, Document document, String desglosado, String regularizado) throws DocumentException, IOException{
		

		int tamanio = 7;
		if(!Utils.skipNull(regularizado).equals(""))
			tamanio = 8;
		PdfPTable tablaCuerpo = new PdfPTable(tamanio);
		try{
			tablaCuerpo.setWidthPercentage(100);
			
			if(tamanio==8)
				tablaCuerpo.setWidths(new int[]{8,10,10,16,14,14,16,12});
			else
				tablaCuerpo.setWidths(new int[]{8,12,12,18,16,16,18});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			tablaCuerpo.setHorizontalAlignment( Element.ALIGN_LEFT );
			String producto = "";
			String productoActual = "";
						
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));   
		      
			for(int i=0; i<listaDetalles.size(); i++){
	
			  
				
			  String[] detalle = (String[]) listaDetalles.get(i);
				
			  productoActual = detalle[10];
		      if(!productoActual.equals(producto)){
		    	  producto = detalle[10];
		    	  
		    	  celda = new PdfPCell(new Phrase("PRODUCTO: " + producto, fuenteParrafoNegrita));
			      celda.setColspan(tamanio);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			      celda.setBorder( 0 );
			      celda.setBackgroundColor(Color.GRAY);
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
		    	  
			      celda = new PdfPCell(new Phrase("FECHA", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      celda = new PdfPCell(new Phrase("TIPO", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      celda = new PdfPCell(new Phrase("DOCUMENTO", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      celda = new PdfPCell(new Phrase("NOMBRE", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      celda = new PdfPCell(new Phrase("ENTRADAS", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);

			      celda = new PdfPCell(new Phrase("SALIDAS", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      celda = new PdfPCell(new Phrase("LOTE", fuenteParrafoNegrita));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorder( 0 );
			      celda.setBorderWidthBottom( 2f );
			      tablaCuerpo.addCell(celda);
			      
			      if(tamanio==8){
			    	  celda = new PdfPCell(new Phrase("REGULARIZACION", fuenteParrafoNegrita));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 2f );
				      tablaCuerpo.addCell(celda);
			      }
		      }
			  
		      celda = new PdfPCell(new Phrase(Utils.date2String( Utils.string2Date(detalle[0], Utils.DATE_MYSQL_SHORT), Utils.DATE_SHORT), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 2);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      celda.setBorderWidthTop( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase(detalle[1], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      celda = new PdfPCell(new Phrase(detalle[2], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 ); 
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      celda = new PdfPCell(new Phrase(detalle[3], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase(detalle[4], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase(detalle[5], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase(detalle[6], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f);
		      tablaCuerpo.addCell(celda);
		      
		      if(tamanio==8){
		    	  celda = new PdfPCell(new Phrase(detalle[11], fuenteParrafo8));
			      celda.setColspan(1);
			      celda.setPaddingBottom( 4 );
			      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			      celda.setBorderWidth( 0.5f);
			      celda.setBorderWidthRight( 0 );
			      tablaCuerpo.addCell(celda);
		      }
		      
		      if(detalle[1].equals("Fabricacion") && !Utils.skipNull(desglosado).equals("")){
				      
		    	  
		    	  
				      
				      celda = new PdfPCell(new Phrase("", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(4);
				      if(tamanio==8){
				    	  celda.setColspan(5);
				      }
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0f );
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("Producto Fabricado", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("Lote Fabricado", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("Cantidad Fabricada", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      celda.setBorderWidthRight( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      {
				    	  celda = new PdfPCell(new Phrase(" ", fuenteParrafoCursiva8));
					      celda.setColspan(4);
					      if(tamanio==8){
					    	  celda.setColspan(5);
					      }
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      tablaCuerpo.addCell(celda);
					      
					      
					      celda = new PdfPCell(new Phrase(detalle[8], fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      celda.setBorderWidthLeft( 0.5f );
					      tablaCuerpo.addCell(celda);
					      
					      celda = new PdfPCell(new Phrase(detalle[7], fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      celda.setBorderWidthLeft( 0.5f );
					      tablaCuerpo.addCell(celda);
					      
					      celda = new PdfPCell(new Phrase(detalle[9], fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      tablaCuerpo.addCell(celda);
				      }
		      }
				      
				      
				      celda = new PdfPCell(new Phrase(" ", fuenteParrafoNegrita));
				      celda.setColspan(tamanio);
				      celda.setPaddingBottom( 4 );
				      celda.setBorder( 0 );
				      tablaCuerpo.addCell(celda);
		      
			}
	      
	      document.add(  tablaCuerpo );
	      

		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
}
