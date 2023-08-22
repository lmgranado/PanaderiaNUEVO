package panaderia.struts.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import panaderia.beans.FacturasCompraDetalle;
import panaderia.beans.FacturasVentaDetalle;
import panaderia.beans.Productos;
import panaderia.beans.Stock;
import utils.Utils;

public class GenerarEtiquetasVentasAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {  


			ActionForward forward = null;
			Date ahora = new Date();
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/yy");
		    String fecha = mdyFormat.format(ahora);

			
			try{
			
				final String gramos = ResourceBundle.getBundle( "etiquetas" ).getString( "gramos" );
				final String kilos = ResourceBundle.getBundle( "etiquetas" ).getString( "kilos" );
				
				
				Enumeration enumera = request.getParameterNames();
				
				while(enumera.hasMoreElements()){
					
					String nombrePS = (String)enumera.nextElement();
					
					if( nombrePS.indexOf("fvdId_") != -1 ){
				
						String fvdId = nombrePS.replace("fvdId_", "");
						FacturasVentaDetalle detalle = FacturasVentaDetalle.getFacturasVentaDetalleByFvdId( fvdId );
						Stock stock = Stock.getStockByStId( detalle.getFvdStId() );
						Productos producto = Productos.getProductosByProdId( stock.getStProdId() );
						int numEtiquetas = Integer.parseInt( Utils.skipNullNumero( request.getParameter(nombrePS) ) );	
						
						if( numEtiquetas > 0 ){
							String nombre = producto.getProdNombre();
							float peso = Float.parseFloat( producto.getProdPesoTotal() );
							String unidad = kilos;
							if( peso < 1 ){
								peso = peso * 1000;
								unidad = gramos;
							}
							
							String copias = "P" + numEtiquetas;
							
				    		String comando = "N\n"
				    				/*+"FK\"ALBA\"\n"
				    				+"FS\"ALBA\"\n"
				    				+"V00,40,L,\"vender1\"\n"
				    				+"V01,40,L,\"vender2\"\n"
				    				+"V02,40,L,\"vender3\"\n"
				    				+"V03,33,L,\"formaVenta\"\n"
				    				+"V04,30,L,\"infolote1\"\n"
				    				+"V05,26,L,\"descripcion\"\n"
				    				+"V06,10,L,\"anyo\"\n"
				    				+"V07,6,L,\"09/09\"\n"
				    				+"V08,16,L,\"lote\"\n"*/
				    				+"Q300\n"
				    				+"q550\n"
				    				+"S4\n"
				    				+"D12\n"
				    				+"ZB\n" 
				    				+"I8,1,034 y\n"
				    				//+"TTh:m:s:,+ \n"
				    				//if( no posee ingredientes)
				    				+"A24,20,0,4,1,1,N," +nombre+ "\n"
				    				+"A24,55,0,2,1,1,N,\"PESO NETO:" + peso + " " + unidad +"\"\n"
				    				+"A24,77,0,2,1,1,N,\"LOTE  \"\n"
				    				+"A96,77,0,2,1,1,N,"+fecha+"\n"
				    				+"A168,77,0,2,1,1,N,\"- \"\n"
				    				+"A192,77,0,2,1,1,N,"+ stock.getStProdLote() +"\n"
				    				//+"A24,108,0,1,1,1,N,\"INGRED: COMINO, OREGANO, PIMENTON  \"\n"
				    				//+"A24,130,0,1,1,1,N,\"COMINO, OREGANO, PIMENTON  \"\n"
				    				//+"A24,96,0,1,1,1,N,\"\"\n"
				    				//+"A24,120,0,1,1,1,N,\"\"\n"
				    				//+"A24,144,0,1,1,1,N,\"\"\n"				
				    				+"A24,176,0,2,1,1,N,"+stock.getStProdOrigen()+"\n"
				    				+"A24,230,0,1,1,2,N,\"Consumir pref.antes del\"\n"
				    				+"A280,230,0,3,1,1,N,"+ stock.getStFCaducidad("dd/MM/yyyy")+ "\n"
				    				+"B465,240,3,E30,2,7,62,B,"+producto.getProdCodigoBarras()+"\n" 
				    				//+"B160,30,1,1A,2,7,50,B,\"612041600021580109\"\n" 
				    				+"FE\n"
				    				+"" + copias + "\n";//imprimir numero de etiquetas
				    				/*+"FR\"ALBA\"\n"
				    				+"?\n"
				    				 
				    				+"\"PESO NETO APROX: 1 KG\"\n"
				    				 
				    				+"\"ANIS GRANO\"\n"                
				    				+"\"31/12/2015\"\n"
				    				+"\"01/13\"\n"
				    				+"\"3\"\n"*/
				    				// "FR"ALBA"
				    	    		//?
				    		
				    		/* IMPRIMIR EN IMPRESORA POR DEFECTO */
				    		//Cogemos el servicio de impresión por defecto (impresora por defecto)
				    /*		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
				    			
			    			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			    			Doc doc = new SimpleDoc(comando.getBytes(), flavor, null);
			    			DocPrintJob docPrintJob = service.createPrintJob();
				    			   
				    		//Mandamos a impremir el documento
				    		docPrintJob.print(doc, null);  */
				    		
				    		/* FIN DE IMPRIMIR EN IMPRESORA POR DEFECTO */
				    		
				    		
				    		/* IMPRIMIR EN IMPRESORA POR NOMBRE DE IMPRESORA */
				    		
				    		 //Formato de Documento
				    		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
				    	    //Lectura de Documento
				    	    Doc document = new SimpleDoc(comando.getBytes(), flavor, null);
				    	 
				    	    //Nombre de la impresora
				    	    String printerName = "zebra";
				    	 
				    	    //Inclusion del nombre de impresora y sus atributos
				    	    AttributeSet attributeSet = new HashAttributeSet();
				    	    attributeSet.add(new PrinterName(printerName, null));
				    	    attributeSet = new HashAttributeSet();
				    	    //Soporte de color o no
				    	    attributeSet.add(ColorSupported.NOT_SUPPORTED);
				    	 
				    	    //Busqueda de la impresora por el nombre asignado en attributeSet
				    	    PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, attributeSet);
				    	 
				    	    System.out.println("Imprimiendo en : " + services[0].getName());
				    	 
				    	    //DocPrintJob printJob = services[0].createPrintJob();
				    	    //Envio a la impresora
				    	    //printJob.print(document, new HashPrintRequestAttributeSet());
				    	 
				    	    //inputStream.close();
				    	    
				    	  //En caso de que tengamos varias impresoras configuradas
				    	    PrintService myPrinter = null;
				    	    for (int i = 0; i < services.length; i++){
				    	    	if (services[i].getName().equals(printerName)){
				    	    		myPrinter = services[i];
				    	    		System.out.println("Imprimiendo en : " + services[i].getName());
				    	    		break;
				    	    	}
				    	    }

				    	    DocPrintJob printJob = myPrinter.createPrintJob();
				    	    //Envio a la impresora
				    	    PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
				    	    
				    	    printJob.print(document, aset);
						}
					}
				}
			}catch( Exception e ){
				System.out.println("Error al imprimir etiquetas compras: "+e.getMessage());
			}
			
			return forward;
	}
			
}