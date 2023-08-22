package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.utilidades.Empresa;
import utils.Utils;

import com.itextpdf.text.pdf.ColumnText;
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
import com.lowagie.text.pdf.PdfWriter;

public class bCuerposFacturasVentaPdf extends PdfPageEventHelper{
	
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
	
	public Font fuenteParrafo = new Font(helvetica(), 10, Font.NORMAL);
	public Font fuenteParrafo9 = new Font(helvetica(), 9, Font.NORMAL);
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 11, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 18, Font.BOLD,Color.BLACK);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 11, Font.BOLD);
    
    private int numProductos = 20;
    private float tamanyoTablaCuerpo = 650;
    
    
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
    
    
	
	public PdfPTable generaCabeceraFactura(FacturasVenta factura, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
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
		  
	      
	      PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
	      im.scalePercent(100);
	      celda = new PdfPCell(im);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celda.setBorderWidth(0);
	      tablaCabecera.addCell(celda);
	      
	      PdfPTable datosEmpresa = new PdfPTable(1);
	      Empresa empresa = new Empresa( factura.getFvIdEmpresa() );
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase( empresa.getNombreComercial(), fuenteNotaGrande));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      celda.setBorder(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase( empresa.getDireccion1(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      celda.setBorder(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase( empresa.getCodigoPostal()+" "+empresa.getLocalidad()+" ("+empresa.getProvincia()+")", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      celda.setBorder(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fábrica: "+empresa.getTelefonoFabrica(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      if( !Utils.empty( empresa.getDireccion2() ) ){
		      celdaDatos = new PdfPCell(new Phrase( empresa.getDireccion2(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorder(0);
		      datosEmpresa.addCell(celdaDatos);
	      }
	      
	      if( !Utils.empty( empresa.getTelefonoOficina() ) ){
		      celdaDatos = new PdfPCell(new Phrase("Oficina: "+empresa.getTelefonoOficina(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorder(0);
		      datosEmpresa.addCell(celdaDatos);
	      }
	      
	      celdaDatos = new PdfPCell(new Phrase("CIF : "+empresa.getCif(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( datosEmpresa );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
	
	
	public PdfPTable generaCabeceraDatosCliente(FacturasVenta factura, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		String nombre = "";
		
		if( "A".equals( factura.getFvIva() ) ){
			numProductos = 20;
			nombre = "N° FRA";
			tamanyoTablaCuerpo = 550;
		}else{
			numProductos = 34;
			tamanyoTablaCuerpo = 650;
		}
		
		RoundRectangle rectangle = new RoundRectangle();
		//String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_LEFT);
		//tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		//tablaCabecera.getDefaultCell().setCellEvent( rectangle );
		
		try{
			
		  tablaCabecera.setWidthPercentage(95);
		  tablaCabecera.setWidths(new int[]{30,65});
		  tablaCabecera.setSpacingAfter(20f);
		  
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{50,50});
	      
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));	      
	      celdaDatos = new PdfPCell(new Phrase(nombre, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("C" + factura.getFvNumeroFactura(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(factura.getFvFecha("dd/MM/yyyy"), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("NOTA ENTREGA" , fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      if( "A".equals( factura.getFvIva() ) ){
		      
		      celdaDatos = new PdfPCell(new Phrase(cliente.getClNombre() +" " + cliente.getClApellidos() +" ("+cliente.getClNombreComercial()+")", fuenteParrafo9));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
		      
		      
		      celdaDatos = new PdfPCell(new Phrase(cliente.getClNif(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
		      
		      
		      celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
		      
		      
		      celdaDatos = new PdfPCell(new Phrase( cliente.getClCodigoPostal() + " " + cliente.getClDatosRelacionados()[3] + " ("+cliente.getClDatosRelacionados()[4]+")" , fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
	      }
	      else{
	    	  
		      /*celdaDatos = new PdfPCell(new Phrase(cliente.getClNombreComercial(), fuenteParrafo9));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
		      
		      
		      celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);
		      
		      
		      celdaDatos = new PdfPCell(new Phrase( cliente.getClCodigoPostal() + " " + cliente.getClDatosRelacionados()[3] + " ("+cliente.getClDatosRelacionados()[4]+")" , fuenteParrafo));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celdaDatos.setBorder(0);
		      celdaDatos.setPaddingLeft( 15f );
		      
		      tablaDerecha.addCell(celdaDatos);*/
	      }
	      
	      tablaCabecera.addCell( tablaDerecha );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
	
	
	public PdfPTable generaCabeceraDatosCliente(ArrayList fechas, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request) throws DocumentException, IOException{
		
		RoundRectangle rectangle = new RoundRectangle();
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		
		String fechaInicio = ((String[])fechas.get(0))[1];
		String fechaFin = ((String[])fechas.get(0))[0];
		
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
		tablaCabecera.setSpacingAfter(12f);
		tablaCabecera.getDefaultCell().setBorder( 0 );
		//tablaCabecera.getDefaultCell().setCellEvent( rectangle );
		
		try{
			
		  tablaCabecera.setWidthPercentage(100);
		  tablaCabecera.setWidths(new int[]{30,65});
			
		  
	      PdfPTable tablaIzquierda = new PdfPTable(2);
	      tablaIzquierda.setWidths(new int[]{65,35});
	      
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));	      
	      celdaDatos = new PdfPCell(new Phrase("FACTURA AGRUPADA", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingTop( 7f );
	      celdaDatos.setPaddingBottom( 6f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("CODIGO CLIENTE", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingBottom( 7f );
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClId(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("FECHA INICIO", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(Utils.date2String(Utils.string2Date(fechaInicio,Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("FECHA FIN", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(Utils.date2String(Utils.string2Date(fechaFin,Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      celdaDatos.setBorder(0);
	      
	      tablaIzquierda.addCell(celdaDatos);
	      
	      tablaCabecera.addCell( tablaIzquierda );
	      
	      
	      
	      PdfPTable tablaDerecha = new PdfPTable(1);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase("Datos Cliente" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNombre() +" " + cliente.getClApellidos() +" ("+cliente.getClNombreComercial()+")", fuenteParrafo9));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClNif(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase(cliente.getClDireccion(), fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	      
	      celdaDatos = new PdfPCell(new Phrase( cliente.getClCodigoPostal() + " " + cliente.getClDatosRelacionados()[3] + " ("+cliente.getClDatosRelacionados()[4]+")" , fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorder(0);
	      celdaDatos.setPaddingLeft( 15f );
	      
	      tablaDerecha.addCell(celdaDatos);
	      
	     
	      
	      tablaCabecera.addCell( tablaDerecha );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        return tablaCabecera;
	}
	
    
    
	public void generaCuerpoFactura(FacturasVenta factura, Clientes cliente, ArrayList listaDetalles, HttpServletRequest request, Document document, ArrayList fechas, boolean copia) throws DocumentException, IOException{
		

		RoundRectangle rectangle = new RoundRectangle();
		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 95 );
		tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuerpo );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaExterior.getDefaultCell().setBorder( 0 );
		//tablaExterior.getDefaultCell().setCellEvent( rectangle );
		
		int contador = 1;
		float numero_piezas = 0;
		
		PdfPTable tablaCuerpo = new PdfPTable(5);
		try{
			tablaCuerpo.setWidthPercentage(95);
			tablaCuerpo.setWidths(new int[]{10,50,15,15,15});
			//tablaCuerpo.setSpacingBefore(50);
			//tablaCuerpo.setSpacingAfter(50);
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	           
		      double precioTotal = 0;
		      double BaseImponible = 0;
		      
	      for(int i=0; i<listaDetalles.size(); i++){
	    	  FacturasVentaDetalle detalle = (FacturasVentaDetalle) listaDetalles.get(i);
	    	  float cantidad = Float.parseFloat( detalle.getFvdCantidad() );
	    	  
	    	  celda = new PdfPCell(new Phrase("" + Utils.formateaCantidadUnidades(detalle.getFvdCantidad()), fuenteParrafo));
	          celda.setColspan(1);
	          //celda.setBorderWidthRight( 0.5f );
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setFixedHeight(20);
	          //celda.setPaddingRight(5f);
	          celda.setBorderWidth(0); 
	          
	          tablaCuerpo.addCell(celda);
	          
	          
	          celda = new PdfPCell(new Phrase(detalle.getFvdProducto(), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	          celda.setFixedHeight(20);
	          celda.setBorderWidth(0); 
	          //celda.setBorderWidthRight( 0.5f );
	          celda.setPaddingLeft( 10f );
	          
	          tablaCuerpo.addCell(celda);
	          
	          
	          celda = new PdfPCell( new Phrase( detalle.getLote(), fuenteParrafo) );
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setFixedHeight(20);
	          celda.setBorderWidth(0); 
	          
	          tablaCuerpo.addCell(celda);
	          
	          
	          double precioUnitario = Double.parseDouble(detalle.getFvdPrecioVenta());
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidadFactura(Double.toString(precioUnitario)), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setFixedHeight(20);
	          celda.setBorderWidth(0); 
	          //celda.setBorderWidthRight( 0.5f );
	          
	          tablaCuerpo.addCell(celda);
	          
	          double base = Double.parseDouble(detalle.getFvdPrecioVenta()) * Double.parseDouble(detalle.getFvdCantidad());
	          celda = new PdfPCell(new Phrase(Utils.formateaCantidadFactura(Double.toString(base)), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setFixedHeight(20);
	          celda.setBorderWidth(0); 
	          //celda.setBorderWidthRight( 0.5f );
	          
	          tablaCuerpo.addCell(celda);
	          
	          precioTotal += Double.parseDouble( detalle.getFvdPrecioVenta() );
	          
	          BaseImponible += base;        
	          
	          
	          if( "A".equals(factura.getFvIva()) )
	        	  numProductos = 20;
	          else
	        	  numProductos = 25;
	          if( contador == numProductos && i != listaDetalles.size() - 1 ){
	        	  tablaExterior.addCell( tablaCuerpo );
	    	      
	    	      document.add(  tablaExterior );
	    	      
	    	      document.newPage();
	    	      
	    	      PdfPTable cabecera = this.generaCabeceraFactura( factura, listaDetalles, request );
	    	      PdfPTable datosCliente = null;
	    	      if(Utils.skipNull(factura.getFvId()).equals(""))
	    	      	  datosCliente = this.generaCabeceraDatosCliente( fechas, cliente, listaDetalles, request );
	    	      else
	    	    	  datosCliente = this.generaCabeceraDatosCliente( factura, cliente, listaDetalles, request );
	    	      
	    	      if( "A".equals(factura.getFvIva()) )
	    	    	  document.add( cabecera );
	    	      
	    	      document.add( datosCliente ); 
	    	      
	    	      
	    	      tablaExterior = new PdfPTable(1);
	    		  tablaExterior.setWidthPercentage( 95 );
	    		  tablaExterior.getDefaultCell().setFixedHeight( tamanyoTablaCuerpo );
	    		  tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		  tablaExterior.getDefaultCell().setBorder( 0 );
	    		  //tablaExterior.getDefaultCell().setCellEvent( rectangle );
	    		  
	    		  
	    		  tablaCuerpo = new PdfPTable(5);
			      tablaCuerpo.setWidthPercentage(95); 
			      //tablaCuerpo.setWidths(new int[]{16,42,15,18,9,9});
				  //tablaCuerpo.getDefaultCell().setPaddingLeft( 10f );
				  
				  tablaCuerpo.setWidthPercentage(95);
				  tablaCuerpo.setWidths(new int[]{10,50,15,15,15});
				  //tablaCuerpo.setSpacingBefore(50);
				  //tablaCuerpo.setSpacingAfter(50);
					
				  celda = new PdfPCell(new Phrase("", fuenteParrafo));
				  
				  
				  contador = 0;
	          }
	          
	          contador ++;
	      }
	      
	      //Anadimos el nombre de la persona de la factura
	      celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
          celda.setFixedHeight(20);
          celda.setBorderWidth(0); 
	      tablaCuerpo.addCell(celda);
	      
	      celda = new PdfPCell(new Phrase(factura.getFvObservaciones(), fuenteParrafo));
	      celda.setColspan(4);
	      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
          celda.setFixedHeight(20);
          celda.setBorderWidth(0); 
	      tablaCuerpo.addCell(celda);
	      
	      tablaExterior.addCell( tablaCuerpo );
	      document.add(  tablaExterior );
	      
	      
	      this.generaPieFactura( factura, cliente, request, document, copia, BaseImponible );

		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
	
	
	public void generaPieFactura( FacturasVenta factura, Clientes cliente, HttpServletRequest request, Document document, boolean copia, double BaseImponible ){
		
		PdfPTable tablaPie = new PdfPTable(5);
		RoundRectangle rectangle = new RoundRectangle();
		String totalFactura = factura.getFvTotal();
		String descuento = factura.getFvDescuento();
		
		try{
			tablaPie.setWidthPercentage(90);
			//tablaPie.setSpacingBefore( 10f );
			tablaPie.setWidths(new int[]{40,15,15,15,20});
			
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
	      
			if(copia)
				celda = new PdfPCell(new Phrase( "COPIA", fuenteNotaGrande) );
			else
				celda = new PdfPCell(new Phrase( "", fuenteNotaGrande) );
		   celda.setColspan(1);
	       celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	       celda.setBorderWidth(0); 
	       //celda.setCellEvent(rectangle);
	       celda.setPaddingBottom( 4f );
	       //celda.setBackgroundColor(new Color(251,243,232));
	       tablaPie.addCell(celda);
		       
	       
	       double dto = Float.parseFloat( descuento );
	       if( dto != 0 ){
	    	   
	    	   celda = new PdfPCell(new Phrase( ( Utils.formateaCantidadFactura(String.valueOf(BaseImponible) ) ) , fuenteParrafo));
		       celda.setBorderWidth(0);
	       	   tablaPie.addCell(celda);
	       	   
	    	   double importeDeDescuento = ( (BaseImponible*dto) / 100 );
	    	   celda = new PdfPCell(new Phrase( ( Utils.formateaCantidad(descuento) + "%" ) , fuenteParrafo));
		       celda.setBorderWidth(0);
	       	   tablaPie.addCell(celda);
	       	   
	       	   celda = new PdfPCell(new Phrase( Utils.formateaCantidadFactura(String.valueOf(importeDeDescuento)) , fuenteParrafo));
		       celda.setBorderWidth(0);
	       	   tablaPie.addCell(celda);
	       }else{
	    	   celda = new PdfPCell(new Phrase("", fuenteParrafo));
	    	   celda.setBorderWidth(0);
	    	   tablaPie.addCell(celda);
	       	   tablaPie.addCell(celda);
	       }
	     	       
	       
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
