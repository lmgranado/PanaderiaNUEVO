package panaderia.struts.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import com.lowagie.text.Image;



public class GeneraGraficos {
	
	//private static final String ruta = Configurator.getInstance().getValue("RUTAEXCEL");
	private static String rutaInformes = "";
	private Paint colores[] = {Color.blue, Color.orange, Color.cyan, Color.green, Color.magenta, Color.orange, Color.pink, Color.yellow, Color.darkGray, Color.gray, Color.white, Color.blue, Color.red, Color.green, Color.magenta, Color.orange, Color.pink, Color.yellow, Color.darkGray, Color.gray, Color.white, Color.blue, Color.red, Color.yellow};
	//Contiene todos los datos necesarios para generar el informe
	private ArrayList lista = new ArrayList();
	//Diferentes tamanyos de graficos dependiendo si es en PDF o en RTF
	private int xBar = 530;
	private int yBar = 550;
	private Font helvetica1 = new Font("Verdana", Font.BOLD, 11);
	
	static{
		ResourceBundle config = ResourceBundle.getBundle( "informes" );
		rutaInformes = config.getString( "ruta_informes" );
	}
	
	
	public GeneraGraficos( ArrayList lista ) {
		this.lista = lista;
		//Introducimos esto ya que el tamaño de los gráficos los interpreta de foma diferente en RTF que en PDF
		helvetica1 = new Font("Verdana", Font.BOLD, 10);
	}
	
	public ArrayList crearGraficoBarras( String agrupacion ){
		String xtitle = "Clientes";
		if( "1".equals( agrupacion ) )
			xtitle = "Productos";
		String ytitle =	"";
		ArrayList listaImagenes = new ArrayList();
		int bucle = (int)Math.floor(lista.size()/20) + 1;
		for(int contador=0; contador<bucle; contador++){
			String rutaFichero = rutaInformes +"Informe_de_Ventas" + contador + ".jpg";
			try{
				File fichero = new File( rutaFichero );
				fichero.delete();
				FileOutputStream salidaGrafico = new FileOutputStream( fichero );
				float calidadJPEG = 1.00f;
			
				DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
				for(int k = contador*20; k< lista.size() && k < (contador + 1)*20 ;k++ ){
					String [] datos = (String[]) this.lista.get( k );
					dataSet.setValue( Float.parseFloat(datos[0]) , datos[1], datos[1] );			
				}
				
				//creamos el objeto grafico
				JFreeChart jfreechart = ChartFactory.createStackedBarChart3D("", xtitle, ytitle, dataSet, PlotOrientation.HORIZONTAL, false, true, false);
				//Titulo del Gráfico
				TextTitle title = new TextTitle("Precio (€)");
				title.setPosition( RectangleEdge.LEFT );
			    jfreechart.setTitle(title);
			    jfreechart.getTitle().setMargin(0, 0, 5, 0);
			    jfreechart.getTitle().setFont( helvetica1 );
			    //jfreechart.getLegend().setItemFont( helvetica2 );
				title.setPosition(RectangleEdge.TOP);
				//Leyenda del Grafico en la parte inferior
				//jfreechart.getLegend().setPosition(RectangleEdge.BOTTOM);
				jfreechart.setBorderPaint( Color.BLACK );
				jfreechart.setBorderVisible(true);
				
				CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();			
				int re=0;
				while( re<10 ){	
		             plot.getRenderer().setSeriesPaint( re, colores[re] );  
					 re++;
				}
				BarRenderer renderer = (BarRenderer)plot.getRenderer();
				renderer.setMaximumBarWidth(.02);
		        
				ChartUtilities.writeChartAsJPEG(salidaGrafico, calidadJPEG, jfreechart, xBar, yBar);
					
				salidaGrafico.flush();
				salidaGrafico.close();
				listaImagenes.add(rutaFichero);
			}catch(Exception e){
				System.out.println("[crearFicheroGrafico] "+e);
			}
		}
		
		return listaImagenes;
	}

}
