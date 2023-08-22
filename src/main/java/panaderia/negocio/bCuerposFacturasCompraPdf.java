package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.Proveedores;
import panaderia.negocio.bCuerposFacturasVentaPdf.RoundRectangle;
import panaderia.utilidades.Empresa;
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

public class bCuerposFacturasCompraPdf extends PdfPageEventHelper{
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			//fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.COURIER, BaseFont.NOT_EMBEDDED);
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
	
	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
	public Font fuenteParrafo9 = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 11, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 14, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 11, Font.BOLD);
    
    private int numProductos = 20;
    private float tamanyoTablaCuerpo = 650;
    
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
    
    
	/** Método que genera la cabecera de la Factura donde se encuentre el logo de la empresa y los datos de la misma */
	public PdfPTable generaCabeceraFactura(FacturasCompra factura, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		RoundRectangle rectangle = new RoundRectangle();
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		try{
			tablaCabecera.setWidthPercentage(95);
			tablaCabecera.setWidths(new int[]{50,50});
			
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
	      Empresa empresa = new Empresa( "1" );
	      
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
	      
	      if( !Utils.empty( empresa.getDireccion2() ) ){
		      celdaDatos = new PdfPCell(new Phrase( empresa.getDireccion2(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidth(0);
		      datosEmpresa.addCell(celdaDatos);
	      }
	      
	      if( !Utils.empty( empresa.getTelefonoOficina() ) ){
		      celdaDatos = new PdfPCell(new Phrase("Oficina: "+empresa.getTelefonoOficina(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidth(0);
		      datosEmpresa.addCell(celdaDatos);
	      }
	      
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
	
	
	/** Método que genera la cabecera de la Factura donde se encuentran los datos del Cliente junto con el Numero
	 *  de Factura y la Fecha de la misma */
	public PdfPTable generaCabeceraDatosProveedor(FacturasCompra factura, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		String nombre = "Nota C";
		//Si factura
		if( "A".equals( factura.getFcIva() ) ){
			numProductos = 20;
			nombre = "N° FRA";
			tamanyoTablaCuerpo = 450;
		}else{
			numProductos = 34;
			tamanyoTablaCuerpo = 650;
		}
		
		RoundRectangle rectangle = new RoundRectangle();
		//String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		
		try{
			
		  tablaCabecera.setWidthPercentage(100);
		  tablaCabecera.setWidths(new int[]{25,85});
			
		  //TABLA IZQUIERDA, CON EL NUM_FACTURA, FECHA, CODIGO CLIENTE
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{50,50});
	      
	      //NUMERO DE FACTURA
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));	      
	      celdaDatos = new PdfPCell(new Phrase(nombre, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(factura.getFcId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	         
	      //FECHA DE FACTURA
	      celdaDatos = new PdfPCell(new Phrase("FECHA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(factura.getFcFecha("dd/MM/yyyy"), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      //TABLA DERECHA, CON DATOS DEL CLIENTE DE LA FACTURA	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      //ENCABEZADO
	      celdaDatos = new PdfPCell(new Phrase("PRUEBA" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	    	  //NOMBRE COMERCIAL
		      celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo9));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingLeft( 15f );
		      //celdaDatos.setBackgroundColor(new Color(230,230,230));
		      tablaDerecha.addCell(celdaDatos);
		      
		      //DIRECCION
		      celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingLeft( 15f );
		      //celdaDatos.setBackgroundColor(new Color(230,230,230));
		      tablaDerecha.addCell(celdaDatos);
		      
		      //CODIGO POSTAL Y LOCALIDAD
		      celdaDatos = new PdfPCell(new Phrase( "", fuenteParrafo));
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
	
	
	/** Método que genera la cabecera de la Factura donde se encuentran los datos del Cliente junto con el Numero
	 *  de Factura y la Fecha de la misma */
	public PdfPTable generaCabeceraDatosCliente(ArrayList fechas, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		String fechaInicio = ((String[])fechas.get(0))[1];
		String fechaFin = ((String[])fechas.get(0))[0];
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		
		try{
			
		  tablaCabecera.setWidthPercentage(100);
		  tablaCabecera.setWidths(new int[]{30,65});
			
		  //TABLA IZQUIERDA, CON EL NUM_FACTURA, FECHA, CODIGO CLIENTE
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{65,35});
	      
	      //NUMERO DE FACTURA
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));	      
	      celdaDatos = new PdfPCell(new Phrase("FACTURA AGRUPADA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      //////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      //////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      //CODIGO DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase("CODIGO PROVEEDOR", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 7f );
	      //////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(proveedor.getPrId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      //FECHA DE FACTURA
	      celdaDatos = new PdfPCell(new Phrase("FECHA INICIO", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(Utils.date2String(Utils.string2Date(fechaInicio,Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("FECHA FIN", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(Utils.date2String(Utils.string2Date(fechaFin,Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorderWidth(0);
	      ////celdaDatos.setBackgroundColor(new Color(251,243,232));
	      tablaIzquierda.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      //TABLA DERECHA, CON DATOS DEL CLIENTE DE LA FACTURA	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      //ENCABEZADO
	      celdaDatos = new PdfPCell(new Phrase("Datos Proveedor" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //NOMBRE - APELLIDOS DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase(proveedor.getPrNombre() +" " + proveedor.getPrApellidos() +" ("+proveedor.getPrNombreComercial()+")", fuenteParrafo9));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //DNI DEL CLIENTE
	      celdaDatos = new PdfPCell(new Phrase(proveedor.getPrNif(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //DIRECCION
	      celdaDatos = new PdfPCell(new Phrase(proveedor.getPrDireccion(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingLeft( 15f );
	      //celdaDatos.setBackgroundColor(new Color(230,230,230));
	      tablaDerecha.addCell(celdaDatos);
	      
	      //CODIGO POSTAL Y LOCALIDAD
	      celdaDatos = new PdfPCell(new Phrase( proveedor.getPrCodigoPostal() + " " + proveedor.getPrDatosRelacionados()[3] + " ("+proveedor.getPrDatosRelacionados()[4]+")" , fuenteParrafo));
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
	
    
    /** Genera el cuerpo de la Factura, mostrando todos los detalles de los productos del Cliente*/
	public void generaCuerpoFactura(FacturasCompra factura, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request, Document document, ArrayList fechas) throws DocumentException, IOException{
		

		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 100 );
		tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuerpo );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaExterior.getDefaultCell().setBorder( 0 );
		
		//Introducimos una imagen en el centro
		//this.anadeImagen( request, "logo_solo_grande.jpg", document );
		
		
		int contador = 1;
		//float numero_piezas = 0;
		
		PdfPTable tablaCuerpo = new PdfPTable(5);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{12,50,15,15,15});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			
			//ESTO ES UNA FORMA MAS ELEGANTE DE HACER LA RUPTURA DE LA FACTURA
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
		      celda = new PdfPCell(new Phrase("", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      tablaCuerpo.addCell(celda);
		      
		      //DESCRIPCION DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      tablaCuerpo.addCell(celda);
		      
		      
		      //LOTE DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      tablaCuerpo.addCell(celda);
		      
		      
		      //PRECIO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      tablaCuerpo.addCell(celda);
		      float precioTotal = 0;
		      
		      //TOTAL (CANTIDAD x PRECIO) DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      tablaCuerpo.addCell(celda);
	      
		      //IVA DEL PRODUCTO
		      /*celda = new PdfPCell(new Phrase("IVA (%)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      celda.setBorderWidthBottom( 0.5f );
		      
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);
		      
		      //DESCUENTO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("Dto. (%)", fuenteParrafo));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 5 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth(0);
		      celda.setBorderWidthBottom( 0.5f );
		      //celda.setCellEvent(rectangle);
		      //celda.setBackgroundColor(new Color(251,243,232));
		      tablaCuerpo.addCell(celda);*/
		      
	      for(int i=0; i<listaDetalles.size(); i++){
	    	  FacturasCompraDetalle detalle = (FacturasCompraDetalle) listaDetalles.get(i);
	    	  //numero_piezas += Float.parseFloat( detalle.getFcdCantidad() );
	    	  
	    	  //CANTIDAD DEL PRODUCTO
	    	  celda = new PdfPCell(new Phrase("" + Utils.formateaCantidadFactura(detalle.getFcdCantidad()), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          //celda.setCellEvent(rectangle);
	          tablaCuerpo.addCell(celda);
	          
	          //DETALLE DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(detalle.getFcdProducto(), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setBorderWidth(0); 
	          tablaCuerpo.addCell(celda);
	          
	          //LOTE DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(detalle.getFcdLote(), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setBorderWidth(0); 
	          tablaCuerpo.addCell(celda);
	          
	          //PRECIO DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidadFactura(Float.toString(Float.parseFloat(detalle.getFcdPrecioCompra()))), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setBorderWidth(0); 
	          tablaCuerpo.addCell(celda);
	          precioTotal += Float.parseFloat( detalle.getFcdPrecioCompra() );
	          
	          
	          //IMPORTE TOTAL DEL PRODUCTO
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidadFactura(Float.toString(Float.parseFloat(detalle.getFcdImporte()))), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          
	          celda.setBorderWidth(0); 
	          
	          tablaCuerpo.addCell(celda);
	          
	         /* String iva = Utils.formateaCantidadFactura(Float.toString( Float.parseFloat(detalle.getFcdIva())*100 ));
	          //IVA DEL PRODUCTO
	          celda = new PdfPCell(new Phrase( iva , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          
	          celda.setBorderWidth(0); 
	          
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);
	          
	          String descuento = Utils.formateaCantidadFactura(Float.toString(Float.parseFloat( Utils.skipNullNumero(detalle.getFcdDescuento()))*100));
	          //DESCUENTO DEL PRODUCTO
	          celda = new PdfPCell( new Phrase( descuento, fuenteParrafo) );
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          
	          celda.setBorderWidth(0); 
	          //celda.setCellEvent(rectangle);
	          //celda.setBackgroundColor(new Color(230,230,230));
	          tablaCuerpo.addCell(celda);*/
	          
	          //Si el contador llega a 30 productos, debemos de pintar el pie en blanco y pasar a la
	          //siguiente página y volver a pintar la cabecera
	          numProductos = 20;
	          if( contador == numProductos && i != listaDetalles.size() - 1 ){
	        	  tablaExterior.addCell( tablaCuerpo );
	    	      //Añadimos el cuerpo de la Factura al Documento
	    	      document.add(  tablaExterior );
	    	      //this.generaPieFactura( factura, cliente, 0, request, document );
	    	      document.newPage();
	    	      
	    	      PdfPTable cabecera = this.generaCabeceraFactura( new FacturasCompra(), proveedor, listaDetalles, request );
	    	      PdfPTable datosCliente = null;
	    	      if(Utils.skipNull(factura.getFcId()).equals(""))
	    	      	  datosCliente = this.generaCabeceraDatosCliente( fechas, proveedor, listaDetalles, request );
	    	      else
	    	    	  datosCliente = this.generaCabeceraDatosProveedor( factura, proveedor, listaDetalles, request );
	    	      //Añadimos al documento la cabecera y datos del cliente de la nueva página
	    	      document.add( cabecera );
	    	      document.add( datosCliente );
	    	      
	    	      //Reiniciamos los datos de las tablas para que no añadamos los mismos datos de nuevo
	    	      tablaExterior = new PdfPTable(1);
	    		  tablaExterior.setWidthPercentage( 100 );
	    		  tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuerpo );
	    		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		  tablaExterior.getDefaultCell().setBorder( 0 );
	    		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
	    		  //tablaExterior.deleteBodyRows();
	    		  
	    		  tablaCuerpo = new PdfPTable(6);
			      tablaCuerpo.setWidthPercentage(100);
			      tablaCuerpo.setWidths(new int[]{12,50,15,15,15});
				  tablaCuerpo.getDefaultCell().setPaddingLeft( 10f );
				  
				  celda = new PdfPCell(new Phrase("", fuenteParrafo));
				  
				  //Inicializamos de nuevo el contador
				  contador = 0;
	          }
	          
	          contador ++;
	      }
	      
	      
	      //Añdimos el cuerpo de la Factura al Documento
	      tablaExterior.addCell( tablaCuerpo );
	      document.add(  tablaExterior );
	      
	      //Generamos el pie de la Factura
	      this.generaPieFactura( factura, proveedor, request, document );

		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
	
	/** Método que genera el pie de la factura con los totales de la misma */
	public void generaPieFactura( FacturasCompra factura, Proveedores proveedor, HttpServletRequest request, Document document ){
		PdfPTable tablaPie = new PdfPTable(1);
		RoundRectangle rectangle = new RoundRectangle();
		try{
			tablaPie.setWidthPercentage(90);
			//tablaPie.setSpacingBefore( 10f );
			//tablaPie.setWidths(new int[]{20});
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
	       String totalFactura = factura.getFcTotal();
	       celda = new PdfPCell( new Phrase( ( Utils.formateaCantidadFactura(totalFactura)  ), fuenteParrafoNegrita));
	       celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	       celda.setBorderWidth(0); 
	       //celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(230,230,230));
	       tablaPie.addCell(celda);
       
       document.add( tablaPie );
       
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
	

}
