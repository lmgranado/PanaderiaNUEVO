package panaderia.negocio;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import panaderia.beans.FacturasVenta;

/**
 * Para generar una hoja Excel Simple
 *
 * @author jose
 *
 */
public class bCuerpoParteDiarioExcel {

	/**
	 * Para escribir el contenido de una celda.
	 *
	 * @param row Row.
	 * @param i posicion en la fila.
	 * @param value texto a escribir.
	 * @param style estilo de la celda.
	 */
	public static void createCell(Row row, int i, String value, CellStyle style) {
		Cell cell = row.createCell(i);
		value = value+" ";
		cell.setCellValue(value);
		// si no hay estilo, no se aplica
		if (style != null)
			cell.setCellStyle(style);
	}
	//-------------

	/**
	 * Crea una hoja Excel con el contenido especificado.
	 * @param v Vector con los datos a escribir en la hoja.
	 * @param namesheet nombre de la hoja.
	 * @param filename path del fichero donde se escribe.
	 */
	public static void crearExcel(ArrayList v, String nombreFichero )
			throws Exception {
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet(nombreFichero);
			int filas = v.size();
			for (int i = 0; i < filas; i++) {
				String [] fila = (String[]) v.get(i);
				Row row = sheet.createRow(i);
				for( int j=0;j<fila.length;i++)
					// para la cabecera, la primera fila, aplicamos un estilo (negrita y color de fondo azul)
					if (i == 0) {
						CellStyle style = wb.createCellStyle();
						style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);
						Font font = wb.createFont();
						// font.setFontHeightInPoints((short)10);
						font.setFontName("Courier New");
						// font.setItalic(true);
						// font.setStrikeout(true);
						font.setBoldweight(Font.BOLDWEIGHT_BOLD);
						font.setColor(IndexedColors.WHITE.getIndex());
						style.setFont(font);
						//createCell(row, j, token, style);
					} else{
						j++;
						//createCell(row, j, token, null);

				}

			}

			// Asignar automaticamente el tamaño de las celdas en funcion del contenido
			for (int i = 0; i < filas; i++) {
				sheet.autoSizeColumn(i);
			}

			// Escribir el fichero.
			FileOutputStream fileOut = new FileOutputStream("C:/"+nombreFichero);
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
