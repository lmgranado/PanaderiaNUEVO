package panaderia.struts.actions;

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

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.AlbaranesVenta;
import panaderia.beans.Entregas;
import panaderia.beans.NotasEntrega;
import panaderia.negocio.bCuerposPdf;
import utils.Utils;

public class PrevisionCrearAction extends Action{
	
	public BaseFont helvetica(){
		BaseFont fuente = null;
		try{
			fuente = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
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
		  String agCliente = request.getParameter("agCliente");
		  String agFamilia = request.getParameter("agFamilia");
		  String agRuta = request.getParameter("agRuta");
		  String agEntrega = request.getParameter("agEntrega");
		  String notFechaInicio = request.getParameter("notFechaInicio");
		  String notFechaFin = request.getParameter("notFechaFin");
		  String notClId = request.getParameter("notClId");
		  String rutIds = request.getParameter("rutIds");
		  String entIds = request.getParameter("entIds");
		  String directo = request.getParameter("directo");
		  		  
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
	
	      bCuerposPdf cuerpo = new bCuerposPdf();
	      
	      PdfPCell celdaCompleta = new PdfPCell(new Phrase("", fuenteParrafoNegrita));  
	      celdaCompleta.setColspan(6);
	      celdaCompleta.setCellEvent(rectangle);
	      celdaCompleta.setHorizontalAlignment(Element.ALIGN_CENTER);
	      celdaCompleta.setBorderWidth(0);
	      
	      Date fechaInicioDate = Utils.string2Date(notFechaInicio, Utils.DATE_SHORT);
	      Date fechaFinDate = Utils.string2Date(notFechaFin, Utils.DATE_SHORT);
	      
	      ArrayList listaPrevision = new ArrayList();
	      if(!"".equals(directo))
	    	listaPrevision = AlbaranesVenta.getAllPrevisionFabricacionDirecto(Utils.date2String(fechaInicioDate, Utils.DATE_MYSQL_SHORT), Utils.date2String(fechaFinDate, Utils.DATE_MYSQL_SHORT), agCliente, directo, notClId, agFamilia);
	      else if("".equals(entIds) && "".equals(rutIds))
		  	listaPrevision = NotasEntrega.getAllPrevisionFabricacion(Utils.date2String(fechaInicioDate, Utils.DATE_MYSQL_SHORT), Utils.date2String(fechaFinDate, Utils.DATE_MYSQL_SHORT), agCliente, agRuta, notClId, "", agFamilia);
	      else if(!entIds.equals("")){
	    	entIds = "0" + entIds;
	    	listaPrevision = Entregas.getAllPrevisionFabricacion(agCliente, agRuta, agEntrega, agFamilia, entIds);
	      }else if(!rutIds.equals("")){
	    	rutIds = "0" + rutIds;
		    listaPrevision = NotasEntrega.getAllPrevisionFabricacionRutas(Utils.date2String(fechaInicioDate, Utils.DATE_MYSQL_SHORT), Utils.date2String(fechaFinDate, Utils.DATE_MYSQL_SHORT), agCliente, agRuta, notClId, rutIds, agFamilia);
	      }
	      
		  PdfPTable tabla = cuerpo.generaCuerpoPrevision(agCliente, agRuta, agEntrega, agFamilia, listaPrevision, request);
		  
		  celdaCompleta.addElement(tabla);
	      
	      PdfPTable tablaCompleta = new PdfPTable(1);
	      tablaCompleta.setWidthPercentage(100);
	      tablaCompleta.addCell(celdaCompleta);
	      document.add(tablaCompleta);

	      
	      
	      document.close();
	      
	      response.setHeader("Content-type", "application/pdf");
	      response.setHeader("Content-Disposition","inline; filename=\"previsionFabricacion.pdf\"");
	
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