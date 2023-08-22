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
import panaderia.beans.FacturasVenta;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.NotasEntregaDetalle;
import panaderia.negocio.bCuerpoFichasProductosPdf;
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

public class GenerarFichasProductosAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
								ActionForm form, 
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {    
		ActionForward forward = null;
		try
	    {
	
	      bCuerpoFichasProductosPdf cuerpo = new bCuerpoFichasProductosPdf();
	
		  String[] chkValues = request.getParameterValues("checkList");	
		  
		  cuerpo.generaFichasProductos( request, response, chkValues );
		  
	    }
	    catch( Exception e )
	    {
	      System.out.println(e);
	    }
	    return forward;
    }
}