package panaderia.negocio;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import panaderia.beans.Clientes;
import panaderia.beans.HistoricoAc;
import panaderia.informes.GenerarListadoProductosPDF;
import panaderia.negocio.bCuerposFacturasVentaPdf_old.RoundRectangle;
import panaderia.struts.actions.GenerarHistoricoAcAction;
import utils.Utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

public class bCuerposHistoricoAcPdf extends PdfPageEventHelper{
	
	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    
    private Document document = null;
    private ByteArrayOutputStream buffer = null;
    private PdfWriter writer = null;
    
    private final int fondoCabeceraCuerpo = 1;
    private final int fondoCuerpo = 2;
    private final int fondoCeldaFiltro = 3;
    
    private int numLineas = 32;
    
    public bCuerposHistoricoAcPdf(){
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
    
	public void inicializa() throws DocumentException, FileNotFoundException{
    	buffer = new ByteArrayOutputStream();
    	writer = PdfWriter.getInstance(document, buffer);
        writer.setPageEvent(new bCuerposHistoricoAcPdf());
	}
	
	
	 
    public void anadeImagen( HttpServletRequest request, String nombreImagen ) throws DocumentException, MalformedURLException, IOException{
    	String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
    	int transparency[] = {255, 255};
		Image img = Image.getInstance(ruta + "/img/"+nombreImagen);
    	img.scaleToFit(200, 200); 
    	img.setAbsolutePosition(200, 330);
    	img.setTransparency( transparency );
    	img.setAlignment(Image.ALIGN_MIDDLE | Image.UNDERLYING);
		document.add( img );
    	
    }
    
    
    public byte[] generaHistoricoAc( HttpServletRequest request, HttpServletResponse response, ArrayList lista, Clientes cliente ) throws DocumentException, IOException{
    		
    		
    		inicializa();
    		
    		
    		abrirDocumento();
    		
			
    		generaCabeceraInforme( request, cliente );
    		
    		
    		generaCuerpoInforme( lista );
    		
    		
	        generaPiePagina();
	        
	        
	        cerrarDocumento( response );
	        
	        return buffer.toByteArray();
		
	}
    
    
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Listado De Entregas a Cuenta");
	    document.addProducer();
	    document.open();
	}
	
	
	public PdfPTable generaCabeceraInforme( HttpServletRequest request, Clientes cliente ){
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		String fechaInicio = request.getParameter("hacFechaDesde");
		if( Utils.empty( fechaInicio )  )
			fechaInicio = "-";
		String fechaFin = request.getParameter("hacFechaHasta");
		if( Utils.empty( fechaFin )  )
			fechaFin = "-";
			
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		try{
			tablaCabecera.setWidthPercentage(95);
			tablaCabecera.setWidths(new int[]{30,70});
			
		  Image im = Image.getInstance(ruta + "/img/logo_solo.jpg");
		  im.setAlignment(Image.LEFT | Image.UNDERLYING);
		  
	      
	      PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
	      im.scalePercent(100);
	      celda = new PdfPCell(im);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celda.setBorderWidth(0);
	      tablaCabecera.addCell(celda);
	      
	      RoundRectangle rectangle = new RoundRectangle();
		  PdfPTable tablaExterior = new PdfPTable(1);
		  tablaExterior.setWidthPercentage( 60 );
		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		  tablaExterior.getDefaultCell().setBorder( 0 );
		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
			
	      PdfPTable datosEmpresa = new PdfPTable(2);
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("Filtro historico de entregas a cuenta", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setColspan(2);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      celdaDatos.setBorderWidthBottom( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      

	      celdaDatos = new PdfPCell(new Phrase("Cliente", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setPadding( 4f );	      
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNombreComercial(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fecha Desde", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(fechaInicio, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fecha Hasta", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(fechaFin, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      tablaExterior.addCell( datosEmpresa );
	      
	      tablaCabecera.addCell( tablaExterior );
	      
	      document.add( tablaCabecera );
	      
	      PdfPTable tablaNombreListado = new PdfPTable(1);
	      tablaNombreListado.setWidths(new int[]{95});
	      tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      tablaNombreListado.setSpacingAfter(10f);
	      tablaNombreListado.setSpacingBefore(10f);
		
	      PdfPCell celdaNombreListado = new PdfPCell(new Phrase("Listado Entregas a Cuenta", fuenteNotaGrande));
	      celdaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaNombreListado.setBorderWidth( 0 );
	      tablaNombreListado.addCell( celdaNombreListado );
	      
	      document.add( tablaNombreListado );
	      
		}catch (Exception e) {
			System.out.println(" Error en generaCabeceraInforme()"+ e.toString() );
		}
        return tablaCabecera;
	}
		
	
    
    
	public void generaCuerpoInforme( ArrayList lista ) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 50 );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaExterior.getDefaultCell().setBorder( 0 );
		tablaExterior.getDefaultCell().setCellEvent( rectangle );
		
		PdfPTable tablaCuerpo = new PdfPTable(2);
		try{
			tablaCuerpo.setWidthPercentage(90);
			tablaCuerpo.setWidths(new int[]{15,15});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			
			PdfPCell celda = this.generaCabeceraCuerpoInforme( tablaCuerpo );
			
			float totalACuenta = 0;
			int contador = 1;
			
			for(int i=0; i<lista.size(); i++){
				HistoricoAc historico = (HistoricoAc) lista.get(i);
				
				celda = new PdfPCell(new Phrase( historico.getHacFechaEntrega(Utils.DATE_SHORT) , fuenteParrafo));
				celda.setColspan(1);
				celda.setBorderWidthRight( 0.5f );
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBorderWidth(0); 
				celda.setPadding( 4f );
				
				tablaCuerpo.addCell(celda);
	          
				totalACuenta += Float.parseFloat( historico.getHacCantidad() );
				
				String precio = Utils.formateaCantidad(Float.toString(Float.parseFloat(historico.getHacCantidad())));
				
				celda = new PdfPCell(new Phrase( precio , fuenteParrafo));
				celda.setColspan(1);
				celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celda.setBorderWidth(0); 
				celda.setPaddingLeft( 10f );
				
				tablaCuerpo.addCell(celda);
	          
				if( contador == numLineas && i != lista.size() - 1 ){
					tablaExterior.addCell( tablaCuerpo );
					document.add( tablaExterior );
					document.newPage();
					
					
					tablaExterior = new PdfPTable(1);
					tablaExterior.setWidthPercentage( 50 );
					tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
					tablaExterior.getDefaultCell().setBorder( 0 );
					tablaExterior.getDefaultCell().setCellEvent( rectangle );
					
					tablaCuerpo = new PdfPTable(2);
					tablaCuerpo.setWidthPercentage(90);
					tablaCuerpo.setWidths(new int[]{15,15});
					tablaCuerpo.getDefaultCell().setBorder( 0 );
		  			 
					celda = this.generaCabeceraCuerpoInforme( tablaCuerpo );
					
					contador = 0;
					numLineas = 40;
				}
				contador ++;
			}
	      
			
	      tablaExterior.addCell( tablaCuerpo );
	      
	      document.add(  tablaExterior );
	      
	      PdfPTable total = this.getTotalACuenta( totalACuenta );
	      
	      document.add(  total );
	      
		}catch (Exception e) {
			System.out.println( "generaCuerpoListado()"+e );
		}
	}

	
	public PdfPCell generaCabeceraCuerpoInforme( PdfPTable tablaCuerpo ){
		
		 PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
        
		  
	      celda = new PdfPCell(new Phrase("FECHA ENTREGA", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      
	      celda = new PdfPCell(new Phrase("CANTIDAD (�)", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
     
	      return celda;
	}
	
	public PdfPTable getTotalACuenta( float total ){
		PdfPTable tablaTotal = new PdfPTable(2);
		RoundRectangle rectangle = new RoundRectangle();
		try{
			tablaTotal.setWidthPercentage(50);
			tablaTotal.setSpacingBefore( 10f );
			tablaTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaTotal.setWidths(new int[]{15,15});
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
			celda = new PdfPCell(new Phrase( "Cantidad Total", fuenteParrafo) );
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBorderWidth(0); 
			celda.setCellEvent(rectangle);
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			celda.setPaddingBottom( 4f );
			tablaTotal.addCell(celda);
		       
		       
			celda = new PdfPCell( new Phrase( Utils.formateaCantidad(Float.toString(total)), fuenteParrafo));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorderWidth(0); 
			celda.setCellEvent(rectangle);
			celda.setPaddingBottom( 4f );
			tablaTotal.addCell(celda);
       
		}catch (Exception e) {
			System.out.println(e);
		}
		return tablaTotal;
		
	}
		  
	public void onEndPage(PdfWriter writer, Document document) {
        try {
            Rectangle page = document.getPageSize();
            PdfPTable foot = new PdfPTable(1);
            foot.setWidthPercentage(100);
            foot.getDefaultCell().setBorderWidth(0);
            foot.addCell( "P�gina " + writer.getPageNumber() );
            foot.setHorizontalAlignment(Element.ALIGN_LEFT);
            foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
	
	public void cerrarDocumento( HttpServletResponse response ) throws IOException{
		 document.close();
	     response.setHeader("Content-type", "application/pdf");
	     response.setHeader("Content-Disposition","inline; filename=\"FacturasVenta.pdf\"");
	
	     DataOutput output = new DataOutputStream( response.getOutputStream() );
	     output.write( buffer.toByteArray());
	}
	
	public void generaPiePagina(){
	}
	
	
	public Color dameColorFondo( int tipo ){
		Color color = new Color( 000, 000, 000 );
		switch ( tipo ) {
			case fondoCabeceraCuerpo:
				color = new Color (100,200,230);
			break;
			
			case fondoCuerpo:
				color = new Color (100,200,230);
			break;
			
			case fondoCeldaFiltro:
				color = new Color(251,243,232);
			break;
			
			
		}
		
		return color;
	}
	
}
