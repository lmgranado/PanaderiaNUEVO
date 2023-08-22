package panaderia.informes;

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
import panaderia.beans.Localidades;
import panaderia.utilidades.Empresa;

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

public class GenerarListadoClientesPDF extends PdfPageEventHelper{
	
	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    
    private int numClientes = 11;
    private float tamanyoTablaCuepro = 600;
    private Document document = null;
    private ByteArrayOutputStream buffer = null;
    private PdfWriter writer = null;
    
    private final int fondoCabeceraCuerpo = 1;
    private final int fondoCuerpo = 2;
    
    public GenerarListadoClientesPDF(){
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
        writer.setPageEvent(new GenerarListadoClientesPDF());
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
    
    
    public void generaListadoClientes( HttpServletRequest request, HttpServletResponse response, ArrayList clientes ) throws DocumentException, IOException{
    		
    		//Inicializamos los atributos del Informe
    		inicializaListado();
    		
    		//Abrimos el documento
    		abrirDocumento();
    		
			//Generamos la cabecera
    		generaCabeceraInforme( request );
    		
    		//Generamos el cuerpo
    		generaCuerpoListado( clientes, request );
    		
    		//Generamos el Pie de Página
	        generaPiePagina();
	        
	        //Cerramos el documento
	        cerrarDocumento( response );
		
	}
    
    
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Listado de Clientes");
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
		//HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
	    //pie.setBorderWidth(0);
	    //document.setFooter(pie);
	}
	
	
	public PdfPTable generaCabeceraInforme( HttpServletRequest request ){
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		try{
			tablaCabecera.setWidthPercentage(95);
			tablaCabecera.setWidths(new int[]{65,35});
			
		  Image im = Image.getInstance(ruta + "/img/logo_solo.jpg");
		  im.setAlignment(Image.LEFT | Image.UNDERLYING);
		  
	      //Parte 1
	      PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
	      im.scalePercent(100);
	      celda = new PdfPCell(im);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celda.setBorderWidth(0);
	      tablaCabecera.addCell(celda);
	      
	      PdfPTable datosEmpresa = new PdfPTable(1);
	      //Sería conveniente en los listados, dar la posibilidad de escoger de la empresa de la que se quiere el listado
	      Empresa empresa = new Empresa( );
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase( empresa.getNombreComercial(), fuenteNotaGrande));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase( empresa.getDireccion1(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase( empresa.getCodigoPostal()+" "+empresa.getLocalidad()+" ("+empresa.getProvincia()+")", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fábrica: "+empresa.getTelefonoFabrica(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase( empresa.getDireccion2(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	     
	      celdaDatos = new PdfPCell(new Phrase("Oficina: "+empresa.getTelefonoOficina(), fuenteParrafo));
	      
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("CIF : "+empresa.getCif(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( datosEmpresa );
	      
	      document.add( tablaCabecera );
	      
	      PdfPTable tablaNombreListado = new PdfPTable(1);
	      tablaNombreListado.setWidths(new int[]{95});
	      tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      tablaNombreListado.setSpacingAfter(10f);
	      tablaNombreListado.setSpacingBefore(10f);
		
	      PdfPCell celdaNombreListado = new PdfPCell(new Phrase("Listado de Clientes", fuenteNotaGrande));
	      celdaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaNombreListado.setBorderWidth( 0 );
	      tablaNombreListado.addCell( celdaNombreListado );
	      
	      document.add( tablaNombreListado );
	      
		}catch (Exception e) {
			System.out.println(" Error en generaCabeceraListado()"+ e.toString() );
		}
        return tablaCabecera;
	}
		
	
    
    /** Genera el cuerpo del Listado */
	public void generaCuerpoListado( ArrayList clientes, HttpServletRequest request ) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 100 );
		tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaExterior.getDefaultCell().setBorder( 0 );
		tablaExterior.getDefaultCell().setCellEvent( rectangle );
		
		//Introducimos una imagen en el centro
		this.anadeImagen( request, "logo_solo_grande.jpg" );
		
		
		int contador = 1;
		
		PdfPTable tablaCuerpo = new PdfPTable(6);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{12,30,22,20,11,11});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			//Introducimos la cabecera del cuerpo del listado
			PdfPCell celda = this.generaCabeceraCuerpoListado( tablaCuerpo );
			
		      
	      for(int i=0; i<clientes.size(); i++){
	    	  Clientes cliente = (Clientes) clientes.get(i);
	    	  //CANTIDAD DEL PRODUCTO
	    	  celda = new PdfPCell(new Phrase( cliente.getClNif() , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setBorderWidthRight( 0.5f );
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); celda.setFixedHeight(50);
	          celda.setPadding( 4f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //DETALLE DEL PRODUCTO
	          celda = new PdfPCell(new Phrase( cliente.getClNombre() + " " + cliente.getClApellidos() , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );celda.setFixedHeight(50);
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //IMPORTE TOTAL DEL PRODUCTO
	          celda = new PdfPCell( new Phrase( cliente.getClNombreComercial() , fuenteParrafo) );
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );celda.setFixedHeight(50);
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //DIRECCION
	          Localidades localidad = Localidades.getLocalidadesByLocId( cliente.getClLocId() );
	          celda = new PdfPCell(new Phrase( cliente.getClDireccion()+", "+localidad.getLocNombre(), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );celda.setFixedHeight(50);
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //IVA DEL PRODUCTO
	          celda = new PdfPCell(new Phrase( cliente.getClTelefono() , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );celda.setFixedHeight(50);
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //DESCUENTO DEL PRODUCTO
	          celda = new PdfPCell( new Phrase( cliente.getClMovil() , fuenteParrafo) );
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); celda.setFixedHeight(50);
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //Si el contador llega a 30 clientes, debemos de pintar el pie en blanco y pasar a la
	          //siguiente página y volver a pintar la cabecera
	          numClientes = 11;
	          if( contador == numClientes && i != clientes.size() - 1 ){
	        	  tablaExterior.addCell( tablaCuerpo );
	    	      //Añadimos el cuerpo del Listado
	    	      document.add(  tablaExterior );
	    	      document.newPage();
	    	      
	    	      //Añadimos la cabecera
	    	      this.generaCabeceraInforme( request );
	    	      
	    	      //Reiniciamos los datos de las tablas para que no añadamos los mismos datos de nuevo
	    	      tablaExterior = new PdfPTable(1);
	    		  tablaExterior.setWidthPercentage( 100 );
	    		  tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
	    		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		  tablaExterior.getDefaultCell().setBorder( 0 );
	    		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
	    		  //tablaExterior.deleteBodyRows();
	    		  
	    		  tablaCuerpo = new PdfPTable(6);
			      tablaCuerpo.setWidthPercentage(100);
			      tablaCuerpo.setWidths(new int[]{12,30,22,20,11,11});
				  tablaCuerpo.getDefaultCell().setPaddingLeft( 10f );
				  
				  //Introducimos la cabecera del cuerpo del listado
				  celda = this.generaCabeceraCuerpoListado( tablaCuerpo );
				  
				  //Inicializamos de nuevo el contador
				  contador = 0;
	          }
	          
	          contador ++;
	      }
	      
	      
	      //Añdimos el cuerpo de la Factura al Documento
	      tablaExterior.addCell( tablaCuerpo );
	      
	      document.add(  tablaExterior );
	      
	      //Generamos el pie de la Factura
	      this.generaPiePagina();
	      
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public void onEndPage(PdfWriter writer, Document document) {
        try {
            Rectangle page = document.getPageSize();
            PdfPTable foot = new PdfPTable(1);
            foot.getDefaultCell().setBorderWidth(0);
            foot.addCell( "Página " + writer.getPageNumber() );
            foot.setHorizontalAlignment(Element.ALIGN_RIGHT);
            foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

	
	public PdfPCell generaCabeceraCuerpoListado( PdfPTable tablaCuerpo ){
		
		 PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
        
		 //UNIDADES DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("DNI", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      //celda.setCellEvent(rectangle);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Nombre", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      //celda.setCellEvent(rectangle);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //TOTAL (CANTIDAD x PRECIO) DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Nombre Comercial", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      //celda.setBorderWidth(0); celda.setCellEvent(rectangle);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //PRECIO DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Dirección", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      //celda.setCellEvent(rectangle);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
     
	      //IVA DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Teléfono", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      celda.setBorderWidthRight( 0.5f );
	      //celda.setCellEvent(rectangle);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCUENTO DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Móvil", fuenteParrafoNegrita));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBorder( 0 );
	      celda.setBorderWidthBottom( 0.5f );
	      //celda.setCellEvent(rectangle);
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
		}
		
		return color;
	}
}
