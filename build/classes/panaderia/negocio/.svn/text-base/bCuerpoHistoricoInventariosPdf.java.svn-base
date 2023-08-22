package panaderia.negocio;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.HistoricoInventarios;
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

public class bCuerpoHistoricoInventariosPdf extends PdfPageEventHelper{
	
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
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 14, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
    public Font fuenteNotaMediana = new Font(helvetica(), 11, Font.BOLD);
        
    
	/** Método que genera la cabecera de la Factura donde se encuentre el logo de la empresa y los datos de la misma */
	public void generaCabeceraInventario(HistoricoInventarios historicoinventario, ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
				
		try{
				  
	      PdfPTable datosEmpresa = new PdfPTable(1);
	      datosEmpresa.setWidthPercentage(100);
	      datosEmpresa.setWidths(new int[]{100});
			
	      PdfPCell celdaDatos = new PdfPCell(new Phrase( "PANADERIA PINEPAN", fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      datosEmpresa.addCell(celdaDatos);
	      
	      PdfPTable tableTitulo = new PdfPTable(1);
	      tableTitulo.setWidthPercentage(30);
	      tableTitulo.setWidths(new int[]{30});
	      tableTitulo.setSpacingAfter(10f);
	      
	      celdaDatos = new PdfPCell(new Phrase( "Histórico de inventarios", fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaDatos.setBorderWidthBottom( 2 );
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 5 );
	      celdaDatos.setPaddingTop( 15 );
	      tableTitulo.addCell(celdaDatos);      
	          
	      PdfPTable tableFechaUsuario = new PdfPTable(4);
	      tableFechaUsuario.setWidthPercentage(100);
	      tableFechaUsuario.setWidths(new int[]{15,80,12,20});
	      tableFechaUsuario.setSpacingAfter(20f);
	      tableFechaUsuario.setHorizontalAlignment( Element.ALIGN_LEFT );
	      
	      celdaDatos = new PdfPCell(new Phrase( "Usuario:", fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 5 );
	      celdaDatos.setPaddingTop( 15 );
	      tableFechaUsuario.addCell(celdaDatos); 
	      
	      Usuarios usuario = Usuarios.getUsuariosByUsId( historicoinventario.getHinvUsId() );
	      celdaDatos = new PdfPCell(new Phrase( usuario.getUsNombre() + " ("+usuario.getUsLogin()+")" , fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 5 );
	      celdaDatos.setPaddingTop( 15 );
	      tableFechaUsuario.addCell(celdaDatos); 
	      
	      celdaDatos = new PdfPCell(new Phrase( "Fecha:", fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 5 );
	      celdaDatos.setPaddingTop( 15 );
	      tableFechaUsuario.addCell(celdaDatos); 
	      
	      celdaDatos = new PdfPCell(new Phrase( historicoinventario.getHinvFecha( Utils.DATE_SHORT), fuenteNotaMediana));
	      celdaDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
	      celdaDatos.setBorderWidth(0);
	      celdaDatos.setPaddingBottom( 5 );
	      celdaDatos.setPaddingTop( 15 );
	      tableFechaUsuario.addCell(celdaDatos); 
	      
	      document.add( datosEmpresa );
	      document.add( tableTitulo );
	      document.add( tableFechaUsuario );
	      
	      //tablaCabecera.addCell( datosEmpresa );
	      //tablaCabecera.addCell( tableTitulo );
	      
		}catch (Exception e) {
			System.out.println(e);
		}
        
	}
	    
    /** Genera el cuerpo de la Factura, mostrando todos los detalles de los productos del Cliente*/
	public void generaCuerpoInventario(ArrayList listaDetalles, HttpServletRequest request, Document document) throws DocumentException, IOException{
	
		PdfPTable tablaCuerpo = new PdfPTable(6);
		try{
			tablaCuerpo.setWidthPercentage(100);
			tablaCuerpo.setWidths(new int[]{8,25,12,10,10,24});
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
		      
		      
		      //LOTE DEL PRODUCTO
		      celda = new PdfPCell(new Phrase("Lote", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //RECUENTO
		      celda = new PdfPCell(new Phrase("Recuento", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      //REGULARIZACION
		      celda = new PdfPCell(new Phrase("Regulariz.", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
		      //OBSERVACIONES
		      celda = new PdfPCell(new Phrase("Observaciones", fuenteParrafoNegrita));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 2f );
		      tablaCuerpo.addCell(celda);
		      
			for(int i=0; i<listaDetalles.size(); i++){
			
				HistoricoInventariosDetalle detalle = (HistoricoInventariosDetalle) listaDetalles.get(i);
				
			  //CODIGO DEL PRODUCTO
		      celda = new PdfPCell(new Phrase(detalle.getHinvdProdId(), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 2);
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //DESCRIPCION DEL PRODUCTO
		      celda = new PdfPCell(new Phrase(detalle.getHinvdNombreProducto(), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_LEFT);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //LOTE DEL PRODUCTO
		      celda = new PdfPCell(new Phrase(detalle.getHinvdLote(), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      
		      //RECUENTO
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getHinvdRecuento()))), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //REGULARIZACION
		      celda = new PdfPCell(new Phrase(Utils.formateaCantidad(Float.toString(Float.parseFloat(detalle.getHinvdRegularizacion()))), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      celda.setBorderWidthRight( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      //OBSERVACIONES
		      celda = new PdfPCell(new Phrase(detalle.getHinvdObservaciones(), fuenteParrafo9));
		      celda.setColspan(1);
		      celda.setPaddingBottom( 4 );
		      celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		      celda.setBorder( 0 );
		      celda.setBorderWidthBottom( 0.5f );
		      tablaCuerpo.addCell(celda);
		      
		      /*** DATOS ***/
		      
			}
	      //Añdimos el cuerpo del inventario
	      document.add(  tablaCuerpo );
	      
	      //Generamos el pie de la Factura
	      this.generaPieFactura( request, document );

		}catch (Exception e) {
			System.out.println(e);
		}
	}
		
	
	/** Método que genera el pie de la factura con los totales de la misma */
	public void generaPieFactura( HttpServletRequest request, Document document ){
		
		PdfPTable tablaPie = new PdfPTable(6);
		try{
			tablaPie.setWidthPercentage(100);
			tablaPie.setSpacingBefore( 10f );
			tablaPie.setWidths(new int[]{22,16,4,20,16,17});
			//Parte 1
			PdfPCell celda = new PdfPCell(new Phrase("", fuenteParrafo9));
	      
			
	       tablaPie.addCell(celda);
       
       document.add( tablaPie );
       
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
		
	

}
