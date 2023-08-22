package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.ComposicionFabricacion;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.HistoricoInventariosDetalle;
import panaderia.beans.Proveedores;
import panaderia.beans.Usuarios;
import panaderia.utilidades.Empresa;
import utils.Utils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;

public class bCuerposFabricacionPdf extends PdfPageEventHelper{
	
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
    
    private int numProductos = 20;
    private float tamanyoTablaCuerpo = 450;
    
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
	public void generaCabeceraFabricacion(FacturasCompra factura, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
		
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
		      
		      celdaDatos = new PdfPCell(new Phrase( "FABRICACIÓN PROPIA", fuenteNotaMediana));
		      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celdaDatos.setBorderWidthBottom( 2 );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 15 );
		      tableTitulo.addCell(celdaDatos);      
		          
		      PdfPTable tableFechaUsuario = new PdfPTable(4);
		      tableFechaUsuario.setWidthPercentage(100);
		      tableFechaUsuario.setWidths(new int[]{20,60,12,20});
		      tableFechaUsuario.setSpacingAfter(20f);
		      tableFechaUsuario.setHorizontalAlignment( Element.ALIGN_LEFT );
		      
		      celdaDatos = new PdfPCell(new Phrase( "Proveedor:", fuenteNotaMediana));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      celdaDatos = new PdfPCell(new Phrase( proveedor.getPrNombre() , fuenteParrafo9));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      celdaDatos = new PdfPCell(new Phrase( "Fecha:", fuenteNotaMediana));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      celdaDatos = new PdfPCell(new Phrase( factura.getFcFecha( Utils.DATE_SHORT ), fuenteParrafo9));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      celdaDatos = new PdfPCell(new Phrase( "Nº Fabricación:", fuenteNotaMediana));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      celdaDatos = new PdfPCell(new Phrase( factura.getFcId(), fuenteParrafo9));
		      celdaDatos.setVerticalAlignment( Element.ALIGN_MIDDLE );
		      celdaDatos.setBorderWidth(0);
		      celdaDatos.setColspan(3);
		      celdaDatos.setPaddingBottom( 5 );
		      celdaDatos.setPaddingTop( 10 );
		      tableFechaUsuario.addCell(celdaDatos); 
		      
		      document.add( datosEmpresa );
		      document.add( tableTitulo );
		      document.add( tableFechaUsuario );
		      
		      
			}catch (Exception e) {
				System.out.println(e);
			}
	        
	}
	    
    /** Genera el cuerpo de la Factura, mostrando todos los detalles de los productos del Cliente*/
	public void generaCuerpoFabricacion(FacturasCompra factura, Proveedores proveedor, ArrayList listaDetalles, HttpServletRequest request, Document document, ArrayList fechas) throws DocumentException, IOException{
		

		PdfPTable tablaCuerpo = new PdfPTable(5);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{8,25,12,10,10});
			tablaCuerpo.getDefaultCell().setBorder( 0 );
			tablaCuerpo.setHorizontalAlignment( Element.ALIGN_LEFT );
						
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafoNegrita));
	        
//			CODIGO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("Código", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      //DESCRIPCION DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("Producto", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //CANTIDAD
		      celda = new PdfPCell(new Phrase("Cantidad", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //FECHA DE CADUCIDAD
		      celda = new PdfPCell(new Phrase("Fecha Caduc.", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      //LOTE
		      celda = new PdfPCell(new Phrase("Lote", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		     
		      
			for(int i=0; i<listaDetalles.size(); i++){
			
				FacturasCompraDetalle detalle = (FacturasCompraDetalle) listaDetalles.get(i);
				
			  //CODIGO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase(detalle.getFcdReferencia(), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 2);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      celda.setBorderWidthTop( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //DESCRIPCION DEL PRODUCTO
		      celda = new PdfPCell(new Phrase(detalle.getFcdProducto(), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //CANTIDAD
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getFcdCantidad()))), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //FECHA DE CADUCIDAD
		      celda = new PdfPCell(new Phrase(detalle.getFcdFCaducidad( Utils.DATE_SHORT), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //LOTE
		      celda = new PdfPCell(new Phrase(detalle.getFcdLote(), fuenteParrafo8));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorderWidth( 0.5f);
		      celda.setBorderWidthRight( 0 );
		      tablaCuerpo.addCell(celda);
		      
		      
				      //Lista de Componentes del producto en la fabricacion
				      ArrayList componentes = (ArrayList) detalle.getComponentesUsados();
				      
				      
				      celda = new PdfPCell(new Phrase("", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(2);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 0f );
				      tablaCuerpo.addCell(celda);
				      
				      //PRODUCTO USADO
				      celda = new PdfPCell(new Phrase("Producto Usado", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      //LOTE
				      celda = new PdfPCell(new Phrase("Lote Usado", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      
				      //CANTIDAD
				      celda = new PdfPCell(new Phrase("Cantidad", fuenteParrafoCursivaNegrita8));
				      celda.setColspan(1);
				      celda.setPaddingBottom( 4 );
				      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				      celda.setBorder( 0 );
				      celda.setBorderWidthBottom( 1f );
				      celda.setBorderWidthTop( 1f );
				      celda.setBorderWidthLeft( 1f );
				      celda.setBorderWidthRight( 1f );
				      tablaCuerpo.addCell(celda);
				      
				      for(int j=0; j<componentes.size(); j++){
				    	  ComposicionFabricacion componente = (ComposicionFabricacion) componentes.get(j);
				    	  
				    	  celda = new PdfPCell(new Phrase(" ", fuenteParrafoCursiva8));
					      celda.setColspan(2);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      tablaCuerpo.addCell(celda);
					      
					      //DESCRIPCION DEL PRODUCTO
					      celda = new PdfPCell(new Phrase(componente.getNombreProdUsado(), fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      celda.setBorderWidthLeft( 0.5f );
					      tablaCuerpo.addCell(celda);
					      
					      celda = new PdfPCell(new Phrase(componente.getLote(), fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      celda.setBorderWidthLeft( 0.5f );
					      tablaCuerpo.addCell(celda);
					      
					      
					      //CANTIDAD
					      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(componente.getCfCantidad()))), fuenteParrafoCursiva8));
					      celda.setColspan(1);
					      celda.setPaddingBottom( 4 );
					      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					      celda.setBorder( 0 );
					      celda.setBorderWidthBottom( 0.5f );
					      celda.setBorderWidthRight( 0.5f );
					      tablaCuerpo.addCell(celda);
				      }
				      
				      //SALTO DE LINEA
				      celda = new PdfPCell(new Phrase(" ", fuenteParrafoNegrita));
				      celda.setColspan(5);
				      celda.setPaddingBottom( 4 );
				      celda.setBorder( 0 );
				      tablaCuerpo.addCell(celda);
		      
			}
	      //Añdimos el cuerpo del inventario
	      document.add(  tablaCuerpo );
	      

		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
}
