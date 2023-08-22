package panaderia.informes;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class GenerarListadoStockPorLote extends PdfPageEventHelper{
	
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
    
    private Document document = null;
    private ByteArrayOutputStream buffer = null;
    private PdfWriter writer = null;
    
    public GenerarListadoStockPorLote(){
    	document = new Document();
    	document.setPageSize(PageSize.A4);
    }
    
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.COURIER, BaseFont.NOT_EMBEDDED);
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
    
	public void inicializaListado() throws DocumentException, FileNotFoundException{
    	buffer = new ByteArrayOutputStream();
    	writer = PdfWriter.getInstance(document, buffer);
        writer.setPageEvent(new GenerarListadoStockPorLote());
	}
          
	
	public void generaInformeStockLote( ArrayList listaStock, HttpServletRequest request, HttpServletResponse response ) throws DocumentException, IOException{
		
		//Inicializamos los atributos del Informe
		inicializaListado();
		
		//Abrimos el documento
		abrirDocumento();
		
		//Generamos la cabecera
		generaCabeceraInforme( );
		
		//Generamos el cuerpo
		generaCuerpo( listaStock, request );
		
        //Cerramos el documento
        cerrarDocumento( response );
	
	}
	
	/** Método que genera la cabecera de la Factura donde se encuentre el logo de la empresa y los datos de la misma */
	public void generaCabeceraInforme(  ) throws DocumentException, IOException{
		
		try{
			  
		      PdfPTable datosEmpresa = new PdfPTable(1);
		      datosEmpresa.setWidthPercentage(100);
		      datosEmpresa.setWidths(new int[]{100});
				
		      PdfPCell celdaDatos = new PdfPCell(new Phrase( "INFORME", fuenteNotaMediana));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorderWidth(0);
		      datosEmpresa.addCell(celdaDatos);
		      
		      PdfPTable tableTitulo = new PdfPTable(1);
		      tableTitulo.setWidthPercentage(30);
		      tableTitulo.setWidths(new int[]{30});
		      tableTitulo.setSpacingAfter(10f);
		      
		      celdaDatos = new PdfPCell(new Phrase( "Informe de stock por lote", fuenteNotaMediana));
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
	    

	/**stock[0] --> st_id
	 * stock[1] --> st_prod_id
	 * stock[2] --> st_cantidad_inicial
	 * stock[3] --> st_cantidad_final
	 * stock[4] --> st_regularizacion
	 * stock[5] --> st_salidas
	 * stock[6] --> st_f_caducidad
	 * stock[7] --> fc_fecha
	 * stock[8] --> fcd_producto
	 * stock[9] --> fcd_f_entrada
	 * stock[10] --> fcd_f_caducidad
	 * stock[11] --> fcd_lote
	 * */
	/** Método que genera la cabecera de la Factura donde se encuentre el logo de la empresa y los datos de la misma */
	public void generaCabeceraListado( String[] stock, PdfPTable tablaCuerpo ) throws DocumentException, IOException{
		
		PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
		  
		  //ARTICULO: PRODUCTO
	      celda = new PdfPCell(new Phrase("Articulo: "+stock[1]+ " " + stock[8], fuenteParrafoNegrita));
	      celda.setColspan(7);
	      celda.setPaddingBottom( 8 );
	      celda.setPaddingTop( 10 );
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celda.setBorder( 0 );
	      tablaCuerpo.addCell(celda);
		
		  //LOTE PRODUCTO
	      celda = new PdfPCell(new Phrase("Lote", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	      
	      //FECHA ENTRADD
	      celda = new PdfPCell(new Phrase("Fecha entrada", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	      
	      
	      //FECHA CADUCIDAD
	      celda = new PdfPCell(new Phrase("Fecha Caducidad", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	      
	      
	      //ENTRADAS
	      celda = new PdfPCell(new Phrase("Entradas", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	      
	      //SALIDAS
	      celda = new PdfPCell(new Phrase("Salidas", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	      
	      //REGULARIZACION
	      celda = new PdfPCell(new Phrase("Regularización", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
	     
	      //STOCK
	      celda = new PdfPCell(new Phrase("Stock", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 4 );
	      celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 1f );
	      tablaCuerpo.addCell(celda);
		
	}
	
	
	/**stock[0] --> st_id
	 * stock[1] --> st_prod_id
	 * stock[2] --> st_cantidad_inicial
	 * stock[3] --> st_cantidad_final
	 * stock[4] --> st_regularizacion
	 * stock[5] --> st_salidas
	 * stock[6] --> st_f_caducidad
	 * stock[7] --> fc_fecha
	 * stock[8] --> fcd_producto
	 * stock[9] --> fcd_f_entrada
	 * stock[10] --> fcd_f_caducidad
	 * stock[11] --> fcd_lote
	 * */	
    /** Genera el cuerpo de la Factura, mostrando todos los detalles de los productos del Cliente*/
	public void generaCuerpo( ArrayList listaStock, HttpServletRequest request ) throws DocumentException, IOException{
		

		PdfPTable tablaCuerpo = new PdfPTable(7);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{12,12,12,10,10,10,10});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			tablaCuerpo.setHorizontalAlignment( Element.ALIGN_LEFT );
						
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
		      
			String[] stockAnt = null;
			
			for(int i=0; i<listaStock.size(); i++){
			
				String[] stock = (String[]) listaStock.get(i);
				
				if( i == 0){
					stockAnt = stock;
					this.generaCabeceraListado( stock, tablaCuerpo );
				}else
					stockAnt = (String[]) listaStock.get(i-1);
				
				if(  i!=0 && !stockAnt[1].equals(stock[1]) ){											
					this.generaCabeceraListado( stock, tablaCuerpo );
				}
				
			  //LOTE
		      celda = new PdfPCell(new Phrase(stock[11], fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //FECHA ENTRADA
		      celda = new PdfPCell(new Phrase( Utils.date2String( Utils.string2Date(stock[7], Utils.DATE_MYSQL_SHORT), Utils.DATE_SHORT) , fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //FECHA CADUCIDAD
		      celda = new PdfPCell(new Phrase(Utils.date2String( Utils.string2Date(stock[6], Utils.DATE_MYSQL_SHORT), Utils.DATE_SHORT), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //ENTRADAS
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(stock[2]))), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //SALIDAS
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(stock[5]))), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      celda.setBorderWidth( 0.5f);
		      tablaCuerpo.addCell(celda);
		      
		      //REGULARIZACION
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(stock[4]))), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      celda.setBorderWidth( 0.5f);
		      tablaCuerpo.addCell(celda);
		      
		      //STOCK
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(stock[3]))), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
			}
	      //Añdimos el cuerpo del inventario
	      document.add(  tablaCuerpo );
	      

		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Informe de stock por lote");
	    document.addProducer();
	    document.open();
	}
	
	public void onEndPage(PdfWriter writer, Document document) {
        
	    Rectangle page = document.getPageSize();

	    PdfPCell pdfCell = null;
	    PdfPCell pdfCell2 = null;
	    
	    pdfCell = new PdfPCell( new Phrase("Fecha : "+String.valueOf( Utils.getFechaActualSinHoraString() ), fuenteParrafo8 ));
	    pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    pdfCell.setBorderWidth(0);
	    
	    pdfCell2 = new PdfPCell( new Phrase(String.valueOf( "Página " + writer.getPageNumber() ), fuenteParrafo8 ));
	    pdfCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    pdfCell2.setBorderWidth(0);

	    PdfPTable foot = new PdfPTable(2);
	    foot.addCell(pdfCell);
	    foot.addCell(pdfCell2);

	    foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

	    foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());

    }
	
	public void cerrarDocumento( HttpServletResponse response ) throws IOException{
		 document.close();
	     response.setHeader("Content-type", "application/pdf");
	     response.setHeader("Content-Disposition","inline; filename=\"InformeStockLote.pdf\"");
	
	     DataOutput output = new DataOutputStream( response.getOutputStream() );
	     output.write( buffer.toByteArray());
	}
		
}
