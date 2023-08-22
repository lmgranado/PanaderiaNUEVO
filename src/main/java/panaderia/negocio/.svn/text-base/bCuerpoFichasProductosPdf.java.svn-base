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

import panaderia.beans.ProductoComposicion;
import panaderia.beans.ProductoFicha;
import panaderia.beans.Productos;
import panaderia.utilidades.Empresa;
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
public class bCuerpoFichasProductosPdf extends PdfPageEventHelper{

	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
	public Font fuenteParrafo7 = new Font(helvetica(), 7, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNoDatos = new Font(helvetica(), 9, Font.BOLD, Color.RED );
    
    private Document document = null;
    private ByteArrayOutputStream buffer = null;
    private PdfWriter writer = null;
    
    private final int fondoCabeceraCuerpo = 1;
    private final int fondoCuerpo = 2;
    private final int fondoCeldaFiltro = 3;
    
    public bCuerpoFichasProductosPdf(){
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
    
	 /** Añade una imagen al centro del documento con la trasparencia que le indiquemos */
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
    
    
    public byte[] generaFichasProductos( HttpServletRequest request, HttpServletResponse response, String[] chkValues ) throws DocumentException, IOException{
    		
    		//Inicializamos los atributos del Informe
    		inicializa();
    		
    		//Abrimos el documento
    		abrirDocumento();
    		
  		  	for(int i=0; i<chkValues.length; i++){
  			  
  		  		  ProductoFicha ficha = ProductoFicha.getProductoFichaByProdfProdId( chkValues[i] );
  		  		  ArrayList listaComposicion = ProductoComposicion.getAllComposicionProductosByProdCompprodFId( ficha.getProdfId() );
  		  		  
  		  		  if( !Utils.empty(ficha.getProdfId()) ){
		  			  //Generamos la cabecera
		  			  generaCabeceraInforme( request );
		    		
		  			  //Generamos el cuerpo
		  			  generaCuerpoInforme( ficha, listaComposicion, request );
		  			  
		  			  document.newPage();
  		  		  }
  		  		  else{
  		  			  System.out.println( "El producto con id "+ficha.getProdfProdId()+" no tiene ninguna ficha asociada." );
  		  		  }
	  			  
  		  	}
  			  //Cerramos el documento
  			  cerrarDocumento( response );
	        
  			  return buffer.toByteArray();
		
	}
    
    
    public void inicializa() throws DocumentException, FileNotFoundException{
    	document = new Document();    
	    document.setPageSize(PageSize.A4);
    	buffer = new ByteArrayOutputStream();
    	writer = PdfWriter.getInstance(document, buffer);
        writer.setPageEvent( new bCuerpoFichasProductosPdf() );
	}
    
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Ficha de Productos");
	    document.addProducer();
	    document.open();
	}
	
	public PdfPTable generaCabeceraInforme( HttpServletRequest request ){
		//String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		PdfPTable tablaCabecera = new PdfPTable(2);
		
		try{
			
			
			tablaCabecera.setWidthPercentage(90);
			tablaCabecera.setWidths(new int[]{80,20});
			tablaCabecera.setSpacingAfter(5f);
			tablaCabecera.getDefaultCell().setBorder( 2 );
			tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		
			PdfPCell celda = new PdfPCell(new Phrase("FICHAS TÉCNICAS", fuenteParrafoNegrita));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCabecera.addCell(celda);
		      
			celda = new PdfPCell(new Phrase("FECHA", fuenteParrafoNegrita));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setPadding( 2f );
			tablaCabecera.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "DESCRIPCIÓN  DE LOS PRODUCTOS" , fuenteParrafoNegrita));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			celda.setPadding( 2f );
			tablaCabecera.addCell(celda);
		      
			celda = new PdfPCell(new Phrase( Utils.getFechaActualSinHoraString() , fuenteParrafoNegrita));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setPadding( 2f );
			tablaCabecera.addCell(celda);
		      
			document.add( tablaCabecera );
	
	      
		}catch (Exception e) {
			System.out.println(" Error en generaCabeceraInforme()"+ e.toString() );
		}
        return tablaCabecera;
	}
	
	
	/** Genera el cuerpo del Listado */
	public void generaCuerpoInforme( ProductoFicha ficha, ArrayList listaComposicion, HttpServletRequest request ) throws DocumentException, IOException{
		
		/*PdfPTable tablaNombreListado = new PdfPTable(3);
		tablaNombreListado.setWidths(new int[]{95});
		tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaNombreListado.setSpacingAfter(10f);
		tablaNombreListado.setSpacingBefore(10f);

		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 80 );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaExterior.getDefaultCell().setBorder( 0 );*/
	
		PdfPTable tablaCuerpo = new PdfPTable(3);
		int[] widths = new int[]{33,33,33};
		try{
			tablaCuerpo.setWidthPercentage(90);
			tablaCuerpo.setWidths(widths);
			tablaCuerpo.setKeepTogether(true);
			//tablaCuerpo.getDefaultCell().setBorder( 0 );
			tablaCuerpo.setSpacingBefore( 5f );
			tablaCuerpo.setSpacingAfter( 5f );
		
	    	String nombreProducto = ( Productos.getProductosByProdId( ficha.getProdfProdId() ) ).getProdNombre();  
			PdfPCell  celda = new PdfPCell(new Phrase( "Nombre del producto" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	          
			celda = new PdfPCell(new Phrase( nombreProducto , fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Denominación Comercial" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	        
			celda = new PdfPCell(new Phrase( ficha.getProdfDenominacionComercial() , fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Clasificación del producto según legislación vigente" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	          
			celda = new PdfPCell(new Phrase( ficha.getProdfClasificacionLegislacion() , fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Clasificación del producto según industria" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	          
			celda = new PdfPCell(new Phrase( ficha.getProdfClasificacionIndustria() , fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Marca comercial sobre la que se elabora y envasa" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfMarcaComercial(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Composición cualitativa y cuantitativa" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			if( listaComposicion.size() > 0 ){
				for( int i=0; i<listaComposicion.size(); i++ ){
					ProductoComposicion composicion = (ProductoComposicion) listaComposicion.get(i);
					
					celda = new PdfPCell(new Phrase( composicion.getProdcompNombre(), fuenteParrafo));
					celda.setColspan(1);
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setPadding( 2f );
					tablaCuerpo.addCell(celda);
					
					celda = new PdfPCell(new Phrase( composicion.getProdcompDescripcion(), fuenteParrafo));
					celda.setColspan(1);
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setPadding( 2f );
					tablaCuerpo.addCell(celda);
					
					celda = new PdfPCell(new Phrase( Utils.formateaCantidad(composicion.getProdcompPeso()) + " " + composicion.getProdcompMedida(), fuenteParrafo));
					celda.setColspan(1);
					celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					celda.setPadding( 2f );
					tablaCuerpo.addCell(celda);
				}
			}else{
				celda = new PdfPCell(new Phrase( "No existe composición cualitativa y cuantitativa para dicho producto" , fuenteParrafo));
				celda.setColspan(3);
				celda.setHorizontalAlignment(Element.ALIGN_LEFT);
				celda.setPadding( 2f );
				tablaCuerpo.addCell(celda);
			}
			
			celda = new PdfPCell(new Phrase( "Procesado" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	        
			celda = new PdfPCell(new Phrase(  ficha.getProdfProcesado(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Formato de Presentación" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
	        
			celda = new PdfPCell(new Phrase(  ficha.getProdfFormato(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Envasado (cuando el cliente es una colectividad)" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfEnvasado(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Etiquetado" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfEtiquetado(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Condiciones de almacenamiento" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfCondicionAlmacenamiento(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Condiciones de transporte" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfCondicionTransporte(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Destino final previsto para el producto" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfDestinoProducto(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Caracteristicas del número de lote (coincide con la fecha de fabricación F/F)" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfCaracteristicaLote(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Consumo preferente (C/P)" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfConsumoPreferente(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase( "Características del producto terminado (R.D. 1137/1984, y posteriores modificaciones)" , fuenteParrafoNegrita));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			tablaCuerpo.addCell(celda);
			
			celda = new PdfPCell(new Phrase(  ficha.getProdfCaracteristicaProducto(), fuenteParrafo));
			celda.setColspan(3);
			celda.setHorizontalAlignment(Element.ALIGN_LEFT);
			celda.setPadding( 2f );
			tablaCuerpo.addCell(celda);
			
			document.add( tablaCuerpo );
	      
		}catch (Exception e) {
			System.out.println( "generaCuerpoInforme()"+e );
		}
	}
	
	
	public void cerrarDocumento( HttpServletResponse response ) throws IOException{
		 document.close();
	     response.setHeader("Content-type", "application/pdf");
	     response.setHeader("Content-Disposition","inline; filename=\"FichasProductos.pdf\"");
	
	     DataOutput output = new DataOutputStream( response.getOutputStream() );
	     output.write( buffer.toByteArray());
	}
	
	public void onEndPage(PdfWriter writer, Document document) {
            /*Rectangle page = document.getPageSize();
            PdfPTable foot = new PdfPTable(1);
            foot.getDefaultCell().setBorderWidth(0);
            foot.addCell( new Phrase( "Página " + writer.getPageNumber(), fuenteParrafo7 ) );
            foot.setHorizontalAlignment(Element.ALIGN_RIGHT);
            foot.setTotalWidth(page.width() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());*/
			Empresa empresa = new Empresa( );
        	try{
        	
	        	PdfPTable tablaPie = new PdfPTable(3);
	        	tablaPie.setHorizontalAlignment(Element.ALIGN_CENTER);
	        	tablaPie.setSpacingAfter(5f);
    			tablaPie.setWidthPercentage(90);
    			tablaPie.setWidths(new int[]{40,50,10});
    		
	    	      PdfPCell celdaDatos = new PdfPCell(new Phrase("Empresa", fuenteParrafo7));
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	      celdaDatos.setColspan(1);
	    	      celdaDatos.setBottom( 5f );
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      celdaDatos = new PdfPCell(new Phrase("Actividad", fuenteParrafo7));
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	      celdaDatos.setColspan(1);
	    	      celdaDatos.setBottom( 5f );
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      celdaDatos = new PdfPCell(new Phrase("Página", fuenteParrafo7));
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	      celdaDatos.setColspan(1);
	    	      celdaDatos.setBottom( 5f );
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      celdaDatos = new PdfPCell(new Phrase( 
	    	    		  empresa.getNombreComercial()+"\n"+empresa.getDireccion1()+"\n"+empresa.getLocalidad()+" ("+empresa.getCodigoPostal()+"), "+empresa.getProvincia() , fuenteParrafo7));
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      celdaDatos = new PdfPCell(new Phrase( "Fabricación/Elaboración/Transformación de pan y masas congeladas.\nEnvasado de pan y masas congeladas" , fuenteParrafo7));
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      celdaDatos = new PdfPCell(new Phrase( "\n"+writer.getPageNumber(), fuenteParrafo7 ) );
	    	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER );
	    	      celdaDatos.setPadding( 4f );
	    	      tablaPie.addCell(celdaDatos);
	    	      
	    	      Rectangle page = document.getPageSize();
	    	      tablaPie.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
	    	      tablaPie.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()+30, writer.getDirectContent());
	    	      //document.add( tablaPie );
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
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
