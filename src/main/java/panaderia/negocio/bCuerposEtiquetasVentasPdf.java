package panaderia.negocio;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import panaderia.beans.ComposicionFabricacion;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.HistoricoInventariosDetalle;
import panaderia.beans.Productos;
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
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class bCuerposEtiquetasVentasPdf extends PdfPageEventHelper{
	
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
	
	Font fuenteNombre = new Font(Font.TIMES_ROMAN, 11, Font.BOLD);
	Font fuenteDescripcion = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);
    
	
    public void generaEtiquetas( Document document, FacturasVentaDetalle detalle, int numEtiquetas, Image imagen ) throws DocumentException, IOException{
		
		try{
			  
	        PdfPTable tablaCabecera = new PdfPTable(2);
			tablaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaCabecera.getDefaultCell().setBorder( 0 );
			tablaCabecera.setWidthPercentage(98);
			//tablaCabecera.setSpacingAfter(5);
		 	//tablaCabecera.setSpacingBefore(5f);
			tablaCabecera.setWidths(new int[]{80,18});
			
			for(int j=0; j<numEtiquetas; j++){
				
			 	PdfPTable tablatexto = new PdfPTable(1);
			 	tablatexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	tablatexto.getDefaultCell().setBorder( 1 );
			 	tablatexto.setWidthPercentage(100);	
			
			 	PdfPCell celdaTexto = new PdfPCell(new Phrase(detalle.getFvdProducto(), fuenteNombre));
			 	celdaTexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	celdaTexto.setBorderWidth(0);
			 	tablatexto.addCell(celdaTexto);
			 	
			 	//Productos producto = Productos.getProductosByProdId( prodId );
			 	celdaTexto = new PdfPCell(new Phrase("PESO NETO APROX: 1 KG", fuenteDescripcion));
			 	celdaTexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	celdaTexto.setBorderWidth(0);
			 	tablatexto.addCell(celdaTexto);
			 	
			 	celdaTexto = new PdfPCell(new Phrase("LOTE 09/14 - E07568", fuenteDescripcion));
			 	celdaTexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	celdaTexto.setBorderWidth(0);
			 	celdaTexto.setFixedHeight(30);
			 	tablatexto.addCell(celdaTexto);
			
			 	celdaTexto = new PdfPCell(new Phrase("Origen: india", fuenteDescripcion));
			 	celdaTexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	celdaTexto.setBorderWidth(0);
			 	tablatexto.addCell(celdaTexto);
			 	
			 	celdaTexto = new PdfPCell(new Phrase("Comsumir pref. antes de: 31/12/14", fuenteDescripcion));
			 	celdaTexto.setHorizontalAlignment(Element.ALIGN_LEFT);
			 	celdaTexto.setBorderWidth(0);
			 	tablatexto.addCell(celdaTexto);
			 	
			 	PdfPTable tablaImagen = new PdfPTable(1);
			 	tablaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 	tablaImagen.getDefaultCell().setBorder( 0 );
			 	tablaImagen.setWidthPercentage(100);
			 	//tablaImagen.setSpacingAfter(15);
			 	//tablaImagen.setSpacingBefore(15f);
			
			 	PdfPCell celdaImagen = new PdfPCell(new Phrase("", fuenteDescripcion));
			 	celdaImagen = new PdfPCell(imagen);
			 	celdaImagen.setHorizontalAlignment(Element.ALIGN_MIDDLE);
			 	celdaImagen.setBorderWidth(0);
			 	tablaImagen.addCell(celdaImagen);
	        
			 	tablaCabecera.addCell(tablatexto );
			 	tablaCabecera.addCell(tablaImagen );
				 	
				document.add(tablaCabecera);
			}
		      
		}catch (Exception e) {
			System.out.println(e);
		}
	        
	}
	    
}
