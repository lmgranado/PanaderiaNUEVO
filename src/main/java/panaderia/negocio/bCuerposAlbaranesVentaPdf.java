package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.utilidades.Empresa;
import utils.Utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
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
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTable;

public class bCuerposAlbaranesVentaPdf extends PdfPageEventHelper{
	
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
	
	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
	public Font fuenteParrafo9 = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 16, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 11, Font.BOLD);
    
    private int numProductos = 25;
    private float tamanyoTablaCuepro = 550;
    
    /** Añade una imagen al centro del documento con la trasparencia que le indiquemos */
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
    
    
	/** Método que genera la cabecera del Albaran donde se encuentre el logo de la empresa y los datos de la misma */
	public PdfPTable generaCabeceraAlbaran(AlbaranesVenta albaran, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		RoundRectangle rectangle = new RoundRectangle();
		
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
	      Empresa empresa = new Empresa( cliente.getClPertenencia() );
	      
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
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
	
	/** Método que genera la cabecera del Albarán donde se encuentran los datos del Cliente junto con el Numero
	 *  de Albaran y la Fecha del mismo */
	public PdfPTable generaCabeceraDatosCliente(AlbaranesVenta albaran, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		tablaCabecera.getDefaultCell().setCellEvent( rectangle );
		
		try{
			
		  tablaCabecera.setWidthPercentage(100);
		  tablaCabecera.setWidths(new int[]{30,65});
			
		  //TABLA IZQUIERDA, CON EL NUM_ALBARAN, FECHA, CODIGO CLIENTE
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{60,40});
	      
	      //NUMERO DE ALBARAN
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));	      
	      celdaDatos = new PdfPCell(new Phrase("NÚMERO ALBARAN", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(albaran.getAvId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      //CODIGO DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase("CODIGO CLIENTE", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 7f );
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      //FECHA DE ALBARAN
	      celdaDatos = new PdfPCell(new Phrase("FECHA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(albaran.getAvFecha("dd/MM/yyyy"), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      //celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      //TABLA DERECHA, CON DATOS DEL CLIENTE DEL ALBARAN	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      //ENCABEZADO
	      celdaDatos = new PdfPCell(new Phrase("Datos Cliente" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //NOMBRE - APELLIDOS DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNombre() +" " + cliente.getClApellidos() +" ("+cliente.getClNombreComercial()+")", fuenteParrafo9));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //DNI DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNif(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //DIRECCION
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //CODIGO POSTAL Y LOCALIDAD
	      celdaDatos = new PdfPCell(new Phrase( cliente.getClCodigoPostal() + " " + cliente.getClDatosRelacionados()[3] + " ("+cliente.getClDatosRelacionados()[4]+")" , fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	     
	      
	      tablaCabecera.addCell( tablaDerecha );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
	
    
    /** Genera el cuerpo del Albaran, mostrando todos los detalles de los productos del Cliente*/
	public void generaCuerpoAlbaran(AlbaranesVenta albaran, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 100 );
		tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaExterior.getDefaultCell().setBorder( 0 );
		tablaExterior.getDefaultCell().setCellEvent( rectangle );
		
		//Introducimos una imagen en el centro
		//this.anadeImagen( request, "logo_solo_grande.jpg", document );
		
		
		int contador = 1;
		int numero_piezas = 0;
		
		PdfPTable tablaCuerpo = new PdfPTable(10);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{26,6,6,6,6,6,8,6,4,6});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			//ESTO ES UNA FORMA MAS ELEGANTE DE HACER LA RUPTURA DE LA ALBRAN
			/*PdfPCell h1 = new PdfPCell(new Paragraph("Header 1", font));
			PdfPCell h2 = new PdfPCell(new Paragraph("Header 2", font));
			table.setHeaderRows(1);
			table.addCell(h1);
			table.addCell(h2);
			PdfPCell cell;
			for (int row = 1; row <= 2000; row++) {
				if (row % fragmentsize == fragmentsize - 1) {
					document.add(table);
					table.deleteBodyRows();
					table.setSkipFirstHeader(true);
				}
			*/
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	           
			 //UNIDADES DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("DESCRIPCIÓN", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      
		      //VIAJES
		      celda = new PdfPCell(new Phrase("V1", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,150,150));
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase("V2", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,150,150));
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase("V3", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,150,150));
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase("V4", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,150,150));
		      tablaCuerpo.addCell(celda);
		      
		      celda = new PdfPCell(new Phrase("V5", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setPaddingTop( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,150,150));
		      tablaCuerpo.addCell(celda);
		      
		      //PRECIO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("PRECIO (€)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );		      
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      float precioTotal = 0;
		      
		      //TOTAL (CANTIDAD x PRECIO) DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("TOTAL (€)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setBorderWidth(0); celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      
		      //IVA DEL PRODUCTO
		      /*
		      celda = new PdfPCell(new Phrase("IVA (%)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      */
		      
		      //DESCUENTO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("Dto. (%)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      
		    //Lote
		      celda = new PdfPCell(new Phrase("Lote", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      
	      for(int i=0; i<listaDetalles.size(); i++){
	    	  String[] detalles = (String[])listaDetalles.get(i);
	    	  numero_piezas += Integer.parseInt( detalles[6] );
	    	  FacturasCompraDetalle fcd = FacturasCompraDetalle.getFacturasCompraDetalleByFcdStId(detalles[11]);
	  		  String lote = fcd.getFcdLote();
	    	  
	    	  //DETALLE DEL PRODUCTO
	    	  celda = new PdfPCell(new Phrase("" + detalles[0], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setBorderWidthRight( 0.5f );
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setPadding( 4f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //VIAJES++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	          celda = new PdfPCell(new Phrase(detalles[1], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(detalles[2], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(detalles[3], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(detalles[4], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase(detalles[5], fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	          
	          //PRECIO DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalles[7]))), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          precioTotal += Float.parseFloat( detalles[7] );
	          
	          //IMPORTE TOTAL DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalles[8]))), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);celda.setFixedHeight(30);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          
	          /*
	          String iva = Utils.formateaCantidad(Float.toString( Float.parseFloat(detalles[10])*100 ));
	          //IVA DEL PRODUCTO
	          celda = new PdfPCell(new Phrase( iva , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          */
	          
	          String descuento = Utils.formateaCantidad(Float.toString(Float.parseFloat( Utils.skipNullNumero(detalles[9]))*100));
	          //DESCUENTO DEL PRODUCTO
	          celda = new PdfPCell( new Phrase( descuento, fuenteParrafo) );celda.setFixedHeight(30);
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          celda.setBorderWidthRight( 0.5f );
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          
	          //LOTE
	          celda = new PdfPCell( new Phrase( lote, fuenteParrafo) );celda.setFixedHeight(30);
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          
	          //Si el contador llega a 30 productos, debemos de pintar el pie en blanco y pasar a la
	          //siguiente página y volver a pintar la cabecera
	          numProductos = 17;
	          if( contador == numProductos && i != listaDetalles.size() - 1 ){
	        	  tablaExterior.addCell( tablaCuerpo );
	    	      //Añadimos el cuerpo del Albaran al Documento
	    	      document.add(  tablaExterior );
	    	      //this.generaPieAlbaan( albaran, cliente, 0, request, document );
	    	      document.newPage();
	    	      
	    	      PdfPTable cabecera = this.generaCabeceraAlbaran( albaran, cliente, listaDetalles, request );
	    	      PdfPTable datosCliente = this.generaCabeceraDatosCliente( albaran, cliente, listaDetalles, request );
	    	      //Añadimos al documento la cabecera y datos del cliente de la nueva página
	    	      document.add( cabecera );
	    	      document.add( datosCliente );
	    	      
	    	      //Reiniciamos los datos de las tablas para que no añadamos los mismos datos de nuevo
	    	      tablaExterior = new PdfPTable(1);
	    		  tablaExterior.setWidthPercentage( 100 );
	    		  tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuepro );
	    		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		  tablaExterior.getDefaultCell().setBorder( 0 );
	    		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
	    		  //tablaExterior.deleteBodyRows();
	    		  
	    		  tablaCuerpo = new PdfPTable(9);
			      tablaCuerpo.setWidthPercentage(100);
			      tablaCuerpo.setWidths(new int[]{32,6,6,6,6,6,8,6,4});
				  tablaCuerpo.getDefaultCell().setPaddingLeft( 10f );
				  
				  celda = new PdfPCell(new Phrase("", fuenteParrafo));
		           
					 //UNIDADES DEL PRODUCTO
				      celda = new PdfPCell(new Phrase("DESCRIPCIÓN", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,243,232));
				      tablaCuerpo.addCell(celda);
				      
				      //VIAJES
				      celda = new PdfPCell(new Phrase("V1", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,150,150));
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("V2", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,150,150));
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("V3", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,150,150));
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("V4", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,150,150));
				      tablaCuerpo.addCell(celda);
				      
				      celda = new PdfPCell(new Phrase("V5", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setPaddingTop( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,150,150));
				      tablaCuerpo.addCell(celda);
				      
				      //PRECIO DEL PRODUCTO
				      celda = new PdfPCell(new Phrase("PRECIO (€)", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );		      
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,243,232));
				      tablaCuerpo.addCell(celda);
				      
				      //TOTAL (CANTIDAD x PRECIO) DEL PRODUCTO
				      celda = new PdfPCell(new Phrase("TOTAL (€)", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setBorderWidth(0); celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,243,232));
				      tablaCuerpo.addCell(celda);
				      
				      //IVA DEL PRODUCTO
				      /*
				      celda = new PdfPCell(new Phrase("IVA (%)", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      celda.setBorderWidthRight( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,243,232));
				      tablaCuerpo.addCell(celda);
				      */
				      
				      //DESCUENTO DEL PRODUCTO
				      celda = new PdfPCell(new Phrase("Dto. (%)", fuenteParrafo));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 5 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0.5f );
				      //celda.setCellEvent(rectangle);
				      //celda.setBackgroundColor(new Color(251,243,232));
				      tablaCuerpo.addCell(celda);
				  //Inicializamos de nuevo el contador
				  contador = 0;
	          }
	          
	          contador ++;
	      }
	      
	      
	      //Añdimos el cuerpo del Albaran al Documento
	      tablaExterior.addCell( tablaCuerpo );
	      document.add(  tablaExterior );
	      
	      //Generamos el pie de la Factura
	      this.generaPieAlbaran( albaran, cliente, numero_piezas, request, document );
		}catch (Exception e) {
			
		}
	}
		
	
	/** Método que genera el pie del Albaran con los totales del mismo */
	public void generaPieAlbaran( AlbaranesVenta albaran, Clientes cliente, int numero_piezas, HttpServletRequest request, Document document ){
		PdfPTable tablaPie = new PdfPTable(6);
		RoundRectangle rectangle = new RoundRectangle();
		try{
			tablaPie.setWidthPercentage(100);
			tablaPie.setSpacingBefore( 10f );
			tablaPie.setWidths(new int[]{22,16,4,20,16,17});
			//Parte 1
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
			celda = new PdfPCell(new Phrase( "PIEZAS TOTALES", fuenteParrafo) );
		       celda.setColspan(1);
		       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		       celda.setBorderWidth(0); 
		       celda.setCellEvent(rectangle);
		       celda.setPaddingBottom( 4f );
		       //celda.setBackgroundColor(new Color(251,243,232));
		       tablaPie.addCell(celda);
		       
		       //float totalFactura = importe_con_descuento + totalIva;
		       
		       celda = new PdfPCell( new Phrase( "" + numero_piezas, fuenteParrafo));
		       celda.setColspan(1);
		       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
		       celda.setBorderWidth(0); 
		       celda.setCellEvent(rectangle);
		       celda.setPaddingBottom( 4f );
		       //celda.setBackgroundColor(new Color(230,230,230));
		       tablaPie.addCell(celda);
		      
			celda = new PdfPCell(new Phrase("", fuenteParrafo));
			celda.setColspan(2);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setBorderWidth(0);
			tablaPie.addCell(celda);
		    
	       //BASE IMPONIBLE
	      /* celda = new PdfPCell(new Phrase( "Base Imponible", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setPaddingBottom( 4f );
	       celda.setCellEvent(rectangle);
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       //(%) DESCUENTO
	       celda = new PdfPCell(new Phrase( "(%) Descuento", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setPaddingBottom( 4f );
	       celda.setCellEvent(rectangle);
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       //(€) DESCUENTO
	       celda = new PdfPCell(new Phrase( "Total Descuento (€)", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setPaddingBottom( 4f );
	       celda.setCellEvent(rectangle);
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase("", fuenteParrafo));
	       celda.setColspan(3);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0);
	       celda.setPaddingBottom( 4f );
	       tablaPie.addCell(celda);
		    
	       
	       celda = new PdfPCell(new Phrase( Float.toString(importeTotal), fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       float porcentaje_descuento = Float.parseFloat( cliente.getClDescuento() );
	       float totalDescuento = 0;
	       if(  porcentaje_descuento > 0 ){
	       	totalDescuento =  - (importeTotal*porcentaje_descuento)/100 ;
	       }
	       
	       celda = new PdfPCell(new Phrase( Float.toString( porcentaje_descuento ) , fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell( new Phrase( Float.toString( totalDescuento ) , fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0); 
	       celda.setPaddingBottom( 4f );
	       celda.setCellEvent(rectangle);
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase("", fuenteParrafo));
	       celda.setColspan(3);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0);
	       celda.setPaddingBottom( 4f );
	       tablaPie.addCell(celda);
		      
	       celda = new PdfPCell(new Phrase( "Base Imponible", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase( "(%) I.V.A.", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase( "Total I.V.A", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase("", fuenteParrafo));
	       celda.setColspan(3);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0);
	       tablaPie.addCell(celda);
		      
	       float importe_con_descuento = importeTotal + totalDescuento;
	       celda = new PdfPCell(new Phrase( Float.toString( importe_con_descuento ), fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       float porcentajeIva = 7;
	       celda = new PdfPCell(new Phrase( Float.toString( porcentajeIva ), fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       float totalIva = (importe_con_descuento * porcentajeIva)/100;
	       celda = new PdfPCell(new Phrase( Float.toString( totalIva ), fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase("", fuenteParrafo));
	       celda.setColspan(6);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0);
	       celda.setFixedHeight( 12f );
	       tablaPie.addCell(celda);
	       
	       celda = new PdfPCell(new Phrase("", fuenteParrafo));
	       celda.setColspan(4);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0);
	       tablaPie.addCell(celda);
	       */
	       celda = new PdfPCell(new Phrase( "TOTAL", fuenteParrafo) );
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
	       
	       //float totalAlbaran = importe_con_descuento + totalIva;
	       celda = new PdfPCell(new Phrase(Utils.formateaCantidad(albaran.getAvTotal()), fuenteParrafo));
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0); 
	       celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
       
       document.add( tablaPie );
       
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
	

}
