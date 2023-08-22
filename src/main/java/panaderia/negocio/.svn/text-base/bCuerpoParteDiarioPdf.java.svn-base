package panaderia.negocio;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import panaderia.beans.Clientes;
import panaderia.beans.Cobradores;
import panaderia.beans.FacturasVenta;
import panaderia.beans.HistoricoAc;
import panaderia.beans.NotasEntrega;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.informes.GenerarListadoProductosPDF;
import panaderia.negocio.bCuerposPdf.RoundRectangle;
import utils.Utils;
public class bCuerpoParteDiarioPdf extends PdfPageEventHelper{

	public Font fuenteParrafo = new Font(helvetica(), 9, Font.NORMAL);
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
    
    private final int rupturaPorCobrador = 1;
    private final int rupturaPorCliente = 2;
    
    public bCuerpoParteDiarioPdf(){
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
    
    
    
    
    public byte[] generaInformeCobros( HttpServletRequest request, HttpServletResponse response, ArrayList lista ) throws DocumentException, IOException{
    		
    		//Inicializamos los atributos del Informe
    		inicializa();
    		
    		//Abrimos el documento
    		abrirDocumento();
    		
			//Generamos la cabecera
    		generaCabeceraInforme( request );
    		
    		//Nombre del Listado
    		generaNombreListado();
    		
    		
    		if( !lista.isEmpty() )
    			//Generamos el cuerpo
    			generaCuerpoInforme( lista, request );
    		else
    			alertaNoDatos( );
    		
	        //Cerramos el documento
	        cerrarDocumento( response );
	        
	        return buffer.toByteArray();
		
	}
    
    
    public void inicializa() throws DocumentException, FileNotFoundException{
    	document = new Document();    
	    document.setPageSize(PageSize.A4);
    	buffer = new ByteArrayOutputStream();
    	writer = PdfWriter.getInstance(document, buffer);
        writer.setPageEvent(new bCuerpoParteDiarioPdf());
	}
    
	public void abrirDocumento(){
		document.addCreationDate();
	    document.addCreator("Informe de Cobros");
	    document.addProducer();
	    document.open();
	}
	
	public void alertaNoDatos( ) throws DocumentException{
		
		PdfPTable alertaNoDatos = new PdfPTable(1);
		
		try {
			alertaNoDatos.setWidths(new int[]{100});
		
			alertaNoDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
			alertaNoDatos.setSpacingAfter(20f);
			alertaNoDatos.setSpacingBefore(10f);
				
			PdfPCell celdaAlerta = new PdfPCell(new Phrase("NO EXISTEN DATOS PARA LOS PARÁMETROS DE BÚSQUEDA INTRODUCIDOS", fuenteNoDatos));
			celdaAlerta.setHorizontalAlignment(Element.ALIGN_CENTER);
			celdaAlerta.setBorderWidth( 0 );
			alertaNoDatos.addCell( celdaAlerta );
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		document.add( alertaNoDatos );
	}
	
	
	public PdfPTable generaCabeceraInforme( HttpServletRequest request ){
		String ruta = ResourceBundle.getBundle( "informes" ).getString( "ruta_aplicacion" );
		String fechaDesdeHasta = request.getParameter("fvFechaDesde") + " - "+request.getParameter("fvFechaHasta");
		
		String param_cobrador = "Todos";
		if( !Utils.empty(request.getParameter("clCobId")) ){
			Cobradores cobrador = Cobradores.getCobradoresByCobId( request.getParameter("clCobId") );
			param_cobrador = cobrador.getCobNombre()+ " "+ cobrador.getCobApellidos(); 
		}
			
		String param_cliente = "Todos";
		if( !Utils.empty(request.getParameter("clId")) ){
			Clientes cliente = Clientes.getClientesByClId( request.getParameter("clId") );
			param_cliente = cliente.getClNombreComercial();
		}
		
		String param_tipoFactura = "A / B";
		if( !Utils.empty(request.getParameter("fvIva")) ){
			param_tipoFactura = request.getParameter("fvIva");
		}
		
		String param_estado = "Pagadas / No Pagadas";
		if( !Utils.empty(request.getParameter("fvPagada")) ){
			if( "1".equals( request.getParameter("fvPagada") ))
				param_estado = "Pagadas";
			else
				param_estado = "No Pagadas";
		}
		
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
	      
	      RoundRectangle rectangle = new RoundRectangle();
	      
		  PdfPTable tablaExterior = new PdfPTable(1);
		  tablaExterior.setWidthPercentage( 50 );
		  tablaExterior.setHorizontalAlignment(Element.ALIGN_CENTER);
		  tablaExterior.getDefaultCell().setBorder( 0 );
		  tablaExterior.getDefaultCell().setCellEvent( rectangle );
			
	      PdfPTable datosEmpresa = new PdfPTable(2);
	      
	      PdfPCell celdaDatos = new PdfPCell(new Phrase("Filtro Realizado", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setColspan(2);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBottom( 5f );
	      celdaDatos.setBorderWidthBottom( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Fecha Desde - Hasta ", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(fechaDesdeHasta, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Cobrador/es", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(param_cobrador, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Cliente/s", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(param_cliente, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Tipo de factura", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(param_tipoFactura, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase("Estado", fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setBorderWidthRight( 0.5f );
	      celdaDatos.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      celdaDatos = new PdfPCell(new Phrase(param_estado, fuenteParrafo));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidth(0); 
	      celdaDatos.setPadding( 2f );
	      datosEmpresa.addCell(celdaDatos);
	      
	      tablaExterior.addCell( datosEmpresa );
	      
	      tablaCabecera.addCell( tablaExterior );
	      
	      document.add( tablaCabecera );
	
	      
		}catch (Exception e) {
			System.out.println(" Error en generaCabeceraInforme()"+ e.toString() );
		}
        return tablaCabecera;
	}
	
	public PdfPCell generaCabeceraCuerpoInforme( PdfPTable tablaCuerpo ){
		
		 PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo));
		 
		  //REFERENCIA DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("Nº FACT", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("FECHA FACT", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
    
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("FECHA PAGO", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("TIPO", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("TOTAL FACTURA", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      //DESCRIPCION DEL PRODUCTO
	      celda = new PdfPCell(new Phrase("IMPORTE PENDIENTE", fuenteParrafo));
	      celda.setColspan(1);
	      celda.setPaddingBottom( 5 );
	      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
	      tablaCuerpo.addCell(celda);
	      
	      return celda;
	      
	}
	
	
	public void generaNombreListado() throws DocumentException{
		PdfPTable tablaNombreListado = new PdfPTable(1);
		
		try {
			tablaNombreListado.setWidths(new int[]{95});
		
			tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
			tablaNombreListado.setSpacingAfter(5f);
			tablaNombreListado.setSpacingBefore(10f);
				
			PdfPCell celdaNombreListado = new PdfPCell(new Phrase("Informe de Cobros", fuenteNotaGrande));
			celdaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
			celdaNombreListado.setBorderWidth( 0 );
			tablaNombreListado.addCell( celdaNombreListado );
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		document.add( tablaNombreListado );
		
	}
	
	public void pintaEncabezado( String nombre, float margen, int colorFondo, int alineacion ) throws DocumentException{
		
		PdfPTable tablaEncabezado = new PdfPTable(1);
		RoundRectangle rectangle = new RoundRectangle();
		
		tablaEncabezado.setWidthPercentage(40);		
		tablaEncabezado.setHorizontalAlignment(alineacion);
		tablaEncabezado.setSpacingAfter(5f);
		tablaEncabezado.setSpacingBefore(5f);
		
		PdfPCell celda = new PdfPCell(new Phrase( nombre, fuenteParrafoNegrita));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBorderWidth(0); 
		celda.setLeft( margen );
		celda.setBackgroundColor( dameColorFondo( colorFondo ) );
		celda.setCellEvent(rectangle);
		
		tablaEncabezado.addCell( celda );
		
		document.add( tablaEncabezado );
	}
	
	public boolean hayRuptura( String [] actual, String [] anterior, int tipoRuptura ){
		boolean resultado = false;
		
		switch (tipoRuptura) {
			case rupturaPorCobrador:
				if( Integer.parseInt(actual[8]) != Integer.parseInt(anterior[8]) )
					resultado = true;
			break;
			
			case rupturaPorCliente:
				if( Integer.parseInt(actual[6]) != Integer.parseInt(anterior[6]) )
					resultado = true;
				break;
		}		
		
		return resultado;
	}
	
	/** Genera el cuerpo del Listado */
	public void generaCuerpoInforme( ArrayList lista, HttpServletRequest request ) throws DocumentException, IOException{
		
		PdfPTable tablaNombreListado = new PdfPTable(1);
		tablaNombreListado.setWidths(new int[]{95});
		tablaNombreListado.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaNombreListado.setSpacingAfter(10f);
		tablaNombreListado.setSpacingBefore(10f);

		PdfPTable tablaExterior = new PdfPTable(1);
		tablaExterior.setWidthPercentage( 80 );
		tablaExterior.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaExterior.getDefaultCell().setBorder( 0 );
	
		PdfPTable tablaCuerpo = new PdfPTable(6);
		int[] widths = new int[]{6,8,8,6,8,8};
		try{
			tablaCuerpo.setWidthPercentage(80);
			tablaCuerpo.setWidths(widths);
			tablaCuerpo.setKeepTogether(true);
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			tablaCuerpo.setSpacingBefore( 20f );
			
			
		//Introducimos la cabecera del cuerpo del listado
		PdfPCell celda = this.generaCabeceraCuerpoInforme( tablaCuerpo );
		
		String [] anterior = null;
		float totalFacturas = 0;
		float totalImportePendiente = 0;
		
		for(int i=0; i<lista.size(); i++){
	    	  String [] actual = ( String[] ) lista.get(i);
	    	  if( i==0 ){
	    		  pintaEncabezado( actual[9], 0, fondoCabeceraCuerpo, Element.ALIGN_LEFT );
    			  pintaEncabezado( actual[7], 20, fondoCeldaFiltro, Element.ALIGN_CENTER );
	    	  }
	    	  else{
	    		  if( hayRuptura( actual, anterior, rupturaPorCobrador ) || hayRuptura( actual, anterior, rupturaPorCliente ) ){
	    			  
	    			  pintaTotales( tablaCuerpo, totalFacturas, totalImportePendiente );
	    			  document.add(  tablaCuerpo );
	    			  pintaHistorico( anterior[6], request.getParameter("fvFechaDesde"), request.getParameter("fvFechaHasta") );
	    			  
	    			  if( hayRuptura( actual, anterior, rupturaPorCobrador ) ){
	    				  document.newPage();	    			  
	    				  generaCabeceraInforme( request );
	    				  pintaEncabezado( actual[9], 0, fondoCabeceraCuerpo, Element.ALIGN_LEFT );
	    			  }
	    			  
	    			  
	    			  pintaEncabezado( actual[7], 20, fondoCeldaFiltro, Element.ALIGN_CENTER );
	    			  
	    			  //Limpiamos la tabla Cuerpo
	    			  tablaCuerpo.deleteBodyRows();
	    			  //Inicializamos totales
	    			  totalFacturas = 0;
	    			  totalImportePendiente = 0;
	    			  
	    			  generaCabeceraCuerpoInforme( tablaCuerpo );
	    		  }
	    	  }
	    		 /* }else if( hayRuptura( actual, anterior, rupturaPorCliente ) ){
	    			  
	    			  pintaTotales( tablaCuerpo, totalFacturas, totalImportePendiente );
	    			  document.add(  tablaCuerpo );
	    			  pintaHistorico( anterior[6], request.getParameter("fvFechaDesde"), request.getParameter("fvFechaHasta") );
	    			  
	    			  //Pintamos el nombre del cliente actual
	    			  pintaEncabezado( actual[7], 20, fondoCeldaFiltro );
	    			  
	    			  //Inicializamos totales
	    			  totalFacturas = 0;
	    			  totalImportePendiente = 0;
	    			  //Limpiamos la tabla Cuerpo
	    			  tablaCuerpo.deleteBodyRows();
	    			 
	    			  generaCabeceraCuerpoInforme( tablaCuerpo );
	    		  }
	    	  }*/
	    	  
	    	  
	    	  celda = new PdfPCell(new Phrase( actual[0] , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase( Utils.date2String(Utils.string2Date(actual[1], Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT ), fuenteParrafo ));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase( Utils.date2String(Utils.string2Date(actual[2], Utils.DATE_MYSQL_SHORT),Utils.DATE_SHORT ), fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	          celda = new PdfPCell(new Phrase( actual[3] , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_CENTER);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	          totalFacturas += Float.parseFloat(actual[4]);
	          celda = new PdfPCell(new Phrase( Utils.formateaCantidad(actual[4]) , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	          totalImportePendiente += Float.parseFloat(actual[5]);
	          celda = new PdfPCell(new Phrase( Utils.formateaCantidad(actual[5]) , fuenteParrafo));
	          celda.setColspan(1);
	          celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
	          celda.setPadding( 4f );
	          tablaCuerpo.addCell(celda);
	          
	    	  anterior = actual;
	          
	      }
	      
	      
		  pintaTotales( tablaCuerpo, totalFacturas, totalImportePendiente );
		  document.add(  tablaCuerpo );
		  
		  pintaHistorico( anterior[6], request.getParameter("fvFechaDesde"), request.getParameter("fvFechaHasta") );
		  
	      
		}catch (Exception e) {
			System.out.println( "generaCuerpoListado()"+e );
		}
	}
	
	
	public void pintaTotales( PdfPTable tablaCuerpo, float totalFacturas, float totalImportePendiente ){
		
		PdfPCell celda = new PdfPCell(new Phrase( "Cantidades totales" , fuenteParrafo));
        celda.setColspan(4);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding( 4f );
        celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
        tablaCuerpo.addCell(celda);
        
        celda = new PdfPCell(new Phrase( Utils.formateaCantidad(String.valueOf(totalFacturas)) , fuenteParrafo));
        celda.setColspan(1);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setPadding( 4f );
        tablaCuerpo.addCell(celda);
        
        celda = new PdfPCell(new Phrase( Utils.formateaCantidad(String.valueOf(totalImportePendiente)) , fuenteParrafo));
        celda.setColspan(1);
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setPadding( 4f );
        tablaCuerpo.addCell(celda);
	}
	
	public void pintaHistorico( String clId, String fechaDesde, String fechaHasta ) throws ParseException{
		
	   Date fechaDesdeDate = Utils.string2Date(fechaDesde, Utils.DATE_SHORT);
	   Date fechaHastaDate = Utils.string2Date(fechaHasta, Utils.DATE_SHORT);
	      
	   ArrayList lista = HistoricoAc.getHistoricoAcByCliente( Utils.date2String(fechaDesdeDate, Utils.DATE_MYSQL_SHORT), 
				  Utils.date2String(fechaHastaDate, Utils.DATE_MYSQL_SHORT), 
				  clId );
		if( !lista.isEmpty() ){
			PdfPTable tablaHistorico = new PdfPTable(2);
			int[] widths = new int[]{10,10};
			try {
				tablaHistorico.setWidthPercentage(50);
				tablaHistorico.setWidths(widths);
				tablaHistorico.setKeepTogether(true);
				tablaHistorico.setSpacingBefore( 10f );
				tablaHistorico.setSpacingAfter( 10f );
		        
				//REFERENCIA DEL PRODUCTO
				PdfPCell celda = new PdfPCell(new Phrase("FECHA ENTREGA", fuenteParrafo));
				celda.setColspan(1);
				celda.setPadding( 4f );
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
				tablaHistorico.addCell(celda);
			      
				//DESCRIPCION DEL PRODUCTO
				celda = new PdfPCell(new Phrase("CANTIDAD (€)", fuenteParrafo));
				celda.setColspan(1);
				celda.setPadding( 4f );
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setBackgroundColor( dameColorFondo( fondoCabeceraCuerpo ) );
				tablaHistorico.addCell(celda);
			      
				float totalACuenta = 0;
				for( int i=0; i<lista.size();i++){
					HistoricoAc historico = (HistoricoAc) lista.get(i);
					//DESCRIPCION DEL PRODUCTO
					celda = new PdfPCell(new Phrase( historico.getHacFechaEntrega(Utils.DATE_SHORT) , fuenteParrafo));
					celda.setColspan(1);
					celda.setPadding( 4f );
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tablaHistorico.addCell(celda);
		          
					totalACuenta += Float.parseFloat( historico.getHacCantidad() );
					
					String precio = Utils.formateaCantidad(Float.toString(Float.parseFloat(historico.getHacCantidad())));
					//PRECIO DEL PRODUCTO
					celda = new PdfPCell(new Phrase( precio , fuenteParrafo));
					celda.setColspan(1);
					celda.setPadding( 4f );
					celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
					celda.setPaddingLeft( 10f );
					tablaHistorico.addCell(celda);
				}
				
				pintaTotalACuenta( tablaHistorico, totalACuenta );
				
				document.add(  tablaHistorico );
				
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pintaTotalACuenta( PdfPTable tablaHistorico, float total ){
		try{
			
			PdfPCell celda = new PdfPCell(new Phrase( "Cantidad Total", fuenteParrafo) );
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_CENTER);
			celda.setBackgroundColor( dameColorFondo( fondoCeldaFiltro ) );
			celda.setPaddingBottom( 4f );
			tablaHistorico.addCell(celda);
		       
			celda = new PdfPCell( new Phrase( Utils.formateaCantidad(Float.toString(total)), fuenteParrafo));
			celda.setColspan(1);
			celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celda.setPaddingBottom( 4f );
			tablaHistorico.addCell(celda);
       
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void cerrarDocumento( HttpServletResponse response ) throws IOException{
		 document.close();
	     response.setHeader("Content-type", "application/pdf");
	     response.setHeader("Content-Disposition","inline; filename=\"InformeDeCobros.pdf\"");
	
	     DataOutput output = new DataOutputStream( response.getOutputStream() );
	     output.write( buffer.toByteArray());
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
