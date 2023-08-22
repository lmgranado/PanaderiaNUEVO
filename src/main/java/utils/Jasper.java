




package utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.export.JRRtfExporter;



  public class Jasper{

  public static void main(String[] args) {
	  		  

	  
    Connection conexion = null;
    try {
      Map parameters = new HashMap();
      Collection c = new ArrayList();
      c.add("204");
      parameters.put("listaFacturas", c);
      
      JasperReport report = JasperCompileManager.compileReport("C:\\Documents and Settings\\Ivan\\Escritorio\\informePadre.jrxml");
      
      conexion = utils.PoolConexiones.conexion();
      JasperPrint print = JasperFillManager.fillReport(report, parameters, conexion);
      JasperPrint print2 = JasperFillManager.fillReport(report, parameters, conexion);
      ArrayList lista = new ArrayList();
      lista.add(print);
      lista.add(print2);
      
      
      
      JRRtfExporter exporter = new JRRtfExporter();

      exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, lista);
      exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C:\\informe.rtf");
      exporter.exportReport();
      
      JasperViewer.viewReport(print, false);
      
      
      
    }
    catch (Exception e) {
    	Log4j.error("Error", e);
    }
    finally {
      
      try {
        if (conexion != null) {
          conexion.close();
        }
      }
      catch (Exception e) {
    	  Log4j.error("Error", e);
      }
    }

  }
}
