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

import panaderia.informes.GenerarListadoProductosPDF;
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
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class bCuerposGraficaVentasPdf extends PdfPageEventHelper{
	
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
    
    public bCuerposGraficaVentasPdf(){
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
        writer.setPageEvent(new bCuerposGraficaVentasPdf());
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
    
    
    public byte[] generaInformeGraficas( HttpServletRequest request, HttpServletResponse response, ArrayList lista, ArrayList graficaBarras ) throws DocumentException, IOException{
    		
    		
    		inicializa();
    		
    		
    		abrirDocumento();
    		
			
    		generaCabeceraInforme( request );
    		
    		
    		generaCuerpoInforme( lista, request, graficaBarras );
    		
    		
	        generaPiePagina();
	        
	        
	        cerrarDocumento( response );
	        
	        return buffer.toByteArray();
		
	}
    
    
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Listado de Productos");
	    document.addProducer();
	    document.open();
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
	
	
	public PdfPTable generaCabeceraInforme( HttpServletRequest request ){
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		String agrupacion = request.getParameter("agrupacion");
		String fechaInicio = request.getParameter("FechaInicio");
		String fechaFin = request.getParameter("FechaFin");
		String empresa = request.getParameter("empresa");
		String textoAgrupado = "Clientes";
		if( "1".equals(agrupacion) )
			textoAgrupado = "Productos";
		
		if( "1".equals(empresa) )
			empresa = "Pinepan S.L.";
		if( "2".equals(empresa) )
			empresa = "Obrador";
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		try{
			tablaCabecera.setWidthPercentage(95);
			tablaCabecera.setWidths(new int[]{50,50});
			
		  Image im = Image.getInstance(ruta + "/img/logo_solo.jpg");
		  im.setAlignment(Image.LEFT | Image.UNDERLYING);
		  
	      
	      PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	      
	      im.scalePercent(100);
	      celda = new PdfPCell(im);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celda.setBorderWidth(0);
	      tablaCabecera.addCell(celda);
	      
	      RoundRectangle rectangle = new RoundRectangle();
		  PdfPTable tablaExterior = new PdfPTable(1);
		  tablaExterior.setWidthPercentage( 50 );
		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		  tablaExterior.getDefaultCell().setBorder( 0 );
		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
			
	      PdfPTable datosEmpresa = new PdfPTable(2);
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("Filtro para la generaci�n de las gr�ficas", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setColspan(2);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      celdaDatos.setBorderWidthBottom( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fecha de Inicio", fuenteParrafo));
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
	      
	      celdaDatos = new PdfPCell(new Phrase("Fecha de Fin", fuenteParrafo));
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
	      
	      celdaDatos = new PdfPCell(new Phrase("Agrupado por", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setPadding( 4f );	      
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(textoAgrupado, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 4f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      if(!empresa.equals(""))
	      {
	    	  celdaDatos = new PdfPCell(new Phrase("Filtrado por", fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidth(0); 
		      celdaDatos.setBorderWidthRight( 0.5f );
		      celdaDatos.setPadding( 4f );	      
		      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
		      celdaDatos.setBorderWidth(0); 
		      celdaDatos.setPadding( 4f );
		      datosEmpresa.addCell(celdaDatos);
		      
		      celdaDatos = new PdfPCell(new Phrase(empresa, fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidth(0); 
		      celdaDatos.setPadding( 4f );
		      datosEmpresa.addCell(celdaDatos);
	      }
	      
	      tablaExterior.addCell( datosEmpresa );
	      
	      tablaCabecera.addCell( tablaExterior );
	      
	      document.add( tablaCabecera );
	      
	      PdfPTable tablaNombreListado = new PdfPTable(1);
	      tablaNombreListado.setWidths(new int[]{95});
	      tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      tablaNombreListado.setSpacingAfter(10f);
	      tablaNombreListado.setSpacingBefore(10f);
		
	      PdfPCell celdaNombreListado = new PdfPCell(new Phrase("Gr�ficas de Productos", fuenteNotaGrande));
	      celdaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaNombreListado.setBorderWidth( 0 );
	      tablaNombreListado.addCell( celdaNombreListado );
	      
	      document.add( tablaNombreListado );
	      
		}catch (Exception e) {
			System.out.println(" Error en generaCabeceraInforme()"+ e.toString() );
		}
        return tablaCabecera;
	}
		
	
    
    
	public void generaCuerpoInforme( ArrayList lista, HttpServletRequest request, ArrayList graficaBarras ) throws DocumentException, IOException{
		
		for(int i = 0; i <graficaBarras.size(); i++){
			Image graficoSuministro = Image.getInstance( (String)graficaBarras.get(i) );
		    graficoSuministro.setAlignment(Image.ALIGN_CENTER);			     
		    document.add( graficoSuministro );
			document.newPage();
		}
		
		PdfPTable tablaNombreListado = new PdfPTable(1);
	      tablaNombreListado.setWidths(new int[]{95});
	      tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      tablaNombreListado.setSpacingAfter(10f);
	      tablaNombreListado.setSpacingBefore(10f);

		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 90 );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaExterior.getDefaultCell().setBorder( 0 );
		tablaExterior.getDefaultCell().setCellEvent( rectangle );
		
		int[] widths = new int[]{30,120,50};
		int tamanio = 3;
		if(request.getParameter("agrupacion").equals("1")){
			widths = new int[]{120,50};
			tamanio = 2;
		}
		
		PdfPTable tablaCuerpo = new PdfPTable(tamanio);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(widths);
			tablaCuerpo.setKeepTogether(true);
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			
			PdfPCell celda = this.generaCabeceraCuerpoInforme( tablaCuerpo, request.getParameter("agrupacion"));
			
		      
	      for(int i=0; i<lista.size(); i++){
	    	  String[] datos = ( String[]) lista.get(i);
	    	  
	    	  if(!request.getParameter("agrupacion").equals("1")){
		    	  celda = new PdfPCell(new Phrase( datos[2] , fuenteParrafoNegrita));
		          celda.setColspan(1);
		          celda.setBorderWidthRight( 0.5f );
		          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		          celda.setBorderWidth(0); 
		          celda.setPadding( 4f );
		          
		          tablaCuerpo.addCell(celda);
	    	  }
	    	  
	    	  
	    	  celda = new PdfPCell(new Phrase( datos[1] , fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setBorderWidthRight( 0.5f );
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); 
	          celda.setPadding( 4f );
	          
	          tablaCuerpo.addCell(celda);
	          
	          String precio = Utils.formateaCantidad(Float.toString(Float.parseFloat(datos[0])));
	          
	          celda = new PdfPCell(new Phrase( precio , fuenteParrafoNegrita));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); 
	          celda.setPaddingLeft( 10f );
	          
	          tablaCuerpo.addCell(celda);
	          
	      }
	      
	      tablaExterior.addCell( tablaCuerpo );
	      
	      document.add(  tablaExterior );
	      
	      
	      this.generaPiePagina();
	      
		}catch (Exception e) {
			System.out.println( "generaCuerpoListado()"+e );
		}
	}
	
	
	
	public void onEndPage(PdfWriter writer, Document document) {
        try {
            Rectangle page = document.getPageSize();
            PdfPTable foot = new PdfPTable(1);
            foot.getDefaultCell().setBorderWidth(0);
            foot.addCell( "P�gina " + writer.getPageNumber() );
            foot.setHorizontalAlignment(Element.ALIGN_RIGHT);
            foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

	
	public PdfPCell generaCabeceraCuerpoInforme( PdfPTable tablaCuerpo, String producto){
		
		 PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
        
		 if(!producto.equals("1")){
			  
		      celda = new PdfPCell(new Phrase("CODIGO", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      
		      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
		      tablaCuerpo.addCell(celda);
		 }
		 
		  
	      celda = new PdfPCell(new Phrase("NOMBRE", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      
	      celda = new PdfPCell(new Phrase("IMPORTE (�)", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
     
	      return celda;
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
