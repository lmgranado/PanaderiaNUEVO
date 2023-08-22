package panaderia.struts.actions;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.Clientes;
import panaderia.beans.FacturasCompra;
import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.beans.Proveedores;
import panaderia.negocio.bCuerposFacturasCompraPdf;
import panaderia.negocio.bCuerposFacturasVentaPdf_old;
import utils.Utils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class GenerarFacturasCompraAction extends Action{
	
	
	private static String rutaFacturas = "";
	
	static{
		ResourceBundle config = ResourceBundle.getBundle( "informes" );
		rutaFacturas = config.getString( "ruta_facturas" );
	}
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			//fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			String ruta = ResourceBundle.getBundle("informes").getString("ruta_aplicacion");
            String rutaFuente = ruta + "/fuentes/" + "VERDANA.TTF";
            fuente = BaseFont.createFont(rutaFuente, rutaFuente, false);
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
    public Font fuenteParrafoCursiva = new Font(helvetica(), 9, Font.ITALIC);
    public Font fuenteParrafoNegrita = new Font(helvetica(), 9, Font.BOLD);
    public Font fuenteNotaGrande = new Font(helvetica(), 13, Font.BOLD,Color.orange);
    public Font fuenteParrafoNegritaCursiva = new Font(helvetica(), 9, Font.BOLDITALIC);
    public Font fuenteParrafoSubrayado = new Font(helvetica(), 9, Font.NORMAL | Font.UNDERLINE);
    public Font fuenteParrafoCursivaSubrayado = new Font(helvetica(), 9, Font.ITALIC | Font.UNDERLINE);
    public Font fuenteParrafoNegritaSubrayado = new Font(helvetica(), 9, Font.BOLD | Font.UNDERLINE);
    public Font fuenteParrafoNegritaCursivaSubrayado = new Font(helvetica(), 9, Font.BOLDITALIC | Font.UNDERLINE);
	
    
    
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
		  String fcId = request.getParameter("fcId");
		  String agrupados = request.getParameter("agrupados");
			
		  Document document = new Document();    
	      document.setPageSize(PageSize.A4);
	      ByteArrayOutputStream buffer    = new ByteArrayOutputStream();
	      PdfWriter             writer    = PdfWriter.getInstance( document, buffer );
		  
	      document.open();
	      
	      PdfContentByte cb = writer.getDirectContent();      
	      
	      HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
	      pie.setBorderWidth(0);
	      document.setFooter(pie);
	
	      bCuerposFacturasCompraPdf cuerpo = new bCuerposFacturasCompraPdf();
	      
		  if( !"".equals(Utils.skipNull( fcId )) ){
			  FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId( fcId );
			  Proveedores proveedor = Proveedores.getProveedoresByPrId( factura.getFcPrId() );
			  
			  ArrayList listaDetalles = FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId( fcId );
			  
			  if( "A".equals(factura.getFcIva()) ){
				  PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( factura, proveedor, listaDetalles, request );
				  //Añadimos la cabecera
				  document.add(tablaCabecera);
				  
			  }
			  
			  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosProveedor( factura, proveedor, listaDetalles, request );
			  document.add(tablaCabeceraDatosClientes);
			  
		      //Añadimos el cuerpo de la factura
			  cuerpo.generaCuerpoFactura( factura, proveedor, listaDetalles, request, document, new ArrayList());
		    
			  //Pasamos a la siguiente factura
		      document.newPage();
			
		  }else if( !"".equals(Utils.skipNull( agrupados )) ){
			  //Para la parte de agrupados
			  //Cogemos todas las facturas marcadas
			 /* String[] chkValues = request.getParameterValues("checkList");
			  String idsFacturasAgrupadas = Utils.arrayToComaString(chkValues);
			  //Obtenemos los diferentes clientes de las facturas
			  ArrayList listaClientes = FacturasVentaDetalle.getIdsClientesByFvdFvIds(idsFacturasAgrupadas);
			  
			  for(int i=0; i<listaClientes.size(); i++){
				  String idCliente = ((String[])listaClientes.get(i))[0];
				  //Tenemos que ver que datos de la factura mostramos. Vamos a necesitar el nombre, y las fechas entre y hasta
				  ArrayList fechas = FacturasCompraDetalle.getFechasAgrupadas(idsFacturasAgrupadas, idCliente);
				  
				  Clientes cliente = Clientes.getClientesByClId(idCliente );
				  ArrayList listaDetalles = FacturasCDetalle.getFacturasVentaDetalleAgrupadasByFvdFvIdsAndFvClId(idsFacturasAgrupadas, idCliente);
				  
				  PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( new FacturasVenta(), cliente, listaDetalles, request );
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( fechas, cliente, listaDetalles, request );
				  
				  //Añadimos la cabecera
				  document.add(tablaCabecera);
			      document.add(tablaCabeceraDatosClientes);
			      
			      //Añadimos el cuerpo de la factura
				  cuerpo.generaCuerpoFactura( new FacturasVenta(), cliente, listaDetalles, request, document, fechas );
			    
				  //Pasamos a la siguiente factura
			      document.newPage();
			  }*/
		  }else{
			  String[] chkValues = request.getParameterValues("checkList");	
			  for(int i=0; i<chkValues.length; i++){
				  FacturasCompra factura = FacturasCompra.getFacturasCompraByFcId( chkValues[i] );
				  Proveedores proveedor = Proveedores.getProveedoresByPrId( factura.getFcPrId() );
				  
				  ArrayList listaDetalles = FacturasCompraDetalle.getFacturasCompraDetalleByFcdFcId( chkValues[i] );
				  
				  if( "A".equals(factura.getFcIva()) ){
					  PdfPTable tablaCabecera = cuerpo.generaCabeceraFactura( factura, proveedor, listaDetalles, request );
					  //Añadimos la cabecera
					  document.add(tablaCabecera);
					  
				  }
				  
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosProveedor( factura, proveedor, listaDetalles, request );
				  document.add(tablaCabeceraDatosClientes);
				  
			      //Añadimos el cuerpo de la factura
				  cuerpo.generaCuerpoFactura( factura, proveedor, listaDetalles, request, document, new ArrayList());
			    
				  //Pasamos a la siguiente factura
			      document.newPage();
			  }
		  }
	      
	      
	      document.close();
	      
	      response.setHeader("Content-type", "application/pdf");
	      response.setHeader("Content-Disposition","inline; filename=\"FacturasCompra.pdf\"");
	
	      DataOutput output = new DataOutputStream(response.getOutputStream());
	      output.write( buffer.toByteArray());
	      
	      
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}