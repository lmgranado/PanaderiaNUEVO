package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.lowagie.text.*;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;



public class UtilsFacturacion {

	public static float getImporte(float precio, float descuento, float iva, float cantidad){
		float importe = 0;
		try{
			if(iva==0)
				importe = (precio - (precio * descuento)) * cantidad;
			else
				importe = ((precio - (precio * descuento)) + (iva * precio)) * cantidad;
		}catch (Exception e) {
			System.out.println(e);
		}
		return importe;
	}
	
	
	public static float getConvertCmsToPoints(float cm) {
     	return cm * 28.3464567f;
     }
	
	public static Image getBarcode(Document document,  PdfWriter pdfWriter, String servicio,String  codigoTransaccion){
	 	PdfContentByte cimg = pdfWriter.getDirectContent();
	   	Barcode128 code128 = new Barcode128();
	   	code128.setCode(servicio + addZeroLeft(codigoTransaccion));
	   	code128.setCodeType(Barcode128.CODE128);
		code128.setTextAlignment(Element.ALIGN_CENTER);
		Image image = code128.createImageWithBarcode(cimg, null, null);
		float scaler = ((document.getPageSize().getWidth() - document.leftMargin()  - document.rightMargin() - 0) / image.getWidth()) * 70;
		image.scalePercent(scaler);
		image.setAlignment(Element.ALIGN_CENTER);
		return image;
	}
	
	 private static String addZeroLeft(String num) {
	    	NumberFormat formatter = new DecimalFormat("0000000");
	     	return formatter.format((num != null) ? Integer.parseInt(num) : 0000000);
		}
}