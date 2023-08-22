package panaderia.struts.actions;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.AlbaranesVentaDetalle;
import panaderia.beans.Clientes;
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.negocio.bCuerposAlbaranesVentaPdf;
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

public class GenerarAlbaranesVentaAction extends Action{
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		}catch (Exception e) {
			
		}
		return fuente;
	}
	
	
	public class RoundRectangle implements PdfPCellEvent {  
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
		  String avId = request.getParameter("avId");
			
	      Document document = new Document();    
	      document.setPageSize(PageSize.A4);
	      	    
	      RoundRectangle rectangle = new RoundRectangle();
	      ByteArrayOutputStream buffer    = new ByteArrayOutputStream();
	      PdfWriter             writer    = PdfWriter.getInstance( document, buffer );
	      
	      document.addCreationDate();
	      document.addCreator("GESTOR PANADERIA");
	      document.addProducer();
	      document.open();
	      
	      PdfContentByte cb = writer.getDirectContent();      
	      
	      HeaderFooter pie = new HeaderFooter(new Phrase("", fuenteParrafoCursiva), false);
	      pie.setBorderWidth(0);
	      document.setFooter(pie);
	
	      bCuerposAlbaranesVentaPdf cuerpo = new bCuerposAlbaranesVentaPdf();
	      
		  if( !"".equals(Utils.skipNull( avId )) ){
			  AlbaranesVenta albaran = AlbaranesVenta.getAlbaranesVentaByAvId( avId );
			  Clientes cliente = Clientes.getClientesByClId( albaran.getAvClId() );
			  //ArrayList listaDetalles = AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdAvId( avId );
			  ArrayList listaDetalles = AlbaranesVentaDetalle.getAlbaranesVentaDetalleAgrupadosByAvdAvId( avId );
			  
			  //PdfPTable tablaCabecera = cuerpo.generaCabeceraAlbaran( albaran, cliente, listaDetalles, request );
			  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( albaran, cliente, listaDetalles, request );
			  
			  //A�adimos la cabecera
			  //document.add(tablaCabecera);
		      document.add(tablaCabeceraDatosClientes);
		      
		      //A�adimos el cuerpo de la factura
			  cuerpo.generaCuerpoAlbaran( albaran, cliente, listaDetalles, request, document );
			  
		  }else{
			  String[] chkValues = request.getParameterValues("checkListImpresion");	
			  for(int i=0; i<chkValues.length; i++){
				  AlbaranesVenta albaran = AlbaranesVenta.getAlbaranesVentaByAvId( chkValues[i] );
				  Clientes cliente = Clientes.getClientesByClId( albaran.getAvClId() );
				  //ArrayList listaDetalles = AlbaranesVentaDetalle.getAlbaranesVentaDetalleByAvdAvId( chkValues[i] );
				  ArrayList listaDetalles = AlbaranesVentaDetalle.getAlbaranesVentaDetalleAgrupadosByAvdAvId( chkValues[i] );
				  
				  //PdfPTable tablaCabecera = cuerpo.generaCabeceraAlbaran( albaran, cliente, listaDetalles, request );
				  PdfPTable tablaCabeceraDatosClientes = cuerpo.generaCabeceraDatosCliente( albaran, cliente, listaDetalles, request );
				  
				  //A�adimos la cabecera
				  //document.add(tablaCabecera);
			      document.add(tablaCabeceraDatosClientes);
			      
			      //A�adimos el cuerpo de la factura
				  cuerpo.generaCuerpoAlbaran( albaran, cliente, listaDetalles, request, document );
			    
				  //Pasamos a la siguiente factura
			      document.newPage();
			  }
		  }
	      
	      
	      document.close();
	      
	      response.setHeader("Content-type", "application/pdf");
	      response.setHeader("Content-Disposition","inline; filename=\"AlbaranVenta.pdf\"");
	
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